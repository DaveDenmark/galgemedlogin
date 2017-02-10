/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package galgeleg;

import java.rmi.RemoteException;
import javax.xml.ws.Endpoint;

/**
 *
 * @author IamHexor
 */
public class Server {
		public static void main(String[] args) throws RemoteException {
		System.out.println("publicerer Galgelegtjeneste");
		Galgelogik g = new Galgelogik();
    // Ipv6-addressen [::] svarer til Ipv4-adressen 0.0.0.0, der matcher alle maskinens netkort og 
		Endpoint.publish("http://[::]:18371/galgelegtjeneste", g);
		System.out.println("Galgelegtjeneste publiceret.");
	}
}
