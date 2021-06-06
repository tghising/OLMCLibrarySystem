package edu.cqu.olmclibrarysystem.view;

import edu.cqu.olmclibrarysystem.presenter.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * OLMCLibrarySystem: OLMCLibrarySystem is main method() class for OLMCLibrarySystem
 *
 * @author Tikaraj Ghising - 12129239
 */
public class OLMCLibrarySystem extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignInLibrarian.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("OLMC Spiritual Library System");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:favicon.png"));
        
        // db connection for table creation and initialization of main librarian (admin) user
        DBConnection connection = new DBConnection();
        connection.createTables();
        connection.initializeMainLibrarian();       
        stage.show();
    }
}
