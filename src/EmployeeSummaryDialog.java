/*
 * 
 * This is the summary dialog for displaying all Employee details
 * 
 * */

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

public class EmployeeSummaryDialog extends JDialog implements ActionListener {
	// vector with all Employees details
	Vector<Object> allEmployees;
	JButton back;

	private static Vector<String> header;
	private static String[] headerName = { "ID", "PPS Number", "Surname", "First Name", "Gender", "Department", "Salary", "Full Time" };
	private static int[] colWidth = { 15, 100, 120, 120, 50, 120, 80, 80 };
	
	public EmployeeSummaryDialog(Vector<Object> allEmployees) {
		setTitle("Employee Summary");
		setModal(true);
		this.allEmployees = allEmployees;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane(summaryPane());
		setContentPane(scrollPane);

		setSize(850, 500);
		setLocation(350, 250);
		setVisible(true);

	}
	// initialise container
	public Container summaryPane() {
		JPanel summaryDialog = new JPanel(new MigLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JTable employeeTable = createEmployeeTable();

		buttonPanel.add(back = new JButton("Back"));
		back.addActionListener(this);
		back.setToolTipText("Return to the main screen");

		summaryDialog.add(buttonPanel, "growx, pushx, wrap");
		summaryDialog.add(new JScrollPane(employeeTable), "growx, pushx, wrap");
		new JScrollPane(employeeTable).setBorder(BorderFactory.createTitledBorder("Employee Details"));

		return summaryDialog;
	}

	private JTable createEmployeeTable() {
		DefaultTableModel tableModel = new DefaultTableModel(convertDataToVector(allEmployees), convertToVector(header)) {
			@Override
			public Class<?> getColumnClass(int c) {
				switch (c) {
					case 0:
						return Integer.class;
					case 4:
						return Character.class;
					case 6:
						return Double.class;
					case 7:
						return Boolean.class;
					default:
						return String.class;
				}
			}
		};

		JTable employeeTable = new JTable(tableModel);

		for (int i = 0; i < employeeTable.getColumnCount(); i++) {
			employeeTable.getColumn(headerName[i]).setMinWidth(colWidth[i]);
		}

		employeeTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
														   int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				label.setHorizontalAlignment(JLabel.LEFT);
				return label;
			}
		});

		employeeTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
														   int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			}
		});

		employeeTable.getColumnModel().getColumn(6).setCellRenderer(new DecimalFormatRenderer());

		employeeTable.setEnabled(false);
		employeeTable.setPreferredScrollableViewportSize(new Dimension(800, (15 * employeeTable.getRowCount() + 15)));
		employeeTable.setAutoCreateRowSorter(true);

		return employeeTable;
	}

	private Vector<Object> convertToVector(Vector<String> vector) {
		Vector<Object> result = new Vector<>();
		result.addAll(vector);
		return result;
	}

	private Vector<Vector<Object>> convertDataToVector(Vector<Object> data) {
		Vector<Vector<Object>> result = new Vector<>();
		result.add(data);
		return result;
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back){
			dispose();
		}

	}
	// format for salary column
	static class DecimalFormatRenderer extends DefaultTableCellRenderer {
		 private static final DecimalFormat format = new DecimalFormat(
		 "\u20ac ###,###,##0.00" );

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			 JLabel label = (JLabel) c;
			 label.setHorizontalAlignment(JLabel.RIGHT);
			 // format salary column
			value = format.format((Number) value);

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}// end getTableCellRendererComponent
	}// DefaultTableCellRenderer
}// end class EmployeeSummaryDialog
