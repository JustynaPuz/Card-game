package cardgame;

public class SQLCommands {
    public static final String createTableCard = "CREATE TABLE  IF NOT EXISTS  CARD " +
            "(id INTEGER not NULL, " +
            " title VARCHAR(255), " +
            " description TEXT, " +
            "image BLOB," +
            " foodYes INTEGER, " +
            " economyYes INTEGER, " +
            " militaryYes INTEGER, " +
            " religionYes INTEGER, " +
            " foodNo INTEGER, " +
            " economyNo INTEGER, " +
            " militaryNo INTEGER, " +
            " religionNo INTEGER, " +
            " PRIMARY KEY ( id ))";
    public static final String createTableTopScore = "CREATE TABLE IF NOT EXISTS TOPSCORE" +
            "(name VARCHAR(50)," +
            "score INTEGER)";
    public static final String insertCard = "INSERT INTO CARD (id, title, description, image, foodYes, economyYes, militaryYes, religionYes, foodNo, economyNo, militaryNo, religionNo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String getTopSoreByName = "SELECT score FROM TOPSCORE WHERE name = ?";
    public static final String updateTopScore = "UPDATE TOPSCORE SET score = ? WHERE name = ?";
    public static final String insertTopScore = "INSERT INTO TOPSCORE (name, score) VALUES (?, ?)";
    public static final String getCardById = "SELECT * FROM CARD WHERE id = ?";
    public static final String getNumberOfCards = "SELECT MAX(id) AS max_id FROM CARD";
    public static final String getTopScores  = "SELECT name, score FROM TOPSCORE ORDER BY score DESC";
}
