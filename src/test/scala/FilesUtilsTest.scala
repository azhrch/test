import org.scalatest.FunSuite
import phenix.FilesUtils._
import scala.concurrent._

import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global


class FilesUtilsTest extends FunSuite  {

 //test la fonction readStream
   test("test readStream") {
    val stream = readStream("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/input/", "20170514")

     var result: List[scala.Array[String]] = Nil
     stream.onComplete(x => {
       result = x.get.map(line => line.split('|')).toList

       assert(result.length == 45906)

     })


  }


  test("test export") {
   val list : List[(String, Any)]= List(("aziz","herch"), ("lansrod", "bigdata"), ("test", "export"))
    export(list, "/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/testExport")
       val exportedFile : List[scala.Array[String]] = scala.io.Source.fromFile("/home/herch/Documents/work/WorkSpace/phenix-challenge/src/resources/output/testExport")
      .getLines().map(line => line.split('|')).toList
    assert(exportedFile.length == 3)
  }



}
