package ui;

import model.Category;
import model.EventLog;
import model.Event;
import model.Task;
import model.TaskList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// Code referenced from SimpleTableSelectionDemo Project from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// Creates the GUI for the To-do List Application
public class TaskListGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/todolist.json";

    private DefaultTableModel listModel;
    private JLabel label;
    private JLabel bottomLabel;
    private JTable tableView;
    private JTextArea output;

    private JButton addButton;
    private JButton deleteButton;
    private JButton completeButton;
    private JButton importantButton;
    private JButton loadButton;
    private JButton saveButton;

    private TaskList todoList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: Creates frame, table, and labels for application
    public TaskListGUI() {
        super("Todo List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(850, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(30, 43, 43, 43));
        setLayout(new FlowLayout());

        todoList = new TaskList("");
        listModel = new DefaultTableModel();
        tableView = new JTable(listModel);
        listModel.addColumn("Task");
        listModel.addColumn("Category");
        listModel.addColumn("Deadline (in Days)");
        tableView.setPreferredScrollableViewportSize(new Dimension(800, 140));
        tableView.setFillsViewportHeight(true);
        tableView.setOpaque(false);
        add(new JScrollPane(tableView));
        label = new JLabel("To get started, enter a task...");
        label.setFont(new Font("Monaco", Font.PLAIN, 12));
        label.setSize(40, 30);

        bottomLabel = new JLabel("Task Organizer: See Completed or Important Tasks");
        bottomLabel.setFont(new Font("Monaco", Font.PLAIN, 13));
        label.setSize(40, 30);
        guiElementAdder();

    }

    //EFFECTS: Calls all listed methods (to simply
    public void guiElementAdder() {
        addTaskButton();
        deleteTaskButton();
        importantTaskButton();
        completeTaskButton();
        loadFileButton();
        saveFileButton();
        add(label, SwingConstants.CENTER);
        add(bottomLabel);
        addOutput();
        buttonPanel();
        printLog();
    }

    //MODIFIES: this
    //EFFECTS: Creates the bottom jTextArea for complete and important tasks
    public void addOutput() {
        output = new JTextArea(10, 40);
        output.setBackground(new Color(225, 250, 253));
        output.setEditable(false);
        add(new JScrollPane(output));
    }

    //MODIFIES: this
    //EFFECTS: Creates an add task button for interface
    public void addTaskButton() {
        addButton = new JButton("Add Task");
        AddListener addListener = new AddListener(addButton);
        addButton.addActionListener(addListener);
        addButton.setEnabled(true);
    }

    //MODIFIES: this
    //EFFECTS: Creates a delete task button for interface
    public void deleteTaskButton() {
        deleteButton = new JButton("Delete Task");
        deleteButton.setActionCommand("Delete Task");
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(true);
    }

    //MODIFIES: this
    //EFFECTS: Creates an important task button for interface
    public void importantTaskButton() {
        importantButton = new JButton("Mark Important");
        importantButton.setActionCommand("Mark Important");
        importantButton.addActionListener(new ImportantTaskListener());
        importantButton.setEnabled(true);
    }

    //MODIFIES: this
    //EFFECTS: Creates a complete task button for interface
    public void completeTaskButton() {
        completeButton = new JButton("Mark Complete");
        completeButton.setActionCommand("Mark Complete");
        completeButton.addActionListener(new CompleteTaskListener());
        completeButton.setEnabled(true);
    }

    //MODIFIES: this
    //EFFECTS: Creates a load file button for interface
    public void loadFileButton() {
        loadButton = new JButton("Load File");
        loadButton.setActionCommand("Load File");
        loadButton.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: Creates a save file button for interface
    public void saveFileButton() {
        saveButton = new JButton("Save File");
        saveButton.setActionCommand("Save File");
        saveButton.addActionListener(this);
    }

    //EFFECTS: Adds a button panel with all buttons to the interface
    public void buttonPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(3, 2));
        buttonPane.add(addButton);
        buttonPane.add(importantButton);
        buttonPane.add(completeButton);
        buttonPane.add(deleteButton);
        buttonPane.add(loadButton);
        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        add(buttonPane, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // Creates instructions for program on what tasks to execute
    // after "Add Task" has been clicked
    class AddListener implements ActionListener {
        private JTextField field;
        private int pane;
        private Integer[] days = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        private String[] categoryItems = {"PERSONAL", "SCHOOL", "WORK"};
        private JComboBox category;
        private JComboBox deadline;
        private HashMap<String, Category> map = new HashMap<>();
        private JButton button;

        //EFFECTS: Assigns button from field to parameter
        public AddListener(JButton button) {
            this.button = button;
        }

        //MODIFIES: this
        //EFFECTS: Creates a JPanel for the addition of a task to the table
        public void addTaskPanel() {
            this.field = new JTextField(32);
            add(field);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Task:"));
            myPanel.add(field);
            myPanel.add(Box.createHorizontalStrut(20)); // a spacer
            myPanel.add(new JLabel("Category:"));
            category = new JComboBox<>(categoryItems);
            myPanel.add(category);

            myPanel.add(new JLabel("Deadline (in Days):"));
            deadline = new JComboBox<>(days);
            myPanel.add(deadline);

            this.pane = JOptionPane.showConfirmDialog(null, myPanel,
                    "Add Task", JOptionPane.OK_CANCEL_OPTION);
        }

        //MODIFIES: this
        //EFFECTS: Gets field value, category, and deadline from panel and adds to table and TaskList
        public void actionPerformed(ActionEvent event) {
            addTaskPanel();
            String command = event.getActionCommand();
            if (this.pane == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if ("Add Task" == command) {
                listModel.addRow(new Object[]{field.getText(), category.getSelectedItem(), deadline.getSelectedItem()});
                label.setText("Task Added");

                //For storing value in TaskList to save/load
                map.put("PERSONAL", Category.PERSONAL);
                map.put("SCHOOL", Category.SCHOOL);
                map.put("WORK", Category.WORK);
                String cat = (String) category.getSelectedItem();
                Category returnCat = map.get(cat);
                int dueIn = (Integer) deadline.getSelectedItem();
                Task t = new Task(field.getText(), returnCat, dueIn);
                todoList.addTask(t);
            }
        }
    }

    // Creates instructions for program on what tasks to execute
    // after "Delete Task" has been clicked
    class DeleteListener implements ActionListener {
        //REQUIRES: A task to be selected
        //MODIFIES: this
        //EFFECTS: Removes a task from the table
        public void actionPerformed(ActionEvent event) {
            int getSelectedRowForDeletion = tableView.getSelectedRow();
            if (getSelectedRowForDeletion >= 0) {
                listModel.removeRow(getSelectedRowForDeletion);
                todoList.removeTask(getSelectedRowForDeletion);
                label.setText("Task Deleted");
            } else {
                label.setText("Unable to Delete - No Task Selected");
            }
        }
    }

    // Creates instructions for program on what tasks to execute
    // after "Mark Important" has been clicked
    class ImportantTaskListener implements ActionListener {
        //REQUIRES: A task to be selected
        //MODIFIES: this
        //EFFECT: Gets selected Task in table, deletes it from table, and moves it to the output
        public void actionPerformed(ActionEvent event) {
            int getSelectedRowForMarkImportant = tableView.getSelectedRow();
            if (getSelectedRowForMarkImportant >= 0) {
                String a = (String) listModel.getValueAt(tableView.getSelectedRow(), 0);
                String b = (String) listModel.getValueAt(tableView.getSelectedRow(), 1);
                int c = (int) listModel.getValueAt(tableView.getSelectedRow(), 2);
                listModel.removeRow(getSelectedRowForMarkImportant);
                output.append(a + " - " + b + " - " + c + " = IMPORTANT " + "\n");
                label.setText("Task Marked Important");
            } else {
                label.setText("Unable to Mark Important - No Task Selected");
            }
        }
    }

    // Creates instructions for program on what tasks to execute
    // after "Mark Complete" has been clicked
    class CompleteTaskListener implements ActionListener {
        //REQUIRES: A task to be selected
        //MODIFIES: this
        //EFFECT: Gets selected Task in table, deletes it from table, and adds it to the output
        public void actionPerformed(ActionEvent event) {
            int getSelectedRowForMarkComplete = tableView.getSelectedRow();
            if (getSelectedRowForMarkComplete >= 0) {
                String a = (String) listModel.getValueAt(tableView.getSelectedRow(), 0);
                String b = (String) listModel.getValueAt(tableView.getSelectedRow(), 1);
                int c = (int) listModel.getValueAt(tableView.getSelectedRow(), 2);
                listModel.removeRow(getSelectedRowForMarkComplete);
                output.append(a + " - " + b + " - " + c + " = COMPLETE " + "\n");
                label.setText("Task Marked Complete");
            } else {
                label.setText("Unable to Mark Complete - No Task Selected");
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: saves file upon click, or loads file upon click
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save File")) {
            jsonWriter = new JsonWriter(JSON_STORE);
            try {
                jsonWriter.open();
                jsonWriter.write(todoList);
                jsonWriter.close();
                label.setText("File saved to: " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                label.setText("Unable to Save File from: " + JSON_STORE);
            }
        }
        if (e.getActionCommand().equals("Load File")) {
            jsonReader = new JsonReader(JSON_STORE);
            listModel.setRowCount(0);
            output.removeAll();
            try {
                todoList = jsonReader.read();
                label.setText("File Loaded from: " + JSON_STORE);
                loadTasksToTable(listModel);
            } catch (IOException exception) {
                label.setText("Unable to Load File from: " + JSON_STORE);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Loads the stored tasks and values from file onto the rows of table
    public void loadTasksToTable(DefaultTableModel d) {
        ArrayList<Task> tasks = todoList.printArrayTaskList();
        for (Task t : tasks) {
            String getTask = t.getText();
            String getCategory = String.valueOf(t.getCategory());
            int getDueIn = t.getDueIn();
            d.addRow(new Object[]{getTask, getCategory, getDueIn});
        }
    }

    // Code referenced from:
    // https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
    //EFFECTS: prints all events called onto the console
    public void printLog() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EventLog eventLog = todoList.getEventLog();
                for (Event e : eventLog) {
                    System.out.println(e.toString() + "\n");

                }
                System.exit(0);
            }
        });
    }
}