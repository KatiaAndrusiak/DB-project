package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static login.ConnectionDB.connectWithDB;

public class LoginPageController implements Initializable {
    @FXML
    private Button log_button;
    @FXML
    private TextField  login;
    @FXML
    private TextField  haslo;


    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public static String log;



    public void zaloguj(ActionEvent event) throws Exception {
        try {
            con = connectWithDB();

            String sql = "SELECT * FROM public.Admin WHERE login=? and haslo=?";
            ps = con.prepareStatement(sql);
            log = login.getText();
            ps.setString(1, login.getText());
            ps.setString(2, haslo.getText());

            rs = ps.executeQuery();
            if(rs.next()) {
                ((Node) event.getSource()).getScene().getWindow().hide();
                Parent login = FXMLLoader.load(getClass().getResource("/admin_first/first_page.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(login));
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Problem z logowaniem się!!");
                alert.setContentText("Sprawdź poprawność loginu i hasła!");
                alert.showAndWait();
            }
            con.close();
            ps.close();
            rs.close();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Problem z logowaniem się!!");
            alert.setContentText("Nie udało się zalogować! Sprobuj ponownie!" + e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
