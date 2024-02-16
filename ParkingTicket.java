package BootCamp.MultiLevelParkingLot;

import java.util.Date;
import java.util.UUID;

public class ParkingTicket {
	
	 private String vehicleNumber;
	    private String vehicleType;
	    private int slotNumber;
	    private Date inTime;
	    private Date outTime;
	    private String uniqueID;

	    public ParkingTicket(String vehicleNumber, String vehicleType, int slotNumber, Date inTime) {
	        this.vehicleNumber = vehicleNumber;
	        this.vehicleType = vehicleType;
	        this.slotNumber = slotNumber;
	        this.inTime = inTime;
	        this.uniqueID=UUID.randomUUID().toString();
	    }

	    public String getVehicleNumber() {

			return vehicleNumber;
	    }

	    public String getVehicleType() {

			return vehicleType;
	    }

	    public int getSlotNumber() {


			return slotNumber;
	    }

	    public Date getInTime()
		{
	        return inTime;
	    }

	    public Date getOutTime() {

			return outTime;
	    }

	    public void setOutTime(Date outTime) {

			this.outTime = outTime;
	    }
	    
	    public String getUniqueID() {

			return uniqueID;
	    }

}
