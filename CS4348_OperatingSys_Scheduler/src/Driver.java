import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Daniel Dang on 11/3/2016.
 * This is the main driver that will prompt the user which algorithm it should run.
 * This file will ask the user which algorithm the user wanted to run and then it will
 * call the scheduler class to schedule that algorithm to run a list of tasks.
 */
public class Driver {
    //variable to hold the file name and path and the choice the user enter.
    static String fileName;
    static String filePath;
    static int choice;

    static List<Job> procList = new ArrayList<>();

    //Main method
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        //ask the user for the file name
        System.out.print("Enter the file name: ");
        fileName = input.next();
        filePath = "./res/" + fileName;
        getProc();

        //ask user for algorithm preference
        System.out.println("Select an algorithm preference (enter number 1 - 6): ");
        System.out.println("1: FIFO");
        System.out.println("2: Round Robin");
        System.out.println("3: Shortest Job Next");
        System.out.println("4: Shortest Remaining Time");
        System.out.println("5: Highest Response Rate");
        System.out.println("6: Feed Back");
        System.out.print("Selected: ");
        choice = input.nextInt();

        //switch statement will navigate to the choice the user entered and execute that algorithm
        switch(choice){
            case 1: fifo();
                break;
            case 2: roundRobin();
                break;
            case 3: shortestProcN();
                break;
            case 4: shortestRemT();
                break;
            case 5: highestResR();
                break;
            case 6: feedBack();
                break;
            default:
                System.out.println("No such choice existed");
        }
    }

    /*
    This method will attempt to read the file and extract the process name, arrive time, and execution time
    from a given file.
     */
    public static void getProc(){
        Scanner readF = null;                   //scanner to read file
        try {
            File file = new File(filePath);     //set file
            readF = new Scanner(file);          //read file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //read until no more data
        while (readF.hasNextLine()){
            String[] proc = extractStr(readF.nextLine());
            Job temp = new Job(proc[0], Integer.parseInt(proc[1]), Integer.parseInt(proc[2]));
            procList.add(temp);
        }
    }

    /*
    This is the helper method that extract a line to get the job name, arrive time and execution time out
    of the file.
     */
    private static String[] extractStr(String str){
        String[] temp;
        temp = str.split("\t");
        return temp;
    }

    /*
    All the methods below will get all of the job in the lists and add to the scheduler to run the
    algorithm
     */
    public static void fifo(){
        FIFO fifo = new FIFO();
        for (int i = 0; i < procList.size(); i++){
            fifo.scheduleProc(procList.get(i));
        }
        fifo.runScheduler();
    }

    public static void roundRobin(){
        RoundRobin roundRobin = new RoundRobin();
        for (int i = 0; i < procList.size(); i++){
            roundRobin.scheduleProc(procList.get(i));
        }
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a time unit(value bigger than 0): ");
        int time = in.nextInt();
        roundRobin.setTimeUnit(time);

        roundRobin.runScheduler();
    }

    public static void shortestProcN(){
        ShortestProcN shortestProcN = new ShortestProcN();
        for (int i = 0; i < procList.size(); i++){
            shortestProcN.scheduleProc(procList.get(i));
        }
        shortestProcN.runScheduler();
    }

    public static void shortestRemT(){
        ShortestRemT shortestRemT = new ShortestRemT();
        for (int i = 0; i < procList.size(); i++){
            shortestRemT.scheduleProc(procList.get(i));
        }
        shortestRemT.runScheduler();
    }

    public static void highestResR(){
        HighestResR highestResR = new HighestResR();
        for (int i = 0; i < procList.size(); i++){
            highestResR.scheduleProc(procList.get(i));
        }
        highestResR.runScheduler();
    }

    public static void feedBack(){
        FeedBack feedBack = new FeedBack();
        for (int i = 0; i < procList.size(); i++){
            feedBack.scheduleProc(procList.get(i));
        }
        feedBack.runScheduler();
    }
}
