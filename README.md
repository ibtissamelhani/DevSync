# 🗂️ Task Management System

## Overview
The **Task Management System** is a web application designed to help users efficiently manage their tasks. It allows users to create, assign, and track tasks while utilizing a token system to control task modifications. This application ensures that users adhere to specific rules regarding task management, enhancing productivity and organization.

## Features

### Version 1.2.0
- **Task Assignment by Managers:** 
  When a manager replaces a task, they must assign it to another user. This task cannot be modified or deleted using tokens.
  
- **Double Token Reward:** 
  If a manager does not respond to a task change request within 12 hours, the user will receive double the modification token balance the following day.
  
- **Automatic Task Update:** 
  Every 24 hours, tasks that are overdue and not marked as completed will automatically be marked as not done.
  
- **Manager Overview:** 
  Managers can view a summary of all tasks assigned to their employees, including a completion percentage .

### Version 1.1.0
- **Task Creation Restrictions:** 
  Users cannot create tasks with past dates.
  
- **Tagging Requirements:** 
  Multiple tags are required for task creation.
  
- **Task Scheduling Limit:** 
  Tasks can only be scheduled up to 3 days in advance.
  
- **Completion Enforcement:** 
  Tasks must be marked as completed before the due date.
  
- **Self-Assignment:** 
  Users can assign additional tasks to themselves.

- **Token System :**
- Users have **2 tokens per day** to replace tasks assigned by their manager.
- Users have **1 token per month** for task deletion.
- Deleting a task created by the same user does not affect their token balance.

## Technologies Used
- **Java:** The primary programming language used for backend development.
- **Jakarta EE:** Framework for building enterprise applications.
- **JPA (Java Persistence API):** For database interactions.
- **PostgreSQL:** Relational database management system.
- **Maven:** Dependency management and build automation tool.
  
## Testing
- **Mockito:** For mocking dependencies in unit tests.
- **JUnit:** For writing and executing unit tests.
- **JaCoCo:** For code coverage analysis.

## Installation

To set up the Task Management System locally, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/task-management-system.git
2. **Navigate to the project directory:**
   cd task-management-system
3. **Install dependencies**
   mvn install
4. **Configure database connection**
5. **Run the application**
   
