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

public class PIMCollection extends ArrayList<PIMEntity> {
	public Collection getNotes() {
		ArrayList<PIMNote> noteArr = new ArrayList<PIMNote>();
		for (int i = 0; i < this.size(); i++) {
			Object obj = this.get(i);
			if (obj instanceof PIMNote) {
				noteArr.add((PIMNote)obj);
			}
		}
		return noteArr;
	}
	
	public Collection getTodos() {
		ArrayList<PIMTodo> todoArr = new ArrayList<PIMTodo>();
		for (int i = 0; i < this.size(); i++) {
			Object obj = this.get(i);
			if (obj instanceof PIMTodo) {
				todoArr.add((PIMTodo)obj);
			}
		}
		return todoArr;
	}
	
	public Collection getAppointmnets() {
		ArrayList<PIMAppointment> appointmentArr = new ArrayList<PIMAppointment>();
		for (int i = 0; i < this.size(); i++) {
			Object obj = this.get(i);
			if (obj instanceof PIMAppointment) {
				appointmentArr.add((PIMAppointment)obj);
			}
		}
		return appointmentArr;
	}
	
	public Collection getContacts() {
		ArrayList<PIMContact> contactArr = new ArrayList<PIMContact>();
		for (int i = 0; i < this.size(); i++) {
			Object obj = this.get(i);
			if (obj instanceof PIMContact) {
				contactArr.add((PIMContact)obj);
			}
		}
		return contactArr;
	}
	
	public Collection getItemsForDate(Date date) throws Exception{
		ArrayList<PIMEntity> entityArr = new ArrayList<PIMEntity>();
		for (int i = 0; i < this.size(); i++) {
			Object obj = this.get(i);
			if (obj instanceof PIMTodo){
				PIMTodo todo = (PIMTodo)this.get(i);
				if (date.equals(todo.getDate())) {
					entityArr.add(todo);
				}
			} else if (obj instanceof PIMAppointment){
				PIMAppointment appointment = (PIMAppointment)this.get(i);
				if (date.equals(appointment.getDate())) {
					entityArr.add(appointment);
				}
			}
		}
		return entityArr;
    }
	
	public Collection getItemsForPriority(String priority) {
		ArrayList<PIMEntity> entityArr = new ArrayList<PIMEntity>();
		for (int i = 0; i < this.size(); i++) {
			if (priority.equals(this.get(i).getPriority())) {
				entityArr.add(this.get(i));
			}
		}
		return entityArr;
    }
}

