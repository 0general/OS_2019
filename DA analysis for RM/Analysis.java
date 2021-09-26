import java.util.Arrays;


public class Analysis {

	public PlatformInfo pInfo;

	public static void main(String[] args) {
		
	}
	public String run(PlatformInfo p) {
		
		pInfo = p;
		int schedulable=0;
		
		switch(p.scheduler) {
		case 1000:	//utilization bound for RM (only for Uniprocessor)	
			schedulable=UBTestforRM_Uni();  
			break;
		
		case 1001: 	//utilization bound for EDF  (only for Uniprocessor) 
			schedulable=UBTestforEDF_Uni();
			break;
			
		case 1002:	//RTA for RM (only for Uniprocessor)
			schedulable=RTATestforRM_Uni();
			break;
		
			
		case 1003:	//DA test for any scheduling (applicable for both Uni- and Multiprocessor)
			schedulable=DATest_Multi_V1();
			break;
			
		case 1004:	//DA test for any scheduling (applicable for both Uni- and Multiprocessor)
			schedulable=DATestforRM_Multi_V2();
			break;	
			
		}

		String result=schedulable+" "+pInfo.numTask;

		return result;
	}
	
	

	public int UBTestforRM_Uni() {
		double sysUtilization = 0;
		
		for(int i=0;i<pInfo.numTask;i++)		
			sysUtilization += pInfo.tasks.get(i).execTime/pInfo.tasks.get(i).Period;
		
		if(sysUtilization<=pInfo.numTask*(Math.pow(2, 1.0/pInfo.numTask)-1)) 
			return 1;
		else		
			return 0;
		
	}

	public int UBTestforEDF_Uni() {
		 
		double sysUtilization = 0; 
		
		for(int i=0;i<pInfo.numTask;i++)		
			sysUtilization += pInfo.tasks.get(i).execTime/pInfo.tasks.get(i).Period;		
		
		if(sysUtilization<=1)
			return 1;
		else		
			return 0;
	}
	
	public int RTATestforRM_Uni()
	{
		for (int k=0; k<pInfo.numTask; k++) 
		{
			Task task_k = pInfo.tasks.get(k);
			int interval= (int)task_k.execTime;
			boolean pass=false;

			while (interval<= (int)task_k.Deadline && pass==false) 
			{
				int sum=0;
				for (int i=0; i<pInfo.numTask; i++) 
				{
					Task task_i = pInfo.tasks.get(i);
					if (i<k) 
						sum+= Math.ceil(interval/task_i.Period)*task_i.execTime;													
				}
				int new_interval = sum+(int)task_k.execTime;
				if (interval==new_interval) 
					pass=true;
				else 
					interval = new_interval;				
			}			
			if (pass==false) 
				return 0;	
		}									
		return 1;
	}
		
	
	public int DATest_Multi_V1()
	{
		for (int k=0; k<pInfo.numTask; k++)
		{			
			double sum = 0;
			Task task_k = pInfo.tasks.get(k);
			for (int i=0; i<pInfo.numTask; i++) 	
			{	
				if (i!= k) 
				{
				   Task task_i = pInfo.tasks.get(i);
				
				   int NiDk = (int)(task_k.Deadline+task_i.Deadline-task_i.execTime)/(int)task_i.Period;
				   sum = sum + (NiDk*task_i.execTime + Math.min(task_i.execTime,task_k.Deadline+task_i.Deadline-task_i.execTime-NiDk*task_i.Period));
				}
			}
			if (task_k.execTime + (int)(sum/pInfo.numProcessor)>task_k.Deadline) 
			{
				  return 0;
			}
		}
		  return 1;
	}
	
	public int DATestforRM_Multi_V2()
	{
		for (int k=0; k<pInfo.numTask; k++)
		{			
			double sum = 0;
			Task task_k = pInfo.tasks.get(k);
			for (int i=0; i<pInfo.numTask; i++) 	
			{	
				if (k > i) 
				{
				   Task task_i = pInfo.tasks.get(i);
				
				   int NiDk = (int)(task_k.Deadline+task_i.Deadline-task_i.execTime)/(int)task_i.Period;
				   double IikDk = NiDk*task_i.execTime + Math.min(task_i.execTime,task_k.Deadline+task_i.Deadline-task_i.execTime-NiDk*task_i.Period);
				   sum = sum + Math.min(IikDk, task_k.Deadline-task_k.execTime+1);
				}
			}
			if (task_k.execTime + (int)(sum/pInfo.numProcessor)>task_k.Deadline) 
			{
				  return 0;
			}
		}
		  return 1;
	}
	
	
	public int workloadWC(double T, double C, double D, double L) {
		return 0;
	}	
	
	
}