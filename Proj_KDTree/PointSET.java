public class PointSET {
	
	
//	function distance(rect, p) {
// 		  var dx = Math.max(rect.min.x - p.x, 0, p.x - rect.max.x);
//		  var dy = Math.max(rect.min.y - p.y, 0, p.y - rect.max.y);
//		  return Math.sqrt(dx*dx + dy*dy);
//		}
// cpde emd
	
	
   public         PointSET()                               // construct an empty set of points 
   public           boolean isEmpty()                      // is the set empty? 
   public               int size()                         // number of points in the set 
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   public           boolean contains(Point2D p)            // does the set contain point p? 
   public              void draw()                         // draw all points to standard draw 
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 

   public static void main(String[] args)                  // unit testing of the methods (optional) 
}
