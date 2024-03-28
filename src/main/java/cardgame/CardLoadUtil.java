package cardgame;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import lombok.Getter;
import java.util.Map;


public class CardLoadUtil {
    static Map<Integer,Card> cards = new HashMap<>();
    public static void loadCard() {
        Card card;
        List<String> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/CardGame.csv"))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                EnumMap<Attribute, Integer> attributesAccepted = new EnumMap<>(Attribute.class);
                attributesAccepted.put(Attribute.FOOD,Integer.parseInt(values[3]));
                attributesAccepted.put(Attribute.ECONOMY,Integer.parseInt(values[4]));
                attributesAccepted.put(Attribute.MILITARY,Integer.parseInt(values[5]));
                attributesAccepted.put(Attribute.RELIGION,Integer.parseInt(values[6]));

                EnumMap<Attribute, Integer> attributesNotAccepted = new EnumMap<>(Attribute.class);
                attributesNotAccepted.put(Attribute.FOOD, Integer.parseInt(values[7]));
                attributesNotAccepted.put(Attribute.ECONOMY, Integer.parseInt(values[8]));
                attributesNotAccepted.put(Attribute.MILITARY, Integer.parseInt(values[9]));
                attributesNotAccepted.put(Attribute.RELIGION, Integer.parseInt(values[10]));

                card = new Card(Integer.parseInt(values[0]),values[1], values[2], attributesAccepted,attributesNotAccepted );
            cards.put(card.getId(),card);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }


}


