import java.time.LocalDate
import java.time.format.DateTimeFormatter

import phenix.GeneratingService.{generateRefFile, generateTransactionsFile}
import org.scalatest.FunSuite
import phenix.FilesUtils._
import phenix.GeneratingService._
import phenix.ProcessingService.process

import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global


class FilesUtilsTest extends FunSuite  {

  //test la fonction generation des donnes
  test("test generation des données") {

    val path = "./src/resources/output/"
    val date = "20160512"
    val numberOfDays =  3
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
      generateRefFile(path + "/reference_prod-" + generateRandomIdMagasin + "_" + date + ".data" , reflinesNumber)

      {
        i += 1; i - 1
      }
    }

    while ( {
      i < numberOfDays
    }) {
      val date = runningDay.minusDays(i).toString.replace("-", "")
      val transactionsStream = readStream(path + "/transactions_" + date + ".data", date)
      val refStream = readStream(path + "/reference_prod-" + generateRandomIdMagasin + "_" + date + ".data" , date)

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
        i += 1; i - 1
      }
    }



  }


  test("test calcul des indicateurs") {

    val date = "20170514"
    val inPath = "./src/resources/input/"
    val outPath = "./src/resources/output/"
    val numOfDays = 1


    process(date, inPath, outPath, numOfDays)


    assert()
  }


























  //test la fonction readStream
   test("test readStream") {
    val stream = readStream("./src/resources/input/", "20170514")

     var result: List[scala.Array[String]] = Nil
     stream.onComplete(x => {
       result = x.get.map(line => line.split('|')).toList
       assert(result.length == 45906)
     })
  }


  test("test export") {
   val list : List[(String, Any)]= List(("aziz","herch"), ("lansrod", "bigdata"), ("test", "export"))
    export(list, "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/testExport")
       val exportedFile : List[scala.Array[String]] = scala.io.Source.fromFile("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/testExport")
      .getLines().map(line => line.split('|')).toList
    assert(exportedFile.length == 3)
  }








}
