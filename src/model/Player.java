package model;
import javax.tools.Tool;
import java.util.*;

/**
 * This class contains the information that are stored inside of a user
 */
public class Player {
    private double nxtlvl;
    private String username;
    private double coins;
    private double exp;
    private int level;
    private String farmerType;
    private ArrayList<Seed> seeds;
    private ArrayList<Seed> seedInventory;
    private ArrayList<Tools> toolInventory;

    /**
     * This method will store the basic information that needs to be stored and sets all default values
     * @param name Name of the User
     */
    public Player (String name)
    {
        this.coins = 500;
        this.level = 10;
        this.exp = 0;
        this.nxtlvl = level * 5;
        this.farmerType = "Farmer";
        this.username = name;
        seeds = new ArrayList<>();
        seedInventory = new ArrayList<>();
        initiateSeeds(seeds);
        toolInventory = new ArrayList<>();
        addTool(toolInventory);
    }

    /**
     * This method will register the Player to a specific type and is called in line with the RegisterControl
     * @param newfarmerType Farmer Type to be Registered
     */
    public void register(String newfarmerType)
    {
        if (newfarmerType.equals("Registered"))
        {
            if (this.coins >= 200 && level >= 10 && this.farmerType.equals("Farmer")) {
                farmerType = "Registered Farmer";
                this.coins = coins - 200;
                updateBenefits();
            }
        }
        else if (newfarmerType.equals("Distinguished"))
        {
            if (this.coins >= 250 && level >= 15 && this.farmerType.equals("Registered Farmer")) {
                this.farmerType = "Distinguished Farmer";
                this.coins = coins - 250;
                updateBenefits();
            }
        }
        else if (newfarmerType.equals("Honorable"))
        {
            if (this.coins >= 350 && level >= 20 && this.farmerType.equals("Distinguished Farmer")) {
                this.farmerType = "Honorable Farmer";
                this.coins = coins - 350;
                updateBenefits();
            }
        }
    }

    /**
     * This adds to the inventory of the player when buying seeds from the shop
     * @param seed Type of Seed
     */
    public void addToInventory(Seed seed)
    {
        seedInventory.add(seed);
    }

    /**
     * This method will check if the player has bought the same type of seed and will update the inventory accordingly
     * @param price Price of each Items
     * @param seed Type of Seed
     * @param qty Amount of Seed
     */
    public void buy (double price, Seed seed, int qty)
    {
        coins -= price;
        if (seedInventory.size() == 0)
        {
            for (int i = 0; i < seeds.size(); i++)
            {
                if (seed.getName().equals(seeds.get(i).getName()))
                {
                    addToInventory(seeds.get(i));
                }
            }
            seedInventory.get(0).addQty(qty);
        }

        else if (seedInventory.size() > 0)
        {
            int in = -1;
            for (int i = 0; i < seedInventory.size(); i++)
            {
                if (seed.getName().equals(seedInventory.get(i).getName()))
                {
                    in = i;
                }
            }
            if (in == -1)
            {
                addToInventory(seed);
                seedInventory.get(seedInventory.size() - 1).addQty(qty);
            }
            else
            {
                seedInventory.get(in).addQty(qty);
            }
        }

    }

    /**
     * This method will update the quantity of the fertilizer if the user has bought fertilizer from the shop
     * @param price Price per item
     * @param qty Amount of items
     */
    public void buys (double price, int qty)
    {
        coins -= price;

        ((Fertilizer)toolInventory.get(3)).bought(qty);
    }

    /**
     * This method will charge the user the amount to plow a tile with a withered plant
     * @param price Total price
     */
    public void plowWithered(double price)
    {
        coins -= price;
    }

    /**
     * This method will add the total earnings of the user when harvesting a fully grown plant
     * @param price Total price
     */
    public void sell (double price)
    {
        coins += price;
    }

    /**
     * This method will check if the experience points are sufficient for the next level and will update accordingly
     */
    private void levelUp ()
    {
        if (exp >= nxtlvl)
        {
            level++;
            exp -= nxtlvl;
            nxtlvl = level * 5;
        }
    }

    /**
     * This method initiates the seeds that are defined in the specifications
     * @param s List of Seeds
     */
    private void initiateSeeds (ArrayList<Seed> s)
    {
        s.add(new Turnip("Turnip Seed",  "Vegetable","Turnip", 5, 1, 1, 2, 1, 0, 1, 6));
        s.add(new Carrot("Carrot Seed", "Vegetable", "Carrot", 10, 1.5, 1, 2, 1, 0, 1, 9));
        s.add(new Tomato("Tomato Seed", "Vegetable", "Tomato", 20, 2.5, 3, 4, 2, 1, 1, 15));
        s.add(new Potato("Potato Seed", "Vegetable", "Potato", 25, 5, 4, 5, 3, 2, 1, 13));
        s.add(new Rose("Rose Seed", "Flower", "Rose", 5, 1, 1, 2, 0, 1, 2, 5));
        s.add(new Tulip("Tulip Seed", "Flower", "Tulip", 7, 1.5, 2, 3, 0, 1, 2, 7));
        s.add(new Stargazer("Stargazer Seed", "Flower", "Stargazer", 10, 2.5, 2, 3, 0, 1, 2, 9));
        s.add(new Sunflower("Sunflower Seed", "Flower", "Sunflower", 20, 3.5, 2, 3, 1, 2, 2, 19));
        s.add(new Mango("Mango Seed", "Fruit Tree", "Mango", 50, 7, 7, 7, 4, 4, 3, 4));
        s.add(new Apple("Apple Seed", "Fruit Tree","Apple", 55, 7, 7, 7, 5, 5, 3, 3.5));
        s.add(new Banana("Banana Seed", "Fruit Tree", "Banana", 60, 8, 8, 8, 5, 5, 3, 3.5));
        s.add(new Orange("Orange Seed", "Fruit Tree", "Orange", 65, 8, 8, 8, 6, 6, 3, 4.5));
    }

    /**
     * This method will add the tools that are defined in the specifications
     * @param t List of Tools
     */
    private void addTool (ArrayList<Tools> t)
    {
        t.add(new WateringCan());
        t.add(new PickAxe());
        t.add(new PlowTool());
        t.add(new Fertilizer());
    }

    /**
     * This method updates the experience points according to the set amount while doing a specific task
     * @param amt Amount of Experience points gained
     */
    public void updateExp (double amt)
    {
        this.exp += amt;
        if (exp >= nxtlvl)
            levelUp();
    }

    /**
     * This method will call Seed.updateValues as to update the values according to the current farmer type of the player
     */
    public void updateBenefits ()
    {
        switch (farmerType)
        {
            case "Registered Farmer":
                for(int i = 0; i < seeds.size(); i++)
                {
                    seeds.get(i).updateValues(2, 0, 0.05);
                    System.out.println(seeds.get(i).toString());
                }
                break;
            case "Distinguished Farmer":
                for(int i = 0; i < seeds.size(); i++)
                {
                    seeds.get(i).updateValues(1, 1, 0.05);
                    System.out.println(seeds.get(i).toString());
                }
                break;
            case "Honorable Farmer":
                for(int i = 0; i < seeds.size(); i++)
                {
                    seeds.get(i).updateValues(2, 1, 0.05);
                    System.out.println(seeds.get(i).toString());
                }
                break;
        }
    }

    public double getCoins() {
        return coins;
    }

    public double getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }

    public String getFarmerType() {
        return farmerType;
    }

    public ArrayList<Seed> getSeeds ()
    {
        return seeds;
    }

    public double getNxtlvl() {
        return nxtlvl;
    }

    public ArrayList<Seed> getSeedInventory() {
        return seedInventory;
    }

    public ArrayList<Tools> getToolInventory() {
        return toolInventory;
    }
}
