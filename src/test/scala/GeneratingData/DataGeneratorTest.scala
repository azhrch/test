package GeneratingData

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import GeneratingData.DataGenerator.{generateRefFile, generateTransactionsFile, _}
import ProcessingData.FilesUtils._
import org.scalatest.FunSuite
import scala.concurrent.ExecutionContext.Implicits.global


class DataGeneratorTest extends FunSuite {

  //test la fonction generation des donnes
  test("test generation des données") {

    val path = "./src/resources/output/"
    val date = "20160511"
    val numberOfDays = 3
    val transLinesNumber = 1000
    val reflinesNumber = 1000
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val runningDay = LocalDate.parse(date, formatter)

    var i = 0

    while ( {
      i < numberOfDays
    }) {
      val date = runningDay.minusDays(i).toString.replace("-", "")
      generateTransactionsFile(path + "/transactions_" + date + ".data", date, transLinesNumber)
      generateRefFile(path + "/reference_prod-" + generateRandomIdMagasin + "_" + date + ".data", reflinesNumber)

      {
        i += 1;
        i - 1
      }
    }

    while ( {
      i < numberOfDays
    }) {
      val date = runningDay.minusDays(i).toString.replace("-", "")
      val transactionsStream = readStream(path + "/transactions_" + date + ".data", date)
      val refStream = readStream(path + "/reference_prod-" + generateRandomIdMagasin + "_" + date + ".data", date)

      var transactions: List[scala.Array[String]] = Nil
      var refs: List[scala.Array[String]] = Nil

      transactionsStream.onComplete(x => {
        transactions = x.get.map(line => line.split('|')).toList
        assert(transactions.length == transLinesNumber)
      })

      refStream.onComplete(x => {
        refs = x.get.map(line => line.split('|')).toList
        assert(refs.length == reflinesNumber)
      })

      {
        i += 1;
        i - 1
      }
    }


  }
}