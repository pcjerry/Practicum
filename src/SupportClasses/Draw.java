package SupportClasses;

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
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Draw {

    private ProcessFactory p;
    private Queue<Process> processen;

    public Draw() throws ParserConfigurationException, SAXException, IOException {
        p = new ProcessFactory();
    }

    public void TrTs(Stage stage, String aantal) throws ParserConfigurationException, SAXException, IOException {


        StringBuilder sb = new StringBuilder();
        sb.append("Genormaliseerde Omplooptijd bij ");
        sb.append(aantal);
        sb.append(" processen");

        String titel=sb.toString();

        processen=p.leesProcessen(aantal);


        stage.setTitle(titel);

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("bedieningstijd Ts");
        yAxis.setLabel("Genormaliseerde omlooptijd Ts/Tr ");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(20);
        yAxis.setTickUnit(2);

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle(titel);
        lineChart.setCreateSymbols(false);

        //defining a series
        XYChart.Series seriesFCFS= new XYChart.Series();
        seriesFCFS.setName("FCFS");
        XYChart.Series seriesSJF = new XYChart.Series();
        seriesSJF.setName("SJF");
        XYChart.Series seriesSRT = new XYChart.Series();
        seriesSRT.setName("SRT");
        XYChart.Series seriesRR = new XYChart.Series();
        seriesRR.setName("RR q=2");
        XYChart.Series seriesRR2 = new XYChart.Series();
        seriesRR2.setName("RR q=8");
        XYChart.Series seriesHRRN = new XYChart.Series();
        seriesHRRN.setName("HRRN");
        XYChart.Series seriesMLF = new XYChart.Series();
        seriesMLF.setName("MLF");

        ////////////////////////////VULLEN SERIES///////////////////////////////////

        Scheduler fcfs = new FirstComeFirstServed();
        PriorityQueue<Process> fcfsQue = fcfs.schedule(processen);

        Scheduler sjf = new ShortestJobFirst();
        PriorityQueue<Process> sjfQue = sjf.schedule(processen);

        Scheduler srt = new ShortestRemainingTime();
        PriorityQueue<Process> srtQue = srt.schedule(processen);

        Scheduler rr2 = new RoundRobinV2();
        PriorityQueue<Process> rr2Que = rr2.schedule(processen,2);

        Scheduler rr8 = new RoundRobinV2();
        PriorityQueue<Process> rr8Que = rr8.schedule(processen,8);

        Scheduler hrrn = new HighestResponseRatioNext();
        PriorityQueue<Process> hrrnQue = hrrn.schedule(processen);

        Scheduler mlf = new MultiLevelFeedback();
        PriorityQueue<Process> mlfQue = mlf.schedule(processen);

        vulSeriesNorm(fcfsQue,seriesFCFS);
        vulSeriesNorm(sjfQue,seriesSJF);
        vulSeriesNorm(srtQue,seriesSRT);
        vulSeriesNorm(rr2Que,seriesRR);
        vulSeriesNorm(rr8Que,seriesRR2);
        vulSeriesNorm(hrrnQue,seriesHRRN);
        vulSeriesNorm(mlfQue,seriesMLF);

        /////////////////////////////////////////////////////////////////////////////

        lineChart.getData().add(seriesFCFS);
        lineChart.getData().add(seriesSJF);
        lineChart.getData().add(seriesSRT);
        lineChart.getData().add(seriesRR);
        lineChart.getData().add(seriesRR2);
        lineChart.getData().add(seriesHRRN);
        lineChart.getData().add(seriesMLF);

        Scene scene  = new Scene(lineChart,800,600);

        StringBuffer sb2= new StringBuffer();

        sb2.append("/Users/WouterLegiest/Downloads/ChartPracticeOne/chart_Norm_" + aantal + ".png");

        //saveAsPng(scene, sb2.toString());
        //"/Users/WouterLegiest/Downloads/ChartPracticeOne/chart_Wait_10.png"
        stage.setScene(scene);
    }

    public void wait(Stage stage, String aantal) throws ParserConfigurationException, SAXException, IOException {


        StringBuilder sb = new StringBuilder();
        sb.append("Wachttijd bij ");
        sb.append(aantal);
        sb.append(" processen");

        String titel=sb.toString();

        processen=p.leesProcessen(aantal);

        stage.setTitle(titel);

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("bedieningstijd Ts");
        yAxis.setLabel("Wachttijd Tw ");

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1100);
        yAxis.setTickUnit(100);

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle(titel);
        lineChart.setCreateSymbols(false);

        //defining a series
        XYChart.Series seriesFCFS= new XYChart.Series();
        seriesFCFS.setName("FCFS");
        XYChart.Series seriesSJF = new XYChart.Series();
        seriesSJF.setName("SJF");
        XYChart.Series seriesSRT = new XYChart.Series();
        seriesSRT.setName("SRT");
        XYChart.Series seriesRR = new XYChart.Series();
        seriesRR.setName("RR q=2");
        XYChart.Series seriesRR2 = new XYChart.Series();
        seriesRR2.setName("RR q=8");
        XYChart.Series seriesHRRN = new XYChart.Series();
        seriesHRRN.setName("HRRN");
        XYChart.Series seriesMLF = new XYChart.Series();
        seriesMLF.setName("MLF");

        ////////////////////////////VULLEN SERIES///////////////////////////////////

        Scheduler fcfs = new FirstComeFirstServed();
        PriorityQueue<Process> fcfsQue = fcfs.schedule(processen);

        Scheduler sjf = new ShortestJobFirst();
        PriorityQueue<Process> sjfQue = sjf.schedule(processen);

        Scheduler srt = new ShortestRemainingTime();
        PriorityQueue<Process> srtQue = srt.schedule(processen);

        Scheduler rr2 = new RoundRobinV2();
        PriorityQueue<Process> rr2Que = rr2.schedule(processen,2);

        Scheduler rr8 = new RoundRobinV2();
        PriorityQueue<Process> rr8Que = rr8.schedule(processen,8);

        Scheduler hrrn = new HighestResponseRatioNext();
        PriorityQueue<Process> hrrnQue = hrrn.schedule(processen);

        Scheduler mlf = new MultiLevelFeedback();
        PriorityQueue<Process> mlfQue = mlf.schedule(processen,2);

        vulSeriesWait(fcfsQue,seriesFCFS);
        vulSeriesWait(sjfQue,seriesSJF);
        vulSeriesWait(srtQue,seriesSRT);
        vulSeriesWait(rr2Que,seriesRR);
        vulSeriesWait(rr8Que,seriesRR2);
        vulSeriesWait(hrrnQue,seriesHRRN);
        vulSeriesWait(mlfQue,seriesMLF);

        /////////////////////////////////////////////////////////////////////////////

        lineChart.getData().add(seriesFCFS);
        lineChart.getData().add(seriesSJF);
        lineChart.getData().add(seriesSRT);
        lineChart.getData().add(seriesRR);
        lineChart.getData().add(seriesRR2);
        lineChart.getData().add(seriesHRRN);
        lineChart.getData().add(seriesMLF);
        Scene scene  = new Scene(lineChart,800,600);

        StringBuffer sb2= new StringBuffer();

        sb2.append("/Users/WouterLegiest/Downloads/ChartPracticeOne/chart_Wait_" + aantal + ".png");

        //saveAsPng(scene, sb2.toString());

        //saveAsPng(scene, "//OnsFavorieteVak_Labo1/res/chart_Wait_10.png");
        stage.setScene(scene);
    }

    public void saveAsPng(Scene scene, String path) {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vulSeriesNorm (PriorityQueue<Process> que, XYChart.Series ser){

        Process p;
        int aantal = 0;
        int percentielSize = que.size() / 100;

        double normtat = 0;
        double excute = 0;

        while(!que.isEmpty()){
            p=que.poll();
            normtat+=p.getNormtat();
            excute+=p.getServicetimeneeded();

            if (aantal % percentielSize == 0 && aantal != 0) {
                normtat=normtat/percentielSize;
                excute=excute/percentielSize;
                ser.getData().add(new XYChart.Data(excute, normtat));

                normtat = 0;
                excute = 0;
            }
            aantal++;
        }
    }

    public void vulSeriesWait (PriorityQueue<Process> que, XYChart.Series ser){

        Process p;
        int aantal = 0;
        int percentielSize = que.size() / 100;

        double waittime=0;
        double excute =0;

        while(!que.isEmpty()){

            p=que.poll();
            waittime+=p.getWaittime();
            excute+=p.getServicetimeneeded();

            if (aantal % percentielSize == 0 && aantal != 0) {

                waittime=waittime/percentielSize;
                excute=excute/percentielSize;

                ser.getData().add(new XYChart.Data(excute, waittime));
                waittime = 0;
                excute =0;
            }
            aantal++;
        }
    }
}
