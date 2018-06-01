package PIM;

import java.util.Date;

public interface RemotePIMCollection {
	  public PIMCollection getNotes() throws Exception;
	  public PIMCollection getNotes(String owner) throws Exception;
	  public PIMCollection getTodos() throws Exception;
	  public PIMCollection getTodos(String owner) throws Exception;
	  public PIMCollection getAppointments() throws Exception;
	  public PIMCollection getAppointments(String owner) throws Exception;
	  public PIMCollection getContacts() throws Exception;
	  public PIMCollection getContacts(String owner) throws Exception;
	  public PIMCollection getItemsForDate(Date d) throws Exception;
	  public PIMCollection getItemsForDate(Date d, String owner) throws Exception;
	  public PIMCollection getAll() throws Exception;
	  public PIMCollection getAllByOwner(String owner) throws Exception;
	  public boolean addItem(String item);
}
