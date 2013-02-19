import silk.Parser;
import silk.SilkParser;
import silk.Formula;
import silk.Printer;
import silk.Python;
import silk.Json;

object Tests {
  def main(args: Array[String]): Unit = {
	val parser:Parser = new SilkParser();
  	parser.guessAndCompile(args);
  }
}
