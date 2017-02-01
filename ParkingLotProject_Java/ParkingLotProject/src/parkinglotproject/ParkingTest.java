/*
 */
package parkinglotproject;

import java.util.Scanner;

public class ParkingTest {
    //Main method
    public static void main(String[] args){
        
        System.out.println("---------Parking Layout----------");

            ParkingLot parkingLot = new ParkingLot();
            parkingLot.printParkingLot();

        boolean exitButton = true;
        
        do{
            Scanner input = new Scanner(System.in);

            //prompt user to enter input
            System.out.println("Choose from the following menu: \n" +
                               "1: Park vehicle\n" +
                               "2: Get vehicle\n" +
                               "3: Find vehicle\n" +
                               "4: Exit program");
            System.out.print("Enter: ");
            int choice = input.nextInt();

            switch (choice){
                case 1:
                    String spot;
                    int index = 0;
                    String floor;
                    int level = 0;
                    String name;
                    String vehicle;
                    Vehicle type = new Vehicle();

                    System.out.println("Enter vehicle type from the follwing:\n" +
                                       "B = BUS\n" + 
                                       "C = CAR\n" +
                                       "M = MOTORCYCLE");
                    System.out.print("Enter: ");
                    vehicle = input.next();
                    if ("B".equals(vehicle))
                        type = new Bus();
                    else if("C".equals(vehicle))
                        type = new Car();
                    else if("M".equals(vehicle))
                        type = new Motorcycle();

                    System.out.print("Enter your first name and last name without space (ie: DanielDang) in between: ");
                    name = input.next();

                    System.out.print("Enter a floor number from 0 - 4. Or press \"s\" to skip: ");
                    floor = input.next();
                    floor = floor.toLowerCase();

                    if ("s".equals(floor)){
                        level = -1;
                    }
                    else if(Integer.parseInt(floor) >= 0 && Integer.parseInt(floor) <= 5){
                        level = Integer.parseInt(floor);
                    }
                    else{
                        System.out.println("Input error, restarting program!");
                    }

                    System.out.print("Enter a spot number from 0 - 29. Or press \"s\" to skip: ");
                    spot = input.next();
                    spot = spot.toLowerCase();
                    boolean error = false;
                    if ("s".equals(spot))
                        index = -1;
                    else if(vehicle.equals("B") && (!(spot.equals("0") || spot.equals("1")))){
                        System.out.println("Bus can only park in spot 0 or 1 \n" +
                                           "System restart!");  
                        
                        error = true;
                    }
                    else if(Integer.parseInt(spot) >= 0 && Integer.parseInt(spot) <= 30)
                        index = Integer.parseInt(spot);
                    else{
                        System.out.println("Input error, restarting program!");
                    }
                    
                    if (!error){
                        String status = parkingLot.parkVehicle(type, name, level, index);
                        System.out.println(status);
                        parkingLot.printParkingLot();
                    }
                    break;
                case 2:
                    System.out.print("Enter your name associated with the parking you register: ");
                    String ownerC = input.next();
                    System.out.println(parkingLot.getVehicle(ownerC));
                    parkingLot.printParkingLot();
                    break;
                case 3:
                    System.out.print("Enter your name associated with the parking you register: ");
                    String ownerS = input.next();
                    parkingLot.findVehicle(ownerS);
                    break;
                case 4:
                    System.out.println("System now exit!");
                    exitButton = false;
                    break;
                default:
                    break;
            }
        }while(exitButton);
        
        
        ParkingLot test = new ParkingLot();
        test.printParkingLot();
        Vehicle v,b,c;
        v = new Vehicle();
        b = new Motorcycle();
        c = new Car();
        
        v = new Bus();
        test.parkVehicle(b);
        test.printParkingLot();
        test.parkVehicle(c);
        test.printParkingLot();
        test.parkVehicle(v);
        test.printParkingLot();
              test.parkVehicle(v);
        test.printParkingLot();
              test.parkVehicle(v);
        test.printParkingLot();
    }
                
}
