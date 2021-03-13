package Algorithms;

import java.util.PriorityQueue;
import java.util.Queue;
import Input.Process;

public abstract class Scheduler {

    //Omlooptijd (TurnAroundTime) of tat
    //Genormaliseerde omlooptijd (Normalized TurnAroundTime) of normtat
    //Wachttijd (WaitTime) of waittime

    double tat;
    double normtat;
    double waittime;

    public abstract PriorityQueue<Process> schedule(Queue<Process> q);

    public abstract PriorityQueue<Process> schedule(Queue<Process> q, int slice);

    public abstract double[] getParameters();
}
