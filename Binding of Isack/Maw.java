import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
public class Maw extends Enemy{
  BufferedImage[] turretSprite;
  ArrayList<Bullet> firedBullets = new ArrayList<Bullet>();
  int totalBullets;
  int currentSprite,currentStep;
  int direction;
  public Maw (int health, int x, int y){
    super (health,x,y);
    boundingBox = new Rectangle ((int)x,(int)y,66,79);
    totalBullets = 0;
  }
  public void draw(Graphics g){
    try{
      BufferedImage turretSprite = ImageIO.read(new File("Enemy4.png"));
      g.drawImage(turretSprite,(int)this.getX(), (int)this.getY(),null);
    }catch(Exception e) {};
  }
  public void update( int playerX, int playerY){ 
    if (currentStep>=30){
      currentStep=0;
    }
    if (currentStep==0){
      direction = ((int)(Math.random()*4));
    }
    if ( this.getX() < playerX &&this.getX()<=870 ){
      boundingBox=new Rectangle(this.getX(), this.getY(), 29, 30);
      currentStep++;
      currentSprite = 2;
      moveRight();
    } else if ( this.getX() > playerX && this.getX()>=150 ){
      boundingBox=new Rectangle(this.getX(), this.getY(),  29, 30);
      currentStep++;
      currentSprite = 1;
      moveLeft();
      } 
    
    if ( this.getY() < playerY && this.getY()<=585 ){
      boundingBox=new Rectangle(this.getX(), this.getY(),  29, 30);
      currentStep++;
      currentSprite = 0;
      moveDown();
      } else if ( this.getY() > playerY &&this.getY()>=210){
        boundingBox=new Rectangle(this.getX(), this.getY(),  29, 30);
      currentStep++;
      currentSprite = 3;
      moveUp();
      }
    
  }
  public void moveUp(){
    this.setY(this.getY()-1);
  }
  
  public void moveDown(){
    this.setY(this.getY()+1);
  }
  
  public void moveLeft(){
    this.setX(this.getX()-1);
  }
  
  public void moveRight(){
    this.setX(this.getX()+1);
  }
  
  public void takeDamage(){
    this.setHealth(this.getHealth() - 1);
  }
  public void shoot(int playerX, int playerY){
    if (totalBullets<1){
      totalBullets++;
      Bullet bullet = new Bullet (this.getX(),this.getY(),playerX,playerY);
      firedBullets.add(bullet);
    }
  }
  public int attack(int playerHealth){
    return playerHealth;
  }
  
  }