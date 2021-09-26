import java.util.Arrays;


public class Analysis {

	public PlatformInfo pInfo;

	public static void main(String[] args) {
		
	}
	public String run(PlatformInfo p) {
		
		pInfo = p;
		int schedulable=0;
		
		switch(p.scheduler) {
		case 1000:	//utilization bound for RM 	
			schedulable=UBTestforRM();  
			break;
		
		case 1001: 	//utilization bound for EDF 
			schedulable=UBTestforEDF();
			break;
		}

		String result=schedulable+" "+pInfo.numTask;

		return result;
	}
	
	

	public int UBTestforRM () {
		
		double util=0;
		
		
		for (int i=0; i<pInfo.numTask ; i++) {
			Task task = pInfo.tasks.get(i);
			util = util + (task.execTime/task.Period);
			
		}
		if (util<=(pInfo.numTask*(Math.pow(2, (1.0/ pInfo.numTask))-1))) {
			return 1;
		}
		else
		{
			return 0;
		}
			
	}

	public int UBTestforEDF () {
		 
		//위에 double 변수 설정부터 for문까지 그 긴 게 PInfo.util과 똑같다....ㅍㅅㅍ....
		
		if (pInfo.util<=1) {
			return 1;
		}
		else
		{
			return 0;
		}
	}	
}
