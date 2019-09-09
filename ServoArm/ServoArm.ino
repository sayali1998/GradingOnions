#include<Servo.h>
//Servo can be connected on PWM pins only
//PWN pins on arduino: 3,5,6,9,10,11

int servo1=3;
int servo2=5;
int servo3=6;
int servo4=9;
int servo5=10;
int servo6=11;

Servo finger1;
Servo finger2;
Servo finger3;
Servo elbow;
Servo shoulder;
Servo bottom;

void setup() {
  // put your setup code here, to run once:
  finger1.attach(servo1);
  finger2.attach(servo2);
  finger3.attach(servo3);
  elbow.attach(servo4);
  shoulder.attach(servo5);
  bottom.attach(servo6);
  
}

void pickUp()
{
  elbow.write(90);
  delay(2000);  
  int i;
  for(i=90;i>=30;i--)
  {
    finger1.write(i);
    finger2.write(i);
    finger3.write(i);
    delay(200);
    }
  delay(1000);
  elbow.write(75);
  delay(2000);
  
  }

void drop()
{
  int i;  
  for(i=30;i<=90;i++)
  {
    finger1.write(i);
    finger2.write(i);
    finger3.write(i);
    }
  delay(1000);
  
  }



void loop() {
  finger1.write(90);
  delay(2000);
  finger2.write(90);
  delay(2000);
  finger3.write(90);
  delay(2000);
  shoulder.write(15);
  delay(2000);  
  elbow.write(45);
  delay(2000);
  bottom.write(0);
  delay(2000); 
  pickUp();
  delay(5000);
  //Turn to drop
  int i;
  for(i=0;i<=90;i++)
  {
    bottom.write(i);
    delay(100); 
    }
  drop();
  delay(2000);
  bottom.write(0);
  delay(2000);  
    
}
