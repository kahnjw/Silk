import sys.process._
import java.io._
package silk {
	abstract class Printer
	
	case class Python() extends Printer {
	  var title:Title = new Title("");
	  var s:String = new String("");
	  var module:String = "";
	  
	  def sendPackage(t:String) {
	    this.s += "";
	    this.module = t;
	  }
	  
	  def sendTitle(t:String) {
	    this.s += "\n";
	    this.s += "class " + t + "(models.Model):";
	    this.s += "\n\t"
	  }
	  
	  def sendInt(t:String) {
	    this.s += t + " = models.IntegerField()"
	    this.s += "\n\t"
	  }
	  
	  def sendBool(t:String) {
	    this.s += t + " = models.BooleanField(default=True)"
	    this.s += "\n\t"
	  }
	  
	  def sendFloat(t:String) {
	    this.s += t + " = models.FloatField()"
	    this.s += "\n\t"
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
	    this.s += ms + " = models.OneToOne(" + rs + ")";
	    this.s += "\n\t"
	  }
	  
	  def sendOneToMany(ms: String, rs: String) {
	    this.s += ms + " = models.OneToMany(" + rs + ")";
	    this.s += "\n\t"
	  }
	  
	  def sendManyToMany(ms: String, rs: String) {
	    this.s += ms + " = models.ManyToMany(" + rs + ")";
	    this.s += "\n\t"
	  }
	  
	  def defaults():String = {
	    var s:String = "";
	    s += "from django.db import models\n";
	    s += "from django.contrib.auth.models import User\n";
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
	case class Ruby()
	case class Xml()
}