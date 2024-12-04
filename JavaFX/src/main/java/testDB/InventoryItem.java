/*
Author: Dr. E
Date: 2024
Purpose: Instantiable Java class whose items will represent Inventory for a 
         grocery store.
*/

package testDB;

public class InventoryItem {
    public String itemName;
    public String itemDescription;
    public double itemCost;
    public int itemQty;
    
    public InventoryItem(String itemName, 
            String itemDescription, 
            double itemCost, 
            int itemQty)
    {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCost = itemCost;
        this.itemQty = itemQty;
    }

    @Override
    public String toString() {
        return String.format(
                "%-15s%-15s%-7.2f%d", 
                this.itemName, 
                this.itemDescription, 
                this.itemCost, 
                this.itemQty);
    }
    
    
}
