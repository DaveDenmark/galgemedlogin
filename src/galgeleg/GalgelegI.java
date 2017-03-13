/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;

import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author IamHexor
 */
@WebService
public interface GalgelegI {
  @WebMethod  public String getBrugteBogstaver(String bruger, String adgangskode);
   @WebMethod public String getSynligtOrd(String bruger, String adgangskode);
   @WebMethod public String getOrdet(String bruger, String adgangskode);
   @WebMethod public String logStatus(String bruger, String adgangskode);
   @WebMethod public int getAntalForkerteBogstaver(String bruger, String adgangskode);
   //@WebMethod public ArrayList<String> getBrugteForkerteBogstaver();
   @WebMethod public boolean erSpilletVundet(String bruger, String adgangskode);
   @WebMethod public boolean erSpilletTabt(String bruger, String adgangskode);
   @WebMethod public boolean erSpilletSlut(String bruger, String adgangskode);
   @WebMethod public void nulstil(String bruger, String adgangskode);
   @WebMethod public void opdaterSynligtOrd(String bruger, String adgangskode);
   @WebMethod public void gætBogstav(String bogstav, String bruger, String adgangskode);
   @WebMethod boolean login(String bruger, String adgangskode) throws Exception;
   @WebMethod public void startSpil(String brugernavn, String adgangskode); 
   @WebMethod public void addLostGame(String bruger, String adgangskode);
   @WebMethod public void addWonGame(String bruger, String adgangskode);
   @WebMethod public int getLostGames(String bruger, String adgangskode);
   @WebMethod public int getWonGames(String bruger, String adgangskode);
}
