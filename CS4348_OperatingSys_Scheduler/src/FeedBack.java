import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Daniel Dang on 11/3/2016.
 * This algorithm use the concept of multiple level feedback and
 * utilized three queue to pass process from one queue to the next.
 * The first queue has more priority than the second and third as well as
 * the second to the third queue.
 * A process will get admited and go to the first queue then execute. It will execute until
 * another job arrive and then move to second queue so that the new process can be admited to
 * the first queue for processing. And same for the third queue.
 * Once no new process arrive and all process in the third queue, the process will alternatively executing until
 * no more job is left.
 */
public class FeedBack extends Scheduler {
    //static variable for the unit of time for round robin algorithm
    private final int timeUnit = 1;           //1 unit of time for each proc to execute, default value
    private int timeLine = 0;           //time line to keep track of progress units

    private int countY = 0;
    private int countX = 0;

    private HashMap<String, Integer> jobOrderHash = new HashMap<>();

    //three level queue
    private Queue<Job> firstQ;
    private Queue<Job> secondQ;
    private Queue<Job> thirdQ;

    public FeedBack(){
        procQueue = new LinkedList<>();
        firstQ = new LinkedList<>();
        secondQ = new LinkedList<>();
        thirdQ = new LinkedList<>();
    }

    //This method admit the job into the list.
    @Override
    void scheduleProc(Job job) {
        procQueue.add(job);
    }

    //This method will run the scheduler and detect job that are coming in
    //and move them into different queue.
    @Override
    void runScheduler() {
        System.out.println("\nFEEDBACK ALGORITHM\n");

        setOutputArr();
        setJobOrderHash();

        while(!procQueue.isEmpty() || !firstQ.isEmpty() || !secondQ.isEmpty() || !thirdQ.isEmpty()){
            //Pseudocode
            //1: Admit a job put it in the firstQ and run firstQ
            //2: After run the job in first Q put it in secondQ, and check the firstQ for job arrive and execute it
            //3: If no job in the firstQ, run secondQ and move job to thirdQ,
            //4: Check firstQ again and run it, if no job check secondQ and run it, if no job then check thirdQ and run it.
            //5: If there are no more job in first or second queue, run third queue until complete all job.

            //check new job arrive
            if (!procQueue.isEmpty()){
                if(procQueue.peek().getArriveT() <= timeLine) {
                    firstQ.add(procQueue.poll());            //if new process arrive, put it on ready queue
                }
            }

            //check for no job arrive and all other queue is empty
            if(!procQueue.isEmpty() && (procQueue.peek().getArriveT() > timeLine) && (firstQ.isEmpty() && secondQ.isEmpty() && thirdQ.isEmpty())){
                output[countY][countX++] = "_" + " | ";
                timeLine++;
            }

            //run the queues
            if (!firstQ.isEmpty()){
                Job temp = selectRun(firstQ.poll());
                //stay on queue if there are no more process coming or other queue is waiting to execute
                if (!procQueue.isEmpty() && procQueue.peek().getArriveT() > timeLine && secondQ.isEmpty() && thirdQ.isEmpty()){
                    firstQ.add(temp);
                }
                //else move down a level
                else if (temp != null)
                    secondQ.add(temp);
            }
            else if(firstQ.isEmpty() && !secondQ.isEmpty()){
                Job temp = selectRun(secondQ.poll());
                //stay on queue if there are no more process coming or other queue is waiting to execute
                if (!procQueue.isEmpty() && procQueue.peek().getArriveT() > timeLine && firstQ.isEmpty() && thirdQ.isEmpty()){
                    secondQ.add(temp);
                }
                else if (temp != null)
                    thirdQ.add(temp);
            }
            else if (firstQ.isEmpty() && secondQ.isEmpty() && !thirdQ.isEmpty()){
                Job temp = selectRun(thirdQ.poll());
                //stay on queue if there are no more process coming or other queue is waiting to execute
                if (!procQueue.isEmpty() && procQueue.peek().getArriveT() > timeLine && firstQ.isEmpty() && secondQ.isEmpty()){
                    thirdQ.add(temp);
                }
                else if (temp != null)
                    thirdQ.add(temp);
            }
        }
        printOutput();      //print the output
    }

    //This method is the helper method that save all of the output once the job is run. and getting rid of job as
    //needed as the job have no more execution time.
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

    //helper method to initialized the hash array with the name of the job and its corresponding rows
    private void setJobOrderHash(){
        Queue<Job> jobs = new LinkedList<>(procQueue);
        int count = 0;
        while (!jobs.isEmpty()){
            Job temp = jobs.poll();
            jobOrderHash.put(temp.getName(),count++);
        }
    }

    //helper method to initialized the output array
    private void setOutputArr(){
        this.lengthY = procQueue.size();
        this.output = new String[this.lengthY][this.lengthX];
        for (int i = 0; i < lengthY; i++){
            for (int j = 0; j < lengthX; j++){
                output[i][j] = "";
            }
        }
    }

    //print all of the output
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
            System.out.println(""+""+"");
        }
        printTimeline();
    }

    //print the timeline
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
