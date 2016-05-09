package domashna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArticulationPointsInGraph {
	private static int time;
	
	public static void main(String[] args) {
		Set<Integer> set = ArticulationPoints(null);
		System.out.println(set.size());
	}
	
	public static Set<Integer> ArticulationPoints(MyPoint[] points){
		if(points == null) return new HashSet<>();
		time = 0;
		
		Set<Integer> articulationPoints = new HashSet<Integer>();
		Set<Integer> visited = new HashSet<>();
		int startVertex = 0;
		
		Map<Integer, Integer> visitedTime = new HashMap<>();	// vreme koga bilo izminato
		Map<Integer, Integer> lowTime = new HashMap<>();		// najmalo vreme
		Map<Integer, Integer> parent = new HashMap<>();			// roditelite na sekoe teme
		
		DFS(points, visited, articulationPoints, startVertex, visitedTime, lowTime, parent);
		return articulationPoints;
	}

	private static void DFS(MyPoint[] points, Set<Integer> visited, Set<Integer> articulationPoints, int startVertex,
			Map<Integer, Integer> visitedTime, Map<Integer, Integer> lowTime, Map<Integer, Integer> parent) {
		visited.add(startVertex);
		visitedTime.put(startVertex, time);
		lowTime.put(startVertex, time);
		time++;
		int childCount = 0;
		boolean isArticulationPoint = false;
		
		for(MyPoint adj : points[startVertex].getAdjacent()){
			if(parent.containsKey(startVertex)){
				if(adj.getId() == parent.get(startVertex)) continue;
			}
			
			if(!visited.contains(adj.getId())){
				parent.put(adj.getId(), startVertex);
				childCount++;	// zgolemuvame broj na deca( ova ni e vazno samo za root-ot )
				
				DFS(points, visited, articulationPoints, adj.getId(), visitedTime, lowTime, parent);
			
				if(visitedTime.get(startVertex) <= lowTime.get(adj.getId())){	// ako ima sosed(osven roditelot) so pogolemo najmalo vreme,
					isArticulationPoint = true;									// togash nema drug pat do toa teme i ova teme e kriticno
				}
				else{
					int low = Math.min(lowTime.get(startVertex), lowTime.get(adj.getId()));
					lowTime.put(startVertex, low);		// ja updejtirame vrednosta na najkratko vreme
				}
			}
			else{
				int low = Math.min(lowTime.get(startVertex), visitedTime.get(adj.getId()));
				lowTime.put(startVertex, low);			// ja updejtirame vrednosta na najkratko vreme
			}
		
		}
		
		if((parent.get(startVertex)==null && childCount>=2)||(parent.get(startVertex)!=null && isArticulationPoint)){
			articulationPoints.add(startVertex);   // ako e ispolnet nekoj od 2ta uslovi, temeto e kriticno
		}
		
	}
}

class MyPoint{
	private int id;
	private ArrayList<MyPoint> adjacent;
	
	public MyPoint(int id){
		this.id = id;
		adjacent = new ArrayList<>();
	}

	public void addAdjacent(MyPoint MyPoint) {
		adjacent.add(MyPoint);
	}

	public Integer getId() {
		return id;
	}

	public ArrayList<MyPoint> getAdjacent() {
		return adjacent;
	}

}