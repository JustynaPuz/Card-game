package cardgame;

public class Main {

    public static void main(String[] args) {
        Kingdom kingdom = new Kingdom();
        CardLoadUtil.loadCard();
        kingdom.getCard(1,true);

    }
}
