import java.util.Scanner;
public class Bankers {
	private int need[][],allocate[][],max[][],avail[],numP,numR;

	private void input()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("201501489 Youngjin Choi");
		System.out.print("Enter no. of processes and resources : ");
		numP=sc.nextInt();  //# of process
		numR=sc.nextInt();  //# of resources
		need=new int[numP][numR];  //initializing arrays
		max=new int[numP][numR];
		allocate=new int[numP][numR];
		avail=new int[numR];

		System.out.println("Enter allocation matrix -->");
		for(int i=0;i<numP;i++)
			for(int j=0;j<numR;j++)
				allocate[i][j]=sc.nextInt();  //allocation matrix

		System.out.println("Enter max matrix -->");
		for(int i=0;i<numP;i++)
			for(int j=0;j<numR;j++)
				max[i][j]=sc.nextInt();  //max matrix

		System.out.println("Enter available matrix -->");
		for(int j=0;j<numR;j++)
			avail[j]=sc.nextInt();  //available matrix

		sc.close();
	}

	private int[][] calc_need(){
		for(int i=0;i<numP;i++)
			for(int j=0;j<numR;j++)  //calculating need matrix
				need[i][j]=max[i][j]-allocate[i][j];

		return need;
	}

	private boolean check(int i){
		//checking if all resources for ith process can be allocated
		for(int j=0;j<numR;j++) 
			if(avail[j]<need[i][j])
				return false;

		return true;
	}

	public void isSafe(){
		input();
		calc_need();
		 
		boolean Finish[] = new boolean[numP];
		
		for (int k = 0;k<numP;k++)
			Finish[k] = false;

		int t = 0;
		boolean safe = true;
		
		while (t < numP)
		{
			for (int i=0;i<numP;i++) 
			{
				if (check(i)==true && Finish[i]==false) 
				{
					Finish[i] = true;
					System.out.println("Allocated process : " + i);
					for (int j =0; j<numR ;j++)
					{
						avail[j] = avail[j] + allocate[i][j];
					}
					
					break;					
				}
			}
			t++;			
			
		}
				
		for (int k = 0;k<numP;k++) 
		{
			if (Finish[k]==false)
			{
				safe = false;
				break;
			}
				
		}
			
		
		
		if (safe == true) 
		{
			System.out.println("Safely allocated");
		}
		else
		{
			System.out.println("All process cant be alocated safely");
			
		}
		
		
		
	}

	public static void main(String[] args) {
		new Bankers().isSafe();

	}

}
