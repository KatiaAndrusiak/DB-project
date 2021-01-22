package add;

import alerts.AlertUp;
import connectionDB.ConnectionDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import validate.ValidateField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static connectionDB.ConnectionDB.connectWithDB;

/**
 * Klasa obsługująca dodawanie nowych premii, kar, bonusów do bazy danych
 */
public class AddNewBonsController implements Initializable {
    @FXML
    private TextField premia_opis;
    @FXML
    private TextField premia_proc;
    @FXML
    private TextField kara_opis;
    @FXML
    private TextField kara_proc;
    @FXML
    private TextField bon_opis;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    /**
     *Metoda obsługująca dodawanie premii do bazy danych
     */
    public void addPremia(){
        try {
            if((!(premia_proc.getText() == null || premia_proc.getText().trim().isEmpty())) &&
                    !(premia_opis.getText() == null || premia_opis.getText().trim().isEmpty())
                    && ValidateField.validatePercentField(premia_proc))
            {
                con = connectWithDB();
                String sql = "insert into premia(procenty, opis) values (?,?)";
                ps = con.prepareStatement(sql);
                ps.setDouble(1, Double.parseDouble(premia_proc.getText())/100);
                ps.setString(2, premia_opis.getText());
                int i = ps.executeUpdate();
                if(i>0){
                    AlertUp.allertBoxInformation("OK", "Premia została dodana");
                    premia_proc.clear();
                    premia_opis.clear();
                }

                ps.close();
                con.close();
            }
            else{
                throw new Exception("Sprawdź poprawność wprowadzonych danych");
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("Bląd",e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }

    /**
     *Metoda obsługująca dodawanie kar do bazy danych
     */
    public void addKara(){
        try {
            if((!(kara_proc.getText() == null || kara_proc.getText().trim().isEmpty())) &&
                    !(kara_opis.getText() == null || kara_opis.getText().trim().isEmpty())
                    && ValidateField.validatePercentField(kara_proc))
            {
                con = connectWithDB();
                String sql = "insert into kara(procenty, opis) values (?,?)";
                ps = con.prepareStatement(sql);
                ps.setDouble(1, Double.parseDouble(kara_proc.getText())/100);
                ps.setString(2, kara_opis.getText());
                int i = ps.executeUpdate();
                if(i>0){
                    AlertUp.allertBoxInformation("OK", "Kara została dodana");
                    kara_opis.clear();
                    kara_proc.clear();
                }

                ps.close();
                con.close();

            }
            else{
                throw new Exception("Sprawdź poprawność wprowadzonych danych");
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("Bląd",e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }

    /**
     *Metoda obsługująca dodawanie bonusów do bazy danych
     */
    public void addBon(){
        try {
            if(!(bon_opis.getText() == null || bon_opis.getText().trim().isEmpty()))
            {
                con = connectWithDB();
                String sql = "insert into bonus(opis) values (?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, bon_opis.getText());
                int i = ps.executeUpdate();
                if(i>0){
                    AlertUp.allertBoxInformation("OK", "Bonus został dodana");
                    bon_opis.clear();
                }

                ps.close();
                con.close();
            }
            else{
                throw new Exception("Sprawdź poprawność wprowadzonych danych");
            }
        }
        catch (Exception e){
            AlertUp.allertBoxError("Bląd",e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
