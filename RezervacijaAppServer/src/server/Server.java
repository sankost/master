package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import data.Podaci;
import domen.Rezervacija;
import domen.Soba;

public class Server extends Thread {

	int port;
	ServerSocket ss;
	boolean kraj = false;

	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);
			System.out.println("Server je pokrenut i ceka klijenta!");
			while (!kraj) {
				Socket soket = ss.accept();
				System.out.println("Klijent se povezao sa serverom!");
				RezervacijaServis kom = new RezervacijaServis(soket, this);
				kom.start();
			}
		} catch (IOException ex) {
			System.out.println("Server je zaustavljen!");
		}
	}

	public static void main(String[] args) {
		Server s = new Server(1234);
		s.start();
	}
}
