/*
CS2336.002

Analysis: This class is the most specific class in the parking lot system. This
          class is the element of each parking spot. This class have all the information
          about the vehicle that parked in the spot. The class have data fill
          that we'll need to access each parking spot and management the parking
          lot.

Design: This class is design with mostly setter and getter method, or accessor
        and mutator. The mutator will get the information request about the 
        parking spot, and the accessor will get new changed information to specific
        data fill to manage the parking lot system.

Test: To test this parking spot, the user can create a new parking lot and create
      vehicles and park the vehicle in the spot. 
 */
package parkinglotproject;

//Spot class
public class Spot {
    //data fills
    private int number;
    private String spotType;
    private int level;
    private Vehicle vehicle;
    private String owner;
    
    //create empty spot
    public Spot(String type, int index, int level){
        this.spotType = type;
        this.number = index;
        this.level = level;
        this.owner = "";
    }
    
    //set spot number to the number argument of the actually
    //index of the array
    public void setSpotNumber(int index){
        this.number = index;
    }
    
    //Get spot number, return int, get the number of
    //the spot
    public int getSpotNumber(){
        return this.number;
    }
    
    //Set spot type, set the spot type in string as
    //C or M or B stand for car, bus or motorcycle
    public void setSpotType(String type){
        this.spotType = type;
    }
    
    //get spot type return a string of the spot type of
    //the specific parking lot
    public String getSpotType(){
        return this.spotType;
    }
    
    //set spot level to the pass in level of the actual level
    //that the element is currently in. 
    public void setSpotLevel(int level){
        this.level = level;
    }
    
    //get spot level, return the level location of the spot
    public int getSpotLevel(){
        return this.level;
    }
    
    //set spot vehicle type, set the type of vehicle that
    //parked in this spot
    public void setSpotVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }
    
    //get spot vehicle, retunrn the vehicle type in this spot
    public Vehicle getSpotVehicle(){
        return this.vehicle;
    }
    
    //get owner name, return a string of the name of the owner
    public String getOwnerName(){
        return this.owner;
    }
    
    //set owner name, set the name for the owner
    public void setOwnerName(String owner){
        this.owner = owner;
    }
    
    //clear a spot, spot go back to default
    public void clearSpot(String type){
       
        this.spotType = type;
        this.vehicle = null;
        this.owner = "";
    }
}
    
