/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * test.java
 *
 * Created on 25 Μαϊ 2010, 10:08:46 πμ
 */
package com.googlecode.starrating;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ssoldatos
 */
public class test extends javax.swing.JFrame {

  /** Creates new form test */
  public test() {
    initComponents();
    setLocationRelativeTo(null);
    DefaultTableModel model =new DefaultTableModel(new String[]{"Title","Rating"}, 0);
    table.setModel(model);
    model.addRow(new Object[]{"PHP",new Double(3.0)});
    model.addRow(new Object[]{"Java",2.5});
    model.addRow(new Object[]{"VBScript",3});
    model.addRow(new Object[]{"C++",1.5});
    TableCellStarRenderer rend = new TableCellStarRenderer(true);
    TableCellStarEditor ed = new TableCellStarEditor(true);
    table.getColumnModel().getColumn(1).setCellRenderer(rend);
    table.getColumnModel().getColumn(1).setCellEditor(ed);
    ed.addCellEditorListener(new CellEditorListener() {

      public void editingStopped(ChangeEvent e) {
         TableCellStarEditor s =  (TableCellStarEditor) e.getSource();
         System.out.println((double)s.getRating().getRate());
      }

      public void editingCanceled(ChangeEvent e) {
      }

     
    });
    srating.addPropertyChangeListener(new PropertyChangeListener() {

      public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(StarRating.RATE_CHANGED)){
          System.out.println(evt.getNewValue());
        }
      }
    });

  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    table = new javax.swing.JTable();
    srating = new com.googlecode.starrating.StarRating();
    jCheckBox1 = new javax.swing.JCheckBox();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 200));
    jScrollPane1.setViewportView(table);

    jCheckBox1.setSelected(true);
    jCheckBox1.setText("enabled");
    jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox1ActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createSequentialGroup()
            .addGap(167, 167, 167)
            .addComponent(srating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jCheckBox1)))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(5, 5, 5)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(srating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jCheckBox1))
        .addGap(172, 172, 172))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
    srating.setEnabled(jCheckBox1.isSelected());
  }//GEN-LAST:event_jCheckBox1ActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new test().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JScrollPane jScrollPane1;
  private com.googlecode.starrating.StarRating srating;
  private javax.swing.JTable table;
  // End of variables declaration//GEN-END:variables
}
