package ProcessingData
import ProcessingService._

object DataProcessor {

  def main(args: scala.Array[String]) {

    val date =  args(0)
    val numOfDays = args(1).toInt
    val inPath = args(2)
    val outPath =args(3)

    process(date, inPath, outPath, numOfDays)

  }
}
