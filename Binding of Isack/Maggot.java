import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
public class Maggot extends Enemy{
  BufferedImage[] sprites;
  int currentSprite,currentStep;
  int direction;
  int width;
  int length;
  Rectangle boundingBox;
  public Maggot (int health, int x, int y){
    super (health,x,y);
    loadSprites();
    currentSprite = 0;
    currentStep = 0;
    width = 27;
    length = 28;
    boundingBox=new Rectangle((int)x, (int)y, length, width);
  }
  public void loadSprites(){
    try {
      sprites = new BufferedImage[4];      
      sprites[0] = ImageIO.read(new File("Enemy2Down.png"));
      sprites[1] = ImageIO.read(new File("Enemy2Left.png"));
      sprites[2] = ImageIO.read(new File("Enemy2Right.png"));
      sprites[3] = ImageIO.read(new File("Enemy2Up.png")); 
    } catch(Exception e) {}
  }
  public void draw(Graphics g){
    g.drawImage(sprites[currentSprite],this.getX(),this.getY(),null);
    g.setColor(Color.white);
  }
  public void update(){ 
    if (currentStep>=30){
      currentStep=0;
    }
    if (currentStep==0||this.getX()<=150||this.getY()>=585||this.getX()>=870||this.getY()<=210){
      direction = ((int)(Math.random()*4));
    }
    if (direction==0&&this.getY()<585){
      width = 27;
      length = 28;
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentStep++;
      currentSprite = 0;
      moveDown();
    }else if (direction==1&&this.getX()>150){
      width = 37;
      length = 18;
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentStep++;
      currentSprite = 1;
      moveLeft();
    }else if (direction==2&&this.getX()<870){
      width = 37;
      length = 18;
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentStep++;
      currentSprite = 2;
      moveRight();
    }else if (direction==3&&this.getY()>210){
      width = 27;
      length = 28;
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentStep++;
      currentSprite = 3;
      moveUp();
    }
  }
  public void moveUp(){
    this.setY(this.getY()-2);
  }
  
  public void moveDown(){
    this.setY(this.getY()+2);
  }
  
  public void moveLeft(){
    this.setX(this.getX()-2);
  }
  
  public void moveRight(){
    this.setX(this.getX()+2);
  }
  public void takeDamage(){
    this.setHealth(this.getHealth() - 1);
    if ( this.getHealth() <= 0 ){
    }
  }
  
  
  
}