package Input;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Input.Process;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProcessInput {

    public Queue<Process> leesProcessen(String s) throws SAXException, IOException, ParserConfigurationException {

        Queue<Process> processen = new LinkedList<>();

        File file = null;
        if (s.equals("10.000")) {
            file = new File("processen10000.xml");
        } else if (s.equals("20.000")) {
            file = new File("processen20000.xml");
        } else if (s.equals("50.000")){
            file = new File("processen50000.xml");
        }

        DocumentBuilderFactory docfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuild = docfac.newDocumentBuilder();
        Document doc= docBuild.parse(file);
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("process");
        for (int temp = 0; temp < list.getLength(); temp++) {

            Process p = new Process();
            Node node = list.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                p.setId(Integer.parseInt(element.getElementsByTagName("pid").item(0).getTextContent()));
                p.setArrivaltime(Integer.parseInt(element.getElementsByTagName("arrivaltime").item(0).getTextContent()));
                p.setServicetime(Integer.parseInt(element.getElementsByTagName("servicetime").item(0).getTextContent()));
                p.setServicetimeneeded(p.getServicetime());
                p.setStarttime(-1);

                processen.add(p);

            }
        }
        return processen;
    }
}