package domen;

import java.io.Serializable;
import java.sql.Date;

public class Uplata implements Serializable{

	private static final long serialVersionUID = 1L;
	double iznos;
	Date datumUplate;
	Rezervacija rezervacija;
	
	public Uplata(double iznos, Date datumUplate) {
		this.iznos = iznos;
		this.datumUplate = datumUplate;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}

	public Date getDatumUplate() {
		return datumUplate;
	}

	public void setDatumUplate(Date datumUplate) {
		this.datumUplate = datumUplate;
	}

	public Rezervacija getRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(Rezervacija rezervacija) {
		this.rezervacija = rezervacija;
	}
	
	
}
