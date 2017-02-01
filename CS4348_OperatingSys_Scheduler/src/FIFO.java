import java.util.ArrayList;
import java.util.LinkedList;

/**
 * FIFO algorithm scheduler.
 * This algorithm based on the concept first in first out, which the first job get to the queue will run
 * first, then the rest of the job come in run next. So last job which come in last will finished last, and
 * the algorithm do not take into consideration the time of the job.
 */
public class FIFO extends Scheduler {
    private int countY = 0;     //output control variables
    private int countX = 0;     //output control variables

    //constructor
    public FIFO(){
        this.timeLine = 0;      //set default timeline at 0
        this.procQueue = new LinkedList<>();    //The queue for the jobs
    }

    //This method add a job to the queue
    @Override
    public void scheduleProc(Job proc){
        this.procQueue.add(proc);
    }

    //This method run the scheduler which select the job to run. This method do not execute the job.
    //The job is execute itself while the scheduler's only responsibility is to select the job to run.
    @Override
    public void runScheduler(){
        System.out.println("\nFIFO ALGORITHM\n");       //label which algorithm is running

        setOutputArr();             //initialized output array, 2D array

        //While the queue is not empty, keep select job to run.
        while(!procQueue.isEmpty()){
            //get the first job in the list but not getting rid of the job.
            Job temp = procQueue.peek();

            //If the job has not arrived yet, skip the time slide by 1 unit.
            if (temp.getArriveT() > timeLine){
                output[countY][countX++] = "_" + " | ";
                timeLine++;
            }
            //or if the job have already arrived and ready to go, run the job and get the output from the job
            //to put the output in the output array
            else {
                ArrayList<String> out = temp.run();
                for (int i = 0; i < out.size(); i++) {
                    output[countY][countX] = out.get(i);
                    countX++;
                    timeLine++;
                }
                procQueue.poll();   //job already run, now getting rid of the job
                countY++;           //increment to the next output for next job.
            }
        }
        printOutput();              //print out the output after run all the job.
    }

    //Helper method that initialized the output 2D array. Set all array elements to empty string.
    private void setOutputArr(){
        this.lengthY = procQueue.size();
        this.output = new String[this.lengthY][this.lengthX];
        for (int i = 0; i < lengthY; i++){
            for (int j = 0; j < lengthX; j++){
                output[i][j] = "";
            }
        }
    }

    //This method step through all of the elements inside the 2D array and print them out.
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
            System.out.print("\n");
        }
        printTimeline();        //call print timeline method.
    }

    //Print the timeline with number with given format.
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