package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static login.ConnectionDB.connectWithDB;

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

    public void addPremia(ActionEvent event){
        try {
            if((!(premia_proc.getText() == null || premia_proc.getText().trim().isEmpty())) &&
                    !(premia_opis.getText() == null || premia_opis.getText().trim().isEmpty())
                    && admin_first.ValidateField.validatePercentField(premia_proc))
            {
                con = connectWithDB();
                String sql = "insert into premia(procenty, opis) values (?,?)";
                ps = con.prepareStatement(sql);
                ps.setDouble(1, Double.parseDouble(premia_proc.getText())/100);
                ps.setString(2, premia_opis.getText());
                int i = ps.executeUpdate();
                if(i>0){
                    admin_first.AlertUp.allertBoxInformation("OK", "Premia została dodana");
                    premia_proc.clear();
                    premia_opis.clear();
                }

                ps.close();
                con.close();
            }
            else{
                admin_first.AlertUp.allertBoxError("Blad","Sprawdź poprawność wprowadzonych danych");
            }


        }
        catch (Exception e){
            admin_first.AlertUp.allertBoxError("Bląd",e.getMessage());
        }
    }

    public void addKara(ActionEvent event){
        try {
            if((!(kara_proc.getText() == null || kara_proc.getText().trim().isEmpty())) &&
                    !(kara_opis.getText() == null || kara_opis.getText().trim().isEmpty())
                    && admin_first.ValidateField.validatePercentField(kara_proc))
            {
                con = connectWithDB();
                String sql = "insert into kara(procenty, opis) values (?,?)";
                ps = con.prepareStatement(sql);
                ps.setDouble(1, Double.parseDouble(kara_proc.getText())/100);
                ps.setString(2, kara_opis.getText());
                int i = ps.executeUpdate();
                if(i>0){
                    admin_first.AlertUp.allertBoxInformation("OK", "Kara została dodana");
                    kara_opis.clear();
                    kara_proc.clear();
                }

                ps.close();
                con.close();

            }
            else{
                admin_first.AlertUp.allertBoxError("Blad","Sprawdź poprawność wprowadzonych danych");
            }
        }
        catch (Exception e){
            admin_first.AlertUp.allertBoxError("Bląd",e.getMessage());

        }
    }

    public void addBon(ActionEvent event){
        try {
            if(!(bon_opis.getText() == null || bon_opis.getText().trim().isEmpty()))
            {
                con = connectWithDB();
                String sql = "insert into bonus(opis) values (?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, bon_opis.getText());
                int i = ps.executeUpdate();
                if(i>0){
                    admin_first.AlertUp.allertBoxInformation("OK", "Bonus został dodana");
                    bon_opis.clear();
                }

                ps.close();
                con.close();
            }
            else{
                admin_first.AlertUp.allertBoxError("Blad","Sprawdź poprawność wprowadzonych danych");
            }
        }
        catch (Exception e){
            admin_first.AlertUp.allertBoxError("Bląd",e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
