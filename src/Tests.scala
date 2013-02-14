object Tests {
	def main(args: Array[String]) {
	  var formula = new Model(Title("Title"), List(Integer(Title("viewCounts")), Float(Title("decimal")), Boolean(Title("isFast")), Boolean(Title("isHeady"))));
	  var p:Python = formula.compile();
	  print("Tests successful!");
	}
}