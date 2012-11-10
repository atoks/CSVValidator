import java.io.File;

import CSVValidator.CSVReader;
import CSVValidator.CSVRules;
import CSVValidator.CSVRulesReader;
import CSVValidator.CSVTable;
import CSVValidator.CSVValidator;
import CSVValidator.Exceptions.CSVRuleException;

/**
 * Beispiel zeigt wie CSV-Datei mit Hilfe von CSVValidator validiert werden kann.
 * !!!!!! ACHTUNG !!!!!!
 * CSVValidator ist noch nicht vollständig ausprogrammiert. Es werden nur fatale Fehler behandelt. Ales anderes zurzeit wird als valid bezeichnet.
 * Alle Prüfungen, um weitere Fehlertypen rauszufinden, sind vorhanden aber keine entsprechende Routine dafür ausprogrammiert ist. 
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */

public class ValidatorRun {

	/**
	 * 
	 * @author Aleksej Tokarev
	 * http://www.coding-contest.de/index.php?id=537
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		
		// Instantisieren von CSV- Leser
		// CSV- Inhalt wird aus einer Datei entnommen.
		CSVReader v = new CSVReader(new File("db.csv"));
		// Inhaltrenner definieren.
		v.setDelimiter(";");
		
		// CSV-Tabel aus Datei rauslesen.
		CSVTable table = v.parseToTable();
		
		// CSV- Leser schlissen.
		v.close();
		
		
		CSVValidator validator = new CSVValidator(		// Initialisieren von CSV Validator
				new CSVRules(							// Übergabe von CSV Reheln
						new CSVRulesReader(				// Übergabe von CSV Regelleser
								new File("Rule.xml"))));// Übergabe von Datei mit Regeln
		
		
		// validieren von CSV- Tabelle.
		try {
			validator.validateTable(table);
			System.out.println("CSV-Datei wurde erfolgreich validiert.");
		} catch (CSVRuleException e) {
			System.out.println("UPS: CSV-Datei konnte nicht validiert werden.");
			e.printStackTrace();
		}
		
		// Tabelleninhalt ausgeben.
		System.out.println(table.toString());
		
	}

}
