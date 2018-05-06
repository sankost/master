package data;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import domen.Rezervacija;
import domen.Soba;

public class Podaci {

	public static ArrayList<Rezervacija> listaRezervacija;
	public static ArrayList<Soba> listaSoba;
	public static int idRezervacije;
	
	static {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		listaRezervacija = new ArrayList<>();
		listaSoba = new ArrayList<>();
		idRezervacije = 0;
		
		Soba s1 = new Soba(1, "101A", 1);
		listaSoba.add(s1);
		Soba s2 = new Soba(2, "102A", 1);
		listaSoba.add(s2);
		Soba s3 = new Soba(3, "201B", 2);
		listaSoba.add(s3);
		Soba s4 = new Soba(4, "202B", 2);
		listaSoba.add(s4);
		Soba s5 = new Soba(5, "300C", 3);
		listaSoba.add(s5);
		
		try {
			listaRezervacija.add(new Rezervacija("placena", new Date(sdf.parse("1.1.2018").getTime()), new Date(sdf.parse("10.1.2018").getTime()), s5, 3, 5000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void dodajRezervaciju(Rezervacija rezervacija){
		idRezervacije ++;
		listaRezervacija.add(rezervacija);
	}
}
