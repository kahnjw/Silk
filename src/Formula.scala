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
	  	        p
	  	      }
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
	  	  case Text(title, length) => throw new Error("Text not implemented")
	  	  case OneToOne(title, rm) => throw new Error("OneToOne not implemented")
	  	  case OneToMany(title, rm) => throw new Error("OneToMany not implemented")
	  	  case ManyToMany(title, rm) => throw new Error("ManyToMany not implemented")
	  	  case _ => throw new Error("Type mismatch in compile -> Attr")
	  	}
	  }
	  var p = new Python();
	  compileHelper(this, p);
	  p.printer();
	  p
	}
}
case class Model(title: Title, attrs: List[Attr]) extends Formula
case class ReferenceModel(title: Title) extends Formula
case class Attr() extends Formula
case class Integer(title: Title) extends Attr
case class Boolean(title: Title) extends Attr
case class Float(title: Title) extends Attr
case class Text(title: Title, length: Int) extends Attr
case class OneToOne(title: Title, rm: ReferenceModel) extends Attr
case class OneToMany(title: Title, rm: ReferenceModel) extends Attr
case class ManyToMany(title: Title, rm: ReferenceModel) extends Attr
case class Title(text: String)

