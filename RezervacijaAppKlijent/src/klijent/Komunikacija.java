package klijent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Komunikacija {

	
	private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    private static Komunikacija instance;

    private Komunikacija() {

    }

    public static Komunikacija getInstance() {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }
    
    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    public void posaljiZahtev(Object o) throws IOException {
        out.writeObject(o);
    }

    public Object primiOdgovor() throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        return o;
    }
    
    public void zatvoriTokove() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
