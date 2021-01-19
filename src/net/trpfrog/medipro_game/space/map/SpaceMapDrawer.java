package net.trpfrog.medipro_game.space.map;

import net.trpfrog.medipro_game.fieldmap.MapDrawer;
import net.trpfrog.medipro_game.space.SpaceModel;
import java.awt.*;
import java.util.List;
import java.util.LinkedList;

public class SpaceMapDrawer extends MapDrawer {

    private SpaceModel model;
    private Image previousBackground;

    public SpaceMapDrawer(SpaceModel model) {
        super(model.getRocketFloorMap(), model.getRocket());
        this.model = model;

        previousBackground = model.getRocketFloorMap().getBackgroundImage();
    }

    private List<BackgroundDrawer> transition = new LinkedList<>();

    @Override
    public void draw(Graphics2D g) {
        setDrawnMap(model.getRocketFloorMap());
        model.getRocketFloorMap().generateStars(createDrawRangeRectangle());

        var bkg = model.getRocketFloorMap().getBackgroundImage();
        new BackgroundDrawer(model, bkg).draw(g);

        if(!previousBackground.equals(bkg)) {
            transition.add(new BackgroundDrawer(model, previousBackground));
            previousBackground = bkg;
        }
        transition.forEach(e -> e.setAlpha(e.getAlpha() - 1f / 64));
        transition.removeIf(e -> e.getAlpha() < 0);
        transition.forEach(e -> e.draw(g));

        super.draw(g);
    }
}
