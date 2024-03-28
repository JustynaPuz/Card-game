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

    public  void getCard(int Id, Boolean isAccepted) {

       Card card =  CardLoadUtil.cards.get(Id);
       if(isAccepted) {
           changeAttributes(card.getAttributesAccepted());
       }else {
           changeAttributes(card.getAttributesNotAccepted());
       }
    }

    private void changeAttributes(EnumMap<Attribute, Integer> newAttributes) {
        Random random = new Random();
        int value;
        for(Attribute attribute : Attribute.values()) {
            if(newAttributes.get(attribute) != 0) {
                int min = newAttributes.get(attribute) - 6;
                int max = newAttributes.get(attribute) + 5;
                int newAttributeValue = random.nextInt(max - min) + min;
                 value = attributes.get(attribute) + newAttributeValue;
            }else {
                value= 0;
                attributes.put(attribute, value);
                continue;
            }
            if(value < 0) {
                value =0;
            }else if(value > 100) {
                value = 100;
            }
            attributes.put(attribute, value);

        }
    }
}
