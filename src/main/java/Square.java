import java.util.Objects;

public class Square {
  int x;
  int y;

  public Square(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Square add(int x, int y) {
    return new Square(this.x + x, this.y + y);
  }

  public Square add(Square other) {
    return new Square(this.x + other.x, this.y + other.y);
  }

  public double dist(Square other) {
    return Math.hypot(x - other.x, y - other.y);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Square square = (Square) o;
    return x == square.x &&
        y == square.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
