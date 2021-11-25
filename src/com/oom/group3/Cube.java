package com.oom.group3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Cube {
    static Integer[] prevMouse = { null, null };
    static double pitch, roll, heading, fovValue;
    static final int windowSize = 900;
    static boolean showEdges = false;

    public static void main(String[] args) {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void resetValues() {
        pitch = roll = heading = 0;
        fovValue = 60;
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame();
        resetValues();

        // panel to display render results
        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                ArrayList<Polygon3D> polygons = new Object3D().getPolygons();

                Matrix4 headingTransform = new Matrix4(new double[] {
                        Math.cos(heading), 0, -Math.sin(heading), 0, 0, 1,
                        0, 0, Math.sin(heading), 0, Math.cos(heading), 0, 0, 0, 0, 1
                });

                Matrix4 pitchTransform = new Matrix4(new double[] {
                        1, 0, 0, 0, 0, Math.cos(pitch), Math.sin(pitch), 0,
                        0, -Math.sin(pitch), Math.cos(pitch), 0, 0, 0, 0, 1
                });

                Matrix4 rollTransform = new Matrix4(new double[] {
                        Math.cos(roll), -Math.sin(roll), 0, 0,
                        Math.sin(roll), Math.cos(roll), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1
                });

                Matrix4 panOutTransform = new Matrix4(new double[] {
                        1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, -400, 1
                });

                double viewportWidth = getWidth();
                double viewportHeight = getHeight();
                double fovAngle = Math.toRadians(fovValue);
                double fov = Math.tan(fovAngle / 2) / 200;

                Matrix4 transform = headingTransform
                        .multiply(pitchTransform)
                        .multiply(rollTransform)
                        .multiply(panOutTransform);

                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                double[] zBuffer = new double[img.getWidth() * img.getHeight()];

                // initialize array with extremely far away depths
                Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

                for (Polygon3D polygon : polygons) {
                    Point3D v1 = transform.transform(polygon.v1);
                    Point3D v2 = transform.transform(polygon.v2);
                    Point3D v3 = transform.transform(polygon.v3);

                    Point3D ab = new Point3D(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z, v2.w - v1.w);
                    Point3D ac = new Point3D(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z, v3.w - v1.w);
                    Point3D norm = new Point3D(
                            ab.y * ac.z - ab.z * ac.y,
                            ab.z * ac.x - ab.x * ac.z,
                            ab.x * ac.y - ab.y * ac.x,
                            1
                    );

                    double normalLength = Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
                    norm.x /= normalLength;
                    norm.y /= normalLength;
                    norm.z /= normalLength;

                    double angleCos = Math.abs(norm.z);

                    v1.x = v1.x / (-v1.z) / fov;
                    v1.y = v1.y / (-v1.z) / fov;
                    v2.x = v2.x / (-v2.z) / fov;
                    v2.y = v2.y / (-v2.z) / fov;
                    v3.x = v3.x / (-v3.z) / fov;
                    v3.y = v3.y / (-v3.z) / fov;

                    v1.x += viewportWidth / 2;
                    v1.y += viewportHeight / 2;
                    v2.x += viewportWidth / 2;
                    v2.y += viewportHeight / 2;
                    v3.x += viewportWidth / 2;
                    v3.y += viewportHeight / 2;

                    int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
                    int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
                    int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
                    int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

                    double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

                    for (int y = minY; y <= maxY; y++) {
                        for (int x = minX; x <= maxX; x++) {
                            double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                            double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                            double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;

                            if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                                double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                                int zIndex = y * img.getWidth() + x;

                                if (zBuffer[zIndex] < depth) {
                                    final double k = 0.01;
                                    boolean liesOnEdge = !(b1 >= k && b1 <= 1-k && b2 >= k && b2 <= 1-k && b3 >= k && b3 <= 1-k);

                                    img.setRGB(x, y, getShade((showEdges && liesOnEdge) ? Color.DARK_GRAY : polygon.color, angleCos).getRGB());
                                    zBuffer[zIndex] = depth;
                                }
                            }
                        }
                    }
                }

                g2.drawImage(img, 0, 0, null);
            }
        };

        setupKeyboardListeners(frame, renderPanel);
        setupMouseListeners(frame, renderPanel);

        frame.setTitle("OOM Project3");
        frame.add(renderPanel);
        frame.setSize(windowSize, windowSize);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void saveMousePosition(JPanel renderPanel, MouseEvent mouseEvent) {
        if (mouseEvent.getID() == MouseEvent.MOUSE_CLICKED) {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                resetValues();
                renderPanel.repaint();
            }
        } else if (mouseEvent.getID() == MouseEvent.MOUSE_DRAGGED) {
            if (prevMouse[0] != null) {
                float dX = (mouseEvent.getX() - prevMouse[0]), dY = (mouseEvent.getY() - prevMouse[1]);

                if (mouseEvent.isShiftDown()) {
                    roll -= dY * 0.01;
                } else {
                    pitch -= dY * 0.01;
                    heading += dX * 0.01;
                }

                renderPanel.repaint();
            }
        } else if (mouseEvent.getID() == MouseEvent.MOUSE_WHEEL) {
            if (mouseEvent.getClass() == MouseWheelEvent.class && mouseEvent.isControlDown()) {
                fovValue += 4 * ((MouseWheelEvent) mouseEvent).getWheelRotation();
                renderPanel.repaint();
            }
        }

        prevMouse[0] = mouseEvent.getX();
        prevMouse[1] = mouseEvent.getY();
    }

    public static Color getShade(Color color, double shade) {
        final double shadingMultiplier = 2.4;

        double redLinear = Math.pow(color.getRed(), shadingMultiplier) * shade;
        double greenLinear = Math.pow(color.getGreen(), shadingMultiplier) * shade;
        double blueLinear = Math.pow(color.getBlue(), shadingMultiplier) * shade;

        int red = (int) Math.pow(redLinear, 1 / shadingMultiplier);
        int green = (int) Math.pow(greenLinear, 1 / shadingMultiplier);
        int blue = (int) Math.pow(blueLinear, 1 / shadingMultiplier);

        return new Color(red, green, blue);
    }

    public static void setupKeyboardListeners(JFrame frame, JPanel renderPanel) {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyChar() == ' ') {
                    showEdges ^= true;
                    renderPanel.repaint();
                }
            }
        });
    }

    public static void setupMouseListeners(JFrame frame, JPanel renderPanel) {
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                saveMousePosition(renderPanel, mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                renderPanel.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                renderPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                saveMousePosition(renderPanel, mouseEvent);
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                saveMousePosition(renderPanel, mouseEvent);
            }
        });

        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                saveMousePosition(renderPanel, mouseWheelEvent);
            }
        });
    }
}
