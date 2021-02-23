package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.symbols.Comet;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CometManager extends Symbol implements Suspendable {

    private List<Comet> comets = Collections.synchronizedList(new LinkedList<>());
    private Timer moveCometsTimer, addCometsTimer;
    private SpaceModel model;
    private Rectangle mainViewRect = new Rectangle(0,0, MainView.getInstance().getWidth(),MainView.getInstance().getHeight());
    private SpaceMap2D spaceMap2D;
    private Rocket rocket;

    public CometManager(SpaceModel model){
        this.model = model;
        this.rocket = model.getRocket();
        this.spaceMap2D = model.getRocketFloorMap();
        setDrawer(g -> comets.forEach(e->e.getDrawer().draw(g)));

        moveCometsTimer = new Timer(50,e->moveEachComets(40));
        addCometsTimer = new Timer(100,e->addCometsRandomly());
    }

    public List<Comet> getComets(){return comets;}

    public void moveEachComets(int milliseconds){
        var it = comets.iterator();

        while(it.hasNext()){
            var obj = it.next();
            Area hitbox = obj.getAbsoluteHitBox();

            if(model.getRocket().touches(obj)){
                model.getRocket().getAnimation().damaged();
                spaceMap2D.removeSymbol((int)obj.getX(),(int)obj.getY(),obj);
                //it.remove();
            }else{
                obj.moveMilliseconds(milliseconds);
            }
        }
    }

    private Point selectOutOfBoundsPoint(){
        Point p = new Point();
        double angle = Math.PI * 2 * Math.random();
        // 隕石の出現する位置？
        p.x = (int)(Math.random()*mainViewRect.getWidth());
        p.y = (int)(Math.random()*mainViewRect.getHeight());
        return p;
    }

    private static final double CHANCE_OF_COMET = 0.1;

    public void addCometsRandomly(){
        if(Math.random() < CHANCE_OF_COMET){
            var p = selectOutOfBoundsPoint();
            Comet comet;
            comet = new Comet(p.x,p.y);
            comet.faceTo(rocket);
            comet.setRelativeHitBox(RelativeHitBox.makeCircle(30));
            comet.setSpeedPxPerSecond(200);
            comets.add(comet);
            spaceMap2D.addSymbol((int)comet.getX(),(int)comet.getY(),comet);
        }
    }

    @Override
    public void suspend() {
        //moveCometsTimer.stop();
        //addCometsTimer.stop();
    }

    @Override
    public void resume() {
        //moveCometsTimer.start();
        //addCometsTimer.start();
    }
}
