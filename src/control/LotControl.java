package control;

import javafx.scene.control.Alert;
import model.Tile;
import application.Main;

import java.util.ArrayList;

/**
 * This class stores information and displays errors in relation of the LotView
 */
public class LotControl {
    private static ArrayList<Tile> gridList = new ArrayList<>();

    /**
     * This method displays the error due to the misuse of a certain tool
     * @param index Determines on which error message to output
     */
    public static void toolError(int index)
    {
        switch (index)
        {
            case 0:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("My Farm Server");
                alert.setHeaderText("USE TOOL ERROR!");
                alert.setContentText("You can only water a PLOWED TILE that is OCCUPIED");
                alert.showAndWait();
                break;
            case 1:
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("My Farm Server");
                alert1.setHeaderText("USE TOOL ERROR!");
                alert1.setContentText("You can only clear a ROCKY TILE that is NOT OCCUPIED");
                alert1.showAndWait();
                break;
            case 2:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("My Farm Server");
                alert2.setHeaderText("USE TOOL ERROR!");
                alert2.setContentText("You can only plow an UNPLOWED TILE that is NOT OCCUPIED");
                alert2.showAndWait();
                break;
            case 3:
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("My Farm Server");
                alert3.setHeaderText("USE TOOL ERROR!");
                alert3.setContentText("You can only fertilize a PLOWED TILE that is NOT OCCUPIED");
                alert3.showAndWait();
                break;
            case 4:
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("My Farm Server");
                alert4.setHeaderText("USE TOOL ERROR!");
                alert4.setContentText("You ran out of FERTILIZER!!! :O");
                alert4.showAndWait();
                break;
        }

    }

    /**
     * This method displays the error when planting a seed in different conditions
     * @param index Determines on which error message to output
     */
    public static void plantError(int index)
    {
        switch (index)
        {
            case 0:
                Alert alert5 = new Alert(Alert.AlertType.ERROR);
                alert5.setTitle("My Farm Server");
                alert5.setHeaderText("SEED PLANTING ERROR!");
                alert5.setContentText("You can only plant a seed on a FERTILIZED TILE!");
                alert5.showAndWait();
                break;

            case 1:
                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("My Farm Server");
                alert6.setHeaderText("SEED PLANTING ERROR!");
                alert6.setContentText("TILE is already OCCUPIED. Select another FERTILIZED TILE!");
                alert6.showAndWait();
                break;
            case 2:
                Alert tree = new Alert(Alert.AlertType.ERROR);
                tree.setTitle("My Farm Server");
                tree.setHeaderText("SEED PLANTING ERROR!");
                tree.setContentText("Planting a tree requires a 3 x 3 LOT! Plant the center carefully!");
                tree.showAndWait();
                break;
        }
        }

    /**
     * This method stores the information of the dynamically allocated buttons (called tiles)
     * @param tile The buttons that were created
     */
    public static void addgrid(Tile tile)
    {
        gridList.add(tile);
    }

    public static ArrayList<Tile> getGridList()
    {
        return gridList;
    }
}
