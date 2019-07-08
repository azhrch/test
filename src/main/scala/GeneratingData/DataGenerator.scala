package GeneratingData

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import GeneratingData.GeneratorUtils._

object DataGenerator {

  def main(args: scala.Array[String]): Unit = {

    val path = args(0)
    val date = args(1)
    val numberOfDays = args(2).toInt
    val transLinesNumber = args(3).toInt
    val refLinesNumber = args(4).toInt
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val runningDay = LocalDate.parse(date, formatter)

    var i = 0

    while (i < numberOfDays) {
      val date = runningDay
        .minusDays(i)
        .toString
        .replace("-", "")

      generateTransactionsFile(path + "/transactions_" + date + ".data", date, transLinesNumber)
      generateRefFile(path + "/reference_prod-" + generateRandomIdMagasin + "_" + date + ".data", refLinesNumber)
      i += 1
    }
  }
}
