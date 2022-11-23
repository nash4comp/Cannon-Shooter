
import processing.core.PVector;

import static processing.core.PApplet.println;

public class CannonBall implements IMovable, ICollidable{

  PVector position;
  PVector relativePosition;
  PVector direction;
  //type creates a new class?

  Window window;
  protected float speed = 1f;

  protected float width = (float) 20;

  protected float height = (float) 20;

  protected float radious = (float) 30 / 2;

  protected Player player;

  int fillColour = 255;


  public CannonBall(PVector position, PVector direction, Window window) {
    this.position = position;
    this.direction = direction;
    this.window = window;
    this.relativePosition = new PVector(0,0);
  }




  public boolean isHitPlayer(Player player) {
    // if the cannonball hits the other opponent, then loses the opponent's hp, and ends turn
    // if not, ends the turn
    if (this.position.dist(player.getPosition()) == 0) {
      System.out.println("touches player");
      return true;
    }

    return false;
  }


  public boolean outOfBounds(Window window) {
//    float distance = PVector.dist(this.position, window);
//    System.out.println(distance);
//    if (distance == 0) {
//      return true;
//    }
//    return false;



    if ((this.position.x + radious  > window.width
            || this.position.x < 0)
            || (this.position.y  < 0
            || this.position.y + radious >= window.dashboardHeight)) {
      return true;
    } else {
      return false;
    }
  }


  public void removeBall (CannonBall this) {
    this.draw(getPosition(), window);
  }

  public void bounce(float b) {
    this.direction.rotate(b);
  }

  // if the ball is out of bound then remove the ball
  public void move(Player currentPlayer, Window window) {
    //this method will be implemented from IMovable interface
    // cannonball moves toward in certain direction

//    if (isHitPlayer(player, window)) {
//      player.setHp(10);
//      System.out.println(player.getHp());
//    }

//    this.position = this.position.add(this.direction.mult(speed));
//    println(currentPlayer.getAngleVector(currentPlayer).toString());
    setDirection(currentPlayer.getAngleVector(currentPlayer));
    this.relativePosition = this.relativePosition.add(this.direction.mult(speed));
    if (outOfBounds(window)) {
      bounce((float)Math.PI / 4f);
    }

  }

  public void draw(PVector position, Window window) {
    window.fill(this.fillColour);
    window.ellipse(position.x + this.getRadius() + this.relativePosition.x,
            position.y - this.radious + this.relativePosition.y,
            this.width,
            this.height);
  }

  public float getRadius() {
    return this.radious;
  }

  @Override
  public boolean collided(ICollidable c) {
    return false;
  }

  @Override
  public float getWidth() {
    return this.width;
  }

  @Override
  public float getHeight() {
    return this.height;
  }

  public PVector getDirection(){
    return this.direction;
  }

  public void  setDirection(PVector direction){
    this.direction.x = direction.x;
    this.direction.y = direction.y;
  }

  @Override
  public void collideBehaviour(ICollidable c) {

  }

  @Override
  public PVector getPosition() {
    return this.position;
  }

  @Override
  public void setPosition(PVector position) {
    this.position = position;
  }

  @Override
  public PVector getVelocity() {
    return null;
  }

  @Override
  public PVector getPower() {
    return null;
  }
}
