package com.oom.group3;

// Swing imports
import com.oom.group3.shapes.CubeShape;
import com.oom.group3.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// AWT imports
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;

// Event listeners
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

// utilities
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Cube {
    // the application window size
    static final int windowSize = 960;

    // store the old mouse coordinates to compute deltas
    static Integer[] prevMouse = { null, null };

    // angles along each axis and FOV value
    static double alpha, beta, gamma, fovValue;

    // toggle, show edges or not - for all triangles
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
        alpha = beta = gamma = 0;
        fovValue = 60;
    }

    public static void createAndShowGUI() {
        // create a frame (window)
        JFrame frame = new JFrame();

        // initialize with default values
        resetValues();

        // panel to display render results
        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                // fill the background with BLACK color
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // retrieve the list of polygons constructing the 3D object
                ArrayList<Polygon3D> polygons = new CubeShape().getPolygons();

                // compute the 'fov' value from the 'fovAngle'
                double fovAngle = Math.toRadians(fovValue), fov = Math.tan(fovAngle / 2) / 200;

                // X-axis
                Matrix4 xAxisRotation = new Matrix4(new double[] {
                        1, 0, 0, 0,
                        0, Math.cos(alpha), Math.sin(alpha), 0,
                        0, -Math.sin(alpha), Math.cos(alpha), 0,
                        0, 0, 0, 1
                });

                // Y-axis
                Matrix4 yAxisRotation = new Matrix4(new double[] {
                        Math.cos(gamma), 0, -Math.sin(gamma), 0,
                        0, 1, 0, 0,
                        Math.sin(gamma), 0, Math.cos(gamma), 0,
                        0, 0, 0, 1
                });

                // Z-axis
                Matrix4 zAxisRotation = new Matrix4(new double[] {
                        Math.cos(beta), -Math.sin(beta), 0, 0,
                        Math.sin(beta), Math.cos(beta), 0, 0,
                        0, 0, 1, 0,
                        0, 0, 0, 1
                });

                // scaling
                Matrix4 scalingTransform = new Matrix4(new double[] {
                        1, 0, 0, 0,
                        0, 1, 0, 0,
                        0, 0, 1, 0,
                        0, 0, -400, 1
                });

                // create a transform matrix
                // to apply all the three rotations and scaling
                Matrix4 transformMatrix = xAxisRotation
                        .multiply(yAxisRotation)
                        .multiply(zAxisRotation)
                        .multiply(scalingTransform);

                // 'double buffer' to store rendered pixels' data
                BufferedImage doubleBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

                // the 'depth buffer' for storing per-pixel depth values
                double[] zBuffer = new double[doubleBuffer.getWidth() * doubleBuffer.getHeight()];

                // initialize array with extremely far away depths
                Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

                for (Polygon3D polygon : polygons) {
                    // apply transform to the points in 3D space to be converted to 2D space
                    Point3D A = transformMatrix.transform(polygon.A);
                    Point3D B = transformMatrix.transform(polygon.B);
                    Point3D C = transformMatrix.transform(polygon.C);

                    // compute
                    Vector AB = B.copy().sub(A);
                    Vector AC = C.copy().sub(A);

                    // find the surface normal
                    Vector norm = AB.cross(AC).normalize();
                    double angleCos = Math.abs(norm.z);

                    // apply the scaling using FOV
                    A.x = A.x / (-A.z) / fov;
                    A.y = A.y / (-A.z) / fov;

                    B.x = B.x / (-B.z) / fov;
                    B.y = B.y / (-B.z) / fov;

                    C.x = C.x / (-C.z) / fov;
                    C.y = C.y / (-C.z) / fov;

                    // transform to the center of the screen
                    A.x += windowSize / 2.0;
                    A.y += windowSize / 2.0;

                    B.x += windowSize / 2.0;
                    B.y += windowSize / 2.0;

                    C.x += windowSize / 2.0;
                    C.y += windowSize / 2.0;

                    // find the tight-fitting bounding box for the triangle
                    int minX = (int) Math.max(0, Math.ceil(Math.min(A.x, Math.min(B.x, C.x))));
                    int maxX = (int) Math.min(doubleBuffer.getWidth() - 1, Math.floor(Math.max(A.x, Math.max(B.x, C.x))));
                    int minY = (int) Math.max(0, Math.ceil(Math.min(A.y, Math.min(B.y, C.y))));
                    int maxY = (int) Math.min(doubleBuffer.getHeight() - 1, Math.floor(Math.max(A.y, Math.max(B.y, C.y))));

                    // area of the triangle
                    double triangleArea = (A.y - C.y) * (B.x - C.x) + (B.y - C.y) * (C.x - A.x);

                    // loop over the bounding box
                    // to find which points lie inside the current triangle
                    for (int y = minY; y <= maxY; y++) {
                        for (int x = minX; x <= maxX; x++) {
                            // Barycentric coordinate system - point inside triangle
                            double a = ((y - C.y) * (B.x - C.x) + (B.y - C.y) * (C.x - x)) / triangleArea;
                            double b = ((y - A.y) * (C.x - A.x) + (C.y - A.y) * (A.x - x)) / triangleArea;
                            double c = 1 - (a + b);

                            // 0 <= a <= 1 && 0 <= b <= 1 && 0 <= c <= 1
                            if (a >= 0 && a <= 1 && b >= 0 && b <= 1 && c >= 0 && c <= 1) {
                                double depth = a * A.z + b * B.z + c * C.z;
                                int zIndex = y * doubleBuffer.getWidth() + x;

                                // check if the current point can appear in front
                                if (zBuffer[zIndex] < depth) {
                                    final double k = 0.01;
                                    boolean liesOnEdge = !(a >= k && a <= 1-k && b >= k && b <= 1-k && c >= k && c <= 1-k);

                                    // fill the color for the current point
                                    doubleBuffer.setRGB(x, y, getShade((showEdges && liesOnEdge) ? Color.DARK_GRAY : polygon.color, angleCos).getRGB());

                                    // update the depth buffer with the new minimum depth value
                                    zBuffer[zIndex] = depth;
                                }
                            }
                        }
                    }
                }

                // finally, draw the processed pixels - rendered image
                g2.drawImage(doubleBuffer, 0, 0, null);
            }
        };

        // setup event listeners
        setupKeyboardListeners(frame, renderPanel);
        setupMouseListeners(frame, renderPanel);

        // set frame attributes
        frame.setTitle("OOM Project3");
        frame.add(renderPanel);
        frame.setSize(windowSize, windowSize);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * save the mouse position to be used in next event
     * to find the 'delta' values for both 'x' and 'y'
     *
     * @param renderPanel: the current panel on which we are rendering
     * @param mouseEvent: the mouse event that trigger this method
     */
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
                    beta -= dY * 0.01;
                } else {
                    alpha -= dY * 0.01;
                    gamma += dX * 0.01;
                }

                renderPanel.repaint();
            }
        } else if (mouseEvent.getID() == MouseEvent.MOUSE_WHEEL) {
            if (mouseEvent.getClass() == MouseWheelEvent.class && mouseEvent.isControlDown()) {
                fovValue += 4 * ((MouseWheelEvent) mouseEvent).getWheelRotation();

                // constrain the 'fovAngle' between 30 and 120
                if (fovValue > 120) {
                    fovValue = 120;
                }
                else if (fovValue < 30) {
                    fovValue = 30;
                }

                // update the 'renderPanel' to be redrawn
                renderPanel.repaint();
            }
        }

        // save the mouse position, update the older values
        prevMouse[0] = mouseEvent.getX();
        prevMouse[1] = mouseEvent.getY();
    }

    /**
     * apply a simple fading shade to a given color
     *
     * @param color: the color to which to apply the shade
     * @param shade: the amount of shade to apply
     * @return Color: the resulting color after applying the shade
     */
    public static Color getShade(Color color, double shade) {
        final double shadingMultiplier = 2.4;

        double redLinear = Math.pow(color.getRed(), shadingMultiplier) * shade,
                greenLinear = Math.pow(color.getGreen(), shadingMultiplier) * shade,
                blueLinear = Math.pow(color.getBlue(), shadingMultiplier) * shade;

        int red = (int) Math.pow(redLinear, 1 / shadingMultiplier),
                green = (int) Math.pow(greenLinear, 1 / shadingMultiplier),
                blue = (int) Math.pow(blueLinear, 1 / shadingMultiplier);

        return new Color(red, green, blue);
    }

    // to interpret keyboard input
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

    // to interpret and render mouse movements
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
