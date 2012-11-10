package CSVValidator.Rules;
/**
 * Diese Hilfsklasse beinhaltet n&ouml;tige Information, um Regel f&uuml;r Tabellengr&ouml;&szlig;e zu speichern.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class TableSizeRule {
	private int maxColumnCount;
	private int minColumnCount;
	private int maxRowCount;
	private int minRowCount;
	private String errorColumn;
	private String errorRow;
	/**
	 * Konstruktor.</br>
	 * Regel werden alle auf maximale Freiheit gesetzt.
	 */
	public TableSizeRule() {
		this.maxColumnCount	= 0;
		this.minColumnCount	= 0;
		this.maxRowCount	= 0;
		this.minRowCount	= 0;
		this.errorColumn	= "valid"; // 'fatal', 'error', 'warning', 'valid'
		this.errorRow		= "valid"; // 'fatal', 'error', 'warning', 'valid'
	}
	/**
	 * Methode setzt Fehlertyp f&uuml;r Spaltengr&ouml;&szlig;e.
	 * @param errorColumn "fatal", "error", "warning", "valid"
	 */
	public void setColumnError(String errorColumn){
		this.errorColumn = errorColumn;
	}
	/**
	 * Methode liefert Fehlertyp f&uuml;r Spaltengr&ouml;&szlig;e.
	 * @return "fatal", "error", "warning", "valid"
	 */
	public String getColumnError(){
		return errorColumn;
	}
	/**
	 * Methode liefert Fehlertyp f&uuml;r Zeilengr&ouml;&szlig;e.
	 * @return "fatal", "error", "warning", "valid"
	 */
	public String getRowError(){
		return errorRow;
	}
	/**
	 * Methode setzt Fehlertyp f&uuml;r Zeilengr&ouml;&szlig;e.
	 * @param errorRow "fatal", "error", "warning", "valid"
	 */
	public void setRowError(String errorRow){
		this.errorRow = errorRow;
	}
	/**
	 * Methode liefert maximale Spaltenanzahl zur&uuml;ck. Standardm&auml;&szlig;ig ist 0 (unbegrenzt)
	 * @return maximale Spaltenanzahl
	 */
	public int getMaxColumnCount() {
		return maxColumnCount;
	}
	/**
	 * Methode setzt maximale Spaltenanzahl. 0 = (unbegrenzt)
	 * @param maxColumnCount maximale Spaltenanzahl
	 */
	public void setMaxColumnCount(int maxColumnCount) {
		this.maxColumnCount = maxColumnCount;
	}
	/**
	 * Methode liefert minimale Spaltenanzahl zur&uuml;ck. Standardm&auml;&szlig;ig ist 0 (unbegrenzt)
	 * @return minimale Spaltenanzahl
	 */
	public int getMinColumnCount() {
		return minColumnCount;
	}
	/**
	 * Methode setzt minimale Spaltenanzahl. 0 = (unbegrenzt)
	 * @param minColumnCount minimale Spaltenanzahl
	 */
	public void setMinColumnCount(int minColumnCount) {
		this.minColumnCount = minColumnCount;
	}
	/**
	 * Methode liefert maximale Zeilenanzahl zur&uuml;ck. Standardm&auml;&szlig;ig ist 0 (unbegrenzt)
	 * @return maximale Zeilenanzahl
	 */
	public int getMaxRowCount() {
		return maxRowCount;
	}
	/**
	 * Methode setzt Zeilenanzahl. 0 = (unbegrenzt)
	 * @param maxRowCount maximale Zeilenanzahl
	 */
	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}
	/**
	 * Methode liefert minimale Zeilenanzahl zur&uuml;ck. Standardm&auml;&szlig;ig ist 0 (unbegrenzt)
	 * @return minimale Zeilenanzahl
	 */
	public int getMinRowCount() {
		return minRowCount;
	}
	/**
	 * Methode setzt minimale Zeilenanzahl. 0 = (unbegrenzt)
	 * @param minRowCount minimale Zeilenanzahl
	 */
	public void setMinRowCount(int minRowCount) {
		this.minRowCount = minRowCount;
	}

	@Override
	public String toString() {
		return "SizeRule [maxColumnCount=" + maxColumnCount
				+ ", minColumnCount=" + minColumnCount + ", errorColumn="+errorColumn+", maxRowCount="
				+ maxRowCount + ", minRowCount=" + minRowCount + ", errorRow="+errorRow+"]";
	}
}
