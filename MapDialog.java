    // Kartankatseluohjelman graafinen k�ytt�liittym�
     
    import javax.swing.*;
    import javax.swing.event.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.net.*;
     
    public class MapDialog extends JFrame {
     
      // K�ytt�liittym�n komponentit
     
      private JLabel imageLabel = new JLabel();
      private JPanel leftPanel = new JPanel();
     
      private JButton refreshB = new JButton("P�ivit�");
      private JButton leftB = new JButton("<");
      private JButton rightB = new JButton(">");
      private JButton upB = new JButton("^");
      private JButton downB = new JButton("v");
      private JButton zoomInB = new JButton("+");
      private JButton zoomOutB = new JButton("-");
     
      public MapDialog() throws Exception {
        int n = 20;
        int s = -20;
        int w = -40;
        int e = 40;
     
        // Valmistele ikkuna ja lis�� siihen komponentit
     
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
     
        // ALLA OLEVAN TESTIRIVIN VOI KORVATA JOLLAKIN MUULLA ERI ALOITUSN�KYM�N
        // LATAAVALLA RIVILL�
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
        // ALLA OLEVIEN KOLMEN TESTIRIVIN TILALLE SILMUKKA JOKA LIS�� K�YTT�LIITTYM��N
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
      // KAIKKIEN NAPPIEN YHTEYDESS� VOINEE HY�DYNT�� updateImage()-METODIA
      private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
          if(e.getSource() == refreshB) {
            //try { updateImage(); } catch(Exception ex) { ex.printStackTrace(); }
          }
          if(e.getSource() == leftB) {
            // TODO:
            // VASEMMALLE SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
          }
          if(e.getSource() == rightB) {
            // TODO:
            // OIKEALLE SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
          }
          if(e.getSource() == upB) {
            // TODO:
            // YL�SP�IN SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
          }
          if(e.getSource() == downB) {
            // TODO:
            // ALASP�IN SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
          }
          if(e.getSource() == zoomInB) {
            // TODO:
            // ZOOM IN -TOIMINTO
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
          }
          if(e.getSource() == zoomOutB) {
            // TODO:
            // ZOOM OUT -TOIMINTO
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
          }
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
     
      // Tarkastetaan mitk� karttakerrokset on valittu,
      // tehd��n uudesta karttakuvasta pyynt� palvelimelle ja p�ivitet��n kuva
      public void updateImage() throws Exception {
        String s = "";
     
        // Tutkitaan, mitk� valintalaatikot on valittu, ja
        // ker�t��n s:��n pilkulla erotettu lista valittujen kerrosten
        // nimist� (k�ytet��n haettaessa uutta kuvaa)
        Component[] components = leftPanel.getComponents();
        for(Component com:components) {
            if(com instanceof LayerCheckBox)
              if(((LayerCheckBox)com).isSelected()) s = s + com.getName() + ",";
        }
        if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
     
        // TODO:
        // getMap-KYSELYN URL-OSOITTEEN MUODOSTAMINEN JA KUVAN P�IVITYS ERILLISESS� S�IKEESS�
        // imageLabel.setIcon(new ImageIcon(url));
      }
     
    } // MapDialog
