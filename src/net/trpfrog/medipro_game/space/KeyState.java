package net.trpfrog.medipro_game.space;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyState implements KeyListener {
    private Component view;
    private Set<Integer> states;

    public void remove(int keyCode){
        states.remove(keyCode);
    }
    public boolean isPressed(int keyCode){
        return states.contains(keyCode);
    }
    public void clear(){
        states.clear();
    }

    public KeyState(Component component){
        view = component;
        view.addKeyListener(this);
        states = new HashSet<Integer>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        states.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        states.remove(e.getKeyCode());
    }
}
