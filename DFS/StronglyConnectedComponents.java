package domashna;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class StronglyConnectedComponents {
	
	public static void main(String[] args) {
		
		List<Integer> komponenti = KosarajuAlgoritham(null);
		System.out.println(komponenti.size());
		
	}
	private static List<Integer> KosarajuAlgoritham(Node[] node) {
	
	
		if(node == null) return new ArrayList<>();
		Stack<Integer> stack = new Stack<>();   // vo stakot kje gi cuvam teminjata spored nivnoto vreme na zavrsuvanje vo dfs-to
		Set<Integer> visited = new HashSet<Integer>();
		
		
		for(Node mob : node){   // gi pominuvam site teminja so DFS 
			if(visited.contains(mob.id)) continue;

			DFSUtil(mob, visited, stack);
		}
		
		reverseGraph(node); // gi menuvam pravcite na site relacii
		
		visited.clear();
		
		List<Integer> result = new ArrayList<>();  // kje gi cuva goleminite na site komponenti
		
		while(!stack.isEmpty()){
			Node mob = node[stack.pop()];
			if(visited.contains(mob.id)) continue;
			
			Set<Integer> komponenta = new HashSet<>();
			DFSForReversedGraph(mob, visited, komponenta);  // gi naogjam cvrsto svrzanite komponenti
			result.add(komponenta.size());
		}
		
		return result;
	}

	private static void reverseGraph(Node[] node) {
		ArrayList<ArrayList<Node>> reversedGraph = new ArrayList<ArrayList<Node>>();
		
		for(int i=0;i<node.length;i++){
			reversedGraph.add(new ArrayList<Node>());
		}
		
		for(Node mob : node){
			ArrayList<Node> adjacent = mob.getAdjacent();
			for(Node Node : adjacent){
				reversedGraph.get(Node.id).add(mob);
			}
		}
		
		for(int i=0;i<node.length;i++){
			node[i].setAdjacent(reversedGraph.get(i));
		}
	}

	private static void DFSUtil(Node mob, Set<Integer> visited, Stack<Integer> stack) {
		
		visited.add(mob.id);
		ArrayList<Node> adjacent = mob.getAdjacent();
		for(Node Node : adjacent){
			if(visited.contains(Node.id)) continue;
			
			DFSUtil(Node, visited, stack);
		}
		stack.push(mob.id);
	}

	private static void DFSForReversedGraph(Node mob, Set<Integer> visited, Set<Integer> komponenta) {
		visited.add(mob.id);
		komponenta.add(mob.id);
		ArrayList<Node> adjacent = mob.getAdjacent();
		
		for(Node Node : adjacent){
			if(visited.contains(Node.id)) continue;
			DFSForReversedGraph(Node, visited, komponenta);
		}
		
	}
}

class Node{
	int id;
	ArrayList<Node> adjacent;
	
	public Node(int id){
		this.id = id;
		adjacent = new ArrayList<>();
	}

	public void setAdjacent(ArrayList<Node> arrayList) {
		adjacent = arrayList;      // go koristam pri menuvanjeto na pravcite na relaciite    
	}

	public ArrayList<Node> getAdjacent() {
		return adjacent;
	}

	public void addAdjacent(Node Node) {
		adjacent.add(Node); // mu dodavam relacija na Nodeot
	}
}