/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bus.ViewCashCountSheetList_BUS;
import entity.CashCountSheet;
import entity.Employee;
import utilities.CashCountSheetPrinter;
import utilities.RMIService;
import utilities.SVGIcon;

/**
 *
 * @author Hoàng Khang
 */
public class ViewCashCountSheetList_GUI extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1226974583021042942L;
	private DefaultTableModel tblModel_cashCountSheetList;
	private ViewCashCountSheetList_BUS cashCountSheet_BUS;

	/**
	 * Creates new form ViewCashCountSheetList
	 * 
	 * @throws RemoteException
	 */
	public ViewCashCountSheetList_GUI() throws RemoteException {
		try {
			cashCountSheet_BUS = (ViewCashCountSheetList_BUS) Naming.lookup(RMIService.viewCashCountSheetListBus);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTableModel();
		initComponents();
		alterTable();
		renderCustomerTable(cashCountSheet_BUS.getAll());
	}

	public void alterTable() {
		DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);

////        Align
		tbl_cashCountSheetList.getColumnModel().getColumn(4).setCellRenderer(rightAlign);
		tbl_cashCountSheetList.getColumnModel().getColumn(5).setCellRenderer(rightAlign);

	}

	public void renderCustomerTable(List<CashCountSheet> list) {
		tblModel_cashCountSheetList.setRowCount(0);
		for (CashCountSheet cashCountSheet : list) {
			String id = cashCountSheet.getCashCountSheetID();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Employee e1 = cashCountSheet.getCashCountSheetDetailList().get(0).getEmployee();
			Employee e2 = cashCountSheet.getCashCountSheetDetailList().get(1).getEmployee();

			Date createDate = cashCountSheet.getCreatedDate();
			double total = cashCountSheet.getTotal();
			double difference = cashCountSheet.getDifference();

			Object[] row = new Object[] { id, e1.getEmployeeID(), e2.getEmployeeID(), formatter.format(createDate),
					utilities.FormatNumber.toVND(total), utilities.FormatNumber.toVND(difference) };
//            Object[] row = new Object[]{id, "f", "d", createDate, utilities.FormatNumber.toVND(total), utilities.FormatNumber.toVND(difference)};

			tblModel_cashCountSheetList.addRow(row);
		}
	}

	public void initTableModel() {
		// Products
		tblModel_cashCountSheetList = new DefaultTableModel(
				new String[] { "Mã", "Người kiểm", "Đồng kiểm", "Thời điểm lập", "Tổng", "Chênh lệch" }, 0);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		pnl_header = new javax.swing.JPanel();
		pnl_employee = new javax.swing.JPanel();
		pnl_employee1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		pnl_employee2 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		jTextField2 = new javax.swing.JTextField();
		pnl_date = new javax.swing.JPanel();
		pnl_startDate = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		jDateChooser2 = new com.toedter.calendar.JDateChooser();
		pnl_endDate = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jDateChooser1 = new com.toedter.calendar.JDateChooser();
		pnl_money = new javax.swing.JPanel();
		pnl_total = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		cbo_total = new javax.swing.JComboBox<>();
		pnl_total1 = new javax.swing.JPanel();
		jLabel7 = new javax.swing.JLabel();
		cbo_total1 = new javax.swing.JComboBox<>();
		pnl_buttons = new javax.swing.JPanel();
		btn_filter1 = new javax.swing.JButton();
		btn_filter = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tbl_cashCountSheetList = new javax.swing.JTable();

		setPreferredSize(new java.awt.Dimension(1366, 768));
		setLayout(new java.awt.BorderLayout());

		pnl_header.setMinimumSize(new java.awt.Dimension(769, 120));
		pnl_header.setPreferredSize(new java.awt.Dimension(711, 100));
		pnl_header.setLayout(new javax.swing.BoxLayout(pnl_header, javax.swing.BoxLayout.X_AXIS));

		pnl_employee.setLayout(new javax.swing.BoxLayout(pnl_employee, javax.swing.BoxLayout.Y_AXIS));

		pnl_employee1.setMinimumSize(new java.awt.Dimension(164, 30));
		pnl_employee1.setPreferredSize(new java.awt.Dimension(350, 30));
		pnl_employee1.setLayout(new javax.swing.BoxLayout(pnl_employee1, javax.swing.BoxLayout.X_AXIS));

		jLabel1.setText("Nhân viên kiểm:");
		jLabel1.setMaximumSize(new java.awt.Dimension(150, 16));
		jLabel1.setMinimumSize(new java.awt.Dimension(150, 16));
		jLabel1.setPreferredSize(new java.awt.Dimension(150, 16));
		pnl_employee1.add(jLabel1);

		jTextField1.setPreferredSize(new java.awt.Dimension(200, 22));
		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});
		pnl_employee1.add(jTextField1);

		pnl_employee.add(pnl_employee1);

		pnl_employee2.setPreferredSize(new java.awt.Dimension(350, 30));
		pnl_employee2.setLayout(new javax.swing.BoxLayout(pnl_employee2, javax.swing.BoxLayout.X_AXIS));

		jLabel2.setText("Nhân viên đồng kiểm:");
		jLabel2.setMaximumSize(new java.awt.Dimension(150, 16));
		jLabel2.setMinimumSize(new java.awt.Dimension(150, 16));
		jLabel2.setPreferredSize(new java.awt.Dimension(150, 16));
		pnl_employee2.add(jLabel2);

		jTextField2.setPreferredSize(new java.awt.Dimension(200, 22));
		pnl_employee2.add(jTextField2);

		pnl_employee.add(pnl_employee2);

		pnl_header.add(pnl_employee);

		pnl_date.setLayout(new javax.swing.BoxLayout(pnl_date, javax.swing.BoxLayout.Y_AXIS));

		pnl_startDate.setPreferredSize(new java.awt.Dimension(300, 30));
		pnl_startDate.setLayout(new javax.swing.BoxLayout(pnl_startDate, javax.swing.BoxLayout.X_AXIS));

		jLabel3.setText("Từ ngày: ");
		jLabel3.setMaximumSize(new java.awt.Dimension(100, 16));
		jLabel3.setMinimumSize(new java.awt.Dimension(100, 16));
		jLabel3.setPreferredSize(new java.awt.Dimension(100, 16));
		pnl_startDate.add(jLabel3);

		jDateChooser2.setDateFormatString("dd/mm/yyyy");
		jDateChooser2.setPreferredSize(new java.awt.Dimension(200, 22));
		pnl_startDate.add(jDateChooser2);

		pnl_date.add(pnl_startDate);

		pnl_endDate.setPreferredSize(new java.awt.Dimension(300, 30));
		pnl_endDate.setLayout(new javax.swing.BoxLayout(pnl_endDate, javax.swing.BoxLayout.X_AXIS));

		jLabel4.setText("Đến ngày: ");
		jLabel4.setMaximumSize(new java.awt.Dimension(100, 16));
		jLabel4.setMinimumSize(new java.awt.Dimension(100, 16));
		jLabel4.setPreferredSize(new java.awt.Dimension(100, 16));
		pnl_endDate.add(jLabel4);

		jDateChooser1.setPreferredSize(new java.awt.Dimension(200, 30));
		pnl_endDate.add(jDateChooser1);

		pnl_date.add(pnl_endDate);

		pnl_header.add(pnl_date);

		pnl_money.setLayout(new javax.swing.BoxLayout(pnl_money, javax.swing.BoxLayout.Y_AXIS));

		pnl_total.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 30));
		pnl_total.setPreferredSize(new java.awt.Dimension(250, 30));
		pnl_total.setLayout(new javax.swing.BoxLayout(pnl_total, javax.swing.BoxLayout.X_AXIS));

		jLabel6.setText("Tổng từ:");
		jLabel6.setMaximumSize(new java.awt.Dimension(80, 16));
		jLabel6.setMinimumSize(new java.awt.Dimension(80, 16));
		jLabel6.setPreferredSize(new java.awt.Dimension(80, 16));
		pnl_total.add(jLabel6);

		cbo_total.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Dưới 1 triệu",
				"Từ 1 đến 2 triệu", "Từ 2 đến 5 triệu", "Từ 5 đến 10 triệu", "Trên 10 triệu" }));
		cbo_total.setPreferredSize(new java.awt.Dimension(140, 22));
		pnl_total.add(cbo_total);

		pnl_money.add(pnl_total);

		pnl_total1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 30));
		pnl_total1.setPreferredSize(new java.awt.Dimension(250, 30));
		pnl_total1.setLayout(new javax.swing.BoxLayout(pnl_total1, javax.swing.BoxLayout.X_AXIS));

		jLabel7.setText("Chênh lệch: ");
		jLabel7.setMaximumSize(new java.awt.Dimension(80, 16));
		jLabel7.setMinimumSize(new java.awt.Dimension(80, 16));
		jLabel7.setPreferredSize(new java.awt.Dimension(80, 16));
		pnl_total1.add(jLabel7);

		cbo_total1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Dưới 1 triệu",
				"Từ 1 đến 2 triệu", "Từ 2 đến 5 triệu", "Từ 5 đến 10 triệu", "Trên 10 triệu" }));
		cbo_total1.setPreferredSize(new java.awt.Dimension(140, 22));
		pnl_total1.add(cbo_total1);

		pnl_money.add(pnl_total1);

		pnl_header.add(pnl_money);

		pnl_buttons.setLayout(new java.awt.GridLayout(2, 1));

		btn_filter1.setIcon(SVGIcon.getSVGIcon("resources/imgs/public/refresh.svg"));
		btn_filter1.setMaximumSize(new java.awt.Dimension(200, 30));
		btn_filter1.setPreferredSize(new java.awt.Dimension(100, 30));
		pnl_buttons.add(btn_filter1);

		btn_filter.setText("Lọc");
		btn_filter.setIcon(SVGIcon.getSVGIcon("resources/imgs/public/filter.svg"));
		btn_filter.setMaximumSize(new java.awt.Dimension(200, 30));
		btn_filter.setPreferredSize(new java.awt.Dimension(100, 30));
		pnl_buttons.add(btn_filter);

		pnl_header.add(pnl_buttons);

		add(pnl_header, java.awt.BorderLayout.PAGE_START);

		tbl_cashCountSheetList.setModel(tblModel_cashCountSheetList);
		tbl_cashCountSheetList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tbl_cashCountSheetList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					tbl_cashCountSheetListMouseClicked(evt);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jScrollPane1.setViewportView(tbl_cashCountSheetList);

		add(jScrollPane1, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextField1ActionPerformed

	private void tbl_cashCountSheetListMouseClicked(java.awt.event.MouseEvent evt) throws RemoteException {// GEN-FIRST:event_tbl_cashCountSheetListMouseClicked
		// TODO add your handling code here: // Lấy chỉ số dòng được chọn
		int selectedRow = tbl_cashCountSheetList.getSelectedRow();

		// Kiểm tra xem có dòng nào được chọn không
		if (selectedRow != -1) {
			// Lấy giá trị ô đầu tiên trong dòng được chọn
			Object firstCellValue = tbl_cashCountSheetList.getValueAt(selectedRow, 0);

			// In giá trị ô đầu tiên ra console hoặc thực hiện các thao tác khác với giá trị
			// này
			GeneratePDF(cashCountSheet_BUS.getOne((String) firstCellValue));
		}
	}// GEN-LAST:event_tbl_cashCountSheetListMouseClicked

	public void GeneratePDF(CashCountSheet cash) {
		CashCountSheetPrinter printer = new CashCountSheetPrinter(cash);
		printer.generatePDF();

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btn_filter;
	private javax.swing.JButton btn_filter1;
	private javax.swing.JComboBox<String> cbo_total;
	private javax.swing.JComboBox<String> cbo_total1;
	private com.toedter.calendar.JDateChooser jDateChooser1;
	private com.toedter.calendar.JDateChooser jDateChooser2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JPanel pnl_buttons;
	private javax.swing.JPanel pnl_date;
	private javax.swing.JPanel pnl_employee;
	private javax.swing.JPanel pnl_employee1;
	private javax.swing.JPanel pnl_employee2;
	private javax.swing.JPanel pnl_endDate;
	private javax.swing.JPanel pnl_header;
	private javax.swing.JPanel pnl_money;
	private javax.swing.JPanel pnl_startDate;
	private javax.swing.JPanel pnl_total;
	private javax.swing.JPanel pnl_total1;
	private javax.swing.JTable tbl_cashCountSheetList;
	// End of variables declaration//GEN-END:variables
}
