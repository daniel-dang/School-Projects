import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Daniel Dang on 11/3/2016.
 */
public class RoundRobin extends Scheduler{
    //static variable for the unit of time for round robin algorithm
    private int timeUnit = 1;           //1 unit of time for each proc to execute, default value
    private int timeLine = 0;           //time line to keep track of progress units

    private Queue<Job> readyQ;                                                  //readyQ for running jobs
    private HashMap<String, Integer> jobOrderHash = new HashMap<>();            //hash map for output array

    private int countY = 0;     //Row indexes
    private int countX = 0;     //column indexes

    //constructor
    public RoundRobin(){
        procQueue = new LinkedList<>();
        readyQ = new LinkedList<>();
        timeLine = 0;
    }

    //This method set time unit depending the user's choice
    public void setTimeUnit(int time){
        timeUnit = time;
    }

    //This method add job to the job queue
    public void scheduleProc(Job proc){
        this.procQueue.add(proc);
    }

    //This method run the scheduler, which pick a job to run
    //The way this method work is that it pick a job and run depending on the time unit.
    //then put the job back into queue if the job still have not finished.
    //It then get the next job and run. If there are job that arrive, put that job the queue and put
    //the previous job into the queue after the job that just came in.
    public void runScheduler(){
        System.out.println("\nROUND ROBIN ALGORITHM\n");

        setOutputArr();
        setJobOrderHash();
        Job selectedJ = null;
        Job finishedJ = null;
        while(!procQueue.isEmpty() || !readyQ.isEmpty() || finishedJ != null){
            //1: if new job arrive, place new job in the end of the readyQ
            //2: finish up current running job
            //3: add finished current running job to end of readyQ if it still need to run
            //4: get the next job to run.

            //check for arrived job
            if (!procQueue.isEmpty()){
                if(procQueue.peek().getArriveT() <= timeLine) {
                    readyQ.add(procQueue.poll());            //if new process arrive, put it on ready queue
                }
                if(finishedJ != null) {
                    readyQ.add(finishedJ);
                    finishedJ = null;
                }
            }
            //check if the finished job is null, if not add it back into the ready queue
            else if(finishedJ != null) {
                readyQ.add(finishedJ);
                finishedJ = null;
            }
            //check for idle when there is no job available to run
            if(!procQueue.isEmpty() && (procQueue.peek().getArriveT() > timeLine) && readyQ.isEmpty()){
                output[countY][countX++] = "_" + " | ";
                timeLine++;
            }
            //get job from ready queue
            if(!readyQ.isEmpty()) {
                selectedJ = readyQ.poll();
                finishedJ = selectRun(selectedJ);
            }
        }
        printOutput();      //print the output
    }

    //this method set up the hash key and value to the name and row number of each job
    private void setJobOrderHash(){
        Queue<Job> jobs = new LinkedList<>(procQueue);
        int count = 0;
        while (!jobs.isEmpty()){
            Job temp = jobs.poll();
            jobOrderHash.put(temp.getName(),count++);
        }
    }

    //this method will call the job run method and store the output return frorm the
    //job run method to the 2D array
    private Job selectRun(Job job){
        ArrayList<String> out = job.run(timeUnit);
        for (int i = 0; i < out.size(); i++) {
            countY = jobOrderHash.get(job.getName());
            output[countY][countX] = out.get(i);
            countX++;
            timeLine++;
        }

        if (job.getServiceT() != 0)
            return job;
        else
            return null;
    }

    //this job initialized the 2D array to empty string
    private void setOutputArr(){
        this.lengthY = procQueue.size();
        this.output = new String[this.lengthY][this.lengthX];
        for (int i = 0; i < lengthY; i++){
            for (int j = 0; j < lengthX; j++){
                output[i][j] = "";
            }
        }
    }

    //this method print the output
    @Override
    void printOutput(){
        for (int i = 0; i < lengthY; i++){
            for (int j = 0; j < timeLine; j++){
                if (!output[i][j].equals("")) {
                    System.out.print(output[i][j]);
                }
                else{
                    System.out.print("_" + " | ");
                }
            }
            System.out.println();
        }
        printTimeline();
    }

    //This method print the timeline.
    @Override
    void printTimeline(){
        for (int i = 0; i < timeLine; i++){
            if (i < 10)
                System.out.print(i + " | ");
            else
                System.out.print(i + "| ");
        }
        System.out.println("\n");
    }
}
