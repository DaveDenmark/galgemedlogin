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
        URL url = new URL("http://ubuntu4.javabog.dk:18371/galgeservice?wsdl");
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(url, qname);
        GalgelegI g = service.getPort(GalgelegI.class);
        
        Scanner scan = new Scanner(System.in);
        boolean loggedIn = false;
        
        while (!loggedIn){
          System.out.println("Indtast brugernavn");
          String username = scan.next();
          System.out.println("Indtast password");
          String password = scan.next();

          boolean login = g.login(username,password);

          if (login) {
              loggedIn = true;
              g.tilføjSpil(username);
          }
          else {
              loggedIn = false;
              System.out.println("Forkert brugernavn eller password. Prøv igen.");
          }
      }

      g.nulstil();

      g.logStatus();

      while (!g.erSpilletSlut()) {
          g.logStatus();
          System.out.println("Ordet: "+g.getSynligtOrd());
          System.out.println("Gæt et bogstav");
          String input = scan.next();
          g.gætBogstav(input);
          System.out.println("Du har gættet på: " + g.getBrugteForkerteBogstaver());
          int liv = 6 - g.getAntalForkerteBogstaver();
          if (!g.erSpilletTabt())
      {
          System.out.println("Du har " + liv + " liv tilbage");
      }
          
      }
      if (g.erSpilletVundet())
      {
          System.out.println("Tillykke du gættede det rigtige ord: " + g.getOrdet());
          g.addWonGame();
          
      }
      else
      {
          System.out.println("Desværre tabte du. Ordet var: "+g.getOrdet());
          g.addLostGame();
      }
  }
}