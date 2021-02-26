package net.trpfrog.medipro_game.mini_game.galaxy_express.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.ResourceLoader;

import java.awt.*;

public class Station extends Symbol implements Drawable {
    private final String stationName;
    private final Image stationImage = ResourceLoader
            .readImage(".","resource","mini_game","galaxy_express","station.png");
    private final int imageWidth = 200;
    private final int imageHeight = 160;

    public Station(String name){
        this.stationName = name;
        setDrawer(this);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(stationImage,(int)getX(),(int)getY(),imageWidth,imageHeight,null);
        g.setFont(new Font("Dialog",Font.BOLD,30));
        g.drawString(stationName,imageWidth/2-(getStationName().length()/2)*30,imageHeight/2);
    }

    public static Station getStation(String name){
        return new Station(name);
    }

    public String getStationName() {
        return stationName;
    }
}
