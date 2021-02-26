package net.trpfrog.medipro_game.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

import java.io.BufferedInputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MusicPlayer {

    public static final Clip
            SPACE_GAME_THEME = innerLoad("Lobby",       67776),
            ROCKET_SE        = innerLoad("Rocket",      384000),
            MINI_GAME_THEME  = innerLoad("MiniGame",    1016448),
            MENU_THEME       = innerLoad("Menu",        0),
            ACHIEVEMENT_SE   = innerLoad("Achievement", 0);

    private static Clip innerLoad(String name, int repeatStart) {
        Path path = Paths.get(".", "resource", "sound", name + ".wav");
        return load(path, repeatStart);
    }

    public static Clip load(Path path) {
        assert(path.toString().toLowerCase().endsWith("wav"));
        Clip clip;
        try {
            var bufferedStream = new BufferedInputStream(ResourceLoader.getInputStream(path));
            var stream = AudioSystem.getAudioInputStream(bufferedStream);
            var info = new DataLine.Info(Clip.class, stream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (Exception e) {
            e.printStackTrace();
            clip = null;
        }
        return clip;
    }

    public static Clip load(Path path, int repeatStart) {
        Clip ret = load(path);
        ret.setFramePosition(0);
        ret.setLoopPoints(repeatStart, -1);
        return ret;
    }

    /**
     * Clipのゲインを設定するメソッドです。
     * @param clip ゲインを設定するClip
     * @param level 0から1の値, この範囲を出る場合 0か1の近い方に丸められます。
     */
    public static void setClipGain(Clip clip, double level) {
        level = Math.max(0, Math.min(level, 1));
        try {
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float)
                    (Math.log(level <= 0.0 ? 0.0001 : level) / Math.log(10.0)*20.0);
            gainControl.setValue(dB);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private MusicPlayer() {}
}