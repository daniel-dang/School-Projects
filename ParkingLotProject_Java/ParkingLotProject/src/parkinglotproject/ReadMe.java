/*
Program created by: Daniel Dang
CS2336.002

Read instruction for running the program below.
 */
package parkinglotproject;

public class ReadMe {
    /*
        The simple implementation meet the require can be implemented by simply do
        the following steps:
        
    First: create a new parking lot object. This object will have all 5 levels
            and 30 spots in each level
    Second: use the method public void printParkingLot(); to print out the floor
            layout.
    Third: create a new vehicle of any type and call the public method: parkVehicle(Vehicle v);
    Fourth: use the method printParkingLot(); again to print the parking lot to see
            the change.
    After the change was made, repeat step 1-4 to add new anonymous vehicle.
    
    For the new feature there are more methods that can be call
    To create a reference back to the location of the vehicle was park 
    
    First: call method parkVehicle(Vehicle v, String name, int level, int spot); to have
            information to be stored in the spot itself as reference for later on
            to get the vehicle or clear vehicle out of parkinglot
    Second: To find where the vehicle are, call method findVehicle(String name);
            This method will find the vehicle that associated with the name given in
            the previous step, if name was not match error message will be prompt.
    Third: To get vehicle out of the parking lot call method: getVehicle(String name);
            This method will get vehicle with given name that was entered when the vehicle was
            park. After vehicle found, the vehicle will be fetch then remove from
            the parking space.
            If name were incorrect, error message will prompt.
        
    */
}
