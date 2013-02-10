import scala.collection.mutable.ListBuffer


abstract class Formula {

	
	case class Model(title: String, attrs: List[Attr]) extends Formula
	case class Attr() extends Formula
	case class Integer() extends Attr
	case class Boolean() extends Attr
	case class Float() extends Attr
	case class OneToOne() extends Attr
	case class OneToMany() extends Attr
	case class ManyToMany() extends Attr
	
	
	def compile(f: Formula) = {
	  def compileHelper(f: Formula, p: Printer):Printer = f match {
	    
	  	case Model(title, attrs) => {
	      // Put title in printer object
	      var attr:Attr = new Attr();
	      for ( attr <- attrs ) {
	        compileHelper(attr, p);
	      }
	      p
	    }
	  	
	  	case Attr() => f match {
	  	  case Integer() => p
	  	  case Boolean() => p
	  	  case Float() => p
	  	  case OneToOne() => p
	  	  case OneToMany() => p
	  	  case ManyToMany() => p
	  	}
	  }
	}
}