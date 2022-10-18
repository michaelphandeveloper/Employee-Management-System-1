import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;

import java.sql.*;
import javax.swing.*;


public class Employee {

	private JFrame frame;
	private JTextField jtxtEmployeeID;
	private JTable table;
	private JButton btnPrint;
	private JButton btnReset;
	private JButton btnExit;
	private JTextField jtxtNINumber;
	private JLabel lblNiNumber;
	private JTextField jtxtFirstname;
	private JLabel lblNewLabel_2;
	private JTextField jtxtSurname;
	private JLabel lblNewLabel_3;
	private JTextField jtxtSalary;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JTextField jtxtAge;
	private JTextField jtxtDOB;
	private JLabel lblNewLabel_6;
	private JLabel lblGender;
	private JTextField jtxtGender;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	DefaultTableModel model = new DefaultTableModel();
	
	/**
	 * Launch the application.
	 */
	
	public void updateTable() {
		conn = EmployeeData.ConnectDB();
		
		if (conn !=null) {
			String sql = "Select EmpID,NINumber,Firstname,Surname,Gender,DOB,Age,Salary";
		
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			Object[] columnData = new Object[8];
			
			while (rs.next()) {
				columnData [0] = rs.getString("EmpID");
				columnData [0] = rs.getString("NINumber");
				columnData [0] = rs.getString("Firstname");
				columnData [0] = rs.getString("Surname");
				columnData [0] = rs.getString("Gender");
				columnData [0] = rs.getString("DOB");
				columnData [0] = rs.getString("Age");
				columnData [0] = rs.getString("Salary");
				
				model.addRow(columnData);
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Employee() {
		initialize();
		
		conn = EmployeeData.ConnectDB();
		Object col[] = {"EmpID", "NINumber", "Firstname", "Surname", "Gender", "DOB", "Age", "Salary"};
		model.setColumnIdentifiers(col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0,0, 1450, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(23, 59, 145, 47);
		frame.getContentPane().add(lblNewLabel);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtEmployeeID.setBounds(176, 59, 294, 47);
		frame.getContentPane().add(jtxtEmployeeID);
		jtxtEmployeeID.setColumns(10);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			String sql = "INSERT INTO employee(EmpID, NINumber, Firstname, Surname, Gender, DOB, Age, Salary)VALUE(?,?,?,?,?,?,?,?)";
			
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, jtxtEmployeeID.getText());
				pst.setString(2, jtxtNINumber.getText());
				pst.setString(3, jtxtFirstname.getText());
				pst.setString(4, jtxtSurname.getText());
				pst.setString(5, jtxtGender.getText());
				pst.setString(6, jtxtDOB.getText());
				pst.setString(7, jtxtAge.getText());
				pst.setString(8, jtxtSalary.getText());
				
				pst.execute();
				
				rs.close();
				pst.close();
			}
			catch (Exception ev) {
					JOptionPane.showMessageDialog(null, "System Update Completed");
				}
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model .addRow(new Object[] {
					jtxtEmployeeID.getText(),
					jtxtNINumber.getText(),
					jtxtFirstname.getText(),
					jtxtSurname.getText(),
					jtxtGender.getText(),
					jtxtDOB.getText(),
					jtxtAge.getText(),
					jtxtSalary.getText(),
			});
			
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Membership Update Comfirmed", "Employee Database System",
							JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnAddNew.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAddNew.setBounds(23, 597, 265, 53);
		frame.getContentPane().add(btnAddNew);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(535, 60, 861, 475);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmpID", "NINumber", "Firstname", "Surname", "Gender", "DOB", "Age", "Salary"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MessageFormat header = new MessageFormat("Printing in Progress");
				MessageFormat footer = new MessageFormat("Page {0, number, integer}");
				
				try {
					table.print();
				}
				catch(java.awt.print.PrinterException ev) {
					System.err.format("No Printer found", ev.getMessage());
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnPrint.setBounds(411, 597, 265, 53);
		frame.getContentPane().add(btnPrint);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtxtEmployeeID.setText(null);
				jtxtNINumber.setText(null);
				jtxtFirstname.setText(null);
				jtxtSurname.setText(null);
				jtxtGender.setText(null);
				jtxtDOB.setText(null);
				jtxtAge.setText(null);
				jtxtSalary.setText(null);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(785, 597, 265, 53);
		frame.getContentPane().add(btnReset);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frame =new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Employee Database System",
						JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBounds(1131, 597, 265, 53);
		frame.getContentPane().add(btnExit);
		
		jtxtNINumber = new JTextField();
		jtxtNINumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtNINumber.setColumns(10);
		jtxtNINumber.setBounds(176, 114, 294, 51);
		frame.getContentPane().add(jtxtNINumber);
		
		lblNiNumber = new JLabel("NI Number");
		lblNiNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNiNumber.setBounds(23, 114, 145, 51);
		frame.getContentPane().add(lblNiNumber);
		
		jtxtFirstname = new JTextField();
		jtxtFirstname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtFirstname.setColumns(10);
		jtxtFirstname.setBounds(176, 173, 294, 54);
		frame.getContentPane().add(jtxtFirstname);
		
		lblNewLabel_2 = new JLabel("Firstname");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(23, 173, 145, 54);
		frame.getContentPane().add(lblNewLabel_2);
		
		jtxtSurname = new JTextField();
		jtxtSurname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSurname.setColumns(10);
		jtxtSurname.setBounds(176, 235, 294, 47);
		frame.getContentPane().add(jtxtSurname);
		
		lblNewLabel_3 = new JLabel("Surname");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3.setBounds(23, 235, 145, 60);
		frame.getContentPane().add(lblNewLabel_3);
		
		jtxtSalary = new JTextField();
		jtxtSalary.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSalary.setColumns(10);
		jtxtSalary.setBounds(176, 475, 294, 60);
		frame.getContentPane().add(jtxtSalary);
		
		lblNewLabel_4 = new JLabel("Salary");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4.setBounds(23, 475, 145, 60);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Age");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_5.setBounds(23, 413, 145, 54);
		frame.getContentPane().add(lblNewLabel_5);
		
		jtxtAge = new JTextField();
		jtxtAge.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtAge.setColumns(10);
		jtxtAge.setBounds(176, 413, 294, 54);
		frame.getContentPane().add(jtxtAge);
		
		jtxtDOB = new JTextField();
		jtxtDOB.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtDOB.setColumns(10);
		jtxtDOB.setBounds(176, 351, 294, 54);
		frame.getContentPane().add(jtxtDOB);
		
		lblNewLabel_6 = new JLabel("DOB");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_6.setBounds(23, 353, 145, 51);
		frame.getContentPane().add(lblNewLabel_6);
		
		lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGender.setBounds(23, 287, 145, 58);
		frame.getContentPane().add(lblGender);
		
		jtxtGender = new JTextField();
		jtxtGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtGender.setColumns(10);
		jtxtGender.setBounds(176, 290, 294, 53);
		frame.getContentPane().add(jtxtGender);
		
		JLabel lblNewLabel_1 = new JLabel("Employee Database Management System");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(406, 8, 644, 28);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
