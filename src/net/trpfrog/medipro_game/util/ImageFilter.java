package net.trpfrog.medipro_game.util;

import net.trpfrog.medipro_game.MainView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.*;

public class ImageFilter {

    private static final float[] GAUSSIAN_BLUR;

    static {
        GAUSSIAN_BLUR = new float[]{1,  4,  6,  4, 1,
                                    4, 16, 24, 16, 4,
                                    6, 24, 36, 24, 6,
                                    4, 16, 24, 16, 4,
                                    1,  4,  6,  4, 1 };
        for(int i = 0; i < GAUSSIAN_BLUR.length; i++) {
            GAUSSIAN_BLUR[i] *= 1f/256;
        }
    }

    public static Image blur(BufferedImage image) {
        BufferedImageOp blur = new ConvolveOp(new Kernel(5, 5, GAUSSIAN_BLUR));
        return blur.filter(image, null);
    }

    public static Image blur(JPanel panel) {
        var contentPane = MainView.getInstance().getContentPane();
        BufferedImage image = new BufferedImage(
                contentPane.getWidth(), contentPane.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        panel.paint(g);

        g.dispose();

        return blur(image);
    }


}
