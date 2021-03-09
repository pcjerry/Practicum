package sample;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class HighestResponseRatioNext extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> para) {
        Queue<Process> q = new LinkedList<>();
        for (Process p : para) {
            q.add(new Process(p));
        }
        Queue<Process> waitingQueue = new LinkedList<>();
        PriorityQueue<Process> result = new PriorityQueue<>();
        int count = 0;
        Process p;
        while (!q.isEmpty() || !waitingQueue.isEmpty()) {
            while (!q.isEmpty() && q.peek().getArrivaltime() <= count) {
                Process temp = q.poll();
                temp.setStartTime(count);
                waitingQueue.add(temp);
            }
            Process temp = new Process();
            if (!waitingQueue.isEmpty()) {
                double largestTat = waitingQueue.peek().getNormtat();
                for (Process process : waitingQueue) {
                    process.setEndtime(count + process.getServicetime());
                    process.calculate();
                    if (largestTat < process.getNormtat()) {
                        temp = process;
                        largestTat = process.getNormtat();
                    }
                }
                waitingQueue.remove(temp);
                result.add(temp);
                waittime += temp.getWaittime();
                normtat += temp.getNormtat();
                tat += temp.getTat();
            } else {
                temp = q.poll();
                count = temp.getArrivaltime();
                temp.setStartTime(count);
                temp.setEndtime(count + temp.getServicetime());
                temp.calculate();
                result.add(temp);
                waittime += temp.getWaittime();
                normtat += temp.getNormtat();
                tat += temp.getTat();
            }

            count += temp.getServicetime();
        }

        waittime = waittime / para.size();
        normtat = normtat / para.size();
        tat = tat / para.size();

        System.out.println("Highest response ratio next");
        System.out.println("De gemiddelde wachttijd is: " + waittime);
        System.out.println("De gemiddelde genormalisserde omlooptijd is: " + normtat);
        System.out.println("De gemiddelde omlooptijd is: " + tat + "\n");

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
