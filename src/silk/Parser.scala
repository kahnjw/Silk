package silk {
	/* Abstract class Parser defines a small interface that may be 
	 * used to parse user input and translate it to an internal 
	 * representation (Formula) that the Silk compile may use.
	 * Concrete implementations of the parser are implemented for 
	 * Silk (SilkParser) and Json (JsonParser).
	 */
	 
  abstract class Parser {
    def parse(a: Array[String]):Formula;
    def parse(list: List[String]):Formula
    
    def guessAndCompile(a: Array[String]): Printer = {
      def guessHelper(list: List[String]): Printer = list match {
        case "from" :: "silk" :: "to" :: "python" :: tail => {
          var parser:Parser = new SilkParser();
          return parser.parse(tail).compile(new Python());
        }
        case "from" :: "silk" :: "to" :: "json" :: tail => {
          var parser:Parser = new SilkParser();
          return parser.parse(tail).compile(new Json());
        }
      	case _ => throw new Error("Printer options are \"python\" and \"json\"");
      }
      var list = a.elements.toList;
	  return guessHelper(list);
    }
    
  }
  case class SilkParser() extends Parser {
    def parse(list: List[String]):Formula = {
      return parseHelper(list, null);
    }
    def parse(a: Array[String]): Formula = {
	  var list = a.elements.toList;
	  return parseHelper(list, null);
	}
    def parseHelper(list: List[String], f: Formula): Formula = (list, f) match {
	  	case ("package" :: name :: tail, f) => {
	  	  return parseHelper(tail, Package(Title(name), Nil));
	  	}
	  	
	  	case ("model" :: name :: tail, Package(title,list)) => {
	  	  val modelForm = Model(Title(name), Nil);
	  	  return parseHelper(tail, Package(title, modelForm :: list));
	  	}
	  	
	  	case ("bool" :: name :: tail, Package(title, modelHead :: modelList)) => {
	  	  val boolForm = Boolean(Title(name));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, modelList ::: List(Model(modelTitle, attrList ::: List(boolForm)))));
	  	    }
	  	    case _ => throw new Error("bool must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("int" :: name :: tail, Package(title, modelHead :: modelList)) => {
	  	  val intForm = Integer(Title(name));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, modelList ::: List(Model(modelTitle, attrList ::: List(intForm)))));
	  	    }
	  	    case _ => throw new Error("int must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("float" :: name :: tail, Package(title, modelHead :: modelList)) => {
	  	  val floatForm = Float(Title(name));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, modelList ::: List(Model(modelTitle, attrList ::: List(floatForm)))));
	  	    }
	  	    case _ => throw new Error("float must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("oneToOne" :: name :: refName :: tail, Package(title, modelHead :: modelList)) => {
	  	  val oneToOneForm = OneToOne(Title(name), ReferenceModel(Title(refName)));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, modelList ::: List(Model(modelTitle, attrList ::: List(oneToOneForm)))));
	  	    }
	  	    case _ => throw new Error("oneToOne must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("oneToMany" :: name :: refName :: tail, Package(title, modelHead :: modelList)) => {
	  	  val oneToManyForm = OneToMany(Title(name), ReferenceModel(Title(refName)));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, modelList ::: List(Model(modelTitle, attrList ::: List(oneToManyForm)))));
	  	    }
	  	    case _ => throw new Error("oneToMany must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("manyToMany" :: name :: refName :: tail, Package(title, modelHead :: modelList)) => {
	  	  val manyToManyForm = ManyToMany(Title(name), ReferenceModel(Title(refName)));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, modelList ::: List(Model(modelTitle, attrList ::: List(manyToManyForm)))));
	  	    }
	  	    case _ => throw new Error("manyToMany must be placed in a model");
	  	  }
	  	}
	  	
	  	case (Nil, _) => return f;
	  	case (err :: rest, _) => throw new Error("Unknown type: " + err);
	  }
  }
}
