package model;

import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * This class will store the necessary information of each tile in the lot
 */
public class Tile {
    private String id;
    private long startSec;
    private long startMin;
    private long time_start;
    private long time_now;
    private String status;
    private String aStatus = "";
    private String seedContent = null;
    private int productQty;
    private String growth = "";
    private int waterCount = 0;
    private int waterBonusCount = 0;
    private int fertilizerCount = 0;
    private int fertilizerBonusCount = 0;
    private String loading_1 = "";
    private String loading_2 = "";
    private int fertilizerMax = -1;
    private int fertilizerBonusMax = -1;
    private int waterMax = -1;
    private int waterBonusMax = -1;
    private Main m = new application.Main();

    /**
     * This constructor sets the id of the Tile so that it can be easily identified
     * @param i Column number
     * @param d Row number
     * @param status Status of the current Tile
     */
    public Tile (int i, int d, String status)
    {
        this.status = status;
        this.id = "" + i + "-" + d;
    }

    /**
     * This method will "water the tile" and adds to the count accordingly
     * @param waterMax Water needed by the Seed planted
     * @param waterBonusMax Water Bonus of the Seed planted
     * @return True or False according to the success of execution of the function
     */
    public boolean water (int waterMax, int waterBonusMax)
    {
        this.waterMax = waterMax;
        this.waterBonusMax = waterBonusMax;
        if (waterMax == -1)
        {
            waterCount++;
            return true;
        }
        else if (seedContent != null && waterMax != -1)
        {
            aStatus = "Watered";
            if (waterCount < waterMax)
            {
                waterCount++;
                return true;
            }
            else if (waterCount >= waterMax)
                if (waterCount > waterMax)
                {
                    int m = waterCount -= waterMax;
                    waterBonusCount += m;
                }
                else if (waterBonusCount < waterBonusMax)
                {
                    waterBonusCount++;
                    return true;
                }
                else if (waterBonusCount == waterBonusMax)
                    return false;
        }
        return false;
    }

    /**
     * This method will "fertilize the tile" and adds to the count accordingly
     * @return True or False according to the success of execution of the function
     */
    public boolean fertilize ()
    {
        if (checkStatus() == 1 && checkSeedContents() == false)
        {
            aStatus = "Fertilized";
            fertilizerCount++;
            if (!((Fertilizer)m.getPlayer().getToolInventory().get(3)).use())
                return false;
        }
        return true;
    }

    /**
     * Updates the Current needed count and the bonus count of Fertilizer according to the Seed planted
     */
    private void updateFertilizerCount()
    {
        if (fertilizerCount > fertilizerMax) {
            if (fertilizerCount > fertilizerMax) {
                int g = fertilizerCount -= fertilizerMax;
                if (fertilizerBonusCount < fertilizerBonusMax)
                    fertilizerBonusCount += g;
                if (fertilizerBonusCount > fertilizerBonusMax) {
                    fertilizerBonusCount -= fertilizerBonusMax;
                }
            }
        }
    }

    /**
     * This method will "plow the tile"
     */
    public void plow ()
    {
        Seed s = findSeed(seedContent + "");

        if (status.equals("Plowed") && seedContent != null)
        {
            Alert p = new Alert(Alert.AlertType.CONFIRMATION);
            p.setTitle("My Farm Server");
            p.setHeaderText("You are REMOVING the plant!");
            p.setContentText("Are you sure you want to remove the \ncurrent WITHERED seed? This will cost " +  (s.getCost() * 0.1) + " My Farm Coins.");
            Optional<ButtonType> result = p.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                m.getPlayer().plowWithered((s.getCost() * 0.1));
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                System.out.println("Chose OK");
                a.setTitle("My Farm Server");
                a.setHeaderText("SUCCESS!!!");
                a.setContentText("You have Removed the seed.");
                a.showAndWait();
                status = "Unplowed";
                reset();
            }
            else
            {
                System.out.println("Plowing Cancelled by the User");
            }
        }
        else if (status.equals("Unplowed"))
            status = "Plowed";
    }

    /**
     * This method will "clear the tile"
     */
    public void clearRock()
    {
        status = "Unplowed";
    }

    /**
     * This method will "plant a seed" and updates accordingly
     * @param seedname Name of the seed to be planted
     * @return If the seed is a tree or not
     */
    public int plant(String seedname)
    {
        seedContent = seedname;
        Seed s = null;
        s = findSeed(seedname);
        fertilizerBonusMax = s.getFertilizerBonus();
        fertilizerMax = s.getFertilizerNeeded();
        if (s.getType().equals("Fruit Tree"))
            return 1;
        else
        {
            return 0;
        }
    }

    /**
     * This method will "Harvest the crop" when the crop is ready for harvest and adds to the player's OC accordingly
     * @param plant Name of the crop
     */
    public void harvest(String plant)
    {
        Seed p = findSeed(plant + "");

        productQty = p.getNumProducts();

        double sp = 0;

        if (p.getType() == "Flower")
            sp = (productQty * (sp + m.getPlayer().getLevel() + p.getBasePrice() + waterBonusCount + fertilizerBonusCount + (0.05 * (p.getBasePrice() + waterBonusCount + fertilizerBonusCount))));
        else
            sp = productQty * (sp + m.getPlayer().getLevel() + p.getBasePrice() + waterBonusCount + fertilizerBonusCount);

        m.getPlayer().sell(sp);
        Alert profit = new Alert(Alert.AlertType.INFORMATION);
        profit.setTitle("My Farm Server");
        profit.setHeaderText("You have sold the Harvested Crops!");
        profit.setContentText("You have gained a profit of: " + (sp - p.getCost()) + " My Farm Coins!");
        profit.showAndWait();
    }

    /**
     * This method will check if the Fertilizer count was met
     */
    private boolean checkFertilizerCond()
    {
        if (fertilizerCount >= fertilizerMax)
            return true;
        else
            return false;
    }

    /**
     * This method will check if the Water count was met
     */
    private boolean checkWaterCond()
    {
        if (waterCount >= waterMax)
            return true;
        else
            return false;
    }

    /**
     * This method will check the status of the tile
     */
    public int checkStatus ()
    {
        if (status.equals("Rocky"))
            return -1;
        else if (status.equals("Plowed"))
            return 1;
        else if (status.equals("Unplowed"))
            return 0;
        else
            return 99;
    }

    /**
     * This method will check the Astatus of the tile
     */
    public int checkAStatus()
    {
        if (aStatus == null)
            return 0;
        else if (aStatus.equals("Fertilized"))
            return 1;
        else if (aStatus.equals("Watered"))
            return 2;
        else
            return 99;
    }

    /**
     * This method will check if the seed contents of the tile
     */
    public boolean checkSeedContents()
    {
        if (seedContent == null)
            return false;
        else
            return true;
    }

    /**
     * This method will check if the growth status of the crop
     */
    public int checkGrowth()
    {
        if (growth.equals("Ready"))
            return 1;
        else if (growth.equals("Withered"))
            return 0;
        else if (growth.equals("Growing"))
            return 2;
        else
            return -1;
    }

    /**
     * This method will keep checking if the crop has Grown or Wilted
     * @param plant
     */
    public String grow(String plant)
    {
        String h = plant + " Seed";
        Seed p = findSeed(h + "");

        long time_need = convertTimeInSec(p.getHarvestTime());
        time_start = convertTimeInSec(startMin) + startSec;
        time_now = convertTimeInSec(m.getMin()) + m.getSec();

        if (time_now < (time_need + time_start))
        {
            growth = "Growing";
            loading_1 = "growing";
            return "growing";
        }
        else if ((time_now >= (time_need + time_start)) && (time_now <= (time_need + time_start + 60)))
        {
            if (checkFertilizerCond() && checkWaterCond())
            {
                growth = "Ready";
                loading_1 = "ready";
                loading_2 = plant.toLowerCase();
                return "ready";
            }
            else if (!checkFertilizerCond() || !checkWaterCond())
            {
                growth = "Withered";
                loading_1 = "withered";
                return "withered";
            }
        }
        else if ((time_now > (time_need + time_start + 60)) && (time_now < ((time_need * 2) + time_start + 60)))
        {
            if (seedContent == null && growth.equals("Withered"))
            {
                growth = "";
                loading_1 = "";
                return "";
            }
            else
            {
                growth = "Withered";
                loading_1 = "withered";
                return "withered";
            }

        }
        else if (time_now == ((time_need * 2) + time_start + 60))
        {
            reset();
            status = "Unplowed";
            loading_1 = "unplowed";
            return "unplowed";
        }

        return "";
    }

    /**
     * This method returns the Seed counterpart of the name of the seed when found
     * @param name Name of the Seed
     * @return Seed counterpart
     */
    public Seed findSeed(String name)
    {
        for(int i = 0; i < m.getPlayer().getSeeds().size(); i++)
        {
            if (m.getPlayer().getSeeds().get(i).getName().equals(name))
                return m.getPlayer().getSeeds().get(i);
        }
        return null;
    }

    /**
     * This method will convert all Time to Seconds
     * @param sec The time in minutes
     * @return The value of the minutes in Seconds
     */
    public long convertTimeInSec(double sec)
    {
        return (long)(sec * 60);
    }

    /**
     * This method will reset all values to default if harvested or plowed
     */
    public void reset()
    {
        waterCount = 0;
        waterBonusCount = 0;
        fertilizerBonusCount = 0;
        fertilizerCount = 0;
        startMin = 0;
        startSec = 0;
        productQty = 0;
        time_start = 0;
        time_now = 0;
        seedContent = null;
        aStatus = null;
        growth = null;
        loading_1 = null;
        loading_2 = null;
    }

    /**
     * This method will set the starting time of the seed form when it was planted
     */
    public void setTime()
    {
        startSec = m.getSec();
        startMin = m.getMin();
    }

    /**
     * This method will set the seedcontent to null
     */
    public void setBlankSeedContent (){this.seedContent = null;}

    public void setStatus (String n)
    {
        this.status = n;
    }

    public void setGrowth(String g) {
        this.growth = g;
    }

    public String getStatus() {
        return status;
    }

    public String getSeedContent() {
        return seedContent;
    }

    public void setSeedContent(String seedContentnew)
    {
        this.seedContent = seedContentnew;
    }

    public String getGrowth() {
        return growth;
    }

    public long getTime_start()
    {
        return time_start;
    }

    public long getTime_now()
    {
        return time_now;
    }

    public String getLoading_1() {
        return loading_1;
    }

    public String getLoading_2() {
        return loading_2;
    }

    public String toString ()
    {
        return "Tile " + id + "\nType of Tile: " + status + "\nSeed Content: " + seedContent +
                "\nWater Count: " + waterCount + "\nWater Bonus Count: " + waterBonusCount +
                "\nFertilizer Count: " + fertilizerCount +
                "\nStatus: " + growth + "\nTime Start: " + startMin + ":" + startSec;
    }
}
