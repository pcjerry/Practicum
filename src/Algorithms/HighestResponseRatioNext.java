package Algorithms;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import SupportClasses.Process;

public class HighestResponseRatioNext extends Scheduler {

    /**
     * Method for executing the algorithm based on a single input que.
     * @param input The input que, contains all processes
     * @return que of finished processes with information on their processing
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }


        //Verschillende que's aand de hand van waar het proces zich bevindt
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<Process>(10, Comparator.comparingDouble(Process::getResponseRatio));

        Process temp;

        //counter duidt op welk timeslot de processor zich bevindt
        int count = 0;

        //Loop blijft gaan tot alle processen afgerond zijn
        while(finishedProcesses.size()!=input.size()){

            //check of er processen zijn die aan de wachtrij mogen worden toegevoegd
            while(que.peek() != null && que.peek().getArrivaltime()<=count){
                waitingProcesses.add(que.poll());
            }

            for (Process p : waitingProcesses){
                double rr=(p.getServicetimeneeded()+(count-p.getArrivaltime())/(p.getServicetimeneeded()));
                p.setResponseRatio(rr);
            }


            //process uitvoeren als er één klaar is om uitgevoerd te worden, vervolgens de count aanpassen
            if (!waitingProcesses.isEmpty()) {
                //Uit te voeren process uit de wachtrij halen
                temp=waitingProcesses.poll();

                //Parmeters instellen
                temp.setStarttime(count);
                count += temp.getServicetime();
                temp.setEndtime(count);

                //lokale parameters berekenen
                temp.calculate();

                //globale parameters updaten
                waittime += temp.getWaittime();
                normtat += temp.getNormtat();
                tat += temp.getTat();

                //process toevoegen aan de finished lijst
                finishedProcesses.add(temp);


            }else {
                count++;
            }

        }

        waittime = waittime / input.size();
        normtat = normtat / input.size();
        tat = tat / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Glob parameters HRRN ");
        sb.append(waittime + " " + normtat + " " + tat + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    /**
     * Method for executing the algorithm based on a single input que.
     * @param q The input que, contains all processes
     * @param slice The size of a schedueling slice -> no use in HRRN.
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