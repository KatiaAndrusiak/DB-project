package admin_first;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static admin_first.AlertUp.*;
import static admin_first.ValidateField.*;
import static login.ConnectionDB.connectWithDB;

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

    public void clearAll(){
        department.getSelectionModel().clearSelection();
        position.clear();
        stawka.clear();
        idDepartment=0;
        i = 0;
    }

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
                    allertBoxError("Stanowisko nie dodane", "Nie udało się dodać stanowisko. Sprobuj ponownie!!!");
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
    }

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
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }
}
