package employeeinfo;

import alerts.AlertUp;
import connectionDB.ConnectionDB;
import employees_list.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import login.LoginPageController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static connectionDB.ConnectionDB.connectWithDB;

/**
 * Klasa służąca do wyświetlania oraz edytowania danych pracownika
 */
public class EmployeeInfoController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label city;
    @FXML
    private Label email;
    @FXML
    private Label department;
    @FXML
    private Label position;
    @FXML
    private Label etat;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private final int idPracownik =  LoginPageController.log;
    public static Employee account;

    /**
     * Metoda wyświetlająca panel do edytowanie danych pracownika
     */
    public void edit(){
        try {
            Parent login = FXMLLoader.load(getClass().getResource("/editemployee/EditEmployee.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(login));
            stage.show();
        }
        catch (Exception e){
            AlertUp.allertBoxError("error", e.getMessage());
        }
    }

    /**
     * Metoda służy do inicjalizacji danych na tej stronie(panelu)
     */
    private void initData(){
        try{
            con = connectWithDB();
            String sql = "SELECT * FROM lista_pracownikow where  id_pracownik=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPracownik);
            rs = ps.executeQuery();
            if(rs.next()){
                account = new Employee(rs.getInt("id_pracownik"),rs.getString("imie"),rs.getString("nazwisko"),rs.getString("miasto")
                        ,rs.getString("email"),rs.getString("stanowisko"),rs.getString("opis"), rs.getString("etat"));
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

        name.setText(account.getName());
        surname.setText(account.getSurname());
        city.setText(account.getCity());
        email.setText(account.getEmail());
        department.setText(account.getDepartment());
        position.setText(account.getPosition());
        etat.setText(account.getEtat());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }
}
