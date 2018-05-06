package domen;

import java.io.Serializable;

public class Soba implements Serializable {

	private static final long serialVersionUID = 1L;
	int sobaId;
	String brSobe;
	int brojMesta;

	public Soba(int sobaId, String brSobe, int brojMesta) {
		this.sobaId = sobaId;
		this.brSobe = brSobe;
		this.brojMesta = brojMesta;
	}

	public int getSobaId() {
		return sobaId;
	}

	public void setSobaId(int sobaId) {
		this.sobaId = sobaId;
	}

	public String getBrSobe() {
		return brSobe;
	}

	public void setBrSobe(String brSobe) {
		this.brSobe = brSobe;
	}

	public int getBrojMesta() {
		return brojMesta;
	}

	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}

	@Override
	public String toString() {
		return getBrSobe();
	}
}
