package cardgame;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    // Konstruktor, który ładuje obraz
    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}

public class GUI {

    Engine engine = new Engine();
    Integer days = 0;
    JLabel daysLabel = new JLabel("Wynik: ");
    EnumMap<Attribute, Integer> attributesValues = new EnumMap<>(Attribute.class);
    JLabel[] labelsAttributes = new JLabel[]{
            new JLabel("Days"),
            new JLabel("Food"),
            new JLabel("Economy"),
            new JLabel("Military"),
            new JLabel("Religion")
    };
    JFrame frame = new JFrame("Jamnikowe krolestwo");
    BackgroundPanel startScreen = new BackgroundPanel(
            "src/main/resources/startScreenJamnik.png");
    BackgroundPanel gameScreen = new BackgroundPanel("src/main/resources/royal.jpg");
    BackgroundPanel gameOverScreen = new BackgroundPanel("src/main/resources/royal.jpg");
    BackgroundPanel decisionsScreen = new BackgroundPanel("src/main/resources/royal.jpg");
    BackgroundPanel topScoreScreen = new BackgroundPanel("src/main/resources/royal.jpg");
    JTextField titleField = new JTextField();
    JTextArea descriptionArea = new JTextArea();
    JTextArea decisionArea = new JTextArea();
    JTextArea topScoreArea = new JTextArea();

    CardLayout cardLayout = new CardLayout();
    JLabel cardImageLabel = new JLabel();
    JPanel panelContainer = new JPanel(cardLayout);
    JTextField nameField = new JTextField();
    Font font = new Font("Arial", Font.BOLD, 40);
    Font topScoreButtonFont = new Font("Arial", Font.BOLD, 20);
    Font fontTitle = new Font("Arial", Font.BOLD, 20);


    public static void main(String[] args) throws IOException {
        new GUI();
    }

    public GUI() throws IOException {
        createComponents();
    }


    private void createComponents() throws IOException {
        attributesValues = engine.getKingdomAttributes();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);

        //startScreen
        createStartScreen();

        //gameScreen
        createGameScreen();

        //game over screen
        createGameOverScreen();

        //decisions screen
        createDecisionsScreen();

        //top score screen
        createTopScoreScreen();


        // Add panels to container
        panelContainer.add(startScreen, "StartScreen");
        panelContainer.add(gameScreen, "Game");
        panelContainer.add(gameOverScreen, "gameOverScreen");
        panelContainer.add(decisionsScreen, "Decisions");
        panelContainer.add(topScoreScreen, "TopScoreScreen");

        frame.add(panelContainer);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Show first screen initially
        cardLayout.show(panelContainer, "StartScreen");

    }

    public void topScore() throws SQLException {
        topScoreArea.setText(engine.getTopScore());
    }

    public void gameOver() {
        days = 0;
        nameField.setText("");
        engine.generateNewKingdom();
        attributesValues = engine.getKingdomAttributes();
        updateAttributesValues();
    }

    public void decisions() {
        decisionArea.setText(engine.getDecisions());
    }

    public void newCard(boolean isAccepted) throws IOException {
        days++;
        Card card = engine.getRandomCard();
        titleField.setText(card.getTitle());
        descriptionArea.setText(card.getDescription());
        JLabel scaledImage = ImageUtil.createScaledImage(card.getImage(), 300, 500);

        cardImageLabel.setIcon(scaledImage.getIcon());

        engine.generateCardEffect(card, isAccepted);
        attributesValues = engine.getKingdomAttributes();
        updateAttributesValues();
        if (attributesValues.values().stream().anyMatch(integer -> integer <= 0)) {
            daysLabel.setText("Wynik: " + days.toString());
            cardLayout.show(panelContainer, "gameOverScreen");
            frame.setSize(500, 700);

        }
    }

    public void updateAttributesValues() {
        labelsAttributes[0].setText(days.toString());
        labelsAttributes[1].setText(attributesValues.get(Attribute.FOOD).toString());
        labelsAttributes[2].setText(attributesValues.get(Attribute.ECONOMY).toString());
        labelsAttributes[3].setText(attributesValues.get(Attribute.MILITARY).toString());
        labelsAttributes[4].setText(attributesValues.get(Attribute.RELIGION).toString());
    }

    public void addAttribute(Box box, String[] pathImage)
            throws IOException {
        box.add(Box.createHorizontalStrut(100));

        List<Integer> values = new ArrayList<>();
        values.add(days);
        values.add(attributesValues.get(Attribute.FOOD));
        values.add(attributesValues.get(Attribute.ECONOMY));
        values.add(attributesValues.get(Attribute.MILITARY));
        values.add(attributesValues.get(Attribute.RELIGION));

        for (int i = 0; i < 5; i++) {
            BufferedImage daysBuffImage = ImageIO.read(new File(pathImage[i]));
            ImageIcon daysIcon = new ImageIcon(daysBuffImage);
            Image daysScaledImage = daysIcon.getImage()
                    .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon daysScaledIcon = new ImageIcon(daysScaledImage);
            JLabel imageLabel = new JLabel(daysScaledIcon);
            box.add(imageLabel);
            box.add(Box.createHorizontalStrut(30));
            labelsAttributes[i].setText(values.get(i).toString());
            labelsAttributes[i].setFont(new Font("Arial", Font.BOLD, 20));
            box.add(labelsAttributes[i]);
            box.add(Box.createHorizontalStrut(30));

        }

    }

    private void createButton(JButton button, int width, int height, Font font, float alignment) {
        button.setAlignmentX(alignment);
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
    }

    private void createBox(Box box, int width, int height) {
        box.setPreferredSize(new Dimension(width, height));
        box.setMinimumSize(new Dimension(width, height));
        box.setMaximumSize(new Dimension(width, height));

    }

    private JLabel createScaledImage(String path, int width, int height) throws IOException {
        BufferedImage cardBuffImage = ImageIO.read(
                new File(path));
        ImageIcon cardIcon = new ImageIcon(cardBuffImage);
        Image cardScaledImage = cardIcon.getImage().getScaledInstance(300, 500, Image.SCALE_SMOOTH);
        ImageIcon cardScaledIcon = new ImageIcon(cardScaledImage);
        return new JLabel(cardScaledIcon);

    }

    private void createStartScreen() {
        startScreen.setLayout(new BoxLayout(startScreen, BoxLayout.Y_AXIS));
        startScreen.add(Box.createVerticalStrut(15));

        JLabel titleLabel = new JLabel("Sprawdz sie jako król!");
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        startScreen.add(titleLabel);
        startScreen.add(Box.createVerticalStrut(15));

        //Start game button
        Font startGameButtonFont = new Font("Arial", Font.BOLD, 30);
        JButton startGameButton = new JButton("START!");
        createButton(startGameButton, 250, 60, startGameButtonFont, JComponent.CENTER_ALIGNMENT);
        startGameButton.addActionListener(e -> {
            engine.generateNewKingdom();
            engine.clearDecisions();
            decisionArea.setText("");
            cardLayout.show(panelContainer, "Game");
            frame.setSize(800, 700);
        });
        startScreen.add(startGameButton);
        startScreen.add(Box.createVerticalStrut(15));

        //Top scores button
        Font topScoreButtonFont = new Font("Arial", Font.BOLD, 20);
        JButton topScoreButton = new JButton("Najlepsze  wyniki");
        createButton(topScoreButton, 250, 60, topScoreButtonFont, JComponent.CENTER_ALIGNMENT);
        topScoreButton.addActionListener(e -> {
            try {
                topScore();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            cardLayout.show(panelContainer, "TopScoreScreen");
        });
        startScreen.add(topScoreButton);

    }

    private void createGameScreen() throws IOException {
        gameScreen.setLayout(new BoxLayout(gameScreen, BoxLayout.Y_AXIS));
        Box attributesBox = Box.createHorizontalBox();
        createBox(attributesBox, 800, 100);
        String[] paths = {"src/main/resources/calendar.png", "src/main/resources/bread.png",
                "src/main/resources/money-bag.png", "src/main/resources/swords.png",
                "src/main/resources/church.png"};
        addAttribute(attributesBox, paths);
        gameScreen.add(attributesBox);
        gameScreen.add(Box.createVerticalStrut(15));

        //first card
        Card card = engine.getRandomCard();


        //card box
        Box cardBox = Box.createHorizontalBox();
        cardBox.add(Box.createHorizontalStrut(40));
        createBox(cardBox, 800, 400);
        try {
            cardImageLabel = ImageUtil.createScaledImage(card.getImage(), 300, 500);
            cardBox.add(cardImageLabel);
        } catch (IOException e) {
            e.printStackTrace();
            cardBox.add(new JLabel("Brak obrazu"));
        }
        cardBox.add(Box.createHorizontalStrut(15));
        titleField.setText(card.getTitle());
        Font fontSmallerTitle = new Font("Arial", Font.BOLD, 15);
        titleField.setFont(fontSmallerTitle);
        titleField.setMaximumSize(new Dimension(300, 50));

        //description box
        Box cardDescriptionBox = Box.createVerticalBox();
        cardDescriptionBox.add(titleField);
        cardDescriptionBox.add(Box.createVerticalStrut(15));
        Font fontDescription = new Font("Arial", Font.PLAIN, 20);
        descriptionArea.setText(card.getDescription());
        descriptionArea.setFont(fontDescription);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setMaximumSize(new Dimension(300, 435));
        cardDescriptionBox.add(scrollPane);
        cardBox.add(cardDescriptionBox);
        cardBox.add(Box.createHorizontalStrut(15));
        gameScreen.add(cardBox);
        //buttons
        JButton declineButton = new JButton("DECLINE");
        createButton(declineButton, 250, 60, topScoreButtonFont, JComponent.LEFT_ALIGNMENT);
        declineButton.addActionListener(e -> {
            try {
                newCard(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        Box buttonsBox = Box.createHorizontalBox();
        buttonsBox.add(declineButton);
        buttonsBox.add(Box.createHorizontalStrut(15));
        JButton acceptButton = new JButton("ACCEPT");
        createButton(acceptButton, 250, 60, topScoreButtonFont, JComponent.RIGHT_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            try {
                newCard(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonsBox.add(acceptButton);
        gameScreen.add(buttonsBox);

    }

    private void createGameOverScreen() throws IOException {
        gameOverScreen.add(Box.createVerticalStrut(25));
        JLabel gameOverLabel = new JLabel("KONIEC!");
        gameOverLabel.setFont(font);
        gameOverLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameOverScreen.add(gameOverLabel);
        gameOverScreen.add(Box.createVerticalStrut(15));

        //Insert Name

        daysLabel.setFont(font);
        daysLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        Box insertNameBox = Box.createVerticalBox();
        insertNameBox.add(daysLabel);
        JLabel l2 = new JLabel("Wpisz swoje imię: ");
        l2.setFont(font);
        l2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        insertNameBox.add(l2);
        insertNameBox.add(Box.createVerticalStrut(30));
        nameField.setMinimumSize(new Dimension(400, 100));
        nameField.setFont(font);
        nameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        insertNameBox.add(nameField);
        insertNameBox.add(Box.createVerticalStrut(15));
        gameOverScreen.add(insertNameBox);
        Box buttonsGameOverBox = Box.createVerticalBox();
        JButton submitButton = new JButton("Zatwierdź");
        createButton(submitButton, 300, 100, font, JComponent.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            try {
                if (!Objects.equals(nameField.getText(), "")) {
                    engine.gameOver(nameField.getText(), days);
                }
                gameOver();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonsGameOverBox.add(submitButton);
        buttonsGameOverBox.add(Box.createVerticalStrut(15));

        JButton movesButton = new JButton("Decyzje");
        createButton(movesButton, 300, 100, font, JComponent.CENTER_ALIGNMENT);
        movesButton.addActionListener(e -> {
            decisions();
            cardLayout.show(panelContainer, "Decisions");

        });
        buttonsGameOverBox.add(movesButton);
        buttonsGameOverBox.add(Box.createVerticalStrut(15));
        JButton continueButton = new JButton("Kontynuuj");
        createButton(continueButton, 300, 100, font, JComponent.CENTER_ALIGNMENT);

        continueButton.addActionListener(e -> {
            gameOver();
            engine.setDays(0);
            cardLayout.show(panelContainer, "StartScreen");
        });
        buttonsGameOverBox.add(continueButton);
        buttonsGameOverBox.add(Box.createVerticalStrut(30));
        JLabel dachshundImageLabel = createScaledImage("src/main/resources/dachshund.png", 200, 200);
        dachshundImageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsGameOverBox.add(dachshundImageLabel);
        gameOverScreen.add(buttonsGameOverBox);

    }

    private void createDecisionsScreen() {

        Box decisionsBox = Box.createVerticalBox();
        decisionsBox.add(Box.createVerticalStrut(15));
        JLabel l3 = new JLabel("Twoje dezycje");
        l3.setFont(font);
        l3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        decisionsBox.add(l3);
        decisionsBox.add(Box.createVerticalStrut(15));
        Font fontDecisions = new Font("Arial", Font.PLAIN, 15);
        decisionArea.setFont(fontDecisions);
        decisionArea.setMinimumSize(new Dimension(400, 500));
        decisionArea.setMaximumSize(new Dimension(400, 500));
        JScrollPane scrollPane2 = new JScrollPane(decisionArea);
        scrollPane2.setFont(fontDecisions);
        scrollPane2.setPreferredSize(new Dimension(400, 500));
        scrollPane2.setMaximumSize(new Dimension(400, 500));
        scrollPane2.setMinimumSize(new Dimension(400, 500));
        decisionsBox.add(scrollPane2);
        decisionsBox.add(Box.createVerticalStrut(15));

        JButton decisionsButton = new JButton("Powrót");
        createButton(decisionsButton, 300, 50, font, JComponent.CENTER_ALIGNMENT);
        decisionsButton.addActionListener(e -> {
            cardLayout.show(panelContainer, "gameOverScreen");


        });
        decisionsBox.add(decisionsButton);
        decisionsScreen.add(decisionsBox);


    }

    private void createTopScoreScreen() {

        Box topScoreBox = Box.createVerticalBox();
        topScoreBox.add(Box.createVerticalStrut(15));
        JLabel l4 = new JLabel("Najlepsze wyniki");
        l4.setFont(font);
        l4.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        topScoreBox.add(l4);
        topScoreBox.add(Box.createVerticalStrut(15));
        Font fontTopScore = new Font("Arial", Font.PLAIN, 40);
        topScoreArea.setFont(fontTopScore);
        topScoreArea.setMinimumSize(new Dimension(400, 500));
        topScoreArea.setMaximumSize(new Dimension(400, 500));
        JScrollPane scrollPane3 = new JScrollPane(topScoreArea);
        Font fontTopScores = new Font("Arial", Font.PLAIN, 15);
        scrollPane3.setFont(fontTopScores);
        scrollPane3.setPreferredSize(new Dimension(400, 500));
        scrollPane3.setMaximumSize(new Dimension(400, 500));
        scrollPane3.setMinimumSize(new Dimension(400, 500));
        topScoreBox.add(scrollPane3);
        topScoreBox.add(Box.createVerticalStrut(15));
        JButton topScoreButton = new JButton("Powrót");

        createButton(topScoreButton, 300, 50, font, JComponent.CENTER_ALIGNMENT);
        topScoreButton.addActionListener(e -> {
            cardLayout.show(panelContainer, "StartScreen");

        });
        topScoreBox.add(topScoreButton);
        topScoreScreen.add(topScoreBox);

    }


}
