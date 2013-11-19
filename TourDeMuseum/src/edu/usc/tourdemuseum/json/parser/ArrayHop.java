package edu.usc.tourdemuseum.json.parser;

import java.util.ArrayList;
import java.util.List;



public class ArrayHop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[]= {5, 6, 0, 4, 2, 4, 1, 0, 0, 4};
	    
		int[] minJumps1 = ArrayHop.minJumps1(arr, arr.length);
		for(int index = 0; index < minJumps1.length; index++){
			System.out.print(minJumps1[index] + " ");
		}
			
	}
	//0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3
	//5, 6, 0, 4, 2, 4, 1, 0, 0, 4
	//0, 0, 1, 1, 1, 1, 1, 5, 5, 0]
	//0, 5,9,11,out
		public static List<Integer> minJumps(int arr[], int n)
		{
		    int[] jumps = new int[n];  // jumps[n-1] will hold the result
		    int i, j;
		 
		    if (n == 0 || arr[0] == 0)
		        return null;
		 
		    jumps[0] = 0;
		 
		    
		    for (i = 1; i < n; i++)
		    {
		        jumps[i] = Integer.MAX_VALUE;
		        for (j = 0; j < i; j++)
		        {
		            if (i <= j + arr[j] && jumps[j] != Integer.MAX_VALUE)
		            {
		                 jumps[i] = jumps[j] + 1;
		                 break;
		            }
		        }
		    }
		    ArrayList<Integer> indicesList = new ArrayList<Integer>();

		    for (i =n-1 ; i > 0; i--)
		    {
		    	if (jumps[i-1] != jumps[i])
		    	{
		    		indicesList.add(i-1);
		    	}
		    	
		    }
		    
		    return indicesList;
		}
		
		

/*
		set B[i] to infinity for all i
		B[1] = 0;                    <-- zero steps to reach B[1]
		for i = 1 to n-1             <-- Each step updates possible jumps from A[i]
		    for j = 1 to A[i]        <-- Possible jump sizes are 1, 2, ..., A[i]
		        if i+j > n           <-- Array boundary check
		            break
		        if B[i+j] > B[i]+1   <-- If this path to B[i+j] was shorter than previous
		            B[i+j] = B[i]+1  <-- Keep the shortest path value
		            C[i+j] = i       <-- Keep the path itself
*/
		        	public static int[] minJumps1(int arr[], int n)
		    		{
		    		    int[] jumps = new int[n];  // jumps[n-1] will hold the result
		    		    int[] prev = new int[n];
		    		    if (n == 0 || arr[0] == 0)
		    		        return null;
		    		    
		    		    for (int k =0 ;k < n; k++)
		    		    {
		    		    	jumps[k] = Integer.MAX_VALUE;
		    		    }
		    		    
		    		    
		    		    
		    		    jumps[0] = 0;
		    		    for (int i =0 ; i < n;i++)
		    		    {
		    		    	for (int j = 0; j< arr[i];j++)
		    		    	{
		    		    		if (i+j > n-1)
		    		    		{
		    		    			break;
		    		    		}
		    		    		if (jumps[i+j] > jumps[i]+1)
		    		    		{
		    		    			jumps[i+j] = jumps[i];
		    		    			prev[i+j] = i;
		    		    		}
		    		    	}
		    		    }
		    		    return prev;
		        }
		/*
		 * 
		 * 
		 */
}
