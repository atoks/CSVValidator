package CSVValidator;

import java.util.Vector;
/**
 * Diese Klasse repr&auml;sentiert Zeile mit CSV-Inhalt f&uuml;r CSVTable.</br>
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVRow extends Vector<String>{
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "CSVRow: " + super.toString() ;
	}
}
