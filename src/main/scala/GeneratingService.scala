import java.util.{Date, Random}
import java.text.SimpleDateFormat
import java.io.{File, FileNotFoundException, PrintWriter}
import java.util.Random

object GeneratingService {

  def main(args: scala.Array[String]): Unit = {

    println(generateRandomDate(13800))
    writeRefFile("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/a.txt")

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
    generateRandomString(8) + "-" + generateRandomString(5) + "-" + generateRandomString(4) + "-" + generateRandomString(4) + "-" + generateRandomString(12)
  }

  //generate a random date
  def generateRandomDate(n: Int): String = {
    val simple = new SimpleDateFormat("yyyyMMdd'T'HHmmssSSSZ")
    val result = new Date(System.currentTimeMillis+n*5445)
    simple.format(result)
  }



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
  def writeRefFile(path: String): Unit = {
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
