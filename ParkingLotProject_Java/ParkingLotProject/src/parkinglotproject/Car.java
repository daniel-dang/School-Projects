/*
CS2336.002

Analysis: This purpose of this class is to enable the user to create specific 
          instance of the type of vehicle inside the Vehicle class. In this case
          the class is Car. This class car is unique and different from Bus and
          Motorcycle class
Design: The use of this class is used to implement in the ParkingLot class.
        The user will need to create one of this class and will need to park
        an instance of this class in ParkingLot class using parkVehicle() method.
Test: To test this class we simply create an instance of this class and pass
      the instance in to the parkVehicle() method in ParkingLot class to park.
 */

package parkinglotproject;

//Class Car
public class Car extends Vehicle {
    
    //Default constructor
    public Car(){
    }
}