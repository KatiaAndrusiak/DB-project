package editemployee;

import alerts.AlertUp;
import connectionDB.ConnectionDB;
import employeeinfo.EmployeeInfoController;
import employees_list.Employee;
import employees_list.EmployeeListController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static connectionDB.ConnectionDB.connectWithDB;

/**
 * Służy do edytowania danych pracownika
 */
public class EditEmployeeController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField city;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<String> department;
    @FXML
    private ComboBox<String> position;
    @FXML
    private ComboBox<String> etat;
    @FXML
    private Button editButton;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Employee emp;
    int idDepartment = 0;

    /**
     * Metoda służąca do edytowania danych pracownika oraz zmiany tych danych w BD
     */
    public void updateEmployee(ActionEvent event){
        try{
            con = connectWithDB();
            String sql = "select update_pracownik(?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);

            ps.setInt(1, emp.getId());
            ps.setString(2,name.getText());
            ps.setString(3,surname.getText());
            ps.setString(4,city.getText());
            ps.setString(5,email.getText());
            ps.setString(6,department.getSelectionModel().getSelectedItem());
            ps.setString(7,position.getSelectionModel().getSelectedItem());
            ps.setString(8,etat.getSelectionModel().getSelectedItem().split(" \\(" )[0]);
            rs = ps.executeQuery();
            if(rs.next()){
                AlertUp.allertBoxInformation("OK!","Dane zostały zapisane.");
                ((Node) event.getSource()).getScene().getWindow().hide();
            }
            ps.close();
            rs.close();
            con.close();
        }
        catch (Exception e){
            AlertUp.allertBoxError("Error", "Nie udało się zapisać.");
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }


    public void setButtonVisible(){
        editButton.setDisable(false);
    }


    /**
     * Metoda służy do wyswietlania stanowisk w zależności od wybranego dziąłu
     */
    public void addPositionsByDepartment()
    {
        position.getItems().clear();
        try{
            con = connectWithDB();
            String sql = "select * from stanowisko where id_dzial =?";
            ps = con.prepareStatement(sql);

            idDepartment = department.getSelectionModel().getSelectedIndex()+1;
            ps.setInt(1, idDepartment);
            rs = ps.executeQuery();
            while (rs.next()) {
                String tmp = rs.getString("Stanowisko");
                position.getItems().add(tmp);
            }
            ps.close();
            rs.close();
            con.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            AlertUp.allertBoxError("Bład", e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }

    }

    /**
     * Metoda służy do inicjalizacji danych na tej stronie(panelu)
     */
    private void initData()
    {
        emp = EmployeeListController.sel ? EmployeeListController.getEmployee() : EmployeeInfoController.account;
        name.setText(emp.getName());
        surname.setText(emp.getSurname());
        city.setText(emp.getCity());
        email.setText(emp.getEmail());
        department.getSelectionModel().select(emp.getDepartment());
        position.getSelectionModel().select(emp.getPosition());
        etat.getSelectionModel().select(emp.getEtat());
        editButton.setDisable(true);


        try{
            con = connectWithDB();
            String sql = "select * from dzial";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String tmp = rs.getString("opis");
                department.getItems().add(tmp);
            }

            ps.close();
            rs.close();


            String sql1 = "select * from etat";
            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();

            while (rs.next()) {
                String tmp = rs.getString("etat") +  " (" + rs.getDouble("godzin_dziennie") + " godzin dziennie)";
                etat.getItems().add(tmp);
            }
            ps.close();
            rs.close();
            con.close();

            addPositionsByDepartment();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            AlertUp.allertBoxError("Bład", e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       initData();
    }
}
