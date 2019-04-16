import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static Stage mainFX;

    @Override
    public void start(final Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        final Controller controller = (Controller)loader.getController();
        primaryStage.setTitle("PTO-Helper");
        mainFX = primaryStage;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        primaryStage.setMinWidth(width * 0.8);
        primaryStage.setMinHeight(height * 0.7);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("myStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
