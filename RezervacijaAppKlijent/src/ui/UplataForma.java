package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domen.Uplata;
import klijent.Razmena;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class UplataForma extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldDatum;
	private JTextField textFieldIznos;

	/**
	 * Create the dialog.
	 */
	public UplataForma(GlavnaForma glavnaForma) {
		setTitle("Unesite uplatu");
		setBounds(100, 100, 245, 226);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("Uplatite svoju rezervaciju:");
			label.setBounds(10, 11, 197, 14);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Datum:");
			label.setBounds(31, 53, 46, 14);
			contentPanel.add(label);
		}
		{
			textFieldDatum = new JTextField();
			textFieldDatum.setColumns(10);
			textFieldDatum.setBounds(87, 50, 102, 20);
			contentPanel.add(textFieldDatum);
		}
		{
			JLabel label = new JLabel("Iznos:");
			label.setBounds(31, 84, 46, 14);
			contentPanel.add(label);
		}
		{
			textFieldIznos = new JTextField();
			textFieldIznos.setColumns(10);
			textFieldIznos.setBounds(87, 81, 102, 20);
			contentPanel.add(textFieldIznos);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 135, 229, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnSacuvaj = new JButton("Sacuvaj");
				btnSacuvaj.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						Date datumUplate;
						try {
							datumUplate = new Date(sdf.parse(textFieldDatum.getText()).getTime());
						} catch (ParseException ex) {
							System.out.println("Greska pri unosu datuma " + ex.getMessage());
							JOptionPane.showMessageDialog(null, "Morate uneti datum u formatu dd.MM.yyyy!",
									"Greska pri unosu datuma", JOptionPane.ERROR_MESSAGE);
							return;
						}
						Double iznos = Double.parseDouble(textFieldIznos.getText());
						Uplata uplata = new Uplata(iznos, datumUplate);
						uplata.setRezervacija(glavnaForma.rezervacija);
						try {
							glavnaForma.rezervacija = Razmena.vratiInstancu().dodajUplatnicu(uplata);
							if(glavnaForma.rezervacija.getStatus().equals("nepotpuna")){
								System.out.println("Greska pri unosu iznosa ");
								JOptionPane.showMessageDialog(null, "Morate uneti celokupan preostali iznos!",
										"Greska pri unosu iznosa uplate", JOptionPane.ERROR_MESSAGE);
								return;
							}
						} catch (Exception ex) {
							System.out.println("Exception");
						}
						System.out.println("Uplata je dodata");
						glavnaForma.azuririajPerostaliIznos();
						setVisible(false);
					}
				});
				btnSacuvaj.setActionCommand("OK");
				buttonPane.add(btnSacuvaj);
				getRootPane().setDefaultButton(btnSacuvaj);
			}
			{
				JButton btnOdustani = new JButton("Odustani");
				btnOdustani.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				btnOdustani.setActionCommand("Cancel");
				buttonPane.add(btnOdustani);
			}
		}
	}

}
