import java.util.Queue;

/**
 * Created by Daniel Dang on 11/6/2016.
 * This scheduler class is the abstract class of all other scheduling algorithm.
 * This abstract class have basic queue for jobs, 2D array output, timeline.
 * This abstract class also have mandatory methods that is implemented in all other algorithms.
 * Those methods include: print the timeline, print output, run the scheduler and schedule process.
 */
public abstract class Scheduler {
    protected int lengthY;
    protected final int lengthX = 40;

    protected Queue<Job> procQueue;
    protected String output[][];
    protected int timeLine;

    public Scheduler(){};

    abstract void scheduleProc(Job job);

    abstract void runScheduler();

    abstract void printOutput();

    abstract void printTimeline();
}
