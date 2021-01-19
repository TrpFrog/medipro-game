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
        LETS_GO_DIGITAL = innerLoad("Let_s_go_Digital_Regular.ttf");
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
        File fontFile = new File(fontPath.toString());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
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
