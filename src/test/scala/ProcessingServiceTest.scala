
import java.io.File

import ProcessingData.FilesUtils.export
import org.scalatest.FunSuite
import ProcessingData.ProcessingService._

import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

class ProcessingServiceTest extends FunSuite {

  test("test calcul des indicateurs") {

      val date = "20170514"
      val inPath = "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/input/"
      val outPath = "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/"
      val numOfDays = 1


      process(date, inPath, outPath, numOfDays)


      val dir = new File(outPath)
      val files = dir.listFiles((d, name) => name.endsWith(date+".data")).map(_.toString())
      files.foreach(println)



println("ok")
assert (true)
    }


}
