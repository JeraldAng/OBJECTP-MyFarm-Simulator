package view;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import control.BuyControl;
import model.Fertilizer;
import model.Player;
import model.Seed;
import model.Tools;

import java.util.ArrayList;

public class BuyView {
    @FXML Label lbl_coins;
    @FXML Label lbl_vegPrices;
    @FXML Label lbl_flwrPrices;
    @FXML Label lbl_ftPrices;
    @FXML Label lbl_otherPrices;
    @FXML Button btn_menu;
    @FXML Button btn_vegBuy;
    @FXML Button btn_flwrBuy;
    @FXML Button btn_ftBuy;
    @FXML Button btn_otherBuy;
    @FXML ComboBox cmb_vegChoice;
    @FXML ComboBox cmb_flwrChoice;
    @FXML ComboBox cmb_ftChoice;
    @FXML ComboBox cmb_otherChoice;
    @FXML TextField txt_vegQty;
    @FXML TextField txt_flwrQty;
    @FXML TextField txt_ftQty;
    @FXML TextField txt_otherQty;
    @FXML Tab tab_veg;
    @FXML Tab tab_flwr;
    @FXML Tab tab_ft;
    @FXML Tab tab_other;
    @FXML ImageView img_shopBG;
    @FXML ImageView img_shopTopBG;

    public static int vegQty = 1;
    public static int flwrQty = 1;
    public static int ftQty = 1;
    public static int otherQty = 1;
    public static String temp = "";
    private BuyControl b;

    // pass a variable to update the quantity
    private Main m;

    public void initialize ()
    {
        ObservableList<String> veggy = FXCollections.observableArrayList();
        ArrayList<Integer> vegprice = new ArrayList<>();
        ObservableList<String> flwr = FXCollections.observableArrayList();
        ArrayList<Integer> flwrprice = new ArrayList<>();
        ObservableList<String> ft = FXCollections.observableArrayList();
        ArrayList<Integer> ftprice = new ArrayList<>();
        ObservableList<String> other = FXCollections.observableArrayList();

        m = new Main();
        b = new BuyControl();

        AnimationTimer update = new AnimationTimer() {
            @Override
            public void handle(long now) {
                lbl_coins.setText(String.valueOf(m.getPlayer().getCoins()));
            }
        };
        update.start();


        img_shopTopBG.setImage(new Image(getClass().getResourceAsStream("/images/shop_top_bG.png")));
        img_shopBG.setImage(new Image(getClass().getResourceAsStream("/images/shop_bg.png")));

        for (int i = 0; i < m.getPlayer().getSeeds().size(); i++)
        {
            if (m.getPlayer().getSeeds().get(i).getType().equals("Vegetable"))
            {
                veggy.add(m.getPlayer().getSeeds().get(i).getName());
                vegprice.add(m.getPlayer().getSeeds().get(i).getCost());
            }
            else if (m.getPlayer().getSeeds().get(i).getType().equals("Flower"))
            {
                flwr.add(m.getPlayer().getSeeds().get(i).getName());
                flwrprice.add(m.getPlayer().getSeeds().get(i).getCost());
            }
            else if (m.getPlayer().getSeeds().get(i).getType().equals("Fruit Tree"))
            {
                ft.add(m.getPlayer().getSeeds().get(i).getName());
                ftprice.add(m.getPlayer().getSeeds().get(i).getCost());
            }
        }

        other.add(m.getPlayer().getToolInventory().get(3).name);

        cmb_vegChoice.setItems(veggy);
        cmb_flwrChoice.setItems(flwr);
        cmb_ftChoice.setItems(ft);
        cmb_otherChoice.setItems(other);

        cmb_vegChoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayVegPrice();
            }
        });

        cmb_flwrChoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayFlwrPrice();
            }
        });

        cmb_ftChoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayFtPrice();
            }
        });

        cmb_otherChoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayOtherPrice();
            }
        });

        System.out.println(temp);

        // This button returns to the main menu of the user
        btn_menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Main Menu");
                try {
                    m.changeView("/view/UserMain.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_vegBuy.setOnAction(new EventHandler<ActionEvent>() {
            boolean bought;
            @Override
            public void handle(ActionEvent event) {
                int x = 0;
                System.out.println("Clicked on Buy in Vegetable");

                if (!(txt_vegQty.getText().equals("")))
                    try
                    {
                        x = Integer.parseInt(txt_vegQty.getText());
                    }
                    catch(NumberFormatException e){
                        x = -1;
                }
                else if (txt_vegQty.getText().equals(""))
                    x = 1;
                if (x >= 1)
                {
                    if (!(cmb_vegChoice.getSelectionModel().isEmpty()))
                    {
                        b.updateValues(cmb_vegChoice.getSelectionModel().getSelectedItem().toString(), x , 0);
                        System.out.println(temp);
                        System.out.println(x);

                        Seed s = m.getPlayer().getSeeds().get(0);
                        for (int i = 0; i < m.getPlayer().getSeeds().size(); i++)
                            if (m.getPlayer().getSeeds().get(i).getName().equals(cmb_vegChoice.getSelectionModel().getSelectedItem().toString()))
                                s = m.getPlayer().getSeeds().get(i);
                        double tp = b.updateTotalPrice(s, vegQty);
                        // Alert Screen to confirm purchase
                        boolean j = b.buyAlert(0, tp);
                        if (j == true) {
                            if ((s.getQty() + vegQty) <= 99)
                                if (tp <= m.getPlayer().getCoins()) {
                                    m.getPlayer().buy(tp, s, vegQty);
                                    b.confirm(x);
                                }
                                else
                                    b.moneyError();
                            else
                                b.boughtError(vegQty);
                        }
                    }
                    else if (cmb_vegChoice.getSelectionModel().isEmpty())
                    {
                        b.buyError();
                    }
                    else
                        System.out.println("Kill me Now");
                }
                else if (x == -1)
                    b.qtyError(x);
                else if (x <= 0)
                    b.qtyError(x);
                for (int k = 0; k < m.getPlayer().getSeedInventory().size(); k++)
                {
                    System.out.println(m.getPlayer().getSeedInventory().get(k).getName() + " :" + m.getPlayer().getSeedInventory().get(k).getQty());
                }
            }
        });

        btn_flwrBuy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = 0;
                System.out.println("Clicked on Buy in Flower");

                if (!(txt_flwrQty.getText().equals("")))
                    try
                    {
                        x = Integer.parseInt(txt_flwrQty.getText());
                    }
                    catch(NumberFormatException e){
                        x = -1;
                    }
                else if (txt_flwrQty.getText().equals(""))
                    x = 1;
                if (x >= 1)
                {
                    if (!(cmb_flwrChoice.getSelectionModel().isEmpty()))
                    {
                        b.updateValues(cmb_flwrChoice.getSelectionModel().getSelectedItem().toString(), x , 1);
                        // Alert Screen to confirm purchase
                        Seed s = m.getPlayer().getSeeds().get(0);
                        for (int i = 0; i < m.getPlayer().getSeeds().size(); i++)
                            if (m.getPlayer().getSeeds().get(i).getName().equals(cmb_flwrChoice.getSelectionModel().getSelectedItem().toString()))
                                s = m.getPlayer().getSeeds().get(i);
                        double tp = b.updateTotalPrice(s, flwrQty);
                        boolean j = b.buyAlert(1, tp);
                        if (j == true) {
                            if ((s.getQty() + flwrQty) <= 99)
                                if (tp <= m.getPlayer().getCoins()) {
                                    m.getPlayer().buy(tp, s, flwrQty);
                                    b.confirm(x);
                                }
                                else
                                    b.moneyError();
                            else
                                b.boughtError(flwrQty);
                        }
                    }
                    else if (cmb_flwrChoice.getSelectionModel().isEmpty())
                    {
                        b.buyError();
                    }
                    else
                        System.out.println("Kill me Now");
                }
                else if (x == -1)
                    b.qtyError(x);
                else if (x <= 0)
                    b.qtyError(x);
                for (int k = 0; k < m.getPlayer().getSeedInventory().size(); k++)
                {
                    System.out.println(m.getPlayer().getSeedInventory().get(k).getName() + " :" + m.getPlayer().getSeedInventory().get(k).getQty());
                }
            }
        });

        btn_ftBuy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = 0;
                System.out.println("Clicked on Buy in Fruit Tree");

                if (!(txt_ftQty.getText().equals("")))
                    try
                    {
                        x = Integer.parseInt(txt_ftQty.getText());
                    }
                    catch(NumberFormatException e){
                        x = -1;
                    }
                else if (txt_ftQty.getText().equals(""))
                    x = 1;
                if (x >= 1)
                {
                    if (!(cmb_ftChoice.getSelectionModel().isEmpty()))
                    {
                        b.updateValues(cmb_ftChoice.getSelectionModel().getSelectedItem().toString(), x, 2);
                        // Alert Screen to confirm purchase
                        Seed s = m.getPlayer().getSeeds().get(0);
                        for (int i = 0; i < m.getPlayer().getSeeds().size(); i++)
                            if (m.getPlayer().getSeeds().get(i).getName().equals(cmb_ftChoice.getSelectionModel().getSelectedItem().toString()))
                                s = m.getPlayer().getSeeds().get(i);
                        double tp = b.updateTotalPrice(s, ftQty);
                        boolean j = b.buyAlert(2, tp);
                        if (j == true) {
                            if ((s.getQty() + ftQty) <= 99)
                                if (tp <= m.getPlayer().getCoins()) {
                                    m.getPlayer().buy(tp, s, ftQty);
                                    b.confirm(x);
                                }
                                else
                                    b.moneyError();
                            else
                                b.boughtError(ftQty);
                        }
                    }
                    else if (cmb_ftChoice.getSelectionModel().isEmpty())
                    {
                        b.buyError();
                    }
                    else
                        System.out.println("Kill me Now");
                }
                else if (x == -1)
                    b.qtyError(x);
                else if (x <= 0)
                    b.qtyError(x);
                for (int k = 0; k < m.getPlayer().getSeedInventory().size(); k++)
                {
                    System.out.println(m.getPlayer().getSeedInventory().get(k).getName() + " :" + m.getPlayer().getSeedInventory().get(k).getQty());
                }
            }
        });

        btn_otherBuy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = 0;
                System.out.println("Clicked on Buy in Other");

                if (!(txt_otherQty.getText().equals("")))
                    try
                    {
                        x = Integer.parseInt(txt_otherQty.getText());
                    }
                    catch(NumberFormatException e){
                        x = -1;
                    }
                else if (txt_otherQty.getText().equals(""))
                    x = 1;
                if (x >= 1)
                {
                    if (!(cmb_otherChoice.getSelectionModel().isEmpty()))
                    {
                        b.updateValues(cmb_otherChoice.getSelectionModel().getSelectedItem().toString(), x, 3);
                        // Alert Screen to confirm purchase
                        double tp = b.updateTotalPrice(otherQty);
                        boolean j = b.buyAlert(3, tp);
                        if (j == true)
                            if ((((Fertilizer)m.getPlayer().getToolInventory().get(3)).getQty() + otherQty) <= 99)
                                if (tp <= m.getPlayer().getCoins()) {
                                    m.getPlayer().buys(tp, otherQty);
                                    b.confirm(x);
                                }
                                else
                                    b.moneyError();
                            else
                                b.boughtError(otherQty);
                    }
                    else if (cmb_otherChoice.getSelectionModel().isEmpty())
                    {
                        b.buyError();
                    }
                    else
                        System.out.println("Kill me Now");
                }
                else if (x == -1)
                    b.qtyError(x);
                else if (x <= 0)
                    b.qtyError(x);

                System.out.println(((Fertilizer)m.getPlayer().getToolInventory().get(3)).getName() + ": " +((Fertilizer)m.getPlayer().getToolInventory().get(3)).getQty());
            }
        });
    }

    public void displayVegPrice ()
    {
        int cost = 0;

        for (int i = 0; i < m.getPlayer().getSeeds().size(); i++)
            if (cmb_vegChoice.getValue().toString() != null)
                if (m.getPlayer().getSeeds().get(i).getName().equals(cmb_vegChoice.getValue().toString()))
                     cost = m.getPlayer().getSeeds().get(i).getCost();
        lbl_vegPrices.setText(cmb_vegChoice.getValue().toString() + " = " + cost + " My Farm Coins");
    }

    public void displayFlwrPrice()
    {
        int cost1 = 0;
        for (int j = 0; j < m.getPlayer().getSeeds().size(); j++)
            if (cmb_flwrChoice.getValue().toString() != null)
                if (m.getPlayer().getSeeds().get(j).getName().equals(cmb_flwrChoice.getValue().toString()))
                  cost1 = m.getPlayer().getSeeds().get(j).getCost();
        lbl_flwrPrices.setText(cmb_flwrChoice.getValue().toString() + " = " + cost1 + " My Farm Coins");
    }

    public void displayFtPrice()
    {
        int cost2 = 0;
        for (int k = 0; k < m.getPlayer().getSeeds().size(); k++)
            if (cmb_ftChoice.getValue().toString() != null)
                if (m.getPlayer().getSeeds().get(k).getName().equals(cmb_ftChoice.getValue().toString()))
                  cost2 = m.getPlayer().getSeeds().get(k).getCost();
        lbl_ftPrices.setText(cmb_ftChoice.getValue().toString() + " = " + cost2 + " My Farm Coins");
    }

    public void displayOtherPrice()
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

        lbl_otherPrices.setText(cmb_otherChoice.getValue().toString() + " = " + p + " My Farm Coins");
    }
}
