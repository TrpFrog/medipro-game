package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.symbol.MovableSymbol;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Comet extends MovableSymbol {

    public static final Image cometImage = getImage(Paths.get(".","resource","space_game","comet.png"));

    private static Image getImage(Path path){
        return Toolkit.getDefaultToolkit().getImage(path.toString());
    }

    public Comet(int x, int y){
        super(x,y);
        setDrawer(g->{
            Rectangle r = getRelativeHitBox().getBounds();
            r.translate((int)getX(),(int)getY());
            double cx = r.getCenterX();
            double cy = r.getCenterY();
            r.x -= r.width*1.8;
            r.width *= 2.8;
            double angle = Math.toRadians(getAngleDegrees());
            g.rotate(angle,cx,cy);
            g.drawImage(cometImage,r.x,r.y,r.width,r.height,null);
            g.rotate(-angle,cx,cy);
        });
    }
}