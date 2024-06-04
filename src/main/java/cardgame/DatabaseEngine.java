package cardgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseEngine {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    public DatabaseEngine() {
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE  IF NOT EXISTS  CARD " +
                    "(id INTEGER not NULL, " +
                    " title VARCHAR(255), " +
                    " description TEXT, " +
                    "image BLOB,"+
                    " foodYes INTEGER, " +
                    " economyYes INTEGER, " +
                    " militaryYes INTEGER, " +
                    " religionYes INTEGER, " +
                    " foodNo INTEGER, " +
                    " economyNo INTEGER, " +
                    " militaryNo INTEGER, " +
                    " religionNo INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS TOPSCORE" +
                    "(name VARCHAR(50)," +
                    "score INTEGER)";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void closeDatabase() throws SQLException {
        stmt.close();
        conn.close();
    }

    public void insertCards(List<Card> cards) {
        try {
            String sql = "INSERT INTO CARD (id, title, description, image, foodYes, economyYes, militaryYes, religionYes, foodNo, economyNo, militaryNo, religionNo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Card card : cards) {
                EnumMap<Attribute, Integer> accepted = card.getAttributesAccepted();
                EnumMap<Attribute, Integer> notAccepted = card.getAttributesNotAccepted();

                pstmt.setInt(1, card.getId());
                pstmt.setString(2, card.getTitle());
                pstmt.setString(3, card.getDescription());
                pstmt.setBytes(4,card.getImage());
                pstmt.setInt(5, accepted.get(Attribute.FOOD));
                pstmt.setInt(6, accepted.get(Attribute.ECONOMY));
                pstmt.setInt(7, accepted.get(Attribute.MILITARY));
                pstmt.setInt(8, accepted.get(Attribute.RELIGION));
                pstmt.setInt(9, notAccepted.get(Attribute.FOOD));
                pstmt.setInt(10, notAccepted.get(Attribute.ECONOMY));
                pstmt.setInt(11, notAccepted.get(Attribute.MILITARY));
                pstmt.setInt(12, notAccepted.get(Attribute.RELIGION));

                pstmt.executeUpdate();
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void insertTopScore(String nameStr, Integer days) throws SQLException {

        String selectSql = "SELECT score FROM TOPSCORE WHERE name = ?";
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        selectStmt.setString(1, nameStr);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            int existingScore = rs.getInt("score");
            if (days > existingScore) {

                String updateSql = "UPDATE TOPSCORE SET score = ? WHERE name = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, days);
                updateStmt.setString(2, nameStr);
                updateStmt.executeUpdate();
            }
        } else {
            String insertSql = "INSERT INTO TOPSCORE (name, score) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, nameStr);
            insertStmt.setInt(2, days);
            insertStmt.executeUpdate();
        }
    }

    public Card getCardById(int cardId) {
        String query = "SELECT * FROM CARD WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    byte[] image = rs.getBytes("image");
                    EnumMap<Attribute, Integer> accepted = new EnumMap<>(Attribute.class);
                    EnumMap<Attribute, Integer> notAccepted = new EnumMap<>(Attribute.class);

                    accepted.put(Attribute.FOOD, rs.getInt("foodYes"));
                    accepted.put(Attribute.ECONOMY, rs.getInt("economyYes"));
                    accepted.put(Attribute.MILITARY, rs.getInt("militaryYes"));
                    accepted.put(Attribute.RELIGION, rs.getInt("religionYes"));
                    notAccepted.put(Attribute.FOOD, rs.getInt("foodNo"));
                    notAccepted.put(Attribute.ECONOMY, rs.getInt("economyNo"));
                    notAccepted.put(Attribute.MILITARY, rs.getInt("militaryNo"));
                    notAccepted.put(Attribute.RELIGION, rs.getInt("religionNo"));

                    return new Card(id, title, description,image,  accepted, notAccepted);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getNumberOfCards() {
        String query = "SELECT MAX(id) AS max_id FROM CARD";
        int maxId = -1;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }
    public Map<String, Integer> getTopScore() throws SQLException {
        String sql = "SELECT name, score FROM TOPSCORE ORDER BY score DESC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Map<String, Integer> topScores = new HashMap<>();
        while (rs.next()) {
            topScores.put(rs.getString("name"), rs.getInt("score"));

        }
        return topScores;
    }

}




