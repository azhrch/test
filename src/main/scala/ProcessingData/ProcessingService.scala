package ProcessingData

import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import ProcessingData.FilesUtils._
import akka.actor.ActorSystem

object ProcessingService {

  /** Répondre à tous les besoin:
    * - En passant numberOfdays 1 on obtient les 4 premiers besoins 1,2,3,4
    * - En passant numberOfdays 7 on obtient les 4 derniers besoins 4,5,6,7
    *
    * @param date         la date limite du calcul
    * @param inPath       le dossier contenant les données en entrée
    * @param outPath      le dossier du résultat
    * @param numberOfDays le nombre de jours sur lequel on va effectuer le calcul
    */
  def process(date: String, inPath: String, outPath: String, numberOfDays: Int): Unit = {

    implicit val system: ActorSystem = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer: ActorMaterializer = ActorMaterializer(settings)

    var suffix: String = ""
    numberOfDays match {
      case 1 => suffix
      case 7 => suffix = "-J7"
      case _ => println("enter valid number of days : 1 or 7")
    }

    val result = concatFiles(inPath, date, numberOfDays)
    result.onComplete(x => {
      var transactions = x.get.map(line => line.split('|')).toList
      var top100VenteGlobale: List[(String, Int)] = Nil
      var top100CaGlobale: List[(String, Float)] = Nil

      val groupedByMagasin: Unit = transactions
        .filter(x => x(3) != "0")
        .groupBy(x => x(2))
        .foreach(x => {
          val magasinId = x._1
          val groupedByMagVal = x._2
          val groupedByProd = groupedByMagVal.groupBy(x => x(3)).toList.map(x => {
            val productId = x._1
            val groupedByProdVal = x._2
            var qte: Int = 0
            var date = ""

            groupedByProdVal.foreach(x => {
              qte += x(4).toInt
              date = x(1).slice(0, 8)
            })

            // Récuperer le prix par date et par produit
            val priceReference = scala.io.Source.fromFile(inPath + "reference_prod-" + magasinId + "_" + date + ".data").
              getLines().map(line => line.split('|')).toList

            val price = priceReference.filter(x => x(0) == productId)(0)(1).toFloat
            val chiffreAffaire = price * qte

            (productId, qte, chiffreAffaire)

          })

          // Calculer les top 100 vente pour un magasin
          val top100VenteParMagasin = groupedByProd.map(x => (x._1, x._2)).sortBy(-_._2).take(100)

          // Calculer les top 100 CA pour un magasin
          val top100CaParMagasin = groupedByProd.map(x => (x._1, x._3)).sortBy(-_._2).take(100)

          export(top100VenteParMagasin, outPath + "top_100_ventes_" + magasinId + "_" + date + suffix + ".data")
          export(top100CaParMagasin, outPath + "top_100_ca_" + magasinId + "_" + date + suffix + ".data")

          // Concatiner seulement les 100 premiers de chaque magasin pour réduire les ligne traitées.
          top100VenteGlobale = top100VenteGlobale ++ top100VenteParMagasin
          top100CaGlobale = top100CaGlobale ++ top100CaParMagasin

        })

      val top100VenteGlobales = top100VenteGlobale.sortBy(-_._2).take(100)
      val top100CaGlobales = top100CaGlobale.sortBy(-_._2).take(100)

      // Exporter les top 100 ventes global et top 100 ca global
      export(top100VenteGlobales, outPath + "top_100_vente_GLOBAL_" + date + suffix + ".data")
      export(top100CaGlobales, outPath + "top_100_ca_GLOBAL_" + date + suffix + ".data")

      // Terminer Akka actor.
      system.terminate()
      System.exit(0)
    })
  }
}
