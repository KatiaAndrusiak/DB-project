package admin_first;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ResourceBundle;

import static login.ConnectionDB.connectWithDB;

public class EmployeeListController implements Initializable {
    @FXML
    private TableView<Employee>  table;
    @FXML
    private TableColumn<Employee, Integer> id;
    @FXML
    private TableColumn<Employee, String> name;
    @FXML
    private TableColumn<Employee, String> surname;
    @FXML
    private TableColumn<Employee, String> city;
    @FXML
    private TableColumn<Employee, String> email;
    @FXML
    private TableColumn<Employee, String> position;
    @FXML
    private TableColumn<Employee, String> department;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void list(){
        id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<Employee, String>("city"));
        email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        department.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));
        ObservableList<Employee> list = FXCollections.observableArrayList();

        try{

            con = connectWithDB();

           String sql = "SELECT * FROM lista_pracownikow";
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Employee(rs.getInt("id_pracownik"),rs.getString("imie"),rs.getString("nazwisko"),rs.getString("miasto")
                        ,rs.getString("email"),rs.getString("stanowisko"),rs.getString("opis")));
            }
            ps.close();
            rs.close();
            con.close();

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data .");
            a.setContentText("There is some Error in Fetching Data. PLEASE TRY AGAIN..!!!"+e.getMessage());
            a.showAndWait();

        }
        table.setItems(list);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
