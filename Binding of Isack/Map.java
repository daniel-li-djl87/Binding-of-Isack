import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
import java.util.ArrayList;

class GameWindow extends JFrame { 
  
  //Window constructor
  public GameWindow() { 
    setTitle("Binding of Isack");
    //setSize(1280,1024);  // set the size of my window to 400 by 400 pixels
    setResizable(true);  // set my window to allow the user to resize it
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set the window up to end the program when closed
    getContentPane().add( new GamePanel());
    pack(); //makes the frame fit the contents
    setVisible(true);
  }
  
  
  
// An inner class representing the panel on which the game takes place
  static class GamePanel extends JPanel implements KeyListener{
    
    Map map;  
    FrameRate framerate;
    Maw maw1;
    Maw maw2;
    Maw maw3;
    Player player;
    Maggot maggot1;
    Maggot maggot2;
    Maggot maggot3;
    Maggot maggot4;
    Zombie zombie1;
    Zombie zombie2;
    Turret turret1;
    Turret turret2;
    Worm worm1;
    ghostKnight gk1;
    ghostKnight gk2;
    ghostKnight gk3;
    String movement;
    Heart heart1;
    Key key1;
    Coin coin1;
    int roomNum;
    int numHearts;
    int numCoins;
    int numKeys;
    int itemGenerator;
    Boss boss;
    
    
    //constructor
    public GamePanel() { 
      setPreferredSize(new Dimension(1024,768));
      addKeyListener(this);
      setFocusable(true);
      requestFocusInWindow();
      map = new Map(1024,768, player);
      framerate = new FrameRate();
      player = new Player (100, 500, 500);
      roomNum = 0;
      movement = "stand";
      numHearts = player.getHealth();
      numCoins = 0;
      numKeys = 0;
      //player.xPos=25;
      //player.yPos=25;
    }
    
    public void paintComponent(Graphics g) {
      if ( numHearts > 0 ){
        super.paintComponent(g); //required to ensure the panel si correctly redrawn
        //update the content
        if ( roomNum == 0 ){
          player.update();
          map.draw(g);
          framerate.update();
          framerate.draw(g,10,10);
          g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
          g.setColor(Color.WHITE);
          g.drawString(String.valueOf(numHearts), 830, 90);
          player.draw(g);
          if ( player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209){
            player.setX(500);
            player.setY(584);
            roomNum = 1;
            worm1 = new Worm (1,400,400);
            itemGenerator = ((int)(Math.random()*3));
            if ( itemGenerator == 0){
              heart1 = new Heart(500,500);
            } else if ( itemGenerator == 1){
              heart1 = new Heart(500,500);
            } else if ( itemGenerator == 2){
              heart1 = new Heart(500,500);
            }
          }
          for (Tear tear : player.firedTears){
            tear.boundaryCheck();
            if (tear.outOfBounds == true){
            }else{
              tear.move();
              tear.draw(g);
            }
          }
        } else if ( roomNum == 1 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 80);
          
          if (worm1 == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            roomNum = 2;
            maggot1 = new Maggot (1,400,400);
            maggot2 = new Maggot (1,300,400);
            maggot3 = new Maggot (1,500,400);
            maggot4 = new Maggot (1,600,400);
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (worm1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(worm1.boundingBox)){
                  worm1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( worm1 != null ){
              if (player.boundingBox.intersects (worm1.boundingBox)){
                player.takeDamage();
              }
              if ( worm1.getHealth() > 0 ){
                worm1.draw(g);
                worm1.update();
              } else {
                worm1 = null;
              }
            }
            
          }
          
        } else if ( roomNum == 2 ){
          numHearts = player.getHealth();
          if (maggot1 == null && maggot2 == null && maggot3 == null && maggot4 == null &&(player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            roomNum = 3;
            zombie1 = new Zombie (1,200, 200);
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (maggot1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maggot1.boundingBox)){
                  maggot1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (maggot2 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maggot2.boundingBox)){
                  maggot2.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (maggot3 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maggot3.boundingBox)){
                  maggot3.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (maggot4 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maggot4.boundingBox)){
                  maggot4.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( maggot1 != null ){
              if (player.boundingBox.intersects (maggot1.boundingBox)){
                player.takeDamage();
              }
              if ( maggot1.getHealth() > 0 ){
                maggot1.draw(g);
                maggot1.update();
              } else {
                maggot1 = null;
              }
            }
            if ( maggot2 != null ){
              if (player.boundingBox.intersects (maggot2.boundingBox)){
                player.takeDamage();
              }
              if ( maggot2.getHealth() > 0 ){
                maggot2.draw(g);
                maggot2.update();
              } else {
                maggot2 = null;
              }
            }
            if ( maggot3 != null ){
              if (player.boundingBox.intersects (maggot3.boundingBox)){
                player.takeDamage();
              }
              if ( maggot3.getHealth() > 0 ){
                maggot3.draw(g);
                maggot3.update();
              } else {
                maggot3 = null;
              }
            }
            if ( maggot4 != null ){
              if (player.boundingBox.intersects (maggot4.boundingBox)){
                player.takeDamage();
              }
              if ( maggot4.getHealth() > 0 ){
                maggot4.draw(g);
                maggot4.update();
              } else {
                maggot4 = null;
              }
            }
            
          }
          
        }else if ( roomNum == 3 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 80);
          if (zombie1 == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            roomNum = 4;
            turret1 = new Turret (1, 500, 300);
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (zombie1 != null&&tear.destroyed==false){
                if (tear.boundingBox.intersects(zombie1.boundingBox)){
                  zombie1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( zombie1 != null ){
              if (player.boundingBox.intersects (zombie1.boundingBox)){
                player.takeDamage();
              }
              if ( zombie1.getHealth() > 0 ){
                zombie1.draw(g);
                zombie1.update(player.getX(), player.getY());
              } else {
                zombie1 = null;
              }
            }
            
          }
          
        } else if ( roomNum == 4 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 90);
          if (turret1 == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            gk1 = new ghostKnight (1, 500, 300);
            gk2 = new ghostKnight (1, 700, 300);
            gk3 = new ghostKnight (1, 300, 300);
            roomNum = 5;
            
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (turret1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(turret1.boundingBox)){
                  turret1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( turret1 != null ){
              if (player.boundingBox.intersects (turret1.boundingBox)){
                player.takeDamage();
              }
              if ( turret1.getHealth() > 0 ){
                turret1.draw(g);
                turret1.shoot(player.getX(), player.getY());
                for (Bullet bullet : turret1.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    turret1.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      turret1.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,33,40);
                    }
                  }
                }
              } else {
                turret1 = null;
              }
            }
            
          }
          
        } else if ( roomNum == 5 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 90);
          if (gk1 == null && gk2 == null && gk3 == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            roomNum = 6;
            turret1 = new Turret (1, 500, 400);
            zombie1 = new Zombie (1, 200, 200);
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (gk1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(gk1.boundingBox)){
                  gk1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (gk2 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(gk2.boundingBox)){
                  gk2.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (gk3 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(gk3.boundingBox)){
                  gk3.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( gk1 != null ){
              if (player.boundingBox.intersects (gk1.boundingBox)){
                player.takeDamage();
              }
              if ( gk1.getHealth() > 0 ){
                gk1.draw(g);
                gk1.update(player.getX(), player.getY());
              } else {
                gk1 = null;
              }
            }
            if ( gk2 != null ){
              if (player.boundingBox.intersects (gk2.boundingBox)){
                player.takeDamage();
              }
              if ( gk2.getHealth() > 0 ){
                gk2.draw(g);
                gk2.update(player.getX(), player.getY());
              } else {
                gk2 = null;
              }
            }
            if ( gk3 != null ){
              if (player.boundingBox.intersects (gk3.boundingBox)){
                player.takeDamage();
              }
              if ( gk3.getHealth() > 0 ){
                gk3.draw(g);
                gk3.update(player.getX(), player.getY());
              } else {
                gk3 = null;
              }
            }
            
          }
          
        } else if ( roomNum == 6 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 90);
          if (turret1 == null && zombie1 == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            maw1 = new Maw (1, 500, 300);
            turret1 = new Turret (1, 300, 300);
            turret2 = new Turret (1, 600, 300);
            roomNum = 7;
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (turret1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(turret1.boundingBox)){
                  turret1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (zombie1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(zombie1.boundingBox)){
                  zombie1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( turret1 != null ){
              if (player.boundingBox.intersects (turret1.boundingBox)){
                player.takeDamage();
              }
              if ( turret1.getHealth() > 0 ){
                turret1.draw(g);
                turret1.shoot(player.getX(), player.getY());
                for (Bullet bullet : turret1.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    turret1.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      turret1.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,33,40);
                    }
                  }
                }
              } else {
                turret1 = null;
              }
            }
            if ( zombie1 != null ){
              if (player.boundingBox.intersects (zombie1.boundingBox)){
                player.takeDamage();
              }
              if ( zombie1.getHealth() > 0 ){
                zombie1.draw(g);
                zombie1.update(player.getX(), player.getY());
              } else {
                zombie1 = null;
              }
            }
            
          }
          
        } else if ( roomNum == 7 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 90);
          if (maw1 == null && turret1 == null && turret2 == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            roomNum = 8;
            maw1 = new Maw (1, 200, 500);
            maw2 = new Maw (1, 300, 600);
            maw3 = new Maw (1, 400, 500);
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (maw1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maw1.boundingBox)){
                  maw1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }               
              if (turret1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(turret1.boundingBox)){
                  turret1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (turret2 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(turret2.boundingBox)){
                  turret2.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( maw1 != null ){
              if (player.boundingBox.intersects (maw1.boundingBox)){
                player.takeDamage();
              }
              if ( maw1.getHealth() > 0 ){
                maw1.draw(g);
                maw1.shoot(player.getX(), player.getY());
                maw1.update(player.getX(), player.getY());
                for (Bullet bullet : maw1.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    maw1.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      maw1.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,15,15);
                    }
                  }
                }
              } else {
                maw1 = null;
              }
            }
            
            if ( turret1 != null ){
              if (player.boundingBox.intersects (turret1.boundingBox)){
                player.takeDamage();
              }
              if ( turret1.getHealth() > 0 ){
                turret1.draw(g);
                turret1.shoot(player.getX(), player.getY());
                for (Bullet bullet : turret1.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    turret1.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      turret1.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,15,15);
                    }
                  }
                }
              } else {
                turret1 = null;
              }
            }
            if ( turret2 != null ){
              if (player.boundingBox.intersects (turret2.boundingBox)){
                player.takeDamage();
              }
              if ( turret2.getHealth() > 0 ){
                turret2.draw(g);
                turret2.shoot(player.getX(), player.getY());
                for (Bullet bullet : turret2.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    turret2.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      turret2.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,15,15);
                    }
                  }
                }
              } else {
                turret2 = null;
              }
            }
            
            
            
          }
          
        } else if ( roomNum == 8 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 90);
          if (maw1 == null && turret1 == null && turret2 == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            roomNum = 9;
            boss = new Boss(1,500,300);
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (maw1 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maw1.boundingBox)){
                  maw1.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }               
              if (maw2 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maw2.boundingBox)){
                  maw2.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
              if (maw3 != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(maw3.boundingBox)){
                  maw3.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( maw1 != null ){
              if (player.boundingBox.intersects (maw1.boundingBox)){
                player.takeDamage();
              }
              if ( maw1.getHealth() > 0 ){
                maw1.draw(g);
                maw1.shoot(player.getX(), player.getY());
                maw1.update(player.getX(), player.getY());
                for (Bullet bullet : maw1.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    maw1.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      maw1.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,15,15);
                    }
                  }
                }
              } else {
                maw1 = null;
              }
            }
            if ( maw2 != null ){
              if (player.boundingBox.intersects (maw2.boundingBox)){
                player.takeDamage();
              }
              if ( maw2.getHealth() > 0 ){
                maw2.draw(g);
                maw2.shoot(player.getX(), player.getY());
                maw2.update(player.getX(), player.getY());
                for (Bullet bullet : maw2.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    maw2.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      maw2.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,15,15);
                    }
                  }
                }
              } else {
                maw2 = null;
              }
            }
            if ( maw3 != null ){
              if (player.boundingBox.intersects (maw3.boundingBox)){
                player.takeDamage();
              }
              if ( maw3.getHealth() > 0 ){
                maw3.draw(g);
                maw3.shoot(player.getX(), player.getY());
                maw3.update(player.getX(), player.getY());
                for (Bullet bullet : maw3.firedBullets){
                  if (bullet.destroyed ==false&&bullet.boundingBox.intersects(player.boundingBox)){
                    maw3.totalBullets--;
                    bullet.destroyed =true;
                    player.takeDamage();
                  }
                  if (bullet.destroyed==false){
                    bullet.boundaryCheck();
                    if (bullet.outOfBounds==true){
                      maw3.totalBullets--;
                      bullet.destroyed=true;
                    }
                    else{
                      bullet.move();
                      bullet.draw(g,15,15);
                    }
                  }
                }
              } else {
                maw3 = null;
              }
            }
            
            
            
            
          }
          
        } else if ( roomNum == 9 ){
          numHearts = player.getHealth();
          g.drawString(String.valueOf(numHearts), 830, 90);
          if (boss == null && (player.getX() <= 518 && player.getX() >= 500 && player.getY() == 209)){
            player.setX(500);
            player.setY(584);
            roomNum = 10;
          }else {
            player.update();
            map.newRoom(g);
            framerate.update();
            framerate.draw(g,10,10);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(numHearts), 830, 90);
            player.draw(g);
            for (Tear tear : player.firedTears){
              tear.boundaryCheck();
              if (tear.destroyed==false){
                if(tear.outOfBounds==true){
                  player.totalTears --;
                  tear.destroyed = true;
                  tear.destroyed = true;
                }else{
                  tear.move();
                  tear.draw(g);
                }
              }
              if (boss != null&&tear.destroyed==false ){
                if (tear.boundingBox.intersects(boss.boundingBox)){
                  boss.takeDamage();
                  player.totalTears --;
                  tear.destroyed = true;
                }
              }
            }
            if ( boss != null ){
              if (player.boundingBox.intersects (boss.boundingBox)){
                player.takeDamage();
              }
              if ( boss.getHealth() > 0 ){
                boss.draw(g);
                boss.update(player.getX(),player.getY());
                boss.shoot((int)player.getX(),(int)player.getY());
                for (BossBullet bossbullet : boss.firedBullets){
                  if (bossbullet.destroyed ==false&&bossbullet.boundingBox.intersects(player.boundingBox)){
                    boss.totalBullets--;
                    player.setHealth(boss.attack(player.getHealth()));
                    bossbullet.destroyed =true;
                  }
                  if (bossbullet.destroyed==false){
                    bossbullet.boundaryCheck();
                    if (bossbullet.outOfBounds==true){
                      boss.totalBullets--;
                      bossbullet.destroyed=true;
                    }
                    else{
                      bossbullet.move();
                      bossbullet.draw(g);
                    }
                  }
                }
              } else {
                boss = null;
              }
            }
            
          }
          
        } 
        //request a repaint
        repaint();
        if (roomNum == 10){
          map.winGame(g);
        }
      } else {
        map.loseGame(g);
      }
    }
    public void keyTyped(KeyEvent e) {      
      //Move Player
      
    }
    
    public void keyPressed(KeyEvent e) {   
      if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
        movement = "left";
        player.moveLeft = true;
        player.moveDown = false;
        player.moveRight = false;
        player.moveUp = false;
        player.move("left");
        player.lastDirection = "left";
        //map.playerX--;
      } else if(e.getKeyChar() == 's' ){
        movement = "down";
        player.moveDown = true;
        player.moveLeft = false;
        player.moveRight = false;
        player.moveUp = false;
        player.move("down");
        player.lastDirection = "down";
        //map.playerY++;
      } else if(e.getKeyChar() == 'd' ){
        movement = "right";
        player.moveRight = true;
        player.moveDown = false;
        player.moveLeft = false;
        player.moveUp = false;
        player.move("right");
        player.lastDirection = "right";
        //map.playerX++;
      } else if(e.getKeyChar() == 'w' ){
        movement = "up";
        player.moveUp = true;
        player.moveRight = false;
        player.moveDown = false;
        player.moveLeft = false;
        player.move("up");
        player.lastDirection = "up";
        //map.playerY--;
      }
      //player shoots
      if (e.getKeyChar() == ' ' ){
        player.shoot();
      }
    }
    public void keyReleased(KeyEvent e) {
      if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
        movement = "left";
        player.moveLeft = false;
        player.moveRight = false;
        player.moveDown = false;
        player.moveUp = false;
        //map.playerX--;
      } else if(e.getKeyChar() == 's' ){
        movement = "down";
        player.moveLeft = false;
        player.moveRight = false;
        player.moveDown = false;
        player.moveUp = false;
        //map.playerY++;
      } else if(e.getKeyChar() == 'd' ){
        movement = "right";
        player.moveLeft = false;
        player.moveRight = false;
        player.moveDown = false;
        player.moveUp = false;
        //map.playerX++;
      } else if(e.getKeyChar() == 'w' ){
        movement = "up";
        player.moveLeft = false;
        player.moveRight = false;
        player.moveDown = false;
        player.moveUp = false;
        //map.playerY--;
      }
      
      //player shoots
    }  
    
    
  }
}


class Map { 
  //int playerX, playerY;
  Player player;
  
  
  public Map(int xResolution,int yResolution, Player player) { 
    this.player=player;
    //playerX = 25;
    //playerY = 25; 
    
  }
  
  public void draw(Graphics g) { 
    try{
      BufferedImage dungeon = ImageIO.read(new File("startingRoom.png"));
      g.drawImage(dungeon,0,0,null);
    } catch(Exception e) {};
  }
  
  public void newRoom(Graphics g) { 
    try{
      BufferedImage dungeon = ImageIO.read(new File("map.png"));
      g.drawImage(dungeon,0,0,null);
    } catch(Exception e) {};
  }
  
  public void loseGame(Graphics g){
    try{
      BufferedImage deadScreen = ImageIO.read(new File("deadScreen.jpg"));
      g.drawImage(deadScreen,0,0,null);
    } catch(Exception e) {};
  }
  
  public void winGame(Graphics g){
    try{
      BufferedImage winScreen = ImageIO.read(new File("winScreen.png"));
      g.drawImage(winScreen,0,0,null);
    } catch(Exception e) {};
  }
}