package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.map.SpaceMap3D;
import net.trpfrog.medipro_game.space.symbols.Rocket;

import javax.sound.sampled.*;
import java.io.File;
import java.nio.file.Paths;

public class SpaceModel extends GameModel {

    private SpaceMap3D map = new SpaceMap3D(16, 16, 128, 10);
    private Rocket rocket = new Rocket(this);
    private Clip bgm;

    public SpaceModel() {
        initBGM();
    }

    private void initBGM() {
        String path = Paths.get(".", "resource", "space_game", "bgm.wav").toString();
        try {
            bgm = AudioSystem.getClip();
            var stream = AudioSystem.getAudioInputStream(new File(path));
            var info = new DataLine.Info(Clip.class, stream.getFormat());
            bgm = (Clip) AudioSystem.getLine(info);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
            bgm.open(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 宇宙全体のSpaceMap3Dを返します。
     * @return 宇宙全体のSpaceMap3D
     */
    public SpaceMap3D get3DMap() {
        return map;
    }

    /**
     * ロケットが今いる階層の地図を返します。
     * @return ロケットが存在する階層のSpaceMap2D
     */
    public SpaceMap2D getRocketFloorMap() {
        return map.get2DMap(rocket.getDepth());
    }

    /**
     * このゲームで唯一のロケットオブジェクトを返します。
     * @return 操作するロケット
     */
    public Rocket getRocket() {
        return rocket;
    }

    /**
     * ゲーム中で流すBGMの {@code Clip} を返します。
     * @return ゲーム中で流すBGM
     */
    public Clip getBGMClip() {
        return bgm;
    }

    @Override
    public void suspend() {
        rocket.suspend();
    }

    @Override
    public void resume() {
        rocket.resume();
    }
}
