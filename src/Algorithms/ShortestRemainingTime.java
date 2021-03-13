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


        //Verschillende que's aand de hand van waar het proces zich bevindt
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<Process>(10,(a, b)->a.getServicetime()-b.getServicetime());
        PriorityQueue<Process> currentProcess = new PriorityQueue<>();
        Process temp;

        //counter duidt op welk timeslot de processor zich bevindt
        int count = 0;

        //Loop blijft gaan tot alle processen afgerond zijn
        while(finishedProcesses.size()!=input.size()){

            if(!currentProcess.isEmpty()){
                //procces stond al van vorige doorgaan op de processor-> service time needed moet eerst verlaagd worden
                temp=currentProcess.peek();
                temp.decreaseServicetime();
                if (temp.getServicetime()==0){

                    temp=currentProcess.poll();

                    //lokale parameter instellen en andere uitrekenen
                    temp.setEndtime(count);
                    temp.calculate();

                    finishedProcesses.add(temp);

                    //globale parameters updaten
                    waittime += temp.getWaittime();
                    normtat += temp.getNormtat();
                    tat += temp.getTat();
                }
            }

            //check of er processen zijn die aan de wachtrij mogen worden toegevoegd
            while(que.peek() != null && que.peek().getArrivaltime()<=count)
                waitingProcesses.add(que.poll());

          /*  if(waitingProcesses.isEmpty() && currentProcess.isEmpty()){


            }*/
            if(currentProcess.isEmpty() && !waitingProcesses.isEmpty()){
                //Uit te voeren process uit de wachtrij halen
                temp=waitingProcesses.poll();

                //Parmeters instellen
                temp.setStarttime(count);

                //Input.Process op de processor zetten
                currentProcess.add(temp);

            } else if (!currentProcess.isEmpty() && !waitingProcesses.isEmpty()){
                temp=currentProcess.peek();

                //wanneer er processen wachten met lagere servicetime needed -> switchen
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
        waittime = waittime / input.size();
        normtat = normtat / input.size();
        tat = tat / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Glob parameters SRT ");
        sb.append(waittime + " " + normtat + " " + tat + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }


    /**
     * Method for executing the algorithm based on a single input que
     * Not the preferred method for round robin
     * @param q The input que, contains all processes
     * @param slice The size of a schedueling slice -> No use for ShortestRemainingTime.
     * @return Que of finished processes with processing information
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

    /**
     * Method for retrcacting the general parameters
     * @return A list of values (type double) containing the values of the general processor parameters
     */
    @Override
    public double[] getParameters() {
        double [] temp = new double[3];
        temp[0]= waittime;
        temp[1]= normtat;
        temp[2] = tat;
        return temp;
    }
}
