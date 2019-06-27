import java.util.{Date, Random}
import java.text.SimpleDateFormat
import java.io.{File, FileNotFoundException, PrintWriter}
import java.util.Random

object GeneratingService {

  def main(args: scala.Array[String]): Unit = {

    println(randomAlphaNumeric(10))
    generateTransactionsFile("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/azeze.txt")

    import java.time.LocalDate
    import java.time.format.DateTimeFormatter
    val path = args(0)

    val dateS = args(1)

    val nbOfDay = args(2).toInt

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val runDay = LocalDate.parse(dateS, formatter)
    var i = 0
    while ( {
      i < nbOfDay
    }) {
      val date = runDay.minusDays(i).toString.replace("-", "")
      generateTransactionsFile(path + "/transactions_" + date + ".data")
      generateRefFile(path + "/reference_prod-" + generateRandomIdMagasin + "_" + date + ".data")

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
  def generateTransactionsFile(path: String): Unit = {
    val writer = new PrintWriter(new File(path))
    var i = 0
    while ( {
      i < 100000
    }) {
      val rand = new Random
      val transId = Math.abs(rand.nextInt(100))
      val datetime = generateRandomDate(transId)
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
  def generateRefFile(path: String): Unit = {
    val writer = new PrintWriter(new File(path))
    var i = 0
    while ( {
      i < 100000
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
