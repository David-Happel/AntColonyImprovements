import java.util.*;

import com.google.common.collect.EvictingQueue;

public class Ant {
  private Square pos;
  private Set<Square> history;
  private Queue<Square> recent;

  public Ant(Square pos) {
    this.pos = pos;
    this.history = new HashSet<>();
    this.recent = EvictingQueue.create(10);
  }

  public boolean bestMove(Maze maze) {
    List<Direction> moves = maze.getDirections(pos);
    moves.removeIf(m -> recent.contains(m.apply(pos)));

    Direction chosen;
    if(moves.isEmpty()){
      chosen = chooseRandom(maze.getDirections(pos));
    }else{
      chosen = choose(moves, maze);

    }

    pos = chosen.apply(pos);
    history.add(pos);
    recent.add(pos);
    return checkFinnish(maze);
  }

  public boolean checkFinnish(Maze maze) {
    if (pos.equals(maze.goal)) {
      maze.applyPheromones(history);
      return true;
    } else {
      return false;
    }
  }

  public Direction choose(List<Direction> moves, Maze maze) {
    Double[] directionModifiers = new Double[moves.size()];
    for (int i = 0; i < moves.size(); i++) {
      Direction dir = moves.get(i);
      Square dest = dir.apply(pos);
      directionModifiers[i] = pos.dist(maze.goal) - dest.dist(maze.goal);
    }
    directionModifiers = normalize(directionModifiers);

    Double[] pheromoneModifiers = new Double[moves.size()];
    for (int i = 0; i < moves.size(); i++) {
      Direction dir = moves.get(i);
      Square dest = dir.apply(pos);
      pheromoneModifiers[i] = maze.pheromones[dest.x][dest.y];
    }
    pheromoneModifiers = normalize(pheromoneModifiers);

    Double[] scores = new Double[moves.size()];
    for (int i = 0; i < moves.size(); i++) {
      scores[i] = 0.5 + 0.5* directionModifiers[i] + 1.5*pheromoneModifiers[i];
    }

    return moves.get(randomWithScores(scores));
  }

  public Direction chooseRandom(List<Direction> moves) {
    Random rand = new Random();
    int randomI = rand.nextInt(moves.size());
    return moves.get(randomI);
  }

  private Double[] normalize(Double[] in) {
    double min = Collections.min(Arrays.asList(in));
    double max = Collections.max(Arrays.asList(in));
    Double[] res = new Double[in.length];
    if (min == max) {
      for (int i = 0; i < in.length; i++) {
        res[i] = 1d / in.length;
      }
    } else {
      for (int i = 0; i < in.length; i++) {
        res[i] = (in[i] - min) / (max - min);
      }
    }
    return res;
  }

  private Double[] getProbabilities(Double[] in) {
    double total = 0;
    for (int i = 0; i < in.length; i++) {
      total += in[i];
    }
    Double[] res = new Double[in.length];
    for (int i = 0; i < in.length; i++) {
      res[i] = in[i] / total;
    }
    return res;
  }

  private int randomWithScores(Double[] scores) {
    scores = getProbabilities(scores);
    double random = Math.random();

    // for(Double d: scores){
    //   System.out.println(d);
    // }
    double acc = 0;
    for (int i = 0; i < scores.length; i++) {
      acc += scores[i];
      if (acc >= random) {
        return i;
      }
    }
    return -1;
  }

  public Square getPos() {
    return this.pos;
  }

}
