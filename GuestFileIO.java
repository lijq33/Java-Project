import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GuestFileIO extends FileIO<Guest>{

	//Attributes
	final static String fileName = "Guestlist.txt";
	final static File file = new File(fileName);

	//Retrieve data from file
	public void parseList (ArrayList<Guest> glist) throws FileNotFoundException{
		// to create file when it does not exist, else Exception will be thrown
		try { 
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner myScanner = new Scanner (new File (fileName));
		String str;

		while (myScanner.hasNextLine()) {
			str = myScanner.nextLine();
			String[] arr = str.split("\\|");

			Guest temp = new Guest (arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]);
			glist.add(temp);
		}

		myScanner.close();
	}

	public void export (Guest g, PrintWriter fout) {
		fout.print(g.getName() + "|");
		fout.print(g.getAddress() + "|");
		fout.print(g.getCountry() + "|");
		fout.print(g.getGender() + "|");
		fout.print(g.getNationality() + "|");
		fout.print(g.getIdentity() + "|");
		fout.print(g.getIc() + "|");
		fout.print(g.getCreditDetails() + "|");
		fout.println(g.getContact());
	}

	public void exportAll (ArrayList<Guest> glist) throws FileNotFoundException {
		PrintWriter fileOut = new PrintWriter (new FileOutputStream (fileName, false));

		for (Guest temp: glist) 
			export (temp, fileOut);

		fileOut.close();
	}
}
