package packWork;

public class ExecutionWriterResult extends ExecutionTimeConsumer {
	protected int threadOneW;
	protected int threadTwoW;
	protected int threadThreeW;
	protected int threadFourW;
	
	public ExecutionWriterResult(int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9, int t10, int t11, int t12){
		super( t1, t2, t3, t4, t5, t6, t7, t8);
		this.threadOneW = t9;
		this.threadTwoW = t10;
		this.threadThreeW = t11;
		this.threadFourW = t12;
	}
	
	
	public void longestWriterThread(int ... a){
		int i, max = 0;
		for (i=0;i<a.length;i++){
			if(max < a[i]){
				max = a[i];
			}
		}
		for (i=0;i<a.length;i++){
			if(max == a[i])
			{
				System.out.println("longestWriterThread: thread-ul cu numarul " + (i+1) + " a durat cel mai mult: " + a[i] + " ms");
			}
		}
            
	}
	
	public void setExecutionWriterResult(int threadNr, int time1, int time2, int time3, int time4){
		if(threadNr == 1){
			this.threadOneW = time1;
		}
		else if(threadNr == 2){
			this.threadTwoW = time2;
		}
		else if(threadNr == 3){
			this.threadThreeW = time3;
		}
		else if(threadNr == 4){
			this.threadFourW = time4;
		}
		else{
			System.out.println("setExecutionWriterResult: S-a introdus nr thread-ului incorect");
		}
	}	
	
	public int getExecutionWriterResult(int threadNr){
		if(threadNr == 1){
			return this.threadOneW;
		}
		else if(threadNr == 2){
			return this.threadTwoW;
		}
		else if(threadNr == 3){
			return this.threadThreeW;
		}
		else if(threadNr == 4){
			return this.threadFourW;
		}
		else{
			System.out.println("getExecutionWriterResult: S-a introdus nr thread-ului incorect");
			return -1;
		}
	}
}