package klijent;

import java.io.IOException;
import domen.Rezervacija;
import komunikacija.TransferniObjekat;

public class Razmena {

	private static Razmena instanca;
	
	public static Razmena vratiInstancu() {
        if (instanca == null) {
            instanca = new Razmena();
        }
        return instanca;
    }
	
	public Rezervacija proveriRezervaciju(Rezervacija rezervacija) throws IOException, ClassNotFoundException, Exception {
        Rezervacija rez = null;
        TransferniObjekat to = new TransferniObjekat();
        to.setOperacija("provera");
        to.setParametar(rezervacija);
        Komunikacija.getInstance().posaljiZahtev(to);
        to = (TransferniObjekat) Komunikacija.getInstance().primiOdgovor();
        if (to.getIzuzetak() != null) {
            to.getIzuzetak().printStackTrace();
            throw to.getIzuzetak();
        }
        rez = (Rezervacija) to.getRezultat();
        return rez;
    }
	
	public Rezervacija rezervisi(Rezervacija rezervacija) throws IOException, ClassNotFoundException, Exception {
        Rezervacija rez = null;
        TransferniObjekat to = new TransferniObjekat();
        to.setOperacija("rezervacija");
        to.setParametar(rezervacija);
        Komunikacija.getInstance().posaljiZahtev(to);
        to = (TransferniObjekat) Komunikacija.getInstance().primiOdgovor();
        if (to.getIzuzetak() != null) {
            to.getIzuzetak().printStackTrace();
            throw to.getIzuzetak();
        }
        rez = (Rezervacija) to.getRezultat();
        return rez;
    }
	
	public void odjaviKorisnika() throws IOException, ClassNotFoundException, Exception {
        TransferniObjekat to = new TransferniObjekat();
        to.setOperacija("kraj");
        Komunikacija.getInstance().posaljiZahtev(to);
        Komunikacija.getInstance().zatvoriTokove();
    }
}
