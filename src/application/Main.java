package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class will initiate the primary stage for the scene builder and will create a new player
 */
public class Main extends Application {
    public static Stage window;
    public static final int column = 10;
    public static final int row = 5;
    private static Player p;
    public static final AtomicLong min = new AtomicLong();
    public static final AtomicLong sec = new AtomicLong();

    /**
     * This method helps launch the User Interface
     * @param args
     */
    public static void main (String[] args) {launch(args);}

    /**
     * This method initializes the stage for the scenes to be set
     * @param primaryStage This will the main window that will display all views
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.window = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MyFarmOpen.fxml"));

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserLot.fxml"));

        Scene scene = new Scene(loader.load());

        try{
            Media farmingSong = new Media(getClass().getResource("../songs/farm_bg.wav").toExternalForm());
            MediaPlayer player = new MediaPlayer(farmingSong);
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
            player.play();
        }catch (Exception e)
        {
            System.out.println(" ");
        }


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("My Farming Simulator");
        primaryStage.show();

    }

    /**
     * This method will change the scene of the stage set in the start
     * @param file The filename of the fxml file
     */
    public void changeView (String file) throws Exception
    {
        FXMLLoader view = new FXMLLoader(getClass().getResource(file));

        Scene scene = new Scene(view.load());
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public void setTitle (String title)
    {
        window.setTitle(title);
    }

    public void setPlayer (String name)
    {
        p = new model.Player(name);
    }

    public Player getPlayer ()
    {
        return p;
    }

    public long getMin() {
        return this.min.get();
    }

    public long getSec() {
        return this.sec.get();
    }

    /**
     * This method will start the timer or time of the program
     */
    public void startTimer()
    {
        AnimationTimer counter = new AnimationTimer() {
            long time = System.nanoTime();
            @Override
            public void handle(long now) {
                long secs = 0;
                long mins = 0;
                secs = (now - time)/1000000000;
                mins = secs/60;
                secs %= 60;
                min.set(mins);
                sec.set(secs);
            }
        };
        counter.start();
    }
}