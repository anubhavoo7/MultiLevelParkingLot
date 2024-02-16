package BootCamp.MultiLevelParkingLot;

import java.util.*;
import java.util.regex.*;

public class MultiParkingLot {
	
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
        List<ParkingLot> parkingLots = new ArrayList<>();
        boolean initialized = false;

        while (true) {
            System.out.println("Main menu");

            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Enter any to exit");
            System.out.println("Select your choice:");

            if (!scanner.hasNextInt()) {
                System.out.println("Enter valid input.");
                scanner.nextLine();
                continue;
            }

            int modeChoice = scanner.nextInt();
            scanner.nextLine();

            switch (modeChoice) {
                case 1:
                    initialized = adminMenu(scanner, parkingLots, initialized);
                    break;
                case 2:
                    userMenu(scanner, parkingLots, initialized);
                    break;
                case 3:
                    System.out.println("Program End.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Enter valid input.");
                    break;
            }
        }
    }

    private static void userMenu(Scanner scanner, List<ParkingLot> parkingLots, boolean initialized) {
        if (!initialized) {
            System.out.println("\nPlease initialize the parking lot.");
            return;
        }

        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. Park Vehicle.");
            System.out.println("2. Un-park Vehicle.");
            System.out.println("3. Go back to the main menu.");

            if (!scanner.hasNextInt()) {
                System.out.println("\nEnter valid input.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    parkVehicle(parkingLots, scanner);
                    break;
                case 2:
                    unparkVehicle(parkingLots, scanner);
                    break;
                case 3:
                    return; // Exit to mode selection
                default:
                    System.out.println("\nInvalid option. Please choose again.");
                    break;
            }
        }
    }

    private static boolean adminMenu(Scanner scanner, List<ParkingLot> parkingLots, boolean initialized) {
        boolean ans = adminValidation();

        if (ans) {
            while (true) {
                System.out.println("Admin Menu");
                System.out.println("1. Initialize parking levels");
                System.out.println("2. Initialize parking lots");
                System.out.println("3. View Parking Lot current occupancy details");
                System.out.println("4. View all lots details");
                System.out.println("5. Park Vehicle");
                System.out.println("6. Un-park Vehicle");
                System.out.println("7. Go Back to the main menu.");

                if (!scanner.hasNextInt()) {
                    System.out.println("\nEnter valid input.");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        initializeParkingLevels(parkingLots, scanner);
                        initialized = true;
                        if (!initialized) {
                            System.out.println("\nInitialize the parking levels first.");
                            break;
                        }
                        initializeParkingLotsSeparately(parkingLots, scanner);
                        break;
                        
                    case 2:
                    	System.out.println("Enter the level :");
                    	int level=scanner.nextInt();
                       initializeParkingForGivenLevel(level,parkingLots,scanner);
                       break;
                    case 3:
                        if (!initialized) {
                            System.out.println("\nPlease initialize the parking lot first.");
                            break;
                        }
                        viewOccupancyDetails(parkingLots);
                        break;
                    case 4:
                        if (!initialized) {
                            System.out.println("\nInitialize the parking lot first.");
                            break;
                        }
                        viewAllLotsDetails(parkingLots);
                        break;
                    case 5:
                        if (!initialized) {
                            System.out.println("\nInitialize the parking lot first.");
                            break;
                        }
                        parkVehicle(parkingLots, scanner);
                        break;
                    case 6:
                        if (!initialized) {
                            System.out.println("\nInitialize the parking lot first.");
                            break;
                        }
                        unparkVehicle(parkingLots, scanner);
                        break;
                    case 7:
                        return initialized; // Exit to mode selection
                    default:
                        System.out.println("\nEnter valid input.");
                        break;
                }
            }
        } else {
            System.out.println("\nInvalid email or password.");
        }
        return initialized; // Return the unchanged value of initialized
    }

    private static void initializeParkingForGivenLevel(int level, List<ParkingLot> parkingLots, Scanner scanner) {
    	if(level >= 0  && level <= parkingLots.size()) {
    	initializeParkingLotAtLevel(level, parkingLots.get(level-1), scanner);
    	}
    	else {
    		System.out.println("Enter valid level");
    		return;
    	}
	}

	private static void initializeParkingLevels(List<ParkingLot> parkingLots, Scanner scanner) {
        System.out.print("\nEnter number of levels: ");
        int numLevels = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numLevels; i++) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLots.add(parkingLot);
        }
        System.out.println("\nParking levels initialized successfully.");
    }

    private static void initializeParkingLotsSeparately(List<ParkingLot> parkingLots, Scanner scanner) {
    }

    private static void initializeParkingLotAtLevel(int level, ParkingLot parkingLot, Scanner scanner) {
        System.out.print("Enter number of slots for 2 Wheeler on Level " + level + ": ");
        int num2WheelerSlots = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter number of slots for 4 Wheeler on Level " + level + ": ");
        int num4WheelerSlots = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter number of slots for Heavy Vehicle on Level " + level + ": ");
        int numHeavyVehicleSlots = scanner.nextInt();
        scanner.nextLine();

        parkingLot.initializeParkingLot(num2WheelerSlots, num4WheelerSlots, numHeavyVehicleSlots);
    }
	

	    private static void viewOccupancyDetails(List<ParkingLot> parkingLots) {
	        for (int i = 0; i < parkingLots.size(); i++) {
	            System.out.println("\nLevel " + (i + 1) + " Occupancy Details:");
	            ParkingLot parkingLot = parkingLots.get(i);
	            System.out.println("2-Wheeler: Slots: " + parkingLot.num2WheelerSlots +
	                    ", Occupied: " + parkingLot.getOccupiedSlots("2wheeler") +
	                    ", Vacant: " + (parkingLot.num2WheelerSlots- parkingLot.getOccupiedSlots("2wheeler")));
	            System.out.println("4-Wheeler: Slots: " + parkingLot.num4WheelerSlots +
	                    ", Occupied: " + parkingLot.getOccupiedSlots("4wheeler") +
	                    ", Vacant: " + (parkingLot.num4WheelerSlots - parkingLot.getOccupiedSlots("4wheeler")));
	            System.out.println("HeavyVehicle: Slots: " + parkingLot.numHeavyVehicleSlots +
	                    ", Occupied: " + parkingLot.getOccupiedSlots("heavy") +
	                    ", Vacant: " + (parkingLot.numHeavyVehicleSlots - parkingLot.getOccupiedSlots("heavy")));
	        }
	    }

	    private static void viewAllLotsDetails(List<ParkingLot> parkingLots) {
	        for (int i = 0; i < parkingLots.size(); i++) {
	            System.out.println("\nLevel " + (i + 1) + " Parking Tickets:");
	            parkingLots.get(i).printAllOccupiedSlots();
	        }
	    }

	    private static void parkVehicle(List<ParkingLot> parkingLots, Scanner scanner) {
	        System.out.print("Enter vehicle number: ");
	        String vehicleNumber = ValidVehicleNumberPlate();

	        System.out.print("Enter your choice.");
            System.out.println("1.2-Wheeler");
            System.out.println("2.4-Wheeler");
            System.out.println("3. HeavyVehicle");
	        String vehicleType = yourChoice();

	        int level = selectParkingLevel(parkingLots, scanner);
	        if (level != -1) {
	            ParkingLot parkingLot = parkingLots.get(level - 1);
	            ParkingTicket ticket = parkingLot.parkVehicle(vehicleNumber, vehicleType);
	            if (ticket != null) {
	                System.out.println("Vehicle parked successfully on Level " + level + ".");
	                System.out.println("ID             : " + ticket.getUniqueID());
	                System.out.println("Parking Place  : "+"Level:"+level+" "+"Slot No.:"+ ticket.getSlotNumber());
	                System.out.println("Vehicle Number : " + ticket.getVehicleNumber());
	                System.out.println("Vehicle Type   : " + vehicleType);
	                System.out.println("In Time        : " + ticket.getInTime());
	            }
	        } else {
	            System.out.println("\nInvalid parking level.");
	        }
	    }

	    private static void unparkVehicle(List<ParkingLot> parkingLots, Scanner scanner) {
	        int level = selectParkingLevel(parkingLots, scanner);
	        if (level != -1) {
	            ParkingLot parkingLot = parkingLots.get(level - 1);

	            System.out.print("Enter your choice.\n 1. 2 Wheeler\n 2. 4 Wheeler\n 3. Heavy Vehicle.\n  ");
	            String vehicleTypeToUnpark = yourChoice();

	            System.out.print("Enter number plate: ");
	            String vehicleNumberToUnpark = scanner.nextLine().toUpperCase();

	            ParkingTicket unparkedTicket = parkingLot.unparkVehicle(vehicleNumberToUnpark, vehicleTypeToUnpark);
	            if (unparkedTicket != null) {
	                System.out.println("Vehicle Unparked Successfully from Level " + level + ".");
	                System.out.println("ID             : " + unparkedTicket.getUniqueID());
	                System.out.println("Vehicle Number : " + unparkedTicket.getVehicleNumber());
	                System.out.println("Vehicle Type   : " + vehicleTypeToUnpark);
//                    System.out.println("Level/Slot Number    : "+level+"/"+ ticket.getSlotNumber());
	                System.out.println("In Time        : " + unparkedTicket.getInTime());
	                System.out.println("Out Time       : " + unparkedTicket.getOutTime());
	            } else {
	                System.out.println("Vehicle not found in Level " + level + ".");
	            }
	        } else {
	            System.out.println("Invalid parking level.");
	        }
	    }

	    private static int selectParkingLevel(List<ParkingLot> parkingLots, Scanner scanner) {
	        System.out.print("Enter parking level (1-" + parkingLots.size() + "): ");
	        if (scanner.hasNextInt()) {
	            int level = scanner.nextInt();
	            scanner.nextLine();
	            if (level >= 1 && level <= parkingLots.size()) {
	                return level;
	            } else {
	                System.out.println("Invalid parking level. Please enter a valid level.");
	            }
	        } else {
	            System.out.println("Invalid input. Please enter a valid level.");
	            scanner.nextLine();
	        }
	        return -1;
	    }


	   private static String ValidVehicleNumberPlate() {
	    	System.out.println("\nnumber plate");
	    	Scanner scanner = new Scanner(System.in);
	    	String Numberplate = scanner.nextLine().toUpperCase();
	    	String regex = "^[A-Z]{2}[\\ -]{0,1}[0-9]{2}[\\ -]{0,1}[A-Z]{1,2}[\\ -]{0,1}[0-9]{4}$";

	    	Pattern p = Pattern.compile(regex);

	    	Matcher m = p.matcher(Numberplate);

	    	if(m.find()) {
	    		return Numberplate;
	    	}else {
	    		System.out.println("Enter valid Number Plate :");
	    	}
	    	return ValidVehicleNumberPlate();
		}

		private static boolean adminValidation() {
	    	Scanner scanner = new Scanner(System.in);
	    	String admin = "admin";
	        String adminpassword = "1234";
	        System.out.println("Enter username and password");

	        System.out.print("Username ");
	        String username = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();
	        System.out.println("");
	        if (username.equals(admin) && password.equals(adminpassword)) {
	        	return true;
	        }else {
	        	System.out.println("Enter valild input.");
	        	return false;
	        }

		}

		private static String yourChoice() {
			Scanner scan = new Scanner(System.in);
	    	int chooice = 0;
	    	try {
	    		chooice = scan.nextInt();
	    		if(chooice==1) {
	    			return "2wheeler";
	    		}else if(chooice==2) {
	    			return "4wheeler";
	    		}else if(chooice==3) {
	    			return "heavy";
	    		}else {
	    			System.out.println("Enter valid input.");
	    			yourChoice();
	    		}
	    		
	    	}catch (Exception e) {
	    		System.out.println("Enter valid input.");
	    		yourChoice();
			}
	    	scan.close();
			return "2wheeler";
		}

		
}
