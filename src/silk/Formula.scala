package silk {
  import scala.collection.mutable.ListBuffer
  
  abstract class Formula {
	def compile(p: Printer): Printer = {
	  def compileHelper(f: Formula, p: Printer):Printer = f match {
	    case Package(Title(string), models) => {
	      p.sendPackage(string);
	      for(model <- models) {
	        compileHelper(model, p);
	      }
	      p.sendEndPackage();
	      p;
	    }
	    /* CASE IS MODEL ***/
	  	case Model(title, attrs) => {
	  	  title match {
	  	    case Title(s) => {
	  	      p.sendTitle(s);
	  	      for (attr <- attrs) {
	  	        compileHelper(attr, p);
	  	      }
	  	      p.sendEndModel();
	  	      p;
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
	  	  }
	  	  /* CASE IS BOOLEAN ***/
	  	  case Boolean(title) => {
	  		title match {
	  		  case Title(string) => {
	  		    p.sendBool(string);
	  		    p;
	  		  }
	  		  case _ => throw new Error("Type mismatch in compile -> Attr -> Bool");
	  		}
	  	  }
	  	  /* CASE IS FLOAT ***/
	  	  case Float(title) => {
	  	    title match {
	  	      case Title(string) => {
	  	        p.sendFloat(string);
	  	        p;
	  	      }
	  	      case _ => throw new Error("Type mismatch in compile -> Attr -> Float");
	  	    }
	  	  }
	  	  case Text(title, length) => {
	  	    title match {
	  	      case Title(string) => {
	  	        p.sendText(string, length);
	  	        p;
	  	      }
	  	      case _ => throw new Error("Type mismatch in compile -> Attr -> Text");
	  	    }
	  	  }
	  	  case OneToOne(title, rm) => {
	  	    (title, rm) match {
	  	      case (Title(modelString), ReferenceModel(Title(referenceString))) => {
	  	        p.sendOneToOne(modelString, referenceString);
	  	        p;
	  	      }
	  	      case _ => throw new Error("Type mismatch in compile -> Attr -> OneToOne");
	  	    }
	  	  }
	  	  case OneToMany(title, rm) => {
	  	    (title, rm) match {
	  	      case (Title(modelString), ReferenceModel(Title(referenceString))) => {
	  	        p.sendOneToMany(modelString, referenceString);
	  	        p;
	  	      }
	  	      case _ => throw new Error("Type mismatch in compile -> Attr -> OneToOne");
	  	    }
	  	  }
	  	  case ManyToMany(title, rm) => {
	  	    (title, rm) match {
	  	      case (Title(modelString), ReferenceModel(Title(referenceString))) => {
	  	        p.sendManyToMany(modelString, referenceString);
	  	        p;
	  	      }
	  	      case _ => throw new Error("Type mismatch in compile -> Attr -> OneToOne");
	  	    }
	  	  }
	  	  case _ => throw new Error("Type mismatch in compile -> Attr")
	  	}
	  }
	  compileHelper(this, p).printer();
	  return p;
	}
  }
  case class Package(title: Title, models: List[Model]) extends Formula
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
}
