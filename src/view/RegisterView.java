package view;

import control.RegisterControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import application.Main;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class RegisterView {
    @FXML Button btn_registered;
    @FXML Button btn_distinguished;
    @FXML Button btn_honorable;
    @FXML Button btn_menu;
    @FXML Label lbl_farmertype;
    @FXML Label lbl_coins;
    @FXML Label lbl_level;
    @FXML Label lbl_userName;
    @FXML ImageView img_regBG;

    private Main m;
    private RegisterControl r;

    public void initialize (){
        m = new Main();
        r = new RegisterControl();

        img_regBG.setImage(new Image(getClass().getResourceAsStream("/images/bg_register.jpg")));
        lbl_userName.setText(m.getPlayer().getUsername() + ": ");
        lbl_level.setText("Level " + m.getPlayer().getLevel());
        lbl_coins.setText(String.valueOf(m.getPlayer().getCoins()));
        lbl_farmertype.setText(m.getPlayer().getFarmerType());

        if (m.getPlayer().getFarmerType() == "Registered Farmer") {
            btn_registered.setDisable(true);
            btn_registered.setText("Purchased!");
        }
        else if (m.getPlayer().getFarmerType() == "Distinguished Farmer") {
            btn_registered.setDisable(true);
            btn_distinguished.setDisable(true);
            btn_registered.setText("Purchased!");
            btn_distinguished.setText("Purchased!");
        }
        else if (m.getPlayer().getFarmerType() == "Honorable Farmer"){
            btn_registered.setDisable(true);
            btn_distinguished.setDisable(true);
            btn_honorable.setDisable(true);
            btn_registered.setText("Purchased!");
            btn_distinguished.setText("Purchased!");
            btn_honorable.setText("Purchased!");
        }

        // This button returns to the main menu of the user
        btn_menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Return to Menu");
                try {
                    m.changeView("/view/UserMain.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_registered.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Registered Farmer");
                    int choice = r.CheckRegistered();
                    if (choice == 1){
                        if (regConfirm()) {
                            r.ChangeFarmerType("Registered");
                            regAlert();
                        try {
                            m.changeView("/view/UserRegister.fxml");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }} else if (choice == 2)
                        coinError(200);
                    else if (choice == 3)
                        levelError(10);
                    else if (choice == 4)
                        typeError();
                }
        });

        btn_distinguished.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Distinguished Farmer");
                int choice = r.CheckDistinguished();
                if (choice == 1){
                    if (regConfirm()){
                        r.ChangeFarmerType("Distinguished");
                        regAlert();
                    try {
                        m.changeView("/view/UserRegister.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }} else if (choice == 2)
                    coinError(250);
                else if (choice == 3)
                    levelError(15);
                else if (choice == 4)
                    typeError();
            }
        });

        btn_honorable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Honorable Farmer");
                int choice = r.CheckHonorable();
                if (choice == 1){
                    if (regConfirm()){
                        r.ChangeFarmerType("Honorable");
                        regAlert();
                    try {
                        m.changeView("/view/UserRegister.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }} else if (choice == 2)
                    coinError(350);
                else if (choice == 3)
                    levelError(20);
                else if (choice == 4)
                    typeError();
            }
        });
    }

    public void regAlert () {
        Alert alertreg = new Alert(Alert.AlertType.INFORMATION);
        alertreg.setTitle("Registration Complete");
        alertreg.setHeaderText("Registration Successful");
        if (m.getPlayer().getFarmerType() == "Registered Farmer")
            alertreg.setContentText("Congratulations! \nEarning +2 \nBuying -2 \nHarvest Time -5%");
        else if (m.getPlayer().getFarmerType() == "Distinguished Farmer")
            alertreg.setContentText("Congratulations! \nEarning +3 \nBuying -3 \nHarvest Time -10% \nWater/Fertilizer Bonus Limits +1");
        else if (m.getPlayer().getFarmerType() == "Honorable Farmer")
            alertreg.setContentText("Congratulations! \nEarning +5 \nBuying -5 \nHarvest Time -15% \nWater/Fertilizer Bonus Limits +2");
        alertreg.showAndWait();
    }

    public boolean regConfirm ()
    {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

        confirm.setTitle("Farmer Registration");
        confirm.setHeaderText("Are you sure you want to register? ");
        if (m.getPlayer().getFarmerType() == "Farmer")
            confirm.setContentText("Benefits: \nEarning +2 \nBuying -2 \nHarvest Time -5%");
        else if (m.getPlayer().getFarmerType() == "Registered Farmer")
            confirm.setContentText("Benefits: \nEarning +3 \nBuying -3 \nHarvest Time -10% \nWater/Fertilizer Bonus Limits +1");
        else if (m.getPlayer().getFarmerType() == "Distinguished Farmer")
            confirm.setContentText("Benefits: \nEarning +5 \nBuying -5 \nHarvest Time -15% \nWater/Fertilizer Bonus Limits +2");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            return true;
        }
        else
        {
            System.out.println("Registering cancelled by the User");
            return false;
        }
    }



    public void levelError(int levelreq)
    {
        Alert levelalert = new Alert(Alert.AlertType.ERROR);
        levelalert.setTitle("Registration Problem");
        levelalert.setContentText("You must be level " + levelreq + " and above to register!");
        levelalert.showAndWait();
    }

    public void typeError ()
    {
        Alert typealert = new Alert(Alert.AlertType.ERROR);
        typealert.setTitle("Registration Problem");
        typealert.setContentText("You must register the preceding rank first!");
        typealert.showAndWait();
    }

    public void coinError (int coinreq)
    {
        Alert coinalert = new Alert(Alert.AlertType.ERROR);
        coinalert.setTitle("Registration Problem");
        if (coinreq - m.getPlayer().getCoins() == 1)
            coinalert.setContentText("You need " + (coinreq - m.getPlayer().getCoins()) + " more coin to register!");
        else
            coinalert.setContentText("You need " + (coinreq - m.getPlayer().getCoins()) + " more coins to register!");
        coinalert.showAndWait();
    }
}
