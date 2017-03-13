package galgeleg;

import brugerautorisation.transport.soap.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

@WebService(endpointInterface = "galgeleg.GalgelegI")
public class Galgelogik  {
  private Brugeradmin ba;
  private ArrayList<String> muligeOrd = new ArrayList<>();
  
  public String getBrugteBogstaver(String brugernavn, String adgangskode) {
      try {
          return (String)ba.getEkstraFelt(brugernavn, adgangskode, "brugteBogstaver");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return null;
  }
  
    public String getSynligtOrd(String brugernavn, String adgangskode) {
        try {
          return (String) ba.getEkstraFelt(brugernavn, adgangskode, "synligtOrd");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return null;
  }
  
  public String getBrugteForkerteBogstaver(String brugernavn, String adgangskode) {
      try {
          return (String) ba.getEkstraFelt(brugernavn, adgangskode, "brugteForkerteBogstaver");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return null;
  }

  
  public String getOrdet(String brugernavn, String adgangskode) {
      try {
          return (String)ba.getEkstraFelt(brugernavn, adgangskode, "ordet");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return null;
  }

  
  public int getAntalForkerteBogstaver(String brugernavn, String adgangskode) {
      try {
          return (int) ba.getEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return -1;
  }

  
  public boolean erSidsteBogstavKorrekt(String brugernavn, String adgangskode) {
      try {
          return (boolean) ba.getEkstraFelt(brugernavn, adgangskode, "sidsteBogstavVarKorrekt");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return false;
  }

  
  public boolean erSpilletVundet(String brugernavn, String adgangskode) {
      try {
          return (boolean) ba.getEkstraFelt(brugernavn, adgangskode, "spilletErVundet");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return false;
  }

  
  public boolean erSpilletTabt(String brugernavn, String adgangskode) {
      try {
          return (boolean) ba.getEkstraFelt(brugernavn, adgangskode, "spilletErTabt");
      }
      catch(Throwable e) {
          e.printStackTrace();
      }
    return false;
  }

  
  public boolean erSpilletSlut(String brugernavn, String adgangskode) {
    return erSpilletVundet(brugernavn, adgangskode) || erSpilletTabt(brugernavn, adgangskode);
  }

  
  public void nulstil(String brugernavn, String adgangskode) {
      try {
          ba.setEkstraFelt(brugernavn, adgangskode, "brugteBogstaver", " ");
          ba.setEkstraFelt(brugernavn, adgangskode, "brugteForkerteBogstaver", "");
          ba.setEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver", 0);
          ba.setEkstraFelt(brugernavn, adgangskode, "spilletErVundet", false);
          ba.setEkstraFelt(brugernavn, adgangskode, "spilletErTabt", false);
          ba.setEkstraFelt(brugernavn, adgangskode, "ordet", null);
      }
    catch (Throwable e) {
        e.printStackTrace();
    }
    opdaterSynligtOrd(brugernavn, adgangskode);
  }

  
  public void opdaterSynligtOrd(String brugernavn, String adgangskode) {
      try {
          ba.setEkstraFelt(brugernavn, adgangskode, "synligtOrd", "");
          ba.setEkstraFelt(brugernavn, adgangskode, "spilletErVundet", true);
          String ordet = (String) ba.getEkstraFelt(brugernavn, adgangskode, "ordet");
          String brugteBogstaver = (String) getBrugteBogstaver(brugernavn, adgangskode);
          for (int n = 0; n < ordet.length(); n++) {
              String bogstav = ordet.substring(n, n + 1);
              
              if (brugteBogstaver.contains(bogstav)) {
                  ba.setEkstraFelt(brugernavn, adgangskode, "synligtOrd", ba.getEkstraFelt(brugernavn, adgangskode, "synligtOrd") + bogstav);
              } else {
                  ba.setEkstraFelt(brugernavn, adgangskode, "synligtOrd", ba.getEkstraFelt(brugernavn, adgangskode, "synligtOrd") + "*");
                  ba.setEkstraFelt(brugernavn, adgangskode, "spilletErVundet", false);
              }
          }
      }
      catch (Throwable e) {
          e.printStackTrace();
      }
  }

  
  public void gætBogstav(String bogstav, String brugernavn, String adgangskode) {
    if (bogstav.length() != 1) return;
    try {
        System.out.println("Der gættes på bogstavet: " + bogstav);
        String temp = (String) ba.getEkstraFelt(brugernavn, adgangskode, "brugteBogstaver");
        if (temp.contains(bogstav)) return;
        if (erSpilletSlut(brugernavn, adgangskode)) return;

        ba.setEkstraFelt(brugernavn, adgangskode, "brugteBogstaver", (String)getBrugteBogstaver(brugernavn, adgangskode) + bogstav);
        

    if (((String)ba.getEkstraFelt(brugernavn, adgangskode, "ordet")).contains(bogstav)) {
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      ba.setEkstraFelt(brugernavn, adgangskode, "brugteForkerteBogstaver", (String)getBrugteBogstaver(brugernavn, adgangskode) + bogstav);
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      ba.setEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver", ((int) ba.getEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver")) + 1);
      if (((int) ba.getEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver")) > 6) {
        ba.setEkstraFelt(brugernavn, adgangskode, "spilletErTabt", true);
      }
    }
    opdaterSynligtOrd(brugernavn, adgangskode);
    }
    catch (Throwable e) {
        
    }
  }

  public void logStatus(String brugernavn,String adgangskode) {
    try {
        System.out.println("---------- ");
        System.out.println("- ordet (skult) = " + ba.getEkstraFelt(brugernavn, adgangskode, "ordet"));
        System.out.println("- synligtOrd = " + ba.getEkstraFelt(brugernavn, adgangskode, "synligtOrd"));
        System.out.println("- forkerteBogstaver = " + ba.getEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver"));
        System.out.println("- brugteBogstaver = " + ba.getEkstraFelt(brugernavn, adgangskode, "brugteBogstaver"));
        if ((boolean) ba.getEkstraFelt(brugernavn, adgangskode, "erSpilletTabt")) System.out.println("- SPILLET ER TABT");
        if ((boolean) ba.getEkstraFelt(brugernavn, adgangskode, "erSpilletVundet")) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
    }
    catch (Throwable e) {
        
    }
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

  public void hentOrdFraDR() throws Exception {
    DRdkLeg drdkleg = new DRdkLeg();
    String data = drdkleg.getWordsDR();
    System.out.println("data = " + data);
    data = data.replaceAll("<.+?#>,:/", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
    System.out.println("data = " + data);
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));
    System.out.println("muligeOrd = " + muligeOrd);
  }
  
    public boolean login(String brugernavn, String adgangskode) throws Exception {
        try {
            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
            Service service = Service.create(url, qname);
            ba = service.getPort(Brugeradmin.class);
            startSpil(brugernavn, adgangskode);
        }
        catch (Throwable e) {
            return false;
        }
        return true;
    }
    
    public void startSpil(String brugernavn, String adgangskode) {
        try {
            //if (ba.getEkstraFelt(brugernavn, adgangskode, "Spil") == null) {
                hentOrdFraDR();
                String ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
                ba.setEkstraFelt(brugernavn, adgangskode, "ordet", ordet);
                opdaterSynligtOrd(brugernavn, adgangskode);
                ba.setEkstraFelt(brugernavn, adgangskode, "brugteBogstaver", " ");
                ba.setEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver", 0);
                ba.setEkstraFelt(brugernavn, adgangskode, "spilletErVundet", false);
                ba.setEkstraFelt(brugernavn, adgangskode, "spilletErTabt", false);
                ba.setEkstraFelt(brugernavn, adgangskode, "brugteForkerteBogstaver", " ");
                ba.setEkstraFelt(brugernavn, adgangskode, "Spil", true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public void addLostGame(String brugernavn, String adgangskode) {
        try {
            if (ba.getEkstraFelt(brugernavn, adgangskode, "LostGames") == null) {
                ba.setEkstraFelt(brugernavn, adgangskode, "LostGames", 1);
            }
            else {
                ba.setEkstraFelt(brugernavn, adgangskode, "LostGames", getLostGames(brugernavn, adgangskode) + 1 );
            }
        } 
        catch (Throwable e) {
        }
    }
    
    public void addWonGame(String brugernavn, String adgangskode) {
        try {
            if (ba.getEkstraFelt(brugernavn, adgangskode, "WonGames") == null) {
                ba.setEkstraFelt(brugernavn, adgangskode, "WonGames", 1);
            }
            else {
                ba.setEkstraFelt(brugernavn, adgangskode, "WonGames", getWonGames(brugernavn, adgangskode) + 1 );
            }
        } 
        catch (Throwable e) {
        }
    }
    
    public int getLostGames(String brugernavn, String adgangskode) {
        try {
            return (int) ba.getEkstraFelt(brugernavn, adgangskode, "LostGames");
        }
        catch (Throwable e) {
            return 0;
        }
    }
    
    public int getWonGames(String brugernavn, String adgangskode) {
        try {
            return (int) ba.getEkstraFelt(brugernavn, adgangskode, "WonGames");
        }
        catch (Throwable e) {
            return 0;
        }
    }
}
