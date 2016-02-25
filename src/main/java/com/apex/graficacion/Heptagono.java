package com.apex.graficacion;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import javax.swing.*;

/**
 * Clase que muestra un heptágono.
 *
 * Autor: José Carlos López
 */
public class Heptagono {

    public static void main(String[] args){
        JFrame jFrame = new JFrame("Heptágono");
        jFrame.setSize(500, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // We create the OpenGL panel
        GLJPanel panel = new GLJPanel();
        // Add the listener
        panel.addGLEventListener(new MyCanvas());
        // Add it to the JFrame
        jFrame.getContentPane().add(panel);
        jFrame.setVisible(true);
    }

    public static class MyCanvas implements GLEventListener {

        private GL2 gl;
        private char[] array = {74, 111, 115, 101, 32, 67, 97, 114, 108, 111, 115, 32, 76, 111, 112, 101, 122};

        @Override
        public void init(GLAutoDrawable drawable) {
            this.gl = drawable.getGL().getGL2();
            for(char c: array)
                System.out.print(c);
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {
            gl = null;
        }

        @Override
        public void display(GLAutoDrawable drawable) {
            gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);
            gl.glLoadIdentity();
            gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
            gl.glOrtho(0, 0, 0, 0, 0, 0);
            render();
            gl.glFlush();
        }

        private void render(){
            drawHeptagon();
        }

        private void drawHeptagon(){
            gl.glColor3f(0, 1, 1);
            gl.glBegin(GL2.GL_POLYGON);
            for(int i = 0; i < 7; i++)
                gl.glVertex2d(Math.sin(i/7d*2d*Math.PI)*30, Math.cos(i/7d*2d*Math.PI)*30);
            gl.glEnd();
        }

        @SuppressWarnings("Duplicates")
        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
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
    }
}
