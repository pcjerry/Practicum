package SupportClasses;

public class Process extends ProcessFactory implements Cloneable, Comparable {


    private int id;
    private int arrivaltime;
    private int servicetime;
    private int starttime;
    private int endtime;
    private int tat;
    private double normtat;
    private int waittime;
    private int servicetimeneeded;
    private double responseRatio;
    private int priority; //this is for multi level feedabck

    /**
     * DefaultConstructor.
     */
    public Process() {
    }

    /**
     * Constructor with parameters.
     * @param id The process id.
     * @param arrivaltime The arrival time.
     * @param servicetimeNeeded The service time the process needs.
     */
    public Process(int id, int arrivaltime, int servicetimeNeeded) {
        this.id = id;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.servicetimeneeded = servicetime;
        this.responseRatio=0;
        this.priority = 0;

    }

    /**
     * Copy constructor.
     * @param p The input process to copy.
     */
    public Process(Process p) {
        this.arrivaltime = p.arrivaltime;
        this.servicetime = p.servicetime;
        this.servicetimeneeded = p.servicetimeneeded;
        this.id = p.id;
        this.responseRatio=p.responseRatio;
    }

    /**
     * Method for calculating the processor parameters after it's finished.
     */
    public void calculate() {
        this.tat = endtime - arrivaltime;
        this.normtat = (double) this.tat / servicetimeneeded;
        this.waittime = endtime - arrivaltime - servicetimeneeded;
    }

    /**
     * overWrite to string method.
     * @return String containing the parameters of the process.
     */
    @Override
    public String toString() {
        return "SupportClasses.Process{" +
                "id=" + id +
                ", arrivaltime=" + arrivaltime +
                ", servicetime=" + servicetime +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", tat=" + tat +
                ", normtat=" + normtat +
                ", waittime=" + waittime +
                ", servicetimeneeded=" + servicetimeneeded +
                '}';
    }

    /**
     * Compare method
     * @param o The process to compare to.
     * @return Integer value based on comparison.
     */
    @Override
    public int compareTo(Object o) {
        Process p = (Process) o;
        return this.servicetimeneeded < p.servicetimeneeded ? -1 : 1;
    }

    /**
     * Decrease the service time of the process.
     */
    public void decreaseServicetime(){servicetime--;}

    /**
     * Increase the wait time of the process.
     */
    public void increaseWaittime(){waittime++;}

    /**
     * Decrease The service time of the process with a given amount.
     * @param i The decrementing value.
     */
    public void decreaseServicetime(int i) {
        this.servicetime -= i;
    }

    /**
     * Increase the priority value.
     */
    public void increasePriority(){
        priority++;
    }

    /**
     * Getter for the id attribute.
     * @return The value of id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the id attribute.
     * @param id New value of the id attribute.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the arrivaltime attribute.
     * @return The value of arrivaltime.
     */
    public int getArrivaltime() {
        return arrivaltime;
    }

    /**
     * Setter for the ArrivalTime attribute.
     * @param arrivaltime New value of the ArrivalTime attribute.
     */
    public void setArrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    /**
     * Getter for the service time attribute.
     * @return The value of service time.
     */
    public int getServicetime() {
        return servicetime;
    }

    /**
     * Setter for the ServiceTime attribute.
     * @param servicetime New value of the ServiceTime attribute.
     */
    public void setServicetime(int servicetime) {
        this.servicetime = servicetime;
    }

    /**
     * Getter for the start time attribute.
     * @return The value of start time.
     */
    public int getStarttime() {
        return starttime;
    }

    /**
     * Setter for the StartTime attribute.
     * @param starttime New value of the StartTime attribute.
     */
    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    /**
     * Getter for the end time attribute.
     * @return The value of end time.
     */
    public int getEndtime() {
        return endtime;
    }

    /**
     * Setter for the EndTime attribute.
     * @param endtime New value of the EndTime attribute.
     */
    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    /**
     * Getter for the TAT attribute.
     * @return The value of TAT.
     */
    public int getTat() {
        return tat;
    }

    /**
     * Setter for the TAT attribute.
     * @param tat New value of the TAT attribute.
     */
    public void setTat(int tat) {
        this.tat = tat;
    }

    /**
     * Getter for the NormTAT attribute.
     * @return The value of normTat.
     */
    public double getNormtat() {
        return normtat;
    }

    /**
     * Setter for the normTAT attribute.
     * @param normtat New value of the normTAT attribute.
     */
    public void setNormtat(double normtat) {
        this.normtat = normtat;
    }

    /**
     * Getter for the WaitTime attribute.
     * @return The value of WaitTime.
     */
    public int getWaittime() {
        return waittime;
    }

    /**
     * Setter for the WaitTime attribute.
     * @param waittime New value of the WaitTime attribute.
     */
    public void setWaittime(int waittime) {
        this.waittime = waittime;
    }

    /**
     * Getter for the serviceTmeNeeded attribute.
     * @return The value of serviceTmeNeeded.
     */
    public int getServicetimeneeded() {
        return servicetimeneeded;
    }

    /**
     * Setter for the serviceTimeNeeded attribute.
     * @param servicetimeneeded New value of the serviceTimeNeeded attribute.
     */
    public void setServicetimeneeded(int servicetimeneeded) {
        this.servicetimeneeded = servicetimeneeded;
    }

    /**
     * Getter for the ResponseRatio attribute.
     * @return The value of ResponseRatio.
     */
    public double getResponseRatio() {
        return responseRatio;
    }

    /**
     * Setter for the responseRatio attribute.
     * @param responseRatio New value of the responseRatio attribute.
     */
    public void setResponseRatio(double responseRatio) {
        this.responseRatio = responseRatio;
    }

    /**
     * Getter for the Priority attribute.
     * @return The value of Priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Setter for the priority attribute.
     * @param priority New value of the priority attribute.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

