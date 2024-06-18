package cardgame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
    public static byte[] loadImage(String path) throws IOException {
        try (InputStream is = ImageUtil.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("File not found: " + path);
            }
            BufferedImage bImage = ImageIO.read(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            return bos.toByteArray();
        }
    }
    public static JLabel createScaledImage(byte[] imageData, int width, int height) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage bImage = ImageIO.read(bais);
        ImageIcon imageIcon = new ImageIcon(bImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        return new JLabel(imageIcon);
    }
}
