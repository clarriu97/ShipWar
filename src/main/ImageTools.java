package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageTools {

    private ImageTools() { }


    public static Image[] readImages(String dir, String[] filenames) {
        int l = filenames.length;
        Image[] images = new Image[l];
        for (int i = 0; i < l; i++) {
            try {
                images[i] = ImageIO.read(new File("sprites/" + dir + "/" + filenames[i] + ".png"));
            } catch (IOException e) { e.printStackTrace(); }
        }
        return images;
    }

}
