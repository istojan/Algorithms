package lab_ProtokNizMreza;

import java.util.*;

public class FordFulkerson {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		
		Point[] points = new Point[n];
		int[][] capacity = new int[n][n];
		
		for(int i=0;i<n;i++){
			points[i] = new Point(i);
		}
		
		
		for(int i=0;i<m;i++){
			int a = sc.nextInt();
			int b = sc.nextInt();
			int price = sc.nextInt();
			
			capacity[a][b] = price;
			
			points[a].addConnection(points[b], price);
			points[b].addConnection(points[a], 0);
		}
		int startIndex = sc.nextInt();
		int endIndex = sc.nextInt();
		sc.close();
		
		int rez = maxFlow(points, startIndex, endIndex);
		System.out.println(rez);
		
		rez = maxFlow(capacity, startIndex, endIndex);
		System.out.println(rez);
	
	}

	private static int maxFlow(Point[] points, int startIndex, int endIndex) {
		
		int maxFlow = 0;
		
		while(true){
			Set<Integer> visited = new HashSet<>();
			ArrayList<Integer> queue = new ArrayList<>();
			queue.add(startIndex);
			Map<Integer, Integer> parent = new HashMap<>();
			
			visited.add(startIndex);
			boolean existsPath = false;
			while(!queue.isEmpty()){
				Point curr = points[queue.get(0)];
				
				for(Connection conn : curr.connections){
					if(conn.flow>0 && !visited.contains(conn.dest.id)){
						queue.add(conn.dest.id);
						visited.add(conn.dest.id);
						parent.put(conn.dest.id, curr.id);
					}
					
					if(conn.dest.id == endIndex){
						existsPath = true;
						break;
					}
				}
				if(existsPath) break;
			}
			
			if(!existsPath) break;
			
			int flow = Integer.MAX_VALUE;
			
			int curr = endIndex;
			while(curr!=startIndex){
				int par = parent.get(curr);
				flow = Math.min(flow, points[par].getConnectionCap(points[curr]));
				curr = par;
			}
			
			maxFlow+=flow;
			
			curr = endIndex;
			while(curr!=startIndex){
				int par = parent.get(curr);
				points[par].updateConnection(curr,-flow );
				points[curr].updateConnection(par,flow );
				curr = par;
			}
		}
		
		return maxFlow;
	}
	
	private static int maxFlow(int[][] capacity, int source, int dest) {
		
		int resCapacity[][] = new int[capacity.length][capacity[0].length];
		for(int i=0;i<capacity.length; i++){
			for(int j=0;j<capacity[0].length;j++){
				resCapacity[i][j] = capacity[i][j];
			}
		}
		
		Map<Integer, Integer> parent = new HashMap<>();
		
		List<List<Integer>> augmentedPaths = new ArrayList<>();
				
		int maxFlow = 0;
		
		while(bfs(resCapacity, parent, source, dest)){
			List<Integer> augmentedPath = new ArrayList<>();
			int flow = Integer.MAX_VALUE;
			int tmp = dest;
			
			while(tmp!=source){
				augmentedPath.add(0, tmp);
				int par = parent.get(tmp);
				flow = Math.min(flow, resCapacity[par][tmp]);
				tmp = par;
			}
			maxFlow+=flow;
			augmentedPath.add(0, source);
			augmentedPaths.add(augmentedPath);
			
			tmp = dest;
			while(tmp!=source){
				int par = parent.get(tmp);
				resCapacity[par][tmp]-=flow;
				resCapacity[tmp][par]+=flow;
				tmp = par;
			}
			
		}
		printAugmentedPaths(augmentedPaths);
		return maxFlow;
	}

	private static void printAugmentedPaths(List<List<Integer>> augmentedPaths) {
		
		for(List<Integer> curr : augmentedPaths){
			for(int tmp : curr){
				System.out.print(tmp + " ");
			}
			System.out.println();
		}
		
	}

	private static boolean bfs(int[][] resCapacity, Map<Integer, Integer> parent, int source, int dest) {
		Set<Integer> visited = new HashSet<>();

		Queue<Integer> queue = new LinkedList<>();
		
		queue.add(source);
		
		while(!queue.isEmpty()){
			int curr = queue.poll();
			
			for(int i=0;i<resCapacity.length; i++){
				
				if(!visited.contains(i) && resCapacity[curr][i]>0){
					parent.put(i, curr);
					
					visited.add(i);
					
					if(i==dest){
						return true;
					}
					
				}
				
			}
		}
		
		return false;
	}
	
}

class Point{
	int id;
	ArrayList<Connection> connections;

	public Point(int i) {
		id = i;
		connections = new ArrayList<>();
	}

	public void updateConnection(int curr, int i) {
		for(Connection conn : connections){
			if(conn.dest.id == curr){
				conn.flow += i;
				break;
			}
		}
	}

	public int getConnectionCap(Point curr) {
		for(Connection conn : connections){
			if(conn.dest.id == curr.id) return conn.flow;
		}
		return Integer.MAX_VALUE;
	}

	public void addConnection(Point point, int pr) {
		connections.add(new Connection(this, point, pr));
	}
	
}

class Connection{
	Point origin;
	Point dest;
	int flow;
	
	public Connection(Point org, Point dest, int fl){
		origin = org;
		this.dest = dest;
		flow = fl;
	}
}

