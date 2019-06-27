<h1>Phenix project</h1>

<h2>Environnement</h2>
Scala 2.12.0<br>
sbt 1.2.8<br>
IntelliJ<br>

<h2>Lancer le Jar</h2
  <h2>1.Pour génerer des données</h2>
  Utile pour génerer un grand volume de données afin de tester la performance de l'application avec un grand volume de données.
 <br> <b>scala -cp phenix-challenge-assembly-0.1.jar GeneratingData.DataGenerator [outPutPath] [date] [numberOfDays] </b>  <b>[transLinesNumber] [reflinesNumber] </b><br>
  <b>ou </b><br>
  <b> outPutPath : </b>Path ou les fichiers générer vont se trouver.</b><br>
  <b> date : </b>date à laquelle on veux génerer des données. Au Format YYYYMMDD<br>
  <b>numberOfDays :</b> nombre de jours pour les quelles on veux génerer des données aléatoire.<br> 
  <b>transLinesNumber :</b> nombre de ligne qu'on souhaite génerer dans les fichiers de transactions.<br>
  <b>reflinesNumber :</b> nombre de ligne qu'on souhaite génerer dans les fichiers de réferences.<br>

  <h2>1.Pour traiter les données et calculer les indicateur</h2>
<br> <b>scala -cp phenix-challenge-assembly-0.1.jar ProcessingData.DataProcessor [date] [numberOfDays] [inPutPath] [outPutPath] </b><br>
<b>ou </b><br>
  <b> date : </b>date à laquelle on veux traiter des données. Au Format YYYYMMDD<br>
  <b>numberOfDays :</b> nombre de jours pour les quelles on veux traiter les données.Soit 1 ou 7<br>
  <b> inPutPath : </b>Path ou se trouvent les fichiers qu'on veux traiter.</b><br>
  <b> outPutPath : </b>Path ou les résultats des calculs seront exportés</b><br>
  
DataProcessor génère les fichiers si numberOfDays passé est 1<br><br>

top_100_ventes_<ID_MAGASIN>_YYYYMMDD.data<br>
top_100_ventes_GLOBAL_YYYYMMDD.data<br>
top_100_ca_<ID_MAGASIN>_YYYYMMDD.data<br>
top_100_ca_GLOBAL_YYYYMMDD.data<br>
<br>
et les fichiers si numberOfDays passé est 7.<br>

top_100_ventes_<ID_MAGASIN>_YYYYMMDD-J7.data<br>
top_100_ventes_GLOBAL_YYYYMMDD-J7.data<br>
top_100_ca_<ID_MAGASIN>_YYYYMMDD-J7.data<br>
top_100_ca_GLOBAL_YYYYMMDD-J7.data<br>




Simulation des contraintes

il faux indiquer un pourcentage des thread disponibles.
cpulimit -l 25 sbt run &

scala -J-Xmx512Mo
