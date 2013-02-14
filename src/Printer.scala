import sys.process._
import java.io._

abstract class Printer

case class Python() extends Printer {
  var title:Title = new Title("");
  var s:String = new String("");
  def sendTitle(t:String) {
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
  
  def defaults():String = {
    var s:String = "";
    s += "from django.db import models\n";
    s += "from django.contrib.auth.models import User\n";
    s += "\n"
    return s;
  }
  
  def printer() {
    val writer = new PrintWriter(new File("/Users/jarrodkahn/Documents/class2.py" ));
    
    writer.write(defaults());
    writer.write(s);
    writer.write("\n\n");
    writer.close();
  }
}
case class Ruby()
case class Xml()