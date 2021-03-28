package view;

import application.Main;
import control.LotControl;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LotView<player_lot> {
    @FXML Label lbl_name;
    @FXML Label lbl_level;
    @FXML Label lbl_coins;
    @FXML Label lbl_farmertype;
    @FXML Label lbl_selectedTool;
    @FXML Label lbl_lotTime;
    @FXML Label lbl_fertQty;
    @FXML Label lbl_display;
    @FXML Label lbl_noitems;
    @FXML Label lbl_info;
    @FXML Button btn_mainMenu;
    @FXML Button btn_select1;
    @FXML Button btn_select2;
    @FXML Button btn_select3;
    @FXML Button btn_select4;
    @FXML Button btn_Inventory;
    @FXML Button btn_Lot;
    @FXML Button btn_action;
    @FXML Button btn_harvest;
    @FXML Rectangle rec_exp;
    @FXML ImageView img_lotBG;
    @FXML ImageView img_select;
    @FXML ImageView img_sel1;
    @FXML ImageView img_sel2;
    @FXML ImageView img_sel3;
    @FXML ImageView img_sel4;
    @FXML ImageView img_frame;
    @FXML ListView List_inv;
    @FXML AnchorPane anchorPane_Lot;
    @FXML AnchorPane anchorPane_Inv;
    private Node[] but;
    private static String selected;
    private WateringCan w = new WateringCan();
    private PickAxe p = new PickAxe();
    private PlowTool pt = new PlowTool();
    private Fertilizer f = new Fertilizer();
    private GridPane PlayerLot = new GridPane();
    private List PlayerItem = new List();
    private Main m;
    private boolean x = true;

    public void initialize() {
        m = new Main();
        but = new Node[m.column * m.row];
        anchorPane_Inv.setVisible(false);
        PlayerLot = makeGrid(m.column, m.row);
        System.out.println("" + m.column + m.row);
        List PlayerItem = makeList();

        img_lotBG.setImage(new Image(getClass().getResourceAsStream("/images/lot_bg.png")));
        img_select.setImage(new Image(getClass().getResourceAsStream("/images/lot_select.png")));
        img_sel1.setImage(new Image(getClass().getResourceAsStream("/images/watering_can.png")));
        img_sel2.setImage(new Image(getClass().getResourceAsStream("/images/pick_axe.png")));
        img_sel3.setImage(new Image(getClass().getResourceAsStream("/images/plow_tool.png")));
        img_sel4.setImage(new Image(getClass().getResourceAsStream("/images/fertilizer.png")));
        img_frame.setImage(new Image(getClass().getResourceAsStream("/images/inv_frame.jpg")));
        lbl_name.setText(m.getPlayer().getUsername() + " :");
        lbl_coins.setText(String.valueOf(m.getPlayer().getCoins()));
        lbl_farmertype.setText(m.getPlayer().getFarmerType());
        lbl_level.setText("Level " + m.getPlayer().getLevel());
        lbl_selectedTool.setText("Selected Tool: " + selected);
        lbl_display.setVisible(false);
        lbl_noitems.setVisible(false);
        btn_action.setVisible(false);
        btn_harvest.setDisable(false);

        if (PlayerItem == null) {
            lbl_display.setText("You have no items!");
            lbl_noitems.setVisible(true);
            lbl_info.setVisible(false);
        }
        System.out.println(((m.getPlayer().getExp() / m.getPlayer().getNxtlvl())));

        AnimationTimer time = new AnimationTimer() {
            public void handle(long now) {
                lbl_fertQty.setText("x " + ((Fertilizer) m.getPlayer().getToolInventory().get(3)).getQty());
                lbl_fertQty.setAlignment(Pos.BOTTOM_RIGHT);
                if (m.getMin() < 10) {
                    if (m.getSec() < 10)
                        lbl_lotTime.setText("0" + m.getMin() + " minutes 0" + m.getSec() + " seconds");
                    else if (m.getSec() >= 10)
                        lbl_lotTime.setText("0" + m.getMin() + " minutes " + m.getSec() + " seconds");
                } else if (m.getMin() >= 10) {
                    if (m.getSec() < 10)
                        lbl_lotTime.setText(m.getMin() + " minutes 0" + m.getSec() + " seconds");
                    else if (m.getSec() >= 10)
                        lbl_lotTime.setText(m.getMin() + " minutes " + m.getSec() + " seconds");
                }
                lbl_lotTime.setAlignment(Pos.CENTER);
                lbl_level.setText("Level " + m.getPlayer().getLevel());
                lbl_coins.setText(String.valueOf(m.getPlayer().getCoins()));
                rec_exp.setWidth(140 * ((m.getPlayer().getExp() / m.getPlayer().getNxtlvl())));
                if (! m.getPlayer().getSeedInventory().isEmpty() && List_inv.getSelectionModel().isEmpty() == false) {
                    btn_action.setVisible(true);
                    lbl_info.setText("SEED DESCRIPTION: \n" + m.getPlayer().getSeedInventory().get(List_inv.getSelectionModel().getSelectedIndex()));
                }
            }
        };
        time.start();

        btn_mainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Main Menu");
                selected = null;
                //System.out.println("Minutes: " + m.getMin() + " Seconds: " + m.getSec());
                try {
                    m.changeView("/view/UserMain.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_select1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selected == null || selected.equals("Pickaxe") || selected.equals("Plow Tool") || selected.equals("Fertilizer") || selected.equals("Harvest"))
                    selected = "Watering Can";
                else if (selected.equals("Watering Can"))
                    selected = null;

                lbl_selectedTool.setText("Selected Tool: " + selected);
                System.out.println(getSelected());
                System.out.println(m.getMin() + " " + m.getSec());
            }
        });

        btn_select1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow shadow = new DropShadow();
                btn_select1.setEffect(shadow);
                btn_select1.setTooltip(new Tooltip(w.getFunction()));
            }
        });

        btn_select1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn_select1.setEffect(null);
            }
        });


        btn_select2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selected == null || selected.equals("Watering Can") || selected.equals("Plow Tool") || selected.equals("Fertilizer") || selected.equals("Harvest"))
                    selected = "Pickaxe";
                else if (selected.equals("Pickaxe"))
                    selected = null;

                lbl_selectedTool.setText("Selected Tool: " + selected);
                System.out.println(getSelected());
            }
        });

        btn_select2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow shadow = new DropShadow();
                btn_select2.setEffect(shadow);
                btn_select2.setTooltip(new Tooltip(p.getFunction()));
            }
        });

        btn_select2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn_select2.setEffect(null);
            }
        });

        btn_select3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selected == null || selected.equals("Pickaxe") || selected.equals("Watering Can") || selected.equals("Fertilizer") || selected.equals("Harvest"))
                    selected = "Plow Tool";

                else if (selected.equals("Plow Tool"))
                    selected = null;

                lbl_selectedTool.setText("Selected Tool: " + selected);
                System.out.println(getSelected());
            }
        });

        btn_select3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow shadow = new DropShadow();
                btn_select3.setEffect(shadow);
                btn_select3.setTooltip(new Tooltip(pt.getFunction()));
            }
        });

        btn_select3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn_select3.setEffect(null);
            }
        });

        btn_select4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selected == null || selected.equals("Pickaxe") || selected.equals("Plow Tool") || selected.equals("Watering Can") || selected.equals("Harvest"))
                    selected = "Fertilizer";
                else if (selected.equals("Fertilizer"))
                    selected = null;

                lbl_selectedTool.setText("Selected Tool: " + selected);
                System.out.println(getSelected());
            }
        });

        btn_select4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow shadow = new DropShadow();
                btn_select4.setEffect(shadow);
                btn_select4.setTooltip(new Tooltip(f.getFunction()));
            }
        });

        btn_select4.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn_select4.setEffect(null);
            }
        });

        btn_harvest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selected == null || selected.equals("Pickaxe") || selected.equals("Plow Tool") || selected.equals("Watering Can") || selected.equals("Fertilizer"))
                    selected = "Harvest";
                else if (selected.equals("Harvest"))
                    selected = null;

                lbl_selectedTool.setText("Selected Tool: " + selected);
                System.out.println(getSelected());
            }
        });

        btn_harvest.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow shadow = new DropShadow();
                btn_harvest.setEffect(shadow);
                btn_harvest.setTooltip(new Tooltip("This Function will HARVEST the plant."));
            }
        });

        btn_harvest.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn_harvest.setEffect(null);
            }
        });

        btn_Inventory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selected = null;
                lbl_selectedTool.setText("Selected Tool: " + selected);
                System.out.println("Clicked Check Inventory");
                anchorPane_Inv.setVisible(true);
                PlayerLot.setVisible(false);
                btn_mainMenu.setDisable(true);
                btn_select1.setDisable(true);
                btn_select2.setDisable(true);
                btn_select3.setDisable(true);
                btn_select4.setDisable(true);
                btn_Inventory.setDisable(true);
                btn_harvest.setDisable(true);
                btn_Inventory.setText("Check Inventory");
                lbl_display.setVisible(false);
            }
        });

        btn_Lot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Back to Lot");
                anchorPane_Inv.setVisible(false);
                PlayerLot.setVisible(true);
                btn_mainMenu.setDisable(false);
                btn_select1.setDisable(false);
                btn_select2.setDisable(false);
                btn_select3.setDisable(false);
                btn_select4.setDisable(false);
                btn_Inventory.setDisable(false);
                btn_harvest.setDisable(false);
                lbl_display.setVisible(false);
                btn_harvest.setDisable(true);
            }
        });

        btn_action.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked Action Button");
                selected = "Seed";
                lbl_selectedTool.setText("Selected Tool: " + selected);
                if (List_inv.getSelectionModel().getSelectedItem().toString().contains("Seed"))
                {
                    lbl_display.setVisible(true);
                    PlayerLot.setVisible(true);
                    anchorPane_Inv.setVisible(false);
                    btn_mainMenu.setDisable(true);
                    btn_select1.setDisable(true);
                    btn_select2.setDisable(true);
                    btn_select3.setDisable(true);
                    btn_select4.setDisable(true);
                    btn_harvest.setDisable(false);
                    btn_Inventory.setText("Cancel");
                    btn_Inventory.setDisable(false);
                    btn_harvest.setDisable(true);
                }
            }
        });
    }

    private List makeList(){
        List inventory = new List();
        ArrayList<Seed> seedlistcontent = m.getPlayer().getSeedInventory();
        ArrayList<Tools> toollistcontent = m.getPlayer().getToolInventory();
        int i,j;

        for (i = 0; i < seedlistcontent.size(); i++){
            if (seedlistcontent.get(i).getQty() != 0) {
                inventory.add(seedlistcontent.get(i).getName() + "  x" + seedlistcontent.get(i).getQty());
                List_inv.getItems().add(inventory.getItem(i));
            }
            else {
                m.getPlayer().getSeedInventory().remove(i);
                i--;
            }
        }
        if (seedlistcontent.isEmpty())
            return null;

        return inventory;
    }

    private GridPane makeGrid(int c, int r) {
        GridPane lot = new GridPane();
        lot.setLayoutX(42.0);
        lot.setLayoutY(79.0);
        lot.setMaxHeight(252.0);
        lot.setMaxWidth(513.0);

        ColumnConstraints column = new ColumnConstraints(513 / c);
        column.setMaxWidth(513 / c);
        RowConstraints row = new RowConstraints(252 / r);
        row.setMaxHeight(252 / r);

        int k = 0;

        for (int b = 0; b < r; b++) {
            lot.getRowConstraints().add(row);
        }
        for (int a = 0; a < c; a++) {
            lot.getColumnConstraints().add(column);
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Button x = new Button(null);
                x.setMaxHeight(row.getMaxHeight());
                x.setMaxWidth(column.getMaxWidth());
                x.setMinHeight(row.getMaxHeight());
                x.setMinWidth(column.getMaxWidth());
                x.setBlendMode(BlendMode.MULTIPLY);
                x.setOpacity(1);

                int y = i;
                int z = j;

                ImageView plowed = new ImageView();
                plowed.setFitHeight(row.getMaxHeight());
                plowed.setFitWidth(column.getMaxWidth());

                StackPane stack = new StackPane();
                stack.getChildren().add(plowed);

                x.setGraphic(stack);

                if (LotControl.getGridList().size() < (r*c)) {
                    Random a = new Random();
                    int pp = a.nextInt(99 + 1) + 1;
                    String status = "";

                    if (pp >= 1 && pp <= 15) {
                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/rocky_tile.png")));
                        status = "Rocky";
                    } else if (pp >= 16 && pp <= 100) {
                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/unplowed_tile.png")));
                        status = "Unplowed";
                    }
                    LotControl.addgrid(new Tile(i, j, status));
                    lot.add(x, j, i);
                }
                else if (LotControl.getGridList().size() == (r*c))
                {
                    if (LotControl.getGridList().get(k).getStatus().equals("Plowed"))
                    {
                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/plowed_tile.png")));

                        if (LotControl.getGridList().get(k).getSeedContent() != null)
                        {
                            Tile tile_planted = LotControl.getGridList().get(k);
                            int fk = k;
                            System.out.println(tile_planted.getLoading_1());
                            AnimationTimer update = new AnimationTimer() {
                                @Override
                                public void handle(long now) {
                                    String[] c = tile_planted.getSeedContent().split(" ");
                                    tile_planted.grow(c[0] + "");
                                    if (tile_planted.getLoading_1().equals("growing") || tile_planted.getLoading_1().equals("withered"))
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/" + tile_planted.getLoading_1() + "_tile.jpg")));
                                    else if (tile_planted.getLoading_1().equals("ready"))
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/" + tile_planted.getLoading_1() + tile_planted.getLoading_2() + "_tile.jpg")));
                                    else if (tile_planted.getLoading_1().equals("growing") && (LotControl.getGridList().get(fk).getTime_now() - LotControl.getGridList().get(fk).getTime_start() < 15))
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/planted_tile.png")));
                                    else if (tile_planted.getLoading_1() == null)
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/unplowed_tile.png")));
                                }
                            };
                            update.start();

                        }
                    }
                    else if (LotControl.getGridList().get(k).getStatus().equals("Unplowed"))
                    {
                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/unplowed_tile.png")));
                    }
                    else if (LotControl.getGridList().get(k).getStatus().equals("Rocky"))
                    {
                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/rocky_tile.png")));
                    }
                    k++;
                    lot.add(x, j, i);
                }

                x.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Tile: " + y + "-" + z);
                        Tile v = LotControl.getGridList().get(y * m.column + z);
                        if (getSelected() != null)
                        {
                            switch (getSelected())
                            {
                                case "Watering Can":
                                    if (v.checkStatus() == 1 && v.checkSeedContents())
                                    {
                                        Seed s = null;
                                        for(int d = 0; d < m.getPlayer().getSeeds().size(); d++) {
                                            if (m.getPlayer().getSeeds().get(d).getName().equals(v.getSeedContent()))
                                                s = m.getPlayer().getSeeds().get(d);
                                        }

										boolean g = v.water(s.getWaterNeeded(), s.getWaterBonus());
                                        if (g)
                                            m.getPlayer().updateExp(1);
                                    }

                                    else
                                        LotControl.toolError(0);
                                    break;
                                case "Pickaxe":

                                    if (v.checkStatus() == -1 && !v.checkSeedContents())
                                    {
                                        v.clearRock();
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/unplowed_tile.png")));
                                        m.getPlayer().updateExp(3);
                                    }

                                    else
                                        LotControl.toolError(1);
                                    break;
                                case "Plow Tool":
                                    if ((v.checkStatus() == 0 && !v.checkSeedContents()) || (v.checkStatus() == 0 && !v.checkSeedContents()&& v.checkGrowth() == 0))
                                    //(v.checkStatus() == 1 && v.checkSeedContents() && v.checkGrowth() == 2)
                                    {
                                        v.plow();
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/plowed_tile.png")));
                                        m.getPlayer().updateExp(1);
                                    }
                                    else if ((v.checkStatus() == 1 && v.checkSeedContents() && v.checkGrowth() == 0))
                                    {
                                        v.plow();
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/unplowed_tile.png")));
                                        m.getPlayer().updateExp(1);
                                    }
                                    else
                                        LotControl.toolError(2);
                                    break;
                                case "Fertilizer":
                                    if (v.checkStatus() == 1 && !v.checkSeedContents())
                                    {
                                        if (((Fertilizer)m.getPlayer().getToolInventory().get(3)).getQty() > 0)
                                        {
                                            if (v.fertilize())
                                                m.getPlayer().updateExp(2.5);
                                        }
                                        else LotControl.toolError(4);

                                    }
                                    else
                                        LotControl.toolError(3);
                                    break;
                                case "Seed":
                                    if (v.checkAStatus() == 1 && !v.checkSeedContents()) {
                                        String[] plantedseed = List_inv.getSelectionModel().getSelectedItem().toString ().split(" ");
                                        int istree = v.plant(plantedseed[0] + " " + plantedseed[1]);
                                        if((istree == 1) && y != 0 && z != 0 && y != r-1 && z != c-1 &&
                                                LotControl.getGridList().get((y-1) * m.column + z - 1).getSeedContent() == null &&
                                                LotControl.getGridList().get((y-1) * m.column + z).getSeedContent() == null &&
                                                LotControl.getGridList().get((y-1) * m.column + z + 1).getSeedContent() == null &&
                                                LotControl.getGridList().get(y * m.column + z - 1).getSeedContent() == null &&
                                                LotControl.getGridList().get(y * m.column + z + 1).getSeedContent() == null &&
                                                LotControl.getGridList().get((y+1) * m.column + z - 1).getSeedContent() == null &&
                                                LotControl.getGridList().get((y+1) * m.column + z).getSeedContent() == null &&
                                                LotControl.getGridList().get((y+1) * m.column + z + 1).getSeedContent() == null
                                        ) {
                                            LotControl.getGridList().get((y - 1) * m.column + z - 1).plant(plantedseed[0] + " " + plantedseed[1]);
                                            LotControl.getGridList().get((y - 1) * m.column + z).plant(plantedseed[0] + " " + plantedseed[1]);
                                            LotControl.getGridList().get((y - 1) * m.column + z + 1).plant(plantedseed[0] + " " + plantedseed[1]);
                                            LotControl.getGridList().get((y + 1) * m.column + z - 1).plant(plantedseed[0] + " " + plantedseed[1]);
                                            LotControl.getGridList().get((y + 1) * m.column + z).plant(plantedseed[0] + " " + plantedseed[1]);
                                            LotControl.getGridList().get((y + 1) * m.column + z + 1).plant(plantedseed[0] + " " + plantedseed[1]);
                                            LotControl.getGridList().get(y * m.column + z + 1).plant(plantedseed[0] + " " + plantedseed[1]);
                                            LotControl.getGridList().get(y * m.column + z - 1).plant(plantedseed[0] + " " + plantedseed[1]);

                                        }
                                        else if (istree != 0){
                                            LotControl.getGridList().get(y * m.column + z).setBlankSeedContent();
                                            LotControl.plantError(2);
                                            istree = 3;
                                        }

                                        if (istree == 0 || istree == 1) {
                                            plowed.setImage(new Image(getClass().getResourceAsStream("/images/planted_tile.png")));
                                            v.setTime();
                                            v.grow(plantedseed[0] + "");
                                            AnimationTimer update = new AnimationTimer() {
                                                @Override
                                                public void handle(long now) {
                                                    String o = v.grow(plantedseed[0] + "");
                                                    String first = plantedseed[0].toLowerCase();
                                                    if (v.getGrowth() == null)
                                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/unplowed_tile.png")));
                                                    else if (o.equals("growing") && (v.getTime_now() - v.getTime_start()) < 15)
                                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/planted_tile.png")));
                                                    else if (o.equals("growing") || o.equals("withered"))
                                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/" + o + "_tile.jpg")));
                                                    else if (o.equals("ready")) {
                                                        btn_harvest.setDisable(false);
                                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/" + o + first + "_tile.jpg")));
                                                    }    }
                                            };
                                            update.start();
                                            selected = null;
                                            lbl_selectedTool.setText("Selected Tool: " + selected);
                                            lbl_display.setVisible(false);
                                            m.getPlayer().getSeedInventory().get(List_inv.getSelectionModel().getSelectedIndex()).deductQty();
                                            PlayerItem.removeAll();
                                            List_inv.getItems().clear();
                                            PlayerItem = makeList();
                                            if (PlayerItem == null) {
                                                btn_action.setVisible(false);
                                                lbl_display.setText("You have no items!");
                                                lbl_noitems.setVisible(true);
                                                lbl_info.setVisible(false);
                                            }

                                            btn_mainMenu.setDisable(false);
                                            btn_select1.setDisable(false);
                                            btn_select2.setDisable(false);
                                            btn_select3.setDisable(false);
                                            btn_select4.setDisable(false);
                                            btn_Inventory.setText("Check Inventory");
                                            m.getPlayer().updateExp(3);
                                        }
                                    }
                                    else if (v.checkAStatus() != 1)
                                        LotControl.plantError(0);
                                    else if (v.checkSeedContents()) {
                                        LotControl.plantError(1);
                                    }
                                    break;

                                case "Harvest":
                                    if (v.checkStatus() == 1 && v.checkSeedContents() == true && v.checkGrowth() == 1)
                                    {
                                        //v.harvest(v.getSeedContent() + "");
                                        plowed.setImage(new Image(getClass().getResourceAsStream("/images/unplowed_tile.png")));
                                        if (v.findSeed(v.getSeedContent() + "").getType() == "Fruit Tree") {
                                            v.harvest(v.getSeedContent() + "");
                                            LotControl.getGridList().get((y - 1) * m.column + z - 1).setBlankSeedContent();
                                            LotControl.getGridList().get((y - 1) * m.column + z).setBlankSeedContent();
                                            LotControl.getGridList().get((y - 1) * m.column + z + 1).setBlankSeedContent();
                                            LotControl.getGridList().get((y + 1) * m.column + z - 1).setBlankSeedContent();
                                            LotControl.getGridList().get((y + 1) * m.column + z).setBlankSeedContent();
                                            LotControl.getGridList().get((y + 1) * m.column + z + 1).setBlankSeedContent();
                                            LotControl.getGridList().get(y * m.column + z + 1).setBlankSeedContent();
                                            LotControl.getGridList().get(y * m.column + z - 1).setBlankSeedContent();
                                            v.reset();
                                            v.setStatus("Unplowed");
                                            v.setGrowth("");
                                        }
                                        else
                                        {
                                            v.harvest(v.getSeedContent() + "");
                                            v.reset();
                                            v.setStatus("Unplowed");
                                            v.setGrowth("");
                                        }
                                        if (v.getGrowth().equals("Withered"))
                                        {
                                            v.setGrowth("");
                                        }

                                        m.getPlayer().updateExp(5);
                                    }
                                    break;
                            }
                        }
                    }
                });
                but[(i * m.column) + j] = x;
            }
        }
        lot.setAlignment(Pos.CENTER);
        lot.setGridLinesVisible(true);
        anchorPane_Lot.getChildren().add(lot);
        for (int g = 0; g < LotControl.getGridList().size(); g++)
        {
            int gg = g;
            AnimationTimer update = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    but[gg].setOnMouseEntered(new EventHandler<MouseEvent>() {
                        Tile t = LotControl.getGridList().get(gg);
                        @Override
                        public void handle(MouseEvent event) {
                            DropShadow shadow = new DropShadow();
                            ((Button)but[gg]).setEffect(shadow);
                            ((Button)but[gg]).setTooltip(new Tooltip(t.toString()));
                        }
                    });
                }
            };
            but[gg].setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ((Button)but[gg]).setEffect(null);
                }
            });
            update.start();
        }

        return lot;
    }


    private String getSelected()
    {
        return selected;
    }
}