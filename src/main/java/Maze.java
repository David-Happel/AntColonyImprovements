import java.util.*;

public class Maze {
  private int width;
  private int height;
  double[][] pheromones;
  double[][] terrain;

  Square start;
  Square goal;


  public Maze(int width, int height, Square start, Square goal){
    this.width = width;
    this.height = height;
    this.start = start;
    this.goal = goal;
    this.pheromones = new double[width][height];
    this.terrain = new double[width][height];
  }

  public void applyPheromones(Set<Square> path){
    double amount = 15f/Math.pow(path.size(),0.5);
    for (Square s : path){
      pheromones[s.x][s.y] += amount;
    }
  }

  public void evaporate(){
    for (int x = 0; x < pheromones.length; x++) {
      for (int y = 0; y < pheromones[x].length; y++) {
        pheromones[x][y] = pheromones[x][y] * 0.95;
      }
    }
  }

  public List<Direction> getDirections(Square pos){
    List<Direction> res = new ArrayList<>();
    for(Direction d : Direction.values()){
      Square next = d.apply(pos);
      if(next.x >= 0 && next.y >= 0 && next.x < width && next.y < height && terrain[next.x][next.y] != -1){
        res.add(d);
      }
    }
    return res;
  }
}
