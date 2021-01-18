package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class MouseState implements MouseListener, MouseMotionListener, MouseWheelListener {
    private Component view;
    private boolean clicked, wheeled, wheelUp;
    private Point pointerLocation;

    public MouseState(Component component){
        view = component;
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        component.addMouseWheelListener(this);
        clicked = false;
        wheeled = false;
        wheelUp = true;
    }

    public void onClick(){
        clicked = true;
    }
    public void offClick(){
        clicked = false;
    }
    public boolean isClicked(){
        return clicked;
    }

    public void pointerLocationUpdate(){
        PointerInfo pi = MouseInfo.getPointerInfo();
        pointerLocation = pi.getLocation();
        SwingUtilities.convertPointFromScreen(pointerLocation, MainView.getInstance().getContentPane());
    }
    public double getPointerX(){
        return pointerLocation.getX();
    }
    public double getPointerY(){
        return pointerLocation.getY();
    }
    public double getPointerDistance(){
        double mouseX = getPointerX();
        double mouseY = getPointerY();
        double distance = Point2D.distance(
                mouseX, mouseY,
                view.getWidth()/2, view.getHeight()/2
        );
        return distance;
    };

    public void onWheel(boolean isUp){
        wheeled = true;
        wheelUp = isUp;
    }
    public void offWheel(){
        wheeled = false;
    }
    public boolean isWheeled(){
        return wheeled;
    }
    public int getWheelVec(){
        return wheelUp ? 1 : -1;
    }

    public void clear(){
        offClick();
        offWheel();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        onClick();
        pointerLocationUpdate();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        offClick();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int dz = e.getWheelRotation();
        boolean isUp = 0 < dz;
        onWheel(isUp);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pointerLocationUpdate();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}