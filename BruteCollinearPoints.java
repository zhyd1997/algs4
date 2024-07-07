import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * A program that examines 4 points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments.
 * <p>
 * To check whether the 4 points p, q, r, and s are collinear,
 * check whether the three slopes between p and q, between p and r, and between p and s are all equal.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int count = 0;

    private Point start;
    private Point end;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        getLineSegments(points);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    private void getLineSegments(Point[] points) {
        LineSegment[] temp = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            start  = p;
            end = p;

            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];

                se(q);

                double slope_p_q = p.slopeTo(q);
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    double slope_p_r = p.slopeTo(r);

                    if (slope_p_q != slope_p_r) continue;

                    se(r);

                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        double slope_p_s = p.slopeTo(s);

                        if (slope_p_r == slope_p_s) {
                            se(s);

                            LineSegment lineSegment = new LineSegment(start, end);

                            temp[count++] = lineSegment;
                            break;
                        }
                    }
                }
            }
        }

        LineSegment[] duplicateLS = new LineSegment[count];

        int index = 0;
        for (int i = count - 1; i >= 0; i--) {
            duplicateLS[index++] = temp[i];
        }

        lineSegments = deduplicate(duplicateLS);
    }

    private void se(Point that) {
        if (start.compareTo(that) > 0) {
            start = that;
        }

        if (end.compareTo(that) < 0) {
            end = that;
        }
    }

    private LineSegment[] deduplicate(LineSegment[] input) {
        LineSegment[] output = input;

        int len = 0;
        for (int i = 0; i < input.length; i++) {
            LineSegment l1 = input[i];

            for (int j = i + 1; j < input.length; j++) {
                LineSegment l2 = input[j];

                if (l1.toString().equals(l2.toString())) {
                    i += 1;
                    len += 1;
                    break;
                }

                if (j == input.length - 1) {
                    len += 1;
                }
            }
        }

        output = new LineSegment[len];
        for (int i = 0; i < input.length; i++) {
            LineSegment l1 = input[i];

            for (int j = i + 1; j < input.length; j++) {
                LineSegment l2 = input[j];

                if (l1.toString().equals(l2.toString())) {
                    i += 1;
                    output[--len] = l1;
                    break;
                }

                if (j == input.length - 1) {
                    output[--len] = l1;
                }
            }
        }

        return output;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
