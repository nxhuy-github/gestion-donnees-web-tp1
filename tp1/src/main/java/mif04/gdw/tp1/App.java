package mif04.gdw.tp1;

import mif04.gdw.tp1.itf.WebServer;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        // Démarrage du serveur Jetty embarqué
        WebServer server = new WebServer();
        server.start();
        // Lancement du navigateur à la bonne adresse
        if(Desktop.isDesktopSupported())
        {
            Desktop.getDesktop().browse(server.getUri());
        }
        server.join();
    }
}
