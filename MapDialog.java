    // Kartankatseluohjelman graafinen käyttöliittymä
     
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;
    import java.io.IOException;

    import javax.xml.parsers.DocumentBuilder;
    import javax.xml.parsers.DocumentBuilderFactory;
    import javax.xml.parsers.ParserConfigurationException;
    import javax.xml.xpath.XPath;
    import javax.xml.xpath.XPathConstants;
    import javax.xml.xpath.XPathExpression;
    import javax.xml.xpath.XPathExpressionException;
    import javax.xml.xpath.XPathFactory;
    
    public class MapDialog extends JFrame {
     
      // Käyttöliittymän komponentit
     
      private JLabel imageLabel = new JLabel();
      private JPanel leftPanel = new JPanel();
     
      private JButton refreshB = new JButton("Pï¿½ivitï¿½");
      private JButton leftB = new JButton("<");
      private JButton rightB = new JButton(">");
      private JButton upB = new JButton("^");
      private JButton downB = new JButton("v");
      private JButton zoomInB = new JButton("+");
      private JButton zoomOutB = new JButton("-");
      
      // Kuvan sijainti
        private int x = 0;
        private int y = 20;
        private int z = 80;
        private int o = 20;
     
      public MapDialog() throws Exception {

     
        // Valmistele ikkuna ja lisää siihen komponentit
     
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
     
        // UUSI ALOTUSNÄKYMÄ EHKÄ?
        String urlA = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX="+s+","+w+","+n+","+e+"&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=bluemarble,cities&STYLES=&FORMAT=image/png&TRANSPARENT=true";
        imageLabel.setIcon(new ImageIcon(new URL(urlA)));
     
        add(imageLabel, BorderLayout.EAST);
     
        ButtonListener bl = new ButtonListener();
        refreshB.addActionListener(bl);  
        leftB.addActionListener(bl);
        rightB.addActionListener(bl);
        upB.addActionListener(bl);
        downB.addActionListener(bl);
        zoomInB.addActionListener(bl);
        zoomOutB.addActionListener(bl);
     
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        leftPanel.setMaximumSize(new Dimension(100, 600));
     
        // TODO:
        // ALLA OLEVIEN KOLMEN TESTIRIVIN TILALLE SILMUKKA JOKA LISï¿½ï¿½ Kï¿½YTTï¿½LIITTYMï¿½ï¿½N
        // KAIKKIEN XML-DATASTA HAETTUJEN KERROSTEN VALINTALAATIKOT MALLIN MUKAAN
        leftPanel.add(new LayerCheckBox("bluemarble", "Maapallo", true));
        leftPanel.add(new LayerCheckBox("cities", "Kaupungit", false));
        
     
        leftPanel.add(refreshB);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(leftB);
        leftPanel.add(rightB);
        leftPanel.add(upB);
        leftPanel.add(downB);
        leftPanel.add(zoomInB);
        leftPanel.add(zoomOutB);
     
        add(leftPanel, BorderLayout.WEST);
     
        pack();
        setVisible(true);
     
      }
     
      public static void main(String[] args) throws Exception {
        new MapDialog();
      }
     
      // Kontrollinappien kuuntelija
      // KAIKKIEN NAPPIEN YHTEYDESSï¿½ VOINEE HYï¿½DYNTï¿½ï¿½ updateImage()-METODIA
      private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
          if(e.getSource() == refreshB) {
            //try { updateImage(); } catch(Exception ex) { ex.printStackTrace(); }
          }
          else if(e.getSource() == leftB) {
            // VASEMMALLE SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
            x = x + o;
          }
          else if(e.getSource() == rightB) {
            // OIKEALLE SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
            x = x - o;
          }
          else if(e.getSource() == upB) {
            // YLï¿½SPï¿½IN SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
            y = y + o;
          }
          else if(e.getSource() == downB) {
            // ALASPï¿½IN SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
            y = y - o;
          }
          else if(e.getSource() == zoomInB) {
            // ZOOM IN -TOIMINTO
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
            z = new Double(z*0.75).intValue();
          }
          else if(e.getSource() == zoomOutB) {
            // ZOOM OUT -TOIMINTO
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
            z = new Double(z*1.25).intValue();
          }
          updateImage();
        }
      }
     
      // Valintalaatikko, joka muistaa karttakerroksen nimen
      private class LayerCheckBox extends JCheckBox {
        private String name = "";
        public LayerCheckBox(String name, String title, boolean selected) {
          super(title, null, selected);
          this.name = name;
        }
        public String getName() { return name; }
      }
     
      // Tarkastetaan mitkï¿½ karttakerrokset on valittu,
      // tehdï¿½ï¿½n uudesta karttakuvasta pyyntï¿½ palvelimelle ja pï¿½ivitetï¿½ï¿½n kuva
      public void updateImage() throws Exception {
        String s = "";
     
        // Tutkitaan, mitkï¿½ valintalaatikot on valittu, ja
        // kerï¿½tï¿½ï¿½n s:ï¿½ï¿½n pilkulla erotettu lista valittujen kerrosten
        // nimistï¿½ (kï¿½ytetï¿½ï¿½n haettaessa uutta kuvaa)
        Component[] components = leftPanel.getComponents();
        for(Component com:components) {
            if(com instanceof LayerCheckBox)
              if(((LayerCheckBox)com).isSelected()) s = s + com.getName() + ",";
        }
        if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
     
        // TODO:
        // getMap-KYSELYN URL-OSOITTEEN MUODOSTAMINEN JA KUVAN Pï¿½IVITYS ERILLISESSï¿½ Sï¿½IKEESSï¿½
        // imageLabel.setIcon(new ImageIcon(url));
      }
     
    } // MapDialog
