package PIM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;

import calendar.Cal;

public class CalGUI {
	public static void main(String []args) throws Exception {
		new MyFrame();
	}
}

class MyFrame extends JFrame implements RemotePIMCollection {
    Calendar cal = Calendar.getInstance();
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public PIMCollection itemArr = new PIMCollection();
    public String owner;
    public String sf;
    PIMManager pm = new PIMManager();
    private Container content;
    private JLabel monthy;
    private monthJPanel monthJ;

    public MyFrame() throws Exception {
        owner = JOptionPane.showInputDialog(null, "Please input your account name");
        if (owner == null) {
        }else if (owner.equals("")){
        	JOptionPane.showMessageDialog(null, "Please input right account name!","Error Account!",JOptionPane.ERROR_MESSAGE);
        }else {
        	cal.set(Calendar.DAY_OF_MONTH, 1);
            content = this.getContentPane();
            content.setLayout(new GridBagLayout());
            PIMManager.load();
			addMenuBar();
			addAccount();
			addButton();
			monthy = addMY();
	        addWeekTitle();
	        monthJ = addMonth();
	        setTitle("PIMCalendar");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(360, 130, 1100, 750);
	        setVisible(true);
        }
    }

    @Override
	public PIMCollection getNotes() throws Exception {
    	itemArr.clear();
		PIMCollection pc = getAll();
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMNote ){
				itemArr.add((PIMNote)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getNotes(String owner) throws Exception {
		itemArr.clear();
		PIMCollection pc = getAllByOwner(owner);
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMNote ){
				itemArr.add((PIMNote)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getTodos() throws Exception {
		itemArr.clear();
		PIMCollection pc = getAll();
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMTodo ){
				itemArr.add((PIMTodo)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getTodos(String owner) throws Exception {
		itemArr.clear();
		PIMCollection pc = getAllByOwner(owner);
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMTodo ){
				itemArr.add((PIMTodo)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getAppointments() throws Exception {
		itemArr.clear();
		PIMCollection pc = getAll();
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMAppointment ){
				itemArr.add((PIMAppointment)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getAppointments(String owner) throws Exception {
		itemArr.clear();
		PIMCollection pc = getAllByOwner(owner);
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMAppointment ){
				itemArr.add((PIMAppointment)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getContacts() throws Exception {
		itemArr.clear();
		PIMCollection pc = getAll();
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMContact){
				itemArr.add((PIMContact)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getContacts(String owner) throws Exception {
		itemArr.clear();
		PIMCollection pc = getAllByOwner(owner);
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMContact){
				itemArr.add((PIMContact)obj);
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getItemsForDate(Date d) throws Exception {
		itemArr.clear();
		PIMCollection pc = getAll();
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMTodo){
				PIMTodo todo = (PIMTodo)obj;
				if (d.equals(todo.getDate())) {
					itemArr.add(todo);
				}
			} else if (obj instanceof PIMAppointment){
				PIMAppointment appointment = (PIMAppointment)obj;
				if (d.equals(appointment.getDate())) {
					itemArr.add(appointment);
				}
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getItemsForDate(Date d, String owner) throws Exception {
		itemArr.clear();
		PIMCollection pc = getAllByOwner(owner);
		for (int i = 0; i < pc.size(); i++) {
			Object obj = pc.get(i);
			if (obj instanceof PIMTodo){
				PIMTodo todo = (PIMTodo)obj;
				if (d.equals(todo.getDate())) {
					itemArr.add(todo);
				}
			} else if (obj instanceof PIMAppointment){
				PIMAppointment appointment = (PIMAppointment)obj;
				if (d.equals(appointment.getDate())) {
					itemArr.add(appointment);
				}
			}
		}
		return itemArr;
	}

	@Override
	public PIMCollection getAll() throws Exception {
		PIMCollection pim = new PIMCollection();
		PIMCollection pc = pm.listItem;
		for (int i = 0; i < pc.size(); i++) {
			if (pc.get(i).owner.equals(owner)) {
				pim.add(pc.get(i));
			}
		}
		return pim;
	}

	@Override
	public PIMCollection getAllByOwner(String owner) throws Exception {
		PIMCollection pim = new PIMCollection();
		PIMCollection pc = pm.listItem;
		for (int i = 0; i < pc.size(); i++) {
			if (pc.get(i).owner.equals(owner) && pc.get(i).sharedFlag.equals("public")) {
				pim.add(pc.get(i));
			}
		}
		return pim;
	}
    
    @Override
	public boolean addItem(String item) {
    	ArrayList<String> list=new ArrayList<String>();
    	int index = item.indexOf(':');
    	String sItem = item.substring(index + 1);
    	String[] itemArr = sItem.split(" ",2);
    	switch (itemArr[0]) {
		case "TODO":
			PIMManager.listItem.add(new PIMTodo());
			String[] todoArr = itemArr[1].split(" ",5);
			list.add(todoArr[0]);
			list.add(todoArr[1]);
            list.add(todoArr[3]);
            list.add(todoArr[4]);
            list.add(todoArr[2]);
            PIMManager.itemId++;
            PIMManager.listItem.get(PIMManager.listItem.size()-1).fromString(list);
			break;
		case"NOTE":
            PIMManager.listItem.add(new PIMNote());
            String[] noteArr = itemArr[1].split(" ",4);
            list.add(noteArr[0]);
            list.add(noteArr[1]);
            list.add(noteArr[3]);
            list.add(noteArr[2]);
            PIMManager.itemId++;
            PIMManager.listItem.get(PIMManager.listItem.size()-1).fromString(list);
            break;
        case"APPOINTMENT":
            PIMManager.listItem.add(new PIMAppointment());
            String[] appointmentArr = itemArr[1].split(" ",5);
            list.add(appointmentArr[0]);
            list.add(appointmentArr[1]);
            list.add(appointmentArr[3]);
            list.add(appointmentArr[4]);
            list.add(appointmentArr[2]);
            PIMManager.itemId++;
            PIMManager.listItem.get(PIMManager.listItem.size()-1).fromString(list);
            break;
        case"CONTACT":
        	PIMManager.listItem.add(new PIMContact());
            String[] contactArr = itemArr[1].split(" ",6);
            list.add(contactArr[0]);
            list.add(contactArr[1]);
            list.add(contactArr[3]);
            list.add(contactArr[4]);
            list.add(contactArr[5]);
            list.add(contactArr[2]);
            PIMManager.itemId++;
            PIMManager.listItem.get(PIMManager.listItem.size()-1).fromString(list);
            break;
		default:
			return false;
		}
    	return true;
	}

	void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveAction = new JMenuItem("Save");
        JMenuItem loadAction = new JMenuItem("Load");
        fileMenu.add(saveAction);
        fileMenu.add(loadAction);

        saveAction.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                PIMManager.save();
            }
        });

        loadAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                PIMManager.load();
                try {
 					monthJ.refresh();
 					monthJ.updateUI();
 					monthJ.repaint();
 				} catch (Exception exception ) {
 					// TODO Auto-generated catch block
 					exception.printStackTrace();
 				}
            }
        });
        
        JMenu create = new JMenu("Create");
        JMenuItem ctodo = new JMenuItem("Todo");
        JMenuItem cnote = new JMenuItem("Note");
        JMenuItem cappointment = new JMenuItem("Appointment");
        JMenuItem ccontact = new JMenuItem("Contact");
        create.add(ctodo);
        create.add(cnote);
        create.add(cappointment);
        create.add(ccontact);
        ArrayList<String> list=new ArrayList<String>();
        ctodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	pm.listItem.add(new PIMTodo());
                list.clear();
                list.add(owner);
                sf = menuDialog("Choose shared flag: public or private");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter date for todo item(dd/MM/yyyy)");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter todo text");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter todo priority");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
                try {
 					monthJ.refresh();
 					monthJ.updateUI();
 					monthJ.repaint();
 				} catch (Exception exception ) {
 					// TODO Auto-generated catch block
 					exception.printStackTrace();
 				}
                JOptionPane.showMessageDialog(null, "Create the item successfully","Congratulation",JOptionPane.INFORMATION_MESSAGE);
                //PIMManager.save();
            }
        });
        cnote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	PIMManager.listItem.add(new PIMNote());
                list.clear();
                list.add(owner);
                sf = menuDialog("Choose shared flag: public or private");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter note text");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter note priority");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
                try {
 					monthJ.refresh();
 					monthJ.updateUI();
 					monthJ.repaint();
 				} catch (Exception exception ) {
 					// TODO Auto-generated catch block
 					exception.printStackTrace();
 				}
                JOptionPane.showMessageDialog(null, "Create the item successfully","Congratulation",JOptionPane.INFORMATION_MESSAGE);
                //PIMManager.save();
            }
        });
        cappointment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	pm.listItem.add(new PIMAppointment());
                list.clear();
                list.add(owner);
                sf = menuDialog("Choose shared flag: public or private");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter date for appointment item(dd/MM/yyyy)");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter appointment description");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter appointment priority");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
                try {
 					monthJ.refresh();
 					monthJ.updateUI();
 					monthJ.repaint();
 				} catch (Exception exception ) {
 					// TODO Auto-generated catch block
 					exception.printStackTrace();
 				}
                JOptionPane.showMessageDialog(null, "Create the item successfully","Congratulation",JOptionPane.INFORMATION_MESSAGE);
                //PIMManager.save();
            }
        });
        ccontact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	pm.listItem.add(new PIMContact());
                list.clear();
                list.add(owner);
                sf = menuDialog("Choose shared flag: public or private");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter firstname for contact item");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter lastname for contact item");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter email for contact item");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                sf = menuDialog("Enter contact priority");
                if (sf == null) {
                	return;
                }
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
                try {
 					monthJ.refresh();
 					monthJ.updateUI();
 					monthJ.repaint();
 				} catch (Exception exception ) {
 					// TODO Auto-generated catch block
 					exception.printStackTrace();
 				}
                JOptionPane.showMessageDialog(null, "Create the item successfully","Congratulation",JOptionPane.INFORMATION_MESSAGE);
                //PIMManager.save();
            }
        });

        JMenu viewMenu = new JMenu("Query");
        JMenu view = new JMenu("List");
        JMenuItem viewM = new JMenuItem("Me");
        JMenuItem viewO = new JMenuItem("Other");
        JMenu todo = new JMenu("Todo");
        JMenuItem todoM = new JMenuItem("Me");
        JMenuItem todoO = new JMenuItem("Other");
        JMenu note = new JMenu("Note");
        JMenuItem noteM = new JMenuItem("Me");
        JMenuItem noteO = new JMenuItem("Other");
        JMenu appointment = new JMenu("Appointment");
        JMenuItem appointmentM = new JMenuItem("Me");
        JMenuItem appointmentO = new JMenuItem("Other");
        JMenu contact = new JMenu("Contact");
        JMenuItem contactM = new JMenuItem("Me");
        JMenuItem contactO = new JMenuItem("Other");
        JMenu date = new JMenu("Date");
        JMenuItem dateM = new JMenuItem("Me");
        JMenuItem dateO = new JMenuItem("Other");
        /*
        JMenu priority = new JMenu("Priority");
        JMenuItem urgent = new JMenuItem("urgent");
        JMenuItem important = new JMenuItem("important");
        JMenuItem ordinary = new JMenuItem("ordinary");
        JMenuItem negligible = new JMenuItem("negligible");
        */
        view.add(viewM);
        view.add(viewO);
        todo.add(todoM);
        todo.add(todoO);
        note.add(noteM);
        note.add(noteO);
        appointment.add(appointmentM);
        appointment.add(appointmentO);
        contact.add(contactM);
        contact.add(contactO);
        date.add(dateM);
        date.add(dateO);
        /*
        priority.add(urgent);
        priority.add(important);
        priority.add(ordinary);
        priority.add(negligible);
        */
        viewMenu.add(todo);
        viewMenu.add(note);
        viewMenu.add(appointment);
        viewMenu.add(contact);
        viewMenu.add(date);
        //viewMenu.add(priority);

        viewM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getAll(),"My all items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        viewO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		if (sf == null) {
                    	return;
                    }
            		new viewWindow(getAllByOwner(sf),sf + "'s all items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        todoM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getTodos(),"My todo items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        todoO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		if (sf == null) {
                    	return;
                    }
            		new viewWindow(getTodos(sf),sf + "'s todo items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        noteM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getNotes(),"My note items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        noteO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		if (sf == null) {
                    	return;
                    }
            		new viewWindow(getNotes(sf),sf + "'s note items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        appointmentM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getAppointments(),"My appointment items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        appointmentO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		if (sf == null) {
                    	return;
                    }
            		new viewWindow(getAppointments(sf),sf + "'s appointment items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        contactM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getContacts(),"My contact items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        contactO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		if (sf == null) {
                    	return;
                    }
            		new viewWindow(getContacts(sf),sf + "'s contact items");
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        dateM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input date(dd/MM/yyyy)");
            		if (sf == null) {
                    	return;
                    }
            		Date d = df.parse(sf);
            		new viewWindow(getItemsForDate(d),"My items on " + sf);
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        dateO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		if (sf == null) {
                    	return;
                    }
            		Date d = df.parse(JOptionPane.showInputDialog(null, "Please input date(dd/MM/yyyy)"));
            		new viewWindow(getItemsForDate(d,sf),sf + "'s items on " + df.format(d));
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        menuBar.add(fileMenu);
        menuBar.add(create);
        menuBar.add(viewMenu);
        menuBar.add(view);
        setJMenuBar(menuBar);
    }
    
	public String menuDialog(String text) {
		sf = JOptionPane.showInputDialog(null, text);
        while (sf == null || sf.equals("")) {
        	int cf = JOptionPane.showConfirmDialog(null, "Are you sure to cancel it?","Warning!",JOptionPane.YES_NO_OPTION);
        	if (cf == 0) { //yes:0 no: 1
        		return null;
        	}
        	sf = JOptionPane.showInputDialog(null, text);
        }
        return sf;
	}
	
    public void addButton() {
    	JButton last = new JButton("Last Month");
        JButton next = new JButton("Next Month");
        JButton goTo = new JButton("Go To");
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.WEST;
        gc.gridx = 1;
        gc.gridy = 0;
        this.add(last, gc);

        gc.gridx = 2;
        this.add(next, gc);
        
        gc.gridx = 3;
        this.add(goTo, gc);
        
        JTextField textMY = new JTextField("Year Month",8);
        gc.gridx = 4;
        this.add(textMY, gc);
        
        last.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) - 1);
				monthy.setText("   " + Cal.MONTHS[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
				try {
 					monthJ.refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) + 1);
				monthy.setText("   " + Cal.MONTHS[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
				try {
 					monthJ.refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        goTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sf = textMY.getText();
				String[] d = sf.split("[\\s/]");
				try {
					int year = Integer.parseInt(d[0]);
					int month = Integer.parseInt(d[1]);
					cal.set(Calendar.MONTH,month - 1);
					cal.set(Calendar.YEAR,year);
					monthy.setText("   " + Cal.MONTHS[month - 1] + " " + cal.get(Calendar.YEAR));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Date input error!","Warning",JOptionPane.WARNING_MESSAGE);
				}
				try {
 					monthJ.refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    public void addAccount() {
    	JLabel account = new JLabel("  Account: " + owner + "              ");
    	account.setFont(new Font("",1,40));
    	account.setForeground(Color.BLUE);
    	GridBagConstraints gc = new GridBagConstraints();
    	gc.fill = GridBagConstraints.WEST;
        gc.gridx = 0;
        gc.gridy = 0;
    	this.add(account,gc);
    }
    
    public JLabel addMY() {
		JLabel dateJ = new JLabel("   " + Cal.MONTHS[cal.get(Calendar.MONTH)] + " " +  cal.get(Calendar.YEAR),JLabel.CENTER);
    	dateJ.setFont(new Font("", 2, 30));
    	dateJ.setForeground(Color.orange);
    	GridBagConstraints gc = new GridBagConstraints();
    	gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 5;
        gc.gridy = 0;
        this.add(dateJ,gc);
        return dateJ;
	}
    
    public void addWeekTitle() {
    	JPanel jpWeek = new JPanel(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
        c.weightx=10;
        c.weighty=0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1,1,1,0);
        c.anchor = GridBagConstraints.PAGE_END;
        for (int i=0; i<7; i++) {
            c.gridx=i;
            c.gridy=0;
            JLabel l = new JLabel(Cal.WEEKS[i],JLabel.CENTER);
            l.setBackground(Color.white);
            l.setFont(new Font("",1,30));
            //l.set
            //l.setAlignment(SwingConstants.CENTER);
            l.setBorder(new LineBorder(Color.BLUE));
            jpWeek.add(l, c);
        }
        jpWeek.setBackground(new Color(197, 228, 251));//red green blue 255 light blue
        GridBagConstraints gc = new GridBagConstraints();
    	gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridwidth = 7;
        gc.gridy = 1;
        this.add(jpWeek,gc);
    }
    
    class monthJPanel extends JPanel {
    	public monthJPanel(Calendar cal, PIMCollection collection) throws Exception {
            super(new GridLayout(6,7,1,1));
            refresh();
    	}
    	void refresh() throws Exception {
    		this.removeAll();
            int[][] monthDay;
			try {
				monthDay = Cal.printCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
				PIMCollection monthItem = new PIMCollection();
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 7; j++) {
						JLabel jLabel = new JLabel("",JLabel.CENTER);
						jLabel.setBorder(new LineBorder(Color.YELLOW));
						jLabel.setFont(new Font("",5,16));
						if (monthDay[i][j] != 0) {
							String text = "";
							monthItem.clear();
							text +="<html>" + monthDay[i][j] + "<br>";
							cal.set(Calendar.DAY_OF_MONTH,monthDay[i][j]);
							Date d = df.parse(df.format(cal.getTime()));
							monthItem = getItemsForDate(d);
							PIMCollection dayItem = new PIMCollection();
							for (int k = 0; k < monthItem.size(); k++) {
								Object obj = monthItem.get(k);
								if(obj instanceof PIMTodo) {
									PIMTodo todo = (PIMTodo)obj;
									text += todo.todoText + "<br>";
									dayItem.add(todo);
								}
								else if(obj instanceof PIMAppointment) {
									PIMAppointment appoint = (PIMAppointment)obj;
									text += appoint.description + "<br>";
									dayItem.add(appoint);
								}
							}
							text += "</html>";
							//System.out.println(text);
							jLabel.setText(text);
							jLabel.addMouseListener(new MouseAdapter(){ //MouseListener is not interface
								
								@Override
								public void mouseExited(MouseEvent e) {
									jLabel.setBorder(new LineBorder(Color.YELLOW));
									jLabel.setFont(new Font("",5,16));
								}
								
								@Override
								public void mouseEntered(MouseEvent e) {
									// TODO Auto-generated method stub
									jLabel.setFont(new Font("",5,18));
									jLabel.setBorder(new LineBorder(Color.GREEN));
								}
								
								@Override
								public void mouseClicked(MouseEvent e) {
									if (e.getClickCount() == 2) {
										new viewWindow(dayItem,"My items on " + df.format(d));
									}
								}
							});
						}
						this.add(jLabel);
						cal.set(Calendar.DAY_OF_MONTH,1);
					}
				}
			} catch (ParseException e) {
			}      
    	}
    }
    
    public monthJPanel addMonth() throws Exception{
    	GridBagConstraints gc = new GridBagConstraints();
    	gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        //gConstraints.gridwidth = 40;
        gc.gridy = 2;
        gc.weighty = 700;
        gc.weightx = 700;
        gc.gridwidth = 7;
        monthJPanel mj = new monthJPanel(cal,getAll());
        mj.setBackground(Color.white);
        this.add(mj,gc);
        return mj;
    }
    
    class viewWindow extends JFrame {
        viewWindow(PIMCollection collection,String title) {
    		if (collection.size() > 0) {
    			JList<PIMEntity> list = new JList<PIMEntity>(collection.toArray(new PIMEntity[0]));
    	        JScrollPane scrollPane = new JScrollPane(list);
    	        this.setTitle(title);
    	        this.add(scrollPane);
    	        this.setBounds(720, 250, 500, 300);
    	        this.setVisible(true);
    	        viewWindow vw = this;
    	        MouseListener mouseListener = new MouseAdapter() {
    	            public void mouseClicked(MouseEvent e) {
    	            	PIMEntity beforeModify = list.getSelectedValue();
    	            	if (beforeModify == null) return; 
    	                if (e.getClickCount() == 2) {
    	                	if (!beforeModify.owner.equals(owner)) { //Only the account owner can modify item!
    	                		JOptionPane.showMessageDialog(null, "Only the account owner: "+ beforeModify.owner +" can modify this item!","Error!",JOptionPane.ERROR_MESSAGE);
    	                		return; 
    	                	}
    	                    int cf = JOptionPane.showConfirmDialog(null, "Are you sure to modify it?","Waring!",JOptionPane.WARNING_MESSAGE);
    	                	if (cf == 0) { //yes:0 no: 1
    	                		//PIMManager.listItem.remove(list.getSelectedValue());
    	                		
    	                		sf = JOptionPane.showInputDialog(null, "Input modification by the right format: ");
    	                		while (sf == null || sf.equals("")) {
    	                        	int cf2 = JOptionPane.showConfirmDialog(null, "Are you sure to cancel it?","Warning!",JOptionPane.YES_NO_OPTION);
    	                        	if (cf2 == 0) { //yes:0 no: 1
    	                        		return;
    	                        	}
    	                        	sf = JOptionPane.showInputDialog(null, "Input modification by the right format: ");
    	                        }
    	                		/*
    	                		String[] modiStr = sf.split(":",2);
    	                		Object[] possibleValues = { "First", "Second", "Third" };
    	                		Object selectedValue = JOptionPane.showInputDialog(null, 
    	                		"Choose one", "Input",
    	                		JOptionPane.INFORMATION_MESSAGE, null,
    	                		possibleValues, possibleValues[0]);*/
    	                		PIMManager.listItem.remove(beforeModify);
    	                		addItem(sf);
    	                		JOptionPane.showMessageDialog(null, "You modified it successfully!","Notice",JOptionPane.INFORMATION_MESSAGE);
    	                		try {
    	                			vw.removeAll();
    	         					monthJ.refresh();
    	         					monthJ.updateUI();
    	         					monthJ.repaint();
    	         				} catch (Exception exception ) {
    	         					// TODO Auto-generated catch block
    	         					exception.printStackTrace();
    	         				}
    	                		return;
    	                	}
    	                }
    	                else if (e.isMetaDown()) {//Right Click
    	                	if (!beforeModify.owner.equals(owner)) { //Only the account owner can modify item!
    	                		JOptionPane.showMessageDialog(null, "Only the account owner: "+ beforeModify.owner +" can delete this item!","Error!",JOptionPane.ERROR_MESSAGE);
    	                		return; 
    	                	}
    	                    int cf = JOptionPane.showConfirmDialog(null, "Are you sure to delete it?","Warning!",JOptionPane.WARNING_MESSAGE);
    	                	if (cf == 0) { //yes:0 no: 1
    	                		PIMManager.listItem.remove(beforeModify);
    	                		try {
    	                			//vw.refresh(collection);
    	                			vw.removeAll();
    	         					monthJ.refresh();
    	         					monthJ.updateUI();
    	         					monthJ.repaint();
    	         				} catch (Exception exception ) {
    	         					// TODO Auto-generated catch block
    	         					exception.printStackTrace();
    	         				}
    	                		JOptionPane.showMessageDialog(null, "You deleted it successfully!","Notice",JOptionPane.INFORMATION_MESSAGE);
    	                		return;
    	                	}
    	                }
    	            }
    	        };
    	        list.addMouseListener(mouseListener);
    		}
    		else {
    			JOptionPane.showMessageDialog(null, "There is no item!","Notice",JOptionPane.INFORMATION_MESSAGE);
    		}
        }
    } 
}
