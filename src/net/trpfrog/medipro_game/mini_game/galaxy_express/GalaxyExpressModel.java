package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.data_structures.MovableSymbolManager;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.mini_game.GameOverWindow;
import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Background;
import net.trpfrog.medipro_game.mini_game.galaxy_express.symbols.Train;
import net.trpfrog.medipro_game.scene.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GalaxyExpressModel extends GameModel implements ActionListener {

    private MovableSymbolManager<Train> trains = new MovableSymbolManager<>();
    private Background background;
    static int trainNums;
    private Timer trainTimer = new Timer(100,this);
    private int counter = 0;

    public GalaxyExpressModel(){
        trainNums = (int)(Math.random()*10) + 10;
        for(int i=0; i<trainNums; i++ ){ trains.add(new Train());}
    }

    public MovableSymbolManager<Train> getTrains(){ return trains; }
    public Background getBackground(){ return background; }
    public int getCounter(){return counter;}
    public int getTrainNums(){return trainNums;}

    @Override
    public void suspend() {
        trainTimer.stop();
    }

    @Override
    public void resume() {
        trainTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        trains.forEach(t->t.translate(Math.random()*5,Math.random()*5));
        if(counter > trainNums*8){
            var window = new GalaxyExpressAnswerPanel();
            var scene = new DialogBackgroundScene(window, false);
            SceneManager.getInstance().push(scene);
        }
    }
}
