import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
class Tear extends Entity{
  private int tearSpeed;
  String direction;
  Player player;
  Boolean outOfBounds;
  Boolean destroyed;
  BufferedImage[] tearSprite;
  Rectangle boundingBox;
  
  public Tear(int x, int y) {
    super(x,y);
    this.tearSpeed = 6;
    this.outOfBounds = false;
    destroyed = false;
    boundingBox = new Rectangle ((int) x,(int) y,9,8);
  }
  public void fire(String direction){
    this.direction = direction;
  }
  public void move(){
    if (direction=="down"){
      this.setY(this.getY() + this.tearSpeed);
    } else if (direction=="left"){
      this.setX(this.getX() - this.tearSpeed);
    } else if (direction =="right"){
      this.setX(this.getX() + this.tearSpeed);
    }else if (direction =="up"){
      this.setY(this.getY() - this.tearSpeed);
    }
    boundingBox.x = this.getX();
    boundingBox.y = this.getY();
  }  
  public void draw(Graphics g) { 
    try{
      BufferedImage tearSprite = ImageIO.read(new File("tear.png"));
      if (direction=="down"){
        g.drawImage(tearSprite,this.getX()+12, this.getY(),null);
      } else if (direction=="left"){
        g.drawImage(tearSprite,this.getX(), this.getY()+3,null);
      } else if (direction =="right"){
        g.drawImage(tearSprite,this.getX(), this.getY()+3,null);
      }else if (direction =="up"){
        g.drawImage(tearSprite,this.getX()+12, this.getY(),null);
      }
    }catch(Exception e) {};
  }
  public void boundaryCheck(){
    if (this.getX()<150||this.getX()>870||this.getY()<210||this.getY()>585){
      this.outOfBounds = true;
    }
  }
}