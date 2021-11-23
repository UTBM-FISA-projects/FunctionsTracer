package Views;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Représente la fenêtre de temps de chargement
 *
 * @author Kilian GOËTZ
 */
public class SplashScreen extends JFrame {

    /**
     *
     * Créer une fenêtre de chargement avec une animation (gif) fermer automatiquement à la fin de celui-ci
     * l'annimation peut-être passer en appuyant sur entrée
     *
     * @author Kilian GOËTZ
     */

    public SplashScreen(){
        ImageIcon icon = new ImageIcon("resources/functionTracer.gif");
        JLabel image = new JLabel(icon);
        setUndecorated(true);
        setSize(640, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(image);
        setVisible(true);
        synchronized (this){
            try {
                this.wait(5000);
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     *
     * Permet de stopper l'animation en appuyant sur entrée
     *
     * @author Kilian GOËTZ
     * @param e
     */

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        synchronized (this){
            this.notifyAll();
            this.dispose();
        }
    }

    public void main(String[] args) throws InterruptedException {
        SplashScreen test = new SplashScreen();
    }
}
