package cardgame;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {
    Engine engine = new Engine();
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
    public void generateCardEffectTEST() {
        EnumMap<Attribute, Integer> attributesBefore = new EnumMap<>(engine.getKingdomAttributes());
        engine.generateCardEffect(card, true);
        EnumMap<Attribute, Integer> attributesAfter = new EnumMap<>(engine.getKingdomAttributes());

        assertNotEquals(attributesBefore.get(Attribute.FOOD), attributesAfter.get(Attribute.FOOD));
        assertNotEquals(attributesBefore.get(Attribute.ECONOMY), attributesAfter.get(Attribute.ECONOMY));
        assertNotEquals(attributesBefore.get(Attribute.MILITARY), attributesAfter.get(Attribute.MILITARY));
        assertNotEquals(attributesBefore.get(Attribute.RELIGION), attributesAfter.get(Attribute.RELIGION));


    }

}