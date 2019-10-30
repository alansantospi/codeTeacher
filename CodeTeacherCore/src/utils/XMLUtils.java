package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLUtils {

	public static void main(String[] args) {
		String text = "<LOGINRESPONSE xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" Message=\"Login Successful\" Token=\"SFTT67FGHUU\" DataFormat=\"CSV\" Header=\"true\" Suffix=\"true\" xmlns=\"http://ws.eoddata.com/Data\" />";
		try (ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes())) {
		    org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bais);
		    System.out.println(doc);
		    NamedNodeMap atts = doc.getDocumentElement().getAttributes();
		    Node node = atts.getNamedItem("Message");
		    System.out.println("Message = " + node.getTextContent());
		} catch (ParserConfigurationException | SAXException | IOException exp) {
		    exp.printStackTrace();
		}
	}
}
