/*
CS2336.002

Analysis: This Level class control all the spots that going on in the parking
          System in a single level. The ParkingLot can handle the level however,
          this class will handle on a smaller scope, that it will handle only
          specific row. This class will have methods that will check for spots
          availability in a row, get level spot number and other similair method
          like the ParkingLot but on a smaller scale, spot scope only.

Design: The design of this class is similair to the ParkingLot, however, instead of
        managing the whole parking system, this class will only manage the entire
        level where each 30 spot exist. This class have varous method that available
        to use to obtain information, set information control spots in a single
        level.

Test: To test this class, the user can make a new pakring lot and add vehicles
      to the parking lot and call on the method to set specific row and spot to
      park their vehicle. This class hand also handle specific row parking. The user
      can set their vehicle in certain level but any spot.
 */
package parkinglotproject;

//Level class
public class Level {
    private int size = 30;                  //spot cap
    protected Spot[] spots = new Spot[size];//array of instances of spot type.
    
    //Create an empty floor
    public Level(int level){
        for (int i = 0; i < size; i++){
            if (i < 6 )
                spots[i] = new Spot("l", i, level);
            else if (i >= 6 && i < 24)
                spots[i] = new Spot ("c", i, level);
            else if (i >= 24 && i < size)
                spots[i] = new Spot ("m", i, level);
        }  
    }
    
    //Get the spots capacity, return a int of how many space
    //can fit this level
    public int getSpotCap(){
        return this.size;
    }
    
    //Get spot array. this method allow other class to have access to the low level
    //of spots to control and manage levels.
    public Spot[] getLevel(){
        return this.spots;
    }
    
    //This method print individual floor
    public void printFloor(){
        for (int i = 0; i < size; i++){
            System.out.print(spots[i].getSpotType());
            if (i == 9 || i == 19)
                System.out.print(" ");
        }
        System.out.println();
    }
    
    //This method determine if the name "owner" in the spot, match with the 
    //the name that was pass in. If the name match, the method return the
    //index for the owner where the location of their car is.
    public int isOwner(String name){
        int indexOwner = -1;
        for (int i = 0; i < size; i++){
            if (spots[i].getOwnerName().equals(name)){
                indexOwner = i;
            }
        }
        return indexOwner;
    }
    
    //This method will clear the spot out of the system and set everything back
    //to default.
    public void clearSpot(int index){
        if (index < 6)
            spots[index].clearSpot("l");
        else if (index >= 6 && index < 24)
            spots[index].clearSpot("c");
        else if (index >= 24 && index < size)
            spots[index].clearSpot("m");
    }
    
    //This method will check if the spot is empty. This method have variuos 
    //variations that overiding the same method for different arguments
    public boolean isSpotFill(Vehicle vehicle){
        boolean flag = false;
        
        //These lines of code will check each specific type of vehicle to
        //make sure the spot fit specific types of vehicle in the parking lot.
        if (vehicle.getClass() == Bus.class){
            int occupied = 0;
            for (int i = 0; i < 6; i++){
                if (spots[i].getOwnerName() != "" && (i == 0 || i == 5))
                    occupied++;
                else if (spots[i].getOwnerName() != "" && (i >= 1 && i <= 4)){
                    flag = true;
                    break;
                } 
            }
            if (occupied > 1)
                flag = true;
        }
        
        //Check name for car class
        else if (vehicle.getClass() == Car.class){
            for (int i = 0; i < 24; i++){
                if (spots[i].getOwnerName() == "")
                    flag = false;
            }
        }
        //Check name for motorcycle class
        else if(vehicle.getClass() == Motorcycle.class){
            for (int i = 0; i < size; i++){
                if (spots[i].getOwnerName() == "")
                    flag = false;
            }
        }
        return flag;    //return whether or not the name is match
    }
    
    //This class check if the specific spot is empty in all level
    //Similair to the other method, this method will check the name of 
    //each type of parking.
    public boolean isSpotFill(Vehicle vehicle, int index){
        boolean flag = true;
        if (vehicle.getClass() == Bus.class){
            flag = isSpotFill(vehicle);
            if (!flag){
                if (spots[1].getOwnerName() == "")
                    if(spots[index].getOwnerName() == "")
                        flag = false;
            }     
        }
        
        else if (vehicle.getClass() == Car.class){
            for (int i = 0; i < 24; i++){
                if(spots[index].getOwnerName() == ""){
                    flag = false;
                    break;
                }
            }
        }
        
        else if (vehicle.getClass() == Motorcycle.class){
            for (int i = 0; i < size; i++){
                if (spots[index].getOwnerName() == ""){
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
    
    //These method down here is the park method that park each specific vehicle. 
    //Each of this type of method have more than one variation to match all the arguments
    //that can be pass in.
    
    //This method will park a bus with full arguments of all the information available.
    public void parkBus(Vehicle vehicle, int spotNumber, String ownerName, int level){
        if (isSpotFill(vehicle, spotNumber)){
            for (int i = spotNumber; i < spotNumber + 4; i++){
                spots[i].setSpotVehicle(vehicle);
                spots[i].setSpotNumber(i);
                spots[i].setSpotLevel(level);
                spots[i].setOwnerName(ownerName);
                spots[i].setSpotType("B");
            }
        }
    }
    
    //This method will park a bus with limited number of information
    //This method is more general and require more error checking.
    public void parkBus(Vehicle vehicle, String ownerName, int level){
        if (spots[0].getOwnerName() == ""){
            for (int i = 0; i < 5; i++){
                spots[i].setSpotVehicle(vehicle);
                spots[i].setSpotNumber(i);
                spots[i].setSpotLevel(level);
                spots[i].setOwnerName(ownerName);
                spots[i].setSpotType("B");
            }
        }
        //since bus can only park in a straight line, this step check
        //whether the spots available for the bus to park if one of the large
        //space happen to be occupied.
        else if(spots[1].getOwnerName() == ""){
            if(spots[1].getOwnerName() == ""){
                if(spots[0].getOwnerName() == ""){
                    for (int i = 0; i < 5; i++){
                        spots[i].setSpotVehicle(vehicle);
                        spots[i].setSpotNumber(i);
                        spots[i].setSpotLevel(level);
                        spots[i].setOwnerName(ownerName);
                        spots[i].setSpotType("B");
                    }
                }
                else if(spots[0].getOwnerName() != ""){
                    for (int i = 1; i < 6; i++){
                        spots[i].setSpotVehicle(vehicle);
                        spots[i].setSpotNumber(i);
                        spots[i].setSpotLevel(level);
                        spots[i].setOwnerName(ownerName);
                        spots[i].setSpotType("B");
                    }
                }  
            }
            else
                System.out.println("Cannot park! No available parking space.");
        }
    }
    
    //This method park a car with full arguments
    public void parkCar(Vehicle vehicle, int spotNumber, String ownerName, int level){
        spots[spotNumber].setSpotVehicle(vehicle);
        spots[spotNumber].setSpotNumber(spotNumber);
        spots[spotNumber].setSpotLevel(level);
        spots[spotNumber].setOwnerName(ownerName);
        spots[spotNumber].setSpotType("C");
    }
    
    //General method to park a car.
    public void parkCar(Vehicle vehicle, String ownerName, int level){
        boolean park = false;
        for (int i = 0; i < 24; i++){
            if (spots[i].getOwnerName() == ""){
                spots[i].setSpotVehicle(vehicle);
                spots[i].setSpotNumber(i);
                spots[i].setSpotLevel(level);
                spots[i].setOwnerName(ownerName);
                spots[i].setSpotType("C");
                park = true;
            }
            if (park)
                break;
        }
    }
    
    //This method park a motorcycle with full arguments
    public void parkMotorcycle(Vehicle vehicle, int spotNumber, String ownerName, int level){
        spots[spotNumber].setSpotVehicle(vehicle);
        spots[spotNumber].setSpotNumber(spotNumber);
        spots[spotNumber].setSpotLevel(level);
        spots[spotNumber].setOwnerName(ownerName);
        spots[spotNumber].setSpotType("M");
    }
    
    //This method park a motorcycle with limited information
    public void parkMotorcycle(Vehicle vehicle, String ownerName, int level){
        for (int i = 0; i < size; i++){
            if (spots[i].getOwnerName() == ""){
                spots[i].setSpotVehicle(vehicle);
                spots[i].setSpotNumber(i);
                spots[i].setSpotLevel(level);
                spots[i].setOwnerName(ownerName);
                spots[i].setSpotType("M");
                break;
            }
        }
    }
}
