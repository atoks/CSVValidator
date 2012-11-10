package CSVValidator;

import java.util.Vector;
/**
 * Diese Klasse repr&auml;sentiert Tabelle mit CSV Inhalt
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVTable extends Vector<CSVRow>{
	/**
	 * String (default)
	 */
	public static final String DATATYPE_TEXT	= "text";
	/**
	 * Integer
	 */
	public static final String DATATYPE_NUMBER	= "number";
	/**
	 * Double
	 */
	public static final String DATATYPE_DECIMAL	= "decimal";
	/**
	 * (String) TT.MM.JJJJ
	 */
	public static final String DATATYPE_DATE	= "date";
	/**
	 * Regul&auml;re Ausdruck
	 */
	public static final String DATATYPE_OWN		= "own";
	/**
	 * NULL
	 */
	public static final String DATATYPE_UNKNOWN	= null; // unknown

	private static final long serialVersionUID = 1L;
	private int headId = -1;
	
	/*
	 * Datentypen werden bei Validierung hinnzugef&uuml;gt
	 * oder auch per Hand
	 * text 	= (String) "default"
	 * number 	= (Integer)
	 * decimal 	= (Double)
	 * date		= (TT.MM.JJJJ)
	 * own		= (RegEx)
	 */
	private CSVRow dataTypes = new CSVRow();
	
	/**
	 * Methode gibt Tabellenkopfindex zur&uuml;ck. Bei -1 ist Tabellenkopf nicht definiert.
	 * @return Tabellenkopfindex
	 */
	public int getHeadId(){
		return headId;
	}
	/**
	 * Methode setzt Tabellenkopfindex. Standardm&auml;&szlig;ig ist -1 ("Kein Tabellenkopf")
	 * @param headId Tabellenkopfindex
	 */
	public void setHeadId(int headId){
		if(headId<size())
			this.headId = headId;
	}
	/**
	 * Methode setzt Datentyp von der Spalte, die Index <b>columnId</b> besitzt.
	 * @param columnId Spaltenindex
	 * @param dataTypeName Datentype</br>
	 * DATATYPE_TEXT</br>
	 * DATATYPE_NUMBER</br>
	 * DATATYPE_DECIMAL</br>
	 * DATATYPE_DATE</br>
	 * DATATYPE_OWN
	 */
	public void setColumnDatatype(int columnId, String dataTypeName){
		// Wenn Datentyp ist unbekannt
		if(dataTypeName!=DATATYPE_TEXT &&
			dataTypeName!=DATATYPE_NUMBER &&
			dataTypeName!=DATATYPE_DECIMAL &&
			dataTypeName!=DATATYPE_DATE &&
			dataTypeName!=DATATYPE_OWN
				)
			dataTypes.set(columnId, DATATYPE_UNKNOWN);
			
		// Bekante Datentype speichern
		dataTypes.set(columnId, dataTypeName);
	}
	/**
	 * Methode liefert Datentyp von Spalte, die index <b>columnId</b> besitzt, zur&uuml;ck.
	 * @param columnId Spaltenindex
	 * @return
	 * DATATYPE_TEXT 	= (String) "default" </br>
	 * DATATYPE_NUMBER 	= (Integer) </br>
	 * DATATYPE_DECIMAL = (Double) </br>
	 * DATATYPE_DATE	= (TT.MM.JJJJ) </br>
	 * DATATYPE_OWN		= (RegEx) </br>
	 * DATATYPE_UNKNOWN	= unknown (null)
	 */
	public String getColumnDatatype(int columnId){
		if(columnId<dataTypes.size())
			return dataTypes.get(columnId);
		return null;
	}
	/**
	 * Methode liefert Zeile mit Datentypen zur&uuml;ck.
     * Datentypen werden zu Spalten durch Spaltenindex zugeordnet.
	 * @return CSVRow mit Datentypen f&uuml;r jede Spalte</br>
	 * DATATYPE_TEXT 	= (String) "default" </br>
	 * DATATYPE_NUMBER 	= (Integer) </br>
	 * DATATYPE_DECIMAL = (Double) </br>
	 * DATATYPE_DATE	= (TT.MM.JJJJ) </br>
	 * DATATYPE_OWN		= (RegEx) </br>
	 * DATATYPE_UNKNOWN	= unknown (null)
	 */
	public CSVRow getDatatypeRow(){
		return dataTypes;
	}
	/**
	 * Methode setzt Zeile mit Datentypen
	 * @param dataTypes Zeile mit Datentypen
	 */
	public void setDatatypeRow(CSVRow dataTypes){
		this.dataTypes = dataTypes;
	}
	/**
	 * Methode liefert Tabellenkopf zur&uuml;ck oder <b>null</b>, wenn kein Kopf gesetzt ist.
	 * @return Tabellenkopf
	 */
	public CSVRow getHead(){
		if(headId>=0 && headId<size())
			return this.get(headId);
		else
			return null;
	}
	/**
	 * Methode liefert Zeile mit dem Index <b>id</b>
	 * @param id Zeilenindex
	 * @return Zeile mit Index <b>id</b>
	 */
	public CSVRow getRow(int id){
		return this.get(id);
	}
	/**
	 * Methode f&uuml;gt eine CSV-Zeile in die Tabelle hinzu.
	 * @param row CSV-Zeile
	 */
	public void addRow(CSVRow row){
		// Datentype Zeile vorbereiten
		if(dataTypes.size()==0){
			for(int i=0; i<row.size(); i++)
				dataTypes.add("text"); // Default-Datatype
		}
		this.add(row);
	}
	/**
	 * Methode setzt eine CSV-Zeile in die Tabelle an Position <b>id</b>.
	 * @param id Position an welche muss Zeile gesetzt werden.
	 * @param row CSV-Zeile
	 * @return Alte CSV-Zeile die am Position <b>id</id> gesetzt war.
	 */
	public CSVRow setRow(int id, CSVRow row){
		// Datentype Zeile vorbereiten
		if(dataTypes.size()==0){
			for(int i=0; i<row.size(); i++)
				dataTypes.add("text"); // Default-Datatype
		}
		return this.set(id, row);
	}
	/**
	 * Mthode entfernt eine CSV-Zeile aus der Tabelle von Position <b>id</b>.
	 * @param id Zeilenindex
	 * @return Gel&ouml;schte Zeile
	 */
	public CSVRow removeRow(int id){
		return this.remove(id);
	}
	/**
	 * Entleert die Tabelle
	 */
	public void clearTable(){
		this.clear();
	}
	
	@Override
	public String toString(){
		String result = "CVSTable: [\n";
		
		result = result.concat("Datatypes: ");
		result = result.concat(dataTypes.toString());
		result = result.concat("\n");
		
		if(headId >= 0 && this.size()>0){
			result = result.concat("<< Head: ");
			result = result.concat(this.getHead().toString());
			result = result.concat(" >>\n");
		}
		for(int i=0; i<this.size(); i++){
			result = result.concat(this.getRow(i).toString());
			result = result.concat("\n");
		}
		result = result.concat("]");
		return result;
	}
}
