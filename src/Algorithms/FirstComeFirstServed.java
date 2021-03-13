package Algorithms;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import Input.Process;

public class FirstComeFirstServed extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) throws NullPointerException {

        //Jerry Xiong 11/03/2021
        //Manier voor uitvoeren van de algoritme op basis van een enkele input queue
        //Input: alle input processen
        //Output: een queue van processen met geprocesseerd gegevens

        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }

        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();

        //Counter gaat hier aanduiden waar de processor zich bevindt (timeslot)
        int count = 0;
        int wait = 0;
        Process tmp;

        //Voorwaarde: loop zolang de queue niet leeg is
        while (!que.isEmpty()) {

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
            waittime += tmp.getWaittime();
            normtat += tmp.getNormtat();
            tat += tmp.getTat();

        }

        waittime = waittime / input.size();
        normtat = normtat / input.size();
        tat = tat / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Glob parameters FCFS ");
        sb.append(waittime + " " + normtat + " " + tat + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

    //Manier voor uitvoeren van de algoritme op basis van een enkele queue en een slice grootte
    //Slice is niet gebruikt bij FCFS
    //Input: alle processen
    //Output: een queue van processen met geprocesseerd gegevens

    @Override
    public double[] getParameters() {

        //Manier voor oproepen van globale parameters
        //Output: gemiddelde wachttijd, gemiddelde genormaliseerde omlooptijd, gemiddelde omlooptijd

        double [] temp = new double[3];
        temp[0]= waittime;
        temp[1]= normtat;
        temp[2] = tat;
        return temp;
    }
    
    
}
