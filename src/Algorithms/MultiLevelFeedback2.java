package Algorithms;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import Input.Process;

public class MultiLevelFeedback2 extends Scheduler {

    //Slice 1: x (1) Constant
    //Slice 2: x^2 (2) Exponentieel

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input, int slice) {
        int slice1= slice;
        int slice2= (int) Math.pow(slice1, 2);

        //Opstellen queue van input
        Queue<Process> inputQue = new LinkedList<>();

        for (Process p : input) {
            inputQue.add(new Process(p));
        }

        //Opstellen van queue van processen afhankelijk van positie, 5 queues
        PriorityQueue<Process> waitingQue0 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue1 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue2 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue3 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue4 = new PriorityQueue<>();

        //Opstellen van queue van output
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> currentProcess = new PriorityQueue<>();

        Process temp;

        //Counter gaat hier aanduiden waar de processor zich bevindt (timeslot)
        int count = 0;
        int currentSlice=0;
        int tempCounter=0;
        boolean swap=false;

        //Voorwaarde: loop totdat alle processen afgerond zijn met processeren
        while(finishedProcesses.size()!=input.size()){

            //Bedieningstijd verminderen als er al een process is op de processor
            if(!currentProcess.isEmpty()){
                currentProcess.peek().decreaseServicetime();
                tempCounter++;
            }

            //Bekijken voor de proccessen die gedaan zijn met processeren + afhalen
            if(currentSlice==tempCounter)
                swap=true;
            else if(!currentProcess.isEmpty()){
                if(currentProcess.peek().getServicetime()==0)
                    swap=true;
            }

            //Processen vinden die nog aan de queue kunnen toegevoegd worden
            while(inputQue.peek() != null && inputQue.peek().getArrivaltime()<=count)
                waitingQue0.add(inputQue.poll());

            if(swap) {
                //Huidig process
                if (!currentProcess.isEmpty()) {
                    temp = currentProcess.poll();
                    if (temp.getServicetime() == 0) {
                        temp.setEndtime(count);
                        temp.calculate();
                        currentSlice=0;
                        tempCounter = 0;
                        finishedProcesses.add(temp);

                        //Globale parameters aanpassen
                        tat += temp.getTat();
                        normtat += temp.getNormtat();
                        waittime += temp.getWaittime();

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
                    //Tijd geven aan bepaalde process wanneer er geen actief process bezig is in queue
                    if (!waitingQue0.isEmpty()) {
                        temp = waitingQue0.poll();
                        temp.setStarttime(count);
                        temp.setPriority(0);
                        currentSlice = slice1;
                        tempCounter = 0;
                        currentProcess.add(temp);

                    } else if (!waitingQue1.isEmpty()) {
                        temp = waitingQue1.poll();
                        temp.setPriority(1);
                        currentSlice = slice2;
                        tempCounter = 0;
                        currentProcess.add(temp);

                    } else if (!waitingQue2.isEmpty()) {
                        temp = waitingQue2.poll();
                        temp.setPriority(2);
                        currentSlice = -1;
                        tempCounter = 0;
                        currentProcess.add(temp);

                    } else if (!waitingQue3.isEmpty()) {
                        temp = waitingQue3.poll();
                        temp.setPriority(3);
                        currentSlice = -2;
                        tempCounter = 0;
                        currentProcess.add(temp);

                    } else if (!waitingQue4.isEmpty()) {
                        temp = waitingQue4.poll();
                        temp.setPriority(4);
                        currentSlice = -3;
                        tempCounter = 0;
                        currentProcess.add(temp);
                    }
                }
                swap = false;
            }
            count++;
        }

        tat = tat / input.size();
        normtat = normtat / input.size();
        waittime = waittime / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Globale parameters MLF.E: ");
        sb.append(tat + "---" + normtat + "---" + waittime + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return schedule(q,1);
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
