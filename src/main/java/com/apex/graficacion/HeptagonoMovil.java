package com.apex.graficacion;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import jogamp.demos.redbook.glredbook10.GLSkeleton;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase que despliega un JFrame con un GLJPanel.
 *
 * Muestra una proyección de un heptágono.
 *
 * Para rotar:
 *
 * Teclado:
 *     Flecha Izq/Derecha: Rotación izq/der
 *     Cualquier otra tecla: Detiene rotación
 *
 * Mouse:
 *     Clic izq: Rotación izq.
 *     Clic der: Detiene rotación.
 *
 *
 *  -----------------------------------------------
 *
 *  A class that shows a JFrame with a GJPanel.
 *
 *  Shows a prrojection of an heptagon.
 *
 *  Rotation controls:
 *
 *      Keyboard:
 *          Left/Right keys: L/R Rotation.
 *          Any other key: Stop roration.
 *
 *      Mouse:
 *          Left Click: Rotate Left
 *          Right Click: Stops rotation
 *
 *  Autor: José Carlos López
 */

public class HeptagonoMovil extends GLSkeleton<GLJPanel> implements GLEventListener, KeyListener, MouseListener {

    private GL2 gl;
    private float spin = 0;
    private float spinDelta = 0;

    public static void main(String[] args){
        HeptagonoMovil hepta = new HeptagonoMovil();
        FPSAnimator animator = new FPSAnimator(hepta.drawable, 144);
        hepta.setAnimator(animator);
        JFrame frame = new JFrame("Heptágono Movil");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(hepta.drawable);
        frame.setVisible(true);
        hepta.drawable.requestFocusInWindow();
        animator.start();
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected GLJPanel createDrawable() {
        GLCapabilities  glCapabilities = new GLCapabilities(null);
        GLJPanel gljPanel = new GLJPanel(glCapabilities);
        gljPanel.setDoubleBuffered(true);
        gljPanel.addGLEventListener(this);
        gljPanel.addKeyListener(this);
        gljPanel.addMouseListener(this);
        return gljPanel;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL2.GL_FLAT);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        animator.stop();
        gl = null;
    }

    @Override
    public synchronized void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glPushMatrix();
        gl.glRotatef(spin, 0.0f, 0.0f, 1.0f);
        drawHeptagon(gl);
        gl.glPopMatrix();
        gl.glFlush();
        spinIt();
    }

    private void drawHeptagon(GL2 gl){
        gl.glColor3f(0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_POLYGON);
        for(int i = 0; i < 7; i++)
            gl.glVertex2d(Math.sin(i/7d*2d*Math.PI)*30, Math.cos(i/7d*2d*Math.PI)*30);
        gl.glEnd();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        float aspect = 0;
        if (width <= height) {
            aspect = (float) height / (float) width;
            gl.glOrtho(-50.0, 50.0, -50.0 * aspect, 50.0 * aspect, //
                    -1.0, 1.0);
        } else {
            aspect = (float) width / (float) height;
            gl.glOrtho(-50.0 * aspect, 50.0 * aspect, -50.0, 50.0, //
                    -1.0, 1.0);
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void spinIt(){ spin = (spin > 360)? spin -= 360: spin + spinDelta; }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case 37:
                spinDelta = 2f;
                break;
            case 39:
                spinDelta = -2f;
                break;
            default:
                spinDelta = 0;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                spinDelta = 2f;
                break;
            case MouseEvent.BUTTON2:
            case MouseEvent.BUTTON3:
                spinDelta = 0f;
                break;
        }
        super.refresh();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
