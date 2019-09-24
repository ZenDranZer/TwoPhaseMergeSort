import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ReadingFile 
{
	
	public static void main(String[] args) throws IOException 
	{
		ReadingFile obj=new ReadingFile();
		int m=3;
		String[] chunk=new String[m];
		int count=0;
		//Scanner sc=new Scanner(new File("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\input.txt"));
		Scanner sc=new Scanner(new File("input.txt"));
		sc.nextLine();
		sc.nextLine();
		try {
			while (sc.hasNextLine()) 
	        {
	            chunk [count] = sc.nextLine();
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
	       
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	 }
	public void sort(String[] chunk,int len) throws IOException
	{
		
		//BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\Output.txt",true));
		BufferedWriter out = new BufferedWriter(new FileWriter("Output.txt",true));
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
					out.write(a[k]+" ");
				}
				out.newLine();
				out.flush();  
				}
	}
	}
	



