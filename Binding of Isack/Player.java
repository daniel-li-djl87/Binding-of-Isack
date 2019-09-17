import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
import java.util.ArrayList;

class Player extends Life{ 
  static int movePixel; 
  // int xScr,yScr;
  String shootDirection;
  BufferedImage[] sprites;
  BufferedImage deadScreen;
  int currentStep;
  int currentSprite;
  boolean moveLeft, moveRight, moveUp, moveDown;
  boolean dead;
  Rectangle boundingBox;
  ArrayList<Tear> firedTears = new ArrayList<Tear>();
  int width = 28;
  int length = 37;
  int totalTears;
  String lastDirection;
  
  
  
  public Player(int health, int x, int y){ 
    super (health,x,y);
    loadSprites();
    currentSprite=0;
    currentStep=0;
    this.movePixel=4;
    this.moveLeft = false;
    this.moveRight = false;
    this.moveUp = false;
    this.moveDown = false;
    totalTears=0;
    lastDirection = "down";
    boundingBox=new Rectangle((int)x, (int)y, 28, 37);
    // this.xScr=25;
    // this.yScr=25;
  }
  
  public void loadSprites() { 
    try {
      deadScreen = ImageIO.read(new File("deadScreen.jpg"));
      sprites = new BufferedImage[38];
      //moving down
      sprites[0] = ImageIO.read(new File("backwards1.png"));
      sprites[1] = ImageIO.read(new File("backwards2.png"));
      sprites[2] = ImageIO.read(new File("backwards3.png"));
      sprites[3] = ImageIO.read(new File("backwards4.png"));
      sprites[4] = ImageIO.read(new File("backwards5.png"));
      sprites[5] = ImageIO.read(new File("backwards6.png"));
      sprites[6] = ImageIO.read(new File("backwards7.png"));
      sprites[7] = ImageIO.read(new File("backwards8.png"));
      sprites[8] = ImageIO.read(new File("backwards9.png"));
      
      //moving left
      sprites[9] = ImageIO.read(new File("left1.png"));
      sprites[10] = ImageIO.read(new File("left2.png"));
      sprites[11] = ImageIO.read(new File("left3.png"));
      sprites[12] = ImageIO.read(new File("left4.png"));
      sprites[13] = ImageIO.read(new File("left5.png"));
      sprites[14] = ImageIO.read(new File("left6.png"));
      sprites[15] = ImageIO.read(new File("left7.png"));
      sprites[16] = ImageIO.read(new File("left8.png"));
      sprites[17] = ImageIO.read(new File("left9.png"));
      sprites[18] = ImageIO.read(new File("left10.png"));
      
      //moving right
      sprites[19] = ImageIO.read(new File("right1.png"));
      sprites[20] = ImageIO.read(new File("right2.png"));
      sprites[21] = ImageIO.read(new File("right3.png"));
      sprites[22] = ImageIO.read(new File("right4.png"));
      sprites[23] = ImageIO.read(new File("right5.png"));
      sprites[24] = ImageIO.read(new File("right6.png"));
      sprites[25] = ImageIO.read(new File("right7.png"));
      sprites[26] = ImageIO.read(new File("right8.png"));
      sprites[27] = ImageIO.read(new File("right9.png"));
      sprites[28] = ImageIO.read(new File("right10.png"));
      
      //moving up
      sprites[29] = ImageIO.read(new File("forward1.png"));
      sprites[30] = ImageIO.read(new File("forward2.png"));
      sprites[31] = ImageIO.read(new File("forward3.png"));
      sprites[32] = ImageIO.read(new File("forward4.png"));
      sprites[33] = ImageIO.read(new File("forward5.png"));
      sprites[34] = ImageIO.read(new File("forward6.png"));
      sprites[35] = ImageIO.read(new File("forward7.png"));
      sprites[36] = ImageIO.read(new File("forward8.png"));
      sprites[37] = ImageIO.read(new File("forward9.png"));
      
      
      
      
    } catch(Exception e) {}
  }
  
  
  public void draw(Graphics g) { 
    g.drawImage(sprites[currentSprite],this.getX(),this.getY(),null);
  }
  
  public void update() {
    
    
    if(moveDown==true) { 
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentSprite++;
      currentStep++;
      if (currentSprite>=8) {
        currentSprite=0;
        //direction="stand";
      } else if (this.getY() < 585){
        movePixel = 3; 
        this.setY(this.getY()+movePixel);//--;
      } else {
        movePixel = 0;
        this.setY(this.getY()+movePixel);
      }
    }
    
    if(moveLeft==true) { 
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentSprite++;
      currentStep++;
      if (currentSprite>=18) {
        currentSprite=9;
        //direction="stand";
      } else if (this.getX()> 150){
        movePixel = 3; 
        this.setX(this.getX()-movePixel);//--;
      } else {
        movePixel = 0;
        this.setX(this.getX()-movePixel);
      }
    }
    
    if(moveRight==true) { 
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentSprite++;
      currentStep++;
      if (currentSprite>=28) {
        currentSprite=19;
        //direction="stand";
      } else if (this.getX() < 870){
        movePixel = 3; 
        this.setX(this.getX()+movePixel);//--;
      } else {
        movePixel = 0;
        this.setX(this.getX()+movePixel);
      }
    }
    
    if(moveUp==true) { 
      boundingBox=new Rectangle(this.getX(), this.getY(), width, length);
      currentSprite++;
      currentStep++;
      if (currentSprite>=37) {
        currentSprite=29;
        //direction="stand";
      } else if (this.getY() > 210){
        movePixel = 3; 
        this.setY(this.getY()-movePixel);//--;
      } else {
        movePixel = 0;
        this.setY(this.getY()-movePixel);
      }
    }
    
  }
  
  public void move(String movement) { 
    
    if (currentStep>5) {
      currentStep=0;
      movement="stand";
    }
    if (currentStep==0){
      currentStep++;
      if(moveDown ==true) {       
        currentSprite=0;
      } else if(moveLeft == true) {
        currentSprite=9;
      } else if(moveRight == true) {
        currentSprite=19;
      } else if(moveUp == true) {
        currentSprite=29;
      }
    }
  }
  
  public void shoot() {
    if (totalTears < 10){
      Tear tear = new Tear(this.getX(),this.getY());
      firedTears.add(tear);
      tear.fire(lastDirection);
      totalTears++;
    }
  }
  public void takeDamage(){
    this.setHealth(this.getHealth() - 1);
    System.out.println(this.getHealth());
    if ( this.getHealth() <= 0 ){
      dead = true;
    }
  }
}