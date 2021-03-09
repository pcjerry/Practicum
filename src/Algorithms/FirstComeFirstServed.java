package Algorithms;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import SupportClasses.Process;

public class FirstComeFirstServed extends Scheduler {

    /**
     * Method for executing the algorithm based on a single input que
     * @param input Que containing the input processes
     * @return A que of finished processes with processing information
     * @throws NullPointerException
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) throws NullPointerException {

        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }

        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();

        //counter duidt op welk timeslot de processor zich bevindt
        int count = 0;
        int wait = 0;
        Process tmp;

        //blijven lopen zolang er processen in de wachtrij staan.
        while (!que.isEmpty()) {

            tmp = que.poll();

            if (count < tmp.getArrivaltime()) {

                //aanpassen counter naar timeslot na volgend process
                count = tmp.getArrivaltime() + tmp.getServicetime();

                //lokale parameters juist zetten
                tmp.setStarttime(tmp.getArrivaltime());
                tmp.setEndtime(count);

                //lokale parameters worden in het proces uitgerekend
                tmp.calculate();
                finishedProcesses.add(tmp);

            } else {
                //counter, start en eind tijd instellen
                tmp.setStarttime(count);
                count += tmp.getServicetime();
                tmp.setEndtime(count);

                //lokale parameters berekenen
                tmp.calculate();
                finishedProcesses.add(tmp);

            }

            //globale parameters updaten
            waittime += tmp.getWaittime();
            normtat += tmp.getNormtat();
            tat += tmp.getTat();

        }

        waittime = waittime / input.size();
        normtat = normtat / input.size();
        tat = tat / input.size();

        StringBuffer sb = new StringBuffer();

        sb.append("Glob parameters FCFS ");
        sb.append(waittime + " " + normtat + " " + tat + " ");

        System.out.println(sb.toString());

        return finishedProcesses;
    }

    /**
     * Method for executing the algorithm based on a input que and a slice size.
     * @param q The input que, contains all processes
     * @param slice Has no use for the first come first serve algorithm
     * @return que of finished processes with information on their processing
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
