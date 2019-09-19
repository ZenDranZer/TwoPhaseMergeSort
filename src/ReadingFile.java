import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		String sCurrentLine;
		//BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\Sample.txt"));
		Scanner sc=new Scanner(new File("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\Sample.txt"));
		try {
			while (sc.hasNextLine()) 
	        {
	            chunk [count] = sc.nextLine();
	            count++;
	            if(count%m==0)
	            {
	            	obj.sort(chunk);
	            	count=0;
	            }
	        }
	        if(count!=0)
	        {
	        	BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\Output.txt",true));
	        	if(count==2)
	        	{
	        		
	        		out.write(chunk[0]);
	        		out.write(chunk[1]);
	        	}
	        	else
	        	{
	        		out.write(chunk[0]);
	        	}
	        	out.close();
	        }
	       
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	 }
	public void sort(String[] chunk) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Shivam\\git\\TwoPhaseMergeSort\\src\\Output.txt",true));
		for(int i=0;i<chunk.length;i++)
		{

			
			if (chunk[i] != null) {
				out.write(chunk[i]);
				out.newLine();
			}
		}
		out.close();
		}
	
	}
	



