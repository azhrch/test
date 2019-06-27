package ProcessingData

import java.io.{File, FileNotFoundException, PrintWriter}
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.stream.scaladsl.{FileIO, Framing, Keep, Sink}
import akka.util.ByteString
import akka.stream.scaladsl.Source
import scala.concurrent.Future

object FilesUtils {

  //lecture d'un seul fichier avec akka streams.
  def readStream(path: String, date: String): Future[Seq[String]] = {

    implicit val system = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer = ActorMaterializer(settings)

    val result: Future[Seq[String]] =
      FileIO.fromPath(Paths.get(path + "transactions_" + date + ".data"))
        .via(Framing.delimiter(ByteString("\n"), 256, true))
        .map(_.utf8String)
        .toMat(Sink.seq)(Keep.right)
        .run()

    result
  }


  //concatine plusieurs fichier en entrée et retourne un future de type sequence se string.
  // utile pour concatiner les transaction des 7 derniers jours
  //utilise akka Streams pour manipuler des fichiers de grande taille.
  @throws[FileNotFoundException]
  def concatFilesAkka(path : String, date : String, numberOfDays : Int) : Future[Seq[String]] = {

    implicit val system = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer = ActorMaterializer(settings)

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    val formattedDate = LocalDate.parse(date, formatter);

    def files = {

      var filesList = List[String]()

      var c = 0
      while( c < numberOfDays) {
        val date = formattedDate.minusDays(c).toString().replace("-", "")
        val filePath: String = path + "transactions_" + date + ".data"
        filesList = filesList ++ List(filePath)
        c = c+1
      }






      filesList
    }
    val result = Source(files).flatMapConcat(filename =>
      FileIO.fromPath(Paths.get(filename))
        .via(Framing.delimiter(ByteString("\n"), 256, allowTruncation = true).map(_.utf8String))
    ).toMat(Sink.seq)(Keep.right)
      .run()

    result

    }

    //exporte une liste dans un fichier au path donné.
    def export(list: List[(String, Any)], path: String) {
      val writer = new PrintWriter(new File(path))
      list.foreach(x => writer.write(x._1 + "|" + x._2 + "\n"))
      writer.close()
    }

}
