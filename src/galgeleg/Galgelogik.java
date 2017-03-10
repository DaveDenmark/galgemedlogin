package galgeleg;

import brugerautorisation.transport.soap.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

@WebService(endpointInterface = "galgeleg.GalgelegI")
public class Galgelogik  {
  private ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;
  private ArrayList<String> brugteForkerteBogstaver = new ArrayList<String>();
  private ArrayList<String> senesteSpil = new ArrayList<>();
  private Brugeradmin ba;
  private String brugerNavn;
  private String adgangsKode;
  

  public ArrayList<String> getSenesteSpil() {
      return senesteSpil;
  }
  
  public void tilføjSpil(String brugernavn) {
      senesteSpil.add(brugernavn);
  }
  
  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }
  
  public ArrayList<String> getBrugteForkerteBogstaver() {
     return brugteForkerteBogstaver;
  }

  
  public String getSynligtOrd() {
    return synligtOrd;
  }

  
  public String getOrdet() {
    return ordet;
  }

  
  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  
  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }

  
  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  
  public boolean erSpilletTabt() {
    return spilletErTabt;
  }

  
  public boolean erSpilletSlut() {
    return spilletErTabt || spilletErVundet;
  }

  public Galgelogik() throws Exception {
    hentOrdFraDr();
    nulstil();
  }
  
  
  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    opdaterSynligtOrd();
  }

  
  public void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  
  public void gætBogstav(String bogstav) {
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav);
    if (brugteBogstaver.contains(bogstav)) return;
    if (spilletErVundet || spilletErTabt) return;

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      brugteForkerteBogstaver.add(bogstav);
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public void logStatus() {
    System.out.println("---------- ");
    System.out.println("- ordet (skult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt) System.out.println("- SPILLET ER TABT");
    if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
  }


  public static String hentUrl(String url) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }

  public void hentOrdFraDr() throws Exception {
    DRdkLeg drdkleg = new DRdkLeg();
    String data = drdkleg.getWordsDR();
    System.out.println("data = " + data);

    data = data.replaceAll("<.+?#>,:/", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
    System.out.println("data = " + data);
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
  }
  
    public boolean login(String bruger, String adgangskode) throws Exception {
        System.out.println("Du skal logge ind før, at du kan spille Galgeleg");
      
          URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
       // URL url = new URL("http://s154280@ubuntu4.javabog.dk:18371/galgelegtjeneste?wsdl");
        QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
        Service service = Service.create(url, qname);
            ba = service.getPort(Brugeradmin.class);
            
            try {
        Bruger b = ba.hentBruger(bruger, adgangskode);
        brugerNavn = bruger;
        adgangsKode = adgangskode;
    } catch (Throwable e) {
        return false;
    }
        return true;
    }
    
    public void addLostGame() {
        try {
            if (ba.getEkstraFelt(brugerNavn, adgangsKode, "LostGames") == null) {
                ba.setEkstraFelt(brugerNavn, adgangsKode, "LostGames", 1);
            }
            else {
                ba.setEkstraFelt(brugerNavn, adgangsKode, "LostGames", getLostGames() + 1 );
            }
        } 
        catch (Throwable e) {
        }
    }
    
    public void addWonGame() {
        try {
            if (ba.getEkstraFelt(brugerNavn, adgangsKode, "WonGames") == null) {
                ba.setEkstraFelt(brugerNavn, adgangsKode, "WonGames", 1);
            }
            else {
                ba.setEkstraFelt(brugerNavn, adgangsKode, "WonGames", getWonGames() + 1 );
            }
        } 
        catch (Throwable e) {
        }
    }
    
    public int getLostGames() {
        try {
            return (int) ba.getEkstraFelt(brugerNavn, adgangsKode, "LostGames");
        }
        catch (Throwable e) {
            return 0;
        }
    }
    
    public int getWonGames() {
        try {
            return (int) ba.getEkstraFelt(brugerNavn, adgangsKode, "WonGames");
        }
        catch (Throwable e) {
            return 0;
        }
        
    }
}
