package employees_list;

import alerts.AlertUp;
import connectionDB.ConnectionDB;
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

import static connectionDB.ConnectionDB.connectWithDB;

/**
 * Klasa służąca do wyświetlania listy pracowników
 */
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

    private static Employee selItem;
    public static boolean sel = false;

    public  void onTableClick(){
        editButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    /**
     * Metoda wyświetlająca panel do edytowanie danych pracownika
     */
    public void edit(){
        try {
            if (table.getSelectionModel().getSelectedItem() != null) {
                sel = true;
                selItem = table.getSelectionModel().getSelectedItem();
                Parent login = FXMLLoader.load(getClass().getResource("/editemployee/EditEmployee.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(login));
                stage.show();
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("error", e.getMessage());
        }
    }

    /**
     * Metoda służąca do usuwania pracowników z listy i BD
     */
    public void delete(){
        try {
            if (table.getSelectionModel().getSelectedItem() != null) {
                selItem = table.getSelectionModel().getSelectedItem();
                con = connectWithDB();
                String sql = "select delete_pracownik(?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, selItem.getId());
                boolean i =false;
                rs = ps.executeQuery();
                if (rs.next()) {
                    i = rs.getBoolean(1);
                }
                else {
                  throw new Exception("Nie udało się wykonać tego polecenia.");
                }
                if(i){
                    AlertUp.allertBoxInformation("OK","Pracownik "+selItem.getName() +" "+selItem.getSurname() +" został usuniety");
                    list();
                }
                ps.close();
                rs.close();
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("Błąd", e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }


    public static Employee getEmployee(){
        return selItem;
    }

    /**
     * Metoda odczytująca z BD listę pracowników
     */
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
            AlertUp.allertBoxError("Błąd", e.getMessage()+"\nSprobuj ponownie!");
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
        table.setItems(list);
    }



    /**
     * Metoda służy do inicjalizacji danych na tej stronie(panelu)
     */
    private void initData(){
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        sel = false;
        list();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }
}
