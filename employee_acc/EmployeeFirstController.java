package employee_acc;

import alerts.AlertUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import connectionDB.ConnectionDB;
import login.LoginPageController;
import setscene.SetScene;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


/**
 * Klasa obsługująca stronę główną pracownika
 */
public class EmployeeFirstController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Pane mainPane;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private final int idPracownik =  LoginPageController.log;
    SetScene scene = new SetScene();


    /**
     * Metoda zmieniająca panel na panel wyświetlający historie wyplat
     */
    public void historyclick(){
        scene.setNewPane(mainPane,"../salaryhistory/history.fxml");
    }

    /**
     * Metoda zmieniająca panel na panel wyświetlający dane osobowe pracownika
     */
    public void employeeInfoClick(){
        scene.setNewPane(mainPane,"../employeeinfo/employee_info.fxml");
    }

    /**
     * Metoda zmieniająca panel na panel wyświetlający formularz do zmiany hasła
     */
    public void changePass(){
        scene.setNewPane(mainPane,"../emplchangepass/changePass.fxml");
    }

    /**
     * Metoda, która sługuje do wylogowania użytkownika
     * @param event ActionEvent
     */
    public void wyloguj(ActionEvent event) {
        try {
            scene.setNewWindow(event, "/login/login_page.fxml");
        }
        catch (Exception e){
            AlertUp.allertBoxError("Błąd", "Nie udało się wyłogować"+e.getMessage());
        }
    }

    /**
     * Metoda służy do inicjalizacji danych na tej stronie(panelu)
     */
    private void initData(){
        try {
            con = ConnectionDB.connectWithDB();
            String sql = "select * from pracownik where id_pracownik=?;";

            ps = con.prepareStatement(sql);
            ps.setInt(1,idPracownik);
            rs = ps.executeQuery();

            if (rs.next()) {
                nameLabel.setText(rs.getString("imie")+" "+ rs.getString("nazwisko"));
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (Exception e){
            AlertUp.allertBoxError("Błąd",e.getMessage());
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
