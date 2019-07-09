package GeneratingData

import java.io.{File, PrintWriter}
import java.text.SimpleDateFormat
import java.util.{Date, Random}

object GeneratorUtils {

  private val ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvxyzw0123456789"

  /** Générer un alphanumérique aléatoire
    *
    * @param length La longueur de l'alphanumérique
    * @return un alphanumérique aléatoire de longeur length
    */
  def randomAlphaNumeric(length: Int): String = {
    val builder = new StringBuilder
    var i = 0
    while (i < length) {
      i += 1
      val character = (Math.random * ALPHA_NUMERIC_STRING.length).toInt
      builder.append(ALPHA_NUMERIC_STRING.charAt(character))
    }
    builder.toString
  }


  /** Générer un id magasin alphanumérique aléatoire.
    */
  def generateRandomIdMagasin: String = {
    randomAlphaNumeric(8) + "-" +
      randomAlphaNumeric(5) + "-" +
      randomAlphaNumeric(4) + "-" +
      randomAlphaNumeric(4) + "-" +
      randomAlphaNumeric(12)
  }

  /** Générer une date aléatoire
    *
    * @param n une valeur utilisée pour changer la date
    * @return une date aléatoire
    */
  def generateRandomDate(n: Int): String = {
    val simple = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ")
    val result = new Date(System.currentTimeMillis + n * 5445)
    simple.format(result)
  }

  /** Générer un fichier de transactions
    *
    * @param path        la destination du fichier
    * @param date        la date des transactions
    * @param linesNumber le nombre des lignes dans le fichier
    *
    */

  def generateTransactionsFile(path: String, date: String, linesNumber: Int) {
    val writer = new PrintWriter(new File(path))
    var i = 0
    while (i < linesNumber) {
      val rand = new Random
      val transId = Math.abs(rand.nextInt(100))
      val n = Math.abs(rand.nextInt(100))
      val randomTime = generateRandomDate(n)
      val datetime = date + randomTime.substring(8, randomTime.toString.length)
      val magasinId = generateRandomIdMagasin
      val produitId = Math.abs(rand.nextInt(100))
      val qte = Math.abs(rand.nextInt(100))
      writer.write(transId + "|" + datetime + "|" + magasinId + "|" + produitId + "|" + qte + "\n")
      i += 1
    }
    writer.close()
  }

  /** Générer un fichier de references
    *
    * @param path        la destination du fichier
    * @param linesNumber le nombre des lignes dans le fichier
    *
    */

  def generateRefFile(path: String, linesNumber: Int) {
    val writer = new PrintWriter(new File(path))
    var i = 0
    while (i < linesNumber) {
      val rand = new Random
      val produit = Math.abs(rand.nextInt(100))
      val prix = "%05.2f".format(rand.nextFloat * 100) + ""
      writer.write(produit + "|" + prix.substring(0, 5) + "\n")
      i += 1
    }
    writer.close()
  }
}
