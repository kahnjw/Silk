import silk.Parser;
import silk.Formula;
import silk.Printer;
import silk.Python;
import silk.Json;

object Tests {
  def main(args: Array[String]): Unit = {
	val parser:Parser = new Parser();
	var p:Printer = parser.parse(args).compile(new Python());
  }
}
