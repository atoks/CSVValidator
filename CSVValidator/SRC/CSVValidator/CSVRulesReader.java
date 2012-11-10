package CSVValidator;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
/**
 * Diese Klasse repr&auml;sentiert Regelleser.
 * Regel werden aus XML-Datei gelesen.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVRulesReader {
	private Document dokument;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private XPath xpath;
	private XPathExpression exp;
	/**
	 * Konstruktor mit der Eingabe von Dom-Document mit Regel.
	 * @param dokument Dom-Document.
	 * @throws Exception
	 */
	public CSVRulesReader(Document dokument) throws Exception{
		init();
		this.dokument = dokument;
	}
	/**
	 * Konstruktor mit der Eingabe von XML-Datei mit Regeln.
	 * @param f XML-Datei mit Regeln.
	 * @throws Exception
	 */
	public CSVRulesReader(File f) throws Exception{
		init();
		this.dokument = builder.parse(f);
	}
	/**
	 * Konstruktor mit der Eingabe von XML-InputStream mit Regel.
	 * @param is XML-InputStream mit Regel.
	 * @throws Exception
	 */
	public CSVRulesReader(InputStream is) throws Exception{
		init();
		this.dokument = builder.parse(is);
	}
	/**
	 * Methode liest eine Liste mit Regeln aus Dom-Struktur raus und gibt es zur&uuml;ck.
	 * @param expression Pfadausdruck zu Regelsammlung
	 * @return Liste mit Regeln
	 * @throws XPathExpressionException
	 */
	public NodeList readPath(String expression) throws XPathExpressionException{
		return readPath(dokument, expression);
	}
	/**
	 * Methode liest eine Liste mit Regeln aus Dom-Struktur raus und gibt es zur&uuml;ck.
	 * @param item Dom-Baum
	 * @param expression Pfadausdruck zu Regelsammlung
	 * @return Liste mit Regeln
	 * @throws XPathExpressionException
	 */
	public NodeList readPath(Object item, String expression) throws XPathExpressionException{
		exp = xpath.compile(expression);
		if(exp == null)
			return null;
		return (NodeList)exp.evaluate(item, XPathConstants.NODESET);
	}
	/*
	 * Objektinitialisierung
	 */
	private void init() throws ParserConfigurationException{
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		xpath = XPathFactory.newInstance().newXPath();
	}
	
}
