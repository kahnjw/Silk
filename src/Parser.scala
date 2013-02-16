package silk {
  class Parser {
	def parse(a: Array[String]) = {
	  
	  def parseHelper(list: List[String], f: Formula): Formula = list match {
	  	case head :: tail => {
	  	  head match {
	  	    case "package" => {
	  	      tail match {
	  	        case packageName :: newTail => {
	  	          return parseHelper(newTail, Package(Title(packageName), Nil));
	  	        }
	  	        case _ => throw new Error("Package must have name")
	  	      }
	  	    }
	  	    case "class" => {
	  	      tail match {
	  	        case className :: newTail => {
	  	          f match {
	  	            case Package(title, list) => {
	  	              val modelForm = Model(Title(className), Nil);
	  	              return parseHelper(newTail, Package(title, modelForm :: list));
	  	            }
	  	            case _ => throw new Error("Matching on: "+ f);
	  	          }
	  	          // return  parseHelper(newTail, Model(Title(className),List()));
	  	        }
	  	        case _ => throw new Error("Classes must be named")
	  	      }
	  	    }
	  	    
	  	    case "bool" => {
	  	      tail match {
	  	        case intName :: newTail => {
	  	          val intForm = Boolean(Title(intName));
	  	          f match {
	  	            case Package(packageTitle, modelHead :: modelList) => {
	  	              modelHead match {
	  	                case Model(title, list) => {
	  	                  return parseHelper(newTail, Package(packageTitle, Model(title, intForm :: list) :: modelList));
	  	                }
	  	                case _ => throw new Error("Got: " + f +" Required: Model(title, list)")
	  	              }
	  	            }
	  	          	/*
	  	            case Model(title, list) => {
	  	          	  return parseHelper(newTail, Model(title, intForm :: list));
	  	          	}*/
	  	          	case _ => throw new Error("Got: " + f +" Required: Model(title, list)")
	  	          }
	  	          throw new Error("Attributes belong in classes")
	  	        }
	  	        case _ => throw new Error("Ints must be named")
	  	      }
	  	    }
	  	    
	  	    case "int" => {
	  	      tail match {
	  	        case intName :: newTail => {
	  	          val intForm = Integer(Title(intName));
	  	          f match {
	  	            case Package(packageTitle, modelHead :: modelList) => {
	  	              modelHead match {
	  	                case Model(title, list) => {
	  	                  return parseHelper(newTail, Package(packageTitle, Model(title, intForm :: list) :: modelList));
	  	                }
	  	                case _ => throw new Error("Got: " + f +" Required: Model(title, list)")
	  	              }
	  	            }
	  	            /*
	  	          	case Model(title, list) => {
	  	          	  return parseHelper(newTail, Model(title, intForm :: list));
	  	          	}*/
	  	          	case _ => throw new Error("Got: " + f +" Required: Model(title, list)")
	  	          }
	  	          throw new Error("Attributes belong in classes")
	  	        }
	  	        case _ => throw new Error("Ints must be named")
	  	      }
	  	    }
	  	    
	  	    case "float" => {
	  	      tail match {
	  	        case intName :: newTail => {
	  	          val intForm = Float(Title(intName));
	  	          f match {
	  	            case Package(packageTitle, modelHead :: modelList) => {
	  	              modelHead match {
	  	                case Model(title, list) => {
	  	                  return parseHelper(newTail, Package(packageTitle, Model(title, intForm :: list) :: modelList));
	  	                }
	  	                case _ => throw new Error("Got: " + f +" Required: Model(title, list)")
	  	              }
	  	            }
	  	            /*
	  	          	case Model(title, list) => {
	  	          	  return parseHelper(newTail, Model(title, intForm :: list));
	  	          	}*/
	  	          	case _ => throw new Error("Got: " + f +" Required: Model(title, list)")
	  	          }
	  	          throw new Error("Attributes belong in classes")
	  	        }
	  	        case _ => throw new Error("Ints must be named")
	  	      }
	  	    }
	  	  }
	  	}
	  	case Nil => return f;
	  	case _ => throw new Error("Syntax error - you broke it!")
	  }
	  
	  var list = a.elements.toList;
	  parseHelper(list, null);
	}
  }
}