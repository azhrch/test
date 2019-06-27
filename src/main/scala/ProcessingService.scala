package phenix

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import phenix.FilesUtils._

import scala.concurrent.{Await, ExecutionContext}
import ExecutionContext.Implicits.global


object ProcessingService {

  //traitement par date
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
    //val transactions: List[scala.Array[String]] = concatFiles(inPath,date, numberOfDays-1)


  val a = concatFilesAkka(inPath, date, numberOfDays)
  a.onComplete(x => {
    var transactions = x.get.map(line => line.split('|')).toList
    var top100VenteGlobale: List[(String, Int)] = Nil
    var top100CaGlobale: List[(String, Float)] = Nil

    //should filter otherwise outofbound exeption, there is no product id 0 in product price files
    // however transactions contain transactions with product id 0 , weird..
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

    top100VenteGlobale.sortBy(-_._2).take(100)
    top100CaGlobale.sortBy(-_._2).take(100)

   // exporting top 100 vente global et top 100 ca global
   export(top100VenteGlobale, outPath + "top_100_vente_GLOBAL_" + date + suffix + ".data")
   export(top100CaGlobale, outPath + "top_100_ca_GLOBAL_" + date + suffix + ".data")

  })
    //terminate akka actor.
    system.terminate()

  }
}
