package cardgame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


public class CardLoadUtil {

    public static List<Card> loadCard(String pathCSV) {
        Card card;
        List<Card> cards = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(pathCSV), StandardCharsets.UTF_8))) {
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

                byte[] image = ImageUtil.loadImage("cardImages/" + values[0] +".png");

                card = new Card(Integer.parseInt(values[0]),values[1], values[2], image, attributesAccepted,attributesNotAccepted );
            cards.add(card);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }
}


