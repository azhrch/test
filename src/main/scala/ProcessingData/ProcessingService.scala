package ProcessingData

import ProcessingData.FilesUtils._
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

object ProcessingService {

/* répond a tous les besoin, en passant numberOfdays 1 on obtient les 4 premiers besoins 1,2,3,4
  et si on passe 7 on obtient les 4 output 4,5,6,7 */
  def process(date: String, inPath: String,  outPath: String, numberOfDays : Int): Unit = {

    implicit val system = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer = ActorMaterializer(settings)

    var suffix: String = ""
    numberOfDays match {
      case 1 => suffix
      case 7 => suffix ="-J7"
      case _ => println("enter valid number of days : 1 or 7")
    }

  val a = concatFilesAkka(inPath, date, numberOfDays)
  a.onComplete(x => {
    var transactions = x.get.map(line => line.split('|')).toList
    var top100VenteGlobale: List[(String, Int)] = Nil
    var top100CaGlobale: List[(String, Float)] = Nil

    val groupedByMagasin = transactions.filter(x => x(3) != "0").groupBy(x => x(2)).foreach(x => {
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

        //get price by date, produit
        val priceReference = scala.io.Source.fromFile(inPath + "reference_prod-" + magasinId + "_" + date + ".data").
          getLines().map(line => line.split('|')).toList

        val price = priceReference.filter(x => x(0) == productId)(0)(1).toFloat
        val chiffreAffaire = price * qte

        (productId, qte, chiffreAffaire)

      })

      // top 100 vente pour le magasin en cours
      val top100VenteParMagasin = groupedByProd.map(x => (x._1, x._2)).sortBy(-_._2).take(100)
      //top 100 CA pour le magasin en cours
      val top100CaParMagasin = groupedByProd.map(x => (x._1, x._3)).sortBy(-_._2).take(100)

      export(top100VenteParMagasin, outPath + "top_100_ventes_" + magasinId + "_" + date + suffix + ".data")
      export(top100CaParMagasin, outPath + "top_100_ca_" + magasinId + "_" + date + suffix + ".data")

      //on ne concatine que les 100 premier de chaque magasin pour réduire les ligne traités après.
      top100VenteGlobale = top100VenteGlobale ++ top100VenteParMagasin
      top100CaGlobale = top100CaGlobale ++ top100CaParMagasin

    })

    val top100VenteGlobales = top100VenteGlobale.sortBy(-_._2).take(100)
    val top100CaGlobales = top100CaGlobale.sortBy(-_._2).take(100)

   // exporting top 100 vente global et top 100 ca global
   export(top100VenteGlobales, outPath + "top_100_vente_GLOBAL_" + date + suffix + ".data")
   export(top100CaGlobales, outPath + "top_100_ca_GLOBAL_" + date + suffix + ".data")

    //terminate akka actor.
    system.terminate()
    System.exit(0)

  })

  }
}
