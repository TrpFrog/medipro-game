package net.trpfrog.medipro_game.mini_game.shonben_kozou;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols.*;
import net.trpfrog.medipro_game.mini_game.shonben_kozou.symbols.Character;
import net.trpfrog.medipro_game.scene.GameModel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ShonbenKozouModel extends GameModel {
    private Cup cup;
    private Kozou kozou;
    private CircleManager circles;
    private Character character;
    private GameTimer gameTimer;

    public ShonbenKozouModel(){
        MainView mv = MainView.getInstance();

        cup = new Cup(this);
        kozou = new Kozou(this);
        circles = new CircleManager(this);
        character = new Character(this);
        gameTimer = new GameTimer(this);
    }

    public Cup getCup() {
        return cup;
    }

    public Kozou getKozou(){
        return kozou;
    }

    public CircleManager getCircles(){
        return circles;
    }

    public Character getCharacter() { return character; }

    public GameTimer getGameTimer() { return gameTimer; }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }
}
