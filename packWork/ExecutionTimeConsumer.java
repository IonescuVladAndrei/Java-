package packWork;
public class ExecutionTimeConsumer extends ExecutionTimeProducer {
	protected int threadOneC;
	protected int threadTwoC;
	protected int threadThreeC;
	protected int threadFourC;
	
	public ExecutionTimeConsumer(int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8){
		super(t1,t2,t3,t4);
		this.threadOneC = t5;
		this.threadTwoC = t6;
		this.threadThreeC = t7;
		this.threadFourC = t8;
	}
	
	
	public void longestConsumerThread(int ... a){
		int i,max = 0;
		for (i=0;i<a.length;i++){
			if(max < a[i]){
				max = a[i];
			}
		}
		for (i=0;i<a.length;i++){
			if(max == a[i])
			{
				System.out.println("ExecutionTimeConsumer: thread-ul cu numarul " + (i+1) + " a durat cel mai mult: " + a[i] + " ms");
			}
		}
            
	}
	
	public void setExecutionTimeConsumer(int threadNr, int time1, int time2, int time3, int time4){
		if(threadNr == 1){
			this.threadOneC = time1;
		}
		else if(threadNr == 2){
			this.threadTwoC = time2;
		}
		else if(threadNr == 3){
			this.threadThreeC = time3;
		}
		else if(threadNr == 4){
			this.threadFourC = time4;
		}
		else{
			System.out.println("setExecutionTimeConsumer: S-a introdus nr thread-ului incorect");
		}
	}	
	
	public int getExecutionTimeConsumer(int threadNr){
		if(threadNr == 1){
			return this.threadOneC;
		}
		else if(threadNr == 2){
			return this.threadTwoC;
		}
		else if(threadNr == 3){
			return this.threadThreeC;
		}
		else if(threadNr == 4){
			return this.threadFourC;
		}
		else{
			System.out.println("getExecutionTimeConsumer: S-a introdus nr thread-ului incorect");
			return -1;
		}
	}
}