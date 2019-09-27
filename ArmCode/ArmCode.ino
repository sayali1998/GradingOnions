#include <SoftwareSerial.h>
#include<Servo.h>
//PWM pins on arduino: 3,5,6,9,10,11.

int x;
int y;
int h;
int distCovered=0;   //Distance covered when it moves by 1 degree
float angle;
int degree;
float roundOffAngle;
int distanceMovedAngle;

int servo_base=3;
int servo_length_rack=5;
int servo_depth_rack=6;
int servo_hold=9;
int servo_syringe=10;

String coords;

Servo servoBase;
Servo servoLengthRack;
Servo servoDepthRack;
Servo servoHold;
Servo servoSyringe;

int distanceSensor=A0;

SoftwareSerial mySerial(0, 1);

void setup() {
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  servoBase.attach(servo_base);
  servoLengthRack.attach(servo_length_rack);
  servoDepthRack.attach(servo_depth_rack);
  servoHold.attach(servo_hold);
  servoSyringe.attach(servo_syringe);
  pinMode(distanceSensor,INPUT);
  Serial.println("Communication Started");
  mySerial.begin(9600);
  mySerial.println("Hello, world?");
}

void initServo()
{
  servoBase.write(0);
  servoLengthRack.write(0);
  servoDepthRack.write(0);
  servoHold.write(0);
  servoSyringe.write(0);
  delay(1000);
  }

void loop() {

  initServo();
  //Serial Communication x,y values
  if (mySerial.available()) {
    coords=mySerial.readString();
    Serial.println(coords);
  }
  
//  h=sqrt((x*x)+(y*y));
//  angle=atan(h)*57.2958;
//
//  servoBase.write(angle);
//
//  roundOffAngle=h/distCovered;
//  if(roundOffAngle>=(int(roundOffAngle)+0.5))
//  {
//    distanceMovedAngle=int(roundOffAngle)+1;
//    }
//    else
//    {
//      distanceMovedAngle=int(roundOffAngle);
//    }
//  servoLengthRack.write(distanceMovedAngle);
//  delay(2000);
//  degree=0;
//  while(analogRead(distanceSensor)>1)
//  {
//    servoDepthRack.write(degree++);
//    delay(100);
//    }
//  servoDepthRack.write(degree+3);
//  delay(100);
//
//  servoSyringe.write(180);
//  delay(1000);
//
//  servoDepthRack.write(degree-10);
//  delay(100);
//
//  servoHold.write(100);
//  delay(1000);
//
//  servoBase.write(120);
//  delay(1000);
//
//  servoSyringe.write(0);
//  delay(1000);
//
//  servoHold.write(0);
//  delay(500);
}
