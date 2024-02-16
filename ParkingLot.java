package BootCamp.MultiLevelParkingLot;

import java.util.*;

public class ParkingLot {
	int num2WheelerSlots;
    int num4WheelerSlots;
    int numHeavyVehicleSlots;
    private boolean[] occupied2WheelerSlots;
    private boolean[] occupied4WheelerSlots;
    private boolean[] occupiedHeavyVehicleSlots;
    private Map<Integer, ParkingTicket> ticketMap;

    public ParkingLot() {
        this.num2WheelerSlots = 0;
        this.num4WheelerSlots = 0;
        this.numHeavyVehicleSlots = 0;
        this.occupied2WheelerSlots = new boolean[0];
        this.occupied4WheelerSlots = new boolean[0];
        this.occupiedHeavyVehicleSlots = new boolean[0];
        this.ticketMap = new HashMap<>();
    }

    public boolean isInitialized() {
        return num2WheelerSlots >=0  && num4WheelerSlots >= 0 && numHeavyVehicleSlots >= 0;
    }

    public void initializeParkingLot(int num2WheelerSlots, int num4WheelerSlots, int numHeavyVehicleSlots) {
        if (num2WheelerSlots < 0 || num4WheelerSlots < 0 || numHeavyVehicleSlots < 0) {
            System.out.println("Number of slots must be greater than 0.");
            return;
        }
        this.num2WheelerSlots = num2WheelerSlots;
        this.num4WheelerSlots = num4WheelerSlots;
        this.numHeavyVehicleSlots = numHeavyVehicleSlots;
        this.occupied2WheelerSlots = new boolean[num2WheelerSlots];
        this.occupied4WheelerSlots = new boolean[num4WheelerSlots];
        this.occupiedHeavyVehicleSlots = new boolean[numHeavyVehicleSlots];
        this.ticketMap.clear();
        System.out.println("Parking lot initialized successfully.");
    }

    public boolean checkAvailability(String vehicleType) {
        if (!isInitialized()) {
            System.out.println("Please initialize slots first.");
            return false;
        }

        switch (vehicleType) {
            case "2wheeler":
                return findAvailableSlot(occupied2WheelerSlots) != -1;
            case "4wheeler":
                return findAvailableSlot(occupied4WheelerSlots) != -1;
            case "heavy":
                return findAvailableSlot(occupiedHeavyVehicleSlots) != -1;
            default:
                return false;
        }
    }

    private int findAvailableSlot(boolean[] occupiedSlots) {
        for (int i = 0; i < occupiedSlots.length; i++) {
            if (!occupiedSlots[i]) {
                return i + 1; 
            }
        }
        return -1; 
    }

    public ParkingTicket parkVehicle(String vehicleNumber, String vehicleType) {
        if (!isInitialized()) {
            System.out.println("Please initialize slots first.");
            return null;
        }

        if (!Arrays.asList("2wheeler", "4wheeler", "heavy").contains(vehicleType)) {
            System.out.println("Invalid vehicle type.");
            return null;
        }
        
        for (Map.Entry<Integer, ParkingTicket> entry : ticketMap.entrySet()) {
        	ParkingTicket ticket = entry.getValue();
        	if(ticket.getVehicleNumber().equals(vehicleNumber) && ticket.getVehicleType().equals(vehicleType)) {
        		System.out.println("Vehicle is already present.");
        		return null;
        	}
        	
        }
        

        if (checkAvailability(vehicleType)) {
            int slotNumber = -1;
            switch (vehicleType) {
                case "2wheeler":
                    slotNumber = findAvailableSlot(occupied2WheelerSlots);
                    occupied2WheelerSlots[slotNumber - 1] = true;
                    break;
                case "4wheeler":
                    slotNumber = findAvailableSlot(occupied4WheelerSlots);
                    occupied4WheelerSlots[slotNumber - 1] = true;
                    break;
                case "heavy":
                    slotNumber = findAvailableSlot(occupiedHeavyVehicleSlots);
                    occupiedHeavyVehicleSlots[slotNumber - 1] = true;
                    break;
            }
            ParkingTicket ticket = new ParkingTicket(vehicleNumber, vehicleType, slotNumber, new Date());
            ticketMap.put(slotNumber, ticket);
            return ticket;
        } else {
            System.out.println("No available slots for this vehicle type.");
            return null;
        }
    }

    public ParkingTicket unparkVehicle(String vehicleNumber,String vehicleType ) {
        if (!isInitialized()) {
            System.out.println("Initialize slots first.");
            return null;
        }

        // Search for the vehicle in the ticketMap
        for (Map.Entry<Integer, ParkingTicket> entry : ticketMap.entrySet()) {
            ParkingTicket ticket = entry.getValue();
            if (ticket.getVehicleNumber().equals(vehicleNumber) && ticket.getVehicleType().equals(vehicleType)) {
               // String vehicleType = ticket.getVehicleType();
                int slotNumber = entry.getKey();

                switch (vehicleType) {
                    case "2wheeler":
                        occupied2WheelerSlots[slotNumber - 1] = false;
                        break;
                    case "4wheeler":
                        occupied4WheelerSlots[slotNumber - 1] = false;
                        break;
                    case "heavy":
                        occupiedHeavyVehicleSlots[slotNumber - 1] = false;
                        break;
                }

                ticket.setOutTime(new Date());
                ticketMap.remove(slotNumber);
                return ticket;
            }
        }

        System.out.println("No vehicle found with the specified number plate.");
        return null;
    }

    public int getTotalSlots(String vehicleType) {
        switch (vehicleType) {
            case "2wheeler":
                return num2WheelerSlots;
            case "4wheeler":
                return num4WheelerSlots;
            case "heavy":
                return numHeavyVehicleSlots;
            default:
                return 0;
        }
    }

    public int getOccupiedSlots(String vehicleType) {
        switch (vehicleType) {
            case "2wheeler":
                return countOccupiedSlots(occupied2WheelerSlots);
            case "4wheeler":
                return countOccupiedSlots(occupied4WheelerSlots);
            case "heavy":
                return countOccupiedSlots(occupiedHeavyVehicleSlots);
            default:
                return 0;
        }
    }
    
    public void printAllOccupiedSlots() {
    	
    	if(ticketMap.isEmpty()) {
    		System.out.println("No Vehicles present in lot ");
    		return;
    	}
    	
    	System.out.println("\nAll Parking Tickets:");
        for (Map.Entry<Integer, ParkingTicket> entry : ticketMap.entrySet()) {
            ParkingTicket ticket = entry.getValue();
            System.out.println("Slot Number    : " + entry.getKey());
            System.out.println("ID             : " + ticket.getUniqueID());
            System.out.println("Vehicle Number : " + ticket.getVehicleNumber());
            System.out.println("Vehicle Type   : " + ticket.getVehicleType());
            System.out.println("In Time        : " + ticket.getInTime());
            System.out.println("Out Time       : " + (ticket.getOutTime() != null ? ticket.getOutTime() : "00:00"));
        }
    	
    }

    private int countOccupiedSlots(boolean[] occupiedSlots) {
        int count = 0;
        for (boolean slot : occupiedSlots) {
            if (slot) {
                count++;
            }
        }
        return count;
    }
}
