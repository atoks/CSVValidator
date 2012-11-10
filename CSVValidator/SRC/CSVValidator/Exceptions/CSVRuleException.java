package CSVValidator.Exceptions;
/**
 * Diese Klasse repr&auml;sentiert Ausnahmen, die bei Regelsuche oder Regelanwendung geworfen werden.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVRuleException extends Exception{
	private static final long serialVersionUID = 1L;

	public CSVRuleException() {
		super();
	}

	public CSVRuleException(String message) {
		super(message);
	}
	
}
