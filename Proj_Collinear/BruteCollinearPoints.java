import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    //This array will hold of collinear segments
    private LineSegment[] collinearSegments;

    // create a dynamic array of segmenetd from all points
    private ArrayList<LineSegment> arraysegments = new ArrayList<LineSegment>(); // Create an ArrayList object

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        // Return exception if arguments is null;
        if (points == null) {throw new IllegalArgumentException("Error - null point");}

        for (int i = 0; i < points.length; i++) {
           	if (points[i] == null) throw new IllegalArgumentException("Error - null point");
        }

        // Copy all points to new array and work with copy
        Point[] copyofpoints = Arrays.copyOf(points, points.length);
       
        // Sort array of points
        Arrays.sort(copyofpoints);

        // Check for repeated points
        for (int counter = 1; counter < copyofpoints.length; counter++) {
            if (copyofpoints[counter - 1].compareTo(copyofpoints[counter]) == 0)
                throw new IllegalArgumentException("Error - repeated point");
        }
        
        int N = points.length;
        double s1 = 0.0;
        double s2 = 0.0;
        double s3 = 0.0;
        double s4 = 0.0;

        for (int i = 0; i < N - 3; i++) {
            for (int j = i + 1; j < N - 2; j++) {
                for (int k = j + 1; k < N - 1; k++) {
                    for (int l = k + 1; l < N; l++) {
                        // Compare p to q, q to s, s to t, t to u and if all 3 have same slope
                        // add p and t
                        s1 = copyofpoints[i].slopeTo(copyofpoints[j]);
                        s2 = copyofpoints[i].slopeTo(copyofpoints[k]);
                        s3 = copyofpoints[i].slopeTo(copyofpoints[l]);

                        // check all 3 slopes are the same
                        if ((s1 == s2) && (s2 == s3)) {
                            // add segment to arraylist
                            // use first and last point (i and l)
                            LineSegment oneSegment = new LineSegment(copyofpoints[i], copyofpoints[l]);
                            //  if (!segmentsList.contains(tempLineSegment))
                            if (!arraysegments.contains(oneSegment)) {
                                arraysegments.add(oneSegment);
                            }
                        }
                    }

                }
            }
        }
        // JUAN collinearSegments = arraysegments.toArray(new LineSegment[arraysegments.size()]);

    }

    public int numberOfSegments()        // the number of line segments
    {
        return arraysegments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
        // JUAN return collinearSegments;
    	return arraysegments.toArray(new LineSegment[arraysegments.size()]);
    }
    
  
}
