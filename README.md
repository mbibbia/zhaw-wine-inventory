# zhaw-wine-inventory
ZHAW Wine Inventory (CAS OOP)

Modularbeit im Modul Programmiersprachen (CAS OOP) an der ZHAW School of Engineering in Zürich

<h2>Ausgangslage</h2>

Das Ziel der Arbeit war die Erstellung einer Desktop-Applikation zur Verwaltung eines Weinkellers.
Die Applikation erlaubt es, verschiedene Informationen zu den Weinen, beispielsweise Name, Art,
Klassifikation, geographische Herkunft oder Hersteller darzustellen, neu zu erfassen, zu ändern
oder zu löschen.

<h1>Aufbau der Applikation</h2>

Die Applikation nutzt Spring Boot als FXML Controller Factory.

<h2>Persistierung</h2>

Für jede Entität steht ein Service-Interface zur Verfügung. Die Service-Interfaces implementieren ihrerseits 
ein generisches Service-Interface. Damit wird sichergestellt, dass alle Entitäten dieselben Methoden mit derselben
Funktionalität anbieten. DieImplementierungen dieser Service-Interfaces verwenden jeweils ein Repository,
welches das JPA Repository des Spring-Frameworks erweitert. Die Entitäten werden mittels ORM (Object-Relational Mapping) persistiert.

<h2>Zusammensetzung von einzelnen Views zu einem Desktop</h2>

Die Applikation stellt die Daten in vordefinierten Hauptansichten (Desktops) dar. Eine Hauptansicht enthält dabei mehrere
Teilansichten (Views), z.B. eine Tabelle, eine Detailansicht oder ein Bild, welche in vertikalen oder horizontalen Gruppen
angeordnet sind. Das Desktop Objekt mit seinen untergeordneten Gruppen und Views wurde mit dem Composite Design Pattern
modelliert.

<h2>Kommunikation zwischen Views (Event-Handling)</h2>

Jeder View-Controller teilt Veränderungen am Zustand des Objekts oder Aktionen, die von anderen Views verarbeitet
werden sollen, mittels Auslösen von Events mit. Gleichzeitig werden in den View-Controllern Events von anderen
View-Controllern registriert und verarbeitet.

<h2>Vererbung in Controller-Klassen</h2>

Alle DetailController und TableController Klassen erweitern die Basisklassen MainDetailController beziehungsweise MainTableController. Durch das Implementieren
der Grundfunktionalitäten in den beiden Basisklassen, etwa das Editieren und Speichern der Daten oder das Versenden von
Notifikationen, konnte die Funktionsweise der Controller harmonisiert und redundanter Sourcecode
vermieden werden. Die nötigen Typ-spezifischen Anpassungen beschränken sich hauptsächlich
auf die implementierten abstrakten bzw. überschriebenen Methoden.
