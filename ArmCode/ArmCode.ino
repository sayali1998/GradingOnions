#include <SoftwareSerial.h>
#include<Servo.h>
//PWM pins on arduino: 3,5,6,9,10,11.

int x;
int y;
float h=45;
int distCovered=0.5;   //Distance covered when it moves by 1 degree
float angle;
int degree;
float roundOffAngle;
int distanceMovedAngle;

int servo_base=3;
int servo_length_rack=5;
int servo_depth_rack=6;
int servo_hold=9;
int servo_hold2=10;

String coords;

Servo servoBase;
Servo servoLengthRack;
Servo servoDepthRack;
Servo servoHold;
Servo servoHold2;

int distanceSensor=A0;

SoftwareSerial mySerial(10, 11);

void setup() {
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  servoBase.attach(servo_base);
  servoLengthRack.writeMicroseconds(500);
  servoLengthRack.attach(servo_length_rack,500,3500);
  servoDepthRack.attach(servo_depth_rack);
  servoHold.attach(servo_hold);
  servoHold2.attach(servo_hold2);
  pinMode(distanceSensor,INPUT);
  Serial.println("Communication Started");
  mySerial.begin(9600);
  mySerial.println("Hello, world?");
}

void initServo()
{
  servoBase.write(0);
  servoDepthRack.write(0);
  servoHold.write(0);
  servoHold2.write(100);
  coords="";
  delay(1000);
  }

void loop() {
  
  initServo();
  //Serial Communication x,y values
//  if (mySerial.available()) {
//    coords=mySerial.readString();
//    Serial.println(coords);
//    x=coords[0];
//    y=coords[1];
// }
  
  //h=sqrt((x*x)+(y*y));
  angle=atan(h)*57.2958;
  int j;
  for(j=0;j<45;j++)
  {
    servoBase.write(j);
    delay(50);
    }
  delay(2000);

//  roundOffAngle=h/distCovered;
//  if(roundOffAngle>=(int(roundOffAngle)+0.5))
//  {
//    distanceMovedAngle=int(roundOffAngle)+1;
//    }
//    else
//    {
//      distanceMovedAngle=int(roundOffAngle);
//    }
  
//  servoLengthRack.write(180);
//  delay(3000);
//  degree=0;
//  for(;degree<90;degree++)
//    {
//      servoDepthRack.write(degree);
//      delay(1000);
//      }
  int i;
  for(i=0;i<120;i++)
  {
    servoHold.write(i);
    servoHold2.write(120-i);
    delay(50);
    }
  int k;
    for(k=50;k<100;k++)
    {
  servoBase.write(k); 
  delay(50);   
      }
  
  delay(1000);

  servoHold2.write(90);

  delay(1000);

  servoHold.write(0);
  delay(500);
  for(k=100;k>0;k--)
    {
  servoBase.write(k); 
  delay(50);   
      }
}
