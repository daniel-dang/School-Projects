import java.util.ArrayList;

/**
 * Created by Daniel Dang on 11/3/2016.
 * This class file is the class file that hold the job information such as its name, arrival time, execution time
 */
public class Job {
    private String name;        //process name
    private int arriveT;        //process arrival time
    private int finishedT;      //process finished time
    private int serviceT;       //service time in units

    //default constructor
    public Job(){}

    //constructor
    public Job(String name, int arriveT, int serviceT){
        this.name = name;
        this.arriveT = arriveT;
        this.serviceT = serviceT;
        setFinishedT();
    }

    //All getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServiceT() {
        return serviceT;
    }

    public void setServiceT(int serviceT) {
        this.serviceT = serviceT;
    }

    public int getArriveT() {
        return arriveT;
    }

    public void setArriveT(int arriveT) {
        this.arriveT = arriveT;
    }

    public int getFinishedT() {
        return finishedT;
    }

    public void setFinishedT() {
        this.finishedT = this.arriveT + this.serviceT;
    }

    //display method for each process. currently unused
    public String getOutput(){
        return this.name + " | ";
    }

    //This is the run method of the job. This method will run the job to its completion
    //without interruption
    //This method is use for non-preeemtive scheduling algorithm
    public ArrayList<String> run(){
        ArrayList<String> out = new ArrayList<>();
        while(this.serviceT != 0){
            out.add(this.name + " | ");
            serviceT--;
        }
        return out;
    }

    //This is the run method for the job. This method will run the job with given amount of time,
    //then pause the job at that time.
    //This method is use for preemtive scheduling algorithm
    public ArrayList<String> run(int time){
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < time; i++){
            if (this.getServiceT() != 0) {
                out.add(this.name + " | ");
                serviceT--;
            }
        }
        return out;
    }
}
