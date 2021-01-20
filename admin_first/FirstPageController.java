package admin_first;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageController implements Initializable {
    @FXML
    private MenuItem lista;
    @FXML
    private MenuItem add_empl;
    @FXML
    private MenuItem add_dep;
    @FXML
    private MenuItem count;
    @FXML
    private Button logout;
    @FXML
    private Pane mainPane;


    public void wyloguj(ActionEvent event) throws Exception {

        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent login = FXMLLoader.load(getClass().getResource("/login/login_page.fxml"));
        Scene scene = new Scene(login);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void employeeList(ActionEvent event)throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("../employees_list/employeeList.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().addAll(fxml);
    }

    @FXML
    public void addNewEmployee(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("../add/addEmployee.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().addAll(fxml);

    }
    @FXML
    public void addNewDepartment(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("../add/addNewPosition.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().addAll(fxml);
    }

    @FXML
    public void addNewBons(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("../add/addNewBons.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().addAll(fxml);
    }

    public void countSalary(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("../count_salary/addBon.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().addAll(fxml);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
