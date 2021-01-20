package employees_list;

import admin_first.AlertUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    @FXML
    private TableColumn<Employee, String> etat;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public static Employee selItem;

    public  void onTableClick(){
        editButton.setDisable(false);
    }

    public void edit(){
        try {
            if (table.getSelectionModel().getSelectedItem() != null) {
                selItem = table.getSelectionModel().getSelectedItem();
                Parent login = FXMLLoader.load(getClass().getResource("/employees_list/EditEmployee.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(login));
                stage.show();
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("error", e.getMessage());
        }
    }

    public void delete(){
        try {
            if (table.getSelectionModel().getSelectedItem() != null) {
                selItem = table.getSelectionModel().getSelectedItem();
                con = connectWithDB();
//                String sql = "delete from pracownik where id_pracownik=?";
//                ps = con.prepareStatement(sql);
//                ps.setInt(1, selItem.getId());

                int i = ps.executeUpdate();
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("error", e.getMessage());
        }
    }
    public static Employee getEmployee(){
        return selItem;
    }




    public void list(){
        id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<Employee, String>("city"));
        email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        department.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));
        etat.setCellValueFactory(new PropertyValueFactory<Employee, String>("etat"));
        ObservableList<Employee> list = FXCollections.observableArrayList();

        try{

            con = connectWithDB();

           String sql = "SELECT * FROM lista_pracownikow";
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Employee(rs.getInt("id_pracownik"),rs.getString("imie"),rs.getString("nazwisko"),rs.getString("miasto")
                        ,rs.getString("email"),rs.getString("stanowisko"),rs.getString("opis"), rs.getString("etat")));
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


    private void initData(){
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        list();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }
}
