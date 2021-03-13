package Algorithms;

import Input.Process;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoundRobin extends Scheduler{


    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return null;
    }

    //Manier voor uitvoeren van de algoritme op basis van een parameter
    //Input: alle input processen
    //Output: een queue van processen met geprocesseerd gegevens

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> queue, int slice) {

        //Manier voor uitvoeren van de algoritme op basis van twee parameters
        //Input: alle input processen
        //Output: een queue van processen met geprocesseerd gegevens
        //Slice -> 2 4 8

        int inputSize = queue.size();

        Queue<Process> que = new LinkedList<>();

        for (Process p : queue) {
            que.add(new Process(p));
        }

        PriorityQueue<Process> readyQue = new PriorityQueue<>();

        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        Process currentScheduledProcess = null, tempProcess =null;

        //Opstellen parameters
        int counter = 0;
        boolean processFinished = false;
        boolean swap =true;

        while(finishedProcesses.size()!=inputSize){

            //Toevoegen van processen die aangekomen zijn bij de queue
            while(!que.isEmpty() && que.peek().getArrivaltime()<=counter ){
                readyQue.add(que.poll());
            }

            //Voorwaarden voor het wisselen
            if (currentScheduledProcess!=null){
                currentScheduledProcess.decreaseServicetime();
                if(currentScheduledProcess.getServicetime()==0){
                    processFinished = true;
                    swap= true;
                }
            }
            if(counter%slice==0){
                swap = true;
            }

            //Processor opruimen
            if(swap && currentScheduledProcess!=null){
                tempProcess=currentScheduledProcess;
                currentScheduledProcess=null;
                if(processFinished){
                    tempProcess.setEndtime(counter);
                    tempProcess.calculate();

                    finishedProcesses.add(tempProcess);

                    //Globale parameters aanpassen
                    waittime += tempProcess.getWaittime();
                    normtat += tempProcess.getNormtat();
                    tat += tempProcess.getTat();
                    processFinished=false;
                }else {
                    readyQue.add(tempProcess);
                }
                tempProcess = null;
            }

            //Plaatsen process op de processor + counter aanpassen
            if(swap && currentScheduledProcess==null){
                if(!readyQue.isEmpty()){
                    tempProcess=readyQue.poll();
                    if(tempProcess.getStarttime()==-1){
                        tempProcess.setStarttime(counter);
                    }
                    currentScheduledProcess=tempProcess;
                }else if(!que.isEmpty()){
                    tempProcess=que.poll();
                    counter=tempProcess.getArrivaltime();
                    tempProcess.setStarttime(counter);
                    currentScheduledProcess=tempProcess;
                }
                tempProcess=null;
                swap=false;
            }
            counter++;
        }

        tat = tat / queue.size();
        normtat = normtat / queue.size();
        waittime = waittime / queue.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Globale parameters RR: " + slice + " - ");
        sb.append(tat + "---" + normtat + "---" + waittime + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    @Override
    public double[] getParameters() {
        return new double[0];
    }
}
