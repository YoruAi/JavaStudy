package AdvancedFeatures.Chapter3;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.nio.file.Path;

public class XPathTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(Path.of(XMLParseTest.XMLFile).toFile());
        // XPath表达式 //
        // 具体规则详见卷二p148-149
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();

        // Java9之前使用evaluate(...,XPathConstants.NODESET|NODE|NUMBER|STRING)
        // 未知类型
        XPathEvaluationResult<?> result = xpath.evaluateExpression("/document/parent/son", doc);
        result.type();  // 类型
        result.value();
        // 获取文本
        String text = xpath.evaluate("/document/parent/son/text()", doc);
        // 获取处理结果数字
        int number = xpath.evaluateExpression("count(/document/parent/son)", doc, Integer.class);
        // 多节点
        XPathNodes nodes = (XPathNodes) xpath.evaluateExpression("/ducument/parent/son", doc,
                XPathNodes.class); // 可使用foreach遍历
        // 单节点
        Node node = (Node) xpath.evaluateExpression("/document/parent/son[1]", doc,
                Node.class);
        // 相对节点
        String resolveText = xpath.evaluate("text()", node);
    }
}
