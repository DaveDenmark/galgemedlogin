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
		public static void main(String[] args) throws Exception {
		System.out.println("publicerer Galgelegtjeneste");
		Galgelogik g = new Galgelogik();
		Endpoint.publish("http://[::]:18371/galgeservice", g);
		System.out.println("Galgelegtjeneste publiceret.");
	}
}
