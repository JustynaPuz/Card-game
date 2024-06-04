package cardgame;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static cardgame.CardLoadUtil.loadCard;
import static org.junit.jupiter.api.Assertions.*;

class CardLoadUtilTest {
    List<Card> cards = loadCard("src/test/java/cardgame/testCSV.csv");

    @Test
    void loadOneCardTitleAndDescriptionTEST() {
        assertTrue(cards.get(0).getTitle().compareTo("Turniej Mistrzów Łucznictwa") == 0);
        assertTrue(cards.get(0).getDescription().compareTo(" W krolestwie odbedzie sie wielki turniej, w ktorym najlepsi łucznicy zmierza sie w rywalizacji. ") == 0);
    }

    @Test
    void loadOneCardAttributesValues() {
        EnumMap<Attribute, Integer> accepted = new EnumMap<>(Map.of(
                Attribute.FOOD, -10,
                Attribute.ECONOMY, 0,
                Attribute.MILITARY, 20,
                Attribute.RELIGION, 0

        ));
        EnumMap<Attribute, Integer> notAccepted = new EnumMap<>(Map.of(
                Attribute.FOOD, 10,
                Attribute.ECONOMY, -5,
                Attribute.MILITARY, 0,
                Attribute.RELIGION, -5

        ));
        assertEquals(cards.get(0).getAttributesAccepted(), accepted);
        assertEquals(cards.get(0).getAttributesNotAccepted(), notAccepted);

    }

}