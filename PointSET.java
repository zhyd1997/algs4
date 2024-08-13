import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;

public class PointSET {
    private SET<Point2D> points;

    public         PointSET()                               // construct an empty set of points
    {
        points = new SET<Point2D>();
    }
    public           boolean isEmpty()                      // is the set empty?
    {
        return points.isEmpty();
    }
    public               int size()                         // number of points in the set
    {
        return points.size();
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        validatePoint(p);
        if (!contains(p)) {
            points.add(p);
        }
    }
    public           boolean contains(Point2D p)            // does the set contain point p?
    {
        validatePoint(p);
        return points.contains(p);
    }
    public              void draw()                         // draw all points to standard draw
    {
        for (Point2D p : points) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException();
        return null;
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        validatePoint(p);

        if (isEmpty()) {
            return null;
        }

        Point2D result = p;
        double distance = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double t = point.distanceTo(p);
            if (t < distance) {
                distance = t;
                result = point;
            }
        }

        return result;
    }

    private void validatePoint(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {}
}
