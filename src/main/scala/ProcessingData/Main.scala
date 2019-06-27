package ProcessingData
import ProcessingService._

import java.io.{File, FileNotFoundException, PrintWriter}
import java.nio.file.Paths


object Main {

  def main(args: scala.Array[String]) {

    val date = "20170514"
    val inPath = "./src/resources/input/"
    val outPath = "./src/resources/output/"
    val numOfDays = 1


    //process(date, inPath, outPath, numOfDays)

    val dir = new File("./src/resources/output/")
    val files = dir.listFiles((d, name) => name.endsWith("20170514.data")).map(_.toString())
    files.foreach(println)
  }
}
