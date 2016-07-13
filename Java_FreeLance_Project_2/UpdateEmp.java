import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;

public class UpdateEmp extends JFrame implements ActionListener {
    int count = 0;
    Record [] emp;

    JFrame f;
    JPanel p1, p2, p3, p4;
    JButton b1,b2, b3;
    JLabel lab1,lab2,lab3,lab4,lab5;
    JTextField t1, t2,t3, t4;
    JTextArea ta1;
    File empFile = new File("Emp.txt");
    //PrintWriter fileOutput;
    BufferedReader br;

    public UpdateEmp(){
        super("Applet Viewer:UpdateEmp.class");

        try {
            br = new BufferedReader(new FileReader(empFile));
            //fileOutput = new PrintWriter(new PrintStream(empFile));
        } catch (Exception e) {
            System.err.println("COULDN'T OPEN FILE");
            System.exit(0);
        }
        emp = new Record[100];

        setLayout(new BorderLayout());

        Container c= getContentPane();
        c.setLayout(new BorderLayout());

        p1= new JPanel();
        p2= new JPanel();
        p3= new JPanel();
        p4= new JPanel();


        b1 =new JButton();
        b2= new JButton();
        b3= new JButton();

        t1= new JTextField();
        t2= new JTextField();
        t3= new JTextField();
        t4= new JTextField();
        ta1= new JTextArea();

        lab1= new JLabel("Employment Identification");
        lab2= new JLabel("Telephone Number");
        lab3 =new JLabel("Employee Name");
        lab4 =new JLabel("Years of Work");
        lab5=new JLabel("Update Program");

        t1= new JTextField("##");
        t1.setSize(200,60);
        t1.setEditable(false);
        t2= new JTextField("PHONE_NUMBER");
        t2.setSize(200, 60);
        t3= new JTextField("LAST_NAME");
        t3.setSize(200,60);
        t4= new JTextField("##");
        t4.setSize(200,60);
        ta1= new JTextArea(500,100);
        ta1.setPreferredSize(new Dimension(640,200));
        //ta1.setLineWrap(true);


        //ButtonListener listen = new ButtonListener();
        b1= new JButton("Get Record");
        b1.setSize(210, 30);
        b1.addActionListener(this);
        b2= new JButton("Update");
        b2.setSize(210, 30);
        b2.addActionListener(this);
        b3= new JButton("Display all Record");
        b3.setSize(210, 30);
        b3.addActionListener(this);

        p1.setLayout(new GridLayout(6, 1));
        p1.add(lab1);
        p1.add(lab2);
        p1.add(lab3);
        p1.add(lab4);
        p1.add(b1);
        p1.add(b3);
        //c.add(p1, new FlowLayout(FlowLayout.LEFT));


        p2.setLayout(new GridLayout(5, 1));
        p2.add(t1);
        p2.add(t2);
        p2.add(t3);
        p2.add(t4);
        p2.add(b2);
        //c.add(p2, new FlowLayout(FlowLayout.RIGHT));

        p3.add(ta1);
        //c.add(p3, new FlowLayout(FlowLayout.TRAILING));

        p4.add(lab5);
        //c.add(p4,new FlowLayout(FlowLayout.LEADING));
        //add(p1,BorderLayout.WEST);
        //add(p2, BorderLayout.EAST);
        Panel northPanel = new Panel(new BorderLayout());
        northPanel.add(p1,BorderLayout.WEST);
        northPanel.add(p2, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);
        add(ta1, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                String line = br.readLine();
                if (line != null) {
                    String[] temp = line.split(" ");
                    t1.setText(temp[0]);
                    t2.setText(temp[1]);
                    t3.setText(temp[2]);
                    t4.setText(temp[3]);
                }
            } catch (Exception b) {

            }
        } else if (e.getSource() == b2) {
            int id = Integer.parseInt(t1.getText());
            int y = Integer.parseInt(t4.getText());
            Record r = new Record(id, t2.getText(), t3.getText(), y);
            emp[count++] = r;
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
        } else if (e.getSource() == b3) {
            Record[] sorted = sort();
            String s = "";
            for (int i = 0; i < count; i++) {
                s += sorted[i].toString() + "\n";
            }
            ta1.setText(s);
        }
    }

    public Record[] sort() {
        Record[] sorted = emp;
        int n = count;
        Record temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n-1); j++) {
                if (sorted[j-1].getName().compareTo(sorted[j].getName()) > 0) {
                    temp = sorted[j-1];
                    sorted[j-1] = sorted[j];
                    sorted[j] = temp;
                }
            }
        }
        return sorted;
    }


    public static void main (String[] args ){
        UpdateEmp appFrame = new UpdateEmp();
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setVisible(true);
        appFrame.setSize(640, 480);

    }

}

