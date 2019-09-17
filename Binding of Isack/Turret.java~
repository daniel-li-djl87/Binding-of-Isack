import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
public class Turret extends Enemy{
  BufferedImage[] turretSprite;
  ArrayList<Bullet> firedBullets = new ArrayList<Bullet>();
  int totalBullets;
  public Turret (int health, int x, int y){
    super (health,x,y);
    boundingBox = new Rectangle ((int)x,(int)y,66,79);
    totalBullets = 0;
  }
  public void draw(Graphics g){
    try{
      BufferedImage turretSprite = ImageIO.read(new File("turret.png"));
      g.drawImage(turretSprite,(int)this.getX(), (int)this.getY(),null);
    }catch(Exception e) {};
  }
  public void moveUp(){
  }
  public void moveDown(){
  }
  public void moveLeft(){
  }
  public void moveRight(){
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