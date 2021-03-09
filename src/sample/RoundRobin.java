package sample;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoundRobin extends Scheduler {

    int delta;

    public RoundRobin(int i) {
        delta = i;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> para) {
        Queue<Process> q = new LinkedList<>();
        for (Process p : para) {
            q.add(new Process(p));
        }
        PriorityQueue<Process> result = new PriorityQueue<>();
        Queue<Process> waitingQueue = new LinkedList();
        int count = 0;

        while (!q.isEmpty() || !waitingQueue.isEmpty()) {
            Process p;
            if (!q.isEmpty() && waitingQueue.isEmpty()) {
                p = q.poll();
                count = p.getArrivaltime();
                p.setStartTime(count);
            } else {
                p = waitingQueue.poll();
            }

            if (p.getServicetime() <= delta) {
                count += p.getServicetime();
                while (!q.isEmpty() && q.peek().getArrivaltime() <= count) {
                    Process temp = q.poll();
                    temp.setStartTime(count);
                    waitingQueue.add(temp);
                }
                p.setEndtime(count);
                p.calculate();
                result.add(p);
                waittime += p.getWaittime();
                normtat += p.getNormtat();
                tat += p.getTat();
            } else {
                count += delta;
                while (!q.isEmpty() && q.peek().getArrivaltime() <= count) {
                    Process temp = q.poll();
                    temp.setStartTime(count);
                    waitingQueue.add(temp);
                }
                p.setservicetime(p.getServicetime() - delta);
                waitingQueue.add(p);
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
