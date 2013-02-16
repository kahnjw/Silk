import silk.Parser;
import silk.Formula;
import silk.Printer;
import silk.Python;
object Tests {
  def main(args: Array[String]): Unit = {
	val parser:Parser = new Parser();
	var p:Python = parser.parse(args).compile();
  }
}
