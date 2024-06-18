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
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = SQLCommands.createTableCard;
            stmt.executeUpdate(sql);
            sql = SQLCommands.createTableTopScore;
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            System.exit(-1);
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
            String sql = SQLCommands.insertCard;
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Card card : cards) {
                EnumMap<Attribute, Integer> accepted = card.getAttributesAccepted();
                EnumMap<Attribute, Integer> notAccepted = card.getAttributesNotAccepted();

                pstmt.setInt(1, card.getId());
                pstmt.setString(2, card.getTitle());
                pstmt.setString(3, card.getDescription());
                pstmt.setBytes(4, card.getImage());
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

        String selectSql = SQLCommands.getTopSoreByName;
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        selectStmt.setString(1, nameStr);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            int existingScore = rs.getInt("score");
            if (days > existingScore) {

                String updateSql = SQLCommands.updateTopScore;
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, days);
                updateStmt.setString(2, nameStr);
                updateStmt.executeUpdate();
            }
        } else {
            String insertSql = SQLCommands.insertTopScore;
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, nameStr);
            insertStmt.setInt(2, days);
            insertStmt.executeUpdate();
        }
    }
    public Card getCardById(int cardId) {
        String query = SQLCommands.getCardById;
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

                    return new Card(id, title, description, image, accepted, notAccepted);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getNumberOfCards() {
        String query = SQLCommands.getNumberOfCards;
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
        String sql = SQLCommands.getTopScores;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Map<String, Integer> topScores = new HashMap<>();
        while (rs.next()) {
            topScores.put(rs.getString("name"), rs.getInt("score"));

        }
        return topScores;
    }

}




