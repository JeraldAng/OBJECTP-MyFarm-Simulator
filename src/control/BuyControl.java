package control;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Seed;
import application.Main;

import java.util.Optional;

import static view.BuyView.*;

/**
 * This class will hold all controls related to the BuyView
 */
public class BuyControl {
    private Main m = new application.Main();

    /**
     * This method will update the total prices of the seeds bought by the user
     * @param seed Type of seed
     * @param qty Amount of seeds
     */
    public double updateTotalPrice(Seed seed, int qty)
    {
        return seed.getCost() * qty;
    }

    /**
     * This method will update the total prices of the fertilizer/s bought by the user
     * @param qty Amount of fertilizers
     */
    public double updateTotalPrice(int qty)
    {
        int p = 10;
        switch (m.getPlayer().getFarmerType()){
            case "Registered Farmer":
                p -= 2;
                break;
            case "Distinguished Farmer":
                p -= 3;
                break;
            case "Honorable Farmer":
                p -= 5;
                break;
        }
        return p * qty;

    }

    /**
     * This method will update the quantity values of the BuyView
     * @param seed Type of Seed
     * @param qty Amount of Seeds
     * @param value Determining which variable to update
     */
    public void updateValues (String seed, int qty, int value)
    {
        temp = seed;

        System.out.println(temp);
        System.out.println(qty);
        switch (value) {
            case 0:
                vegQty = qty;
                break;
            case 1:
                flwrQty = qty;
                break;
            case 2:
                ftQty = qty;
                break;
            case 3:
                otherQty = qty;
                break;
            default:
                System.out.println("Error Setting Quantity");
        }
    }

    /**
     * This method will display a confirmation alert to confirm the purchase of the user
     * @param value Determining which variable to update
     * @param price Total price of the items bought
     */
    public boolean buyAlert (int value, double price)
    {
        int t = 0;

        switch (value) {
            case 0:
                t = vegQty;
                break;
            case 1:
                t = flwrQty;
                break;
            case 2:
                t = ftQty;
                break;
            case 3:
                t = otherQty;
                break;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);


        alert.setTitle("OBJECTP Market and Service");
        alert.setHeaderText("You are buying the following: ");
        alert.setContentText(temp + "\tx" + t + " for " + price + " My Farm Coins");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            return true;
        }
        else
        {
            System.out.println("Buying Cancelled by the User");
            return false;
        }

    }

    /**
     * This method displays the alert when the user confirms the purchase
     * @param i Amount of items bought by the user
     */
    public void confirm (int i)
    {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        System.out.println("Chose OK");
        alert1.setTitle("OBJECTP Market and Service");
        alert1.setHeaderText(null);
        alert1.setContentText("You have Purchased: " + i + "x " + temp);
        alert1.showAndWait();
    }

    /**
     * This method displays the error when the user has not selected a choice in the combo box
     */
    public void buyError()
    {
        String p = "";

        if (temp == "" || temp == null)
            p = "Please Select an Item";
        else
            p = "This Should Not Display";

        Alert alert2 = new Alert(Alert.AlertType.ERROR);
        alert2.setTitle("OBJECTP Market and Service");
        alert2.setContentText(p);
        alert2.showAndWait();
    }

    /**
     * This method displays the error in buying when the user bought more than the limit
     * @param i Amount of items bought by the user
     */
    public void boughtError(int i)
    {
        String item = "";
        if (temp.contains("seed"))
        item = "seed";
        else
        item = "item";
        Alert alert0 = new Alert(Alert.AlertType.ERROR);
        alert0.setTitle("OBJECTP Market and Service");
        alert0.setHeaderText("OH NO! CANNOT store more than 99 per " + item);
        alert0.setContentText("You are trying to buy: " + i + "x " + temp);
        alert0.showAndWait();
    }

    /**
     * This method displays the error in buying when the user cannot purchase an item due to insufficient funds
     */
    public void moneyError ()
    {
        Alert money = new Alert(Alert.AlertType.ERROR);
        money.setTitle("OBJECTP Market and Service");
        money.setHeaderText("OH NO! You don't have enough MY FARM COINS!");
        money.setContentText("Harvest crops to earn more MY FARM COINS!");
        money.showAndWait();
    }

    /**
     * This method displays the error in buying when the user inputs a value that is less than 0 or an invalid value
     * @param index Determines on which error to display
     */
    public void qtyError(int index)
    {
        switch (index)
        {
            case -1:
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("OBJECTP Market and Service");
                alert3.setContentText("Error! The value should be a VALID INTEGER");
                alert3.showAndWait();
                break;
            case 0:
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("OBJECTP Market and Service");
                alert4.setContentText("Error! The value should be GREATER THAN 0");
                alert4.showAndWait();
                break;
        }

    }
}
