package net.trpfrog.medipro_game.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * 独自フォントを使えるようにするためのクラス
 */
public class CustomFont {

    // Usage : new Font(CustomFont.LETS_GO_DIGITAL, Font.PLAIN, 30);
    public final static String LETS_GO_DIGITAL;

    static {
        LETS_GO_DIGITAL = load("Let_s_go_Digital_Regular.ttf").getName();
    }

    private static Font load(String fontFileName) {
        assert(fontFileName.toLowerCase().endsWith(".ttf"));
        Path fontPath = Paths.get(".","resource", "font", fontFileName);
        File fontFile = new File(fontPath.toString());
        Font font     = create(fontFile);
        register(font);
        return font;
    }

    private static void register(Font customFont) {
        final var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if(!ge.registerFont(customFont)) {
            String errorMessage = customFont.getName() + " has been failed to load!";
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static Font create(File ttf) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, ttf);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CustomFont() {}
}
