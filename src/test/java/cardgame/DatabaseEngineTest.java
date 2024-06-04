package cardgame;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseEngineTest {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    Connection conn = null;
    Statement stmt = null;
    DatabaseEngine db = new DatabaseEngine();

    @Test
    void insertCardsAndGetCardTEST() {
        List<Card> cards = CardLoadUtil.loadCard("src/test/java/cardgame/testCSV.csv");
        db.insertCards(cards);
        Card cardFromdb = db.getCardById(1);
        assertEquals(cards.get(0).getTitle(), cardFromdb.getTitle());
        assertEquals(cards.get(0).getDescription(), cardFromdb.getDescription());
        assertEquals(cards.get(0).getAttributesAccepted(), cardFromdb.getAttributesAccepted());
        assertEquals(cards.get(0).getAttributesNotAccepted(), cardFromdb.getAttributesNotAccepted());

    }

    @Test
    void getNumberOfCardsTEST() {
        int number = db.getNumberOfCards();
        assertEquals(20,number);
    }

}