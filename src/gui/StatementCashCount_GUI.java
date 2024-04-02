/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import bus.StatementCashCount_BUS;
import com.formdev.flatlaf.FlatClientProperties;
import entity.CashCount;
import entity.CashCountSheet;
import entity.Employee;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import main.Application;
import raven.toast.Notifications;
import utilities.CashCountSheetPrinter;
import utilities.FormatNumber;
import utilities.OrderPrinter;
import utilities.SVGIcon;

/**
 *
 * @author Hoàng Khang
 */
public class StatementCashCount_GUI extends javax.swing.JPanel {

    private DefaultTableModel tbl_modalCashCounts = new DefaultTableModel();
    private double sum = 0;
    private StatementCashCount_BUS statementCashCount_BUS = new StatementCashCount_BUS();
    private Employee employee1 = Application.employee;
    private Employee employee2;
    private Date createAt;

    public StatementCashCount_GUI() {
        initTableModel();
        initComponents();
        initInfo(employee1);
        alterTable();
        tbl_cashCounts.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 2) { // Số lượng
                    TableModel model = (TableModel) e.getSource();
                    try {
                        int quantity = Integer.parseInt(model.getValueAt(row, column).toString());
                        double denomination = Integer.parseInt(model.getValueAt(row, 1).toString().replace(".", ""));
                        double total = 0;
                        if (quantity < 0) {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Số lượng không hợp lệ!");
                            quantity = 0;
                            model.setValueAt(0, row, column);

                        } else {
                            total = quantity * denomination;
                        }

//                        sum += total;
                        sum = statementCashCount_BUS.getTotal(getValueInTable());
                        txt_difference.setText(FormatNumber.toVND(sum - 1765000));
                        txt_total.setText(FormatNumber.toVND(sum));
                        model.setValueAt(FormatNumber.toVND(total), row, 3); // Tổng
                    } catch (NumberFormatException ex) {
                        model.setValueAt("0", row, column);
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Số lượng không hợp lệ!");

                    }
                }
            }
        });

    }

    class NonEditableTableModel extends DefaultTableModel {

        public NonEditableTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // Cho phép chỉnh sửa cột "Số lượng", ngăn chặn các cột khác
            return column == 2;
        }
    }

    class NonEditableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public boolean isOpaque() {
            return true;
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setOpaque(false);
            return this;
        }
    }

    public void initInfo(Employee e) {
        createAt = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(createAt);

        txt_timeCashCount.setText(formattedDate);
        txt_employeeCashCount1.setText(e.getEmployeeID());
        txt_employeeCashCount1Name.setText(e.getName());
        txt_cashCountID.setText(statementCashCount_BUS.generateID(createAt));
        txt_difference.setText(FormatNumber.toVND((Double.valueOf("-1765000"))));

    }

    public void alterTable() {
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);

////        Align
        tbl_cashCounts.getColumnModel().getColumn(1).setCellRenderer(rightAlign);
        tbl_cashCounts.getColumnModel().getColumn(2).setCellRenderer(rightAlign);
        tbl_cashCounts.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
        tbl_cashCounts.getColumnModel().getColumn(0).setCellRenderer(rightAlign);

    }

    public void initTableModel() {
        // Products
        tbl_modalCashCounts = new DefaultTableModel(new Object[]{"STT", "Mệnh giá", "Số lượng", "Tổng"
        }, 0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_title = new javax.swing.JPanel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        lbl_titleCashCount = new javax.swing.JLabel();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        pnl_center = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnl_infomation = new javax.swing.JPanel();
        pnl_infoFooter = new javax.swing.JPanel();
        pnl_difference = new javax.swing.JPanel();
        lbl_difference = new javax.swing.JLabel();
        txt_difference = new javax.swing.JTextField();
        pnl_cashCountSave = new javax.swing.JPanel();
        btn_saveCashCount = new javax.swing.JButton();
        pnl_infoBody = new javax.swing.JPanel();
        pnl_cashCountID = new javax.swing.JPanel();
        lbl_cashCountID = new javax.swing.JLabel();
        txt_cashCountID = new javax.swing.JTextField();
        pnl_employeeCashCount1 = new javax.swing.JPanel();
        lbl_employeeCashCount1 = new javax.swing.JLabel();
        txt_employeeCashCount1 = new javax.swing.JTextField();
        pnl_employeeCashCount1Name = new javax.swing.JPanel();
        lbl_employeeCashCount3 = new javax.swing.JLabel();
        txt_employeeCashCount1Name = new javax.swing.JTextField();
        pnl_employeeCashCount2 = new javax.swing.JPanel();
        lbl_employeeCashCount2 = new javax.swing.JLabel();
        txt_employeeCashCount2 = new javax.swing.JTextField();
        btn_addEmoloyee = new javax.swing.JButton();
        pnl_employeeCashCount2Name = new javax.swing.JPanel();
        lbl_employeeCashCount4 = new javax.swing.JLabel();
        txt_employeeCashCount1Name1 = new javax.swing.JTextField();
        pnl_infoHeader = new javax.swing.JPanel();
        txt_timeCashCount = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        scr_cashCounts = new javax.swing.JScrollPane();
        tbl_cashCounts = new javax.swing.JTable();
        pnl_total = new javax.swing.JPanel();
        lbl_total = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1366, 768));
        setLayout(new java.awt.BorderLayout());

        pnl_title.setLayout(new java.awt.BorderLayout());
        pnl_title.add(filler10, java.awt.BorderLayout.SOUTH);

        lbl_titleCashCount.setFont(lbl_titleCashCount.getFont().deriveFont(lbl_titleCashCount.getFont().getStyle() | java.awt.Font.BOLD, 24));
        lbl_titleCashCount.setText("Kiểm tiền dự phòng:");
        pnl_title.add(lbl_titleCashCount, java.awt.BorderLayout.CENTER);
        pnl_title.add(filler11, java.awt.BorderLayout.NORTH);
        pnl_title.add(filler12, java.awt.BorderLayout.LINE_START);

        add(pnl_title, java.awt.BorderLayout.NORTH);

        pnl_center.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setResizeWeight(0.9);

        pnl_infomation.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(71, 118, 185))); // NOI18N
        pnl_infomation.setFont(pnl_infomation.getFont());
        pnl_infomation.setMaximumSize(new java.awt.Dimension(800, 500));
        pnl_infomation.setMinimumSize(new java.awt.Dimension(400, 400));
        pnl_infomation.setPreferredSize(new java.awt.Dimension(450, 500));
        pnl_infomation.setLayout(new java.awt.BorderLayout());

        pnl_infoFooter.setLayout(new javax.swing.BoxLayout(pnl_infoFooter, javax.swing.BoxLayout.Y_AXIS));

        pnl_difference.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 1, 5));
        pnl_difference.setMaximumSize(new java.awt.Dimension(2147483647, 70));
        pnl_difference.setMinimumSize(new java.awt.Dimension(149, 40));
        pnl_difference.setPreferredSize(new java.awt.Dimension(160, 45));
        pnl_difference.setLayout(new javax.swing.BoxLayout(pnl_difference, javax.swing.BoxLayout.LINE_AXIS));

        lbl_difference.setFont(lbl_difference.getFont().deriveFont(lbl_difference.getFont().getStyle() | java.awt.Font.BOLD, 18));
        lbl_difference.setText("Chênh lệch");
        pnl_difference.add(lbl_difference);

        txt_difference.setEditable(false);
        txt_difference.setFont(txt_difference.getFont().deriveFont(txt_difference.getFont().getStyle() & ~java.awt.Font.BOLD, 18));
        txt_difference.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_difference.setText("0");
        txt_difference.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 0)));
        txt_difference.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_difference.setFocusable(false);
        txt_difference.setPreferredSize(new java.awt.Dimension(100, 22));
        txt_difference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_differenceActionPerformed(evt);
            }
        });
        pnl_difference.add(txt_difference);

        pnl_infoFooter.add(pnl_difference);

        pnl_cashCountSave.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 0, 0));
        pnl_cashCountSave.setPreferredSize(new java.awt.Dimension(135, 60));
        pnl_cashCountSave.setLayout(new java.awt.GridLayout(1, 0));

        btn_saveCashCount.setFont(btn_saveCashCount.getFont().deriveFont(btn_saveCashCount.getFont().getStyle() | java.awt.Font.BOLD, 20));
        btn_saveCashCount.setText("Tạo báo cáo");
        btn_saveCashCount.putClientProperty(FlatClientProperties.STYLE, "background: $Menu.background;"+"foreground: $Menu.foreground;");
        btn_saveCashCount.setIcon(SVGIcon.getPrimarySVGIcon("imgs/public/add.svg"));
        btn_saveCashCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveCashCountActionPerformed(evt);
            }
        });
        pnl_cashCountSave.add(btn_saveCashCount);

        pnl_infoFooter.add(pnl_cashCountSave);

        pnl_infomation.add(pnl_infoFooter, java.awt.BorderLayout.SOUTH);

        pnl_infoBody.setToolTipText("");
        pnl_infoBody.setLayout(new javax.swing.BoxLayout(pnl_infoBody, javax.swing.BoxLayout.Y_AXIS));

        pnl_cashCountID.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        pnl_cashCountID.setFont(pnl_cashCountID.getFont().deriveFont((float)16));
        pnl_cashCountID.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        pnl_cashCountID.setMinimumSize(new java.awt.Dimension(0, 40));
        pnl_cashCountID.setPreferredSize(new java.awt.Dimension(0, 40));
        pnl_cashCountID.setLayout(new javax.swing.BoxLayout(pnl_cashCountID, javax.swing.BoxLayout.X_AXIS));

        lbl_cashCountID.setFont(lbl_cashCountID.getFont().deriveFont((float)16));
        lbl_cashCountID.setText("Mã phiếu:");
        lbl_cashCountID.setPreferredSize(new java.awt.Dimension(145, 16));
        pnl_cashCountID.add(lbl_cashCountID);

        txt_cashCountID.setEditable(false);
        txt_cashCountID.setFont(txt_cashCountID.getFont().deriveFont((float)16));
        txt_cashCountID.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        txt_cashCountID.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_cashCountID.setFocusable(false);
        txt_cashCountID.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        txt_cashCountID.setPreferredSize(new java.awt.Dimension(100, 50));
        txt_cashCountID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cashCountIDActionPerformed(evt);
            }
        });
        pnl_cashCountID.add(txt_cashCountID);

        pnl_infoBody.add(pnl_cashCountID);

        pnl_employeeCashCount1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        pnl_employeeCashCount1.setFont(pnl_employeeCashCount1.getFont().deriveFont((float)16));
        pnl_employeeCashCount1.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        pnl_employeeCashCount1.setMinimumSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount1.setPreferredSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount1.setLayout(new javax.swing.BoxLayout(pnl_employeeCashCount1, javax.swing.BoxLayout.X_AXIS));

        lbl_employeeCashCount1.setFont(lbl_employeeCashCount1.getFont().deriveFont((float)16));
        lbl_employeeCashCount1.setText("Nhân viên 1:");
        lbl_employeeCashCount1.setPreferredSize(new java.awt.Dimension(145, 16));
        pnl_employeeCashCount1.add(lbl_employeeCashCount1);

        txt_employeeCashCount1.setEditable(false);
        txt_employeeCashCount1.setFont(txt_employeeCashCount1.getFont().deriveFont((float)16));
        txt_employeeCashCount1.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        txt_employeeCashCount1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_employeeCashCount1.setFocusable(false);
        txt_employeeCashCount1.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        txt_employeeCashCount1.setPreferredSize(new java.awt.Dimension(100, 50));
        txt_employeeCashCount1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employeeCashCount1ActionPerformed(evt);
            }
        });
        pnl_employeeCashCount1.add(txt_employeeCashCount1);

        pnl_infoBody.add(pnl_employeeCashCount1);

        pnl_employeeCashCount1Name.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        pnl_employeeCashCount1Name.setFont(pnl_employeeCashCount1Name.getFont().deriveFont((float)16));
        pnl_employeeCashCount1Name.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        pnl_employeeCashCount1Name.setMinimumSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount1Name.setPreferredSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount1Name.setLayout(new javax.swing.BoxLayout(pnl_employeeCashCount1Name, javax.swing.BoxLayout.X_AXIS));

        lbl_employeeCashCount3.setFont(lbl_employeeCashCount3.getFont().deriveFont((float)16));
        lbl_employeeCashCount3.setPreferredSize(new java.awt.Dimension(145, 16));
        pnl_employeeCashCount1Name.add(lbl_employeeCashCount3);

        txt_employeeCashCount1Name.setEditable(false);
        txt_employeeCashCount1Name.setFont(txt_employeeCashCount1Name.getFont().deriveFont((float)16));
        txt_employeeCashCount1Name.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        txt_employeeCashCount1Name.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_employeeCashCount1Name.setFocusable(false);
        txt_employeeCashCount1Name.setPreferredSize(new java.awt.Dimension(100, 22));
        txt_employeeCashCount1Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employeeCashCount1NameActionPerformed(evt);
            }
        });
        pnl_employeeCashCount1Name.add(txt_employeeCashCount1Name);

        pnl_infoBody.add(pnl_employeeCashCount1Name);

        pnl_employeeCashCount2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        pnl_employeeCashCount2.setFont(pnl_employeeCashCount2.getFont().deriveFont((float)16));
        pnl_employeeCashCount2.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        pnl_employeeCashCount2.setMinimumSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount2.setPreferredSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount2.setLayout(new javax.swing.BoxLayout(pnl_employeeCashCount2, javax.swing.BoxLayout.LINE_AXIS));

        lbl_employeeCashCount2.setFont(lbl_employeeCashCount2.getFont().deriveFont((float)16));
        lbl_employeeCashCount2.setText("Nhân viên 2:");
        lbl_employeeCashCount2.setPreferredSize(new java.awt.Dimension(145, 16));
        pnl_employeeCashCount2.add(lbl_employeeCashCount2);

        txt_employeeCashCount2.setEditable(false);
        txt_employeeCashCount2.setFont(txt_employeeCashCount2.getFont().deriveFont((float)16));
        txt_employeeCashCount2.setText("Chưa có");
        txt_employeeCashCount2.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        txt_employeeCashCount2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_employeeCashCount2.setFocusable(false);
        txt_employeeCashCount2.setMaximumSize(new java.awt.Dimension(2147483647, 250));
        txt_employeeCashCount2.setMinimumSize(new java.awt.Dimension(100, 22));
        txt_employeeCashCount2.setPreferredSize(new java.awt.Dimension(100, 50));
        txt_employeeCashCount2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employeeCashCount2ActionPerformed(evt);
            }
        });
        pnl_employeeCashCount2.add(txt_employeeCashCount2);

        btn_addEmoloyee.setText("+");
        btn_addEmoloyee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addEmoloyeeActionPerformed(evt);
            }
        });
        pnl_employeeCashCount2.add(btn_addEmoloyee);

        pnl_infoBody.add(pnl_employeeCashCount2);

        pnl_employeeCashCount2Name.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        pnl_employeeCashCount2Name.setFont(pnl_employeeCashCount2Name.getFont().deriveFont((float)16));
        pnl_employeeCashCount2Name.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        pnl_employeeCashCount2Name.setMinimumSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount2Name.setPreferredSize(new java.awt.Dimension(0, 40));
        pnl_employeeCashCount2Name.setLayout(new javax.swing.BoxLayout(pnl_employeeCashCount2Name, javax.swing.BoxLayout.X_AXIS));

        lbl_employeeCashCount4.setFont(lbl_employeeCashCount4.getFont().deriveFont((float)16));
        lbl_employeeCashCount4.setPreferredSize(new java.awt.Dimension(145, 16));
        pnl_employeeCashCount2Name.add(lbl_employeeCashCount4);

        txt_employeeCashCount1Name1.setEditable(false);
        txt_employeeCashCount1Name1.setFont(txt_employeeCashCount1Name1.getFont().deriveFont((float)16));
        txt_employeeCashCount1Name1.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        txt_employeeCashCount1Name1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_employeeCashCount1Name1.setFocusable(false);
        txt_employeeCashCount1Name1.setMaximumSize(new java.awt.Dimension(2147483647, 250));
        txt_employeeCashCount1Name1.setMinimumSize(new java.awt.Dimension(64, 50));
        txt_employeeCashCount1Name1.setPreferredSize(new java.awt.Dimension(100, 22));
        txt_employeeCashCount1Name1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employeeCashCount1Name1ActionPerformed(evt);
            }
        });
        pnl_employeeCashCount2Name.add(txt_employeeCashCount1Name1);

        pnl_infoBody.add(pnl_employeeCashCount2Name);

        pnl_infomation.add(pnl_infoBody, java.awt.BorderLayout.CENTER);

        txt_timeCashCount.setEditable(false);
        txt_timeCashCount.setFont(txt_timeCashCount.getFont().deriveFont(txt_timeCashCount.getFont().getStyle() | java.awt.Font.BOLD, 20));
        txt_timeCashCount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_timeCashCount.setBorder(null);
        txt_timeCashCount.setMaximumSize(new java.awt.Dimension(2147483647, 100));
        txt_timeCashCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timeCashCountActionPerformed(evt);
            }
        });
        pnl_infoHeader.add(txt_timeCashCount);

        pnl_infomation.add(pnl_infoHeader, java.awt.BorderLayout.NORTH);

        jSplitPane1.setRightComponent(pnl_infomation);

        jPanel1.setLayout(new java.awt.BorderLayout());

        scr_cashCounts.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết kiểm tiền", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(71, 118, 185)), javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5))); // NOI18N
        scr_cashCounts.setMinimumSize(new java.awt.Dimension(1200, 16));

        tbl_cashCounts.setFont(tbl_cashCounts.getFont().deriveFont(tbl_cashCounts.getFont().getStyle() & ~java.awt.Font.BOLD, 22));
        tbl_cashCounts.setModel(new NonEditableTableModel(
            new Object[][] {
                {"1", "500.000", null, null},
                {"2", "200.000", null, null},
                {"3", "100.000", null, null},
                {"4", "50.000", null, null},
                {"5", "20.000", null, null},
                {"6", "10.000", null, null},
                {"7", "5.000", null, null},
                {"8", "2.000", null, null},
                {"9", "1.000", null, null},
                {"10", "500", null, null}
            },
            new String[] {
                "STT", "Mệnh giá", "Số lượng", "Tổng"
            }
        ));
        for (int i = 0; i < tbl_cashCounts.getColumnCount(); i++) {
            if (i != 2) { // Cột "Số lượng" (cột thứ 2) có thể chỉnh sửa
                tbl_cashCounts.getColumnModel().getColumn(i).setCellRenderer(new NonEditableCellRenderer());
            }
        }
        tbl_cashCounts.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_cashCounts.setMaximumSize(new java.awt.Dimension(2147483647, 800));
        tbl_cashCounts.setMinimumSize(new java.awt.Dimension(800, 450));
        tbl_cashCounts.setRowHeight(45);
        tbl_cashCounts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scr_cashCounts.setViewportView(tbl_cashCounts);

        jPanel1.add(scr_cashCounts, java.awt.BorderLayout.CENTER);

        pnl_total.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnl_total.setMaximumSize(new java.awt.Dimension(2147483647, 70));
        pnl_total.setMinimumSize(new java.awt.Dimension(149, 40));
        pnl_total.setPreferredSize(new java.awt.Dimension(160, 45));
        pnl_total.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lbl_total.setFont(lbl_total.getFont().deriveFont(lbl_total.getFont().getStyle() | java.awt.Font.BOLD, 20));
        lbl_total.setText("Tổng:");
        pnl_total.add(lbl_total);

        txt_total.setEditable(false);
        txt_total.setFont(txt_total.getFont().deriveFont(txt_total.getFont().getStyle() | java.awt.Font.BOLD, 26));
        txt_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_total.setText("0");
        txt_total.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        txt_total.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_total.setEnabled(false);
        txt_total.setFocusable(false);
        txt_total.setPreferredSize(new java.awt.Dimension(200, 22));
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        pnl_total.add(txt_total);

        jPanel1.add(pnl_total, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(jPanel1);

        pnl_center.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        add(pnl_center, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_timeCashCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timeCashCountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_timeCashCountActionPerformed

    private void txt_employeeCashCount1Name1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employeeCashCount1Name1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeCashCount1Name1ActionPerformed

    private void txt_employeeCashCount2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employeeCashCount2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeCashCount2ActionPerformed

    private void txt_employeeCashCount1NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employeeCashCount1NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeCashCount1NameActionPerformed

    private void txt_employeeCashCount1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employeeCashCount1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeCashCount1ActionPerformed

    private void btn_saveCashCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveCashCountActionPerformed
        // TODO add your handling code here:
        if (employee2 == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Chưa có nhân viên đồng kiểm!");
            Notifications.getInstance().show(Notifications.Type.ERROR, "Lưu phiếu kiểm tiền không thành công!");
        } else {
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(employee1);
            employees.add(employee2);
            statementCashCount_BUS.createCashCountSheet(getValueInTable(), employees, createAt);
            Notifications.getInstance().show(Notifications.Type.INFO, "Tạo phiếu kiểm tiền thành công");
            Application.showForm(new StatementCashCount_GUI());
        }

    }//GEN-LAST:event_btn_saveCashCountActionPerformed

    private void txt_differenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_differenceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_differenceActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void btn_addEmoloyeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addEmoloyeeActionPerformed
        String id = JOptionPane.showInputDialog("Nhập mã nhân viên");
        Employee e = statementCashCount_BUS.getEmployeeByID(id);
        if (e == null) {
//            JOptionPane.showConfirmDialog(jSplitPane1, "Nhân viên không tồn tại.");
            Notifications.getInstance().show(Notifications.Type.ERROR, "Nhân viên không tồn tại!");

        } else {
            if (e.equals(employee1)) {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Nhân viên chứng kiến không được là nhân viên kiểm!");

//                JOptionPane.showConfirmDialog(jSplitPane1, "Nhân viên chứng kiến không được là nhân viên kiểm!");
            } else {
                employee2 = e;
                txt_employeeCashCount1Name1.setText(employee2.getName());
                txt_employeeCashCount2.setText(employee2.getEmployeeID());
            }

        }

    }//GEN-LAST:event_btn_addEmoloyeeActionPerformed

    private void txt_cashCountIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cashCountIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cashCountIDActionPerformed

    public ArrayList<CashCount> getValueInTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tbl_cashCounts.getModel();
        int rowCount = tableModel.getRowCount();
        ArrayList<CashCount> cashCounts = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            String valueStr = (String) tableModel.getValueAt(i, 1);
            String quantityStr = "0";
            try {
                quantityStr = (String) tableModel.getValueAt(i, 2);
            } catch (Exception e) {
            }
            double value = Double.parseDouble(valueStr.replace(".", "").replace(",", "."));
            int quantity;
            if (quantityStr == null || quantityStr.equals("0")) {
                quantity = 0;
            } else {
                quantity = Integer.parseInt(quantityStr.trim());
            }
            if (quantity > 0) {
                cashCounts.add(new CashCount(quantity, value));
            }
        }
        return cashCounts;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addEmoloyee;
    private javax.swing.JButton btn_saveCashCount;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lbl_cashCountID;
    private javax.swing.JLabel lbl_difference;
    private javax.swing.JLabel lbl_employeeCashCount1;
    private javax.swing.JLabel lbl_employeeCashCount2;
    private javax.swing.JLabel lbl_employeeCashCount3;
    private javax.swing.JLabel lbl_employeeCashCount4;
    private javax.swing.JLabel lbl_titleCashCount;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel pnl_cashCountID;
    private javax.swing.JPanel pnl_cashCountSave;
    private javax.swing.JPanel pnl_center;
    private javax.swing.JPanel pnl_difference;
    private javax.swing.JPanel pnl_employeeCashCount1;
    private javax.swing.JPanel pnl_employeeCashCount1Name;
    private javax.swing.JPanel pnl_employeeCashCount2;
    private javax.swing.JPanel pnl_employeeCashCount2Name;
    private javax.swing.JPanel pnl_infoBody;
    private javax.swing.JPanel pnl_infoFooter;
    private javax.swing.JPanel pnl_infoHeader;
    private javax.swing.JPanel pnl_infomation;
    private javax.swing.JPanel pnl_title;
    private javax.swing.JPanel pnl_total;
    private javax.swing.JScrollPane scr_cashCounts;
    private javax.swing.JTable tbl_cashCounts;
    private javax.swing.JTextField txt_cashCountID;
    private javax.swing.JTextField txt_difference;
    private javax.swing.JTextField txt_employeeCashCount1;
    private javax.swing.JTextField txt_employeeCashCount1Name;
    private javax.swing.JTextField txt_employeeCashCount1Name1;
    private javax.swing.JTextField txt_employeeCashCount2;
    private javax.swing.JTextField txt_timeCashCount;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
