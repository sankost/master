package ui;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import domen.Rezervacija;
import domen.Rezervacija.TipPlacanja;
import domen.Uplata;
import klijent.Klijent;
import klijent.Razmena;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class GlavnaForma extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static GlavnaForma frame;
	private JLayeredPane contentPane;
	private JTextField textField_datumOd;
	private JTextField textField_datumDo;
	private JTextField textField_brojOsoba;
	private JTextField textField_status;
	private JTextField textField_cena;
	private JButton btnPlati;
	public Rezervacija rezervacija;
	private JTextField textField_soba;
	private JTextField textField_uplata;
	public JComboBox<Integer> comboBoxBrojRata;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GlavnaForma();
					frame.setVisible(true);
					pokreniKlijenta();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected static void pokreniKlijenta() {
		Klijent k = new Klijent(1234);
		k.start();
	}

	/**
	 * Create the frame.
	 */
	public GlavnaForma() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				try {
					formWindowClosing(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

		setBounds(100, 100, 430, 600);
		contentPane = new JLayeredPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Dobro dosili na sistem za rezervaciju soba!");

		JLabel lblDatumOd = new JLabel("Datum od:");

		textField_datumOd = new JTextField();
		textField_datumOd.setColumns(10);

		JLabel lblDatumDo = new JLabel("Datum do:");

		textField_datumDo = new JTextField();
		textField_datumDo.setColumns(10);

		JLabel lblBrojOsoba = new JLabel("Broj osoba:");

		textField_brojOsoba = new JTextField();
		textField_brojOsoba.setColumns(10);

		JButton btnProveri = new JButton("Proveri");
		JButton btnRezervisi = new JButton("Rezervisi");
		btnRezervisi.setEnabled(false);
		btnPlati = new JButton("Plati");
		btnPlati.setEnabled(false);
		
		JLabel lblRezervisanaSoba = new JLabel("Slobodna soba:");
		
		textField_soba = new JTextField();
		textField_soba.setEditable(false);
		textField_soba.setColumns(10);
		
		JLabel lblUnesitePodatkeZa = new JLabel("Unesite podatke za rezrevaciju soba");
		
		ButtonGroup group = new ButtonGroup();
		JRadioButton rdbtnGotovinskoPlacanje = new JRadioButton("Gotovinsko placanje");
		group.add(rdbtnGotovinskoPlacanje);
		rdbtnGotovinskoPlacanje.setSelected(true);
		
		JRadioButton rdbtnPlacanjeNaRate = new JRadioButton("Placanje na rate");
		group.add(rdbtnPlacanjeNaRate);
		
		JLabel lblBrojRata = new JLabel("Broj rata:");
		
		comboBoxBrojRata = new JComboBox<Integer>();
		comboBoxBrojRata.addItem(2);
		comboBoxBrojRata.addItem(3);
		comboBoxBrojRata.addItem(4);
		
		JLabel lblPreostaloZaUplatu = new JLabel("Preostalo za uplatu:");
		
		textField_uplata = new JTextField();
		textField_uplata.setColumns(10);
		textField_uplata.setEditable(false);
		
		btnProveri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				Date vreme1;
				Date vreme2;
				try {
					vreme1 = new Date(sdf.parse(textField_datumOd.getText()).getTime());
					vreme2 = new Date(sdf.parse(textField_datumDo.getText()).getTime());
				} catch (ParseException ex) {
					System.out.println("Greska pri unosu datuma " + ex.getMessage());
					JOptionPane.showMessageDialog(frame, "Morate uneti datum u formatu dd.MM.yyyy!",
							"Greska pri unosu datuma", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int brOsoba = Integer.parseInt(textField_brojOsoba.getText());
				textField_soba.setText("");
				rezervacija = new Rezervacija("nova", vreme1, vreme2, null, brOsoba, 0);
				System.out.println("Provera rezervacije na klijentu. Status: "+rezervacija.getStatus());
				try {
					rezervacija = Razmena.vratiInstancu().proveriRezervaciju(rezervacija);
						textField_status.setText(rezervacija.getStatus());	
						textField_cena.setText(rezervacija.getCena()+"");	
						if(rezervacija.getStatus().equals("odobrena")){
							textField_soba.setText(rezervacija.getSoba()+"");
							btnRezervisi.setEnabled(true);
						}
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				
			}
		});

		JLabel lblStatus = new JLabel("Status Vase trenutne rezervacije:");

		textField_status = new JTextField();
		textField_status.setColumns(10);
		textField_status.setEditable(false);
		textField_status.setText("nova");

		JLabel lblCena = new JLabel("Cena:");

		textField_cena = new JTextField();
		textField_cena.setColumns(10);
		textField_cena.setEditable(false);


		btnRezervisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					rezervacija.setCena(Double.parseDouble(textField_cena.getText()));
					if(rdbtnPlacanjeNaRate.isSelected()){
						int rata = Integer.parseInt(comboBoxBrojRata.getSelectedItem().toString());
						rezervacija.inicijalizujListu(rata);
						rezervacija.setTipPlacanja(TipPlacanja.na_rate);
					}else{
						rezervacija.inicijalizujListu(1);
						rezervacija.setTipPlacanja(TipPlacanja.gotovinsko);
					}
					rezervacija = Razmena.vratiInstancu().rezervisi(rezervacija);
					textField_status.setText(rezervacija.getStatus());	
					textField_cena.setText(rezervacija.getCena()+"");
					if(rezervacija.getStatus().equals("rezervisana")){
						btnPlati.setEnabled(true);
						btnProveri.setEnabled(false);
						btnRezervisi.setEnabled(false);
						comboBoxBrojRata.setEnabled(false);
						rdbtnGotovinskoPlacanje.setEnabled(false);
						rdbtnPlacanjeNaRate.setEnabled(false);
						textField_uplata.setText(rezervacija.getCena()+"");
						btnPlati.setEnabled(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		
		btnPlati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnGotovinskoPlacanje.isSelected()){
					Date date = new Date(Calendar.getInstance().getTime().getTime());
					Uplata uplata = new Uplata(rezervacija.getCena(), date);
					rezervacija.dodajUplatu(uplata);
					azuririajPerostaliIznos();
				}else{
				 UplataForma dialog = new UplataForma(frame);
				 dialog.setModal(true);
				 dialog.setVisible(true);
				}
			}
		});
		
		
		JLabel lblStatus_1 = new JLabel("Status");
		
		rdbtnPlacanjeNaRate.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	comboBoxBrojRata.setEnabled(true);
	        	int rata = Integer.parseInt(comboBoxBrojRata.getSelectedItem().toString());
	        	double cena = rezervacija.getCena();
	        	cena += cena * rata * 0.1;
	        	textField_cena.setText(cena + "");
	        }
	    });
		
		rdbtnGotovinskoPlacanje.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	comboBoxBrojRata.setEnabled(false);
	        	textField_cena.setText(rezervacija.getCena() + "");
	       }
	    }); 
		
		comboBoxBrojRata.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	rdbtnPlacanjeNaRate.doClick();;
	       }
	    }); 
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblUnesitePodatkeZa)
					.addContainerGap(263, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblStatus_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(213, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCena)
						.addComponent(lblRezervisanaSoba))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textField_soba, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
							.addGap(179))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textField_cena, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(21)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(113)
											.addComponent(btnProveri))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblDatumOd)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(textField_datumOd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(26)
											.addComponent(lblDatumDo, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblNewLabel)))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(21)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnPlacanjeNaRate)
									.addGap(18)
									.addComponent(lblBrojRata)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(comboBoxBrojRata, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(rdbtnGotovinskoPlacanje)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblBrojOsoba)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_brojOsoba, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblPreostaloZaUplatu)
									.addGap(18)
									.addComponent(textField_uplata, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(26)))
					.addComponent(textField_datumDo, 81, 81, 81)
					.addGap(114))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(101)
					.addComponent(btnRezervisi)
					.addGap(18)
					.addComponent(btnPlati, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(180, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(lblStatus)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatus_1)
						.addComponent(textField_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblUnesitePodatkeZa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDatumDo)
						.addComponent(textField_datumDo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDatumOd)
						.addComponent(textField_datumOd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_brojOsoba, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBrojOsoba))
					.addGap(18)
					.addComponent(btnProveri)
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRezervisanaSoba)
						.addComponent(textField_soba, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCena)
						.addComponent(textField_cena, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(37)
					.addComponent(rdbtnGotovinskoPlacanje)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnPlacanjeNaRate)
						.addComponent(lblBrojRata)
						.addComponent(comboBoxBrojRata, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRezervisi)
						.addComponent(btnPlati))
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPreostaloZaUplatu)
						.addComponent(textField_uplata, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(66))
		);
		contentPane.setLayout(gl_contentPane);
		
	}

	private void formWindowClosing(java.awt.event.WindowEvent evt) throws ClassNotFoundException, Exception {// GEN-FIRST:event_formWindowClosing
		// TODO add your handling code here:
		try {
			Razmena.vratiInstancu().odjaviKorisnika();
			this.setVisible(false);
		} catch (IOException ex) {
			Logger.getLogger(GlavnaForma.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void azuririajPerostaliIznos() {
		double uplaceno = 0;
		for (Uplata u : rezervacija.getListaUplata()){
			uplaceno += u.getIznos();
		}
		textField_uplata.setText(rezervacija.getCena() - uplaceno + "");
		if(Double.parseDouble(textField_uplata.getText()) == 0){
			btnPlati.setEnabled(false);
			JOptionPane.showMessageDialog(this, "Hvala Vam sto ste uplatili Vasu rezervaciju", "Uplata izvrsena", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
 
    

    
}
