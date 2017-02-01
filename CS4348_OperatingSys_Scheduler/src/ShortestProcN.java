import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Daniel Dang on 11/3/2016.
 * This algorithm will process the job in the order of the shortest execution time first.
 * The algorithm will check all of the job that coming in and select the one that have the
 * shortest execution time.
 * This algorithm is non preemtive, which mean when a job is selected, it will run until completion
 */
public class ShortestProcN extends Scheduler {
    //static variable for the unit of time for round robin algorithm
    private int timeLine;           //time line to keep track of progress units
    private int countY = 0;
    private int countX = 0;

    private ArrayList<Job> runList;
    private HashMap<String, Integer> jobOrderHash = new HashMap<>();

    public ShortestProcN(){
        this.timeLine = 0;      //set default timeline at 0
        this.procQueue = new LinkedList<>();    //The queue for the jobs
        this.runList = new ArrayList<>();
    }

    //this method add the job into the job list
    @Override
    void scheduleProc(Job job) {
        this.procQueue.add(job);
    }

    //This method run the scheduler which detect and pick the job that have the shortest execution time
    //This method will not switch to another job until it is finished executing.
    @Override
    void runScheduler() {
        System.out.println("\nSHORTEST PROCESS NEXT\n");

        setOutputArr();
        setJobOrderHash();

        while(!procQueue.isEmpty() || !runList.isEmpty()){
            //pseudocode
            //1: check job list, pick the shortest job currently
            //2: run job to finish
            //3: pick next shortest job until all job is run.
            while (!procQueue.isEmpty() && procQueue.peek().getArriveT() <= timeLine) {
                runList.add(procQueue.poll());
                if(procQueue.isEmpty())
                    break;
            }

            //check for idle time when there is no job available to run
            if(!procQueue.isEmpty() && (procQueue.peek().getArriveT() > timeLine) && runList.isEmpty()){
                output[countY][countX++] = "_" + " | ";
                timeLine++;
            }
            //if the running queue is empty but not the job queue skip to the next iteration
            if(runList.isEmpty() && !procQueue.isEmpty()) {
                continue;
            }
            Job shortest = getShortestJob();
            selectRun(shortest);
        }
        printOutput();
    }

    //this job help save all of the output from the job that is selected to run
    private void selectRun(Job job){
        ArrayList<String> out = job.run();
        for (int i = 0; i < out.size(); i++) {
            countY = jobOrderHash.get(job.getName());
            output[countY][countX] = out.get(i);
            countX++;
            timeLine++;
        }
        //getting rid of job. this algorithm is the non preemptive algorithm so once a job is run it will finished without
        //interruption
        for (int i = 0; i < runList.size(); i++){
            if (job.getName().equals(runList.get(i).getName()))
                runList.remove(i);
        }
    }

    //helper method to initialized the hash array with the job name and its corresponding rows
    private void setJobOrderHash(){
        Queue<Job> jobs = new LinkedList<>(procQueue);
        int count = 0;
        while (!jobs.isEmpty()){
            Job temp = jobs.poll();
            jobOrderHash.put(temp.getName(),count++);
        }
    }

    //this method will get and return the job that has the shortest execution time
    private Job getShortestJob(){
        Job shortestJ = runList.get(0);
        if (runList.size() <= 1){
            return shortestJ;
        }else {
            for (int i = 1; i < runList.size(); i++) {
                if (shortestJ.getServiceT() > runList.get(i).getServiceT())
                    shortestJ = runList.get(i);
            }
        }
        return shortestJ;
    }

    //this method initialized the output array to empty string
    private void setOutputArr(){
        this.lengthY = procQueue.size();
        this.output = new String[this.lengthY][this.lengthX];
        for (int i = 0; i < lengthY; i++){
            for (int j = 0; j < lengthX; j++){
                output[i][j] = "";
            }
        }
    }

    //this method print out the output
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
            System.out.println("");
        }
        printTimeline();
    }

    //this method print out the timeline
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
