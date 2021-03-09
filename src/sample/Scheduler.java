package sample;

import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Scheduler {

    double tat;
    double normtat;
    double waittime;

    public abstract PriorityQueue<Process> schedule(Queue<Process> q);
    public abstract double[] getParameters();
}
