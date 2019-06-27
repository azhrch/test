package ProcessingData
import ProcessingService._

object Main {

  def main(args: scala.Array[String]) {

    val date = "20170514" //args(0)
    val numOfDays = 7 //args(1)
    val inPath = "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/input/" //args(2)
    val outPath = "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/" //args(3)


    process(date, inPath, outPath, numOfDays)

  }
}
