package net.trpfrog.medipro_game.space.map;

import net.trpfrog.medipro_game.fieldmap.MapDrawer;
import net.trpfrog.medipro_game.space.SpaceModel;

import java.awt.*;

public class SpaceMapDrawer extends MapDrawer {

    private SpaceModel model;

    public SpaceMapDrawer(SpaceModel model) {
        super(model.getRocketFloorMap(), model.getRocket());
        this.model = model;
    }

    @Override
    public void draw(Graphics2D g) {
        setDrawnMap(model.getRocketFloorMap());

        // 背景の描画

        super.draw(g);
    }
}
