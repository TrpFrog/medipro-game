package net.trpfrog.medipro_game.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MusicPlayer {

    public static final Clip
            SPACE_GAME_THEME = innerLoad("Lobby",    210120),
            ROCKET_SE        = innerLoad("Rocket",   384000),
            MINI_GAME_THEME  = innerLoad("MiniGame", 1016448),
            MENU_THEME       = innerLoad("Menu",     210120);

    private static Clip innerLoad(String name, int repeatStart) {
        Path path = Paths.get(".", "resource", "sound", name + ".wav");
        return load(path, repeatStart);
    }

    public static Clip load(Path path) {
        assert(path.toString().toLowerCase().endsWith("wav"));
        Clip clip;
        try {
            var stream = AudioSystem.getAudioInputStream(new File(path.toString()));
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

    private MusicPlayer() {}
}