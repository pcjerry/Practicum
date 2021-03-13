package Algorithms;

import Input.Process;

import java.util.*;

public class ShortestJobFirst extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }

        //Opstellen van queue van processen afhankelijk van positie
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<Process>(10,(a, b)->a.getServicetime()-b.getServicetime());

        Process temp;

        //Counter gaat hier aanduiden waar de processor zich bevindt (timeslot)
        int count = 0;

        //Voorwaarde: loop totdat alle processen afgerond zijn met processeren
        while(finishedProcesses.size()!=input.size()){

            //Processen vinden die nog aan de queue kunnen toegevoegd worden
            while(que.peek() != null && que.peek().getArrivaltime()<=count)
                waitingProcesses.add(que.poll());

            //Uitvoeren van process wanneer er 1 gereed is om uit te voeren + count aanpassen
            if (!waitingProcesses.isEmpty()) {

                //Uit te voeren process uit de wachtrij halen
                temp=waitingProcesses.poll();

                //Parameters opstellen
                temp.setStarttime(count);
                count += temp.getServicetime();
                temp.setEndtime(count);

                //Parameters berekenen (Lokaal)
                temp.calculate();

                //Geprocesseerd processen aan de FinishedQueue toevoegen
                finishedProcesses.add(temp);

                //Parameters aanpassen (Globaal)
                tat += temp.getTat();
                normtat += temp.getNormtat();
                waittime += temp.getWaittime();

            }else {
                count++;
            }

        }

        tat = tat / input.size();
        normtat = normtat / input.size();
        waittime = waittime / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Globale parameters SJF: ");
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
