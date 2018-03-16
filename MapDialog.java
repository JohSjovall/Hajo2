    // Kartankatseluohjelman graafinen kï¿½yttï¿½liittymï¿½
     
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
    import java.util.logging.Level;
    import java.util.logging.Logger;

    import javax.xml.parsers.DocumentBuilder;
    import javax.xml.parsers.DocumentBuilderFactory;
    import javax.xml.parsers.ParserConfigurationException;
    import javax.xml.xpath.XPath;
    import javax.xml.xpath.XPathConstants;
    import javax.xml.xpath.XPathExpression;
    import javax.xml.xpath.XPathExpressionException;
    import javax.xml.xpath.XPathFactory;
    
    public class MapDialog extends JFrame {
     
      // Kï¿½yttï¿½liittymï¿½n komponentit
     
      private JLabel imageLabel = new JLabel();
      private JPanel leftPanel = new JPanel();
     
      private JButton refreshB = new JButton("Pï¿½ivitï¿½");
      private JButton leftB = new JButton("<");
      private JButton rightB = new JButton(">");
      private JButton upB = new JButton("^");
      private JButton downB = new JButton("v");
      private JButton zoomInB = new JButton("+");
      private JButton zoomOutB = new JButton("-");
      
      private final String ServerinOsoite = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1";
      private final String SRS = "EPSG:4326";
      
      
    // Resoluutio & formaatti 
        private final int Leveys = 960;
        private final int Korkeus = 480;
        private final String IMAGE_FORMAT = "image/png";
        private final boolean TRANSPARENCY = true;
      
      
    // Kuvan sijainti
        private int x = 0;
        private int y = 0;
<<<<<<< HEAD
        private int z = 200;
        private int o = 100;
=======
        private int z = 80;
        private int o = 20;
>>>>>>> a9c6f450f2a33a54637ffdcdb91834b44686f9d2
     
        
      public MapDialog() throws Exception {

     
        // Valmistele ikkuna ja lisï¿½ï¿½ siihen komponentit
     
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
     
<<<<<<< HEAD
        // UUSI ALOTUSNï¿½KYMï¿½ EHKï¿½?
        String urlA = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX="+x+","+y+","+z+","+o+"&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=bluemarble,cities&STYLES=&FORMAT=image/png&TRANSPARENT=true";
=======
        // UUSI ALOTUSNÄKYMÄ EHKÄ?
        String urlA = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX="+x+","+x+","+y+","+y+"&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=bluemarble,cities&STYLES=&FORMAT=image/png&TRANSPARENT=true";
>>>>>>> a9c6f450f2a33a54637ffdcdb91834b44686f9d2
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
        CheckBoxes();
        
     
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
        private void CheckBoxes() {
          String url = ServerinOsoite + "&REQUEST=GetCapabilities";
          try {
              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
              for (String l : getLayers(doc)) {
                  LayerCheckBox b = new LayerCheckBox(l, l, false);
                  checkboxes.add(b);
                  leftPanel.add(b);
              }
          } catch (ParserConfigurationException | IOException | SAXException e) {
              e.printStackTrace();
          }
      }
      private static List<String> getLayers(Document doc) {
        List<String> list = new ArrayList<>();

        XPathFactory xF = XPathFactory.newInstance();
        XPath xP = xF.newXPath();
        String txt = "/WMT_MS_Capabilities/Capability/Layer/Layer/Name/text()";
        try { 
            XPathExpression expr = xP.compile(txt);
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            // koitin kÃ¤yttÃ¤Ã¤ forEach mut NodeList ei suostunu :(
            for (int i=0; i<nodes.getLength(); i++) {
                list.add(nodes.item(i).getNodeValue());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return list;
      }
<<<<<<< HEAD
=======
      
     
>>>>>>> a9c6f450f2a33a54637ffdcdb91834b44686f9d2
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
<<<<<<< HEAD
          //updateImage();
          System.out.println(x+", "+y+", "+z+", "+o);
=======
            try {
                updateImage();
            } catch (Exception ex) {
                Logger.getLogger(MapDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
          System.out.println("x: " + x + "y: " + y + "z: " + z + "o: " + o);
>>>>>>> a9c6f450f2a33a54637ffdcdb91834b44686f9d2
        }
      }

      public void updateImage()  {
        String set = String.join(",", checkboxes.stream().filter(cb -> cb.isSelected()).map(cb -> cb.getName()).collect(Collectors.toList()));
        new MapThread(set).run();
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
     
        // Tutkitaan, mitkä valintalaatikot on valittu, ja
        // kerätään s:ään pilkulla erotettu lista valittujen kerrosten
        // nimistä (käytetään haettaessa uutta kuvaa)
        Component[] components = leftPanel.getComponents();
        for(Component com:components) {
            if(com instanceof LayerCheckBox)
              if(((LayerCheckBox)com).isSelected()) s = s + com.getName() + ",";
        }
        if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
        
        new MapThread(s).run();
      }
          

    // Säie joka hakee uuden karttakuvan palvelimelta
      private class MapThread extends Thread {
        private String Tasot;

        public MapThread(String s) {
            this.Tasot = s;
        }

        // mene.jpg
        public void run() {
            int x1 = x - 2 * z,
                y1 = y - z,
                x2 = x + 2 * z,
                y2 = y + z;

            String url = ServerinOsoite
                    + "&REQUEST=GetMap"
                    + String.format("&BBOX=%d,%d,%d,%d", x1, y1, x2, y2)
                    + "&SRS=" + SRS
                    + "&WIDTH=" + Leveys
                    + "&HEIGHT=" + Korkeus
                    + "&LAYERS=" + Tasot
                    + "&STYLES="
                    + "&FORMAT=" + IMAGE_FORMAT
                    + "&TRANSPARENT=" + TRANSPARENCY;

            try {
                imageLabel.setIcon(new ImageIcon(new URL(url)));
            } catch (MalformedURLException m) {
            }
        }
    }

} // MapDialog
