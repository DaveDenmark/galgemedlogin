/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package galgeleg;
//
//import brugerautorisation.data.Bruger;
//import brugerautorisation.transport.soap.Brugeradmin;
//import java.net.URL;
//import java.rmi.Naming;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.ArrayList;
//import javax.jws.WebService;
//import javax.xml.namespace.QName;
//import javax.xml.ws.Service;
//
///**
// *
// * @author Henrik
// */
//@WebService 
//public class GalgeImpl extends UnicastRemoteObject implements GalgelegI {
//    
//    private Galgelogik logik;
//    private Brugeradmin BI;
//    
//    public GalgeImpl() throws java.rmi.RemoteException {
//            
//        logik = new Galgelogik();
//        
//        try {
//            logik.hentOrdFraDr();
//            System.out.println("Hentede succesfuldt ord fra dr.dk's hjemmeside");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Mislykkedes med at hente ord fra dr.dk - anvender standard udvalg");
//        }
//        
//        try {
//            //BI = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
//            	URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
//		QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
//		Service service = Service.create(url, qname);
//		Brugeradmin BI = service.getPort(Brugeradmin.class);
//                
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }  
//    
//    @Override
//    public String getSynligtOrd() {
//        return logik.getSynligtOrd();
//    }
//    
//    @Override
//    public void gætBogstav(String ord) {
//        logik.gætBogstav(ord);
//    }
//    
//    @Override
//    public String logStatus() {
//        
//        String str = "";
//        
//        str += "---------- \n";
//        //str += "Ordet (skjult) = " + logik.getOrdet() + "\n";
//        str += "Synligt Ord = " + logik.getSynligtOrd() + "\n";
//        str += "Antal forkerte bogstaver = " + logik.getAntalForkerteBogstaver() + "\n";
//        str += "Brugte Bogstaver = " + logik.getBrugteBogstaver() + "\n";
//        if (logik.erSpilletTabt())
//            str += "SPILLET ER TABT! - Ordet var " + logik.getOrdet() + "\n";
//        if (logik.erSpilletVundet())
//            str += "SPILLET ER VUNDET!\n";
//        str += "---------- ";
//        
//        return str;
//    }
//    
//    @Override
//    public boolean erSpilletSlut() {
//        return logik.erSpilletSlut();
//    }
//    
//    @Override
//    public void nulstil() {
//        logik.nulstil();
//    }
//    
//    @Override
//    public String getOrdet() {
//        return logik.getOrdet();
//    }
//    
//    @Override
//    public boolean hentBruger(String brugernavn, String password) {
//        
//        try {
//            Bruger b = BI.hentBruger(brugernavn, password);
//            System.out.println("GalgelegImpl.java : Objekt modtaget");
//            return true;
//        } catch (IllegalArgumentException e) {
//            System.out.println("GalgelegImpl.java : IllegalArgumentException");
//            e.printStackTrace();
//            return false;
//        } catch (Exception p) {
//            System.out.println("GalgelegImpl.java : Exception");
//            p.printStackTrace();
//        }
//        
//        return false;
//    }
//
//    @Override
//    public ArrayList<String> getBrugteBogstaver() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//
//    @Override
//    public int getAntalForkerteBogstaver() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean erSidsteBogstavKorrekt() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean erSpilletVundet() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean erSpilletTabt() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void opdaterSynligtOrd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//}
