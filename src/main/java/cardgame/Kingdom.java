package cardgame;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Random;

@Getter
public class Kingdom {
    @Setter
    private EnumMap<Attribute, Integer> attributes = new EnumMap<>(Attribute.class);

    public Kingdom() {
        Random rand = new Random();
        for (Attribute attribute : Attribute.values()) {
            int randomValue = rand.nextInt(40, 91);
            attributes.put(attribute, randomValue);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Attribute atr : attributes.keySet()) {
            output.append(atr.toString()).append(" ").append(attributes.get(atr)).append("\n");
        }
        return output.toString();
    }

}
