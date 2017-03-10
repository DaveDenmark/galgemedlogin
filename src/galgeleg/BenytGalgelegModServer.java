/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package galgeleg;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author IamHexor
 */

public class BenytGalgelegModServer {
    
    //main run
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, Exception{
        run();
    }
    
    //menuerne
    private static void run() throws MalformedURLException, RemoteException, NotBoundException, Exception {
      // URL url = new URL("http://ubuntu4.javabog.dk:18371/galgeservice?wsdl");
        URL url = new URL("http://localhost:18371/galgeservice?wsdl");
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(url, qname);
        GalgelegI g = service.getPort(GalgelegI.class);
        
        Scanner scan = new Scanner(System.in);
        boolean loggedIn = false;
        int choice;
   
        
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
          System.out.println("Du har gættet på: " + g.getBrugteBogstaver());
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
        
//        while(true){
//            if(!loggedIn){
//                System.out.println("1. Log ind");
//                System.out.println("2. Afslut");
//                
//                choice = scan.nextInt();
//                switch(choice){
//                    case 1:
//                        System.out.print("Indtast dit brugernavn: ");
//                       String brugernavn = scan.next();
//                        System.out.print("Indtast din adgangskode: ");
//                       String adgangskode = scan.next();
//                        
//                        boolean login = g.login(brugernavn, adgangskode); break;
//                    case 2: break;
//                    default: break;
//                }
//            }else{
//                System.out.println("1. Nyt spil");
//                System.out.println("2. Log ud");
//                
//                choice = scan.nextInt();
//                switch(choice){
//                    case 1: spil(); ; break;
//                    case 2: loggedIn = false; break;
//                    default: break;
//                }
//            }
//        }
    }
    
//    private static void login() throws MalformedURLException, RemoteException, NotBoundException {
//        System.out.println("Du skal logge ind før, at du kan spille Galgeleg");
//        while (true) {
//          //  GalgelegI g = (GalgelegI) Naming.lookup("rmi://localhost:18371/Galgeleg");
//                   URL url = new URL("http://localhost:18371/galgelegtjeneste?wsdl");
//       // URL url = new URL("http://s154280@ubuntu4.javabog.dk:18371/galgelegtjeneste?wsdl");
//        QName qname = new QName("http://galgeleg/", "GalgeImplService");
//        Service service = Service.create(url, qname);
//
//        GalgelegI g = service.getPort(GalgelegI.class);
//            Scanner login = new Scanner(System.in);
//            System.out.println("Indtast dit brugernavn (studie-nr.)");
//            String bruger = login.nextLine();
//
//            System.out.println("Indtast dit password");
//            String password = login.nextLine();
//
//            if (g.hentBruger(bruger, password))
//                break;
//            else
//               System.out.println("Forkert login - prøv igen");
//
//        }
//    }
    
//    private static void spil() throws MalformedURLException {
//        URL url = new URL("http://localhost:18371/galgelegtjeneste?wsdl");
//        // URL url = new URL("http://s154280@ubuntu4.javabog.dk:18371/galgelegtjeneste?wsdl");
//        QName qname = new QName("http://galgeleg/", "GalgeImplService");
//        Service service = Service.create(url, qname);
//        GalgelegI g = service.getPort(GalgelegI.class);
//        
//        Scanner scan = new Scanner(System.in);
//        String gaet;
//        int liv = 7;
//        
//        System.out.println("- Spillet er startet -");
//        
//        while(!g.erSpilletSlut()){
//            System.out.println("Dit ord "+ g.getSynligtOrd());
//            System.out.println("Dine liv " + liv);
//            System.out.println("Gæt på et bogstav");
//            
//            gaet= scan.nextLine();
//            g.gætBogstav(gaet);
//            if (!g.getOrdet().contains(gaet)) {
//                System.out.println("Du gættede forkert!");
//                liv--;
//            }else{
//                System.out.println("Du gættede rigtigt");
//            }
//            
//            if(g.erSpilletTabt()){
//                System.out.println("Du har tabt");
//                System.out.println("Order var: " + g.getOrdet());
//            }else if(g.erSpilletVundet()){
//                System.out.println("Du har vundet");
//            }
//        }
//        g.nulstil();
//    }
   
