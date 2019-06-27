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

 /*   val a = readStream("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/input/", "20170514")
    var aa: List[scala.Array[String]] = Nil
    a.onComplete(x => {
      aa = x.get.map(line => line.split('|')).toList

      println(aa.length)
    })
*/
/*

   val a =  concatFilesAkka(inPath,date,1)
    a.onComplete(x => {
      var aa = x.get.map(line => line.split('|')).toList

      println(aa.length)
    })
*/

  }
}