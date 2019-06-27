import java.util.{Date, Random}
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.io.{File, FileNotFoundException, PrintWriter}



object GeneratingService {
  def main(args: scala.Array[String]): Unit = {

    println(generateRandomDate(13800))
    writeRefFile("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/new3dssdssss3g2g.txt")

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



  def generateRandomDate(n: Int): String = {
    val simple = new SimpleDateFormat("yyyyMMdd'T'HHmmssSSSZ")
    val result = new Date(System.currentTimeMillis+n*5445)
    simple.format(result)
  }


  import java.io.FileNotFoundException
  import java.io.PrintWriter
  import java.util.Random

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


  import java.io.FileNotFoundException
  import java.io.PrintWriter
  import java.util.Random

  @throws[FileNotFoundException]
  def writeRefFile(path: String): Unit = {
    val writer = new PrintWriter(new File(path))
    var i = 0
    while ( {
      i < 100000
    }) {
      val r = new Random
      val produit = Math.abs(r.nextInt(100))
      val prix = 00.01+ r.nextFloat * 100 + ""
      writer.write(produit + "|" + prix.substring(0, 5) + "\n")

      {
        i += 1; i - 1
      }
    }
    writer.close()
  }



}
