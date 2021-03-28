package model;

/**
 * This class inherits methods from Tools and will only store the description and functions of the Fertilizer
 */
public class Fertilizer extends Tools {
    private int qty = 5;

    /**
     * This method inherits variables from Tools and will only store the description and functions of the Fertilizer
     */
    public Fertilizer() {
        super("Fertilizer", "This consumable fertilizes the selected plowed tile. Cannot be used when the tile is occupied.");
    }

    /**
     * This method decreases the count of the fertilizer when used
     */
    public boolean use()
    {
        if (qty >= 1)
        {
            qty--;
            return true;
        }

        else
            return false;

    }

    /**
     * This method increases the count of the fertilizer when the user bought from the shop
     * @param qty Amount of the Fertilizer
     */
    public void bought(int qty)
    {
        this.qty += qty;
    }

    public int getQty()
    {
        return qty;
    }
}
