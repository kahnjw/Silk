package silk {
  class Parser {
	def parse(a: Array[String]) = {
	  
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
	  	    case Model(title, attrList) => {
	  	      return parseHelper(tail, Package(title, Model(title, boolForm :: attrList) :: modelList));
	  	    }
	  	    case _ => throw new Error("bool must be placed in a model");
	  	  }
	  	}
	  	case ("int" :: name :: tail, Package(title, modelHead :: modelList)) => {
	  	  val boolForm = Integer(Title(name));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, Model(modelTitle, boolForm :: attrList) :: modelList));
	  	    }
	  	    case _ => throw new Error("int must be placed in a model");
	  	  }
	  	}
	  	case ("float" :: name :: tail, Package(title, modelHead :: modelList)) => {
	  	  val boolForm = Float(Title(name));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, Model(modelTitle, boolForm :: attrList) :: modelList));
	  	    }
	  	    case _ => throw new Error("float must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("oneToOne" :: name :: refName :: tail, Package(title, modelHead :: modelList)) => {
	  	  val oneToOneForm = OneToOne(Title(name), ReferenceModel(Title(refName)));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, Model(modelTitle, oneToOneForm :: attrList) :: modelList));
	  	    }
	  	    case _ => throw new Error("oneToOne must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("oneToMany" :: name :: refName :: tail, Package(title, modelHead :: modelList)) => {
	  	  val oneToManyForm = OneToMany(Title(name), ReferenceModel(Title(refName)));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, Model(modelTitle, oneToManyForm :: attrList) :: modelList));
	  	    }
	  	    case _ => throw new Error("oneToMany must be placed in a model");
	  	  }
	  	}
	  	
	  	case ("manyToMany" :: name :: refName :: tail, Package(title, modelHead :: modelList)) => {
	  	  val manyToManyForm = ManyToMany(Title(name), ReferenceModel(Title(refName)));
	  	  modelHead match {
	  	    case Model(modelTitle, attrList) => {
	  	      return parseHelper(tail, Package(title, Model(modelTitle, manyToManyForm :: attrList) :: modelList));
	  	    }
	  	    case _ => throw new Error("manyToMany must be placed in a model");
	  	  }
	  	}
	  	case (Nil, _) => return f;
	  	case (err :: rest, _) => throw new Error("Unknown type: " + err);
	  }
	  var list = a.elements.toList;
	  parseHelper(list, null);
	}
  }
}