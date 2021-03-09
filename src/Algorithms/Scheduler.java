package Algorithms;

import java.util.PriorityQueue;
import java.util.Queue;
import SupportClasses.Process;

public abstract class Scheduler {

    double tat;
    double normtat;
    double waittime;

    public abstract PriorityQueue<Process> schedule(Queue<Process> q);

    /**
     * Schedueling method based on two parameters.
     * @param q Que of input parameters.
     * @param slice The size of a schedueling slice.
     * @return Que of finished processes.
     */
    public abstract PriorityQueue<Process> schedule(Queue<Process> q, int slice);

    /**
     * Method for retrcacting the general parameters
     * @return A list of values (type double) containing the values of the general processor parameters
     */
    public abstract double[] getParameters();
}
