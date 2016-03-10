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

public class EstrellaMovil extends GLSkeleton<GLJPanel> implements GLEventListener, KeyListener, MouseListener  {

    private GL2 gl;
    private float spin = 0;
    private float spinDelta = 0;
    private double[] vertex = {0, 0, 0, 1, 0.245d, 0.5d};
    private double[] vertex2 = {0, 0, 0, 1, -0.24995d, 0.5d};
    private double multiplier = 20;
    private double angle = 51.42d;

    public static void main (String[] args){
        EstrellaMovil estrellaMovil = new EstrellaMovil();
        FPSAnimator animator = new FPSAnimator(estrellaMovil.drawable, 144);
        estrellaMovil.setAnimator(animator);
        JFrame frame = new JFrame("Estrella Móvil");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(estrellaMovil.drawable);
        frame.setVisible(true);
        estrellaMovil.drawable.requestFocusInWindow();
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

    @SuppressWarnings("Duplicates")
    @Override
    public synchronized void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glPushMatrix();
        gl.glRotatef(spin, 0.0f, 0.0f, 1.0f);
        drawStar();
        gl.glPopMatrix();
        gl.glFlush();
        spinIt();
    }


    private void drawStar(){
        gl.glColor3f(0f, 0f, 1.0f);
        gl.glBegin(GL2.GL_POLYGON);
        for(int rotate = 0; rotate < 7; rotate++) {
            for (int i = 0; i < vertex.length; i += 2) {
                gl.glVertex2d(((vertex[i] * Math.cos((rotate*angle*Math.PI)/180d)) - (vertex[i+1]* Math.sin((rotate*angle*Math.PI)/180d))) * multiplier ,
                              ((vertex[i + 1] * Math.cos((rotate*angle*Math.PI)/180d)) + (vertex[i] * Math.sin((rotate*angle*Math.PI)/180d))) * multiplier);
            }
        }
        gl.glEnd();
        gl.glColor3f(0f, 1f, 0f);
        gl.glBegin(GL2.GL_POLYGON);
        for(int rotate = 0; rotate < 7; rotate++) {
            for (int i = 0; i < vertex2.length; i += 2) {
                gl.glVertex2d(((vertex2[i] * Math.cos((rotate*angle*Math.PI)/180d)) - (vertex2[i+1]* Math.sin((rotate*angle*Math.PI)/180d))) * multiplier ,
                        ((vertex2[i + 1] * Math.cos((rotate*angle*Math.PI)/180d)) + (vertex2[i] * Math.sin((rotate*angle*Math.PI)/180d))) * multiplier);
            }
        }
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

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
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
