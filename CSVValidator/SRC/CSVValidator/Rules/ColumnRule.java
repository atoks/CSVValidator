package CSVValidator.Rules;

import CSVValidator.CSVTable;
/**
 * Diese Hilfsklasse beinhaltet n&ouml;tige Information, um Spaltenregel zu speichern.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class ColumnRule {
	private int id;
	private String defaultValue;
	private boolean canEmpty;
	private String regEx;
	private String error;
	private String dataType;
	/**
	 * Konstruktor mit der Eingabe von Spaltenindex. </br>
	 * Standardm&auml;&szlig;ig Regel sehen wie folgt aus:  </br>
	 * Default Value ist undefiniert,  </br>
	 * K&auml;stchen darf leer sein,  </br>
	 * K&auml;stchen darf beliebigen Inhalt beinhalten,  </br>
	 * Fehler werden Komplet ignoriert,  </br>
	 * Datentyp ist Text.
	 * @param id Spaltenindex
	 */
	public ColumnRule(int id) {
		this.id		= id;
		defaultValue= null;
		canEmpty	= true;
		regEx		= ".*";
		error		= "valid"; // 'fatal', 'error', 'warning', 'valid'
		dataType	= CSVTable.DATATYPE_TEXT;
	}
	/**
	 * Methode setzt Datentyp f&uuml;r gesamte Spalte.
	 * @param dataType </br>
	 * CSVTable.DATATYPE_TEXT</br>
	 * CSVTable.DATATYPE_NUMBER</br>
	 * CSVTable.DATATYPE_DECIMAL</br>
	 * CSVTable.DATATYPE_DATE</br>
	 * CSVTable.DATATYPE_OWN</br>
	 * CSVTable.DATATYPE_UNKNOWN
	 */
	public void setDatatype(String dataType){
		// Wenn Datentyp ist unbekannt
		if(dataType!=CSVTable.DATATYPE_TEXT &&
			dataType!=CSVTable.DATATYPE_NUMBER &&
			dataType!=CSVTable.DATATYPE_DECIMAL &&
			dataType!=CSVTable.DATATYPE_DATE &&
			dataType!=CSVTable.DATATYPE_OWN
				)
			this.dataType = CSVTable.DATATYPE_UNKNOWN;
		this.dataType = dataType;
	}
	/**
	 * Methode gibt Datentyp von der Spalte zur&uuml;ck.
	 * @return
	 * CSVTable.DATATYPE_TEXT</br>
	 * CSVTable.DATATYPE_NUMBER</br>
	 * CSVTable.DATATYPE_DECIMAL</br>
	 * CSVTable.DATATYPE_DATE</br>
	 * CSVTable.DATATYPE_OWN</br>
	 * CSVTable.DATATYPE_UNKNOWN
	 */
	public String getDatatype(){
		return dataType;
	}
	/**
	 * Methode setzt Fehlertype.
	 * @param error "fatal", "error", "warning", "valid"
	 */
	public void setError(String error){
		this.error = error;
	}
	/**
	 * Methode leifert Fehlertyp zur&uuml;ck. 
	 * @return "fatal", "error", "warning", "valid"
	 */
	public String getError(){
		return error;
	}
	/**
	 * Methode sagt, ob die Spalte leer sein darf.
	 * @return <b>true</b> = darf leer sein, <b>false</b> = darf nicht leer sein 
	 */
	public boolean canBeEmpty(){
		return canEmpty;
	}
	/**
	 * Methode bestimmt, ob die Spalte leer sein darf.
	 * @param canEmpty <b>true</b> = darf leer sein, <b>false</b> = darf nicht leer sein 
	 */
	public void setCanBeEmpty(boolean canEmpty){
		this.canEmpty = canEmpty;
	}
	/**
	 * Methode leifert Spaltenindex.
	 * @return Spaltenindex
	 */
	public int getId() {
		return id;
	}
	/**
	 * Methode setzt Spaltenindex.
	 * @param id Spaltenindex
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Methode liefert Defaultvalue zur&uuml;ck.</br>
	 * Defaultvalue ist Spalteninhalt f&uuml;r die Spalten, die nicht leer sein d&uuml;rfen.
	 * @return Defaultvalue.
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * Methode leifert Defaultvalue f&uuml;r Spalte
	 * Defaultvalue ist Spalteninhalt f&uuml;r die Spalten, die nicht leer sein d&uuml;rfen.
	 * @param defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * Methode gibt regul&auml;rer Ausdruck, mit welchem wird Spalteninhalt validiert, zur&uuml;ck.
	 * @return Validierungsausdruck
	 */
	public String getRegEx() {
		return regEx;
	}
	/**
	 * Methode setzt Validierungsausdruck f&uuml;r die Spalte.
	 * @param regEx Validierungsausdruck
	 */
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	@Override
	public String toString() {
		return "ColimnRule [id=" + id + ", defaultValue=" + defaultValue
				+ ", canEmpty=" + canEmpty + ", regEx=" + regEx + ", error="+error+", dataType="+dataType+"]";
	}
	
}
