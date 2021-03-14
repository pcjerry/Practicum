package Input;

import Algorithms.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Display {

    private ProcessInput input;
    private Queue<Process> processen;

    public Display() throws ParserConfigurationException, SAXException, IOException {
        input = new ProcessInput();
    }

    //NORMDISPLAY########################################################################

    public void norm(Stage stage, String aantal) throws ParserConfigurationException, SAXException, IOException {

        //Grafiek Titel
        StringBuilder sb = new StringBuilder();
        sb.append("Genormaliseerde Omlooptijd in functie van ");
        sb.append(aantal);
        sb.append(" processen");
        String titel= sb.toString();
        processen=input.uitlezenProcessen(aantal);
        stage.setTitle(titel);


        //Grafiek X-Y-assen
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Bedieningstijd");
        yAxis.setLabel("Genormaliseerde Omlooptijd ");


        //X-Y-assen waarden
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(400);
        xAxis.setTickUnit(40);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(30);
        yAxis.setTickUnit(3);

        //Chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle(titel);
        lineChart.setCreateSymbols(false);

        //Verschillende algoritmen aan chart toevoegen
        XYChart.Series reeksFCFS= new XYChart.Series();
        reeksFCFS.setName("FCFS");
        XYChart.Series reeksSJF = new XYChart.Series();
        reeksSJF.setName("SJF");
        XYChart.Series reeksSRT = new XYChart.Series();
        reeksSRT.setName("SRT");
        XYChart.Series reeksRR = new XYChart.Series();
        reeksRR.setName("RR q= 2");
        XYChart.Series reeksRR1 = new XYChart.Series();
        reeksRR1.setName("RR q= 4");
        XYChart.Series reeksRR2 = new XYChart.Series();
        reeksRR2.setName("RR q=8");
        XYChart.Series reeksHRRN = new XYChart.Series();
        reeksHRRN.setName("HRRN");
        XYChart.Series reeksMLF = new XYChart.Series();
        reeksMLF.setName("MLF.L");
        XYChart.Series reeksMLF2 = new XYChart.Series();
        reeksMLF2.setName("MLF.E");

        //########################################################################

        //Queues invullen aan de series
        Scheduler fcfs = new FirstComeFirstServed();
        PriorityQueue<Process> fcfsQueue = fcfs.schedule(processen);

        Scheduler sjf = new ShortestJobFirst();
        PriorityQueue<Process> sjfQueue = sjf.schedule(processen);

        Scheduler srt = new ShortestRemainingTime();
        PriorityQueue<Process> srtQueue = srt.schedule(processen);

        Scheduler rr2 = new RoundRobin();
        PriorityQueue<Process> rr2Queue = rr2.schedule(processen,2);

        Scheduler rr4 = new RoundRobin();
        PriorityQueue<Process> rr4Queue = rr4.schedule(processen, 4);

        Scheduler rr8 = new RoundRobin();
        PriorityQueue<Process> rr8Queue = rr8.schedule(processen,8);

        Scheduler hrrn = new HighestResponseRatioNext();
        PriorityQueue<Process> hrrnQueue = hrrn.schedule(processen);

        Scheduler mlf = new MultiLevelFeedback();
        PriorityQueue<Process> mlfQueue = mlf.schedule(processen);

        Scheduler mlf2 = new MultiLevelFeedback2();
        PriorityQueue<Process> mlfQueue2 = mlf2.schedule(processen);

        reeksNorm(fcfsQueue, reeksFCFS);
        reeksNorm(sjfQueue, reeksSJF);
        reeksNorm(srtQueue, reeksSRT);
        reeksNorm(rr2Queue, reeksRR);
        reeksNorm(rr4Queue, reeksRR1);
        reeksNorm(rr8Queue, reeksRR2);
        reeksNorm(hrrnQueue, reeksHRRN);
        reeksNorm(mlfQueue, reeksMLF);
        reeksNorm(mlfQueue2, reeksMLF2);

        //########################################################################

        //Toevoegen aan Chart met de reeks
        lineChart.getData().add(reeksFCFS);
        lineChart.getData().add(reeksSJF);
        lineChart.getData().add(reeksSRT);
        lineChart.getData().add(reeksRR);
        lineChart.getData().add(reeksRR1);
        lineChart.getData().add(reeksRR2);
        lineChart.getData().add(reeksHRRN);
        lineChart.getData().add(reeksMLF);
        lineChart.getData().add(reeksMLF2);

        //Resolutie scherm
        Scene scene  = new Scene(lineChart,1280,720);
        stage.setScene(scene);
    }


    //WAITDISPLAY########################################################################

    public void wait(Stage stage, String aantal) throws ParserConfigurationException, SAXException, IOException {

        //Grafiek titel
        StringBuilder sb = new StringBuilder();
        sb.append("Wachttijd in functie van ");
        sb.append(aantal);
        sb.append(" processen");
        String titel=sb.toString();
        processen=input.uitlezenProcessen(aantal);
        stage.setTitle(titel);


        //Grafiek X-Y-assen
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Bedieningstijd");
        yAxis.setLabel("Wachttijd ");


        //X-Y-assen waarden
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(300);
        xAxis.setTickUnit(30);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1000);
        yAxis.setTickUnit(100);

        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle(titel);
        lineChart.setCreateSymbols(false);

        //Verschillende algoritmen aan chart toevoegen
        XYChart.Series reeksFCFS= new XYChart.Series();
        reeksFCFS.setName("FCFS");
        XYChart.Series reeksSJF = new XYChart.Series();
        reeksSJF.setName("SJF");
        XYChart.Series reeksSRT = new XYChart.Series();
        reeksSRT.setName("SRT");
        XYChart.Series reeksRR = new XYChart.Series();
        reeksRR.setName("RR q= 2");
        XYChart.Series reeksRR1 = new XYChart.Series();
        reeksRR1.setName("RR q= 4");
        XYChart.Series reeksRR2 = new XYChart.Series();
        reeksRR2.setName("RR q=8");
        XYChart.Series reeksHRRN = new XYChart.Series();
        reeksHRRN.setName("HRRN");
        XYChart.Series reeksMLF = new XYChart.Series();
        reeksMLF.setName("MLF.L");
        XYChart.Series reeksMLF2 = new XYChart.Series();
        reeksMLF2.setName("MLF.E");


        //########################################################################

        //Queues invullen aan de series
        Scheduler fcfs = new FirstComeFirstServed();
        PriorityQueue<Process> fcfsQueue = fcfs.schedule(processen);

        Scheduler sjf = new ShortestJobFirst();
        PriorityQueue<Process> sjfQueue = sjf.schedule(processen);

        Scheduler srt = new ShortestRemainingTime();
        PriorityQueue<Process> srtQueue = srt.schedule(processen);

        Scheduler rr2 = new RoundRobin();
        PriorityQueue<Process> rr2Queue = rr2.schedule(processen,2);

        Scheduler rr4 = new RoundRobin();
        PriorityQueue<Process> rr4Queue = rr4.schedule(processen, 4);

        Scheduler rr8 = new RoundRobin();
        PriorityQueue<Process> rr8Queue = rr8.schedule(processen,8);

        Scheduler hrrn = new HighestResponseRatioNext();
        PriorityQueue<Process> hrrnQueue = hrrn.schedule(processen);

        Scheduler mlf = new MultiLevelFeedback();
        PriorityQueue<Process> mlfQueue = mlf.schedule(processen);

        Scheduler mlf2 = new MultiLevelFeedback2();
        PriorityQueue<Process> mlfQueue2 = mlf2.schedule(processen);

        reeksWait(fcfsQueue, reeksFCFS);
        reeksWait(sjfQueue, reeksSJF);
        reeksWait(srtQueue, reeksSRT);
        reeksWait(rr2Queue, reeksRR);
        reeksWait(rr4Queue, reeksRR1);
        reeksWait(rr8Queue, reeksRR2);
        reeksWait(hrrnQueue, reeksHRRN);
        reeksWait(mlfQueue, reeksMLF);
        reeksWait(mlfQueue2, reeksMLF2);

        //########################################################################

        //Toevoegen aan Chart met de reeks
        lineChart.getData().add(reeksFCFS);
        lineChart.getData().add(reeksSJF);
        lineChart.getData().add(reeksSRT);
        lineChart.getData().add(reeksRR);
        lineChart.getData().add(reeksRR1);
        lineChart.getData().add(reeksRR2);
        lineChart.getData().add(reeksHRRN);
        lineChart.getData().add(reeksMLF);
        lineChart.getData().add(reeksMLF2);

        //Resolutie scherm
        Scene scene  = new Scene(lineChart,1280,720);
        stage.setScene(scene);
    }


    public void reeksNorm (PriorityQueue<Process> que, XYChart.Series rk){

        Process p;
        int aantal = 0;
        int percentielSize = que.size() / 100;

        double normtat = 0;
        double execute = 0;

        while(!que.isEmpty()){
            p= que.poll();
            normtat+= p.getNormtat();
            execute+= p.getServicetimeneeded();

            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat= normtat/percentielSize;
                execute= execute/percentielSize;
                rk.getData().add(new XYChart.Data(execute, normtat));

                normtat = 0;
                execute = 0;
            }
            aantal++;
        }
    }

    public void reeksWait (PriorityQueue<Process> que, XYChart.Series rk){

        Process p;
        int aantal = 0;
        int percentielSize = que.size() / 100;

        double waittime=0;
        double execute =0;

        while(!que.isEmpty()){

            p= que.poll();
            waittime+= p.getWaittime();
            execute+= p.getServicetimeneeded();

            if (aantal % percentielSize == 0 && aantal != 0) {

                waittime= waittime/percentielSize;
                execute= execute/percentielSize;

                rk.getData().add(new XYChart.Data(execute, waittime));
                waittime= 0;
                execute= 0;
            }
            aantal++;
        }
    }
}
