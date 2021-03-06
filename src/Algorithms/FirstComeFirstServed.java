package Algorithms;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import Input.Process;

public class FirstComeFirstServed extends Scheduler {

        //Jerry Xiong 11/03/2021

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) throws NullPointerException {

        //Manier voor uitvoeren van de algoritme op basis van een enkele input queue
        //Input: alle input processen
        //Output: een queue van processen met geprocesseerd gegevens
        //Zelfde geval voor andere algoritmes: FCFS, HRRN, SJF, SRT, MLF

        //Opstellen queue van input
        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }

        //Opstellen queue van output
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();

        //Counter gaat hier aanduiden waar de processor zich bevindt (timeslot)
        int count = 0;
        int wait = 0;
        Process tmp;

        //Voorwaarde: loop zolang de queue niet leeg is
        while (!que.isEmpty()) {

            //Process uithalen
            tmp = que.poll();

            if (count < tmp.getArrivaltime()) {

                //Counter aanpassen
                count = tmp.getArrivaltime() + tmp.getServicetime();

                //Parameters opstellen
                tmp.setStarttime(tmp.getArrivaltime());
                tmp.setEndtime(count);

                //Parameters niet uitrekenen (Lokaal)
                tmp.calculate();
                finishedProcesses.add(tmp);

            } else {
                //Definieren Counter, starttijd, stoptijd
                tmp.setStarttime(count);
                count += tmp.getServicetime();
                tmp.setEndtime(count);

                //Parameters berekenen (Lokaal)
                tmp.calculate();
                finishedProcesses.add(tmp);

            }

            //Parameters aanpassen (Globaal)
            tat += tmp.getTat();
            normtat += tmp.getNormtat();
            waittime += tmp.getWaittime();
        }

        tat = tat / input.size();
        normtat = normtat / input.size();
        waittime = waittime / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Globale parameters FCFS: ");
        sb.append(tat + "---" + normtat + "---" + waittime + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {

        //Manier voor uitvoeren van de algoritme op basis van een enkele queue en een slice grootte
        //Slice is niet gebruikt bij FCFS, HRRN, SJF, SRT
        //Input: alle processen
        //Output: een queue van processen met geprocesseerd gegevens
        //Zelfde geval voor andere algoritmes: FCFS, HRRN, RR, SJF, SRT, MLF

        return schedule(q);
    }

    @Override
    public double[] getParameters() {

        //Manier voor oproepen van globale parameters
        //Output: gemiddelde wachttijd, gemiddelde genormaliseerde omlooptijd, gemiddelde omlooptijd

        double [] temp = new double[3];
        temp[0]= tat;
        temp[1]= normtat;
        temp[2] = waittime;
        return temp;
    }
}
