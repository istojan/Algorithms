package lab_Geometriski;

import java.awt.Point;
import java.util.*;
import java.awt.geom.Line2D;

//https://www.topcoder.com/community/data-science/data-science-tutorials/geometry-concepts-line-intersection-and-its-applications/

public class Presek_megju_otsecki {
	public static void main(String[] args) {
		//Point point = new Point(3, 9);
		
	}
	
	public static Point[] naiveClosestPair(Point[] points) {
	    double min = Double.MAX_VALUE;

	    Point[] closestPair = new Point[2];

	    for (Point p1 : points) {
	        for (Point p2 : points) {
	            if (p1 != p2) {
	                double dist = p1.distance(p2);

	                if (dist < min) {
	                    min = dist;

	                    closestPair[0] = p1;
	                    closestPair[1] = p2;
	                }
	            }
	        }
	    }

	    return closestPair;
	}
	
	public static boolean PolygonPoint(Polygon p, MyPoint tocka){
	
		Edge pom = new Edge(tocka, new MyPoint(100000, 100000));
		int br = 0;
		
		for(Edge edge: p.edges){
			if(lineIntersect(edge, pom)) br++;
		}
		
		if(br%2==1) return true;
		return false;
	}
	
	public static boolean lineIntersect(Edge prv, Edge vtor){
		return Line2D.linesIntersect(prv.start.x, prv.start.y, prv.end.x, prv.end.y,
				vtor.start.x, vtor.start.y, vtor.end.x, vtor.end.y);
	}
	
	public static MyPoint intersects(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
		if(!Line2D.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4)) return null;
		double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		if (d == 0) return null;
		double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
		double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
		return new MyPoint(xi,yi);
	}
	
	public static MyPoint TopCoderLineIntersect(Edge prv, Edge vtor){
		// Ax+By=C
//		A = y2-y1
//		B = x1-x2
//	    C = A*x1+B*y1
//		A1x + B1y = C1
//		A2x + B2y = C2
		double A1 = prv.end.y - prv.start.y;
		double B1 = prv.start.x - prv.end.x;
		double C1 = A1*prv.start.x + B1*prv.start.y;
		
		double A2 = vtor.end.y - vtor.start.y;
		double B2 = vtor.start.x - vtor.end.x;
		double C2 = A2*vtor.start.x + B2*vtor.start.y;
		
		double x, y;
		double det = A1*B2 - A2*B1;
	    if(det == 0){
			return null;
	    }else{
			x = (B2*C1 - B1*C2)/det;
		    y = (A1*C2 - A2*C1)/det;
	    }
		if(Math.min(prv.start.x, prv.end.x)<=x && Math.max(prv.start.x, prv.end.x)>=x
				&& Math.min(prv.start.y, prv.end.y)<=y && Math.max(prv.start.y, prv.end.y)>=x)
		return new MyPoint(x, y);
		return null;
	}
	
	public static MyPoint slopesIntersect(double A1, double B1, double C1, double A2, double B2, double C2){
		double x, y;
		double det = A1*B2 - A2*B1;
	    if(det == 0){
			return null;
	    }else{
			x = (B2*C1 - B1*C2)/det;
		    y = (A1*C2 - A2*C1)/det;
	    }
//		if(Math.min(prv.start.x, prv.end.x)<=x && Math.max(prv.start.x, prv.end.x)>=x
//				&& Math.min(prv.start.y, prv.end.y)<=y && Math.max(prv.start.y, prv.end.y)>=x)
		return new MyPoint(x, y);
	}
	
	public static MyPoint reflection(Edge edge, MyPoint tocka){
		double A1 = edge.end.y - edge.start.y;
		double B1 = edge.start.x - edge.end.x;
		double C1 = A1*edge.start.x + B1*edge.start.y;
		
		//double xIntersect = (edge.start.x + edge.end.x)/2;
		//double yIntersect = (edge.start.y + edge.end.y)/2;
		
		//MyPoint tocka2 = new MyPoint(xIntersect, yIntersect);
		double A2 = -B1;
		double B2 = A1;
		double C2 = A2*tocka.x + B2*tocka.y;
		//double A2 = tocka2.y - tocka.y;
		//double B2 = tocka.x - tocka2.x;
		//double C2 = A2*tocka.x + B2*tocka.y;
		
		MyPoint intersection = slopesIntersect(A1, B1, C1, A2, B2, C2);
		double x = intersection.x - (tocka.x - intersection.x);
		double y = intersection.y - (tocka.y - intersection.y);
		return new MyPoint(x, y);
	}
}

class Polygon{
	ArrayList<Edge> edges;
	
	public Polygon(){
		edges = new ArrayList<>();
	}
}

class Edge{
	MyPoint start;
	MyPoint end;
	
	public Edge(MyPoint st, MyPoint en){
		start = st;
		end = en;
	}
}

class MyPoint{
	public MyPoint(double xi, double yi) {
		x = xi;
		y = yi;
	}
	double x;
	double y;
}
