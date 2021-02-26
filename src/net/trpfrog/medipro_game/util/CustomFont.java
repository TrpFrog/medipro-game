package net.trpfrog.medipro_game.util;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 独自フォントを使えるようにするためのクラス
 */
public class CustomFont {

    // Usage : new Font(CustomFont.LETS_GO_DIGITAL, Font.PLAIN, 30);
    public final static String DSEG7_CLASSIC;
    public final static String DSEG7_CLASSIC_MINI;
    public final static String BUNGEE_SHADE;

    static {
        DSEG7_CLASSIC = innerLoad("DSEG7Classic-Bold.ttf");
        innerLoad("DSEG7Classic-BoldItalic.ttf");
        DSEG7_CLASSIC_MINI = innerLoad("DSEG7ClassicMini-Bold.ttf");
        innerLoad("DSEG7ClassicMini-BoldItalic.ttf");
        BUNGEE_SHADE = innerLoad("BungeeShade-Regular.ttf");
    }

    private static String innerLoad(String fontFileName) {
        Path fontPath = Paths.get(".","resource", "font", fontFileName);
        try {
            return load(fontPath).getName();
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定したパスのTrueTypeFontを読み込みJavaの環境に追加、生成したFontオブジェクトを返します。
     * @param fontPath フォントが存在するパス
     * @return 生成したFontオブジェクト
     * @throws IllegalArgumentException ファイル拡張子がttfでない場合, フォントのロードに失敗した場合
     */
    public static Font load(Path fontPath) throws FontFormatException, IOException {
        assert(fontPath.toString().toLowerCase().endsWith(".ttf"));
        var fontInputStream = ResourceLoader.getInputStream(fontPath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);
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

    private CustomFont() {}
}
