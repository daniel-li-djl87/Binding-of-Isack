import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
class Bullet extends Entity{
  private int bulletSpeed, ySpeed, xSpeed;
  String direction;
  Boolean outOfBounds;
  Boolean destroyed;
  BufferedImage[] bulletSprite;
  
  public Bullet(int x, int y, int playerX, int playerY) {
    super(x,y);
    this.bulletSpeed = 8;
    this.outOfBounds = false;
    boundingBox = new Rectangle ((int) x,(int) y,9,8);
    destroyed = false;
    ySpeed = (playerY-this.getY());
    xSpeed = (playerX-this.getX());
    double factor = this.bulletSpeed / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
    ySpeed*=factor;
    xSpeed*=factor;
  }
  public void move(){
    this.setX(this.getX()+xSpeed);
    this.setY(this.getY()+ySpeed);
    boundingBox.x = (int)this.getX();
    boundingBox.y = (int)this.getY();
  }  
  public void draw(Graphics g, int changeX, int changeY) { 
    try{
    BufferedImage bulletSprite = ImageIO.read(new File("redtear.png"));
    g.drawImage(bulletSprite,(int)this.getX() + changeX, (int)this.getY() + changeY,null);
    }catch(Exception e) {};
  }
  public void boundaryCheck(){
    if (this.getX()<148||this.getX()>872||this.getY()<208||this.getY()>587){
      this.outOfBounds = true;
    }
  }
}