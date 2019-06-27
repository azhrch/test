<h1>Phenix project</h1>

<h2>Environnement</h2>
Scala 2.12.0<br>
sbt 1.2.8<br>
IntelliJ<br>

<h2>Lancer le Jar</h2
  <b>Pour génerer des données</b>
  scala -cp phenix-challenge-assembly-0.1.jar GeneratingData.DataGenerator <outPutPath> <date> <numberOfDays> <transLinesNumber> <reflinesNumber> <br>
  ou <br>
  <b> outPutPath : Path ou les fichiers générer vont se trouver.</b><br>
  <b> date </b> : date à laquelle on veux génerer des données. Au Format YYYYMMDD<br>

  





Simulation des contraintes

il faux indiquer un pourcentage des thread disponibles.
cpulimit -l 25 sbt run &

scala -J-Xmx512Mo
