package add;

import connectionDB.ConnectionDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import static alerts.AlertUp.*;
import static alerts.AlertUp.allertBoxInformation;
import static validate.ValidateField.*;
import static connectionDB.ConnectionDB.connectWithDB;

/**
 * Klasa obsługująca dodawanie pracowników do bazy danych
 */
public class AddEmployeeController implements Initializable {
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
    private Label plogin;
    @FXML
    private Label phaslo;



    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private int idDepartment = 0;
    private int idPrac = 0;

    private int i= 0;
    private int j= 0;

    /**
     * Metoda słuząca do wyczyszczenia pól
     */
    public void clearAll(){
        name.clear();
        surname.clear();
        city.clear();
        email.clear();
        department.getSelectionModel().clearSelection();
        position.getSelectionModel().clearSelection();
        etat.getSelectionModel().clearSelection();
        idDepartment = 0;
        idPrac = 0;
        i= 0;
        j= 0;

        plogin.setText(" ");
        phaslo.setText(" ");
    }

    /**
     * Metoda obsługująca dodawanie pracowników do bazy danych
     */
    public void addEmployeeToDB()
    {
        try{
            con = connectWithDB();

            if(validateCharField(name) && validateCharField(surname) && validateCharField(city) && validateEmail(email)
                    && validateComboBox(department) && validateComboBox(position) && validateComboBox(etat)) {
                System.out.println("ok!!");


                String sql = "select  dodaj_pracownik(?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, surname.getText());
                ps.setString(3, city.getText());
                ps.setString(4, email.getText());
                ps.setString(5, department.getSelectionModel().getSelectedItem());
                ps.setString(6, position.getSelectionModel().getSelectedItem());
                ps.setString(7, etat.getSelectionModel().getSelectedItem().split(" \\(" )[0]);
                rs = ps.executeQuery();
                if (rs.next()) {
                    idPrac = rs.getInt(1);
                    i++;
                }
                ps.close();
                rs.close();

                String login = "p" + idPrac + surname.getText() + "";
                String haslo = idPrac + surname.getText() + "";

                String sql1 = "insert into prac_login values (?,?,?)";
                ps = con.prepareStatement(sql1);
                ps.setInt(1, idPrac);
                ps.setString(2, login);
                ps.setString(3, haslo);
                j = ps.executeUpdate();

                ps.close();
                rs.close();

                if (i > 0 && j >0) {
                    plogin.setText(login);
                    phaslo.setText(haslo);
                    allertBoxInformation("Pracownik został dodany","OK!");

                } else {
                    throw new Exception("Nie udało się dodać pracownika. Sprobuj ponownie!!!");
                }
                rs.close();
                ps.close();

            }
            con.close();

        }catch (SQLException e){
            allertBoxError("SQLException", e.getMessage());
        }
        catch(Exception e){
            allertBoxError("Pracownik nie dodany", e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
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
            position.setDisable(false);
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

    /**
     * Metoda służy do inicjalizacji danych na tej stronie(panelu)
     */
    private void initData()
    {
        position.setDisable(true);
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
