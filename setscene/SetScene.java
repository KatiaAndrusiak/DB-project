package setscene;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Klasa służąca do ustawienia nowych scen
 */
public class SetScene {

    /**
     *Metoda, która zmienia okno
     * @param event ActionEvent
     * @param res ścieżka do nowego okna
     * @throws IOException
     */
    public void  setNewWindow(ActionEvent event, String res) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent login = FXMLLoader.load(getClass().getResource(res));
        Stage stage = new Stage();
        stage.setScene(new Scene(login));
        stage.show();
    }

    /**
     * Metoda, która zmienia panel
     * @param pane panel na którym rysuje się nowy
     * @param res ścieżka do nowego panelu
     */
    public void setNewPane(Pane pane, String res){
        Parent fxml= null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.getChildren().removeAll();
        pane.getChildren().addAll(fxml);
    }
}
