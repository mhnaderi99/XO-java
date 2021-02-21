import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private JLabel label;

    public ImagePanel(){
        super(new BorderLayout());
        label = new JLabel();
        setOpaque(true);
        setEnabled(true);
        setFocusable(true);
        setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setEnabled(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(label);

    }

    public void setImage(String path) {
        Image image = null;  //this generates an image file
        try {
            image = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(image);
            label.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
