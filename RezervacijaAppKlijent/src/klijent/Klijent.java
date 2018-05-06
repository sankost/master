package klijent;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Klijent extends Thread {

    Socket socket;
    int port;
    
    public Klijent(int port) {
    	this.port = port;
	}
    
    @Override
    public void run() {
        try {
            socket = new Socket("localhost", port);
            Komunikacija.getInstance().setSocket(socket);
            System.out.println("Klijent je povezan");
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);

        } 
//      forma.obavesti(moze);
    } 
    
    
}
