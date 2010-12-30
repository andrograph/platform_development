package com.android.tools.dict;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MakeSlideDictionary {
    public static void main(String[] args) throws Exception {
        
        if (args.length < 2) {
            System.out.println("Usage: " + getCommandName() + " <keyboard file> <dictionary file>");
            System.exit(1);
        }
        
        File keyboardFile = new File(args[0]);
        File wordlistFile = new File(args[1]);
        
        Map<String, Point> keys = getKeysFromKeyboardFile(keyboardFile);

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new FileInputStream(wordlistFile));
        NodeList words = doc.getDocumentElement().getElementsByTagName("w");
        for (int i = 0; i < words.getLength(); i++) {
          Element w = (Element)words.item(i);
          String word = w.getFirstChild().getNodeValue();
          double[] parameters = WordHash.getSlideParameters(word, keys);
          if (parameters == null) {
              System.err.println("ignoring word " + word);
          } else {
              System.out.printf("%s\t",word);
              WordHash.pr(parameters);
          }
        }
    }
    
    private static String getCommandName() {
        return "makeslidedict";
    }

    private static Map<String, Point> getKeysFromKeyboardFile(File keyboardFile) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new FileInputStream(keyboardFile));
        
        Map<String, Point> result = new HashMap<String, Point>();
 
        int keyWidth = getInteger(doc.getDocumentElement(), "keyWidth");
        int keyWeight = getInteger(doc.getDocumentElement(), "keyHeight");
        
        NodeList rows = doc.getDocumentElement().getElementsByTagName("row");
        for (int i = 0; i < rows.getLength(); i++) {
            Element rowEl = (Element)rows.item(i);
            NodeList keys = rowEl.getElementsByTagName("key");
            int rowOffset = getInteger(rowEl, "offset");
            for (int j = 0; j < keys.getLength(); j++) {
                Element keyEl = (Element)keys.item(j);
                String c = keyEl.getAttribute("char");
                int x = rowOffset + keyWidth / 2 + j * keyWidth;
                int y = keyWeight / 2 + i * keyWidth;
                
                result.put(c, new Point(Integer.valueOf(x), Integer.valueOf(y)));
                
            }
        }
        
        return result;
    }

    private static int getInteger(Element el, String attr) {
        return Integer.parseInt(el.getAttribute(attr));
    }
}
