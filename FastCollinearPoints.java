import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int count = 0;

    private Point start;
    private Point end;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // if the argument to the constructor is null
        if (points == null) {
            throw new IllegalArgumentException();
        }
        // if any point in the array is null
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
        // if the argument to the constructor contains a repeated point
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];

                if (p.compareTo(q) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        findAllLineSegments(points);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    private void findAllLineSegments(Point[] points) {
        LineSegment[] temp = new LineSegment[points.length];
        double[] slopes = new double[points.length];

        for (int i = 0; i < slopes.length; i++) {
            slopes[i] = -0.0;
        }

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            start = p;
            end = p;

            Point[] rest = new Point[points.length - i - 1];

            int index = 0;

            for (int j = i + 1; j < points.length; j++) {
                rest[index++] = points[j];
            }

            Arrays.sort(rest, p.slopeOrder());

            for (int j = 1; j < rest.length - 1; j++) {
                Point s = rest[j-1];
                Point q = rest[j];
                Point r = rest[j+1];

                double slope_q_p = q.slopeTo(p);
                double slope_r_p = r.slopeTo(p);
                double slope_s_p = s.slopeTo(p);
                boolean flag = false;

                for (int idx = 0; idx < slopes.length; idx++) {
                    double slope = slopes[idx];

                    if (Double.compare(slope, slope_q_p) == 0) {
                        flag = true;
                        break;
                    }
                }

                if (slope_q_p == slope_r_p && slope_r_p == slope_s_p && !flag) {
                    se(q);
                    se(r);
                    se(s);
                    if (j < rest.length - 2) {
                        Point t = rest[j+2];
                        double slope_t_p = t.slopeTo(p);

                        if (slope_s_p == slope_t_p) {
                            se(t);
                            slopes[i] = slope_q_p;
                            i += 1;
                            break;
                        }
                    }
                    slopes[i] = slope_q_p;
                }
            }

            if (start.compareTo(end) == 0) {
                temp[i] = null;
            } else {
                temp[i] = new LineSegment(start, end);
                count += 1;
            }
        }

        lineSegments = new LineSegment[count];

        int idx = 0;
        for (LineSegment lineSegment : temp) {
            if (lineSegment == null) continue;

            lineSegments[idx++] = lineSegment;
        }
    }

    private void se(Point that) {
        if (start.compareTo(that) > 0) {
            start = that;
        }

        if (end.compareTo(that) < 0) {
            end = that;
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
