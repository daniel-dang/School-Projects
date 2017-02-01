import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Daniel Dang on 11/3/2016.
 * This class is the class of the algorithm that run the process in the order of highest response rate.
 * Response rate is calculated by taking the wait time add execution time and divide by execution time.
 */
public class HighestResR extends Scheduler {
    //static variable for the unit of time for round robin algorithm
    private int timeLine = 0;           //time line to keep track of progress units
    private int countY = 0;             //Rows variable
    private int countX = 0;             //column variables

    private ArrayList<Job> runList;     //array to keep track of the running job
    private HashMap<String, Integer> jobOrderHash = new HashMap<>();    //hash map to get the row number according to job name

    //constructor
    public HighestResR(){
        this.timeLine = 0;                      //set default timeline at 0
        this.procQueue = new LinkedList<>();    //The queue for the jobs
        this.runList = new ArrayList<>();       //Instance of running queue
    }

    //method to add process to queue
    @Override
    void scheduleProc(Job job) {
        this.procQueue.add(job);
    }

    //This method run the algorithm
    @Override
    void runScheduler() {
        System.out.println("\nHIGHEST RESPONSE RATIO NEXT\n");

        setOutputArr();     //initialized the output array
        setJobOrderHash();  //put the job name and their row number in the hash

        //Keep looping when either the process list is not empty or the process running list is not empty
        while(!procQueue.isEmpty() || !runList.isEmpty()){
            //pseudocode
            //1: search job list, pick job has highest response rate
            //2: run job to finished
            //3: pick another job has highest response rate

            //check for next job arrive
            while (!procQueue.isEmpty() && procQueue.peek().getArriveT() <= timeLine) {
                runList.add(procQueue.poll());
                if(procQueue.isEmpty())
                    break;
            }
            //check for idle time that no job is available to run
            if(!procQueue.isEmpty() && (procQueue.peek().getArriveT() > timeLine) && runList.isEmpty()){
                output[countY][countX++] = "_" + " | ";
                timeLine++;
            }

            //if running process list is empty but not the process's list, continue to next iteration
            if(runList.isEmpty() && !procQueue.isEmpty()) {
                continue;
            }
            Job highestRes = getHighestResJob();        //get the highest response rate job
            selectRun(highestRes);                      //run the job
        }
        printOutput();      //print the output
    }

    //This helper method help the scheduler get the output once the job is run
    private void selectRun(Job job){
        ArrayList<String> out = job.run();
        for (int i = 0; i < out.size(); i++) {
            countY = jobOrderHash.get(job.getName());
            output[countY][countX] = out.get(i);
            countX++;
            timeLine++;
        }
        //getting rid of job, since this algorithm is non-preemtive, once a job run, it will run to completion
        //So getting rid of job everytime a job is run.
        for (int i = 0; i < runList.size(); i++){
            if (job.getName().equals(runList.get(i).getName()))
                runList.remove(i);
        }
    }

    //This method will calculate the highest response rate of job among all the job and return the one that
    //have the highest response rate.
    private Job getHighestResJob(){
        Job highestResJ = runList.get(0);                       //Initialized highest reponse job at index 0
        int waitT = timeLine - runList.get(0).getArriveT();     //calculate wait time
        int exeT = runList.get(0).getServiceT();                //calculate execution time
        double HRRN = (waitT + exeT) / exeT;                    //claculate reponses rate

        //If the running list of job only have 1 job, return that job
        if (runList.size() <= 1) {
            return highestResJ;
        }
        //otherwise if there are other job, run through the entire array and compare to see which job has
        //highest response rate.
        else {
            for (int i = 1; i < runList.size(); i++) {
                int temp_waitT = timeLine - runList.get(i).getArriveT();
                int temp_exeT = runList.get(i).getServiceT();
                double Temp_HRRN = (temp_waitT + temp_exeT) / temp_exeT;
                if (Temp_HRRN > HRRN) {
                    HRRN = Temp_HRRN;
                    highestResJ = runList.get(i);
                }
            }
        }
        return highestResJ;
    }

    //This helper method initialized the hash array with the job name and their corresponding row number
    private void setJobOrderHash(){
        Queue<Job> jobs = new LinkedList<>(procQueue);
        int count = 0;
        while (!jobs.isEmpty()){
            Job temp = jobs.poll();
            jobOrderHash.put(temp.getName(),count++);
        }
    }

    //This helper method initialize the output array to empty string.
    private void setOutputArr(){
        this.lengthY = procQueue.size();
        this.output = new String[this.lengthY][this.lengthX];
        for (int i = 0; i < lengthY; i++){
            for (int j = 0; j < lengthX; j++){
                output[i][j] = "";
            }
        }
    }

    //This method print the output
    @Override
    void printOutput() {
        for (int i = 0; i < lengthY; i++){
            for (int j = 0; j < timeLine; j++){
                if (!output[i][j].equals("")) {
                    System.out.print(output[i][j]);
                }
                else{
                    System.out.print("_" + " | ");
                }
            }
            System.out.println("" + "");
        }
        printTimeline();
    }

    //This method print the timeline
    @Override
    void printTimeline() {
        for (int i = 0; i < timeLine; i++){
            if (i < 10)
                System.out.print(i + " | ");
            else
                System.out.print(i + "| ");
        }
        System.out.println("\n");
    }
}
