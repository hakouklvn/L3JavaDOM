package guelfen.abdelheq.magasin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MagazineParser.initElement();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main.fxml"));
        String css = Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm();

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)  {
        launch();
    }
}