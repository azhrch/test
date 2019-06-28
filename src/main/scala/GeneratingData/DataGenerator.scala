package GeneratingData

import java.io.{File, FileNotFoundException, PrintWriter}
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.{Date, Random}

object DataGenerator {

  def main(args: scala.Array[String]): Unit = {

    val path = args(0) //  ".src/resources/output/"
    val date =  args(1) //"20160512"
    val numberOfDays = args(2).toInt // 3
    val transLinesNumber = args(3).toInt //1000
    val reflinesNumber = args(4).toInt //1000

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
  }

  // generate a random alphanumeric
  private val ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvxyzw0123456789"
  def randomAlphaNumeric(length: Int): String = {
    val builder = new StringBuilder
    var i = 0
    while ( {
      i < length
    }) {
      {
        i += 1;
        i - 1
      }
      {
        val character = (Math.random * ALPHA_NUMERIC_STRING.length).toInt
        builder.append(ALPHA_NUMERIC_STRING.charAt(character))
      }
    }
    builder.toString
  }

  //generate random Id magasin.
  def generateRandomIdMagasin: String = {
    randomAlphaNumeric(8) + "-" + randomAlphaNumeric(5) + "-" + randomAlphaNumeric(4) + "-" + randomAlphaNumeric(4) + "-" + randomAlphaNumeric(12)
  }

  //generate a random date
  def generateRandomDate(n: Int): String = {
    val simple = new SimpleDateFormat("yyyyMMdd'T'HHmmssSSSZ")
    val result = new Date(System.currentTimeMillis+n*5445)
    simple.format(result)
  }

 //generate transaction files
  @throws[FileNotFoundException]
  def generateTransactionsFile(path: String, date: String, linesNumber : Int): Unit = {
    val writer = new PrintWriter(new File(path))
    var i = 0
    while ( {
      i < linesNumber
    }) {
      val rand = new Random
      val transId = Math.abs(rand.nextInt(100))
      val n = Math.abs(rand.nextInt(100))
      val randomTime =  generateRandomDate(n)
      val datetime = date + randomTime.substring(8,randomTime.toString.length)
      val magasinId = generateRandomIdMagasin
      val produitId = Math.abs(rand.nextInt(100))
      val qte = Math.abs(rand.nextInt(100))
      writer.write(transId + "|" + datetime + "|" + magasinId + "|" + produitId + "|" + qte + "\n")

      {
        i += 1; i - 1
      }
    }
    writer.close()
  }

  @throws[FileNotFoundException]
  def generateRefFile(path: String, linesNumber : Int): Unit = {
    val writer = new PrintWriter(new File(path))
    var i = 0
    while ( {
      i < linesNumber
    }) {
      val rand = new Random
      val produit = Math.abs(rand.nextInt(100))
      val prix = "%05.2f".format(rand.nextFloat * 100 ) + ""
      writer.write(produit + "|" + prix.substring(0, 5) + "\n")

      {
        i += 1; i - 1
      }
    }
    writer.close()
  }

}
