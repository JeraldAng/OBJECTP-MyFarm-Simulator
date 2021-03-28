package model;

import java.util.Random;

/**
 * This class stores the details of a Tulip Seed
 */
public class Tulip extends Seed{
    /**
     * This constructor stores all given details of the seeds as indicated in the specification
     * @param name Name of the Seed
     * @param type Type of the Seed
     * @param products Name of the Product produced
     * @param cost Cost of the Seed
     * @param harvestTime Harvest Time of the Seed
     * @param waterNeeded Water Needed by the Seed
     * @param waterBonus Water Bonus of the Seed
     * @param fertilizerBonus Fertilizer Bonus of the Seed
     * @param fertilizerNeeded Fertilizer need by the Seed
     * @param harvestCost Harvest Cost of the Seed
     * @param basePrice Base Selling Price of the Seed
     */
    public Tulip(String name, String type, String products, int cost, double harvestTime, int waterNeeded, int waterBonus,
                  int fertilizerBonus, int fertilizerNeeded, int harvestCost, double basePrice)
    {
        super(name, type, products, cost, harvestTime, waterNeeded, waterBonus, fertilizerBonus, fertilizerNeeded, harvestCost, basePrice);
    }

    /**
     * This method returns a randomized value of the products produced
     * @return Products Produced
     */
    @Override
    public int getNumProducts()
    {
        Random r = new Random ();
        int pp = r.nextInt(1)+1;
        return pp;
    }
}
