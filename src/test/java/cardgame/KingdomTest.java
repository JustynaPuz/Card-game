package cardgame;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KingdomTest {
    Kingdom kingdom = new Kingdom();
    EnumMap<Attribute, Integer> newAttributes = new EnumMap<>( Map.of(
            Attribute.FOOD, 10,
            Attribute.ECONOMY, 20,
            Attribute.MILITARY, 30,
            Attribute.RELIGION,40

    ));

    @Test
    public void setAttributesTEST() {
        kingdom.setAttributes(newAttributes);
        assertEquals(kingdom.getAttributes(), newAttributes);

    }

}