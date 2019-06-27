import org.scalatest.FunSuite
import GeneratingData.DataGenerator.{generateRefFile, generateTransactionsFile}
import org.scalatest.FunSuite
import ProcessingData.FilesUtils._
import GeneratingData.DataGenerator._
import ProcessingData._

import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

class ProcessingServiceTest extends FunSuite {

  test("test calcul des indicateurs") {

      val date = "20170514"
      val inPath = "./src/resources/input/"
      val outPath = "./src/resources/output/"
      val numOfDays = 1


      ProcessingService.process(date, inPath, outPath, numOfDays)


      assert()
    }


}
