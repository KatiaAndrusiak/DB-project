package alerts;

import javafx.scene.control.Alert;

/**
 * Klasa obsługująca dostarczenie komunikatów do użytkownika
 */
public class AlertUp {
    /**
     *Metoda, która wyświetla komunikat o błędzie
     * @param header zawartość nagłówka
     * @param content główna zawartość
     */
    public static void allertBoxError( String header, String content){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    /**
     *Metoda, która wyświetla komunikat
     * @param header zawartość nagłówka
     * @param content główna zawartość
     */
    public static void allertBoxInformation( String header, String content){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("OK");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}
