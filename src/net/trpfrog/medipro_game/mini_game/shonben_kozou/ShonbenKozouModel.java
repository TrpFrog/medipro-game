package net.trpfrog.medipro_game.mini_game.shonben_kozou;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.scene.GameModel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ShonbenKozouModel extends GameModel {

    private boolean playing = true;

    public ShonbenKozouModel(){
        MainView mv = MainView.getInstance();
    }

    public boolean isPlaying(){
        return playing;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
