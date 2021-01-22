package add;

import connectionDB.ConnectionDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static alerts.AlertUp.*;
import static validate.ValidateField.*;
import static connectionDB.ConnectionDB.connectWithDB;


/**
 * Klasa obsługująca dodawanie nowych stanowisk do bazy danych
 */
public class AddNewPositionController implements Initializable {
    @FXML
    private ComboBox<String> department;
    @FXML
    private TextField position;
    @FXML
    private TextField stawka;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private int idDepartment=0;
    private int i = 0;

    /**
     * Metoda słuząca do wyczyszczenia pól
     */
    public void clearAll(){
        department.getSelectionModel().clearSelection();
        position.clear();
        stawka.clear();
        idDepartment=0;
        i = 0;
    }

    /**
     * Metoda obsługująca dodawanie stanowisk do bazy danych
     */
    public void addPosition(){
        try {
            con = connectWithDB();

            if (validateCharField(position) && validateComboBox(department) && validateNum(stawka)) {
                System.out.println("okk");
                idDepartment = department.getSelectionModel().getSelectedIndex() + 1;

                String sql = "insert into stanowisko(id_dzial, stanowisko, stawka_godz)  values (?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, idDepartment);
                ps.setString(2, position.getText());
                ps.setDouble(3, Double.parseDouble(stawka.getText()));
                i = ps.executeUpdate();
                if (i > 0) {
                    allertBoxInformation("Stanowisko zostało dodane","OK!");
                    clearAll();
                } else {
                    throw new Exception("Nie udało się dodać stanowisko. Sprobuj ponownie!!!");
                }

                ps.close();
                rs.close();
                con.close();
           }
        }catch (SQLException e){
            allertBoxError("SQLException", e.getMessage());
        }
        catch(Exception e){
            allertBoxError("Stanowisko nie dodane", e.getMessage());
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
            con.close();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
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
