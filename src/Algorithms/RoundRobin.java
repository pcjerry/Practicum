package Algorithms;

import SupportClasses.Process;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoundRobin extends Scheduler {

    /**
     * Method for executing the algorithm based on a single input que
     * Used as a default in RoundRobin with slice sice of 1.
     * @param q The input que, contains all processes
     * @return que of finished processes with information on their processing
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return schedule(q,1);
    }

    /**
     * Method for executing the algorithm based on a single input que
     * Preferred method for round robin
     * @param input The input que, contains all processes
     * @param slice The size of a schedueling slice
     * @return Que of finished processes with processing information
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input, int slice) {

        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }

        //Verschillende que's aand de hand van waar het proces zich bevindt
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<>();
        PriorityQueue<Process> currentProcess = new PriorityQueue<>();
        Process temp;

        //counter duidt op welk timeslot de processor zich bevindt
        int count = 0;
        int tempCount = 0;
        boolean swap = false;
        boolean finished = false;
        //Loop blijft gaan tot alle processen afgerond zijn
        while(finishedProcesses.size()!=input.size()){

            tempCount++;
            boolean niewProcessOpServer = false;
            swap= false;

            //bekijk of er een swap nodig is
            if(!currentProcess.isEmpty()){
                temp=currentProcess.peek();
                temp.decreaseServicetime();
                if(temp.getServicetime()==0){
                    swap=true;
                    finished=true;
                }
            }else{
                swap=true;
                niewProcessOpServer=true;
            }

            if(tempCount==slice){
                swap=true;
            }

            //check of er processen zijn die aan de wachtrij mogen worden toegevoegd
            while(que.peek() != null && que.peek().getArrivaltime()<=count)
                waitingProcesses.add(que.poll());


            //haal process van de processor
            if(swap && !currentProcess.isEmpty()){
                temp=currentProcess.poll();
                if(finished){
                    temp.setEndtime(count);
                    temp.calculate();

                    finishedProcesses.add(temp);

                    //globale parameters updaten
                    waittime += temp.getWaittime();
                    normtat += temp.getNormtat();
                    tat += temp.getTat();
                    finished=false;
                }else {
                    waitingProcesses.add(temp);
                }
                niewProcessOpServer=true;

            }

            //nieuw process op de processor plaatsen
            if(swap && niewProcessOpServer &&!waitingProcesses.isEmpty()){
                tempCount=0;
                temp=waitingProcesses.poll();

                if (temp.getStarttime()==0){
                    temp.setStarttime(count);
                }
                currentProcess.add(temp);

            }

            count++;
        }

        return finishedProcesses;
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
