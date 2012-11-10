package CSVValidator;
/**
 * Diese Hilfsklasse repr&auml;sentiert K&auml;stcheninformation.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CellInfo {
	private String columnName;
	private int columnId;
	private int rowId;
	private String value;
	/**
	 * Konstruktor. Standardm&auml;ßig wird alles mit 0 b.z.w. null initialisiert.
	 */
	public CellInfo(){
		columnName	= null;
		columnId	= 0;
		rowId		= 0;
		value		= null;
	}
	/**
	 * Konstruktor. Mit Eingabe von Spaltenname und Zeilenindex.
	 * @param columnName Spaltenname
	 * @param rowId Zeilenindex
	 */
	public CellInfo(String columnName, int rowId) {
		this.columnName = columnName;
		this.columnId	= 0;
		this.rowId 		= rowId;
	}
	/**
	 * Konstruktor. Mit Eingabe von Spaltenindex und Zeilenindex.
	 * @param columnId Spaltenindex
	 * @param rowId Zeilenindex
	 */
	public CellInfo(int columnId, int rowId) {
		this.columnName	= null;
		this.columnId 	= columnId;
		this.rowId 		= rowId;
	}
	/**
	 * Methode liefert Spaltenname zurück.
	 * @return Spaltenname
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * Methode setzt Spaltenname.
	 * @param columnName Spaltenname
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * Methode liefert Spaltenindex zurück
	 * @return Spaltenindex
	 */
	public int getColumnId() {
		return columnId;
	}
	/**
	 * Methode setzt Spaltenindex.
	 * @param columnId Spaltenindex
	 */
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	/**
	 * Methode liefert Zeilenindex zurück.
	 * @return Zeilenindex
	 */
	public int getRowId() {
		return rowId;
	}
	/**
	 * Methode setzt Zeilenindex.
	 * @param rowId Zeilenindex
	 */
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	/**
	 * Methode gibt K&auml;stcheninhalt zurück.
	 * @return K&auml;stcheninhalt
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Methode setzt Inhalt von der K&auml;stchen.
	 * @param value K&auml;stcheninhalt
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CSVCell [columnName=" + columnName + ", columnId=" + columnId
				+ ", rowId=" + rowId + ", value=" + value + "]";
	}

}
