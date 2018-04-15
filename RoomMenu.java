import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

public class RoomMenu extends Menu {
	//Room Menu ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public static void roomMenu(ArrayList<Guest>guestList, ArrayList<Room>roomList, 
			ArrayList<Reservation>reservationList, ArrayList<RoomService>serviceList,
			ArrayList<Payment>paymentList) throws FileNotFoundException{

		int choice = 0;
		ReservationFileIO refio = new ReservationFileIO();
		RoomFileIO rfio = new RoomFileIO();
		//Select menu
		do {
			//Print room menu
			printRoomMenu();
			System.out.println(" -------------------------------------------");
			//Get user's choice
			choice = readInt(" Please enter your choice: ");

			switch(choice) {
			case 1: searchRoomByName(roomList,guestList);
					break;
			case 2: searchRoomByNo(roomList);
					break;
			case 3: updateRoom(roomList);
					rfio.exportAll(roomList);
					break;
			case 4: checkIn(guestList,roomList,reservationList);
					rfio.exportAll(roomList);
					refio.exportAll(reservationList);
					break;
			case 5: checkOut(guestList,roomList,reservationList, serviceList,paymentList);
					rfio.exportAll(roomList);
					refio.exportAll(reservationList);
					break;
			case 6: System.out.println("Printing Room Status statistic report");
					printRoomReport(roomList);
					break;
			case 7: System.out.println("Returning to main menu...");
					rfio.exportAll(roomList);
					refio.exportAll(reservationList);
					break;
			case 8: System.out.println("Exiting...");
					rfio.exportAll(roomList);
					refio.exportAll(reservationList);
					System.exit(0);
					break;		
			default:System.out.println("Wrong Input. Please input from 1 - 8.");
					break;
			}
		} while (choice != 7);  
	}	        	

	//print Menu
	public static void printRoomMenu() {
		System.out.println(" ===========================================");
		System.out.println(" *                  Room                   *");
		System.out.println(" ===========================================");
		System.out.println(" * 1. Check Room Status by Guest Name      *");
		System.out.println(" * 2. Check Room Status by Room	Number     *");
		System.out.println(" * 3. Update Room Details                  *");
		System.out.println(" * 4. Room Check-in                        *");
		System.out.println(" * 5. Room Check-out                       *");
		System.out.println(" * 6. Print Room Status statistic report   *");
		System.out.println(" * 7. Previous                             *");
		System.out.println(" * 8. Quit                                 *");
	}	

	//Print details of room base on guest names
	public static void searchRoomByName(ArrayList<Room>roomList,ArrayList<Guest>guestList) {
		//Ask for guest name as primary key, IC is being return as the identifier
		String identifier = readString("Please enter the guest name you would like to search ('Enter' key to display all result): ");
		String IC =	guestNameSearch(guestList, identifier);

		if (!IC.equals("")) {
			int index = roomICSearch(roomList, IC);
			if (index == -1){
				System.out.println("Guest is not assigned to any room");
			} else {
				System.out.println(" -------------------------------------------");
				System.out.println("Room No: " + roomList.get(index).getRoomId() +
						"\nBed Type: " + roomList.get(index).getBedType() +
						"\nRoom Status: " + roomList.get(index).getRoomStatus() +
						"\nCustomer Name: " + roomList.get(index).getCustomerName() +
						"\nCheck In: " + roomList.get(index).getCheckInDate() +
						"\nCheck Out: " + roomList.get(index).getCheckOutDate() +
						"\nNumber of pax staying: " + roomList.get(index).getPax());
				System.out.println(" -------------------------------------------");
			}
		} else {
			System.out.println("Guest is not register in the System!");
			System.out.println("Please create a new guest!");
		}
	}	

	//Print details of room base on room ID
	public static void searchRoomByNo(ArrayList<Room>roomList) {
		//Ask for room ID
		String identifier = readString("Please enter the room number you would like to search: ");
		int index = roomIDSearch(roomList, identifier);
		if (index == -1) {
			System.out.println("Room with room ID: " + identifier + " not found!");
		} else {
			System.out.println("Room with room ID: " + identifier + " found!");
			System.out.println(" -------------------------------------------");
			System.out.println("Room No: " + roomList.get(index).getRoomId() +
					"\nBed Type: " + roomList.get(index).getBedType() +
					"\nRoom Status: " + roomList.get(index).getRoomStatus() +
					"\nCustomer Name: " + roomList.get(index).getCustomerName() +
					"\nCheck In: " + roomList.get(index).getCheckInDate() +
					"\nCheck Out: " + roomList.get(index).getCheckOutDate() +
					"\nNumber of pax staying: " + roomList.get(index).getPax());            
			System.out.println(" -------------------------------------------");
		}
	}        

	//Update details of room 
	public static void updateRoom(ArrayList<Room>roomList) {
		//Ask for room ID
		String identifier = readNonEmptyString("Please enter the room ID to update it's details: ");
		boolean input = false;
		int index = roomIDSearch(roomList,identifier);
		if (index == -1) {
			System.out.println("Room with ID: " + identifier + " not found!");
		} else {
			System.out.println("Room with ID: " + identifier + " found!");
			System.out.println(" -------------------------------------------");
			System.out.println("Please enter new room details ('Enter' key to skip)");
			do {
				String type = readString("Enter new bed type [(S)ingle/(D)ouble/(K)ing]:  ");
				switch(type.toUpperCase()) {
					case "S":	roomList.get(index).setBedType(Room.BedType.SINGLE_SIZE);
								input = true;
								break;
					case "D": 	roomList.get(index).setBedType(Room.BedType.DOUBLE_SIZE);
								input = true;
								break;
					case "K": 	roomList.get(index).setBedType(Room.BedType.KING_SIZE);
								input = true;		  
								break;
					case "":  	input = true;
								break;
					default:	System.out.println("Please enter [(S)ingle/(D)ouble/(K)ing]");
								System.out.println(" Please try again with the correct input !"); 
								input = false; 
								break;
				}
			}while(!input);

			String status = readString("Enter new room status: [(V)acant/(O)ccupied/(R)eserved/(U)nder Maintenance]");
			do {
				switch(status.toUpperCase()) {
					case "V":	roomList.get(index).setRoomStatus(Room.RoomStatus.VACANT);
								input = true;
								break;
					case "O":	roomList.get(index).setRoomStatus(Room.RoomStatus.OCCUPIED);
								input = true;
								break;
					case "R":	roomList.get(index).setRoomStatus(Room.RoomStatus.RESERVED);
								input = true;
								break;
					case "U":	roomList.get(index).setRoomStatus(Room.RoomStatus.UNDER_MAINTENANCE);
								input = true;
								break;
					case "":	input = true;
								break;
					default:	System.out.println("Please enter (V)acant/(O)ccupied/(R)eserved/(U)nder Maintenance");
								System.out.println("Please try again with the correct input !"); 
								input = false; 
								break;
				}
			}while(!input);   
			System.out.println("-------------------------------------------");
			System.out.println("Room updated!");                  
		}
	}        

	//New check CheckIn
	public static void checkIn(ArrayList<Guest>guestList, ArrayList<Room>roomList, ArrayList<Reservation>reservationList) {
		System.out.println(" ===========================================");
		System.out.println(" 1. Walk In                                *");
		System.out.println(" 2. Reservation                            *");
		System.out.println(" ===========================================");
		int choice = readInt(" Please enter the check in method: ");

		if (choice == 1) {
			walkInCheckIn(roomList, guestList, reservationList);
		} else if (choice == 2) {
			reservationCheckIn(roomList, guestList, reservationList);
		} else {
			System.out.println("Wrong input, please enter 1 or 2.");
		}
	}        


	public static void walkInCheckIn(ArrayList<Room>roomList, ArrayList<Guest>guestList, ArrayList<Reservation>reservationList) {
		String identifier = readNonEmptyString("Please enter the guest IC Number: ");
		int index = guestICSearch(guestList, identifier);

		if (index == -1) {
			System.out.println("Guest with IC: " + identifier + " not found!");
		} else {

			Date checkIn = new Date();
			Date checkOut;
			do {
				checkOut = readDate("Please enter the check out date [DD/MM/YYYY]: ");
			}while(checkIn.after(checkOut));
			
			//This method display all room types according to what the user choose 
			String []roomIdarr = searchRoomType(roomList, checkIn, checkOut);

			String roomID;
			int roomIndex;
			boolean input = false;
			do {
				//check if there is such a room
				do {
					roomID = readNonEmptyString("Please enter the room ID: ");
					roomIndex = roomIDSearch(roomList,roomID);
				}while(roomIndex == -1);
				
				//check if this room is in the list of rooms that are available 
				for(int i = 0; roomIdarr.length >= i ;i++) {
					if (roomID.equals(roomIdarr[i])) {
						input = true;
						break;
					}
				}
			} while(!input);
/*			
			if(roomList.get(roomIndex).getRoomStatus().equals(Room.RoomStatus.RESERVED) || 
				roomList.get(roomIndex).getRoomStatus().equals(Room.RoomStatus.VACANT)) {
	
					if(roomList.get(roomIndex).getRoomStatus().equals(Room.RoomStatus.RESERVED)){
						int reservationIndex = reservationSearch(reservationList, roomID);
						if(checkOut.before(reservationList.get(reservationIndex).getCheckInDate())){
							System.out.println("Room is currently reserved from " 
									+ reservationList.get(reservationIndex).getCheckInDate() + " to "
									+ reservationList.get(reservationIndex).getCheckOutDate());
							System.out.println("Please select another room!");
						}
					}
*/
					int pax = readInt("Please enter the number of pax staying: ");

					roomList.get(roomIndex).setGuestIC(guestList.get(index).getIc());
					roomList.get(roomIndex).setCustomerName(guestList.get(index).getName());
					roomList.get(roomIndex).setCheckInDate(checkIn);
					roomList.get(roomIndex).setCheckOutDate(checkOut);
					roomList.get(roomIndex).setPax(Integer.toString(pax));
					roomList.get(roomIndex).setRoomStatus(Room.RoomStatus.OCCUPIED);
					System.out.println("Guest " + guestList.get(index).getName() + " have successfully checked in room " + roomID);

				}
	}

	public static void reservationCheckIn(ArrayList<Room>roomList, ArrayList<Guest>guestList, ArrayList<Reservation>reservationList) {
		int resIdentifier = readInt(" Please enter the reservation number: ");
		int index = reservationSearch(reservationList, resIdentifier);
		if (index == -1) {
			System.out.println("Reservation number: " + resIdentifier + " not found!");
		} else {
			System.out.println("Reservation number: " + resIdentifier + " found!");
			int roomIndex = roomIDSearch(roomList, reservationList.get(index).getRoomId());
			if (roomIndex == -1) {
				System.out.println("Room ID: " + reservationList.get(index).getRoomId() + " not found!");
			} else {
				roomList.get(roomIndex).setRoomStatus(Room.RoomStatus.OCCUPIED);
				roomList.get(roomIndex).setCustomerName(reservationList.get(index).getGuestName());
				roomList.get(roomIndex).setCheckInDate(reservationList.get(index).getCheckInDate());
				roomList.get(roomIndex).setCheckOutDate(reservationList.get(index).getCheckOutDate());
				roomList.get(roomIndex).setPax(reservationList.get(index).getPax());
				reservationList.get(index).setReserveStatus(Reservation.ReservationStatus.CHECKED_IN);
			}
		}
	}




	public static void checkOut(ArrayList<Guest>guestList, ArrayList<Room>roomList, ArrayList<Reservation>reservationList, 
			ArrayList<RoomService>serviceList, ArrayList<Payment>paymentList) {
		String identifier = readString("Please enter the room ID: ");

		int index = roomIDSearch(roomList, identifier);
		if (index == -1) {
			System.out.println("Room with ID: " + identifier + " not found!");
		} else {
			if (roomList.get(index).getRoomStatus().equals(Room.RoomStatus.OCCUPIED)) {
				System.out.println("Room with ID: " + identifier + " found!");
				int reservationIndex = reservationSearch(reservationList,identifier);
				if (reservationIndex == -1) {
					System.out.println("Reservation with ID: " + identifier + " and check out date: " + roomList.get(index).getCheckOutDate() + " not found!");
				} else {
					System.out.println("Reservation with ID: " + identifier + " and check out date: " + roomList.get(index).getCheckOutDate() + " found!");
					reservationList.remove(reservationIndex);
				}
				PaymentMenu.printInvoice(paymentList, serviceList, roomList, index);

				roomList.get(index).setCustomerName("-");
				roomList.get(index).setCheckInDate(null);
				roomList.get(index).setCheckOutDate(null);
				roomList.get(index).setPax("-");
				roomList.get(index).setRoomStatus(Room.RoomStatus.VACANT);
			} else 
				System.out.println("Room is not occupied.");
		}
	}

	public static void printRoomReport(ArrayList<Room>roomList) {

		//Print room occupancy rate
		System.out.println("Printing occupancy rate");
		System.out.println(" -------------------------------------------");

		//Get single room occupancy rate
		int single_occuCount = 0;
		int single_totalCount = 0;
		//Get double room occupancy rate
		int double_occuCount = 0;
		int double_totalCount = 0;
		//Get deluxe room occupancy rate
		int deluxe_occuCount = 0;
		int deluxe_totalCount = 0;
		//Get VIP room occupancy rate
		int vip_occuCount = 0;
		int vip_totalCount = 0;


		ArrayList<Room>single_occuList = new ArrayList<Room>();
		ArrayList<Room>double_occuList = new ArrayList<Room>();
		ArrayList<Room>deluxe_occuList = new ArrayList<Room>();
		ArrayList<Room>vip_occuList = new ArrayList<Room>();

		for (Room r: roomList) {
			if (r instanceof Room_single) {
				if (r.getRoomStatus() == Room.RoomStatus.OCCUPIED) {
					single_occuCount++;
					single_occuList.add(r);
				}
				single_totalCount++;
			}
			if (r instanceof Room_double) {
				if (r.getRoomStatus() == Room.RoomStatus.OCCUPIED) {
					double_occuCount++;
					double_occuList.add(r);
				}
				double_totalCount++;
			}
			if (r instanceof Room_deluxe) {
				if (r.getRoomStatus() == Room.RoomStatus.OCCUPIED) {
					deluxe_occuCount++;
					deluxe_occuList.add(r);
				}
				deluxe_totalCount++;
			}

			if (r instanceof Room_vip) {
				if (r.getRoomStatus() == Room.RoomStatus.OCCUPIED) {
					vip_occuCount++;
					vip_occuList.add(r);
				}
				vip_totalCount++;
			}
		}

		System.out.println("Single:  Number: " + single_occuCount + " out of " + single_totalCount);
		System.out.print("         Rooms: ");
		printRooms (single_occuList);

		System.out.println("Double:  Number: " + double_occuCount + " out of " + double_totalCount);
		System.out.print("         Rooms: ");
		printRooms (double_occuList);

		System.out.println("Deluxe:  Number: " + deluxe_occuCount + " out of " + deluxe_totalCount);
		System.out.print("         Rooms: ");
		printRooms (deluxe_occuList);

		System.out.println("VIP:     Number: " + vip_occuCount + " out of " + vip_totalCount);
		System.out.print("         Rooms: ");
		printRooms (vip_occuList);

		//Print room status
		System.out.println("Printing room status");
		System.out.println(" -------------------------------------------");

		//Get vacant rooms
		ArrayList<Room>vacantList = new ArrayList<Room>();
		//Get occupied rooms
		ArrayList<Room>occupiedList = new ArrayList<Room>();
		//Get reserved rooms
		ArrayList<Room>resList = new ArrayList<Room>();
		//Get maintenance rooms
		ArrayList<Room>maintList = new ArrayList<Room>();

		for (Room r: roomList) {
			if (r.getRoomStatus() == Room.RoomStatus.VACANT) {
				vacantList.add(r);
			}			
			if (r.getRoomStatus() == Room.RoomStatus.OCCUPIED) {	
				occupiedList.add(r);
			}
			if (r.getRoomStatus() == Room.RoomStatus.RESERVED) {
				resList.add(r);
			}
			if (r.getRoomStatus() == Room.RoomStatus.UNDER_MAINTENANCE) {
				maintList.add(r);
			}
		}

		System.out.println("Vacant: ");
		System.out.print("         Rooms: ");
		printRooms (vacantList);

		System.out.println("Occupied: ");
		System.out.print("         Rooms: ");
		printRooms (occupiedList);

		System.out.println("Reserved: ");
		System.out.print("         Rooms: ");
		printRooms (resList);

		System.out.println("Under maintenance: ");
		System.out.print("         Rooms: ");
		printRooms (maintList);
	}

	public static void printRooms (ArrayList<Room>occuList) {

		if (occuList.isEmpty())
			System.out.print("None");
		else {	
			for (Room r: occuList) {
				String roomId = r.getRoomId();
				roomId = roomId.substring(0, 2) + "-" + roomId.substring(2, roomId.length());
				System.out.print(roomId + " ");
			}
		}

		System.out.println("\n");
	}


}