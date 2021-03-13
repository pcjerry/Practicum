package Algorithms;

import Input.Process;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoundRobinV2 extends Scheduler{
    /**
     * Schedueling method based on one parameter.
     *
     * @param q Que of input parameters.
     * @return Que of finished processes.
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return null;
    }

    /**
     * Schedueling method based on two parameters.
     *
     * @param queue     Que of input parameters.
     * @param slice The size of a schedueling slice.
     * @return Que of finished processes.
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> queue, int slice) {
        int inputSize = queue.size();

        Queue<Process> que = new LinkedList<>();

        for (Process p : queue) {
            que.add(new Process(p));
        }

        PriorityQueue<Process> readyQue = new PriorityQueue<>();
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        Process currentScheduledProcess = null, tempProcess =null;

        int counter = 0;

        boolean processFinished = false;
        boolean swap =true;

        while(finishedProcesses.size()!=inputSize){

            //processen toevoegen die gearriveerd zijn.
            while(!que.isEmpty() && que.peek().getArrivaltime()<=counter ){
                readyQue.add(que.poll());
            }

            //voorwaarden worden gecheckt voor een swap
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

            //processor leeg maken als swap toegestaan is
            if(swap && currentScheduledProcess!=null){
                tempProcess=currentScheduledProcess;
                currentScheduledProcess=null;
                if(processFinished){
                    tempProcess.setEndtime(counter);
                    tempProcess.calculate();

                    finishedProcesses.add(tempProcess);

                    //globale parameters updaten
                    waittime += tempProcess.getWaittime();
                    normtat += tempProcess.getNormtat();
                    tat += tempProcess.getTat();
                    processFinished=false;
                }else {
                    readyQue.add(tempProcess);
                }
                tempProcess=null;
            }

            //process op de processor zetten en counter juist zetten
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

        //System.out.println("while loop finished");

        waittime = waittime / queue.size();
        normtat = normtat / queue.size();
        tat = tat / queue.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Glob parameters RR " + slice + " - ");
        sb.append(waittime + " " + normtat + " " + tat + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    /**
     * Method for retrcacting the general parameters
     *
     * @return A list of values (type double) containing the values of the general processor parameters
     */
    @Override
    public double[] getParameters() {
        return new double[0];
    }
}
