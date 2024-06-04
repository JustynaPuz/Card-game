package cardgame;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTest {
    byte[] image;
    EnumMap<Attribute, Integer> accepted = new EnumMap<>( Map.of(
            Attribute.FOOD, 10,
            Attribute.ECONOMY, 20,
            Attribute.MILITARY, 30,
            Attribute.RELIGION,40

    ));
    EnumMap<Attribute, Integer> notAccepted = new EnumMap<>( Map.of(
            Attribute.FOOD, -10,
            Attribute.ECONOMY, -20,
            Attribute.MILITARY, -30,
            Attribute.RELIGION,-40

    ));

    Card card = new Card(1,"Title", "Description", image, accepted, notAccepted);

    @Test
    void getAcceptedAttributes() {
        EnumMap<Attribute, Integer> atr = card.getAttributesAccepted();
        assertEquals(10, atr.get(Attribute.FOOD));
        assertEquals(20, atr.get(Attribute.ECONOMY));
        assertEquals(30, atr.get(Attribute.MILITARY));
        assertEquals(40, atr.get(Attribute.RELIGION));
    }
    @Test
    void getNotAcceptedAttributesTEST() {
        EnumMap<Attribute, Integer> atr = card.getAttributesNotAccepted();
        assertEquals(-10, atr.get(Attribute.FOOD));
        assertEquals(-20, atr.get(Attribute.ECONOMY));
        assertEquals(-30, atr.get(Attribute.MILITARY));
        assertEquals(-40, atr.get(Attribute.RELIGION));
    }


    @Test
    void generateRandomCardImpactRangeOfAttributesAcceptedTEST() {
        EnumMap<Attribute, Integer> atr = card.generateRandomCardImpact(true);
        assertTrue(atr.get(Attribute.FOOD) > card.getAttributesAccepted().get(Attribute.FOOD)-7);
        assertTrue(atr.get(Attribute.FOOD) < card.getAttributesAccepted().get(Attribute.FOOD)+7);
        assertTrue(atr.get(Attribute.ECONOMY) > card.getAttributesAccepted().get(Attribute.ECONOMY)-7);
        assertTrue(atr.get(Attribute.ECONOMY) < card.getAttributesAccepted().get(Attribute.ECONOMY)+7);
        assertTrue(atr.get(Attribute.MILITARY) > card.getAttributesAccepted().get(Attribute.MILITARY)-7);
        assertTrue(atr.get(Attribute.MILITARY) < card.getAttributesAccepted().get(Attribute.MILITARY)+7);
        assertTrue(atr.get(Attribute.RELIGION) > card.getAttributesAccepted().get(Attribute.RELIGION)-7);
        assertTrue(atr.get(Attribute.RELIGION) < card.getAttributesAccepted().get(Attribute.RELIGION)+7);

    }
@Test
    void generateRandomCardImpactRangeOfAttributesNotAcceptedTEST() {
        EnumMap<Attribute, Integer> atr = card.generateRandomCardImpact(false);
        assertTrue(atr.get(Attribute.FOOD) > card.getAttributesNotAccepted().get(Attribute.FOOD)-7);
        assertTrue(atr.get(Attribute.FOOD) < card.getAttributesNotAccepted().get(Attribute.FOOD)+7);
        assertTrue(atr.get(Attribute.ECONOMY) > card.getAttributesNotAccepted().get(Attribute.ECONOMY)-7);
        assertTrue(atr.get(Attribute.ECONOMY) < card.getAttributesNotAccepted().get(Attribute.ECONOMY)+7);
        assertTrue(atr.get(Attribute.MILITARY) > card.getAttributesNotAccepted().get(Attribute.MILITARY)-7);
        assertTrue(atr.get(Attribute.MILITARY) < card.getAttributesNotAccepted().get(Attribute.MILITARY)+7);
        assertTrue(atr.get(Attribute.RELIGION) > card.getAttributesNotAccepted().get(Attribute.RELIGION)-7);
        assertTrue(atr.get(Attribute.RELIGION) < card.getAttributesNotAccepted().get(Attribute.RELIGION)+7);

    }
    @Test
    void generateRandomCardImpactZerosInAttributesValuesTEST() {

        EnumMap<Attribute, Integer> atrZeros = new EnumMap<>( Map.of(
                Attribute.FOOD, 0,
                Attribute.ECONOMY, 0,
                Attribute.MILITARY, 0,
                Attribute.RELIGION,0

        ));
        Card cardWithZeros = new Card(2,"", "", image, atrZeros, atrZeros);
        EnumMap<Attribute, Integer> atrZerosOutput = cardWithZeros.generateRandomCardImpact(true);
        assertEquals(0,atrZerosOutput.get(Attribute.FOOD) );
        assertEquals(0, atrZerosOutput.get(Attribute.ECONOMY));
        assertEquals(0,atrZerosOutput.get(Attribute.MILITARY) );
        assertEquals(0,atrZerosOutput.get(Attribute.RELIGION) );
    }

}