import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Daniel Dang on 11/3/2016.
 * This algorithm will execute the shortest remaining time in the job list.
 * This algorithm is the preemptive algorithm which mean that it will interrupt
 * job that is currently running to swap for another job that likely to be finished faster.
 * So this algorithm will detect and check everytime a new job has arrive and will swap between
 * current job and new job depending on the exetion time of both of them which one will finish faster.
 */
public class ShortestRemT extends Scheduler {
    //static variable for the unit of time for round robin algorithm
    private int timeLine;           //time line to keep track of progress units
    private int countY = 0;
    private int countX = 0;
    private Job curJob = null;

    private ArrayList<Job> runList;
    private HashMap<String, Integer> jobOrderHash = new HashMap<>();

    public ShortestRemT(){
        this.timeLine = 0;                      //set default timeline at 0
        this.procQueue = new LinkedList<>();    //The queue for the jobs
        this.runList = new ArrayList<>();
    }

    //This method add the job to the job list in the scheduler
    @Override
    void scheduleProc(Job job) {
        this.procQueue.add(job);
    }

    //This method run the scheduler and select job that have the least execution time left.
    //once there is no more job that arrive, the job that is running will run to completion and then
    //the algorithm will check again for the next job in the queue that have the least remaining execution time.
    @Override
    void runScheduler() {
        System.out.println("\nSHORTEST REMAINING TIME NEXT\n");

        setOutputArr();
        setJobOrderHash();
        while(!procQueue.isEmpty() || !runList.isEmpty()){
            //Pseudocode
            //1: Select a job that have the least remaining time to execute
            //2: Execute a job until new job arrive and calculate remaining time again.
            //3: Execute new job if remaining time of new job less than remaining time of current job.
            //4: Repeat until no more job available
            while (!procQueue.isEmpty() && procQueue.peek().getArriveT() <= timeLine) {
                Job temp = procQueue.poll();
                //check for job in the very beginning
                if (curJob == null && runList.isEmpty()){
                    curJob = temp;
                    runList.add(curJob);
                }
                //check for new coming job and test for shortest remaining time.
                else if(curJob == null && !runList.isEmpty()){
                    Job shrtRemT = getShortestRemTJob();
                    if (temp.getServiceT() >= shrtRemT.getServiceT()){
                        curJob = shrtRemT;
                        runList.add(temp);
                    }
                    else {
                        curJob = temp;
                        runList.add(temp);
                    }
                }
                //check for remaining time.
                else if(curJob.getServiceT() > temp.getServiceT()){
                    curJob = temp;
                    runList.add(curJob);
                }
                else{
                    runList.add(temp);
                }
                if(procQueue.isEmpty())
                    break;
            }

            //check for idle time, when the program do not have any more job to execute
            if(!procQueue.isEmpty() && (procQueue.peek().getArriveT() > timeLine) && runList.isEmpty()){
                output[countY][countX++] = "_" + " | ";
                timeLine++;
            }
            //check for running list of job if empty but the job list still have job available
            if(runList.isEmpty() && !procQueue.isEmpty()) {
                continue;
            }
            //if no available current, get the next shortest remaining time job.
            if (curJob == null){
                curJob = getShortestRemTJob();
            }
            int runT = calculateRunT(curJob);
            selectRun(curJob, runT);
        }
        printOutput();  //print out the output
    }

    //this helper method will save all of the output from the job that is currently run
    private void selectRun(Job job, int runT){
        ArrayList<String> out = job.run(runT);
        for (int i = 0; i < out.size(); i++) {
            countY = jobOrderHash.get(job.getName());
            output[countY][countX] = out.get(i);
            countX++;
            timeLine++;
        }
        //getting rid of job if needed
        if (job.getServiceT() == 0) {
            //getting rid of job only it have been completed
            for (int i = 0; i < runList.size(); i++) {
                if (job.getName().equals(runList.get(i).getName()))
                    runList.remove(i);
            }
            curJob = null;
        }
    }

    //This method run and calculate the time unit that will be allow
    //for the current job to run
    private int calculateRunT(Job curJob){
        int runT = 0;
        Job temp = null;
        if (procQueue.isEmpty())                    //if the process queue is empty, return the current job and run t completion
            return curJob.getServiceT();
        //otherwise, check the remaining time and select process to run
        else {
            temp = procQueue.peek();
            runT = temp.getArriveT() - timeLine;
        }
        return runT;
    }

    //This method get the shortest remaining time job in the running queue
    private Job getShortestRemTJob(){
        Job shortestRemTJ = runList.get(0);
        if (runList.size() <= 1){
            return shortestRemTJ;
        }else {
            for (int i = 1; i < runList.size(); i++) {
                if (shortestRemTJ.getServiceT() > runList.get(i).getServiceT())
                    shortestRemTJ = runList.get(i);
            }
        }
        return shortestRemTJ;
    }

    //this method initialized the hash array with the job's name and its corresponding row
    private void setJobOrderHash(){
        Queue<Job> jobs = new LinkedList<>(procQueue);
        int count = 0;
        while (!jobs.isEmpty()){
            Job temp = jobs.poll();
            jobOrderHash.put(temp.getName(),count++);
        }
    }

    //this method setup the output array and initialized it to emtpy string
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
            System.out.print("\n"+"");
        }
        printTimeline();
    }

    //this method print out the timeline.
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
