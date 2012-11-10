CSVValidator

Autor: Aleksej Tokarev
Online: http://atoks.bplaced.net
Erstellungsdatum: 10. November 2012
Erstellungszeit: von 6. November 2012 bis 10. November 2012
Aktuelle Version: 1.0

CSV Validator repräsentiert eine JAVA-Bibliothek die zum Überprüfen von 
CSV (Comma-separated values)- Dateien eingesetzt werden kann. 

Bei ausprogrammieren wurde JAVA 1.5 verwändet. 
Diese Entscheidung hängt mit Unterstützung von früheren JAVA-Versionen zusammen.

Zum Validieren von CSV-Dateien benötigt Validator eine Datei mit Validierungsregel. 
Diese Datei ist in XML (Extensible Markup Language)- Struktur geschrieben werden muss. 
Um genau Beschreibung von Regeln zu erhalten lesen Sie bitte Regelbeschreibung.pdf, 
dort ist beschrieben wie genau soll die Datei mit Regeln aussehen (Ein Beispiel ist auch vorhanden). 

Zurzeit sind es nur die Fälle ausprogrammiert, die als Fehlertyp mit „fotal“ bezeichnet werden. 
Das heißt alle anderen Fälle (error, warning, valid) wurden als Valid bezeichnet. 

Für die Fälle (error, warning) ist es alles fast fertig, 
es fehlt nur Anordnung von Daten zu entsprechenden Kategorien. 
Dafür ist die Funktion validateTable der CSVValidator zuständig.

In der Planung ist es auch noch benutzerfreundlich Abfragen. 
Alle Regeln müssen wie bei SQL (Structured Query Language), 
mit einfachem Text definiert werden. 
Aber erst werden die Regeln weiter mit der Hilfe von XML-Datei geschrieben.

Zum Installation von dieser Bibliothek lesen Sie bitte INSTALL.txt.

Wenn Sie weitere Fragen haben, 
kontaktieren Sie mich bitte unter: http://atoks.bplaced.net/index.php?action=contact