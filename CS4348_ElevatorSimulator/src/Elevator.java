import java.util.*;
import java.util.concurrent.Semaphore;

public class Elevator implements Runnable {
    //global constant
    private static final int MAX_PEOPLE = 49;                       //maximum number of people, currently set at 49
    private static final int MAX_LOAD = 7;                          //number of client that the elevator can hold, currently set at 7
    private static int clientCount = 0;                             //client counter for all people who want to ride the elevator, initial 0, max 49.
    private static int currentFloor = 1;                            //set current floor = 1, this value will change depending on the floor the clients want to go
    private static int headCount = 0;                               //variable to count the number of people in the elevator
    private static int transported = 0;                             //number of people the elevator have carried

    //semaphores
    static public Semaphore vacancy = new Semaphore(0, true);       //number of vacancy, free space, in the elevator, initially 0, will be 7 once the elevator is at floor 1
    static public Semaphore door = new Semaphore(0, true);          //set the semaphore to signal door open or close, initial at 0 = door close
    static public Semaphore floor1 = new Semaphore(1, true);        //set the signal to signal the elevator that it is at floor 1, initial at 1 = currently at floor 1
    static public Semaphore takeOff = new Semaphore(0, true);       //set the number of require head count before take off the elevator from floor 1, initial at 0, elevator cannot take off

    //These two semaphores is the main semaphore that will check each other when the client entered the elevator
    //and when they leave, these two semaphores make sure that appropriate amount of people enter the floor to take off
    //and also the appropriate amount of people to let them exit the elevator on the correct floor.
    static public Semaphore myFloor[] = new Semaphore[11];          //set the array of the semaphore to 11, since the floor the elevator need to go is from 2 to 10, index 0 and 1 never used, initial value is 0
    static public Semaphore exited[] = new Semaphore[11];           //signal if the passenger have exit the elevator, initial value = 0 for every index.

    //This array stores the floor number that all of the client has entered.
    //the data store in this array will mimic counting sort to go from the lowest floor to the highest floor.
    static private int floorQueue[] = new int[11];

    //this constructor initialized all arrays in the class
    public Elevator() {
        //initialized the myFloor semaphore array to 0 for each index
        for (int i = 0; i < myFloor.length; i++){
            myFloor[i] = new Semaphore(0, true);
        }
        //initialized the floorQueue to 0
        for (int i = 0; i < floorQueue.length; i++){
            floorQueue[i] = 0;
        }
        //initialized exited semaphore
        for (int i = 0; i < exited.length; i++){
            exited[i] = new Semaphore(0, true);
        }
        //optional welcome message
        welcome();
    }

    //Print welcome statement before elevator operate
    public static void welcome(){
        System.out.println("----- WELCOME - PLEASE ENTER THE ELEVATOR - FLOOR 1 -----");
    }

    //semaphore wait acquire available semaphore if any is available.
    public static void semWait(Semaphore sem){
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //overloading method of the method above, this method will acquire the amount of fixed permits
    public static void semWait(Semaphore sem, int permits){
        try {
            sem.acquire(permits);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //semaphore signal release the semaphore value
    public static void semSignal(Semaphore sem){
        sem.release();
    }

    //overloading method of the method above, this method will release a fixed amount of permits
    public static void semSignal(Semaphore sem, int permits){
        sem.release(permits);
    }

    //this method will increment the index's value by 1 when ever the passenger enter their floor number
    //this mechanism mimic the counting sort, the value is incremented by one every time a customer choose a floor.
    //there are 10 floor, so if no one want to go to floor 10, index 10 will be 0, if 4 peple want to go to floor 6, index 6 will be 4.
    public static void getFloor(int index){
        floorQueue[index]++;
    }

    //this method count the number people that the elevator has transported while reset the number of people
    //at the current floor to 0 since they have already left the elevator.
    public static void remove(){
        transported += floorQueue[currentFloor];
        floorQueue[currentFloor] = 0;
    }

    //this method return the current floor
    public static int getCurrentFloor(){
        return currentFloor;
    }

    //this method get the permits number based on the value in the floorQueue array.
    //the index value tell the program how many people need to get out at certain floor,
    //and that is also the amount of permits need to be release
    public static int getCurrentFloorPermits(){
        return floorQueue[currentFloor];
    }

    //this method check if the floor queue is empty, only empty if all indexes has value of 0
    public static boolean isEmpty(){
        boolean empty = false;

        //step through array to find out if the array is empty, will be false unless all indexes is 0
        for (int i = 0; i < floorQueue.length; i++){
            if (floorQueue[i] == 0)
                empty = true;
            else
                return false;
        }
        return empty;
    }

    //this method return the number of people who is currently in the elevator
    public static int getHeadCount(){
        return headCount;
    }

    //this method set the value of the current floor to the value that is nearest to index 0
    //which mean that the lowest floor after floor 1
    public static void transport(){
        //this loop step through all floor indexes and if the first index encounter have value bigger than 0, then set the elevator to current floor
        for (int i = 0; i < floorQueue.length; i++){
            if (floorQueue[i] > 0) {
                currentFloor = i;
                break;
            }
        }
    }

    //this method reset key variable to 0 once the elevator go back to floor 1
    //transported , headCount will be reset to 0
    //currentFloor reset to 1
    public static void backToFloor1(){
        transported = 0;        //reset transported
        headCount = 0;          //reset head count
        currentFloor = 1;
    }

    //Print message the door is open at certain floor
    public static void openDoor(){
        System.out.println("Elevator door open at floor " + getCurrentFloor());
    }

    //Print message the door is closing
    public static void closeDoor(){
        System.out.println("Elevator door close");
    }

    //return the number of client who ride the elevator, this number is not the number of
    //client who ride the elevator but the number that the elevator has served from start to end, currently max = 49 people
    public static int getClientCount(){
        return clientCount;
    }

    //this message is use for debug program in parts and not part of the project requirements
    public static void systemStatus(int loc){
        System.out.println("hang here: " + loc);
    }

    //run method for the elevator
    @Override
    public void run() {
        //While the elevator has not carry everyone who want to ride the elevator, this while loop keep going
        while(clientCount != MAX_PEOPLE){
            semWait(floor1);                    //check if the elevator at floor 1
            semSignal(door);                    //signal door to open
            openDoor();                         //open the door
            semSignal(vacancy, MAX_LOAD);
            semWait(takeOff, MAX_LOAD);         //wait for all the people to enter then take off
            semWait(door);                      //signal door to close
            closeDoor();                        //close the door

            //this loop will keep going until there are no one remain in the elevator
            while(!isEmpty()){
                transport();            //go to next floor
                semSignal(door);        //signal door open
                openDoor();             //open the door
                semSignal(myFloor[getCurrentFloor()], getCurrentFloorPermits());        //release the amount of permits current floor need to release people who want to get out on this floor
                semWait(exited[getCurrentFloor()], getCurrentFloorPermits());           //wait for the amount of permits from the one who left the elevator to signal back. **Note, the amount of people leave have to = the amount people signal back on the same floor
                remove();               //Remove the people from the elevator queue list once they get out
                semWait(door);          //wait for the door to close
                closeDoor();            //close the door
            }
            semSignal(floor1);          //signal to go back to floor 1
            backToFloor1();             //go back to floor 1
        }

        System.out.println("Total passenger: " + getClientCount());     //Optional: print total passenger carried
        System.out.println("Simulation End");                           //print end message
    }


    //---------------------------------------------------------------PERSON CLASS------------------------------------------------------------
    static class Person implements Runnable{
        private int targetFloor;                        //floor the client want to go
        private int ID;                                 //client's id

        //class run method
        @Override
        public void run() {
            semWait(vacancy);                           //enter if there are slots available
            enterElevator();                            //enter the elevator
            getFloor(this.targetFloor);                 //set the floor the client want to go
            semSignal(takeOff);                         //signal elevator to take off
            semWait(myFloor[this.targetFloor]);         //wait for elevator signal to get out at target floor
            exitElevator();                             //exit the elevator
            semSignal(exited[this.targetFloor]);        //signal elevator passenger got out
        }

        //constructor set id and target floor for each person
        public Person(int floor, int id){
            this.targetFloor = floor;
            this.ID = id;
        }

        //method enter the elevator, print id floor and set head count
        public void enterElevator(){
            headCount++;
            System.out.println("Person " + ID + " enters, go to floor: " + targetFloor);
        }

        //method exit elevator, print id
        public void exitElevator(){
            clientCount++;
            System.out.println("Person " + ID + " leaves elevator");
        }
    }

    //-------------------------------------------------------------------------------MAIN METHOD-------------------------------------------------------------------
    public static void main(String[] args) {
        //Make thread for elevator and all people inline for the elevator
        Elevator elevator= new Elevator();              //make an elevator
        Thread elevatorTh = new Thread(elevator);       //make a thread for the elevator
        Thread personTh[] = new Thread[MAX_PEOPLE];

        //make all person thread, total 49 persons
        //all person thread are to assigned with an id and a random floor number from...
        //2 - 10 inclusive
        for (int i = 0; i < MAX_PEOPLE; i++){
            int floor = randomFloor();
            Person person = new Person(floor, i);
            personTh[i] = new Thread(person);
        }

        //start all the threads
        elevatorTh.start();
        for (int i = 0; i < personTh.length; i++){
            personTh[i].start();
        }

        //join all person threads
        for (int i = 0; i < personTh.length; i++){
            try {
                personTh[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //This function return a random number of floor
    public static int randomFloor(){
        Random rn = new Random();
        return rn.nextInt(10 - 2 + 1) + 2;      //this return a random value [2, 10] inclusive
    }
}