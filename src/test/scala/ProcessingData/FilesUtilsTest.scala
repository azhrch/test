package ProcessingData

import ProcessingData.FilesUtils._
import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global


class FilesUtilsTest extends FunSuite  {



  //test la fonction readStream
   test("test readStream") {

     val stream = readStream("./src/resources/input/", "20170514")

     var result: List[scala.Array[String]] = Nil
     stream.onComplete(x => {
       result = x.get.map(line => line.split('|')).toList
       assert(result.length == 45906)
     })
  }


  test("test export") {
   val list : List[(String, Any)]= List(("v1","v2"), ("v3", "v4"), ("v5", "v6"))
    export(list, "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/testExport")
       val exportedFile : List[scala.Array[String]] = scala.io.Source.fromFile("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/testExport")
      .getLines().map(line => line.split('|')).toList
    assert(exportedFile.length == 3)
  }








}