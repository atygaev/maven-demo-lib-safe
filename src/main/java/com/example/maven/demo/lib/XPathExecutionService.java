package com.example.maven.demo.lib;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class XPathExecutionService {

    public static List<String> findByXPath(Path sourceFile, String xpathExpression) {
        try {
            var documentBuilderFactory = DocumentBuilderFactory.newInstance();

            var documentBuilder = documentBuilderFactory.newDocumentBuilder();

            var document = documentBuilder.parse(sourceFile.toFile());

            var nodes = (NodeList) XPathFactory.newInstance()
                    .newXPath()
                    .compile(xpathExpression)
                    .evaluate(document, XPathConstants.NODESET);

            return IntStream.range(0, nodes.getLength())
                    .mapToObj(nodes::item)
                    .map(XPathExecutionService::toString)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find by XPath in given file: " + e.getMessage(), e);
        } finally {
            System.out.println();
            System.err.println("[INFO] Used version of maven-demo-lib is safe.");
//            System.err.println("[ERROR] Used version of maven-demo-lib is dangerous.");
            System.out.println();
        }
    }

    private static String toString(Node node) {
        var buffer = new StringWriter();

        try {
            var transformer = TransformerFactory.newInstance().newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(node), new StreamResult(buffer));

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert XML node to string: " + e.getMessage(), e);
        }

        return buffer.toString();
    }
}