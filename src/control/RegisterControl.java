package control;

import model.Player;
import application.Main;

/**
 * This class will display the errors found during registration
 */
public class RegisterControl {

    private Main m = new Main();

    /**
     * This method will check the current registration of the player and will return 1 if the player is eligible to register for Registered Farmer
     */
    public int CheckRegistered ()
    {
        if (m.getPlayer().getFarmerType().equals("Farmer"))
            if (m.getPlayer().getLevel() >= 10)
                if (m.getPlayer().getCoins() >= 200) {
                    return 1;
                }
                else
                    return 2;
            else
                return 3;
        else
            return 4;
        }

    /**
     * This method will check the current registration of the player and will return 1 if the player is eligible to register for Distinguished Farmer
     */
    public int CheckDistinguished ()
    {
        if (m.getPlayer().getFarmerType().equals("Registered Farmer"))
            if (m.getPlayer().getLevel() >= 15)
                if (m.getPlayer().getCoins() >= 250) {
                    return 1;
                }
                else
                    return 2;
            else
                return 3;
        else
            return 4;
    }

    /**
     * This method will check the current registration of the player and will return 1 if the player is eligible to register for Honorable Farmer
     */
    public int CheckHonorable ()
    {
        if (m.getPlayer().getFarmerType().equals("Distinguished Farmer"))
            if (m.getPlayer().getLevel() >= 20)
                if (m.getPlayer().getCoins() >= 350) {
                    return 1;
                }
                else
                    return 2;
            else
               return 3;
        else
            return 4;
    }

    /**
     * This method will change the farmertype according to the registration
     * @param type Type of Farmer
     */
    public void ChangeFarmerType (String type)
        {
            m.getPlayer().register(type);
        }

}
