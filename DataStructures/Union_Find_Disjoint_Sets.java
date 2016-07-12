package DataStructures;

import java.util.*;

class Union_Find_Disjoint_Sets {
	int numSets;
	ArrayList<Integer> sets;	// shows in which set does a vertex belong according to its index 
	ArrayList<Integer> rank;	// shows what's the rank of the vertex ( the height of the tree with root in the vertex )
	ArrayList<Integer> size;	// size of set
	
	public Union_Find_Disjoint_Sets(int N) {
		numSets = N;
		sets = new ArrayList<>();
		rank = new ArrayList<>();
		size = new ArrayList<>();
		for(int i = 0 ; i < N ; i++){
			sets.add(i);
			rank.add(0);
			size.add(1);
		}
	}
	
	int findSet(int i){
		if(sets.get(i) == i) return i;
		else{
			int curr = findSet(sets.get(i));  
			sets.set(i, curr);  // path compression ( although rank stays the same )
			return curr;
		}
	}
	
	boolean isSameSet(int i, int j){
		return findSet(i) == findSet(j);
	}
	
	void unionSet(int i, int j){
		if(!isSameSet(i, j)){
			int setOfFirst = findSet(i);
			int setOfSecond = findSet(j);
			
			if(rank.get(setOfFirst) < rank.get(setOfSecond) ){
				sets.set(setOfFirst, setOfSecond);
				size.set(setOfSecond, size.get(setOfSecond) + size.get(setOfFirst));
			}
			else{
				sets.set(setOfSecond, setOfFirst);
				size.set(setOfFirst, size.get(setOfFirst) + size.get(setOfSecond));
			}
		}
	}
	
}
