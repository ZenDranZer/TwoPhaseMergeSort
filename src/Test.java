import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) throws IOException 
	{
		long start = System.currentTimeMillis();
		Test obj=new Test();
		int m=3;
		String[] chunk=new String[m];
		int count=0;
		//Scanner sc=new Scanner(new File("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\input2.txt"));
		//Scanner sc=new Scanner(new File("input.txt"));
		//sc.nextLine();
		//sc.nextLine();
		BufferedReader fileBufferReader = new BufferedReader(new FileReader("C:\\Users\\Shivam\\eclipse-workspace\\GuessGame\\number1.txt"));
		fileBufferReader.readLine();
		fileBufferReader.readLine();
		try {
			String fileLineContent;
			while ((fileLineContent = fileBufferReader.readLine()) != null)
	        {
	            chunk [count] = fileLineContent;
	            count++;
	            if(count%m==0)
	            {
	            	obj.sort(chunk,count);
	            	count=0;
	            }
	        }
	        if(count!=0)
	        {
	        	//BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\Output.txt",true));
	        	if(count==2)
	        	{
	        		obj.sort(chunk, count);
	        		//out.write(chunk[0]);
	        		//out.write(chunk[1]);
	        	}
	        	else
	        	{
	        		obj.sort(chunk, count);
	        		//out.write(chunk[0]);
	        	}
	        	//out.close();
	        }
	        fileBufferReader.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; System.out.println(sec + " seconds");
		
	 }
	public void sort(String[] chunk,int len) throws IOException
	{
		
		BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\Output.txt",true));
		//BufferedWriter out = new BufferedWriter(new FileWriter("Output.txt",true));
			for(int i=0;i<len;i++)
			{
				String [] arrr=chunk[i].split(" ");
				int a[] =new int[arrr.length];
				for(int j=0;j<arrr.length;j++)
				{
					a[j]=Integer.parseInt(arrr[j]);
				}
				Arrays.sort(a);
				for(int k=0;k<a.length;k++)
				{
					out.write(String.valueOf(a[k]).concat(" "));
				}
				out.newLine();
				out.flush();  
				}
			
	}
}
