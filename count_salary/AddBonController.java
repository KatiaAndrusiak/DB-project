package count_salary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ResourceBundle;

import static admin_first.AlertUp.allertBoxError;
import static admin_first.AlertUp.allertBoxInformation;
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
    @FXML
    private Label sum;
    @FXML
    private Label total;
    String ltotal;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private int idDepartment = 0;
    ObservableList<String> months = FXCollections.observableArrayList("Styczeń", "Luty",
            "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik",
            "Listopad", "Grudzień");
    int i = 0;
    int j = 0;
    int idPlaca =0;

    public void clearAll(){
        department.getSelectionModel().clearSelection();
        employee.getSelectionModel().clearSelection();
        month.getSelectionModel().clearSelection();
        days.clear();
        premie.getSelectionModel().clearSelection();
        bonus.getSelectionModel().clearSelection();
        kara.getSelectionModel().clearSelection();
        sum.setText("");
        total.setText("");
    }


    public void countSalary(){

        try {
            con = connectWithDB();

           if(validateNum(year) && validateNum(days) && validateComboBox(department) && validateComboBox(employee)
           && validateComboBox(month)) {


                String idEmployee = employee.getSelectionModel().getSelectedItem().split(" ")[0];
                System.out.println("ok!!   "+ idEmployee);


                String sql = "SELECT insert_placa(?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, month.getSelectionModel().getSelectedIndex()+1);
                ps.setInt(2, Integer.parseInt(year.getText()));
                ps.setInt(3, Integer.parseInt(days.getText()));
                ps.setInt(4, Integer.parseInt(idEmployee));
                rs = ps.executeQuery();
                if (rs.next()) {
                    idPlaca = rs.getInt(1);
                }
               ps.close();
               rs.close();

               double m_salary = 0;
               double salary = 0;
               String sql2 = "SELECT wyplata_mies(?,?)";
               ps = con.prepareStatement(sql2);
               ps.setInt(1, idPlaca);
               ps.setInt(2, Integer.parseInt(idEmployee));

               rs = ps.executeQuery();
               if (rs.next()) {
                   m_salary = rs.getInt(1);
               }
               ps.close();
               rs.close();

               ltotal = "Wynagrodzenie za miesiąc: " + m_salary + "\n";
               salary = m_salary;

               if(premie.getSelectionModel().getSelectedItem() != null){
                   ObservableList<String> selectedItems = premie.getSelectionModel().getSelectedItems();
                   System.out.println(premie.getSelectionModel().getSelectedItems());
                   int p = 0;
                   for (String a: selectedItems) {
                       String sql4 = "select * from premia where opis = ?";
                       ps = con.prepareStatement(sql4);
                       ps.setString(1, a);
                       rs = ps.executeQuery();
                       if (rs.next()) {
                           salary += m_salary * rs.getDouble("procenty");
                           ltotal = ltotal + "Premia "+ a +": +" + m_salary * rs.getDouble("procenty") + "\n";

                           String sql1 = "Insert Into pl_premia (id_placa, id_premia) values (?,?)";
                           ps = con.prepareStatement(sql1);
                           ps.setInt(1, idPlaca);
                           ps.setInt(2, rs.getInt("id_premia"));
                           ps.executeUpdate();
                           ps.close();
                       }
                       System.out.println("Miesiac+Premie   "+salary);
                       ps.close();
                       rs.close();
                   }

               }


               if(kara.getSelectionModel().getSelectedItem() != null){
                   System.out.println(kara.getSelectionModel().getSelectedItems());
                   ObservableList<String> selectedItems = kara.getSelectionModel().getSelectedItems();
                   int k = 0;
                   for (String a: selectedItems) {
                       String sql4 = "select * from kara where opis = ?";
                       ps = con.prepareStatement(sql4);
                       ps.setString(1, a);
                       rs = ps.executeQuery();
                       if (rs.next()) {
                           salary -= m_salary * rs.getDouble("procenty");
                           ltotal = ltotal + "Kara "+ a +": -" + m_salary * rs.getDouble("procenty") + "\n";

                           String sql1 = "Insert Into pl_kara (id_placa, id_kara) values (?,?)";
                           ps = con.prepareStatement(sql1);
                           ps.setInt(1, idPlaca);
                           ps.setInt(2,rs.getInt("id_kara"));
                           ps.executeUpdate();
                           ps.close();
                       }
                       System.out.println("Miesiac+Premie+Kary   " + salary);

                       ps.close();
                       rs.close();
                   }
               }

               if(bonus.getSelectionModel().getSelectedItem() != null){
                   System.out.println(bonus.getSelectionModel().getSelectedItems());
                   ObservableList<String> selectedItems = bonus.getSelectionModel().getSelectedItems();
                   int b = 0;
                   for (String a: selectedItems) {
                       String sql4 = "select * from bonus where opis = ?";
                       ps = con.prepareStatement(sql4);
                       ps.setString(1, a);
                       rs = ps.executeQuery();
                       if (rs.next()) {
                           String sql1 = "Insert Into pl_bon (id_placa, id_bonus) values (?,?)";
                           ps = con.prepareStatement(sql1);
                           ps.setInt(1, idPlaca);
                           ps.setInt(2,rs.getInt("id_bonus"));
                           ps.executeUpdate();
                           ps.close();
                           ltotal = ltotal + "Bonus: " + a + "\n";
                       }
                   }
               }

               String sql5 = "UPDATE placa SET wyplata = ? WHERE id_placa = ?";
               ps = con.prepareStatement(sql5);
               ps.setDouble(1, salary);
               ps.setInt(2,idPlaca);
               ps.executeUpdate();

               sum.setText(salary+" zł");
               total.setText(ltotal);

                ps.close();
                rs.close();
                con.close();
               allertBoxInformation("Wypłata została dodana.","OK!");
           }

        }catch (SQLException e){
            allertBoxError("SQLException", e.getMessage());
        }catch(Exception e){
            allertBoxError("Płaca nie dodana", e.getMessage());
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
                String tmp = rs.getInt("id_pracownik")+" "+rs.getString("imie") + " " +rs.getString("nazwisko")+", "+rs.getString("Stanowisko");
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
        ObservableList<String> bon = FXCollections.observableArrayList();
        ObservableList<String> kar = FXCollections.observableArrayList();
        ObservableList<String> pr = FXCollections.observableArrayList();
        premie.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bonus.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        kara.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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


            String sql1 = "select * from bonus";
            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();

            while (rs.next()) {
                bon.add(rs.getString("opis"));
            }
            bonus.setItems(bon);
            ps.close();
            rs.close();

            String sql2 = "select * from kara";
            ps = con.prepareStatement(sql2);
            rs = ps.executeQuery();

            while (rs.next()) {
                kar.add(rs.getString("opis"));
            }
            kara.setItems(kar);
            ps.close();
            rs.close();

            String sql3 = "select * from premia";
            ps = con.prepareStatement(sql3);
            rs = ps.executeQuery();

            while (rs.next()) {
                pr.add(rs.getString("opis"));
            }
            premie.setItems(pr);
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
