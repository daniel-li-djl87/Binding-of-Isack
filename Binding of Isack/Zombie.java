import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
public class Zombie extends Enemy{
  BufferedImage[] sprites;
  int currentSprite,currentStep;
  int direction;
  Rectangle boundingBox;
  int width = 47;
  int length = 37;
  int playerX;
  int playerY;
  
  public Zombie (int health, int x, int y){
    super (health,x,y);
    loadSprites();
    currentSprite = 0;
    currentStep = 0;
    boundingBox=new Rectangle((int)x, (int)y, width, length);
  }
  
  public void loadSprites(){
    try {
      sprites = new BufferedImage[4];      
      sprites[0] = ImageIO.read(new File("Enemy3Down.png"));
      sprites[1] = ImageIO.read(new File("Enemy3Left.png"));
      sprites[2] = ImageIO.read(new File("Enemy3Right.png"));
      sprites[3] = ImageIO.read(new File("Enemy3Down.png")); 
    } catch(Exception e) {}
  }
  public void draw(Graphics g){
    g.drawImage(sprites[currentSprite],this.getX(),this.getY(),null);
    g.setColor(Color.white);
  }
  public void update( int playerX, int playerY){ 
    if (currentStep>=30){
      currentStep=0;
    }
    if (currentStep==0){
      direction = ((int)(Math.random()*4));
    }
    if ( this.getX() < playerX &&this.getX()<=870 ){
      boundingBox=new Rectangle(this.getX(), this.getY(), 47, 37);
      currentStep++;
      currentSprite = 2;
      moveRight();
    } else if ( this.getX() > playerX && this.getX()>=150 ){
      boundingBox=new Rectangle(this.getX(), this.getY(), 47, 37);
      currentStep++;
      currentSprite = 1;
      moveLeft();
    } 
    
    if ( this.getY() < playerY && this.getY()<=585 ){
      boundingBox=new Rectangle(this.getX(), this.getY(), 47, 37);
      currentStep++;
      currentSprite = 0;
      moveDown();
    } else if ( this.getY() > playerY &&this.getY()>=210){
      boundingBox=new Rectangle(this.getX(), this.getY(), 47, 37);
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
    if ( this.getHealth() <= 0 ){
    }
  }
  
  
}