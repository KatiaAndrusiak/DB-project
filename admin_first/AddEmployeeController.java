package admin_first;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import static admin_first.AlertUp.*;
import static admin_first.AlertUp.allertBoxInformation;
import static admin_first.ValidateField.*;
import static login.ConnectionDB.connectWithDB;

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
    @FXML
    private Button bok;
    @FXML
    private Button addtoDB;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private int idDepartment = 0;
    private int idPrac = 0;
    private int idPosition = 0;
    private int idEtat = 0;
    private int i= 0;
    private int j= 0;
    private String login;
    private String haslo;

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
        idPosition = 0;
        idEtat = 0;
        i= 0;
        j= 0;
    }

    public void addEmployeeToDB(ActionEvent event)
    {

        try{
            con = connectWithDB();

            if(validateCharField(name) && validateCharField(surname) && validateCharField(city) && validateEmail(email)
                    && validateComboBox(department) && validateComboBox(position) && validateComboBox(etat)) {
                System.out.println("ok!!");
                idEtat = etat.getSelectionModel().getSelectedIndex() + 1;

                String sql = "select * from stanowisko where stanowisko = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, position.getSelectionModel().getSelectedItem());
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println(idEtat + "  " + idDepartment + " " + rs.getInt("id_stanowisko"));
                    idPosition = rs.getInt("id_stanowisko");
                }

                ps.close();
                rs.close();

                String sql1 = "insert into pracownik(imie, nazwisko, miasto, email ) values (?,?,?,?)";
                ps = con.prepareStatement(sql1);
                ps.setString(1, name.getText());
                ps.setString(2, surname.getText());
                ps.setString(3, city.getText());
                ps.setString(4, email.getText());

                i = ps.executeUpdate();
                ps.close();
                rs.close();

                String sql2 = "select * from pracownik where imie=? and nazwisko = ? and miasto = ? and email =?";
                ps = con.prepareStatement(sql2);
                ps.setString(1, name.getText());
                ps.setString(2, surname.getText());
                ps.setString(3, city.getText());
                ps.setString(4, email.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println( rs.getInt("id_pracownik"));
                    idPrac = rs.getInt("id_pracownik");
                }
                String sql3 = "insert into prac_stan values (?,?,?,?)";
                ps = con.prepareStatement(sql3);
                ps.setInt(1, idPrac);
                ps.setInt(2, idEtat);
                ps.setInt(3, idPosition );
                ps.setInt(4, idDepartment);

                j = ps.executeUpdate();
                ps.close();
                rs.close();
                login ="p" + idPrac+ surname.getText()+"";
                haslo = idPrac + surname.getText()+"";
                if (i > 0 && j>0) {
                    bok.setDisable(false);
                    plogin.setText(login);
                    phaslo.setText(haslo);
                    allertBoxInformation("Pracownik został dodany","OK!");

                } else {
                    allertBoxError("Pracownik nie dodany", "Nie udało się dodać pracownika. Sprobuj ponownie!!!");
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
    }


    private void initData()
    {
        position.setDisable(true);
        bok.setDisable(true);
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
    }

    public void addPositionsByDepartment(ActionEvent event)
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

    }

    public void addLoginToDB(ActionEvent event){
        Connection con=null;
        PreparedStatement ps = null;

        try {
            con = connectWithDB();
            String sql = "insert into prac_login values (?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPrac);
            ps.setString(2, login);
            ps.setString(3, haslo);
            i = ps.executeUpdate();
            if (i > 0 ) {
                bok.setDisable(false);
                plogin.setText(login);
                phaslo.setText(haslo);
                allertBoxInformation("Login został dodany","OK!");

            } else {
                allertBoxError("Login nie dodany", "Nie udało się dodać login. Sprobuj ponownie!!!");
            }
            ps.close();
            rs.close();
            con.close();
            clearAll();

        }
        catch (Exception e){
            allertBoxError("Login nie dodany", e.getMessage());
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }
}
