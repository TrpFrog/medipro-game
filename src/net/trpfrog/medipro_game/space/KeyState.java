package net.trpfrog.medipro_game.space;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyState implements KeyListener {
    private Component view;
    private Map<Integer, Boolean> states;

    private void add(int keyCode){
        states.put(keyCode, true);
    }
    private void remove(int keyCode){
        states.put(keyCode, false);
    }
    public boolean isPressed(int keyCode){
        return states.getOrDefault(keyCode, false);
    }
    public void clear(){
        states.clear();
    }

    public KeyState(Component component){
        view = component;
        view.addKeyListener(this);
        states = new HashMap<Integer, Boolean>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        remove(e.getKeyCode());
    }
}
