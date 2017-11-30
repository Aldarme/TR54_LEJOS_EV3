package exporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.omg.CORBA.Environment;

/*
 * allow to create file on our PC
 */
public class CsvExporter
{	
	static FileWriter fileWriter;
	static BufferedWriter bfWriter;
	
	private static final String nameFile = "textValuesTp1.csv";
	
	private CsvExporter() 
	{
		 
	}
	
	public static void createFile()
	{
		try 
		{
			fileWriter = new FileWriter(nameFile, true);
			bfWriter = new BufferedWriter(fileWriter);			
		
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Writefile(float pValue)
	{
		try
		{	    
		    bfWriter. write(Float.toString(pValue)+ "\r\n");
		    bfWriter.flush();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	}
	
	public static void closeFile()
	{
		try
		{	    
		    bfWriter.close();
		    fileWriter.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	}
}