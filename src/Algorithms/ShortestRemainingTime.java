package Algorithms;

import Input.Process;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestRemainingTime extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }

        //Opstellen van queue van processen afhankelijk van positie
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<Process>(10,(a, b)->a.getServicetime()-b.getServicetime());
        PriorityQueue<Process> currentProcess = new PriorityQueue<>();
        Process temp;

        //Counter gaat hier aanduiden waar de processor zich bevindt (timeslot)
        int count = 0;

        //Voorwaarde: loop totdat alle processen afgerond zijn met processeren
        while(finishedProcesses.size()!=input.size()){

            if(!currentProcess.isEmpty()){
                temp=currentProcess.peek();
                temp.decreaseServicetime();
                if (temp.getServicetime()==0){

                    temp=currentProcess.poll();

                    //Parameters opstellen
                    temp.setEndtime(count);

                    //Parameters berekenen (Lokaal)
                    temp.calculate();

                    finishedProcesses.add(temp);

                    //Parameters aanpassen (Globaal)
                    waittime += temp.getWaittime();
                    normtat += temp.getNormtat();
                    tat += temp.getTat();
                }
            }

            //Processen vinden die nog aan de queue kunnen toegevoegd worden
            while(que.peek() != null && que.peek().getArrivaltime()<=count)
                waitingProcesses.add(que.poll());

            if(currentProcess.isEmpty() && !waitingProcesses.isEmpty()){
                //Uit te voeren process uit de wachtrij halen
                temp=waitingProcesses.poll();

                //Parameters opstellen
                temp.setStarttime(count);

                //Process op de processor plaatsen
                currentProcess.add(temp);

            } else if (!currentProcess.isEmpty() && !waitingProcesses.isEmpty()){
                temp=currentProcess.peek();

                //Processen die weinig bedieningstijd nodig hebben, wisselen hier als eerst
                if(temp.getServicetimeneeded()>waitingProcesses.peek().getServicetimeneeded()){
                    temp=currentProcess.poll();
                    Process p=waitingProcesses.peek();
                    if(p.getStarttime()==0)
                        p.setStarttime(count);

                    currentProcess.add(waitingProcesses.poll());
                    waitingProcesses.add(temp);
                }
            }
            count++;
        }

        tat = tat / input.size();
        normtat = normtat / input.size();
        waittime = waittime / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Globale parameters SRT: ");
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
