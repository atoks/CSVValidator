package CSVValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Diese Klasse repr&auml;sentiert CSV Leser. Es werden CSV Inhalte gelesen und in CSVTable importiert.
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVReader {
	private BufferedReader br;
	private String delimiter;
	private String recordDelemiter;
	private Pattern csvPattern;
	private Matcher matcher;
	/**
	 * Konstruktor mit der Eingabe von CSV-Datei.
	 * @param f CSV-Datei
	 * @throws FileNotFoundException
	 */
	public CSVReader(File f) throws FileNotFoundException{
		this(new FileInputStream(f));
	}
	/**
	 * Konstruktor mit der Eingabe von CSV-FileInputStream.
	 * @param fis CSV-FileInputStream.
	 */
	public CSVReader(FileInputStream fis){
		this(new InputStreamReader(fis));
	}
	/**
	 * Konstruktor mit der Eingabe von CSV-InputStream.
	 * @param is CSV-InputStream
	 */
	public CSVReader(InputStream is){
		this(new InputStreamReader(is));
	}
	/**
	 * Konstruktor mit der Eingabe von CSV-InputStreamReader.
	 * @param isr CSV-InputStreamReader.
	 */
	public CSVReader(InputStreamReader isr){
		this(new BufferedReader(isr));
	}
	/**
	 * Konstruktor mit der Eingabe von CSV-BufferedReader.
	 * @param br CSV-BufferedReader.
	 */
	public CSVReader(BufferedReader br){
		this.br = br;
		init();
	}
	/**
	 * Methode schlie&szlig;t alle im CSVReader ge&ouml;ffnete Datenfl&uuml;sse.
	 * @throws IOException
	 */
	public void close() throws IOException{
		if(br !=null){
			br.close();
		}
	}
	/**
	 * Methode setzt Inhaltstrenner. Standardm&auml;&szlig;ig als Inhaltstrenner ist Komma gesetzt.</br>
	 * @param delimiter Trennzeichen
	 */
	public void setDelimiter(String delimiter){
		if(this.delimiter!=null)
			// Alte Trenner im RegEx mit neuem ersetzen
			csvPattern = Pattern.compile(csvPattern.pattern().replace(this.delimiter, delimiter));
		this.delimiter = delimiter;
	}
	/**
	 * Methode liefert aktuelle Inhaltstrenner.
	 * @return Trennzeichen
	 */
	public String getDelimiter(){
		return delimiter;
	}
	/**
	 * Methode setzt Datentrenner. Standardm&auml;&szlig;ig als Datentrenner ist "\r\n" gesetzt.</br>
	 * @param recordDelemiter Datentrenner
	 */
	public void setRecordDelimiter(String recordDelemiter){
		this.recordDelemiter = recordDelemiter;
	}
	/**
	 * Methode liefert aktuelle Datentrenner.
	 * @return Datentrenner
	 */
	public String getRecordDelimiter(){
		return recordDelemiter;
	}
	/**
	 * Methode setzt einen regul&auml;ren Ausdruck zum Trennen von Inhalten.
	 * @param csvPattern RegEx
	 */
	public void setPattern(Pattern csvPattern){
		this.csvPattern = csvPattern;
	}
	/**
	 * Methode liefert aktuelle regul&auml;re Ausdruck zum Trennen von Inhalten.
	 * @return RegEx
	 */
	public Pattern getPattern(){
		return csvPattern;
	}
	/**
	 * Methode liest ein Datensatz aus BufferedReader mit Hilfe von BufferedReader.read()</br>
	 * Als Datensatzende wird recordDelemiter oder BufferedReader.read() == -1 gekennzeichnet.
	 * @return Datensatz
	 */
	public String readRecord(){
		if(br==null) // Reader ist nicht initialisiert
			return null;
		try {
			int c=-1;
			String ds = "";
			// Bis zum Datensatztrener lesen 
			while(!ds.endsWith(recordDelemiter) && (c=br.read())!=-1){
				ds += (char)c;
			}
			// Dateiende
			if(ds.isEmpty() && c==-1)
				return null;
			
			// Datensatz ohne DS-Trenner zur&uuml;ckgeben
			return ds.replace(recordDelemiter, "");
		} catch (IOException e) {
			return null;
		}
	}
	/**
	 * Methode zerlegt CSV-Datensatz und speichert in CSVRow.
	 * @param record CSV-Datensatz
	 * @return CSVRow mit Inhalt aus CSV-Datensatz
	 */
	public CSVRow parseRecord(String record){
		if(record==null)
			return null;
		String value;
		CSVRow result = new CSVRow();
		matcher = csvPattern.matcher(record);
		while(matcher.find()){
			value = matcher.group(1);
			if(value==null){
				value = matcher.group(2);
			}
			// Apostrophen entfernen
			if(value.startsWith("\"") && value.endsWith("\"")){
				value = value.substring(1, value.length()-1);
			}
			// Gespiegelte Apostrophen ersetzen
			value = value.replace("\"\"", "\"");
			result.add(value);
		}
		
		return result;
	}
	/**
	 * Methode zerlegt CSV-Datensatz und speichert in CSVRow.
	 * CSV-Datensatz wird, mittels this.readRecord(), direkt aus CSV-Datei gelesen
	 * @return CSVRow mit Inhalt aus CSV-Datensatz
	 */
	public CSVRow parseRecord(){
		return parseRecord(readRecord());
	}
	/**
	 * Methode liest CSV-Datei aus und speichert in CSVTable.
	 * @return CSVTable mit CSV-Inhalt
	 */
	public CSVTable parseToTable(){
		CSVTable table = new CSVTable();
		CSVRow row;
		while((row = parseRecord())!=null){
			table.addRow(row);
		}
		return table;
	}
	/*
	 * Objektinitialisierung
	 */
	private void init(){
		// "\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)"
		// "(?:^|,)(\\\"(?:[^\\\"]+|\\\"\\\")*\\\"|[^,]*)"
		csvPattern = Pattern.compile("(?:^|,)(\\\"(?:[^\\\"]+|\\\"\\\")*\\\"|[^,]*)");
		setDelimiter(",");
		setRecordDelimiter("\r\n");
	}
}
