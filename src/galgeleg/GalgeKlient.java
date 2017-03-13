/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package galgeleg;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class GalgeKlient {
    
    
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, Exception{
        run();
    }

    private static void run() throws MalformedURLException, RemoteException, NotBoundException, Exception {
        URL url = new URL("http://localhost:18371/galgeservice?wsdl");
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(url, qname);
        GalgelegI g = service.getPort(GalgelegI.class);
        
        Scanner scan = new Scanner(System.in);
        boolean loggedIn = false;
        String username = "", password = "";
        
        while (!loggedIn){
          System.out.println("Indtast brugernavn");
          username = scan.next();
          System.out.println("Indtast password");
          password = scan.next();

          boolean login = g.login(username,password);

          if (login) {
              loggedIn = true;
              g.startSpil(username, password);
          }
          else {
              loggedIn = false;
              System.out.println("Forkert brugernavn eller password. Prøv igen.");
          }
      }

      //g.nulstil(username, password);

      g.logStatus(username, password);

      while (!g.erSpilletSlut(username, password)) {
          g.logStatus(username, password);
          System.out.println("Ordet: "+g.getSynligtOrd(username, password));
          System.out.println("Gæt et bogstav");
          String input = scan.next();
          g.gætBogstav(input, username, password);
          System.out.println("Du har gættet på: " + g.getBrugteBogstaver(username, password));
          int liv = 6 - g.getAntalForkerteBogstaver(username, password);
          if (!g.erSpilletTabt(username, password))
      {
          System.out.println("Du har " + liv + " liv tilbage");
          System.out.println(drawFigure(liv));
      }
          
      }
      if (g.erSpilletVundet(username, password))
      {
          System.out.println("Tillykke du gættede det rigtige ord: " + g.getOrdet(username, password));
          g.addWonGame(username, password);
          System.out.println("Vil du spille igen?");
          String svar = scan.next();
          if (svar.contains("ja")) {
              run(); 
          }
          if (svar.contains("nej")) {
             System.out.println("Farvel. Se stats på på http://52.213.91.0 ");
          }
          
      }
      else
      {
          System.out.println(drawFigure(0));
          System.out.println("Desværre tabte du. Ordet var: "+g.getOrdet(username, password));
          g.addLostGame(username, password);
          System.out.println("Vil du spille igen? ja eller nej?");
          String svar = scan.next();
          if (svar.contains("ja")) {
              run(); 
          }
          if (svar.contains("nej")) {
             System.out.println("Farvel. Se stats på http://52.213.91.0 ");
          }
      }
  }
        public static String drawFigure(int lives) {
        //Jeg kom til at slettet min egen Ascii figur ved en fejl, så jeg fandt istedet noget på nettet (https://github.com/swartech/Hangman/blob/master/src/Hangman.java)
        String image = "";
        switch (lives) {
            case 0:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O--\n"
                        + " |  / \\ \n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 1:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O--\n"
                        + " |    \\ \n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 2:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O--\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 3:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 4:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " |   O\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 5:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 6:
                image = " _____\n"
                        + " |/  |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

        }
        return image;
    }

}