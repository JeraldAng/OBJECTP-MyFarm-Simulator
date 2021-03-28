package view;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import application.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class MainView {
    @FXML Label lbl_name;
    @FXML Label lbl_level;
    @FXML Label lbl_coins;
    @FXML Label lbl_farmertype;
    @FXML Label lbl_lotTime;
    @FXML Button btn_buy;
    @FXML Button btn_lot;
    @FXML Button btn_reg;
    @FXML ImageView img_userMainBG;
    @FXML Rectangle rec_exp;

    private Main m;

    public void initialize ()
    {
        m = new application.Main();
        img_userMainBG.setImage(new Image(getClass().getResourceAsStream("/images/bg_user.jpg")));
        m.setTitle(m.getPlayer().getUsername() + "'s Farm");
        lbl_name.setText(m.getPlayer().getUsername() + ":");
        lbl_level.setText("Level " + m.getPlayer().getLevel());
        lbl_coins.setText(String.valueOf(m.getPlayer().getCoins()));
        lbl_farmertype.setText(m.getPlayer().getFarmerType());
        rec_exp.setWidth(140 * ((m.getPlayer().getExp() / m.getPlayer().getNxtlvl())));

        AnimationTimer time = new AnimationTimer() {
            public void handle(long now) {
                if (m.getMin() < 10)
                {
                    if (m.getSec() < 10)
                        lbl_lotTime.setText("0" + m.getMin() + " minutes 0" + m.getSec() + " seconds");
                    else if (m.getSec() >= 10)
                        lbl_lotTime.setText("0" + m.getMin() + " minutes " + m.getSec() + " seconds");
                }
                else if (m.getMin() >= 10)
                {
                    if (m.getSec() < 10)
                        lbl_lotTime.setText(m.getMin() + " minutes 0" + m.getSec() + " seconds");
                    else if (m.getSec() >= 10)
                        lbl_lotTime.setText(m.getMin() + " minutes " + m.getSec() + " seconds");
                }
                lbl_lotTime.setAlignment(Pos.CENTER);
                lbl_level.setText("Level " + m.getPlayer().getLevel());
            }
        };
        time.start();

        btn_buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Buy");
                try{
                    m.changeView("/view/UserBuy.fxml");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        btn_lot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked My Lot");
                try{
                    m.changeView("/view/UserLot.fxml");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //System.out.println("Minutes: " + m.getMin() + " Seconds: " + m.getSec());
            }
        });

        btn_reg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Register");
                try{
                    m.changeView("/view/UserRegister.fxml");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
