package domen;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Rezervacija implements Serializable {

	private static final long serialVersionUID = 1L;
	int id;
	String status;
	Date datumOd;
	Date datumDo;
	Soba soba;
	int brOsoba;
	double cena;
	TipPlacanja tipPlacanja;
	ArrayList<Uplata> listaUplata;

	public Rezervacija(String status, Date datumOd, Date datumDo, Soba soba, int brOsoba, double cena) {
		this.status = status;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.soba = soba;
		this.brOsoba = brOsoba;
		this.cena = cena;
	}
	
	public enum TipPlacanja{
		gotovinsko,
		na_rate
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}

	public Date getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

	public Soba getSoba() {
		return soba;
	}

	public void setSoba(Soba soba) {
		this.soba = soba;
	}

	public int getBrOsoba() {
		return brOsoba;
	}

	public void setBrOsoba(int brOsoba) {
		this.brOsoba = brOsoba;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public void dodajUplatu(Uplata uplata){
		listaUplata.add(uplata);
		System.out.println("rezervacija class- dodavanje uplate , size"+listaUplata.size());
	}
	
	public void inicijalizujListu(int brojUplata){
		listaUplata = new ArrayList<Uplata>(brojUplata);
	}

	public ArrayList<Uplata> getListaUplata() {
		return listaUplata;
	}
	
	
	public TipPlacanja getTipPlacanja() {
		return tipPlacanja;
	}

	public void setTipPlacanja(TipPlacanja tipPlacanja) {
		this.tipPlacanja = tipPlacanja;
	}

	@Override
	public String toString() {
		return "Rezervacija: id: "+ getId() +" Od - " + getDatumOd() + " Do - " + getDatumDo() + " Soba broj: " + getSoba() + " cena: "+getCena();
	}

}
