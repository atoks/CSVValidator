package CSVValidator.Rules;
/**
 * Diese Hilfsklasse beinhaltet n&ouml;tige Information, um Regel f&uuml;r Tabellenkopf zu speichern.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class HeadRule {
	private int headId;
	/**
	 * Konstruktor.
	 * Standardm&auml;&szlig;ig Tabellenkopfindex ist auf -1 gesetzt. Also Tabelle hat kein Kopf.
	 */
	public HeadRule() {
		this.headId = -1; // Ohne Head
	}
	/**
	 * Methode liefert Tabellenkopfindex.
	 * @return Tabellenkopfindex oder -1 (Tabelle hat kein Kopf)
	 */
	public int getHeadId() {
		return headId;
	}
	/**
	 * Methode bestimmt Tabellenkopfindex </br>
	 * Standardm&auml;&szlig;ig Tabellenkopfindex ist auf -1 gesetzt. Also Tabelle hat kein Kopf.
	 * @param headId Tabellenkopfindex
	 */
	public void setHeadId(int headId) {
		this.headId = headId;
	}

	@Override
	public String toString() {
		return "HeadRule [headId=" + headId + "]";
	}
	
}
