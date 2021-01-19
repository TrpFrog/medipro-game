package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.mainmenu.MainMenuScene;
import net.trpfrog.medipro_game.player.Player;

public class MediproGame {

    private static Player player = new Player();

    public static Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        SceneManager sceneManager = SceneManager.getInstance();
        MainView view = MainView.getInstance();
        sceneManager.push(MainMenuScene.getInstance());
    }

}