<h1>Phenix project</h1>

<h2>Environnement</h2>
Scala 2.12.0<br>
sbt 1.2.8<br>
IntelliJ 2018.3.2 CE<br>

<h2>Librairies</h2>
"akka-stream" version compatible avec scala 2.12.0 : Utilisé pours gérer la lecture des fichiers énormes.<br>
"scalatest" : utilisé pour les tests unitaires.<br>

<h2>Pour générer la documentation de l'API Scaladoc</h2>

  ```
  $ sbt doc
  
```
Cela génère un fichier racine index.html et d’autres fichiers associés pour la documentation sous le target répertoire du projet. Avec Scala 2.12 et SBT 1.2.8, le fichier racine se trouve dans target/scala-2.12/api/index.html .
<h2>Pour compiler le code</h2>

  ```
  $ sbt compile
  
```
<h2>Pour packager le code</h2>

  ```
  $ sbt assembly
  
```

Cela génère un fat jar avec la dépendance "akka-stream" y incluse.<br>

<h2>Lancer le Jar</h2>
  <h2>1. Pour génerer des données</h2>
  Utile pour génerer un grand volume de données afin de tester la capacité de l'application à traiter une grande volumeterie.
  
  ```
$ scala -cp phenix-challenge-assembly-0.1.jar GeneratingData.DataGenerator <outPutPath> <date> <numberOfDays> <transLinesNumber> <refLinesNumber> 

```



  <b>-</b> outPutPath : Path ou les fichiers générer vont se trouver.</b><br>
  <b>-</b> date : date à partir de laquelle on veux génerer des données. Format : YYYYMMDD<br>
  <b>-</b> numberOfDays : nombre de jours pour les quelles on veux génerer des données aléatoire.<br> 
  <b>-</b> transLinesNumber : nombre de ligne qu'on souhaite génerer dans les fichiers de transactions.<br>
  <b>-</b> refLinesNumber : nombre de ligne qu'on souhaite génerer dans les fichiers de réferences.<br>

  <h2>2. Pour traiter les données et calculer les indicateurs</h2>

  ```
$ scala -cp phenix-challenge-assembly-0.1.jar ProcessingData.DataProcessor <date> <numberOfDays> <inPutPath> <outPutPath>

```
<br>
  <b>-</b> date : date à laquelle on veux traiter des données. Au Format YYYYMMDD<br>
  <b>-</b> numberOfDays : nombre de jours pour les quelles on veux traiter les données.Soit 1 ou 7<br>
  <b>-</b>  inPutPath : Path ou se trouvent les fichiers qu'on veux traiter.</b><br>
  <b>-</b>  outPutPath : Path ou les résultats des calculs seront exportés</b><br><br>
  
Si numberOfDays passé est "1" DataProcessor génère les fichiers suivants :<br>

top_100_ventes_<ID_MAGASIN>_YYYYMMDD.data<br>
top_100_ventes_GLOBAL_YYYYMMDD.data<br>
top_100_ca_<ID_MAGASIN>_YYYYMMDD.data<br>
top_100_ca_GLOBAL_YYYYMMDD.data<br>
<br>
Si numberOfDays passé est "7" DataProcessor génère les fichiers suivants :<br>

top_100_ventes_<ID_MAGASIN>_YYYYMMDD-J7.data<br>
top_100_ventes_GLOBAL_YYYYMMDD-J7.data<br>
top_100_ca_<ID_MAGASIN>_YYYYMMDD-J7.data<br>
top_100_ca_GLOBAL_YYYYMMDD-J7.data<br>



