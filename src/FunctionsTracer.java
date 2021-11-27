import Views.MainFrame;
import Views.SplashScreen;

import java.util.Locale;

/**
 * Classe principale de l'application.
 */
public class FunctionsTracer {

    /**
     * Point d'entrée de l'application.<br>
     * Affiche un splashscreen (peut être passé en appuyant sur une touche) puis l'appli.
     *
     * @param args arguments de la ligne de commande - inutilisés
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr"));
        new SplashScreen();
        MainFrame main = new MainFrame();
        main.setVisible(true);
    }
}
