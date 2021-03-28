package model;

/**
 * This class will be a template for all seeds whereas all informations about the seeds will be stored in this abstract class
 */
public abstract class Seed {
    private String name;
    private String type;
    private String products; //type of product
    private int cost; //cost of seed
    private double harvestTime;
    private int waterNeeded;
    private int fertilizerNeeded;
    private int waterBonus;
    private int fertilizerBonus;
    private int harvestCost; //harvest cost
    private int numProducts; //number of product produced
    private double basePrice;
    private int qty = 0;

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
    public Seed (String name, String type, String products, int cost, double harvestTime, int waterNeeded, int waterBonus,
                 int fertilizerBonus, int fertilizerNeeded, int harvestCost, double basePrice)
    {
        this.name = name;
        this.type = type;
        this.products = products;
        this.cost = cost;
        this.harvestTime = harvestTime;
        this.waterNeeded = waterNeeded;
        this.waterBonus = waterBonus;
        this.fertilizerNeeded = fertilizerNeeded;
        this.fertilizerBonus = fertilizerBonus;
        this.harvestCost = harvestCost;
        this.basePrice= basePrice;
    }

    /**
     * This method will add the bought items in the shop in the quantity of each seed
     * @param qty Amount of seeds
     */
    public void addQty(int qty)
    {
        if (this.qty < 99)
            this.qty += qty;
    }

    /**
     * This method will deduct the quantity of the seed if the seed is "used" or planted
     */
    public int deductQty()
    {
        qty -= 1;
        return qty;
    }

    /**
     * This method will update the necessary details when the player is registered
     */
    public void updateValues(int price, int bonus, double percent)
    {
        basePrice += price;
        cost -= price;
        waterBonus += bonus;
        fertilizerBonus += bonus;
        harvestTime *= (1 - percent);
    }

    /**
     * This method can be implemented by other class and is the way to randomize the number of products produced
     * @return Products Produced
     */
    public abstract int getNumProducts();

    public String getName() {
        return name;
    }

    public String getProducts() {
        return products;
    }

    public int getCost() {
        return cost;
    }

    public double getHarvestTime() {
        return harvestTime;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public int getFertilizerNeeded() {
        return fertilizerNeeded;
    }

    public int getWaterBonus() {
        return waterBonus;
    }

    public int getFertilizerBonus() {
        return fertilizerBonus;
    }

    public int getHarvestCost() {
        return harvestCost;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getType() {
        return type;
    }

    public int getQty()
    {
        return qty;
    }

    @Override
    public String toString()
    {
        return name + "\nType: " + type + "\nHarvest Time: " + harvestTime +
                "\nWater Needed: " + waterNeeded + "\nWater Bonus: " + waterBonus +
                "\nFertilizer Needed: " + fertilizerNeeded + "\nFertilizer Bonus: " + fertilizerBonus +
                "\nHarvest Cost: " + harvestCost + "\nBase Selling Price: " + basePrice;
    }

}
