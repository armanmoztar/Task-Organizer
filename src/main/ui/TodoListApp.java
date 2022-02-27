package ui;

import model.Category;
import model.Task;
import model.TaskList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoListApp {
    //class represents the user-interface of the TaskList application when ran
    private static final String JSON_STORE = "./data/todolist.json";
    private Scanner input;
    private TaskList todoList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Code referenced from TellerApp and JsonSerializationDemo repository
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: runs the TodoList application
    public TodoListApp() throws FileNotFoundException {
        this.input = new Scanner(System.in);
        this.todoList = new TaskList("Arman's Todo list");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTodoList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTodoList() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddTask();
        } else if (command.equals("d")) {
            doDeleteTask();
        } else if (command.equals("c")) {
            doCompleteTask();
        } else if (command.equals("i")) {
            doImportantTask();
        } else if (command.equals("v")) {
            doPrintList();
        } else if (command.equals("s")) {
            saveTaskList();
        } else if (command.equals("l")) {
            loadTaskList();
        } else {
            System.out.println("Selection is Not Valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the to-do list
    private void init() {
        todoList = new TaskList(todoList.getTaskListName());
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add Task");
        System.out.println("\td -> Delete Task");
        System.out.println("\tc -> Complete Task");
        System.out.println("\ti -> Mark Important");
        System.out.println("\tv -> View List");
        System.out.println("\ts -> Save to File");
        System.out.println("\tl -> Load from File");
        System.out.println("\tq -> QUIT");
    }

    // REQUIRES: task must be a string
    // MODIFIES: this
    // EFFECTS: adds a task
    private void doAddTask() {
        System.out.print("Enter Task to Add: ");
        String text = input.next();
        System.out.print("Days till Deadline: ");
        int dueIn = input.nextInt();
        Category category = selectCategory();
        todoList.addTask(new Task(text, category, dueIn));
        System.out.println("Task Added!");
    }

    //EFFECTS: prompts user to select category and returns it
    private Category selectCategory() {
        System.out.println("Select category of Task");
        int menuLabel = 1;
        for (Category c : Category.values()) {
            System.out.println(menuLabel + ": " + c);
            menuLabel++;
        }

        int menuSelection = input.nextInt();
        return Category.values()[menuSelection - 1];
    }

    // MODIFIES: this
    // EFFECTS: deletes a task
    private void doDeleteTask() {
        doPrintList();
        if (todoList.isEmpty()) {
            System.out.println("Please add a task First");
        } else {
            System.out.print("Type the Task Number to Delete Task: ");
            int remove = input.nextInt() - 1;
            todoList.removeTask(remove);
            System.out.println("Task Deleted");
        }
    }

    // MODIFIES: this
    // EFFECTS: marks a task as complete
    private void doCompleteTask() {
        if (todoList.isEmpty()) {
            System.out.println("Please add a Task First");
        } else {
            System.out.println("Type the Task Number to mark Complete: ");
            input.nextInt();
            System.out.println("Task Complete!");
        }
    }

    // MODIFIES: this
    // EFFECTS: marks a task as important
    private void doImportantTask() {
        if (todoList.isEmpty()) {
            System.out.println("Please add a task first");
        } else {
            System.out.println("Type the Task Number to Mark Important: ");
            input.nextInt();
            System.out.println("Task Marked Important");
        }
    }

    //MODIFIES: this
    //EFFECTS: prints all current tasks that are in the list
    private void doPrintList() {
        if (todoList.isEmpty()) {
            System.out.println("Task List is Empty");
        } else {
            System.out.println("Here is your list...");
            storeOrder(todoList);
        }
    }

    // EFFECTS: Stores the order (number) of the task in the list
    private void storeOrder(TaskList tl) {
        ArrayList<Task> list = tl.printArrayTaskList();
        for (Task t : list) {
            int number = t.getDueIn();
            String days = String.valueOf(number);
            System.out.println(t.getText() + " - days left: " + days);
        }
    }

    //EFFECTS: saves the taskList to file
    private void saveTaskList() {
        try {
            jsonWriter.open();
            jsonWriter.write(todoList);
            jsonWriter.close();
            System.out.println("Saved " + todoList.getTaskListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads taskList from file
    private void loadTaskList() {
        try {
            todoList = jsonReader.read();
            System.out.println("Loaded " + todoList.getTaskListName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}