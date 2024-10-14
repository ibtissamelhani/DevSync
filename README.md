# 🗂️ Task Management System

## Overview

The Task Management System is a web application designed to help users efficiently manage their tasks. It allows users to create, assign, and track tasks while utilizing a token system to control task modifications. This application ensures that users adhere to specific rules regarding task management, enhancing productivity and organization.

## Features

### Version 1.1.0

- **Task Creation Restrictions**: Users cannot create tasks with past dates.
- **Tagging Requirements**: Multiple tags are required for task creation.
- **Task Scheduling Limit**: Tasks can only be scheduled up to 3 days in advance.
- **Completion Enforcement**: Tasks must be marked as completed before the due date.
- **Self-Assignment**: Users can assign additional tasks to themselves.
- **Token System**:
  - Users have **2 tokens per day** to replace tasks assigned by their manager.
  - Users have **1 token per month** for task deletion.
  - Deleting a task created by the same user does **not** affect their token balance.

## Technologies Used

- **Java**: The primary programming language used for backend development.
- **Jakarta EE**: Framework for building enterprise applications.
- **JPA (Java Persistence API)**: For database interactions.
- **PostgreSQL**: Relational database management system.
- **Maven**: Dependency management and build automation tool.


