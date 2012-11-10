package CSVValidator;

import java.util.Vector;

import CSVValidator.Exceptions.CSVRuleException;
import CSVValidator.Exceptions.CSVTableSizeException;
import CSVValidator.Rules.ColumnRule;
import CSVValidator.Rules.HeadRule;
import CSVValidator.Rules.TableSizeRule;
import CSVValidator.Rules.ValueRule;
/**
 * Diese Klasse repr&auml;sentiert CSV Validator. </br>
 * Validierung erfolgt nach Regeln die durch CSVRules gesetzt werden.</br>
 * </br>
 * <p style="color:red;"><b>
 * !!!!!! ACHTUNG !!!!!!</br>
 * </br>
 * Die Klasse ist noch nicht vollst&auml;ndig ausprogrammiert. Es werden nur fatale Fehler behandelt. Ales anderes zurzeit wird als valid bezeichnet.</br>
 * Alle Pr&uuml;fungen, um weitere Fehlertypen rauszufinden, sind vorhanden aber keine entsprechende Routine daf&uuml;r ausprogrammiert ist.
 * </b></p>
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVValidator {
	private CSVRules rules;
	private String lastError;
	private CSVRow headRow;
	private CSVRow dataTypeRow;
	private CSVTable table;
	
	/*
	private CSVTable valid;
	private CSVTable warnings;
	private CSVTable errors;
	*/
	/**
	 * Konstruktor mit der Eingabe von Regeln.
	 * @param rules Validierungsregeln
	 */
	public  CSVValidator(CSVRules rules){
		this.rules = rules;
	}
	/**
	 * Methode liefert aktuelle Validierungsregeln zur&uuml;ck.
	 * @return Validierungsregeln
	 */
	public CSVRules getRules() {
		return rules;
	}
	/**
	 * Methode setzt Validierungsregeln
	 * @param rules Validierungsregeln
	 */
	public void setRules(CSVRules rules) {
		this.rules = rules;
	}
	/**
	 * Methode validiert eingegebene Tabelle mit CSV-Inhalt nach Erf&uuml;hlbarkeit von bestimmten Regeln die durch CSVRules gesetzt wurden.
	 * @param table Tabelle mit CSV-Inhalt
	 * @throws CSVRuleException Ausnahmen die w&auml;hrend Validierung geworfen werden.
	 */
	public void validateTable(CSVTable table) throws CSVRuleException{
		if(table == null)
			throw new CSVRuleException("CSVTable cannot be null");
		if(rules == null)
			throw new CSVRuleException("No rules is set");
		
		this.table = table;
		
		/*
		valid	= new CSVTable();
		warnings= new CSVTable();
		errors	= new CSVTable();
		
		valid.setHeadId(table.getHeadId());
		warnings.setHeadId(table.getHeadId());
		errors.setHeadId(table.getHeadId());
		*/
		
		// Index für Tabellenkopf setzen
		HeadRule hr = rules.getHeadRule();
		table.setHeadId(hr.getHeadId());
		
		
		
		// Tabellengrösse testen
		TableSizeRule tabRul = rules.getTableSizeRule();
		
		//#############################################
		//############ Zeilenanzahl prüfen ############
		//#############################################
		
		// Tabelle hat zu viel Zeilen
		if(tabRul.getMaxRowCount()!=0 && tabRul.getMaxRowCount()<table.size()){
			lastError = tabRul.getRowError();
			if(lastError.equals("fatal"))
				throw new CSVTableSizeException("FATAL ERROR: Table is too large. Must have "+tabRul.getMaxRowCount()+" Rows but not "+table.size());
		}
		
		// Tabelle hat zu wenig Zeilen
		if(tabRul.getMinRowCount()!=0 && tabRul.getMinRowCount()>table.size()){
			lastError = tabRul.getRowError();
			if(lastError.equals("fatal"))
				throw new CSVTableSizeException("FATAL ERROR: Table is too little. Must have "+tabRul.getMinRowCount()+" Rows but not "+table.size());
		}
		
		//##############################################
		//######## Spalten zeilenweise prüfen ##########
		//##############################################
		
		headRow 	= table.getHead();
		dataTypeRow = table.getDatatypeRow();
		
		// Chache für ColumnRule
		Vector<ColumnRule> columnRuleChache = new Vector<ColumnRule>();
		
		// Weitere Inhalt prüffen
		CSVRow row;
		for(int r=0; r< table.size(); r++){
			row = table.getRow(r);
			
			//############ Anzahl von Kästchen in Zeile überprüfen ############
			// Zeile hat zu viel Kästchen
			if(tabRul.getMaxColumnCount()!=0 && tabRul.getMaxColumnCount()<row.size()){
				lastError = tabRul.getColumnError();
				if(lastError.equals("fatal"))
					throw new CSVTableSizeException("FATAL ERROR: Row witch ID: "+r+" is too large. Must be "+tabRul.getMaxColumnCount()+" but is "+row.size());
			}
			
			// Zeile hat zu wenig Kästchen
			if(tabRul.getMinColumnCount()!=0 && tabRul.getMinColumnCount()>row.size()){
				lastError = tabRul.getColumnError();
				if(lastError.equals("fatal"))
					throw new CSVTableSizeException("FATAL ERROR: Row witch ID: "+r+" is too little. Must be "+tabRul.getMinColumnCount()+" but is "+row.size());
			}
			
			// Kopfzeile weiter nicht prüffen
			if(row == headRow) continue;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++ Kestchen auf Regel prüffen +++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++
			
			String cellValue;
			ColumnRule cr;
			for(int c=0; c<row.size(); c++){
				// gespeicherte Regel suchen
				if(c >= columnRuleChache.size()){
					// Regel aus CSVRules raushollen
					columnRuleChache.add(rules.getColumnRule(c));
				}
				// gespeicherte Regell raushollen
				cr = columnRuleChache.get(c);
				// Inhalt von Kästchen hollen
				cellValue = row.get(c);
				
				// Testen ob Kästchen leer sein darf
				if(cellValue.isEmpty() && !cr.canBeEmpty() && cr.getDefaultValue() == null){
					lastError = cr.getError();
					if(lastError.equals("fatal"))
						throw new CSVTableSizeException("FATAL ERROR: cell ("+r+" x "+c+") cannot be empty");
				}
				
				// Testen ob Defaultwert gesetzt weren muss
				// Wenn ja setzen
				if(cellValue.isEmpty() && cr.getDefaultValue() != null){
					// Inhalt setzen
					row.set(c, cr.getDefaultValue());
					// Inhalt von Kästchen hollen
					cellValue = row.get(c);
				}
				
				// Testen ob Inhalt entspricht den Regel
				if(!cellValue.matches(cr.getRegEx())){
					lastError = cr.getError();
					if(lastError.equals("fatal"))
						throw new CSVTableSizeException("FATAL ERROR: value of cell ("+r+" x "+c+" = '"+cellValue+"') is invalid");
				}
				dataTypeRow.set(c, cr.getDatatype());
				
			}
		}
		
		//##############################################
		//## Regel für einzelne Inhalte vergleichen ####
		//##############################################
		
		// Alle Inhaltsregel raushollen
		Vector<ValueRule> vrList = rules.getValueRules();
		CellInfo cr1, 
			cr2;
		String compareRule, // <!-- lt,gt,eq,ne,le,ge -->
			valideRule = null; // Nach dem Vergleich (im Fall wenn Vergleich war erfolgreich, Variable beinhaltet "compareRule")
		CSVCellValue value1, 
			value2;
		int copareResult;
		
		// Alle Regel nach einander überprüffen
		for(ValueRule vr: vrList){
			cr1			= vr.getCell1();
			cr2			= vr.getCell2();
			valideRule	= null;
			
			// Kästcheninhalt nach Kästchenregel zu ermietteln
			// Im Fehlerfahl wir eine Ausnahme geworfen
			value1 = getCellValue(cr1, vr);
			value2 = getCellValue(cr2, vr);
			
			/*
			 * lt = kleiner als
			 * gt = größer als
			 * eq = gleich
			 * le = kleiner gleich
			 * ge = größer gleich
			 * ne = ungleich
			 */
			compareRule	= vr.getCompareRule();
			
			// Für jeder Datentype eigene Compare !!!!!
			copareResult= value1.compareTo(value2);
			
			// Vergleichen
			if(compareRule.equals("lt") && copareResult < 0){ // Auf kleiner als prüfen
				valideRule = compareRule;
			}else if(compareRule.equals("gt") && copareResult > 0){ // Auf größer als prüfen
				valideRule = compareRule;
			}else if(compareRule.equals("eq") && copareResult == 0){ // Auf gleich prüfen
				valideRule = compareRule;
			}else if(compareRule.equals("le") && (copareResult < 0 || copareResult == 0)){ // Auf kleiner gleich prüfen
				valideRule = compareRule;
			}else if(compareRule.equals("ge") && (copareResult > 0 || copareResult == 0)){ // Auf größer gleich prüfen
				valideRule = compareRule;
			}else if(compareRule.equals("ne")){
				valideRule = compareRule;
			}
			
			// Wenn Vergleich gescheitert ist
			if(valideRule == null){
				lastError = vr.getError();
				if(lastError.equals("fatal"))
					throw new CSVTableSizeException("FATAL ERROR: "+value1+" is not ("+compareRule+") to "+value2);
			}
			
			//System.out.println("V1: "+value1+" V2: "+value2+" compareRule: "+compareRule+" valideRule: "+valideRule+" copareResult: "+copareResult);
			
		}
		
	}
	/*
	 * Methode liefert Inhalt von Kästchen die in CellRule mit rowId/columnId difeniert wurde
	 */
	private CSVCellValue getCellValue(CellInfo cellRule, ValueRule vr) throws CSVTableSizeException{	
		CSVCellValue cv = new CSVCellValue();
		//+++++++++++++++++++++++++++++++++++++++++++++
		//+++++ Direkte Inhalt aus regel ermitteln ++++
		//+++++++++++++++++++++++++++++++++++++++++++++
		
		// Wenn Regel nur Inhalt beinhaltet z.B.: <Cell>Content</Cell> oder <Cell row="n">Test</Cell>
		if(cellRule.getValue()!=null && 
				cellRule.getRowId() == 0 && 
				(cellRule.getColumnId() == 0 || cellRule.getColumnName() ==null) ){
			cv.value = cellRule.getValue();
			return cv; // Inhalt zurück geben (Weiter nicht suchen)
		}
		
		String columnName;
		int columnId = -1;
		int rowId = -1;
		
		//+++++++++++++++++++++++++++++
		//+++++++ Cell ermtteln ++++++
		//+++++++++++++++++++++++++++++
		
		// Spaltenindex nach Spaltenname ermitteln
		columnName = cellRule.getColumnName();
		if(columnName!=null && headRow!=null){
			// Index suchen
			for(int i=0; i<headRow.size(); i++){
				if(headRow.get(i).equals(columnName)){
					columnId = i;
					break;
				}
			}
			
			if(columnId == -1){
				lastError = vr.getError();
				if(lastError.equals("fatal"))
					throw new CSVTableSizeException("FATAL ERROR: Column with name: '"+columnName+"' was not found");
			}
		}
		
		// Testen ob columnId war gafunden
		if(columnId == -1) // Wenn nicht gefunden
			columnId = cellRule.getColumnId(); // Direkte Spalten Index raushollen
		
		rowId = cellRule.getRowId(); // Direkte Zeilen Index raushollen
		
		// Testen ob gegebene Kestchen vorhanden sind
		if(table.size()>rowId && table.get(rowId).size()>columnId){ // Wenn ja Inhalt entnemen
			cv.datatype = dataTypeRow.get(columnId);
			cv.value	= table.get(rowId).get(columnId);
			return cv;
		}else{
			lastError = vr.getError();
			if(lastError.equals("fatal"))
				throw new CSVTableSizeException("FATAL ERROR: Cell ("+rowId+" x "+columnId+") was not found");
		}
		
		return null;
	}
	
}
