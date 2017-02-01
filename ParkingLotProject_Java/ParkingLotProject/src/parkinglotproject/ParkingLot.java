/*
CS2336.002

Analysis: This class hold the control between the main method and the entire
          program. This class will call on various method of the Level and Spot
          class to park the vehicle and choosing the floor to park the vehicle. 
          The main method will not interact with Level and Spot class, rather, 
          the main method will use this class, ParkingLot class to control the
          Level class and spot class.

Design: The design of this class is implement various method that will set 
        attribute and data fills for the Level class. The Skeleton of this class
        is the top of the the other two class where it hold all the control and
        information of the other two class. All of the method in this class
        is in abstract base that mean it will point to specific Level rather than
        specific spot. All of the processing that have to deal with spot, the class
        Level will handle that separately.

Test: To test this class, create a vehicle and a parkingLot to call on various method
      that this class have available to park the vehicle. There are overloading
      construction that let the user have different way of parking a vehicle,
      or getting the vehicle they park, or even to know where their vehicle actually
      are. The most generic and simplist method in this class probably the 
      parkVehicle() method which only take one arguement is the vehicle type.
 */
package parkinglotproject;

//Class ParkingLot
public class ParkingLot {
    private final int size = 5;                 //Number of levels
    private Level[] levels = new Level[size];   //Instances of level in an array
    
    //create new empty parking lot
    public ParkingLot(){
        for (int i = 0; i < size; i++){
            levels[i] = new Level(i);
        }
    }
    
    //Return number of levels
    public int getLevelCap(){
        return this.size;
    }
    
    //Display all the levels and spots inside of the parkingLot system
    public void printParkingLot(){
        System.out.println("Floor");
        for (int i = 0; i < size; i++){
            System.out.print(" " + i + " :   ");
            levels[i].printFloor();             //Call to print each floor 
        }      
    }
    
    //Park vehicle with only one argument: vehicle type
    public void parkVehicle(Vehicle vehicle){
        int emptyLvl = -1;
        
        //The following if statement will check whether the vehicle types belong
        //to a specific class then call another method to check for the spot 
        //availability and then finally park the vehicle into the spot which is
        //not occupied.
        if (vehicle.getClass() == Bus.class){
            emptyLvl = getLevelAvai(vehicle);   //Check availablity for spots
            if (emptyLvl == -1){
                System.out.println("There are no available parking!"); 
            }
            else if(emptyLvl > -1){
                levels[emptyLvl].parkBus(vehicle, "noName", emptyLvl);  //Park bus
            }
        }
        else if(vehicle.getClass() == Car.class){
            emptyLvl = getLevelAvai(vehicle);   //Check availability 
            if (emptyLvl == -1){
                System.out.println("There are no available parking!");
            }
            else if(emptyLvl > -1){
                levels[emptyLvl].parkCar(vehicle, "noName", emptyLvl); //Park vehicle
            }
        }
        else if(vehicle.getClass() == Motorcycle.class){
            emptyLvl = getLevelAvai(vehicle);   //Check availability
            if (emptyLvl == -1){
                System.out.println("There are no available parking!");
            }
            else if(emptyLvl > -1){
                levels[emptyLvl].parkMotorcycle(vehicle, "noName", emptyLvl);   //Park vehicle
            }
        }  
    }
    
    //This parkVehicle is an overloading method of the first one.
    //This method take in vehicle type, name of owner, level number and spot number.
    //The user can park their vehicle in specific spot, level, and leave a name
    //so that it enable them to check for their vehicle location and retrieve vehicle.
    public String parkVehicle(Vehicle vehicle, String name, int level, int spot){
        int avaiLevel = -1;
        
        //These line of if/else statement will check for vehicle availability 
        //for each type of cases. 
        if (level == -1 && spot == -1){
            avaiLevel = getLevelAvai(vehicle);
        }
        else if(level == -1){
            avaiLevel = getLevelAvai(spot, vehicle);
        }
        else if(spot == -1){
            avaiLevel = getLevelAvai(vehicle, level); 
        }
        else if(spot > -1 && level > -1){
            avaiLevel = getLevelAvai(vehicle, level, spot);
        }
        
        //In the case of there are no available spot, error message is return
        if (avaiLevel == -1)
            return "Cannot park! No available parking space.";
        
        //These lines of if/else statement will walk through each case and
        //check if the arguements that was passed in is useful. This method let
        //let the user put lack of information if they do not wish to provide. 
        if (spot == -1){
            if (vehicle.getClass() == Bus.class){
                levels[avaiLevel].parkBus(vehicle, name, avaiLevel);
            }
            else if(vehicle.getClass() == Car.class){
                levels[avaiLevel].parkCar(vehicle, name, avaiLevel);
            }
            else if(vehicle.getClass() == Motorcycle.class){
                levels[avaiLevel].parkMotorcycle(vehicle, name , avaiLevel);
            }
        }
        else
            if (vehicle.getClass() == Bus.class){
                levels[avaiLevel].parkBus(vehicle, spot, name, level);
            }
            else if(vehicle.getClass() == Car.class){
                levels[avaiLevel].parkCar(vehicle, spot, name, level);
            }
            else if(vehicle.getClass() == Motorcycle.class){
                levels[avaiLevel].parkMotorcycle(vehicle, spot, name, level);
            }
        return "DONE."; //return done when done park the vehicle.
    }
    
    //This method get the level availability, checking of the level of parking
    //is full.
    //This method will call on another method to check for spots in the level
    //is available to determine if the level is filled.
    public int getLevelAvai(Vehicle vehicle){
        int level = -1;
        boolean flag;
        for (int i = 0; i < 5; i++){
            flag = levels[i].isSpotFill(vehicle);
            if (!flag){
                level = i;
                break;
            }
        }
        return level;
    }
    
    //This method will check the availablility of specific level.
    public int getLevelAvai(Vehicle vehicle, int level){
        boolean flag;
        flag = levels[level].isSpotFill(vehicle);
        if (flag)
            level = -1;
        
        return level;
    }
    
    //This method will check the availability of specific spot in the parking system.
    //This method will check the same spot for all levels.
    public int getLevelAvai(int spot, Vehicle vehicle){
        boolean flag = true;
        int level = -1;
        for (int i = 0; i < 5; i++){
            flag = levels[i].isSpotFill(vehicle,spot);
            if (!flag){
                level = i;
                break;
            }
        }
        return level;
    }
    
    //This method will check the availability for specific level in specific spot.
    public int getLevelAvai(Vehicle vehicle, int level, int spot){
        boolean flag = true;
        flag = levels[level].isSpotFill(vehicle,spot);
        if (flag)
            level = -1;
        
        return level;
    }
    
    //This method will get the vehicle in the parking system and clear the vehicle
    //out of the parking System and return the string of the status of the 
    //operation, if no clear operation carried out, error message prompt.
    public String getVehicle(String name){
        boolean clear = false;
        for (int i = 0; i < size; i++){
            Spot[] temp = levels[i].getLevel();
            for( int j = 0; j < levels[i].getSpotCap();j++){
                if (temp[j].getOwnerName().equals(name)){
                    if (j < 6)
                        temp[j].clearSpot("l");
                    else if (j >= 6 && j < 24)
                        temp[j].clearSpot("c");
                    else if (j >= 24 && j < size)
                        temp[j].clearSpot("m");
                    clear = true;
                }
            }
        }
        if (clear)
            return "DONE.";
        else
            return "Cannot find a parking with your name, check input again!";    
    }
    
    //This method will find the vehicle and prompt the user the location of the
    //vehicle.
    public void findVehicle(String name){
        int ownerIndex;
        for (int i = 0; i < size; i++){
            ownerIndex = levels[i].isOwner(name);
            if (ownerIndex > -1){
                System.out.println("Vehicle found on floor: " + i + "spot number: " + ownerIndex);
            }
        }
    }
}
