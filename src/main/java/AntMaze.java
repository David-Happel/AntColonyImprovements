import processing.core.PApplet;

public class AntMaze extends PApplet {

  int gridWidth = 25;
  int gridHeight = 25;
  int scale;

  Maze maze;
  Ant ant;

  public void settings() {
    size(800, 800);

    scale = width / gridWidth;
    maze = new Maze(gridWidth, gridHeight, new Square(0, 0), new Square(24, 24));
    maze = new Maze(gridWidth, gridHeight, new Square(0, 0), new Square(24, 24));
    maze = new Maze(gridWidth, gridHeight, new Square(0, 0), new Square(24, 24));
  }

  public void draw() {
    background(255);
    noStroke();
    for (int x = 0; x < gridWidth; x++) {
      for (int y = 0; y < gridHeight; y++) {
        float pher = (float) Math.max(0f, 255f-maze.pheromones[x][y]*10f);
        fill(255,pher,pher);
        if(maze.terrain[x][y] == -1){
          fill(0);
        }
        rect(x*scale,y*scale, (x+1)*scale, (y+1)*scale);
      }
    }
    strokeWeight(6);
    stroke(0, 255, 0);
    point((float) (maze.start.x + 0.5) * scale, (float) (maze.start.y + 0.5) * scale);
    stroke(255, 0, 0);
    point((float) (maze.goal.x + 0.5) * scale, (float) (maze.goal.y + 0.5) * scale);

    //stroke(0, 0, 255);
    //point((float) (ant.getPos().x + 0.5) * scale, (float) (ant.getPos().y + 0.5) * scale);
  }

  public void keyPressed() {
    if (key == 32) {
      ant = new Ant(new Square(maze.start.x, maze.start.y));
      while(!ant.bestMove(maze)){

      }
      maze.evaporate();
    }
  }

  public void mouseClicked(){
    int x = floor(mouseX/scale);
    int y = floor(mouseY/scale);
    if(maze.terrain[x][y] != -1){
      maze.terrain[x][y] = -1;
    } else {
      maze.terrain[x][y] = 0;
    }

  }

  public static void main(String... args) {
    PApplet.main("AntMaze");
  }
}
