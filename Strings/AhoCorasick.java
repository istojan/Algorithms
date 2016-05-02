package lab_Stringovi;

import java.util.*;

public class AhoCorasick {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		TrieAC automat = new TrieAC();
		
		automat.insertWord("barbi");
		automat.insertWord("man");
		automat.insertWord("bum");
		automat.insertWord("bibu");
		
		automat.build();
		
		String str = "bumanmanbarbiadasdbarbibum";
		System.out.println(str);
		automat.transition(str);
		automat.deleteWord("bibu");
		automat.transition(str);
		

		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}

class TrieAC{
	TrieNode root;
	
	class TrieNode{
		Map<Character, TrieNode> children;
		boolean endOfWord;
		TrieNode parent;
		TrieNode fall;
		String str;
		
		public TrieNode(TrieNode par, char ch){
			parent = par;
			children = new HashMap<Character, TrieNode>();
			if(par!=null) str = parent.str + ch;
			else str = "";
		}
	}
	
	class Pair{
		String str;
		TrieNode node;
		
		public Pair(TrieNode curr, String st){
			node = curr;
			str = st;
		}
		
	}
	
	public TrieAC(){
		root = new TrieNode(null, 'a');
		root.fall = null;
	}
	
	public void transition(String str) {
		
		TrieNode curr = root;
		TrieNode tmp = null;
		ArrayList<TrieNode> results = new ArrayList<>();
		int i = 0;
		int size = str.length();
		
		while(i < size){
			char ch = str.charAt(i);
			tmp = curr.children.get(ch);
			if(tmp!=null){
				if(tmp.endOfWord){
					results.add(tmp);
				}
				i++;
				curr = tmp;
			}
			else if(tmp==null){
				if(curr==root){
					i++;
				}
				else{
					curr = curr.fall;
				}
			}
		}
		System.out.println(results.size());
		printWordsFound(results);
	}

	private void printWordsFound(ArrayList<TrieNode> results) {
		while(!results.isEmpty()){
			TrieNode curr = results.remove(0);
			System.out.println(curr.str);
		}
	}

	public void build() {
		ArrayList<Pair> queue = new ArrayList<>();
		queue.add(new Pair(root, ""));
		
		while(!queue.isEmpty()){
			Pair curr = queue.remove(0);
			String str = curr.str;
			
			for(char ch :curr.node.children.keySet()){
				String pom = str + ch;
				queue.add(new Pair(curr.node.children.get(ch), pom));
			}
			
			if(curr.node == root) continue;
			curr.node.fall = null;
			
			TrieNode fall = null;
			for(int i=1;i<str.length();i++){
				fall = prefixSearchForBuild(str.substring(i));
				if(fall!=null){
					curr.node.fall = fall;
					break;
				}
			}
			if(fall == null) curr.node.fall = root;	
		}
	}
	
	public TrieNode prefixSearchForBuild(String str){
		
		TrieNode curr = root;
		for(char ch : str.toCharArray()){
			curr = curr.children.get(ch);
			if(curr == null) return null;		
		}
		
		return curr;
	}

	public boolean prefixSearch(String str){
		
		TrieNode curr = root;
		for(char ch : str.toCharArray()){
			curr = curr.children.get(ch);
			if(curr == null) return false;		
		}
		return true;
	}
	
	public boolean wholeWordSearch(String str){
		
		TrieNode curr = root;
		for(char ch : str.toCharArray()){
			curr = curr.children.get(ch);
			if(curr == null) return false;		
		}
		return curr.endOfWord;
	}
	
	public void insertWord(String str){
		TrieNode curr = root;
		for(char ch : str.toCharArray()){
			if(!curr.children.containsKey(ch)){
				curr.children.put(ch, new TrieNode(curr, ch));
			}
			curr = curr.children.get(ch);
		}
		curr.endOfWord = true;
	}
	
	public void deleteWord(String str){
		TrieNode curr = root;
		for(char ch : str.toCharArray()){
			curr = curr.children.get(ch);
			if(curr==null) return;
		}
		
		if(!curr.children.isEmpty()) curr.endOfWord = false;
		else{
			
			int index = str.length();
			//System.out.println(index);
			while(curr.children.size()<2){
				curr = curr.parent;
				index--;
			}
			//System.out.println(index);
			curr.children.remove(str.charAt(index));
		}
		
		this.build(); // da se obnovat site povratni vrski da ne gi vklucuvaat tie kon zborog koj go izbrishavme
		
	}
}




