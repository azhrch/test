package phenix

import java.io.{File, FileNotFoundException, PrintWriter}
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import akka.Done
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, IOResult}
import akka.stream.scaladsl.{FileIO, Framing, Keep, Sink}
import akka.util.ByteString
import akka.stream.scaladsl.Source
import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global
object FilesUtils {

  //working on it with akka streams to handle huge files
  def readStream(path: String, date: String): Future[Seq[String]] = {

    implicit val system = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer = ActorMaterializer(settings)

    val multi = Source(1 to 4)
      .flatMapConcat(i => {
        Source(i to 4)
      })






    val result: Future[Seq[String]] =
      FileIO.fromPath(Paths.get(path + "transactions_" + date + ".data"))
        .via(Framing.delimiter(ByteString("\n"), 256, true))
        .map(_.utf8String)
        .toMat(Sink.seq)(Keep.right)
        .run()
/* var aa: List[scala.Array[String]] = Nil
    result.onComplete(x => {
      aa = x.get.map(line => line.split('|')).toList
    })*/
     result
  }

/*  def concatFiles(path : String, date : String, numberOfDays : Int) : List[scala.Array[String]] = {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    val formattedDate = LocalDate.parse(date, formatter);
    var acc = List[scala.Array[String]]()

    for( a <- 0 to numberOfDays){
      val date = formattedDate.minusDays(a).toString().replace("-", "")


      val transactions = readStream(path , date)
      var result: List[scala.Array[String]] = Nil
      transactions.onComplete(x => {
        result = x.get.map(line => line.split('|')).toList
        acc=  acc ++ result })
    }
    acc
  }*/
  //concats transaction files
  @throws[FileNotFoundException]
  def concatFiles(path : String, date : String, numberOfDays : Int) : List[scala.Array[String]] = {

    implicit val system = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer = ActorMaterializer(settings)

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    val formattedDate = LocalDate.parse(date, formatter);
    var acc = List[scala.Array[String]]()



    for( a <- 0 to numberOfDays){
      val date = formattedDate.minusDays(a).toString().replace("-", "")
      val transactions : List[scala.Array[String]] = scala.io.Source.fromFile(path + "transactions_" + date + ".data")
        .getLines().map(line => line.split('|')).toList

    /* val transactions = readStream(path , date)
      var aa: List[scala.Array[String]] = Nil
      transactions.onComplete(x => {
      aa = x.get.map(line => line.split('|')).toList
      acc =  acc ++ aa })*/
      acc =  acc ++ transactions
 }
acc
  }


  @throws[FileNotFoundException]
  def concatFilesAkka(path : String, date : String, numberOfDays : Int) : Future[Seq[String]] = {

    implicit val system = ActorSystem("Sys")
    val settings = ActorMaterializerSettings(system)
    implicit val materializer = ActorMaterializer(settings)

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    val formattedDate = LocalDate.parse(date, formatter);

    def files = {

      var filesList = List[String]()
      for (a <- 1 to numberOfDays) {
        val date = formattedDate.minusDays(a).toString().replace("-", "")
        val filePath: String = path + "transactions_" + date + ".data"
        filesList = filesList ++ List(filePath)
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


    //exports a list to a file
    def export(list: List[(String, Any)], path: String) {
      val writer = new PrintWriter(new File(path))
      list.foreach(x => writer.write(x._1 + "|" + x._2 + "\n"))
      writer.close()
    }

}
