package Input;

public class Process extends ProcessInput implements Cloneable, Comparable {

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

    // Defaultconstructor
    public Process() {
    }

    // Constructor met parameters
    public Process(int id, int arrivaltime, int servicetimeNeeded) {
        this.id = id;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.servicetimeneeded = servicetime;
        this.responseRatio=0;
        this.priority = 0;

    }

    // Copyconstructor
    public Process(Process p) {
        this.arrivaltime = p.arrivaltime;
        this.servicetime = p.servicetime;
        this.servicetimeneeded = p.servicetimeneeded;
        this.id = p.id;
        this.responseRatio=p.responseRatio;
    }

    // Methode die de TAT, normTAT en Wait time berekent
    public void calculate() {
        this.tat = endtime - arrivaltime;
        this.normtat = (double) this.tat / servicetimeneeded;
        this.waittime = endtime - arrivaltime - servicetimeneeded;
    }

    // Overwrite to string methode
    @Override
    public String toString() {
        return "Input.Process{" +
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

    // Vergelijkmethode
    @Override
    public int compareTo(Object o) {
        Process p = (Process) o;
        return this.servicetimeneeded < p.servicetimeneeded ? -1 : 1;
    }

    // Methode om Service time te verlagen
    public void decreaseServicetime(){servicetime--;}

    // Methode om Wait time te verhogen
    public void increaseWaittime(){waittime++;}

    // Methode om Service time te verlagen met i eenheden
    public void decreaseServicetime(int i) {
        this.servicetime -= i;
    }

    // Methode om Priority value verhogen
    public void increasePriority(){
        priority++;
    }

    // Getter- en settermethode voor ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter- en settermethode voor Arrival Time
    public int getArrivaltime() {
        return arrivaltime;
    }
    public void setArrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    // Getter- en settermethode voor Service Time
    public int getServicetime() {
        return servicetime;
    }
    public void setServicetime(int servicetime) {
        this.servicetime = servicetime;
    }

    // Getter- en settermethode voor Start Time
    public int getStarttime() {
        return starttime;
    }
    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    // Getter- en settermethode voor End Time
    public int getEndtime() {
        return endtime;
    }
    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    // Getter- en settermethode voor TAT attribuut
    public int getTat() {
        return tat;
    }
    public void setTat(int tat) {
        this.tat = tat;
    }

    // Getter- en settermethode voor normTAT attribuut
    public double getNormtat() {
        return normtat;
    }
    public void setNormtat(double normtat) {
        this.normtat = normtat;
    }

    // Getter- en settermethode voor WaitTime attribuut
    public int getWaittime() {
        return waittime;
    }
    public void setWaittime(int waittime) {
        this.waittime = waittime;
    }

    // Getter- en settermethode voor ServiceTimeNeeded attribuut
    public int getServicetimeneeded() {
        return servicetimeneeded;
    }
    public void setServicetimeneeded(int servicetimeneeded) {
        this.servicetimeneeded = servicetimeneeded;
    }

    // Getter- en settermethode voor responseRatio attribuut
    public double getResponseRatio() {
        return responseRatio;
    }
    public void setResponseRatio(double responseRatio) {
        this.responseRatio = responseRatio;
    }

    // Getter- en settermethode voor priority attribuut
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

