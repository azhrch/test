<h1>Phenix project</h1>

<h2>Environnement</h2>
Scala 2.12.0<br>
sbt 1.2.8<br>
IntelliJ<br>

<h2>Lancer le Jar</h2
  <h2>1.Pour génerer des données<h2>
  Utile pour génerer un grand volume de données afin de tester la performance de l'application avec un grand volume de données.
  <b>scala -cp phenix-challenge-assembly-0.1.jar GeneratingData.DataGenerator <outPutPath> <date> <numberOfDays> </b>  <b><transLinesNumber> <reflinesNumber> </b><br>
  <b>ou </b><br>
  <b> outPutPath : </b>Path ou les fichiers générer vont se trouver.</b><br>
  <b> date : </b>date à laquelle on veux génerer des données. Au Format YYYYMMDD<br>
  <b>numberOfDays :</b> nombre de jours pour les quelles on veux génerer des données aléatoire. 
  <b>transLinesNumber :</b> nombre de ligne qu'on souhaite génerer dans les fichiers de transactions.
  <b>reflinesNumber :</b> nombre de ligne qu'on souhaite génerer dans les fichiers de réferences.

  





Simulation des contraintes

il faux indiquer un pourcentage des thread disponibles.
cpulimit -l 25 sbt run &

scala -J-Xmx512Mo
