package main.java.neuronBuffer;

import java.util.LinkedList;
import java.util.List;

public class Transmitter {

	protected int buffer = 0;
	protected int sizeBuffer;
	protected int[][] input;
	protected List<Integer> output;

	public Transmitter(int sizebuffer,int[][] input)
	{
		this.sizeBuffer = sizebuffer;
		this.input = input;
		output = new LinkedList<Integer>();
	}

	public void compute()
	{
		int i = 0;
		while(i < input[0].length)
		{
			int sum = sum(i);
			if(buffer == 0 && sum == 0 )
			{
				output.add(0);
			}
			else
			{
				output.add(1);

				if(sum > 1)
				{
					buffer += (sum -1);
				}
				else
				{
					if(sum == 0)
					{
						buffer --;
					}

				}
			}
			System.out.println("i:"+i + " charge:"+buffer);
			System.out.println("out = " + output.get(output.size()-1));
			i++;
		}
	}

	private int sum(int i) {
		int ret = 0;
		for(int[] l : input )
		{
			System.out.print(l[i] );
			ret += l[i];
		}
		System.out.println();
		return ret;
	}

	public static int[][] createInput(int[] freq, int[] n)
	{
		int max = 0;
		for(int i = 0 ; i < freq.length ; i++)
		{
			int time = (int) (n[i]*freq[i]);
			if(time > max)
				max = time;
		}

		int[][] ret = new int[freq.length][max];

		for(int i = 0 ; i < freq.length ; i++)
		{
			for(int j = 0 ; j< max ; j++)
			{
				//System.out.println(1/freq[i] + " j=" + j+1 + " mod " +  (j+1) / (double)freq[i]);
				if((j+1) % (freq[i]) == 0 && (j+1) / (double)freq[i] <= n[i])
					ret[i][j] = 1;
				else
					ret[i][j] = 0;
			}
		}
		return ret;
	}
	
	public static void tabToString(int[][] tab)
	{
		for(int i = 0 ; i < tab.length ; i++)
		{
			for(int j = 0 ; j< tab[i].length ; j++)
			{
			
				System.out.print(tab[i][j]);
			}
			System.out.println();
		}
	}
	public static void main(String[] args)
	{
		int[] freq = {2,2,2};
		int[] n = {100,100,100 };
		int[][] input = createInput(freq,n);
		tabToString(input);
		
		Transmitter trans = new Transmitter(10, input);
		trans.compute();
	}

}
