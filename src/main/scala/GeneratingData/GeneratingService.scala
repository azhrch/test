package GeneratingData

import java.io.{File, FileNotFoundException, PrintWriter}
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.{Date, Random}

object GeneratingService {


  //a supprimer , mettre toute la classe dans un autre package.
  def main(args: scala.Array[String]): Unit = {

    val path = "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/" //args(0)
    val date = "20160512" //args(1)
    val numberOfDays =  3 //args(2).toInt
    val transLinesNumber = 1000 //args(3).toInt
    val reflinesNumber = 1000  //args(4).toInt
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
      //
      val prix = "%05.2f".format(rand.nextFloat * 100 ) + ""
      writer.write(produit + "|" + prix.substring(0, 5) + "\n")

      {
        i += 1; i - 1
      }
    }
    writer.close()
  }

}
