import java.util.{Date, Random}
import java.text.DateFormat
import java.text.SimpleDateFormat



object GeneratingService {
  def main(args: scala.Array[String]): Unit = {

    println(generateRandomDate(13800))

  }

 // generate random string of a specified length
  def generateRandomString(length: Int): String = {
    val leftLimit = 97
    val rightLimit = 122
    val random = new Random
    val buffer = new StringBuilder(length)
    var i = 0
    while ( {
      i < length
    }) {
      val randomLimitedInt = leftLimit + (random.nextFloat * (rightLimit - leftLimit + 1)).toInt
      buffer.append(randomLimitedInt.toChar)

      {
        i += 1; i - 1
      }
    }
    val generatedString = buffer.toString
    generatedString
  }



//generate random Id magasin.
  def generateRandomIdMagasin: String = {
    generateRandomString(8) + "-" + generateRandomString(4) + "-" + generateRandomString(4) + "-" + generateRandomString(4) + "-" + generateRandomString(12)
  }



  def generateRandomDate(n: Int): String = {
    val simple = new SimpleDateFormat("yyyyMMdd HHmmssSSSZ")
    val result = new Date(System.currentTimeMillis+n*5445)
    simple.format(result)
  }






}
