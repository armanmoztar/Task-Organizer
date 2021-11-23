#To-Do list Application
## A neat way to sort Deadlines

For this project, I have decided to go with a to-do list application, but adding my own different 
features/implementations. The application will allow a user to input their tasks and allow them 
to mark tasks as complete when finished. In addition,
they can mark tasks as "important," to be able to prioritize the more urgent tasks.
This to-do list can be used by anyone as means of 
staying organized and up-to-date with any assignments or tasks. It can be a useful tool 
especially for students to sort through assignments from most important to less. I myself am an
avid user of to-do list applications. I currently use Microsoft To Do, and 
so I'm interested in creating my own implementation of this application to see what I can come up with.

##User Stories

####Phase 1: 
- As a user, I want to be able to add a task with a deadline to my to-do list
- As a user, I want to be able to delete a task from my to-do-list
- As a user, I want to be able to view my tasks in the to-do list as an ordered list
- As a user, I want to be able to mark a task as complete on my to-do list
- As a user, I want to be able to mark tasks as **important**
- As a user, I want to be able to choose the category of the type of task in the to-do list

####Phase 2:
- As a user, I want to be able to save my to-do list to file,
- As a user, I want to be able to load my to-do list from file 

##Phase 4: Task 2
Mon Nov 22 11:33:05 PST 2021
Task Item added to List.

Mon Nov 22 11:33:12 PST 2021
Task Item added to List.

Mon Nov 22 11:33:14 PST 2021
Task Item removed from List.

Mon Nov 22 11:33:20 PST 2021
Task Item added to List.

##Phase 4: Task 3

- If given more time, I would’ve simplified my TaskListGUI class by making new classes in the ui package 
  for AddListener, DeleteListener, ImportantTaskListener, and CompleteTaskListener, instead of all being in one class.
- I could’ve made a new class in the ui package to represent the bottom JTextArea and its functions that rely on it
- I noticed there was poor cohesion and some coupling between some classes. I would have made a separate class 
  for the load and save methods in the TaskListGUI class. Even though they are part of the GUI, having them in a 
  separate class would increase the cohesion of the classes since loading and saving are somewhat 
  separate from adding/remove a task. 
- The same goes for marking important/complete. I would have made 
  separate classes for those instead of having all of their methods and functions 
  be part of the TodoListApp and TaskListGUI. Not only would this increase the cohesion between the classes, 
  but it would also reduce unnecessary coupling.
