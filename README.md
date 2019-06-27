<h1>Phenix project</h1>

<h2>Environnement</h2>
Scala 2.12.0
sbt 1.2.8
IntelliJ

<h2>Lancer le Jar</h2
  <h3>Pour génerer des données</h3>
  scala -cp phenix-challenge-assembly-0.1.jar GeneratingData.DataGenerator <outPutPath> <date> <numberOfDays> <transLinesNumber> <reflinesNumber>

  





Simulation des contraintes

il faux indiquer un pourcentage des thread disponibles.
cpulimit -l 25 sbt run &

scala -J-Xmx512Mo
