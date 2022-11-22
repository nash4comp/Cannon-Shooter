import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Window extends PApplet{
  // create tempPos and tempDir for now
  protected static final int WALL = 1;
  protected static final int CIRCLE = 2;
  protected static final int RECT = 3;

  protected PVector tempPos = new PVector(50, this.height + 500);
  protected PVector tempDir = new PVector(1f, 1f).normalize();

  protected CannonBall ball = new CannonBall(tempPos, tempDir,this);
  protected int width = 1280;
  protected int height = 720;

  // turn = false means player1's turn.
  // turn = true means player2's turn.
  protected boolean turn = false;
//  protected boolean turn = true;
  protected boolean title = true;
  PImage img;
  protected int dashboardHeight = this.height - 130;

  PFont font;

  protected Player leftPlayer = new Player(new PVector(50,this.height - 200), this);
  protected Player rightPlayer = new Player(new PVector(width - 100,this.height - 200), this);
  protected Player currentPlayer;
  protected PVector wallPosition = new PVector(this.width / 2, this.dashboardHeight);

  protected int numberOfObstacles = 40;
  protected Obstacle wall = new Obstacle(1,false);
//  protected Obstacle obstacle = new Obstacle(10, true);

  protected ArrayList<Obstacle> obstacle = new ArrayList<>();
//  PVector circleVector = new PVector(random(this.height), random(this.width));

  protected ArrayList<PVector> obstacleVector = new ArrayList<>();
  private OnEventListner mListener;

  double obstacleSize;

  public void registerOnEventListener(OnEventListner mListener) {
    this.mListener = mListener;
  }

  public void afterFire() {
    System.out.println("Fire button pushed!");

    if(this.mListener != null) {
      mListener.onEvent();
    }
  }

  public void draw() {
//    if (this.title) {
//      showTitle();
//    }
//    Player currentPlayer;
    if (!this.turn) {
      currentPlayer = leftPlayer;
    } else {
      currentPlayer = rightPlayer;
    }
    background(10);
    leftPlayer.draw(this);
    rightPlayer.draw(this);
    ball.draw(currentPlayer.position, this);
    ball.move(this);
    drawAngle(currentPlayer, currentPlayer.angleDirection);
    drawDashboard();
    drawHp();
    drawFuel();
    wall.draw(WALL, wallPosition, obstacleSize, this);
    for (int i = 0; i <numberOfObstacles / 2; i++) {
      this.obstacleSize = 30;
      obstacle.get(i).draw(CIRCLE, obstacleVector.get(i),obstacleSize ,this);
    }
    for (int i = numberOfObstacles / 2; i <numberOfObstacles; i++) {
      this.obstacleSize = 10;
      obstacle.get(i).draw(RECT, obstacleVector.get(i),obstacleSize ,this);
    }
  }

  public void showTitle() {
    image(img, 0, 0);
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    this.title = !this.title;
  }

  public void drawDashboard() {
  textSize(20);
  fill(8, 190, 27);
  rect(0, dashboardHeight, this.width, 2);
  fill(3, 253, 247);
  text("Player1", 50, this.height - 100);
  text("Player2", this.width - 300, this.height - 100);
  textSize(18);
  fill(3, 140, 253);
  // for player1
  text("HP", 50, this.height - 60);
  text("Fuel", 50, this.height - 30);
  // for player2
  text("HP", this.width - 300, this.height - 60);
  text("Fuel", this.width - 300, this.height - 30);
  }


  public void drawHp() {
    // for player1
    if (leftPlayer.getHp() == 100) {
      this.fill(0, 204, 0);
    } else if ((leftPlayer.getFuel() < 100) && (leftPlayer.getFuel() >= 30)) {
      this.fill(249, 227, 41);
    } else if (leftPlayer.getFuel() < 30) {
      this.fill(246, 0, 0);
    }
    this.rect(100, this.height - 70, leftPlayer.getHp(), 10);

    // for player2
    if (rightPlayer.getHp() == 100) {
      this.fill(0, 204, 0);
    } else if ((rightPlayer.getFuel() < 100) && (rightPlayer.getFuel() >= 30)) {
      this.fill(249, 227, 41);
    } else if (rightPlayer.getFuel() < 30) {
      this.fill(246, 0, 0);
    }
    this.rect(this.width - 240, this.height - 70, rightPlayer.getHp(), 10);
  }

  public void drawFuel() {
    // for player1
    if (leftPlayer.getFuel() >= 70) {
      this.fill(118, 59, 0);
    } else if ((leftPlayer.getFuel() < 70) && (leftPlayer.getFuel() >= 30)) {
      this.fill(164, 82, 0);
    } else if (leftPlayer.getFuel() < 30) {
      this.fill(246, 0, 0);
    }
    this.rect(100, this.height - 40, leftPlayer.getFuel(), 10);

    // for player2
    if (rightPlayer.getFuel() >= 70) {
      this.fill(118, 59, 0);
    } else if ((rightPlayer.getFuel() < 70) && (rightPlayer.getFuel() >= 30)) {
      this.fill(164, 82, 0);
    } else if (rightPlayer.getFuel() < 30) {
      this.fill(246, 0, 0);
    }
    this.rect(this.width - 240, this.height - 40, rightPlayer.getFuel(), 10);
  }

  public void drawAngle(Player player, PVector angleDirection) {
    float xPos;
    float yPos;
    xPos = player.getPosition().x;
    yPos = player.getPosition().y;
    this.stroke(255,179,179);
    if (!this.turn) {
      this.line(xPos + player.width, yPos, xPos + (angleDirection.x) * 50, yPos - (angleDirection.y) * 50);
    } else {
      this.line(xPos, yPos, xPos - angleDirection.x * 40, yPos - angleDirection.y * 40);
    }
    this.stroke(0);
  }

  public void settings() {
    size(this.width, this.height);
    img = loadImage("title.jpg");

    // Initialize obstacles
    for (int i = 0; i < numberOfObstacles / 2; i++) {
      obstacle.add(new Obstacle(1,true));
      obstacleVector.add(new PVector(random(width), random(height - 250)));
    }
    for (int i = numberOfObstacles / 2; i < numberOfObstacles; i++) {
      obstacle.add(new Obstacle(1,false));
      obstacleVector.add(new PVector(random(width), random(height - 250)));
    }

  }
  @Override
  public void keyPressed(KeyEvent event) {
    super.keyPressed(event);
//    Player currentPlayer;
    if (!this.turn) {
      currentPlayer = leftPlayer;
    } else {
      currentPlayer = rightPlayer;
    }

    if ((currentPlayer == null) || (currentPlayer.fuel == 0)){
      if(event.getKeyCode() == ENTER) {
        currentPlayer.fire(currentPlayer, this);
      }
      return;
    }
    switch (event.getKeyCode()) {
      case RIGHT:
        currentPlayer.move(5);
        currentPlayer.decreaseFuel(2);
        break;
      case LEFT:
        currentPlayer.move(-5);
        currentPlayer.decreaseFuel(2);
        break;
      case UP:
        currentPlayer.setAngle(currentPlayer, 0.005F);
        currentPlayer.decreaseFuel(0.1F);
        break;
      case DOWN:
        currentPlayer.setAngle(currentPlayer, -0.005F);
        currentPlayer.decreaseFuel(0.1F);
        break;
      case ENTER:
        currentPlayer.fire(currentPlayer, this);
      default:
        break;
    }
  }

  public static void main(String[] args) {
    String[] processingArgs = {"processingWindow"};
    Window processingWindow = new Window();
    PApplet.runSketch(processingArgs, processingWindow);
  }
}
