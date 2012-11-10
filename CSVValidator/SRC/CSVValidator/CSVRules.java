package CSVValidator;

import java.util.Vector;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import CSVValidator.Exceptions.CSVRuleException;
import CSVValidator.Rules.ColumnRule;
import CSVValidator.Rules.HeadRule;
import CSVValidator.Rules.TableSizeRule;
import CSVValidator.Rules.ValueRule;
/**
 * Diese Klasse repr&auml;sentiert Regelsammlung f&uuml;r CSV
 * </br>
 * </br>
 * </br>
 * <b>Datum:</b> 10 November 2012 </br>
 * <b>Lizenz:</b>  <a href="http://www.gnu.de/documents/gpl-2.0.de.html">GNU General Public License Version 2</a>
 * @author Aleksej Tokarev <a href="http://atoks.bplaced.net/">atoks.bplaced.net</a>
 * @version 1.0
 */
public class CSVRules {
	private CSVRulesReader reader;
	private NodeList nodeList;
	private Node node;
	private NamedNodeMap attributes;
	
	/**
	 * Konstruktor mit der Eingabe von CSV Regelleser.
	 * @param reader CSV Regelleser
	 */
	public CSVRules(CSVRulesReader reader){
		setCVSRulesReader(reader);
	}
	/**
	 * Methode setzt CSV Regelleser
	 * @param reader CSV Regelleser
	 */
	public void setCVSRulesReader(CSVRulesReader reader){
		this.reader = reader;
	}
	/**
	 * Methode liefert aktuelle CSV Regelleser zur&uuml;ck
	 * @return CSV Regelleser
	 */
	public CSVRulesReader getCVSRulesReader(){
		return reader;
	}
	/**
	 * Methode liefert Regeln die nach bestimmten XML-Pfad gesucht werden.
	 * @param expression XML-Pfad zu Regel
	 * @return NodeList mit XML-Elementen die Regel beschreiben.
	 * @throws XPathExpressionException
	 */
	public NodeList getRules(String expression) throws XPathExpressionException{
		if(reader!=null){
			return reader.readPath(expression);
		}
		return null;
	}
	/**
	 * Methode liefert Liste mit Regeln, die an bestimmte Inhalte angewendet werden m&uuml;ssen.
	 * @return Liste mit Regeln
	 * @throws CSVRuleException
	 */
	public Vector<ValueRule> getValueRules() throws CSVRuleException{
		Vector<ValueRule> rules = new Vector<ValueRule>();
		try {
			nodeList = getRules("//Rule/Values/Value");
			if(nodeList!=null && nodeList.getLength() >0){
				String v;
				NodeList cellNodeList;
				Node cellNode;
				NamedNodeMap cellAttributes;
				ValueRule vr;
				CellInfo cell[] = new CellInfo[2];
				// Alle Values durhlaufen
				for(int i=0; i<nodeList.getLength(); i++){
					node = nodeList.item(i);
					attributes = node.getAttributes();
					
					// Testen ob compare-Attrebut vorhanden ist
					if(attributes.getNamedItem("compare")!=null){
						cellNodeList = reader.readPath(node, "Cell");
						
						// Testen ob exakt 2 Collums vorhanden sind
						if(cellNodeList!=null && cellNodeList.getLength() == cell.length){
							
							// Alle Cells raushollen
							for(int c=0; c<cell.length; c++){
								cellNode = cellNodeList.item(c);
								cellAttributes = cellNode.getAttributes();
								cell[c] = new CellInfo();
								
								// Wenn keine Attribute vorhanden sind
								if(cellAttributes.getLength()==0){
									v = cellNode.getTextContent();
									cell[c].setValue(v);
									continue; // Weiter arbeiten hat kein Sinn, denn keine Attribute vorhanden sind
								}
								
								// Name
								if(cellAttributes.getNamedItem("name")!=null){
									v = cellAttributes.getNamedItem("name").getTextContent().trim();
									cell[c].setColumnName(v);
								}
								
								// Row
								if(cellAttributes.getNamedItem("row")!=null){
									v = cellAttributes.getNamedItem("row").getTextContent().trim();
									if(v.matches("^[0-9]+$")){
										cell[c].setRowId(Integer.parseInt(v));
									}else{
										throw new CSVRuleException("\"row\"-attribute by cell must be numeric, not:\""+v+"\"");
									}
								}
								
								// Column
								if(cellAttributes.getNamedItem("column")!=null){
									v = cellAttributes.getNamedItem("column").getTextContent().trim();
									if(v.matches("^[0-9]+$")){
										cell[c].setColumnId(Integer.parseInt(v));
									}else{
										throw new CSVRuleException("\"column\"-attribute by cell must be numeric, not:\""+v+"\"");
									}
								}
							}
										
						}else{
							throw new CSVRuleException("Value element must contain exactly "+cell.length+" Columns");
						}
						
						vr = new ValueRule();
						rules.add(vr);
						vr.setCell1(cell[0]);
						vr.setCell2(cell[1]);
						
						// Compare
						v = attributes.getNamedItem("compare").getTextContent().trim();
						if(v.matches("^(lt)|(gt)|(eq)|(ne)|(le)|(ge)$")){ // lt,gt,eq,ne,le,ge
							vr.setCompareRule(v);
						}else{
							throw new CSVRuleException("Unknown compare operator \""+v+"\". Must be (\"lt\" or \"gt\" or \"eq\" or \"le\" or \"ge\" or \"ne\")");
						}
						
						// Error
						if(attributes.getNamedItem("error")!=null){
							v = attributes.getNamedItem("error").getTextContent().trim();
							if(v.matches("^(fatal)|(error)|(warning)|(valid)$")){
								vr.setError(v);
							}else{
								throw new CSVRuleException("Unknown error checker \""+v+"\". Must be (\"fatal\" or \"error\" or \"warning\" or \"valid\")");
							}
						}
						
					}else{
						throw new CSVRuleException("\"compare\"-attribute is expected by Value");
					}
				}
			}
		} catch (XPathExpressionException e) {}
		return rules;
	}
	/**
	 * Methode liefert Regel f&uuml;r Tabellenkopf.
	 * @return Regel f&uuml;r Tabellenkopf
	 */
	public HeadRule getHeadRule(){
		try {
			nodeList = getRules("//Rule/Table/Head");
			if(nodeList!=null && nodeList.getLength() >0){
				node = nodeList.item(0);
				String v = node.getTextContent().trim();
				if(v.matches("^[0-9]+$")){
					HeadRule hr = new HeadRule();
					hr.setHeadId(Integer.parseInt(v));
					return hr;
				}
			}
		} catch (XPathExpressionException e) {}
		
		return new HeadRule(); // Default ohne Kopf
	}
	/**
	 * Methode liefert Regeln zu Tabellengr&ouml;&szlig;e.
	 * @return Regeln zu Tabellengr&ouml;&szlig;e
	 * @throws CSVRuleException 
	 */
	public TableSizeRule getTableSizeRule() throws CSVRuleException{
		try {
			TableSizeRule tr = new TableSizeRule();
			String v;
			// Columnsize
			nodeList = getRules("//Rule/Table/Size/Column[1]");
			if(nodeList!=null && nodeList.getLength() >0){
				node = nodeList.item(0);
				attributes = node.getAttributes();
				// Max aus Atrebut
				if(attributes.getNamedItem("max")!=null){
					v = attributes.getNamedItem("max").getTextContent().trim();
					if(v.matches("^[0-9]+$")){
						tr.setMaxColumnCount(Integer.parseInt(v));
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Attribute \"max\" must be numeric. But is: "+v);
					}
				}else{ // Max aus Content
					v = node.getTextContent().trim();
					if(v.matches("^[0-9]+$")){
						tr.setMaxColumnCount(Integer.parseInt(v));
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Max must be numeric. But is: "+v);
					}
				}
				
				// Min
				if(attributes.getNamedItem("min")!=null){
					v = attributes.getNamedItem("min").getTextContent().trim();
					if(v.matches("^[0-9]+$")){
						tr.setMinColumnCount(Integer.parseInt(v));
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Attribute \"min\" must be numeric. But is: "+v);
					}
				}
				
				// Error
				if(attributes.getNamedItem("error")!=null){
					v = attributes.getNamedItem("error").getTextContent().trim();
					if(v.matches("^(fatal)|(error)|(warning)|(valid)$")){
						tr.setColumnError(v);
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Unknown error checker \""+v+"\". Must be (\"fatal\" or \"error\" or \"warning\" or \"valid\")");
					}
				}
			}
			// Rowsize
			nodeList = getRules("//Rule/Table/Size/Row[1]");
			if(nodeList!=null && nodeList.getLength() >0){
				node = nodeList.item(0);
				attributes = node.getAttributes();
				// Max aus Atrebut
				if(attributes.getNamedItem("max")!=null){
					v = attributes.getNamedItem("max").getTextContent().trim();
					if(v.matches("^[0-9]+$")){
						tr.setMaxRowCount(Integer.parseInt(v));
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Attribute \"max\" must be numeric. But is: "+v);
					}
				}else{ // Max aus Content
					v = node.getTextContent().trim();
					if(v.matches("^[0-9]+$")){
						tr.setMaxRowCount(Integer.parseInt(v));
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Max must be numeric. But is: "+v);
					}
				}
				
				// Min
				if(attributes.getNamedItem("min")!=null){
					v = attributes.getNamedItem("min").getTextContent().trim();
					if(v.matches("^[0-9]+$")){
						tr.setMinRowCount(Integer.parseInt(v));
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Attribute \"min\" must be numeric. But is: "+v);
					}
				}
				
				// Error
				if(attributes.getNamedItem("error")!=null){
					v = attributes.getNamedItem("error").getTextContent().trim();
					if(v.matches("^(fatal)|(error)|(warning)|(valid)$")){
						tr.setRowError(v);
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Unknown error checker \""+v+"\". Must be (\"fatal\" or \"error\" or \"warning\" or \"valid\")");
					}
				}
			}
			
			return tr;
		} catch (XPathExpressionException e) {
			throw new CSVRuleException("XML-Path is invalid: "+e.getMessage());
		}
	}
	/**
	 * Methode liefert Regeln f&uuml;r bestimmte Spalte.
	 * @param id Index von Spalte, f&uuml;r welche Regel gesucht werden m&uuml;ssen.
	 * @return Regel f&uuml;r Spalte mit dem Index <b>id</b> 
	 * @throws CSVRuleException 
	 */
	public ColumnRule getColumnRule(int id) throws CSVRuleException{
		try {
			nodeList = getRules("//Rule/Table/Column[@id="+id+"][1]");
			if(nodeList!=null && nodeList.getLength() >0){
				node = nodeList.item(0);
				attributes = node.getAttributes();
				ColumnRule cr = new ColumnRule(id);
				String v;
				// Empty / DefaultValue
				if(attributes.getNamedItem("empty")!=null){
					v = attributes.getNamedItem("empty").getTextContent().trim();
					
					if(v.equals("no")){
						cr.setCanBeEmpty(false);
					}else if(v.equals("yes")){
						cr.setCanBeEmpty(true);	
					}else if(v.startsWith("'") && v.endsWith("'")){
						cr.setDefaultValue(v.substring(1, v.length()-1));
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Attribute \"empty\" is Invalid. Is \""+v+"\" Must be (\"no\" or \"yes\" or \"'default value'\")");
					}
					
				}
				
				// Match RegEx
				if(attributes.getNamedItem("match")!=null){
					v = attributes.getNamedItem("match").getTextContent().trim();
					if(!v.isEmpty()){
						cr.setRegEx(v);
						cr.setDatatype(CSVTable.DATATYPE_OWN);
					}
				}else{
					v = node.getTextContent();
					if(v.equals("number")){
						cr.setRegEx("^((\\+)?|(\\-)?)[0-9]+$");
						cr.setDatatype(CSVTable.DATATYPE_NUMBER);
					}else if(v.equals("decimal")){
						cr.setRegEx("^((\\+)?|(\\-)?)(([0-9]+)?\\.)?([0-9]+)([eE][-+]?[0-9]+)?$");
						cr.setDatatype(CSVTable.DATATYPE_DECIMAL);
					}else if(v.equals("date")){
						cr.setRegEx("^(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((19|20)\\d\\d)$");
						cr.setDatatype(CSVTable.DATATYPE_DATE);
					}else{ // Auch f&uuml;r v.equals("text")
						cr.setRegEx(".*");
						cr.setDatatype(CSVTable.DATATYPE_TEXT);
					}
				}		
				
				// Error
				if(attributes.getNamedItem("error")!=null){
					v = attributes.getNamedItem("error").getTextContent().trim();
					if(v.matches("^(fatal)|(error)|(warning)|(valid)$")){
						cr.setError(v);
					}else if(!v.isEmpty()){
						throw new CSVRuleException("Unknown error checker \""+v+"\". Must be (\"fatal\" or \"error\" or \"warning\" or \"valid\")");
					}
				}
				
				return cr;
			}
		} catch (XPathExpressionException e) {}
		return new ColumnRule(id); // Default, alles erlaubt
	}
}
