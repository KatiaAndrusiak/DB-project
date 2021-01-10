package admin_first;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageController implements Initializable {
    @FXML
    private Label add_empl;
    @FXML
    private Label add_dep;
    @FXML
    private Label count;
    @FXML
    private Button logout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void wyloguj(ActionEvent event) throws Exception {

        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent login = FXMLLoader.load(getClass().getResource("/login/login_page.fxml"));
        Scene scene = new Scene(login);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }

    public void addNewEmployee(ActionEvent event){

    }
    public void addNewDepartment(ActionEvent event){

    }

    public void countSalary(ActionEvent event){

    }
}
