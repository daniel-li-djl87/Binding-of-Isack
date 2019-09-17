import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
class BossBullet extends Entity{
  private int bulletSpeed, ySpeed, xSpeed;
  private int randX,randY;
  String direction;
  Boolean outOfBounds;
  Boolean destroyed;
  BufferedImage[] bulletSprite;
  
  public BossBullet(int x, int y) {
    super(x,y);
    this.bulletSpeed = 8;
    this.outOfBounds = false;
    this.randX = ((int)(Math.random()*720+170));
    this.randY = ((int)(Math.random()*375+210));
    boundingBox = new Rectangle ((int) x,(int) y,9,8);
    destroyed = false;
    ySpeed = (randY-this.getY());
    xSpeed = (randX-this.getX());
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
  public void draw(Graphics g) { 
    try{
    BufferedImage bulletSprite = ImageIO.read(new File("redtear.png"));
    g.drawImage(bulletSprite,(int)this.getX(), (int)this.getY(),null);
    }catch(Exception e) {};
  }
  public void boundaryCheck(){
    if (this.getX()<148||this.getX()>872||this.getY()<208||this.getY()>587){
      this.outOfBounds = true;
    }
  }
}