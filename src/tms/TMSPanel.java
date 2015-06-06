package tms;

/**
 *
 * @author Chris Liebert
 */
public class TMSPanel extends javax.swing.JPanel {

    // Reference to application object

    private final TMSGUI tms;
    private final CreateTaskDialog createTaskDialog;

    /**
     * Creates new form TMSPanel
     *
     * @param _tms application object
     */
    public TMSPanel(TMSGUI _tms) {
        tms = _tms;
        createTaskDialog = new CreateTaskDialog(tms, true);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newTaskButton = new javax.swing.JButton();
        toDoDeleteTaskButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inProgressList = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        toDoList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        doneList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        toDoBeginButton = new javax.swing.JButton();
        inProgressFinishButton = new javax.swing.JButton();
        doneContinueButton = new javax.swing.JButton();
        inProgressDeleteButton = new javax.swing.JButton();
        doneDeleteTask = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();

        newTaskButton.setText("New Task");
        newTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTaskButtonActionPerformed(evt);
            }
        });

        toDoDeleteTaskButton.setText("Delete");
        toDoDeleteTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDoDeleteTaskButtonActionPerformed(evt);
            }
        });

        inProgressList.setModel(tms.taskController.inProgressList);
        inProgressList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(inProgressList);

        toDoList.setModel(tms.taskController.toDoList);
        toDoList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        toDoList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(toDoList);

        doneList.setModel(tms.taskController.doneList);
        doneList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(doneList);

        jLabel2.setText("To Do");

        jLabel3.setText("In Progress");

        jLabel4.setText("Done");

        toDoBeginButton.setText("Begin");
        toDoBeginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDoBeginButtonActionPerformed(evt);
            }
        });

        inProgressFinishButton.setText("Finish");
        inProgressFinishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inProgressFinishButtonActionPerformed(evt);
            }
        });

        doneContinueButton.setText("Continue");
        doneContinueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneContinueButtonActionPerformed(evt);
            }
        });

        inProgressDeleteButton.setText("Delete");
        inProgressDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inProgressDeleteButtonActionPerformed(evt);
            }
        });

        doneDeleteTask.setText("Delete");
        doneDeleteTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneDeleteTaskActionPerformed(evt);
            }
        });

        logoutButton.setText("Log Out");
        logoutButton.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                logoutButtonAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(newTaskButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(61, 61, 61)))
                        .addComponent(toDoBeginButton)
                        .addGap(18, 18, 18)
                        .addComponent(toDoDeleteTaskButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(54, 54, 54)
                                .addComponent(inProgressFinishButton)
                                .addGap(18, 18, 18)
                                .addComponent(inProgressDeleteButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(68, 68, 68)
                                .addComponent(doneContinueButton)
                                .addGap(18, 18, 18)
                                .addComponent(doneDeleteTask))))
                    .addComponent(logoutButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newTaskButton)
                    .addComponent(logoutButton))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(inProgressFinishButton)
                    .addComponent(doneContinueButton)
                    .addComponent(toDoDeleteTaskButton)
                    .addComponent(inProgressDeleteButton)
                    .addComponent(doneDeleteTask)
                    .addComponent(toDoBeginButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTaskButtonActionPerformed
        createTaskDialog.setVisible(true);
    }//GEN-LAST:event_newTaskButtonActionPerformed

    private void toDoDeleteTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toDoDeleteTaskButtonActionPerformed
        // Delete from to-do
        int selectedIndex = toDoList.getSelectedIndex();
        // Only delete if an item is selected
        if (selectedIndex < tms.taskController.toDoList.getSize() && selectedIndex > -1) {
            try {
                tms.taskController.deleteTask(toDoList.getSelectedValue().toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_toDoDeleteTaskButtonActionPerformed

    private void inProgressDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inProgressDeleteButtonActionPerformed
        // Delete from in-progress
        int selectedIndex = inProgressList.getSelectedIndex();
        // Only delete if an item is selected
        if (selectedIndex < tms.taskController.inProgressList.getSize() && selectedIndex > -1) {
            try {
                tms.taskController.deleteTask(inProgressList.getSelectedValue().toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_inProgressDeleteButtonActionPerformed

    private void doneDeleteTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneDeleteTaskActionPerformed
        // Delete from done
        int selectedIndex = doneList.getSelectedIndex();
        // Only delete if an item is selected
        if (selectedIndex < tms.taskController.doneList.getSize() && selectedIndex > -1) {
            try {
                tms.taskController.deleteTask(doneList.getSelectedValue().toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_doneDeleteTaskActionPerformed

    private void toDoBeginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toDoBeginButtonActionPerformed
        // Begin a task
        int selectedIndex = toDoList.getSelectedIndex();
        // Only begin if a task is selected
        if (selectedIndex < tms.taskController.toDoList.getSize() && selectedIndex > -1) {
            tms.taskController.updateTask(toDoList.getSelectedValue().toString(), 1);
        }
    }//GEN-LAST:event_toDoBeginButtonActionPerformed

    private void inProgressFinishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inProgressFinishButtonActionPerformed
        // Finish a task (move to done list)
        int selectedIndex = inProgressList.getSelectedIndex();
        // Only update if task selected
        if (selectedIndex < tms.taskController.inProgressList.getSize() && selectedIndex > -1) {
            tms.taskController.updateTask(inProgressList.getSelectedValue().toString(), 2);
        }
    }//GEN-LAST:event_inProgressFinishButtonActionPerformed

    private void doneContinueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneContinueButtonActionPerformed
        // Move a task from done to in-progress
        int selectedIndex = doneList.getSelectedIndex();
        // Only move if task is selected
        if (selectedIndex < tms.taskController.doneList.getSize() && selectedIndex > -1) {
            tms.taskController.updateTask(doneList.getSelectedValue().toString(), 1);
        }
    }//GEN-LAST:event_doneContinueButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        tms.taskController.setCredentials(null);
        tms.taskController.toDoList.clear();
        tms.taskController.doneList.clear();
        tms.taskController.inProgressList.clear();
        tms.loginDialog.usernameTextField.setText("");
        tms.loginDialog.passwordTextField.setText("");
        tms.frame.setVisible(false);
        tms.loginDialog.setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void logoutButtonAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_logoutButtonAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton doneContinueButton;
    private javax.swing.JButton doneDeleteTask;
    protected javax.swing.JList doneList;
    private javax.swing.JButton inProgressDeleteButton;
    private javax.swing.JButton inProgressFinishButton;
    protected javax.swing.JList inProgressList;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton newTaskButton;
    private javax.swing.JButton toDoBeginButton;
    private javax.swing.JButton toDoDeleteTaskButton;
    protected javax.swing.JList toDoList;
    // End of variables declaration//GEN-END:variables
}