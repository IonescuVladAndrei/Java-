package packWork;

interface ExecutionTimeInterface {
	  public void longestProducerThread(int ... a); // interface method
	  public void longestConsumerThread(int ... a);
	  public void longestWriterThread(int ... a);
}

public class ExecutionTimeProducer implements ExecutionTimeInterface{
	protected int threadOne;
	protected int threadTwo;
	protected int threadThree;
	protected int threadFour;
	
	public void longestProducerThread(int ... a){
		int i, max = 0;
		for (i=0;i<a.length;i++){
			if(max < a[i]){
				max = a[i];
			}
		}
		for (i=0;i<a.length;i++){
			if(max == a[i])
			{
				System.out.println("ExecutionTimeProducer: thread-ul cu numarul " + (i+1) + " a durat cel mai mult: " + a[i] + " ms");
			}
		}
            
	}
	public void longestConsumerThread(int ... a){}
	
	public void longestWriterThread(int ... a){}
	
	public ExecutionTimeProducer(int t1, int t2, int t3, int t4){
		this.threadOne = t1;
		this.threadTwo = t2;
		this.threadThree = t3;
		this.threadFour = t4;
	}
	
	public void setExecutionTimeProducer(int threadNr, int time1, int time2, int time3, int time4){
		if(threadNr == 1){
			this.threadOne = time1;
		}
		else if(threadNr == 2){
			this.threadTwo = time2;
		}
		else if(threadNr == 3){
			this.threadThree = time3;
		}
		else if(threadNr == 4){
			this.threadFour = time4;
		}
		else{
			System.out.println("setExecutionTimeProducer: S-a introdus nr thread-ului incorect");
		}
	}	
	
	public int getExecutionTimeProducer(int threadNr){
		if(threadNr == 1){
			return this.threadOne;
		}
		else if(threadNr == 2){
			return this.threadTwo;
		}
		else if(threadNr == 3){
			return this.threadThree;
		}
		else if(threadNr == 4){
			return this.threadFour;
		}
		else{
			System.out.println("setExecutionTimeProducer: S-a introdus nr thread-ului incorect");
			return -1;
		}
	}	
}
