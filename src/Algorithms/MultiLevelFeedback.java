package Algorithms;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import Input.Process;

public class MultiLevelFeedback extends Scheduler {

    /**
     * Method for executing the algorithm based on a single input que
     * Preferred method for MultiLevelFeedback
     * @param input The input que, contains all processes
     * @param slice The size of a schedueling slice -> the second que has a slice size multiplied by two.
     * @return Que of finished processes with processing information
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input, int slice) {
        int slice1= slice;
        int slice2= slice1*2;

        Queue<Process> inputQue = new LinkedList<>();

        for (Process p : input) {
            inputQue.add(new Process(p));
        }

        //Verschillende que's aand de hand van waar het proces zich bevindt
        PriorityQueue<Process> waitingQue0 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue1 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue2 = new PriorityQueue<>();
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> currentProcess = new PriorityQueue<>();

        Process temp;

        //counter duidt op welk timeslot de processor zich bevindt
        int count = 0;
        int currentSlice=0;
        int tempCounter=0;
        boolean swap=false;

        //Loop blijft gaan tot alle processen afgerond zijn
        while(finishedProcesses.size()!=input.size()){

            //Moest er al een process op de processor staan, service time verminderen.
            if(!currentProcess.isEmpty()){
                currentProcess.peek().decreaseServicetime();
                tempCounter++;
            }

            //check of het process afgelopen is/of het process van de server moet
            if(currentSlice==tempCounter)
                swap=true;
            else if(!currentProcess.isEmpty()){
                if(currentProcess.peek().getServicetime()==0)
                    swap=true;

            }

            //check of er processen zijn die aan de wachtrij mogen worden toegevoegd
            while(inputQue.peek() != null && inputQue.peek().getArrivaltime()<=count)
                waitingQue0.add(inputQue.poll());




            if(swap) {
                //Als er een process in current zit
                if (!currentProcess.isEmpty()) {
                    temp = currentProcess.poll();
                    if (temp.getServicetime() == 0) {
                        temp.setEndtime(count);
                        temp.calculate();
                        currentSlice=0;
                        tempCounter = 0;
                        finishedProcesses.add(temp);

                        //globale parameters updaten
                        waittime += temp.getWaittime();
                        normtat += temp.getNormtat();
                        tat += temp.getTat();


                    } else {
                        temp.increasePriority();
                        int tempPrior = temp.getPriority();
                        if (tempPrior == 1)
                            waitingQue1.add(temp);
                         else if (tempPrior == 2)
                            waitingQue2.add(temp);

                    }

                }
                else{
                    //Het juiste process processor tijd geven indien er geen actief process is
                    if (!waitingQue0.isEmpty()) {
                        temp = waitingQue0.poll();
                        temp.setStarttime(count);
                        temp.setPriority(0);
                        currentSlice = slice1;
                        tempCounter = 0;
                        currentProcess.add(temp);
                    } else if (!waitingQue1.isEmpty()) {
                        temp = waitingQue1.poll();
                        //temp.setStarttime(count);
                        temp.setPriority(1);
                        currentSlice = slice2;
                        tempCounter = 0;
                        currentProcess.add(temp);
                    } else if (!waitingQue2.isEmpty()) {
                        temp = waitingQue2.poll();
                        //temp.setStarttime(count);
                        temp.setPriority(2);
                        currentSlice = -1;
                        tempCounter = 0;
                        currentProcess.add(temp);
                    }
                }
                swap = false;
            }
            count++;
        }

        waittime = waittime / input.size();
        normtat = normtat / input.size();
        tat = tat / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Glob parameters MLF ");
        sb.append(waittime + " " + normtat + " " + tat + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    /**
     * Method for executing the algorithm based on a single input que
     * Used as a default in MultiLevelFeedback with slice size of 1.
     * @param q The input que, contains all processes
     * @return que of finished processes with information on their processing
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return schedule(q,1);
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
