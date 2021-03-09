import Algorithms.*;
import SupportClasses.Draw;
import SupportClasses.Process;
import SupportClasses.ProcessFactory;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Main extends Application{

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

        Draw venster = new Draw();

        Scanner sc= new Scanner(System.in);

        int keuze=keuzeMenu(sc);

        switch(keuze){
            case(1):
                venster.TrTs(stage,"10.000");break;
            case(2):
                venster.TrTs(stage,"20.000");break;
            case(3):
                venster.TrTs(stage,"50.000");break;
            case(4):
                venster.wait(stage,"10.000");break;
            case(5):
                venster.wait(stage,"20.000");break;
            case(6):
                venster.wait(stage,"50.000");break;
            default:
                System.out.println("Foutje"); break;
        }

        stage.show();

    }

    private static int keuzeMenu(Scanner sc) {
        printKeuze();
        return sc.nextInt();

    }

    private static void printKeuze(){
        StringBuilder keuzeMenu = new StringBuilder();
        keuzeMenu.append("Tik het nummer in van uw keuze, gevolgd door enter: \n");
        keuzeMenu.append("0. Afsluiten  \n");
        keuzeMenu.append("1. Genormaliseerde omlooptijd bij 10.000 processen \n");
        keuzeMenu.append("2. Genormaliseerde omlooptijd bij 20.000 processen \n");
        keuzeMenu.append("3. Genormaliseerde omlooptijd bij 50.000 processen \n");
        keuzeMenu.append("4. Wachttijd bij 10.000 processen \n");
        keuzeMenu.append("5. Wachttijd bij 20.000 processen \n");
        keuzeMenu.append("6. Wachttijd bij 50.000 processen \n \n");
        System.out.println(keuzeMenu.toString());
    }

}