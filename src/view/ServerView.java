package view;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import control.ServerControl;


public class ServerView {
    @FXML private Button btn_play;
    @FXML private TextField txt_name;
    @FXML private ImageView img_farmBG;

    private ServerControl s;
    private Main m;

    public void initialize ()
    {
        m = new application.Main();
        s = new control.ServerControl();

        img_farmBG.setImage(new Image(getClass().getResourceAsStream("/images/server_bg.jpg")));

        btn_play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               if (s.userInput(txt_name.getText()) == true){
                   try {
                       m.changeView("/view/UserMain.fxml");
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   m.startTimer();
               }
            }
        });
    }
}
