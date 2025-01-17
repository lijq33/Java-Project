import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentFileIO extends FileIO<Payment>{

	//Attributes
	final static String fileName = "PaymentRates.txt";
	final static File file = new File(fileName);

	//Retrieve data from file
	public void parseList (ArrayList<Payment> plist) throws FileNotFoundException{
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

			Payment temp = new Payment(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), Double.parseDouble(arr[2]), 
					Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5]), Double.parseDouble(arr[6]), 
					Double.parseDouble(arr[7]), Double.parseDouble(arr[8]), Double.parseDouble(arr[9]));
			plist.add(temp);
		}

		myScanner.close();
	}
	public void export (Payment p, PrintWriter fout) {
		fout.print(p.getTax() + "|");
		fout.print(p.getPromo() + "|");
		fout.print(p.getWifiPrice() + "|");
		fout.print(p.getTvPrice() + "|");
		fout.print(p.getWErates() + "|");
		fout.print(p.getSingleRoomPrice() + "|");
		fout.print(p.getDoubleRoomPrice() + "|");
		fout.print(p.getDeluxeRoomPrice() + "|");
		fout.print(p.getVipRoomPrice() + "|");
		fout.println(p.getOverStayingFine());
	}

	public void exportAll (ArrayList<Payment> plist) throws FileNotFoundException {
		PrintWriter fileOut = new PrintWriter (new FileOutputStream (fileName, false));

		for (Payment temp: plist) 
			export (temp, fileOut);

		fileOut.close();
	}
}