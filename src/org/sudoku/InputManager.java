package org.sudoku;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import javax.swing.event.MouseInputListener;

public class InputManager implements KeyListener, MouseInputListener {
    private final GUI gui;

    InputManager(GUI gui) {
        this.gui = gui;
    }  

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: System.exit(1); break;
            case KeyEvent.VK_SPACE: gui.startSimulation(); break;
            case KeyEvent.VK_ENTER: gui.confirmProvisionalIfValid();
            case KeyEvent.VK_RIGHT: gui.selectRight(); break;
            case KeyEvent.VK_LEFT: gui.selectLeft(); break;
            case KeyEvent.VK_UP: gui.selectAbove(); break;
            case KeyEvent.VK_DOWN: gui.selectBelow(); break;

            case KeyEvent.VK_1: gui.setProvisionalValue(1); break;
            case KeyEvent.VK_2: gui.setProvisionalValue(2); break;
            case KeyEvent.VK_3: gui.setProvisionalValue(3); break;
            case KeyEvent.VK_4: gui.setProvisionalValue(4); break;
            case KeyEvent.VK_5: gui.setProvisionalValue(5); break;
            case KeyEvent.VK_6: gui.setProvisionalValue(6); break;
            case KeyEvent.VK_7: gui.setProvisionalValue(7); break;
            case KeyEvent.VK_8: gui.setProvisionalValue(8); break;
            case KeyEvent.VK_9: gui.setProvisionalValue(9); break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gui.selectCellAt(e.getX(), e.getY() - GUI.windowYOffset);
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}        
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}

}
