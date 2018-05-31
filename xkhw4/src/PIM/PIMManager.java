/**
 * @Description
 * @Author: XiongKai
 * @studentNo 15130120199
 * @Emailaddress 1249235131@qq.com
 */

package PIM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;

class PIMTodo extends PIMEntity{
    String date,todoText;
    
	public void fromString(ArrayList<String> list){
		owner = list.get(0);
		sharedFlag = list.get(1);
        date=list.get(2);
        todoText=list.get(3);
        Priority=list.get(4);
    }
    public String toString() {
        return(" :TODO " + owner + " "+ sharedFlag + " " + Priority + " " + date + " " + todoText);
    }
    
    public Date getDate() throws Exception {
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	return df.parse(date);
    }
}
class PIMNote extends PIMEntity{
    String noteText;
    public void fromString(ArrayList<String> list) {
    	owner = list.get(0);
		sharedFlag = list.get(1);
        noteText=list.get(2);
        Priority=list.get(3);
    }
    public String toString() {
        return(" :NOTE " + owner + " "+ sharedFlag + " " + Priority + " " + noteText);
    }
}
class PIMAppointment extends PIMEntity{
    String date,description;
    public void fromString(ArrayList<String> list) {
    	owner = list.get(0);
		sharedFlag = list.get(1);
        date=list.get(2);
        description=list.get(3);
        Priority=list.get(4);
    }
    public String toString() {
        return(" :APPOINTMENT " + owner + " "+ sharedFlag + " " + Priority + " " + date + " " + description);
    }
    
    public Date getDate() throws Exception {
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	return df.parse(date);
    }
}
class PIMContact extends PIMEntity{
    String firstname,lastname,email;
    public void fromString(ArrayList<String> list) {
    	owner = list.get(0);
		sharedFlag = list.get(1);
        firstname=list.get(2);
        lastname=list.get(3);
        email=list.get(4);
        Priority=list.get(5);
    }
    public String toString() {
        return(" :CONTACT " + owner + " "+ sharedFlag + " " + Priority + " " +firstname + " " + lastname + " " + email);
    }
}

public class PIMManager {
	static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public static int itemId = 0;
    public static PIMCollection listItem=new PIMCollection();
    public static String owner;
    
    public static void main (String[] args) throws Exception{
    	commands();
    }
    
    public static void commands() throws Exception{
        System.out.println("Welcome to PIM.");
        Scanner in = new Scanner(System.in);
        while(listItem.size()<=100) {
            System.out.println("---Enter a command (List Create Delete Save Load Query Quit)---");
            String input = in.nextLine();
            ArrayList<String> list=new ArrayList<String>();
            switch(input) {
                case "List":
                    System.out.println("There are " + itemId + " items");
                    for(int i = 1;i <= itemId;i++) {
                        System.out.println("Item " + i + listItem.get(i-1));
                    }
                    break;
                case "Create":
                    System.out.println("Enter an item type(todo,note,contact or appointment)");
                    input = in.nextLine();
                    switch(input) {
                        case"todo":
                            listItem.add(new PIMTodo());
                            list.clear();
                            System.out.println("Enter date for todo item(dd/MM/yyyy)");
                            list.add(in.nextLine());
                            System.out.println("Enter todo text");
                            list.add(in.nextLine());
                            System.out.println("Enter todo priority");
                            list.add(in.nextLine());
                            itemId++;
                            listItem.get(listItem.size()-1).fromString(list);
                            break;
                        case"note":
                            listItem.add(new PIMNote());
                            list.clear();
                            System.out.println("Enter note text");
                            list.add(in.nextLine());
                            System.out.println("Enter note priority");
                            list.add(in.nextLine());
                            itemId++;
                            listItem.get(listItem.size()-1).fromString(list);
                            break;
                        case"appointment":
                            listItem.add(new PIMAppointment());
                            list.clear();
                            System.out.println("Enter date for appointment item(dd/MM/yyyy)");
                            list.add(in.nextLine());
                            System.out.println("Enter appointment description");
                            list.add(in.nextLine());
                            System.out.println("Enter appointment priority");
                            list.add(in.nextLine());
                            itemId++;
                            listItem.get(listItem.size()-1).fromString(list);
                            break;
                        case"contact":
                            listItem.add(new PIMContact());
                            list.clear();
                            System.out.println("Enter firstname for contact item");
                            list.add(in.nextLine());
                            System.out.println("Enter lastname for contact item");
                            list.add(in.nextLine());
                            System.out.println("Enter email for contact item");
                            list.add(in.nextLine());
                            System.out.println("Enter contact priority");
                            list.add(in.nextLine());
                            itemId++;
                            listItem.get(listItem.size()-1).fromString(list);
                            break;
                        default:
                            System.out.println("the item type is not exist");
                            break;
                    }
                    break;
                case"Save":
                    save();
                    break;
                case"Load":
                	load();
                	break;
                case "Query":
                	System.out.println("Enter what you want to query(todo,note,contact,appointment,date or priority)");
                    input = in.nextLine();
                    Collection itemArr = new PIMCollection();
                    switch(input) {
                    case"todo":
                        itemArr = listItem.getTodos();
                        printQuery(itemArr);
                        break;
                    case"note":
                    	itemArr = listItem.getNotes();
                        printQuery(itemArr);
                        break;
                    case"appointment":
                    	itemArr = listItem.getAppointmnets();
                        printQuery(itemArr);
                        break;
                    case"contact":
                    	itemArr = listItem.getContacts();
                        printQuery(itemArr);
                        break;
                    case"date":
                    	System.out.println("Enter the date you want to query(dd/MM/yyyy)");
                    	input = in.nextLine();
                    	Date date = df.parse(input);
                    	itemArr = listItem.getItemsForDate(date);
                        printQuery(itemArr);
                        break;
                    case"priority":
                    	System.out.println("Enter the priority (urgent > important > ordinary > negligible)");
                    	input = in.nextLine();
                    	itemArr = listItem.getItemsForPriority(input);
                    	printQuery(itemArr);
                    	break;
                    default:
                        System.out.println("Input error: " + input);
                        break;
                }
                	break;
                case"Delete":
                	System.out.println("Enter the item num which you want to delete");
                	int num = Integer.parseInt(in.nextLine());
                	listItem.remove(num - 1);
                	itemId--;
                	System.out.println("Delete the " + num + " item successfully");
                	break;
                case"Quit":
                    in.close();
                    return;
                default:
                    System.out.println("Error command!");
                    break;
            }
        }
        in.close();
    }

    public static void save() {
    	try {
    		 BufferedWriter bw = new BufferedWriter(new FileWriter("PIM.txt"));
    	        for (int i = 0; i < listItem.size(); i++) {
    				bw.write("Item "+(i+1)+listItem.get(i).toString());
    				bw.newLine();
    			}
    	        bw.close();
    	        System.out.println(itemId + " item(s) have been saved.");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public static void load(){
    	listItem.clear();
    	itemId = 0;
    	try {
    		BufferedReader br = new BufferedReader(new FileReader("PIM.txt"));
        	ArrayList<String> list=new ArrayList<String>();
        	String pim;
            while ((pim = br.readLine()) != null) {
            	int index = pim.indexOf(':');
            	String sItem = pim.substring(index + 1);
            	String[] itemArr = sItem.split(" ",2);
            	list.clear();
            	switch (itemArr[0]) {
    			case "TODO":
    				listItem.add(new PIMTodo());
    				String[] todoArr = itemArr[1].split(" ",5);
    				list.add(todoArr[0]);
    				list.add(todoArr[1]);
                    list.add(todoArr[3]);
                    list.add(todoArr[4]);
                    list.add(todoArr[2]);
                    itemId++;
                    listItem.get(listItem.size()-1).fromString(list);
    				break;
    			case"NOTE":
                    listItem.add(new PIMNote());
                    String[] noteArr = itemArr[1].split(" ",4);
                    list.add(noteArr[0]);
                    list.add(noteArr[1]);
                    list.add(noteArr[3]);
                    list.add(noteArr[2]);
                    itemId++;
                    listItem.get(listItem.size()-1).fromString(list);
                    break;
                case"APPOINTMENT":
                    listItem.add(new PIMAppointment());
                    String[] appointmentArr = itemArr[1].split(" ",5);
                    list.add(appointmentArr[0]);
                    list.add(appointmentArr[1]);
                    list.add(appointmentArr[3]);
                    list.add(appointmentArr[4]);
                    list.add(appointmentArr[2]);
                    itemId++;
                    listItem.get(listItem.size()-1).fromString(list);
                    break;
                case"CONTACT":
                    listItem.add(new PIMContact());
                    String[] contactArr = itemArr[1].split(" ",6);
                    list.add(contactArr[0]);
                    list.add(contactArr[1]);
                    list.add(contactArr[3]);
                    list.add(contactArr[4]);
                    list.add(contactArr[5]);
                    list.add(contactArr[2]);
                    itemId++;
                    listItem.get(listItem.size()-1).fromString(list);
                    break;
    			default:
    				break;
    			}
            }
            br.close();
            System.out.println(itemId + " item(s) have been loaded.");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public static void printQuery (Collection arr) {
    	int items = arr.size();
        System.out.println("There are " + items + " item(s)");
        Iterator iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
        	i++;
        	System.out.println("Item " + i + iter.next());
        }
    }
}
