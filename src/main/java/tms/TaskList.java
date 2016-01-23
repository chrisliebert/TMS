package tms;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Chris Liebert
 */
public class TaskList implements javax.swing.ListModel {

    // Item list
    public List<String> items = null;

    TaskList() {
        items = new ArrayList();
    }

    // Try to a task
    void add(String s) throws Exception {
        try {
            addItem(s);
        } catch (Exception ex) {
            throw ex;
        }
    }

    // Method add a task 
    void addItem(String s) throws Exception {
        if (containsTask(s)) {
            throw new TaskExistsException(s);
        } else if (s.isEmpty()) {
            throw new AddEmptyStringException();
        } else {
            items.add(s);
        }
    }

    // Check if a string is already in the list
    boolean containsTask(String s) {
        for (int i = 0; i < getSize(); i++) {
            if (getElementAt(i).equals(s)) {
                return true;
            }
        }
        return false;
    }

    // Remove a task by name
    void delete(String s) throws NoTaskExistsException {
        int itemIndex = -1;
        for (int i = 0; i < getSize(); i++) {
            if (getElementAt(i).equals(s)) {
                itemIndex = i;
            }
        }
        if (itemIndex > -1) {
            items.remove(itemIndex);
        } else {
            throw new NoTaskExistsException();
        }
    }

    // Clear the list
    void clear() {
        items.clear();
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public String getElementAt(int i) {
        return items.get(i);
    }

    @Override
    public void addListDataListener(ListDataListener ll) {
    }

    @Override
    public void removeListDataListener(ListDataListener ll) {
    }

    // Exception thrown if an empty string is added
    public static class AddEmptyStringException extends Exception {

        private final String message;

        public AddEmptyStringException() {
            message = "task can not be empty";
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    // Exception if a task already exists
    public static class TaskExistsException extends Exception {

        private final String message;

        public TaskExistsException() {
            message = "task already in list";
        }

        public TaskExistsException(String s) {
            message = s + " already in list";
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    // Exception thrown if a task is deleted that does not exist
    public static class NoTaskExistsException extends Exception {

        private final String message;

        public NoTaskExistsException() {
            message = "task can not be empty";
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

}
