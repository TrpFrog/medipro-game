package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.scene.GameScene;

import java.util.*;

public class SceneManager {
    private Stack<GameScene> stack = new Stack<>();

    public void push(GameScene scene) {
        stack.peek().suspend();
        scene.resume();
        MainView.getView().addGameView(scene.getView());
        stack.push(scene);
    }

    public GameScene pop() {
        stack.peek().suspend();
        GameScene ret = stack.pop();
        if(stack.isEmpty()) {
            System.exit(0);
        }
        MainView.getView().popGameView();
        stack.peek().resume();
        return ret;
    }
}
