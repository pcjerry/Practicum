package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main extends Application {

    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        ProcessFactory p = new ProcessFactory();
        Queue<Process> processen = p.leesProcessen("20000");
        Queue<Process> processen2 = p.leesProcessen("10000");
        Queue<Process> processen3 = p.leesProcessen("50000");
//fjfjfj

        stage.setTitle("Process scheduler");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis(0, 100, 10);

        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis();

        final NumberAxis xAxis3 = new NumberAxis();
        final NumberAxis yAxis3 = new NumberAxis(0, 100, 10);

        final NumberAxis xAxis4 = new NumberAxis();
        final NumberAxis yAxis4 = new NumberAxis();

        final NumberAxis xAxis5 = new NumberAxis();
        final NumberAxis yAxis5 = new NumberAxis(0, 100, 10);

        final NumberAxis xAxis6 = new NumberAxis();
        final NumberAxis yAxis6 = new NumberAxis();

        xAxis.setLabel("Bedieningstijd");
        yAxis.setLabel("Genormaliseerde omlooptijd");

        xAxis2.setLabel("Bedieningstijd");
        yAxis2.setLabel("Wachttijd [JIFFY]");

        xAxis3.setLabel("Bedieningstijd");
        yAxis3.setLabel("Genormaliseerde omlooptijd");

        xAxis4.setLabel("Bedieningstijd");
        yAxis4.setLabel("Wachttijd [JIFFY]");

        xAxis5.setLabel("Bedieningstijd");
        yAxis5.setLabel("Genormaliseerde omlooptijd");

        xAxis6.setLabel("Bedieningstijd");
        yAxis6.setLabel("Wachttijd [JIFFY]");

        final LineChart<Number, Number> lineChart1 = new LineChart<Number, Number>(xAxis, yAxis);
        final LineChart<Number, Number> lineChart2 = new LineChart<Number, Number>(xAxis2, yAxis2);
        final LineChart<Number, Number> lineChart3 = new LineChart<Number, Number>(xAxis3, yAxis3);
        final LineChart<Number, Number> lineChart4 = new LineChart<Number, Number>(xAxis4, yAxis4);
        final LineChart<Number, Number> lineChart5 = new LineChart<Number, Number>(xAxis5, yAxis5);
        final LineChart<Number, Number> lineChart6 = new LineChart<Number, Number>(xAxis6, yAxis6);

        lineChart1.setTitle("Genormaliseerde TAT in functie van bedieningstijd /20000");
        lineChart1.setCreateSymbols(false);

        lineChart2.setTitle("Wachttijd in functie van bedieningstijd /20000");
        lineChart2.setCreateSymbols(false);

        lineChart3.setTitle("Genormaliseerde TAT in functie van bedieningstijd /10000");
        lineChart3.setCreateSymbols(false);

        lineChart4.setTitle("Wachttijd in functie van bedieningstijd /10000");
        lineChart4.setCreateSymbols(false);

        lineChart5.setTitle("Genormaliseerde TAT in functie van bedieningstijd /50000");
        lineChart5.setCreateSymbols(false);

        lineChart6.setTitle("Wachttijd in functie van bedieningstijd /50000");
        lineChart6.setCreateSymbols(false);

        XYChart.Series series11 = new XYChart.Series();
        series11.setName("First come first serve");

        XYChart.Series series12 = new XYChart.Series();
        series12.setName("Round robin");

        XYChart.Series series13 = new XYChart.Series();
        series13.setName("Highest response ratio next");

        XYChart.Series series14 = new XYChart.Series();
        series14.setName("Multilevel feedback");


        XYChart.Series series21 = new XYChart.Series();
        series21.setName("First come first serve");

        XYChart.Series series22 = new XYChart.Series();
        series22.setName("Round robin");

        XYChart.Series series23 = new XYChart.Series();
        series23.setName("Highest response ratio next");

        XYChart.Series series24 = new XYChart.Series();
        series24.setName("Multilevel feedback");


        XYChart.Series series31 = new XYChart.Series();
        series31.setName("First come first serve");

        XYChart.Series series32 = new XYChart.Series();
        series32.setName("Round robin");

        XYChart.Series series33 = new XYChart.Series();
        series33.setName("Highest response ratio next");

        XYChart.Series series34 = new XYChart.Series();
        series34.setName("Multilevel feedback");


        XYChart.Series series41 = new XYChart.Series();
        series41.setName("First come first serve");

        XYChart.Series series42 = new XYChart.Series();
        series42.setName("Round robin");

        XYChart.Series series43 = new XYChart.Series();
        series43.setName("Highest response ratio next");

        XYChart.Series series44 = new XYChart.Series();
        series44.setName("Multilevel feedback");


        XYChart.Series series51 = new XYChart.Series();
        series51.setName("First come first serve");

        XYChart.Series series52 = new XYChart.Series();
        series52.setName("Round robin");

        XYChart.Series series53 = new XYChart.Series();
        series53.setName("Highest response ratio next");

        XYChart.Series series54 = new XYChart.Series();
        series54.setName("Multilevel feedback");


        XYChart.Series series61 = new XYChart.Series();
        series61.setName("First come first serve");

        XYChart.Series series62 = new XYChart.Series();
        series62.setName("Round robin");

        XYChart.Series series63 = new XYChart.Series();
        series63.setName("Highest response ratio next");

        XYChart.Series series64 = new XYChart.Series();
        series64.setName("Multilevel feedback");


        Scheduler fcfs = new FirstComeFirstServed();
        PriorityQueue<Process> firstcomefirstserve = fcfs.schedule(processen);


        double normtat = 0;
        double waittime = 0;
        int aantal = 0;
        int percentielSize = firstcomefirstserve.size() / 100;
        Process process;
        while (!firstcomefirstserve.isEmpty()) {
            process = firstcomefirstserve.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series11.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series21.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        Scheduler rr = new RoundRobin(2);
        PriorityQueue<Process> roundrobin = rr.schedule(processen);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!roundrobin.isEmpty()) {
            process = roundrobin.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series12.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series22.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        Scheduler hrrn = new HighestResponseRatioNext();
        PriorityQueue<Process> highestresponserationext = hrrn.schedule(processen);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!highestresponserationext.isEmpty()) {
            process = highestresponserationext.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series13.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series23.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        Scheduler mlfb = new MultilevelFeedback2I();
        PriorityQueue<Process> multilevelfeedback = mlfb.schedule(processen);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!multilevelfeedback.isEmpty()) {
            process = multilevelfeedback.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series14.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series24.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        double[] fcfs1 = fcfs.getParameters();
        double[] rr1 = rr.getParameters();
        double[] hrrn1 = hrrn.getParameters();
        double[] mlfb1 = mlfb.getParameters();


        firstcomefirstserve = fcfs.schedule(processen2);
        normtat = 0;
        aantal = 0;
        waittime = 0;
        percentielSize = processen2.size() / 100;
        while (!firstcomefirstserve.isEmpty()) {
            process = firstcomefirstserve.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series31.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series41.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        roundrobin = rr.schedule(processen2);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!roundrobin.isEmpty()) {
            process = roundrobin.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series32.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series42.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        highestresponserationext = hrrn.schedule(processen2);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!highestresponserationext.isEmpty()) {
            process = highestresponserationext.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series33.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series43.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        multilevelfeedback = mlfb.schedule(processen2);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!multilevelfeedback.isEmpty()) {
            process = multilevelfeedback.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series34.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series44.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        double[] fcfs12 = fcfs.getParameters();
        double[] rr2 = rr.getParameters();
        double[] hrrn2 = hrrn.getParameters();
        double[] mlfb2 = mlfb.getParameters();



        firstcomefirstserve = fcfs.schedule(processen3);
        normtat = 0;
        aantal = 0;
        waittime = 0;
        percentielSize = processen3.size() / 100;
        while (!firstcomefirstserve.isEmpty()) {
            process = firstcomefirstserve.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series51.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series61.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        roundrobin = rr.schedule(processen3);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!roundrobin.isEmpty()) {
            process = roundrobin.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series52.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series62.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        highestresponserationext = hrrn.schedule(processen3);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!highestresponserationext.isEmpty()) {
            process = highestresponserationext.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series53.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series63.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        multilevelfeedback = mlfb.schedule(processen3);

        normtat = 0;
        aantal = 0;
        waittime = 0;
        while (!multilevelfeedback.isEmpty()) {
            process = multilevelfeedback.poll();
            normtat += process.getNormtat();
            waittime += process.getWaittime();
            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat = normtat / percentielSize;
                waittime = waittime / percentielSize;
                series54.getData().add(new XYChart.Data(aantal / percentielSize, normtat));
                series64.getData().add(new XYChart.Data(aantal / percentielSize, waittime));
                normtat = 0;
                waittime = 0;
            }
            aantal++;
        }

        double[] fcfs3 = fcfs.getParameters();
        double[] rr3 = rr.getParameters();
        double[] hrrn3 = hrrn.getParameters();
        double[] mlfb3 = mlfb.getParameters();


        Label fcfs11 = new Label("First first served /10000");
        Label rr11 = new Label("Round robin /10000");
        Label hrrn11 = new Label("Highest response ratio next /10000");
        Label mlfb11 = new Label("Multilevel feedback /10000");

        Label gemomloopfcfs = new Label();
        Label gemGenomloopfcfs = new Label();
        Label gemWaitfcfs = new Label();

        gemomloopfcfs.setText(String.valueOf(fcfs1[0]));
        gemGenomloopfcfs.setText(String.valueOf(fcfs1[1]));
        gemWaitfcfs.setText(String.valueOf(fcfs1[2]));

        Label gemomlooprr = new Label();
        Label gemGenomlooprr = new Label();
        Label gemWaitrr = new Label();

        gemomlooprr.setText(String.valueOf(rr1[0]));
        gemGenomlooprr.setText(String.valueOf(rr1[1]));
        gemWaitrr.setText(String.valueOf(rr1[2]));

        Label gemomloophrrn = new Label();
        Label gemGenomloophrrn = new Label();
        Label gemWaithrrn = new Label();

        gemomloophrrn.setText(String.valueOf(hrrn1[0]));
        gemGenomloophrrn.setText(String.valueOf(hrrn1[1]));
        gemWaithrrn.setText(String.valueOf(hrrn1[2]));

        Label gemomloopmlfb = new Label();
        Label gemGenomloopmlfb = new Label();
        Label gemWaitmlfb = new Label();

        gemomloopmlfb.setText(String.valueOf(mlfb1[0]));
        gemGenomloopmlfb.setText(String.valueOf(mlfb1[1]));
        gemWaitmlfb.setText(String.valueOf(mlfb1[2]));

        Label white = new Label(" ");
        Label white1 = new Label(" ");
        Label white2 = new Label(" ");
        Label white3 = new Label (" ");

        Label legende1 = new Label("Gemiddelde omlooptijd");
        Label legende2 = new Label("Gemiddelde genormaliseerde omlooptijd");
        Label legende3 = new Label("Gemiddelde wachttijd");


        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        VBox labels = new VBox();
        labels.getChildren().addAll(legende1, legende2, legende3, white3, fcfs11, gemomloopfcfs, gemGenomloopfcfs, gemWaitfcfs, white, rr11, gemomlooprr, gemGenomlooprr, gemWaitrr, white1,  hrrn11, gemomloophrrn, gemGenomloophrrn, gemWaithrrn, white2, mlfb11, gemomloopmlfb, gemGenomloopmlfb, gemWaitmlfb);
        hbox1.getChildren().addAll(lineChart3,lineChart1, lineChart5, labels);
        hbox2.getChildren().addAll(lineChart4, lineChart2, lineChart6);

        vbox.getChildren().addAll(hbox1, hbox2);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 1000, 800);
        lineChart1.getData().addAll(series11, series12, series13, series14);
        lineChart2.getData().addAll(series21, series22, series23, series24);
        lineChart3.getData().addAll(series31, series32, series33, series34);
        lineChart4.getData().addAll(series41, series42, series43, series44);
        lineChart5.getData().addAll(series51, series52, series53, series54);
        lineChart6.getData().addAll(series61, series62, series63, series64);


        stage.setScene(scene);
        stage.show();

    }

}
