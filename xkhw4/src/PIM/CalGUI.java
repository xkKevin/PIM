package PIM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		MyFrame mf = new MyFrame();
		
	}
}

class MyFrame extends JFrame implements RemotePIMCollection {
    Calendar cal = Calendar.getInstance();
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private GridBagConstraints gConstraints = new GridBagConstraints();
    public PIMCollection itemArr = new PIMCollection();
    public String owner;
    public String sf;
    PIMManager pm = new PIMManager();
    private Container content;
    private JLabel monthy;
    private monthJPanel monthJ;
    //private calPanel month;
    //private myText text;

    public MyFrame() throws Exception {
    	cal.set(Calendar.DAY_OF_MONTH, 1);
        content = this.getContentPane();
        content.setLayout(new GridBagLayout());
        PIMManager.load();
        owner = JOptionPane.showInputDialog(null, "Please input your account name");
		addMenuBar();
		addButton();
		addAccount();
		monthy = addMY();
        addWeekTitle();
        addMonth();
        setTitle("PIMCalendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(360, 130, 1100, 750);
        setVisible(true);
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
	public boolean add(PIMEntity item) throws Exception {
		// TODO Auto-generated method stub
		return false;
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
            }
        });
        
        JMenu create = new JMenu("Create");
        JMenuItem ctodo = new JMenuItem("Todos");
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
                sf = JOptionPane.showInputDialog(null, "Choose shared flag: public(1) and private(0)");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter date for todo item(dd/MM/yyyy)");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter todo text");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter todo priority");
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
            }
        });
        cnote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	pm.listItem.add(new PIMNote());
                list.clear();
                list.add(owner);
                sf = JOptionPane.showInputDialog(null, "Choose shared flag: public(1) and private(0)");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter note text");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter note priority");
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
            }
        });
        cappointment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	pm.listItem.add(new PIMAppointment());
                list.clear();
                list.add(owner);
                sf = JOptionPane.showInputDialog(null, "Choose shared flag: public(1) and private(0)");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter date for appointment item(dd/MM/yyyy)");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter appointment description");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter appointment priority");
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
            }
        });
        ccontact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	pm.listItem.add(new PIMContact());
                list.clear();
                list.add(owner);
                sf = JOptionPane.showInputDialog(null, "Choose shared flag: public(1) and private(0)");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter firstname for contact item");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter lastname for contact item");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter email for contact item");
                list.add(sf);
                sf = JOptionPane.showInputDialog(null, "Enter contact priority");
                list.add(sf);
                pm.itemId++;
                pm.listItem.get(pm.listItem.size()-1).fromString(list);
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
            		new viewWindow(getAll());
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        viewO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		new viewWindow(getAllByOwner(sf));
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        todoM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getTodos());
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        todoO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		new viewWindow(getTodos(sf));
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        noteM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getNotes());
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        noteO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		new viewWindow(getNotes(sf));
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        appointmentM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getAppointments());
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        appointmentO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		new viewWindow(getAppointments(sf));
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        contactM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		new viewWindow(getContacts());
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        contactO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		new viewWindow(getContacts(sf));
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        dateM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input date");
            		Date d = df.parse(sf);
            		new viewWindow(getItemsForDate(d));
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        dateO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		sf = JOptionPane.showInputDialog(null, "Please input account name");
            		Date d = df.parse(JOptionPane.showInputDialog(null, "Please input date"));
            		new viewWindow(getItemsForDate(d,sf));
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
    
    public void addButton() {
    	JButton last = new JButton("Last Month");
        JButton next = new JButton("Next Month");
        gConstraints.fill = GridBagConstraints.NONE;
        gConstraints.gridx = 1;
        gConstraints.gridy = 0;
        this.add(last, gConstraints);

        gConstraints.fill = GridBagConstraints.NONE;
        gConstraints.gridx = 2;
        gConstraints.gridy = 0;
        this.add(next, gConstraints);
        
        last.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) - 1);
				monthy.setText(Cal.MONTHS[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
				try {
					monthJ.refresh(cal, getAll());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) + 1);
				monthy.setText(Cal.MONTHS[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
				try {
					monthJ.refresh(cal, getAll());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
    
    public void addAccount() {
    	JLabel account = new JLabel("  Account: " + owner + "              ");
    	account.setFont(new Font("",1,40));
    	account.setForeground(Color.BLUE);
    	gConstraints.fill = GridBagConstraints.WEST;
        gConstraints.gridx = 0;
        gConstraints.gridy = 0;
    	this.add(account,gConstraints);
    }
    
    public JLabel addMY() {
		JLabel dateJ = new JLabel(Cal.MONTHS[cal.get(Calendar.MONTH)] + " " +  cal.get(Calendar.YEAR),JLabel.CENTER);
    	dateJ.setFont(new Font("", 2, 30));
    	dateJ.setForeground(Color.orange);
    	gConstraints.fill = GridBagConstraints.EAST;
        gConstraints.gridx = 3;
        gConstraints.gridy = 0;
        this.add(dateJ,gConstraints);
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
    	gConstraints.fill = GridBagConstraints.HORIZONTAL;
        gConstraints.gridx = 0;
        gConstraints.gridwidth = 4;
        gConstraints.gridy = 1;
        this.add(jpWeek,gConstraints);
    }
    
    class monthJPanel extends JPanel {
    	public monthJPanel(Calendar cal, PIMCollection collection) {
            super(new GridLayout(6,7,1,1));
            refresh(cal, collection);
    	}
    	void refresh(Calendar cal, PIMCollection collection) {
    		this.removeAll();
            int[][] monthDay;
			try {
				monthDay = Cal.printCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 7; j++) {
						JLabel jLabel;
						if (monthDay[i][j] == 0) {
							jLabel = new JLabel("");
						}
						else {
							jLabel = new JLabel("" + monthDay[i][j],JLabel.CENTER);
							
						}
						jLabel.setBorder(new LineBorder(Color.YELLOW));
						jLabel.setBackground(Color.white);
						this.add(jLabel);
					}
				}
			} catch (ParseException e) {
			}      
    	}
    }
    
    public void addMonth() throws Exception{
    	
    	gConstraints.fill = GridBagConstraints.BOTH;
        gConstraints.gridx = 0;
        //gConstraints.gridwidth = 40;
        gConstraints.gridy = 2;
        gConstraints.weighty = 700;
        gConstraints.weightx = 700;
        monthJ = new monthJPanel(cal,getAll());
        this.add(monthJ,gConstraints);
    }
}

class viewWindow extends JFrame {
    	viewWindow(PIMCollection collection) {
        JList<PIMEntity> list = new JList<PIMEntity>(collection.toArray(new PIMEntity[0]));
        JScrollPane scrollPane = new JScrollPane(list);

        this.add(scrollPane);
        this.setBounds(720, 250, 500, 300);
        this.setVisible(true);
    }
} 
