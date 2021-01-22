package login;

import alerts.AlertUp;
import connectionDB.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import setscene.SetScene;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static connectionDB.ConnectionDB.connectWithDB;

/**
 * Klasa, która obsługuje logowanie użytkownika do aplikacji
 */
public class LoginPageController implements Initializable {
    @FXML
    private TextField  login;
    @FXML
    private TextField  haslo;

    SetScene scene = new SetScene();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public static int log =0;

    /**
     * Metoda, która sluży do zalogowania się użytkownika do aplikacji
     * @param event
     */
    public void zaloguj(ActionEvent event){
        try {
            con = connectWithDB();
            if(login.getText().charAt(0) == 'p') {
                String sql = "SELECT * FROM public.prac_login WHERE login=? and haslo=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, login.getText());
                ps.setString(2, haslo.getText());

                rs = ps.executeQuery();
                if (rs.next()) {
                    log = rs.getInt("id_pracownik");
                    scene.setNewWindow(event,"/employee_acc/employeeFirst.fxml");

                } else {
                    throw new Exception("Sprawdź poprawność loginu i hasła!");
                }
            }
            else {
                String sql = "SELECT * FROM public.Admin WHERE login=? and haslo=?";
                ps = con.prepareStatement(sql);

                ps.setString(1, login.getText());
                ps.setString(2, haslo.getText());

                rs = ps.executeQuery();
                if (rs.next()) {
                    log = rs.getInt("id_admin");
                    scene.setNewWindow(event,"/admin_first/first_page.fxml");

                } else {
                    throw new Exception("Sprawdź poprawność loginu i hasła!");
                }
            }

            ps.close();
            rs.close();
            con.close();
        }
        catch (Exception e){
            AlertUp.allertBoxError("Problem z logowaniem!",
                    "Nie udało się zalogować! Sprobuj ponownie!\n" + e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
