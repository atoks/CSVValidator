package CSVValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import CSVValidator.Exceptions.CSVRuleException;
/**
 * Diese Klasse repr&auml;sentiert den Inhalt vom bestimmten K&auml;stchen.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVCellValue {
	/**
	 * K&auml;stcheninhalt
	 */
	public String value;
	/**
	 * Datentype vom Inhalt
	 */
	public String datatype = CSVTable.DATATYPE_TEXT;
	/**
	 * Methode vergleicht Inhalte von K&auml;stchen in Abh&auml;ngigkeit von Datentyp.
	 * @param to CSVCellValue mit welche verglichen werden muss
	 * @return 
	 * -1 = <b>this</b> kleiner <b>to</b></br>
	 * 0 = <b>this</b> gleich <b>to</b></br>
	 * 1 = <b>this</b> gr&ouml;&szlig;er <b>to</b></br>
	 * @throws CSVRuleException Inhalte k&ouml;nnen nicht verglichen werden.
	 */
	public int compareTo(CSVCellValue  to) throws CSVRuleException{
		if(datatype.equals(CSVTable.DATATYPE_UNKNOWN)){
			throw new CSVRuleException("DATATYPE_UNKNOWN cannot be compared");
		}else if(datatype.equals(CSVTable.DATATYPE_DATE)){
			try {
				Date d = new SimpleDateFormat( "dd.MM.yyyy" ).parse(value);
				Date dTo = new SimpleDateFormat( "dd.MM.yyyy" ).parse(to.value);
				return d.compareTo(dTo);
			} catch (ParseException e) {
				throw new CSVRuleException("DATATYPE_DATE cannot be compared: "+e.getMessage());
			}
		}else if(datatype.equals(CSVTable.DATATYPE_DECIMAL)){
			return Double.valueOf(value).compareTo(Double.valueOf(to.value));
		}else if(datatype.equals(CSVTable.DATATYPE_NUMBER)){
			return Integer.valueOf(value).compareTo(Integer.valueOf(to.value));
		}else{ // Text / Own
			return value.compareTo(to.value);
		}
	}
	public String toString(){return "[ value="+value+", datatype="+datatype+" ]";};
}
