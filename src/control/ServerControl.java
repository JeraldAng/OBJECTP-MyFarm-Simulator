package control;

import application.Main;
import javafx.scene.control.Alert;

/**
 * This class displays all errors regarding the setting of Username
 */
public class ServerControl {
    private String username;
    private Main m;

    /**
     * Ths method will display an error if the user inputs only spaces or does not input anything
     * @param x Name that was inputted by the user
     */
    public boolean userInput(String x) {
        m = new Main();
        System.out.println("Clicked Play");
        String n = "";
        Alert a = new Alert(Alert.AlertType.ERROR);

        if ((x != null) && !(x.isEmpty()) && !(x.trim().isEmpty())) {
            m.setPlayer(x.trim());
            return true;

        } else {
            a.setTitle("My Farm Server Alert");
            a.setHeaderText("Error: No Username detected");
            a.setContentText("Please Input a Valid Username.");
            a.showAndWait();
        }
        return false;
    }
}
