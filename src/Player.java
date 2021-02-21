import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {

    private String name;
    private Character symbol;
    private int tokens;
    private String imgPath;
    private boolean isPlayer;
    private BufferedImage image;

    public Player(String name, Character symbol, int tokens, String imgPath) {
        this.name = name;
        this.symbol = symbol;
        this.tokens = tokens;
        this.imgPath = imgPath;
    }

    public void decrease(){
        if(tokens > 0) {
            tokens--;
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public Character getSymbol() {
        return symbol;
    }

    public int getTokens() {
        return tokens;
    }

}
