package ProcessingData
import ProcessingService._

object DataProcessor {

  def main(args: scala.Array[String]) {

    val date =  args(0) //"20170514"
    val numOfDays = args(1).toInt //7
    val inPath = args(2) //"/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/input/"
    val outPath =args(3) // "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/"

    process(date, inPath, outPath, numOfDays)

  }
}
