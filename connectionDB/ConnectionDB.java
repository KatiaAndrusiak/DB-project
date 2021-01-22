package connectionDB;

import alerts.AlertUp;
import java.sql.*;

/**
 * Klasa obsługująca polączenie z bazą danych
 */
public class ConnectionDB {

    private static final String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/tmuowoqz";
    private static final String user = "tmuowoqz";
    private static final String pass = "NnuexgMYQ1cqDtxYW4Csor3FhhRDc_nY";

    /**
     * Metoda służąca do polączenia się z bazą danuch
     * @return Connection
     * @see Connection
     */
    public static Connection connectWithDB() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Polaczenie z baza danych OK ! ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Brak polaczenia z baza danych.");
            AlertUp.allertBoxError("Brak polączenia z bazą danych!!!","Brak polączenia z bazą danych! Sprobuj ponownie!");
        }
        return conn;
    }

    /**
     * Metoda służąca do polączenia się z bazą danuch
     * @param con Connection
     * @param ps PreparedStatement
     * @param rs ResultSet
     * @see Connection
     * @see PreparedStatement
     * @see ResultSet
     * @see SQLException
     */
    public static void closeDB(Connection con, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) { System.out.println(e.getMessage()); }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) { System.out.println(e.getMessage());}
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) { System.out.println(e.getMessage());}
        }
    }
}
