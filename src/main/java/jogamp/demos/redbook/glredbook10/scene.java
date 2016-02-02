package jogamp.demos.redbook.glredbook10;

import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.*;
import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;

/**
 * This program demonstrates the use of the GL lighting model. Objects are drawn
 * using a grey material characteristic. A single light source illuminates the
 * objects.
 * 
 * @author Kiet Le (Java port) Ported to JOGL 2.x by Claudio Eduardo Goes
 */
public class scene//
        extends GLSkeleton<GLJPanel>
        implements GLEventListener, KeyListener {
    private GLUT glut;

    @Override
    protected GLJPanel createDrawable() {
        GLCapabilities caps = new GLCapabilities(null);
        //
        GLJPanel panel = new GLJPanel(caps);
        panel.addGLEventListener(this);
        panel.addKeyListener(this);
        return panel;
    }

    public static void main(String[] args) {
        scene demo = new scene();
        //
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("scene");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(demo.drawable);
        frame.setVisible(true);
        demo.drawable.requestFocusInWindow();
    }

    /*
     * Initialize material property and light source.
     */
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glut = new GLUT();
        //
        float light_ambient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
        float light_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        float light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        /* light_position is NOT default value */
        float light_position[] = { 1.0f, 1.0f, 1.0f, 0.0f };

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_specular, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glDepthFunc(GL.GL_LESS);
        gl.glEnable(GL.GL_DEPTH_TEST);

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        //
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glPushMatrix();
        gl.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);

        gl.glPushMatrix();
        gl.glTranslatef(-0.75f, 0.5f, 0.0f);
        gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        glut.glutSolidTorus(0.275, 0.85, 20, 20);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(-0.75f, -0.5f, 0.0f);
        gl.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
        glut.glutSolidCone(1.0, 2.0, 20, 20);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.75f, 0.0f, -1.0f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        gl.glPopMatrix();
        gl.glFlush();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl = drawable.getGL().getGL2();
        //
        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if (w <= h)
            gl.glOrtho(-2.5, 2.5, -2.5 * (float) h / (float) w, 2.5 * (float) h
                    / (float) w, -10.0, 10.0);
        else
            gl.glOrtho(-2.5 * (float) w / (float) h, 2.5 * (float) w
                    / (float) h, -2.5, 2.5, -10.0, 10.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
        case KeyEvent.VK_ESCAPE:
            System.exit(0);
            break;

        default:
            break;
        }
    }

    public void keyReleased(KeyEvent key) {
    }

    public void dispose(GLAutoDrawable arg0) {
         
    }

}
