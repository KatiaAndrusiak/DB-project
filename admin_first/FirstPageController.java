package admin_first;

import alerts.AlertUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import setscene.SetScene;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa obsługująca stronę główną administratora
 */
public class FirstPageController implements Initializable {
    @FXML
    private Pane mainPane;
    SetScene scene = new SetScene();

    /**
     * Metoda, która sługuje do wylogowania użytkownika
     * @param event ActionEvent
     */
    public void wyloguj(ActionEvent event) {
        try {
            scene.setNewWindow(event, "/login/login_page.fxml");
        }
        catch (Exception e){
            AlertUp.allertBoxError("Błąd", "Nie udało się wyłogować"+e.getMessage());
        }
    }

    /**
     * Metoda zmieniająca panel na panel wyświetlający listę pracowników
     */
    @FXML
    public void employeeList(){
        scene.setNewPane(mainPane,"../employees_list/employeeList.fxml");
    }

    /**
     * Metoda zmieniająca panel na panel wyświetlający formularz do dodawanie pracownikow
     */
    @FXML
    public void addNewEmployee() {
        scene.setNewPane(mainPane,"../add/addEmployee.fxml");
    }

    /**
     * Metoda zmieniająca panel na panel wyświetlający formularz do dodawanie stanowisk
     */
    @FXML
    public void addNewDepartment() {
        scene.setNewPane(mainPane,"../add/addNewPosition.fxml");
    }

    /**
     * Metoda zmieniająca panel na panel wyświetlający formularz do dodawanie premii, karm bonusów
     */
    @FXML
    public void addNewBons() {
        scene.setNewPane(mainPane,"../add/addNewBons.fxml");
    }

    /**
     * Metoda zmieniająca panel na panel wyświetlający formularz do obliczania wynagrodzenia
     */
    @FXML
    public void countSalary() {
        scene.setNewPane(mainPane,"../count_salary/addBon.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
