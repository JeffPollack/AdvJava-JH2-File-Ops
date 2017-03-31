import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileOperations 
{

	StringTokenizer parseCommand = null;

	public void delete()
	{
		File fDelete = getFile();
		if (fDelete.delete())
			System.out.println("File was deleted");
		else
			System.out.println("File could not be deleted");

	}

	public void rename()
	{

		File f = getFile();
		File f2 = new File(getNextToken());
		if (f.renameTo(f2))
			System.out.println("File has been renamed to " + f2);
		else
			System.out.println("Unable to rename file " + f);
	}

	public void list()
	{
		File currWorkingDirectory = getFile();

		String[] fName = currWorkingDirectory.list();
		for (int i=0; i < fName.length; i++)
			System.out.println(fName[i]);

	}

	public void size()
	{
		File f = getFile();
		long size = f.length();
		System.out.printf("Size for "+ f +": " + size);       
	}

	public void lastModified()
	{

		File fs = getFile();
		long time = fs.lastModified();
		Date d = new Date(time);
		System.out.printf("Last modified for " + fs + " At date: " + d);

	}

	public void mkdir()
	{
		File dir = getFile();
		if (dir.mkdir())
			System.out.println("Directory was created as: " + dir);
		else
			System.out.println("Couldnt create Dir");
	}
	public void createFile()
	{

		PrintStream ps;
		File file = getFile();
		String next = getNextToken();

		try {
			ps = new PrintStream(file);
		} catch (FileNotFoundException e1) {
			System.out.printf("%n" + e1 + "%n");
			return;
		}

		while (next != null)
		{
			ps.println(next);
			System.out.print(" " + next);
			next = getNextToken();
		}

		ps.close();

	}

	public void printFile()
	{
		
		File file = getFile();
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");	
			return;
		}
		while (sc.hasNextLine())
			System.out.println(sc.nextLine());
		
		sc.close();
		

	}

	void printUsage()
	{

		System.out.printf("%n? %nquit %ndelete filename %nrename oldFilename newFilename %nsize filename "
				+ "%nlastModified filename %nlist dir %nprintFile filename %ncreateFile filename %nmkdir dir %nquit");

	}

	private String getNextToken()
	{
		if (parseCommand.hasMoreTokens())
			return parseCommand.nextToken();
		else
			return null;
	}

	private File getFile()
	{
		File f = null;
		String fileName = getNextToken();
		if (fileName == null)     
			System.out.println("Missing a File name");                  
		else
			f = new File(fileName);      

		return f;
	}

	public boolean processCommandLine(String line)    
	{
		System.out.println("\n\n***************Processing: " + line);
		parseCommand = new StringTokenizer(line);
		String command;
		
		switch (command = getNextToken())
		{

		case "?": printUsage();
		break;
		case "createFile": createFile();
		break;
		case "printFile": printFile();
		break;
		case "lastModified": lastModified();
		break;
		case "size": size();
		break;
		case "rename": rename();
		break;
		case "mkdir": mkdir();
		break;
		case "delete": delete();
		break;
		case "list": list();
			break;
		case "quit": 
			return false;

		default: System.out.printf("%nInvalid command entered%n*********************************");
		break;
		}
		
		return false;
	}

	void processCommandFile(String commandFile)
	{
		Scanner scanner=null;

		try
		{
			FileInputStream fi = new FileInputStream(commandFile);
			scanner = new Scanner(fi);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("error: "+e);
		}
		while (scanner.hasNext() != false)
		{
			String s = scanner.nextLine();
			
			processCommandLine(s);			
		}


		if (scanner != null)
			scanner.close();

	}

	public static void main(String[] args) 
	{
		FileOperations fo= new FileOperations();
		for (int i=0; i < args.length; i++)
		{
			System.out.println("\n\n============  Processing " + args[i] +" =======================\n");
			fo.processCommandFile(args[i]);
		}
		System.out.printf("%nDone with FileOperations");

	}
}