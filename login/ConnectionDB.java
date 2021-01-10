package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/tmuowoqz";
    private static final String user = "tmuowoqz";
    private static final String pass = "NnuexgMYQ1cqDtxYW4Csor3FhhRDc_nY";

    public static Connection connectWithDB() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Polaczenie z baza danych OK ! ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Brak polaczenia z baza danych.");
        }
        return conn;
    }
}
