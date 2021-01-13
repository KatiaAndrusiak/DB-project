package admin_first;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Year;
import java.util.Arrays;
import java.util.ResourceBundle;

import static admin_first.ValidateField.*;
import static admin_first.ValidateField.validateComboBox;
import static login.ConnectionDB.connectWithDB;

public class AddBonController implements Initializable {
    @FXML
    private ComboBox<String> department;
    @FXML
    private ComboBox<String> employee;
    @FXML
    private TextField year;
    @FXML
    private ComboBox<String> month;
    @FXML
    private TextField days;
    @FXML
    private ListView<String> premie;
    @FXML
    private ListView<String> bonus;
    @FXML
    private ListView<String> kara;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private int idDepartment = 0;
    ObservableList<String> months = FXCollections.observableArrayList("Styczeń", "Luty",
            "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik",
            "Listopad", "Grudzień");
//    public void  l(){
//        TableView.TableViewSelectionModel<Employee> selectionModel = table.getSelectionModel();
//        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
//        ObservableList<Employee> selectedItems = selectionModel.getSelectedItems();
//        System.out.println(Arrays.toString(selectedItems.toArray()));
//    }

    public void countSalary(){
        try {
//            con = connectWithDB();
//
//           if(validateNum(year) && validateNum(days) && validateComboBox(department) && validateComboBox(employee)
//           && validateComboBox(month)){
//                    && validateComboBox(department) && validateComboBox(position) && validateComboBox(etat)) {
//                System.out.println("ok!!");
//                idEtat = etat.getSelectionModel().getSelectedIndex() + 1;
//
//                String sql = "select * from stanowisko where stanowisko = ?";
//                ps = con.prepareStatement(sql);
//                ps.setString(1, position.getSelectionModel().getSelectedItem());
//                rs = ps.executeQuery();
//                if (rs.next()) {
//                    System.out.println(idEtat + "  " + idDepartment + " " + rs.getInt("id_stanowisko"));
//                    idPosition = rs.getInt("id_stanowisko");
//               }
//
//                ps.close();
//                rs.close();
        }
            catch(Exception e){

            }
    }

    public void setEmployeeByDep(){
        employee.getItems().clear();
        try{
            con = connectWithDB();
            String sql = "select * from lista_pracownikow where opis =?";
            ps = con.prepareStatement(sql);

            idDepartment = department.getSelectionModel().getSelectedIndex()+1;
            ps.setString(1, department.getSelectionModel().getSelectedItem());
            rs = ps.executeQuery();
            while (rs.next()) {
                String tmp = rs.getString("imie") + " " +rs.getString("nazwisko")+", "+rs.getString("Stanowisko");
                employee.getItems().add(tmp);
            }
            employee.setDisable(false);
            ps.close();
            rs.close();
            con.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void initData()
    {
        int yr = Year.now().getValue();
        year.setText(yr + "");
        employee.setDisable(true);
        month.getItems().addAll(months);
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
