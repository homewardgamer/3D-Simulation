package com.oom.group3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project3 extends JFrame implements MouseListener, MouseWheelListener, MouseMotionListener {
    int pmouseX, pmouseY;
    Object3D cube;

    Project3(String title) {
        super(title);

        setSize(640, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Project3::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        Project3 project3 = new Project3("OOM Project 3");

        JPanel renderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // double buffering and rasterization
                // draw cube
            }
        };

        project3.add(renderPanel);
        project3.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // reset the view if DOUBLE_CLICKED
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // reset mouse cursor back to ARROW
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        // to zoom in and out using a modifier - CTRL + SCROLL
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        // move the object
        // change mouse cursor to HAND
        // update pmouseX, pmouseY
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        // update pmouseX, pmouseY
    }
}
