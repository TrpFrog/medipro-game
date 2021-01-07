package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.map.SpaceMap3D;
import net.trpfrog.medipro_game.space.symbols.Rocket;

public class SpaceModel extends GameModel {

    private SpaceMap3D map = new SpaceMap3D(128, 128, 16, 10);
    private Rocket rocket = new Rocket(this);


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


    @Override
    public void suspend() {
        rocket.suspend();
    }

    @Override
    public void resume() {
        rocket.resume();
    }
}
