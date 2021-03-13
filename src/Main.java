import Algorithms.*;
import Input.*;
import java.util.Scanner;
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Application;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class Main extends Application{

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Plotten
        Display grafiek = new Display();

        //Inlezen van keuze input (0-6)
        Scanner sc= new Scanner(System.in);
        int optie = optieInput(sc);

        switch(optie){
            case(1):
                grafiek.TrTs(stage,"10000");break;
            case(2):
                grafiek.TrTs(stage,"20000");break;
            case(3):
                grafiek.TrTs(stage,"50000");break;
            case(4):
                grafiek.wait(stage,"10000");break;
            case(5):
                grafiek.wait(stage,"20000");break;
            case(6):
                grafiek.wait(stage,"50000");break;
            default:
                System.out.println("ERROR"); break;
        }
        stage.show();
    }

    private static int optieInput(Scanner sc) {
        printOptie();
        return sc.nextInt();
    }

    private static void printOptie(){
        StringBuilder optieInput = new StringBuilder();
        optieInput.append("Type het nummer van gekozen optie + ENTER \n");
        optieInput.append("0: Programma sluiten \n");

        optieInput.append("1: 10000 processen - Genormaliseerde omlooptijd ifv bedieningstijd \n");
        optieInput.append("2: 20000 processen - Genormaliseerde omlooptijd ifv bedieningstijd \n");
        optieInput.append("3: 50000 processen - Genormaliseerde omlooptijd ifv bedieningstijd \n");

        optieInput.append("4: 10000 processen - Wachttijd ifv bedieningstijd \n");
        optieInput.append("5: 20000 processen - Wachttijd ifv bedieningstijd \n");
        optieInput.append("6: 50000 processen - Wachttijd ifv bedieningstijd \n \n");
        System.out.println(optieInput.toString());
    }

}