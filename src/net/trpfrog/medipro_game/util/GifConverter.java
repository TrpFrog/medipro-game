package net.trpfrog.medipro_game.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GifConverter {
    public static java.util.List<Image> toImageList(Path gifPath) {
        List<Image> list = new ArrayList<>();
        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(new File(String.valueOf(gifPath)));
            reader.setInput(iis, false);
            int numberOfImages = reader.getNumImages(true);
            for(int i = 0; i < numberOfImages; i++) {
                list.add(reader.read(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
