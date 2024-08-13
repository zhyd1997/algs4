import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size = 0;
    private int tempLevel = 0;

    private class Node {
        private Point2D key;
        private int level;
        private Node left, right;

        public Node(Point2D key, int level) {
            this.key = key;
            this.level = level;
        }
    }

    // construct an empty set of points
    public         KdTree()
    {}

    // is the set empty?
    public           boolean isEmpty()
    {
        return size() == 0;
    }

    // number of points in the set
    public               int size()
    {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public              void insert(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;
        root = insert(root, p);
    }
    private Node insert(Node x, Point2D p) {
        if (x == null) {
            size = size + 1;
            int level = tempLevel + 1;
            tempLevel = 0;
            return new Node(p, level);
        }

        double cmp;
        if (x.level % 2 != 0) {
            cmp = p.x() - x.key.x();
        } else {
            cmp = p.y() - x.key.y();
        }

        if (cmp < 0.0) {
            if (x.left == null) {
                tempLevel = x.level;
            }
            x.left = insert(x.left, p);
        } else {
            if (x.right == null) {
                tempLevel = x.level;
            }
            x.right = insert(x.right, p);
        }

        return x;
    }

    // does the set contain point p?
    public           boolean contains(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }
    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        if (x.key.equals(p)) {
            return true;
        }
        double cmp;
        if (x.level % 2 != 0) {
            cmp = p.x() - x.key.x();
        } else {
            cmp = p.y() - x.key.y();
        }

        if (cmp < 0.0) {
            return contains(x.left, p);
        } else {
            return contains(x.right, p);
        }
    }

    // draw all points to standard draw
    public              void draw()
    {
       Node temp = root;
       draw(temp, true, false, 1.0, 1.0);
    }
    private void draw(Node x, boolean isRoot, boolean isSmall, double xMax, double yMax) {
        if (x == null) {
            return;
        }

        double xCoordinate = x.key.x();
        double yCoordinate = x.key.y();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(xCoordinate, yCoordinate, 0.008);

        if (x.level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.BLUE);
            if (isRoot) {
                // do nothing
            } else if (isSmall) {
                StdDraw.line(xCoordinate, yCoordinate, 0.0, yCoordinate);
                StdDraw.line(xCoordinate, yCoordinate, xMax, yCoordinate);
            } else {
                double xMin = xMax;
                StdDraw.line(xCoordinate, yCoordinate, 1.0, yCoordinate);
                StdDraw.line(xCoordinate, yCoordinate, xMin, yCoordinate);
            }
        } else {
            StdDraw.setPenColor(StdDraw.RED);

            if (isRoot) {
                StdDraw.line(xCoordinate, yCoordinate, xCoordinate, 0.0);
                StdDraw.line(xCoordinate, yCoordinate, xCoordinate, 1.0);
            } else if (isSmall) {
                StdDraw.line(xCoordinate, yCoordinate, xCoordinate, yMax);
                StdDraw.line(xCoordinate, yCoordinate, xCoordinate, 0.0);
            } else {
                double yMin = yMax;
                StdDraw.line(xCoordinate, yCoordinate, xCoordinate, 1.0);
                StdDraw.line(xCoordinate, yCoordinate, xCoordinate, yMin);
            }
        }
        draw(x.left, false, true, xCoordinate, yCoordinate);
        draw(x.right, false, false, xCoordinate, yCoordinate);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) throw new IllegalArgumentException();
        return range(rect, root, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    private Iterable<Point2D> range(RectHV rect, Node x, double xMin, double yMin, double xMax, double yMax) {
        if (isEmpty()) return null;

        RectHV that = new RectHV(xMin, yMin, xMax, yMax);
        if (!rect.intersects(that)) return null;
        range(rect, x.left, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, x.key.x(), x.key.y());
        range(rect, x.right, x.key.x(), x.key.y(), Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        return nearest(p, root, Double.POSITIVE_INFINITY);
    }
    private Point2D nearest(Point2D p, Node x, double minDistance) {
        if (isEmpty()) return null;

        if (x == null) return null;

        if (x.left != null) {
            double distanceL = p.distanceTo(x.left.key);
            if (distanceL < minDistance) {
                return nearest(p, x.left, distanceL);
            }
        }
        if (x.right != null) {
            double distanceR = p.distanceTo(x.right.key);
            if (distanceR < minDistance) {
                return nearest(p, x.right, distanceR);
            }
        }
        return x.key;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args)
    {}
}
