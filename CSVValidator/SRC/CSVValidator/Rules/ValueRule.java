package CSVValidator.Rules;

import CSVValidator.CellInfo;

/**
 * Diese Hilfsklasse beinhaltet n&ouml;tige Information mit Regel die dienen zum Vergleich von 2-bestimmten K&auml;stchen.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class ValueRule {
	private CellInfo c1;
	private CellInfo c2;
	private String error;
	private String compareRule;
	/**
	 * Konstruktor. </br>
	 * Standardm&auml;&szlig;ig: Vergleichsregel ist "eq" und Fehlertype ist "valid".
	 */
	public ValueRule(){
		compareRule = "eq"; // lt,gt,eq,ne,le,ge
		error		= "valid"; // 'fatal', 'error', 'warning', 'valid'
	}
	/**
	 * Methode liefert Information &uuml;ber erste K&auml;stchen
	 * @return K&auml;stcheninformation
	 */
	public CellInfo getCell1() {
		return c1;
	}
	/**
	 * Methode setzt K&auml;stcheninformation f&uuml;r erste K&auml;stchen
	 * @param c1 K&auml;stcheninformation
	 */
	public void setCell1(CellInfo c1) {
		this.c1 = c1;
	}
	/**
	 * Methode liefert Information &uuml;ber zweite K&auml;stchen
	 * @return K&auml;stcheninformation
	 */
	public CellInfo getCell2() {
		return c2;
	}
	/**
	 * Methode setzt K&auml;stcheninformation f&uuml;r zweite K&auml;stchen
	 * @param c2 K&auml;stcheninformation
	 */
	public void setCell2(CellInfo c2) {
		this.c2 = c2;
	}
	/**
	 * Methode leifert Fehlertyp zur&uuml;ck. 
	 * @return "fatal", "error", "warning", "valid"
	 */
	public String getError() {
		return error;
	}
	/**
	 * Methode setzt Fehlertype.
	 * @param error "fatal", "error", "warning", "valid"
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * Methode liefert Vergleichsregel zur&uuml;ck.
	 * @return
	 * "lt" = kleiner als </br>
	 * "gt" = gr&ouml;&szlig;er als </br>
	 * "eq" = gleich </br>
	 * "ne" = ungleich </br>
	 * "le" = kleiner gleich </br>
	 * "ge" = gr&ouml;&szlig;er gleich
	 */
	public String getCompareRule() {
		return compareRule;
	}
	/**
	 * Methode setzt Vergleichsregel.</br>
	 * "lt" = kleiner als </br>
	 * "gt" = gr&ouml;&szlig;er als </br>
	 * "eq" = gleich </br>
	 * "ne" = ungleich </br>
	 * "le" = kleiner gleich </br>
	 * "ge" = gr&ouml;&szlig;er gleich
	 * @param compareRule Vergleichsregel (Standardm&auml;&szlig;ig "eq")
	 */
	public void setCompareRule(String compareRule) {
		this.compareRule = compareRule;
	}

	@Override
	public String toString() {
		return "ValueRule [c1=" + c1 + ", c2=" + c2 + ", error=" + error
				+ ", compareRule=" + compareRule + "]";
	}
	
	
}
