import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
	// write your code here
        Class.forName("org.sqlite.JDBC");

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contactmgr.db")) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS contacts");
            statement.executeUpdate("CREATE TABLE contacts(id INTEGER PRIMARY KEY, firstname STRING, lastname STRING, email STRING, phone INT(10))");
            /*statement.executeUpdate("INSERT INTO contacts (firstname, lastname, email, phone) VALUES ('Chris', 'Ramacciotti', 'cr@gmail.com', 7735554535)");
            statement.executeUpdate("INSERT INTO contacts (firstname, lastname, email, phone) VALUES ('James', 'Gosling', 'cr@java.com', 4555774885)");
            statement.executeUpdate("INSERT INTO contacts (firstname, lastname, email, phone) VALUES ('Damilare', 'Onasanya', 'dd@gmail.com', 772324233)");*/
            save(new Contact("Chris", "Ramacciotti", "cr@gmail.com", 7735554535L), statement);
            save(new Contact("James", "Gosling", "cr@java.com", 4555774885L), statement);
            save(new Contact("Damilare", "Onasanya", "dd@gmail.com", 772324233L), statement);

            ResultSet rs = statement.executeQuery("SELECT * FROM contacts");

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                System.out.printf("%s %s (%d)", firstname, lastname, id);
            }

        } catch (SQLException e) {
            // e.printStackTrace();
            System.err.printf("There was a database error %s%n", e.getMessage());
        }
    }

    public static void save(Contact contact, Statement statement) throws SQLException {
        String sql = "INSERT INTO contacts (firstname, lastname, email, phone) VALUES ('%s', '%s', '%s', %d)";
        sql = String.format(sql, contact.getFirstname(), contact.getLastname(), contact.getEmail(), contact.getPhone());

        statement.executeUpdate(sql);
    }
}
