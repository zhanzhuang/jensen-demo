package parseXml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;

/**
 * 解析xml
 */
public class Test extends DefaultHandler {
    private static SAXParserFactory parserFactory;
    private static SAXParser parser;
    private static HashMap<String, SecDef> map = new HashMap<String, SecDef>();
    private String xBondTransactableBondInformation_Id;
    private SecDef secdef;
    private Instrmt instrmt;

    @Override
    public void startDocument() {

    }

    // 读取XML的<后开始执行
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {
        if ("SecDef".equals(qName)) {
            secdef = new SecDef();
            instrmt = new Instrmt();
            secdef.setCentraQuoteBondIndic(attributes
                    .getValue("CentraQuoteBondIndic"));
            secdef.setDesc(attributes.getValue("Desc"));
            secdef.setInstrmt(instrmt);
        }
        if ("Instrmt".equals(qName)) {
            xBondTransactableBondInformation_Id = attributes.getValue("ID");
            instrmt.setId(attributes.getValue("ID"));
            instrmt.setSym(attributes.getValue("Sym"));
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) {
    }

    // 读取XML的</后开始执行
    @Override
    public void endElement(String uri, String localName, String qName) {
        map.put(
                xBondTransactableBondInformation_Id, secdef);
    }

    @Override
    public void endDocument() {

    }

    /**
     * 解析方法
     *
     * @return map
     */
    public static HashMap<String, SecDef> loadXML(String filePath) {
        try {
            parserFactory = SAXParserFactory.newInstance();
            parser = parserFactory.newSAXParser();
            Test reader = new Test();
            parser.parse(new InputSource(filePath), reader);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    public static HashMap<String, SecDef> resultMap;

    public static void main(String[] args) {
        resultMap = loadXML("src/input/xml/test.xml");

        for (String key : resultMap.keySet()) {
            System.out.print("key=" + key + "\t");
            System.out.println("value="
                    + resultMap.get(key));
        }
    }

}

