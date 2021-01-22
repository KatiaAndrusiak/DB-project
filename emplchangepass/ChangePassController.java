package emplchangepass;

import alerts.AlertUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import connectionDB.ConnectionDB;
import login.LoginPageController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Klasa służaca do zmiany hasła pracownika
 */
public class ChangePassController implements Initializable {
    @FXML
    private PasswordField oldpass;
    @FXML
    private PasswordField newpass;
    @FXML
    private PasswordField newpass2;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private final int idPracownik  = LoginPageController.log;

    /**
     * Metoda służaca do zmiany hasła pracownika
     */
    public void changePass(){
        try{
            if(newpass.getText().equals(newpass2.getText())){
                con = ConnectionDB.connectWithDB();
                String sql = "select zmien_haslo(?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setInt(1,idPracownik);
                ps.setString(2,oldpass.getText());
                ps.setString(3,newpass.getText());
                rs= ps.executeQuery();
                if (rs.next()) {
                    if(rs.getInt(1) == 1) {
                        AlertUp.allertBoxInformation("OK", "Udało się zmienić hasło.");
                    } else {
                        throw new Exception("Nie udało się zmienić hasło.");
                    }
                }
                ps.close();
                rs.close();
                con.close();
            }
            else {
                throw new Exception("Niepotwierdzone nowe hasło");
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("Błąd", e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
