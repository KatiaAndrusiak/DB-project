package admin_first;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateField {

    public static boolean validateCharField(TextField data){
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(data.getText());
        if(m.find() && m.group().equals(data.getText())){
            return true;
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Dane niepoprawne");
            a.setHeaderText(" Niepoprawnie wprowadzone dane! ");
            a.setContentText("Wprowadź prawidłowe dane!!!");
            a.showAndWait();
            return false;
        }
    }

    public static boolean validatePercentField(TextField data){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(data.getText());
        if(m.find() && m.group().equals(data.getText()) && ((Integer.parseInt(data.getText())>0) && (Integer.parseInt(data.getText()) <=100))){
            return true;
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Dane niepoprawne");
            a.setHeaderText("Procenty muszą być w przedziale[1-100] ");
            a.setContentText("Sprawdź poprawność wprowadzonych danych.");
            a.showAndWait();
            return false;
        }
    }

    public static boolean validateEmail(TextField data) {
        Pattern p = Pattern.compile("^(.+)@(.+)$");
        Matcher m = p.matcher(data.getText());

        if(m.find() && m.group().equals(data.getText())){
            return true;
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Dane niepoprawne");
            a.setHeaderText(" Niepoprawnie wprowadzony e-mail! ");
            a.setContentText("Wprowadź prawidłowe dane!!!");
            a.showAndWait();
            return false;
        }
    }

    public static boolean validateComboBox(ComboBox myComboBox) {
        if( !myComboBox.getSelectionModel().isEmpty()){
            return true;
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Dane niepoprawne");
            a.setHeaderText(" Nie wprowadzone wszystkie dane! ");
            a.setContentText("Wybierz z listy informacje!!!");
            a.showAndWait();
            return false;
        }
    }

    public static boolean validateNum(TextField data){
        Pattern p = Pattern.compile("([0-9]+).([0-9]*)");
        Matcher m = p.matcher(data.getText());
        if(m.find() && m.group().equals(data.getText())){
            return true;
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText(" Niepoprawnie wprowadzona liczba.");
            a.setContentText("Sprawdź poprawność wprowadzonych danych.");
            a.showAndWait();
            return false;
        }
    }
}
