import sys.process._
import java.io._
package silk {
	/* Abstract class Printer defines an interface which a compiler may
	 * use to print code to a file. Concrete implementations of the 
	 * printer implemented in Python and Json.
	 */
	
	abstract class Printer {
	  def sendPackage(t: String);
	  def sendEndPackage();
	  def sendTitle(t: String);
	  def sendEndModel();
	  def sendInt(t: String);
	  def sendBool(t: String);
	  def sendFloat(t: String);
	  def sendText(t: String, length: Int);
	  def sendOneToOne(ms: String, rs: String);
	  def sendOneToMany(ms: String, rs: String);
	  def sendManyToMany(ms: String, rs: String);
	  def defaults():String;
	  def printer();
	}
	
	case class Python() extends Printer {
	  var title:Title = new Title("");
	  var s:String = new String("");
	  var module:String = "";
	  
	  def sendPackage(t:String) {
	    this.s += "";
	    this.module = t;
	  }
	  
	  def sendEndPackage() {
	    this.s += "\n";
	  }
	  
	  def sendTitle(t:String) {
	    this.s += "\n";
	    this.s += "class " + t + "(models.Model):";
	  }
	  
	  def sendEndModel() {
	    this.s += "\n";
	  }
	  
	  def sendInt(t:String) {
	    this.s += "\n"
	    this.s += "\t" + t + " = models.IntegerField()"
	  }
	  
	  def sendBool(t:String) {
	    this.s += "\n"
	    this.s += "\t" + t + " = models.BooleanField(default=True)"

	  }
	  
	  def sendFloat(t:String) {
	    this.s += "\n"
	    this.s += "\t" + t + " = models.FloatField()"

	  }
	  
	  def sendText(t: String, length: Int) {
	    if (length > 500) {
	      this.s += t + " = models.CharField(max_length=" + length + ")";
	    } else {
	      this.s += t + " = models.TextField(max_length=" + length + ")";
	    }
	    this.s += "\n\t"
	  }
	  
	  def sendOneToOne(ms: String, rs: String) {
	    this.s += "\n"
	    this.s += "\t" + ms + " = models.OneToOne(" + rs + ")";
	  }
	  
	  def sendOneToMany(ms: String, rs: String) {
	    this.s += "\n"
	    this.s += "\t" + ms + " = models.OneToMany(" + rs + ")";

	  }
	  
	  def sendManyToMany(ms: String, rs: String) {
	    this.s += "\n"
	    this.s += "\t" + ms + " = models.ManyToMany(" + rs + ")";
	  }
	  
	  def defaults():String = {
	    var s:String = "";
	    s += "from django.db import models\n";
	    s += "from django.contrib.auth.models import User\n";
	    return s;
	  }
	  
	  def video():String = {
	    var s:String = "";
	    s += "class Video(models.Model):\n\t";
	    s += "path = models.CharField(max_length=500)"
	    return s;
	  }
	  
	  def printer() {
	    val path = System.getProperty("user.dir") + "/" + this.module + ".py";
	    val writer = new PrintWriter(new File(path));
	    
	    writer.write(defaults());
	    writer.write(s);
	    writer.write("\n\n");
	    writer.close();
	  }
	}
	
	case class Json() extends Printer {
	  var title:Title = new Title("");
	  var s:String = new String("");
	  var module:String = "";
	  var indentCount: Int = 0;
	  
	  def tabs(): String = {
	    var s:String = "";
	    for (i <- 1 to this.indentCount) {
	      s += "\t";
	    }
	    return s;
	  }
	  
	  def pline(s:String) {
	    this.s += tabs();
	    this.s += s;
	  }
	  
	  def sendPackage(t:String) {
	    this.module = t;
	    this.s += "{\n";
	    this.indentCount += 1;
	    pline("\"package\": {\n");
	    this.indentCount += 1;
	    pline("\"title\": \"" + t + "\",\n");
	  }
	  
	  def sendEndPackage() {
	    this.indentCount -= 1;
	    pline("}\n")
	    this.indentCount -= 1;
	    pline("}\n");
	  }
	  
	  def sendTitle(t:String) {
	    pline("\"model\": {\n");
	    this.indentCount += 1;
	    pline("\"title\": \"" + t + "\",");
	  }
	  
	  def sendEndModel() {
	    this.indentCount -= 1;
	    pline("\n");
	    pline("},");
	    this.s += "\n";
	  }
	  
	  def sendInt(t:String) {
	    this.s += "\n"
	    pline("\"integer\": \"" + t + "\",");
	  }
	  
	  def sendBool(t:String) {
	    this.s += "\n"
	    pline("\"boolean\": \"" + t + "\",");

	  }
	  
	  def sendFloat(t:String) {
	    this.s += "\n"
	    pline("\"float\": \"" + t + "\",");

	  }
	  
	  def sendText(t: String, length: Int) {
	    this.s += "\n"
	    pline("\"string\": \"" + t + "\",");
	  }
	  
	  def sendOneToOne(ms: String, rs: String) {
	    this.s += "\n"
	    pline("\"oneToOne\": {\n");
	    this.indentCount += 1;
	    pline("\"title\": \"" + ms + "\",\n");
	    pline("\"reference\": \"" + rs + "\",\n");
	    this.indentCount -= 1;
	    pline("},\n");
	  }
	  
	  def sendOneToMany(ms: String, rs: String) {
	    this.s += "\n"
	    pline("\"oneToMany\": {\n");
	    this.indentCount += 1;
	    pline("\"title\": \"" + ms + "\",\n");
	    pline("\"reference\": \"" + rs + "\",\n");
	    this.indentCount -= 1;
	    pline("},\n");
	  }
	  
	  def sendManyToMany(ms: String, rs: String) {
	    this.s += "\n"
	    pline("\"manyToMany\": {\n");
	    this.indentCount += 1;
	    pline("\"title\": \"" + ms + "\",\n");
	    pline("\"reference\": \"" + rs + "\",\n");
	    this.indentCount -= 1;
	    pline("},\n");
	  }
	  
	  def defaults():String = {
	    return "";
	  }
	  
	  def video():String = {
	    return "";
	  }
	  
	  def printer() {
	    val path = System.getProperty("user.dir") + "/" + this.module + ".json";
	    val writer = new PrintWriter(new File(path));
	    
	    writer.write(defaults());
	    writer.write(s);
	    writer.write("\n\n");
	    writer.close();
	  }
	}
}
