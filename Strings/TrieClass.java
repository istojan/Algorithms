package lab_Stringovi;

import java.util.*;

public class TrieClass {
	
	
	public static void main(String[] args) {
		Trie trie = new Trie();
		String str = "abc";
		trie.insertWord(str);
		str = "bum";
		trie.insertWord(str);
		trie.insertWord("barbi");
		System.out.println(trie.wholeWordSearch("bum"));
		trie.deleteWord(str);
		System.out.println(trie.wholeWordSearch("bum"));
		System.out.println(trie.wholeWordSearch("barbi"));
	}
	
}

class Trie{
	TrieNode root;
	
	class TrieNode{
		Map<Character, TrieNode> children;
		boolean endOfWord;
		TrieNode parent;
		
		public TrieNode(TrieNode par){
			parent = par;
			children = new HashMap<Character, TrieNode>();
		}
	}
	
	public Trie(){
		root = new TrieNode(null);
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
				curr.children.put(ch, new TrieNode(curr));
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
		
	}
}


