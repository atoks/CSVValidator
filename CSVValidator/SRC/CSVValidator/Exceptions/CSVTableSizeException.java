package CSVValidator.Exceptions;
/**
 * Diese Klasse repr&auml;sentiert Ausnahmen, die f&uuml;r Tabellengr&ouml;&szlig;e geworfen werden m&uuml;ssen.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVTableSizeException extends CSVRuleException{
	private static final long serialVersionUID = 1L;

	public CSVTableSizeException() {
		super();
	}

	public CSVTableSizeException(String message) {
		super(message);
	}

}
