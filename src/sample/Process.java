package sample;
public class Process implements Cloneable, Comparable {

    private int id;
    private int arrivaltime;
    private int servicetime;
    private int starttime;
    private int endtime;
    private int tat;
    private double normtat;
    private int waittime;
    private int servicetimeneeded;

    public void setServicetimeneeded(int i) {
        servicetimeneeded = i;
    }

    public int getServicetimeNeeded() {
        return this.servicetimeneeded;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public void setTat(int tat) {
        this.tat = tat;
    }

    public void setNormtat(int normtat) {
        this.normtat = normtat;
    }

    public void setWaittime(int waittime) {
        this.waittime = waittime;
    }

    public int getEndtime() {
        return endtime;
    }

    public int getTat() {
        return tat;
    }

    public double getNormtat() {
        return normtat;
    }

    public int getWaittime() {
        return waittime;
    }

    public int getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public int getServicetime() {
        return servicetime;
    }

    public void setServicetime(int servicetime) {
        this.servicetime = servicetime;
    }

    public int getId() {
        return id;
    }

    public Process() {
    }

    public Process(int id, int arrivaltime, int servicetime) {
        this.id = id;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.servicetimeneeded = servicetime;
    }

    public Process(Process p) {
        this.arrivaltime = p.arrivaltime;
        this.servicetime = p.servicetime;
        this.servicetimeneeded = p.servicetimeneeded;
        this.id = p.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setarrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public void setservicetime(int servicetime) {
        this.servicetime = servicetime;
    }

    public int getStartTime() {
        return this.starttime;
    }

    public void verminder(int i) {
        this.servicetime -= i;
    }

    @Override
    public int compareTo(Object o) {
        Process p = (Process) o;
        return this.servicetimeneeded < p.servicetimeneeded ? -1 : 1;
    }

    public void calculate() {
        this.tat = (endtime - arrivaltime);
        this.normtat = (double) this.tat / servicetimeneeded;
        this.waittime = endtime - arrivaltime - servicetimeneeded;
    }

    public void setStartTime(int i) {
        this.starttime = i;
    }

}
