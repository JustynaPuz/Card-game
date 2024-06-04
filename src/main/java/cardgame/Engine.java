package cardgame;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.io.BufferedReader;

public class Engine{

    private int days;
    private Kingdom kingdom;
    private List<Card> cards;
    private Boolean gameOver = false;
    DatabaseEngine db = new DatabaseEngine();
    private List<Card> usedCards = new ArrayList<>();
    private List<EnumMap<Attribute,Integer>> changedAttributes =new ArrayList<>();
    private List<Boolean> isAssceptedList = new ArrayList<>();

    public Engine() {
        cards = CardLoadUtil.loadCard("src/main/resources/newDeck.csv");
        db.insertCards(cards);
        kingdom = new Kingdom();
    }

    public EnumMap<Attribute, Integer> getKingdomAttributes() {
        return kingdom.getAttributes();
    }

    public Card getRandomCard() {
        Random rand = new Random();
        return db.getCardById(rand.nextInt(db.getNumberOfCards()));

    }
    public void clearDecisions() {
        usedCards.clear();
        isAssceptedList.clear();
    }

    public void generateCardEffect(Card card, Boolean isAccepted) {
        days++;
        usedCards.add(card);
        isAssceptedList.add(isAccepted);
        EnumMap<Attribute, Integer> attributesFromCard = card.generateRandomCardImpact(isAccepted);
        EnumMap<Attribute, Integer> newAttributes = new EnumMap<>(kingdom.getAttributes());

        for (Attribute attribute : Attribute.values()) {
            int value = newAttributes.get(attribute) + attributesFromCard.get(attribute);
            if (value > 100) {
                value = 100;
            } else if (value <= 0) {
                System.out.println("GAME OVER!");
                gameOver = true;
            }
            newAttributes.put(attribute, value);
        }
        kingdom.setAttributes(newAttributes);
        changedAttributes.add(newAttributes);
    }

    public void gameOver(String name, Integer days) throws SQLException {
        db.insertTopScore(name, days);
    }

    public void generateNewKingdom() {
        kingdom = new Kingdom();
    }

    public String getDecisions() {
        StringBuilder output = new StringBuilder();
        EnumMap<Attribute, Integer> attributes = new EnumMap<>(Attribute.class);

        for (int i = 0; i < usedCards.size(); i++) {

            if (isAssceptedList.get(i)) {
                attributes = usedCards.get(i).getAttributesAccepted();
            } else {
                attributes = usedCards.get(i).getAttributesNotAccepted();
            }
            output.append("\n").append(usedCards.get(i).getTitle()).append(": ").append("\n");
            output.append(Attribute.FOOD).append(": ")
                    .append(changedAttributes.get(i).get(Attribute.FOOD)).append(" ");
            output.append(Attribute.ECONOMY).append(": ")
                    .append(changedAttributes.get(i).get(Attribute.ECONOMY)).append(" ");
            output.append(Attribute.MILITARY).append(": ")
                    .append(changedAttributes.get(i).get(Attribute.MILITARY)).append(" ");
            output.append(Attribute.RELIGION).append(": ")
                    .append(changedAttributes.get(i).get(Attribute.RELIGION)).append(" ").append("\n");

        }
        return output.toString();
    }

    public String getTopScore() throws SQLException {
        Map<String, Integer> topScores = db.getTopScore();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(topScores.entrySet());
        entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        StringBuilder output = new StringBuilder();

        for (Map.Entry<String, Integer> entry : entries) {
            output.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return output.toString();

    }


    public void run() {
        System.out.println("Welcome!");
        System.out.println("Type 'N' to decline and 'Y' to accept");
        boolean isAccepted = false;

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (!gameOver) {
            Card card = this.getRandomCard();
            System.out.println(card.getTitle());
            System.out.println(card.getDescription());
            System.out.println("Make a decision:");
            String decision = null;
            try {
                decision = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (Objects.equals(decision, "Y")) {
                isAccepted = true;
            } else if (Objects.equals(decision, "N")) {
                isAccepted = false;
            }
            generateCardEffect(card, isAccepted);
            System.out.println(kingdom.toString());

        }
    }
}

