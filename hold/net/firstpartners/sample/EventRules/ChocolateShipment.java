/**
 * 
 * (c) Paul Browne, FirstPartners.net
 * Contains sample code from FIT and Drools
 */
package net.firstpartners.sample.EventRules;


public class ChocolateShipment {

	private long shipmentAmount;
	private OoompaLoompaDate shipmentDate;
	private long itemsStillToShip;


	public ChocolateShipment(){}
	public ChocolateShipment(long shipmentQuantity){
		super();
		setShipmentAmount(shipmentQuantity);
	}

	public OoompaLoompaDate getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(OoompaLoompaDate ooompaLoompaDate) {
		shipmentDate = ooompaLoompaDate;
	}



	@Override
	public String toString(){
		StringBuffer returnValue = new StringBuffer("Shipment amount:");
		returnValue.append(shipmentAmount);
		returnValue.append(" date:");
		returnValue.append(shipmentDate);
		returnValue.append(" chocolate bars left in order:");
		returnValue.append(itemsStillToShip);
		return returnValue.toString();
	}
	public long getShipmentAmount() {
		return shipmentAmount;
	}
	public void setShipmentAmount(long shipmentAmount) {
		this.shipmentAmount = shipmentAmount;
	}
	public long getItemsStillToShip() {
		return itemsStillToShip;
	}
	public void setItemsStillToShip(long itemsStillToShip) {
		this.itemsStillToShip = itemsStillToShip;
	}


}
