#include<Servo.h>
//Servo can be connected on PWM pins only
//PWN pins on arduino: 3,5,6,9,10,11

int servo1=3;
int servo2=5;
int servo3=6;

Servo finger1;
Servo finger2;
Servo finger3;

void setup() {
  // put your setup code here, to run once:
  finger1.attach(servo1);
  finger2.attach(servo2);
  finger3.attach(servo3);
  
}

void loop() {
  // put your main code here, to run repeatedly:
  finger1.write(0);
  delay(2000);
  finger2.write(0);
  delay(2000);
  finger3.write(0);
  delay(2000);
  finger1.write(90);
  delay(2000);
  finger2.write(90);
  delay(2000);
  finger3.write(90);
  delay(2000);
}
