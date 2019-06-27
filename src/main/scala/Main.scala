package phenix

import phenix.ProcessingService._
import phenix.FilesUtils._
import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global

object Main {

  def main(args: scala.Array[String]) {

       val date = "20170514" //args(0)
    val inPath = "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/input/" //args(1)
    val outPath = "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/" //args(2)

    // last 7 days transactions
    process(date, inPath, outPath, 1)
   // process(date, inPath, outPath, 7)



  }
}