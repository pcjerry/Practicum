package sample;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class FirstComeFirstServed extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> para) throws NullPointerException {
        Queue<Process> q = new LinkedList<>();
        for (Process p : para) {
            q.add(new Process(p));
        }
        PriorityQueue<Process> result = new PriorityQueue();
        int count = 0;
        int wait = 0;
        Process temp;
        while (!q.isEmpty()) {
            temp = q.poll();
            if (count < temp.getArrivaltime()) {
                count = temp.getArrivaltime() + temp.getServicetime();
                temp.setStartTime(temp.getArrivaltime());
                temp.setEndtime(count);
                temp.calculate();
                result.add(temp);
                waittime += temp.getWaittime();
                normtat += temp.getNormtat();
                tat += temp.getTat();
            } else {
                temp.setStartTime(count);
                count += temp.getServicetime();
                temp.setEndtime(count);
                temp.calculate();
                result.add(temp);
                waittime += temp.getWaittime();
                normtat += temp.getNormtat();
                tat += temp.getTat();
            }

        }

        waittime = waittime / para.size();
        normtat = normtat / para.size();
        tat = tat / para.size();

        return result;
    }

    @Override
    public double[] getParameters() {
        double [] temp = new double[3];
        temp[0]= waittime;
        temp[1]= normtat;
        temp[2] = tat;
        return temp;
    }


}
