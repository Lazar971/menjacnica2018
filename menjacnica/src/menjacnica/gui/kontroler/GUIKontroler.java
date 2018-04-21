package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	public static MenjacnicaGUI glavni;
	public static Menjacnica sistem=new Menjacnica();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					glavni = new MenjacnicaGUI();
					glavni.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(glavni,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(glavni,
				"Autor: Lazar Milosavljevic, Verzija 2.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(glavni);
		prozor.setVisible(true);
	}
	
	
	public static void prikaziObrisiKursGUI() {
		
		if (glavni.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(glavni.getTable().getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(model.vratiValutu(glavni.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(glavni);
			prozor.setVisible(true);
		}
	}
	public static void prikaziIzvrsiZamenuGUI() {
		if (glavni.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(glavni.getTable().getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(glavni,
					model.vratiValutu(glavni.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(glavni);
			prozor.setVisible(true);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavni);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				glavni.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavni, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavni);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavni, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void unesiKurs(String naziv,String skraceniNaziv,int sifra,double prodajni,double srednji,double kupovni) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			// Dodavanje valute u kursnu listu
			sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			glavni.prikaziSveValute();
			
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavni, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}
	public static void obrisiValutu(Valuta valuta) {
		try{
			sistem.obrisiValutu(valuta);
			
			glavni.prikaziSveValute();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavni, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static double izvrsiZamenu(Valuta valuta,boolean prodaja,double iznos){
		try{
			return   sistem.izvrsiTransakciju(valuta,prodaja, iznos);
			
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavni, e1.getMessage(),"Greska", JOptionPane.ERROR_MESSAGE);
			return 0;
			
		}
	}
}
