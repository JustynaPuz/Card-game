package cardgame;

import java.util.EnumMap;
import java.util.Random;

public class Kingdom {

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
        for(Attribute atr : attributes.keySet()) {
            output.append(atr.toString()).append(" ").append(attributes.get(atr)).append("\n");
        }
        return output.toString();
    }

    public void setAttributes(EnumMap<Attribute, Integer> newAttributes) {
        attributes = newAttributes;

    }

    public EnumMap<Attribute, Integer> getAttributes(){return attributes;}
    public Integer getSpecificAttribute(Attribute atr) {
        return attributes.get(atr);
    }

}
