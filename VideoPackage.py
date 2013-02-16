from django.db import models
from django.contrib.auth.models import User

class MyVideo(models.Model):
	myInt = models.IntegerField()
	myref = models.OneToOne(OtherClass)
	secondInt = models.IntegerField()
	myFloat = models.FloatField()
	myBoolean = models.BooleanField(default=True)
	
class secondCalls(models.Model):
	thisInt = models.IntegerField()
	

