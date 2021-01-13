package admin_first;

import javafx.scene.control.Alert;

public class AlertUp {

    public static void allertBoxError( String header, String content){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    public static void allertBoxInformation( String header, String content){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("OK");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}
