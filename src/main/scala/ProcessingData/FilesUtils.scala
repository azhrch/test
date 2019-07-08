package ProcessingData

import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.stream.scaladsl.{FileIO, Framing, Keep, Sink}
import java.io.{File, PrintWriter}
import java.time.format.DateTimeFormatter
import akka.stream.scaladsl.Source
import scala.concurrent.{ExecutionContext, Future}
import akka.actor.ActorSystem
import akka.util.ByteString
import java.nio.file.{NoSuchFileException, Paths}
import java.time.LocalDate
import ExecutionContext.Implicits.global

object FilesUtils {

  implicit val system : ActorSystem = ActorSystem("Sys")
  val settings = ActorMaterializerSettings(system)
  implicit val materializer : ActorMaterializer = ActorMaterializer(settings)

  /** Lire un seul fichier
    *
    * @param path l'arborescence du fichier
    * @param date la date utilisée
    * @return un future de type sequence se string.
    */
  def readStream(path: String, date: String): Future[Seq[String]] = {

    val result: Future[Seq[String]] =
      FileIO.fromPath(Paths.get(path + "transactions_" + date + ".data"))
        .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true))
        .map(_.utf8String)
        .toMat(Sink.seq)(Keep.right)
        .run()
        .recover {
          case _: NoSuchFileException => println("File not found")
            Seq.empty[String]
        }
    result
  }

  /** Concatiner plusieurs fichiers en entrée
    *  - utile pour concatiner les transaction des 7 derniers jours
    *  - utilise akka Streams pour manipuler des fichiers de grande taille
    *
    * @param path l'arborescence de l'input
    * @param date la dernière date du calcul
    * @param numberOfDays le nombre des jours
    * @return un future de type sequence se string
    */

  def concatFiles(path : String, date : String, numberOfDays : Int) : Future[Seq[String]] = {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val formattedDate = LocalDate.parse(date, formatter)

    /** Générer la liste des fichiers qu'on veux concatiner
      */
    def files : List[String] = {

      var filesList = List[String]()
      var c = 0
      while( c < numberOfDays) {
        val date = formattedDate.minusDays(c)
          .toString
          .replace("-", "")

        val filePath: String = path + "transactions_" + date + ".data"
        filesList = filesList ++ List(filePath)
        c = c+1
      }
      filesList
    }

    val result = Source(files).flatMapConcat(filename =>
      FileIO.fromPath(Paths.get(filename))
        .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true).map(_.utf8String))
    ).toMat(Sink.seq)(Keep.right)
      .run()
      .recover {
        case _: NoSuchFileException => println("File not found")
          Seq.empty[String]
      }
    result
  }

  /** Exporte une liste dans un fichier au path donné.
    *
    * @param list la liste à inserer dans le fichier
    * @param path l'arborescence du fichier d'output
    */
    def export(list: List[(String, Any)], path: String) {
      val writer = new PrintWriter(new File(path))
      list.foreach(x => writer.write(x._1 + "|" + x._2 + "\n"))
      writer.close()
    }
}
