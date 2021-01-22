package salaryhistory;

import alerts.AlertUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import connectionDB.ConnectionDB;
import login.LoginPageController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Klasa tworząca historie wyplat
 */
public class HistoryController  implements Initializable {
    @FXML
    private TableView<HistoryRow> table;
    @FXML
    private TableColumn<HistoryRow, String> date;
    @FXML
    private TableColumn<HistoryRow, Integer> days;
    @FXML
    private TableColumn<HistoryRow, Double> brutto;
    @FXML
    private TableColumn<HistoryRow, String> premia;
    @FXML
    private TableColumn<HistoryRow, String> kara;
    @FXML
    private TableColumn<HistoryRow, String> bon;
    @FXML
    private TableColumn<HistoryRow, Double> total;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private final int idPracownik =  LoginPageController.log;

    int idPlaca =0;

    /**
     * Metoda wyświetlająca historię wyplat
     */
    public void hist(){
        date.setCellValueFactory(new PropertyValueFactory<HistoryRow, String>("date"));
        days.setCellValueFactory(new PropertyValueFactory<HistoryRow, Integer>("days"));
        brutto.setCellValueFactory(new PropertyValueFactory<HistoryRow, Double>("brutto"));
        premia.setCellValueFactory(new PropertyValueFactory<HistoryRow, String>("premia"));
        kara.setCellValueFactory(new PropertyValueFactory<HistoryRow, String>("kara"));
        bon.setCellValueFactory(new PropertyValueFactory<HistoryRow, String>("bon"));
        total.setCellValueFactory(new PropertyValueFactory<HistoryRow, Double>("total"));
        ObservableList<HistoryRow> list = FXCollections.observableArrayList();
        try {
            con = ConnectionDB.connectWithDB();
            String sql = "select * from historia_wyplat where id_pracownik=? order by miesiac,rok;";

            ps = con.prepareStatement(sql);
            ps.setInt(1,idPracownik);
            rs = ps.executeQuery();

            while (rs.next()) {
                String prem_str = "";
                String kara_str = "";
                String bon_str = "";
                idPlaca = rs.getInt("id_placa");

                PreparedStatement ps1;
                ResultSet rs1;
                String sql1 ="select * from naliczone_premie where id_placa=?";
                ps1 = con.prepareStatement(sql1);
                ps1.setInt(1,idPlaca);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    prem_str = prem_str + "+"+rs1.getDouble("kwota") + " ["+rs1.getString("opis")+", "+rs1.getDouble("procenty")+"%]\n";
                }
                ps1.close();
                rs1.close();

                String sql2 ="select * from naliczone_kary where id_placa=?";
                ps1 = con.prepareStatement(sql2);
                ps1.setInt(1,idPlaca);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    kara_str = kara_str + "-"+rs1.getDouble("kwota") + " ["+rs1.getString("opis")+", "+rs1.getDouble("procenty")+"%]\n";
                }
                ps1.close();
                rs1.close();

                String sql3 ="select * from udzielone_bony where id_placa=?";
                ps1 = con.prepareStatement(sql3);
                ps1.setInt(1,idPlaca);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    bon_str = bon_str + "["+rs1.getString("opis")+"]\n";
                }
                ps1.close();
                rs1.close();

                list.add(new HistoryRow(rs.getString("miesiac")+"."+rs.getString("rok"),
                        rs.getInt("ilosc_dni_robocz"),
                        rs.getDouble("brutto"),
                        prem_str, kara_str, bon_str,
                        rs.getDouble("wyplata")
                        ));
            }
            rs.close();
            ps.close();
            con.close();

        }
        catch (Exception e){
            AlertUp.allertBoxError("Błąd",e.getMessage());
        }
        finally {
            ConnectionDB.closeDB(con, ps, rs);
        }
        table.setItems(list);
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hist();
    }
}
