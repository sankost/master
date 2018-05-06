package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.Podaci;
import domen.Rezervacija;
import domen.Soba;
import domen.Uplata;
import domen.Rezervacija.TipPlacanja;
import komunikacija.TransferniObjekat;

public class Komunikacija extends Thread {

	Socket soket;
	ObjectInputStream in;
	ObjectOutputStream out;
	Server server;
	boolean kraj = false;

	public Komunikacija(Socket soket, Server server) {
		this.soket = soket;
		this.server = server;
		try {
			in = new ObjectInputStream(soket.getInputStream());
			out = new ObjectOutputStream(soket.getOutputStream());
		} catch (IOException ex) {
			Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void run() {
		TransferniObjekat o = null;
		while (!interrupted()) {
			try {
				o = (TransferniObjekat) in.readObject();
			} catch (IOException | ClassNotFoundException ex) {
				Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (o != null && o.getOperacija().equals("provera")) {
				o.setRezultat(proveriRezervaciju((Rezervacija) o.getParametar()));
			}
			if (o != null && o.getOperacija().equals("rezervacija")) {
				o.setRezultat(rezervisi((Rezervacija) o.getParametar()));
			}
			if (o != null && o.getOperacija().equals("uplata")) {
				o.setRezultat(dodajUplatu((Uplata) o.getParametar()));
			}
			if (o != null && o.getOperacija().equals("kraj")) {
				try {
					in.close();
					out.close();
					soket.close();
					return;
				} catch (Exception ex) {
					System.out.println("Greska");
				}
			}
			try {
				out.writeObject(o);
			} catch (IOException ex) {
				Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private Rezervacija dodajUplatu(Uplata uplata) {
		Rezervacija rezervacija = uplata.getRezervacija();
		rezervacija.dodajUplatu(uplata);
		double ukupnoUplaceno = 0;
		for (Uplata u : rezervacija.getListaUplata()) {
			ukupnoUplaceno += u.getIznos();
		}
		if (rezervacija.getListaUplata().size() == rezervacija.getBrojRata()
				&& ukupnoUplaceno != rezervacija.getCena()) {
			rezervacija.setStatus("nepotpuna");
			rezervacija.getListaUplata().remove(uplata);
		} else {
			if (ukupnoUplaceno < rezervacija.getCena()) {
				rezervacija.setStatus("delimicno uplacena");
			} else {
				rezervacija.setStatus("uplacena");
			}

			for (Rezervacija rez : Podaci.listaRezervacija) {
				if (rez.getId() == rezervacija.getId()) {
					rez = rezervacija;
				}
			}
		}
		return rezervacija;
	}

	@SuppressWarnings("deprecation")
	public Rezervacija proveriRezervaciju(Rezervacija rez) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rez.getDatumOd());

		ArrayList<Soba> odgovarajuceSobe = new ArrayList<>();
		for (Soba s : Podaci.listaSoba) {
			if (s.getBrojMesta() == rez.getBrOsoba()) {
				odgovarajuceSobe.add(s);
			}
		}
		for (Rezervacija r : Podaci.listaRezervacija) {
			if (r.getBrOsoba() == rez.getBrOsoba()) {
				if (rez.getDatumOd().before(r.getDatumDo())
						&& (rez.getDatumOd().after(r.getDatumOd()) || rez.getDatumOd().equals(r.getDatumOd()))
						|| (rez.getDatumDo().before(r.getDatumDo()) && (rez.getDatumDo().after(r.getDatumOd())))
						|| (rez.getDatumOd().before(r.getDatumOd()) && (rez.getDatumDo().after(r.getDatumDo())))) {
					odgovarajuceSobe.remove(r.getSoba());
				}
			}
		}
		if (odgovarajuceSobe.size() > 0) {
			rez.setSoba(odgovarajuceSobe.get(0));
			rez.setStatus("odobrena");

			int cena = 0;
			Date datum = rez.getDatumOd();
			while (datum.before(rez.getDatumDo())) {
				if (datum.getDay() == 0 || datum.getDay() == 6) {
					cena += 2000;
				} else {
					cena += 1000;
				}
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				datum = new Date(calendar.getTimeInMillis());
			}
			if (rez.getTipPlacanja() == TipPlacanja.na_rate) {
				cena += cena * rez.getBrojRata() * 0.1;
			}
			rez.setCena(cena * rez.getBrOsoba());
		} else {
			rez.setStatus("odbijena");
		}

		return rez;

	}

	public Rezervacija rezervisi(Rezervacija rez) {
		if (rez.getStatus().equals("odobrena")) {
			rez.setId(Podaci.idRezervacije);
			if (rez.getTipPlacanja() == TipPlacanja.gotovinsko) {
				rez.inicijalizujListu(1);
			} else {
				rez.inicijalizujListu(rez.getBrojRata());
			}
			Podaci.dodajRezervaciju(rez);
			for (Rezervacija r : Podaci.listaRezervacija) {
				System.out.println(r);
			}
			rez.setStatus("rezervisana");
		}
		return rez;
	}
}
