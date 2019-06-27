
import java.io.File

import org.scalatest.FunSuite
import ProcessingData.ProcessingService._
import scala.io.Source

class ProcessingServiceTest extends FunSuite {

  test("test calcul des indicateurs") {

      val date = "20170514"
      val inPath = "./src/resources/input/"
      val outPath = "./src/resources/output/"


      process(date, inPath, outPath, 1)
      process(date, inPath, outPath, 7)



    //liste des fichiers produites par process
      val dir = new File(outPath)
      val files1 = dir.listFiles((d, name) => name.endsWith(date+".data")).map(_.toString())
      val files7 = dir.listFiles((d, name) => name.endsWith(date+"-J7.data")).map(_.toString())
      val files = files1 ++ files7


       for (file <- files) {
          val list = Source.fromFile(file)

          val rows = list.getLines().map(line => line.split('|')).toList
          assert (rows.length == 100)

      }

    }

}
