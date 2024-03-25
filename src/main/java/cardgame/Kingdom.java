package cardgame;

import java.util.EnumMap;
import java.util.Random;

public class Kingdom {
    private EnumMap<Attribute, Integer> attributes = new EnumMap<>(Attribute.class);

    public Kingdom() {
        Random rand = new Random();
        for(Attribute attribute : Attribute.values()) {
            int randomValue = rand.nextInt(40,91);
            attributes.put(attribute,randomValue);

        }
    }
}
