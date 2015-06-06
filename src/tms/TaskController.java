package tms;

/**
 *
 * @author Chris Liebert
 */
public class TaskController {

    // Credentials are stored in the controller

    private Credentials credentials = null;
    // Application object reference
    private TMSGUI tmsgui = null;
    // Task lists
    public TaskList toDoList, inProgressList, doneList = null;

    TaskController(TMSGUI _tmsgui) {
        // Load running appliation object
        tmsgui = _tmsgui;
        // Initialize task lists
        toDoList = new TaskList();
        inProgressList = new TaskList();
        doneList = new TaskList();
    }

    // Set TMS credentials
    public void setCredentials(Credentials _credentials) {
        credentials = _credentials;
    }

    // Create a task and update the database
    public void createTask(String taskString) throws Exception {
        if (taskString.isEmpty()) {
            throw new TaskList.AddEmptyStringException();
        } else if (isUniqueTask(taskString)) {
            toDoList.add(taskString);
            if (!tmsgui.getDBAdapter().createTask(taskString, credentials)) {
                System.err.println("Unable to create task " + taskString);
                tmsgui.showLoginDialog();
            }
        } else {
            throw new TaskList.TaskExistsException();
        }
    }

    // Load array of to-do tasks
    public void loadToDoTasks(String[] tasks) throws Exception {
        toDoList.clear();
        for (String task : tasks) {
            toDoList.addItem(task);
        }
    }

    // Load array of in-progress tasks
    public void loadInProgressTasks(String[] tasks) throws Exception {
        inProgressList.clear();
        for (String task : tasks) {
            inProgressList.addItem(task);
        }
    }

    // Load array of done tasks
    public void loadDoneTasks(String[] tasks) throws Exception {
        doneList.clear();
        for (String task : tasks) {
            doneList.addItem(task);
        }
    }

    // Delete a task and update the database
    public void deleteTask(String taskString) throws TaskList.NoTaskExistsException {
        try {
            if (toDoList.containsTask(taskString)) {
                toDoList.delete(taskString);
                tmsgui.panel.toDoList.updateUI();
            } else if (inProgressList.containsTask(taskString)) {
                inProgressList.delete(taskString);
                tmsgui.panel.inProgressList.updateUI();
            } else if (doneList.containsTask(taskString)) {
                doneList.delete(taskString);
                tmsgui.panel.doneList.updateUI();
            } else {
                return;
            }
            // Delete task from database
            if (!tmsgui.getDBAdapter().deleteTask(taskString, credentials)) {
                tmsgui.showLoginDialog();
            }
        } catch (TaskList.NoTaskExistsException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Check to see if a task is already in one of the task lists
    public boolean isUniqueTask(String taskString) {
        return (!toDoList.containsTask(taskString)
                && !inProgressList.containsTask(taskString)
                && !doneList.containsTask(taskString));
    }

    // Move a task to a different list. 0 = to-do, 1 = in-progress, 2 = done.
    public void updateTask(String taskString, int listID) {
        // Remove from the current list
        try {
            if (toDoList.containsTask(taskString)) {
                toDoList.delete(taskString);
                tmsgui.panel.toDoList.updateUI();
            } else if (inProgressList.containsTask(taskString)) {
                inProgressList.delete(taskString);
                tmsgui.panel.inProgressList.updateUI();
            } else if (doneList.containsTask(taskString)) {
                doneList.delete(taskString);
                tmsgui.panel.doneList.updateUI();
            } else {
                return;
            }

            // Add to the new list
            if (listID == 0) {
                toDoList.add(taskString);
                tmsgui.panel.toDoList.updateUI();
            } else if (listID == 1) {
                inProgressList.add(taskString);
                tmsgui.panel.inProgressList.updateUI();
            } else if (listID == 2) {
                doneList.add(taskString);
                tmsgui.panel.doneList.updateUI();
            }

            // Update the database
            if (!tmsgui.getDBAdapter().updateTask(taskString, listID, credentials)) {
                tmsgui.showLoginDialog();
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

}
