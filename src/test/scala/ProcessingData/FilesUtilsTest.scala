package ProcessingData

import ProcessingData.FilesUtils._
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import org.scalatest.FunSuite
import scala.concurrent.ExecutionContext.Implicits.global

class FilesUtilsTest extends FunSuite {


  test(testName = "Test readStream") {

    val stream = readStream("./src/resources/input/", "20170514")
    var result: List[scala.Array[String]] = Nil
    stream.onComplete(x => {
      result = x.get.map(line => line.split('|')).toList
      assert(result.length == 45906)
    })
  }

  test(testName = "Test exportation des fichiers") {

    val list: List[(String, Any)] = List(("v1", "v2"), ("v3", "v4"), ("v5", "v6"))
    export(list, "./src/resources/output/testExport")
    val exportedFile: List[scala.Array[String]] = scala.io.Source.fromFile("./src/resources/output/testExport")
      .getLines().map(line => line.split('|')).toList
    assert(exportedFile.length == 3)
  }

  test(testName = "Test concatFiles") {

    implicit val system: ActorSystem = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer: ActorMaterializer = ActorMaterializer(settings)

    val result = concatFiles("./src/resources/input/", "20170514", 2)
    result.onComplete(x => {
      var transactions = x.get.map(line => line.split('|')).toList
      assert(transactions.length == 91812)

    })
  }

}
