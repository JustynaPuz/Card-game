package cardgame;

import java.util.EnumMap;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Card {

    private final int id;
    private final String title;
    private final String description;
    private final byte[] image;
    private final EnumMap<Attribute, Integer> attributesAccepted;
    private final EnumMap<Attribute, Integer> attributesNotAccepted;

    public EnumMap<Attribute, Integer> generateRandomCardImpact(Boolean isAccepted) {
        Random random = new Random();
        int value;
        EnumMap<Attribute, Integer> attributes =
                isAccepted ? attributesAccepted : attributesNotAccepted;
        EnumMap<Attribute, Integer> newAttributes = new EnumMap<>(Attribute.class);

        for (Attribute attribute : attributes.keySet()) {
            if (attributes.get(attribute) != 0) {
                int min = attributes.get(attribute) - 6;
                int max = attributes.get(attribute) + 7;
                value = random.nextInt(max - min) + min;

                value = Math.max(-100,Math.min(100, value));
            } else {
                value = 0;
                newAttributes.put(attribute, value);
                continue;
            }

            newAttributes.put(attribute, value);
        }
        return newAttributes;
    }
}
