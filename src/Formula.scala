import scala.collection.mutable.ListBuffer

abstract class Formula {

	def compile():Python = {
	  def compileHelper(f: Formula, p: Python):Python = f match {
	    
	  	case Model(title, attrs) => {
	  	  title match {
	  	    case Title(s) => {
	  	      p.sendTitle(s);
	  	      for (attr <- attrs) {
	  	        compileHelper(attr, p)
	  	      }
	  	      p
	  	    }
	  	    case _ => throw new Error("Type mismatch in compile -> Model")
	  	  }
	      return p;
	    }
	  	case Attr() => f match {
	  	  /* CASE IS INTEGER ***/
	  	  case Integer(title) => { 
	  	    title match {
	  	      case Title(string) => {
	  	        p.sendInt(string);
	  	        p
	  	      }
	  	      case _ => throw new Error("Type mismatch in compile -> Attr -> Int")
	  	    }
	  	    // p;
	  	  }
	  	  /* CASE IS BOOLEAN ***/
	  	  case Boolean(title) => {
	  		title match {
	  		  case Title(string) => {
	  		    p.sendBool(string);
	  		    p
	  		  }
	  		  case _ => throw new Error("Type mismatch in compile -> Attr -> Bool")
	  		}
	  	  }
	  	  /* CASE IS FLOAT ***/
	  	  case Float(title) => {
	  	    title match {
	  	      case Title(string) => {
	  	        p.sendFloat(string);
	  	        p
	  	      }
	  	      case _ => throw new Error("Type mismatch in compile -> Attr -> Float")
	  	    }
	  	  }
	  	  case OneToOne(title) => p
	  	  case OneToMany(title) => p
	  	  case ManyToMany(title) => p
	  	}
	  }
	  var p = new Python();
	  compileHelper(this, p);
	  p.printer();
	  p
	}
}
case class Model(title: Title, attrs: List[Attr]) extends Formula
case class Attr() extends Formula
case class Integer(title: Title) extends Attr
case class Boolean(title: Title) extends Attr
case class Float(title: Title) extends Attr
case class OneToOne(title: Title) extends Attr
case class OneToMany(title: Title) extends Attr
case class ManyToMany(title: Title) extends Attr
case class Title(text: String)

