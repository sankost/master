package komunikacija;

import java.io.Serializable;

public class TransferniObjekat implements Serializable{

	private String operacija;
    private Object parametar;
    private Object rezultat;
    private Exception izuzetak;

    public TransferniObjekat() {
    }
    
    public TransferniObjekat(String operacija, Object parametar, Object rezultat, Exception izuzetak) {
        this.operacija = operacija;
        this.parametar = parametar;
        this.rezultat = rezultat;
        this.izuzetak = izuzetak;
    }

    public String getOperacija() {
        return operacija;
    }

    public void setOperacija(String operacija) {
        this.operacija = operacija;
    }

    public Object getParametar() {
        return parametar;
    }

    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }

    public Object getRezultat() {
        return rezultat;
    }

    public void setRezultat(Object rezultat) {
        this.rezultat = rezultat;
    }

    public Exception getIzuzetak() {
        return izuzetak;
    }

    public void setIzuzetak(Exception izuzetak) {
        this.izuzetak = izuzetak;
    }
}
