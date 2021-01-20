package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Comet extends EventStar implements Drawable {
    private SpaceModel model;
    private Path path = Paths.get(".","resource","space_game","comet.png");
    private Image cometImage = Toolkit.getDefaultToolkit().getImage(path.toString());
    private int cometWidth;
    private int cometHeight;
    private MainView mainView;

    public Comet(int radius,SpaceModel model) {
        this.model = model;
        this.cometWidth = radius;
        this.cometHeight = radius;
        this.mainView = MainView.getInstance();

        setDrawer(this);
        setRelativeHitBox(RelativeHitBox.makeRectangle(cometWidth,cometHeight));
        setEvent((rocket1, star) -> rocket1.getAnimation().damaged());
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(cometImage,(int)(getX()-cometWidth/2),(int)(getY()-cometHeight/2),cometWidth,cometHeight,null);
    }
}