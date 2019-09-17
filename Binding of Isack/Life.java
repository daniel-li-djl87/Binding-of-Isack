abstract public class Life extends Entity{
  
  private int health;
  
  public Life ( int health, int x, int y ){
    super (x,y);
    this.health = health;
  }
  public int getHealth(){
    return this.health;
  }
  public void setHealth(int health){
    this.health = health;
  }
  
  
}

