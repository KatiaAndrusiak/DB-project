package validate;

import alerts.AlertUp;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa służąca do walidacji wprowadzonych danych
 */
public class ValidateField {

    /**
     * Walidacja pól gdzie oczekujemy tylko litery
     * @param data TextField
     * @return true jeśli wprowadzone dane są ok
     */
    public static boolean validateCharField(TextField data){
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(data.getText());
        if(m.find() && m.group().equals(data.getText())){
            return true;
        }
        else{
            AlertUp.allertBoxError(" Niepoprawnie wprowadzone dane! ","Wprowadź prawidłowe dane!!!");
            return false;
        }
    }

    /**
     * Walidacja pól gdzie oczekujemy procenty
     * @param data TextField
     * @return true jeśli wprowadzone dane są ok
     */
    public static boolean validatePercentField(TextField data){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(data.getText());
        if(m.find() && m.group().equals(data.getText()) && ((Integer.parseInt(data.getText())>0) && (Integer.parseInt(data.getText()) <=100))){
            return true;
        }
        else{
            AlertUp.allertBoxError("Procenty muszą być w przedziale[1-100] ","Sprawdź poprawność wprowadzonych danych.");
            return false;
        }
    }

    /**
     * Walidacja pól gdzie oczekujemy email
     * @param data TextField
     * @return true jeśli wprowadzone dane są ok
     */
    public static boolean validateEmail(TextField data) {
        Pattern p = Pattern.compile("^(.+)@(.+)$");
        Matcher m = p.matcher(data.getText());

        if(m.find() && m.group().equals(data.getText())){
            return true;
        }
        else{
            AlertUp.allertBoxError(" Niepoprawnie wprowadzony e-mail! ","Wprowadź prawidłowe dane!!!");
            return false;
        }
    }

    /**
     * Walidacja ComboBox
     * @param myComboBox ComboBox
     * @return true gdy gdy zotało coś wybrane
     */
    public static boolean validateComboBox(ComboBox myComboBox) {
        if( !myComboBox.getSelectionModel().isEmpty()){
            return true;
        }
        else {
            AlertUp.allertBoxError("Nie wprowadzone wszystkie dane! ","Wybierz z listy informacje!!!");
            return false;
        }
    }

    /**
     * Walidacja pól gdzie oczekujemy liczbę
     * @param data TextField
     * @return true jeśli wprowadzone dane są ok
     */
    public static boolean validateNum(TextField data){
        Pattern p = Pattern.compile("([0-9]+).([0-9]*)");
        Matcher m = p.matcher(data.getText());
        if(m.find() && m.group().equals(data.getText())){
            return true;
        }
        else{
            AlertUp.allertBoxError(" Niepoprawnie wprowadzona liczba.","Sprawdź poprawność wprowadzonych danych.");
            return false;
        }
    }
}
