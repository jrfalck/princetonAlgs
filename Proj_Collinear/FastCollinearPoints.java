import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    //This array will hold of collinear segments
    private LineSegment[] collinearSegments;

    // create a dynamic array of segmenetd from all points
    private ArrayList<LineSegment> arraysegments = new ArrayList<LineSegment>(); // Create an ArrayList object

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points

        // Return exception if arguments is null;
        if (points == null) throw new IllegalArgumentException("Error - null argument");

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

// *********************************************************
        for (int i = 0; i < copyofpoints.length - 3; i++) {
            Arrays.sort(copyofpoints);  

            Arrays.sort(copyofpoints, copyofpoints[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < copyofpoints.length; last++) {
                // find last collinear to p point
                while (last < copyofpoints.length
                        && Double.compare(copyofpoints[p].slopeTo(copyofpoints[first]), copyofpoints[p].slopeTo(copyofpoints[last])) == 0) {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && copyofpoints[p].compareTo(copyofpoints[first]) < 0) {
                    
                    arraysegments.add(new LineSegment(copyofpoints[p], copyofpoints[last - 1]));
                }
                // Try to find next
                first = last;
            }
        }
// **********************************************************************

     // JUAN   collinearSegments = arraysegments.toArray(new LineSegment[arraysegments.size()]);

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
