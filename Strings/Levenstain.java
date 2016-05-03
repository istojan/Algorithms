package lab_Stringovi;

public class Levenstain {
	
	// https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance
	// od tuka e kodov
	
	public static int levenshteinDistance (CharSequence lhs, CharSequence rhs) {                          
	    int len0 = lhs.length() + 1;                                                     
	    int len1 = rhs.length() + 1;                                                     
	                                                                                    
	    // the array of distances                                                       
	    int[] cost = new int[len0];                                                     
	    int[] newcost = new int[len0];                                                  
	                                                                                    
	    // initial cost of skipping prefix in String s0                                 
	    for (int i = 0; i < len0; i++) cost[i] = i;                                     
	                                                                                    
	    // dynamically computing the array of distances                                  
	                                                                                    
	    // transformation cost for each letter in s1                                    
	    for (int j = 1; j < len1; j++) {                                                
	        // initial cost of skipping prefix in String s1                             
	        newcost[0] = j;                                                             
	                                                                                    
	        // transformation cost for each letter in s0                                
	        for(int i = 1; i < len0; i++) {                                             
	            // matching current letters in both strings                             
	            int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             
	                                                                                    
	            // computing cost for each transformation                               
	            int cost_replace = cost[i - 1] + match;                                 
	            int cost_insert  = cost[i] + 1;                                         
	            int cost_delete  = newcost[i - 1] + 1;                                  
	                                                                                    
	            // keep minimum cost                                                    
	            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
	        }                                                                           
	                                                                                    
	        // swap cost/newcost arrays                                                 
	        int[] swap = cost; cost = newcost; newcost = swap;                          
	    }                                                                               
	                                                                                    
	    // the distance is the cost for transforming all letters in both strings        
	    return cost[len0 - 1];                                                          
	}
	
	 public static String[] alignment(String a, String b) {
	        a = a.toLowerCase();
	        b = b.toLowerCase();
	        // i == 0
	        int[][] costs = new int[a.length()+1][b.length()+1];
	        for (int j = 0; j <= b.length(); j++)
	            costs[0][j] = j;
	        for (int i = 1; i <= a.length(); i++) {
	            costs[i][0] = i;
	            for (int j = 1; j <= b.length(); j++) {
	                costs[i][j] = Math.min(1 + Math.min(costs[i-1][j], costs[i][j-1]), a.charAt(i - 1) == b.charAt(j - 1) ? costs[i-1][j-1] : costs[i-1][j-1] + 1);
	            }
	        }
	 
		// walk back through matrix to figure out path
		StringBuilder aPathRev = new StringBuilder();
		StringBuilder bPathRev = new StringBuilder();
		for (int i = a.length(), j = b.length(); i != 0 && j != 0; ) {
		    if (costs[i][j] == (a.charAt(i - 1) == b.charAt(j - 1) ? costs[i-1][j-1] : costs[i-1][j-1] + 1)) {
			aPathRev.append(a.charAt(--i));
			bPathRev.append(b.charAt(--j));
		    } else if (costs[i][j] == 1 + costs[i-1][j]) {
			aPathRev.append(a.charAt(--i));
			bPathRev.append('-');
		    } else if (costs[i][j] == 1 + costs[i][j-1]) {
			aPathRev.append('-');
			bPathRev.append(b.charAt(--j));
		    }
		}
	        return new String[]{aPathRev.reverse().toString(), bPathRev.reverse().toString()};
	    }

	public static void main(String[] args) {
		System.out.println(levenshteinDistance("baacaabc", "abacbcac"));
		String[] tmp = alignment("baacaabc", "abacbcac");
		System.out.println(tmp[0]);
		System.out.println(tmp[1]);
	}
}

