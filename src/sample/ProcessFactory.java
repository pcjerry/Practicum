package sample;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProcessFactory {

    public Queue<Process> leesProcessen(String s) throws SAXException, IOException, ParserConfigurationException {

        Queue<Process> processen = new LinkedList<>();

        File file;
        if (s.equals("10000")) {
            file = new File("processen10000.xml");
        } else if (s.equals("20000")) {
            file = new File("processen20000.xml");
        } else {
            file = new File("processen50000.xml");
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);

        NodeList list = document.getElementsByTagName("process");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Process p = new Process();
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {								//we willen een element node
                Element element = (Element) node;
                NodeList children = element.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node n = children.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) n;
                        if (e.getNodeName() == "pid") {
                            p.setId(Integer.parseInt(e.getTextContent()));
                        }
                        if (e.getNodeName() == "arrivaltime") {
                            p.setarrivaltime(Integer.parseInt(e.getTextContent()));
                        }
                        if (e.getNodeName() == "servicetime") {
                            p.setservicetime(Integer.parseInt(e.getTextContent()));
                            p.setServicetimeneeded(p.getServicetime());
                            processen.add(p);
                        }
                    }
                }
            }
        }
        return processen;
    }
}
