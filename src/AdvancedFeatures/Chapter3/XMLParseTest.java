package AdvancedFeatures.Chapter3;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class XMLParseTest {
    public static String XMLFile = "src/AdvancedFeatures/Chapter3/resources/Document.xml";

    public static void main(String[] args) throws ParserConfigurationException, IOException,
            SAXException, XMLStreamException {
        // XML文档结构 //
        // 文档头(可选): <?xml version="1.0" encoding="UTF-8"?>
        // 文档类型定义(可选): <!DOCTYPE web-app ...>
        // 正文: 元素可以有子元素和文本<size unit="pt">36</size>
        // 其他规则详见卷二p124
        // xmlns属性使用命名空间，要开启DocumentBuilderFactory的setNamespaceAware方法


        // 解析XML //
        // 树形解析器(占内存)
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document doc = builder.parse(Path.of(XMLFile).toFile());    // 可传入文件、URL、输入流等

        Element root = doc.getDocumentElement();    // 返回根元素
        String rootTag = root.getTagName();         // 获得元素标签名
        NamedNodeMap attrs = root.getAttributes();  // 获取属性，或getAttribute("key");
        for (int i = 0; i < attrs.getLength(); ++i) {
            Node attr = attrs.item(i);
            String key = attr.getNodeName();
            String value = attr.getNodeValue();
        }
        NodeList childNodes = root.getChildNodes();         // 获得子元素列表
        for (int i = 0; i < childNodes.getLength(); ++i) {  // 也可通过firstChild,nextSibling来遍历
            Node child = childNodes.item(i);
            String localName = child.getLocalName();        // 获取本地名
            if (child instanceof Element childElement) {
                // Element元素
                if (childElement.getTagName().equals("text")) {
                    Text textNode = (Text) childElement.getFirstChild();
                    String text = textNode.getData().trim();
                } else if (childElement.getTagName().equals("number")) {
                    Text textNode = (Text) childElement.getFirstChild();
                    double text = Double.parseDouble(textNode.getData().trim());
                }
            } else if (child instanceof Text childText) {
                // Text文本
                String text = childText.getData().trim();       // 获取文本型元素数据
            } else if (child instanceof Comment childComment) {
                // Comment注释
                String text = childComment.getData().trim();    // 获取文本型元素数据
            }
        }

        // 流机制解析器(不关心上下文)
        // 详见卷二p154
        // 1-SAX解析器（事件回调）
        // startDocument, endDocument
        // startElement, endElement
        // characters
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        SAXParser parser = saxParserFactory.newSAXParser();
        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException {
                if (localName.equalsIgnoreCase("a") && attributes != null)
                    for (int i = 0; i < attributes.getLength(); ++i) {
                        String attrName = attributes.getLocalName(i);
                        if (attrName.equalsIgnoreCase("href"))
                            System.out.println(attributes.getValue(i));
                    }
            }
        };
        parser.parse(Path.of(XMLFile).toFile(), handler);  // 可传入文件，URL，输入流
        // 2-StAX解析器（拉解析器）
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();   // 命名空间默认开启，可使用setProperty设置关闭
        XMLStreamReader streamReader = inputFactory
                .createXMLStreamReader(new FileInputStream(Path.of(XMLFile).toFile())); // 可传入输入流
        while (streamReader.hasNext()) {
            int event = streamReader.next();
            if (event == XMLStreamConstants.START_ELEMENT)
                if (streamReader.getLocalName().equalsIgnoreCase("a")) {
                    String href = streamReader.getAttributeValue(null, "href");
                    if (href != null)
                        System.out.println(href);
                }
        }
    }
}
