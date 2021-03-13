package Algorithms;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import Input.Process;

public class HighestResponseRatioNext extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> que = new LinkedList<>();

        //Opstellen queue van input
        for (Process p : input) {
            que.add(new Process(p));
        }

        //Opstellen van queue van output
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();

        //Opstellen van queue van processen afhankelijk van positie
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<Process>(10, Comparator.comparingDouble(Process::getResponseRatio));

        //Counter gaat hier aanduiden waar de processor zich bevindt (timeslot)
        int count = 0;
        Process temp;

        //Voorwaarde: loop totdat alle processen afgerond zijn met processeren
        while(finishedProcesses.size() != input.size()){

            //Processen vinden die nog aan de queue kunnen toegevoegd worden
            while(que.peek() != null && que.peek().getArrivaltime()<=count){
                waitingProcesses.add(que.poll());
            }

            for (Process p : waitingProcesses){
                double rr= (p.getServicetimeneeded() + (count-p.getArrivaltime())/(p.getServicetimeneeded()));
                p.setResponseRatio(rr);
            }

            //Uitvoeren van process wanneer er 1 gereed is om uit te voeren + count aanpassen
            if (!waitingProcesses.isEmpty()) {

                //Process die uitgevoerd moet worden uit de queue halen
                temp = waitingProcesses.poll();

                //Definieren starttijd, bedieningstijd, stoptijd
                temp.setStarttime(count);
                count += temp.getServicetime();
                temp.setEndtime(count);

                //Parameters berekenen (Lokaal)
                temp.calculate();

                //Parameters aanpassen (Globaal)
                tat += temp.getTat();
                normtat += temp.getNormtat();
                waittime += temp.getWaittime();

                //Geprocesseerd processen aan de FinishedQueue toevoegen
                finishedProcesses.add(temp);

            }else {
                count++;
            }

        }

        tat = tat / input.size();
        normtat = normtat / input.size();
        waittime = waittime / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Globale parameters HRRN: ");
        sb.append(tat + "---" + normtat + "---" + waittime + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

    @Override
    public double[] getParameters() {
        double [] temp = new double[3];
        temp[0]= tat;
        temp[1]= normtat;
        temp[2] = waittime;
        return temp;
    }
}