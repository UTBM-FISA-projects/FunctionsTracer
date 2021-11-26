import Views.MainFrame;
import Views.SplashScreen;

import java.util.Locale;

public class FunctionsTracer {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr"));
        new SplashScreen();
        MainFrame main = new MainFrame();
        main.setVisible(true);
    }
}
