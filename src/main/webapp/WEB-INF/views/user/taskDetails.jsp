<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>Title</title>
        <script src="https://cdn.tailwindcss.com"></script>
            <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
  </head>
  <body>

<header class="bg-white">
  <div class="mx-auto max-w-screen-xl px-4 py-8 sm:px-6 sm:py-12 lg:px-8">
    <div class="flex flex-col items-start gap-4 md:flex-row md:items-center md:justify-between">
      <div>
        <h1 class="text-2xl font-bold text-gray-900 sm:text-3xl">DevSync</h1>

        <p class="mt-1.5 text-sm text-gray-500">
          Your Next-Level Task Management Solution
        </p>
      </div>

      <div class="flex items-center gap-4">
        <a href="users?action=userInterface&id=${task.assignee.id}"
          class="inline-flex items-center justify-center gap-1.5 rounded border border-gray-200 bg-white px-5 py-3 text-gray-900 transition hover:text-gray-700 focus:outline-none focus:ring"
        >
          <span class="text-sm font-medium"> View All tasks </span>

          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="size-4"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            stroke-width="2"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"
            />
          </svg>
        </a>
      </div>
    </div>
  </div>
</header>
<div class="flow-root mx-10">
  <dl class="-my-3 divide-y divide-gray-100 text-sm">
    <div class="grid grid-cols-1 gap-1 py-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Title</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.title}</dd>
    </div>

    <div class="grid grid-cols-1 gap-1 py-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Start Date</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.creationDate}</dd>
    </div>

    <div class="grid grid-cols-1 gap-1 py-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Due Date</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.dueDate}</dd>
    </div>

    <div class="grid grid-cols-1 gap-1 py-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Status</dt>
      <dd class="text-gray-700 sm:col-span-2">
        <form action="tasks?action=editStatus" method="POST">
          <input type="hidden" name="task_id" value="${task.id}" />
          <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown"class="inline-flex items-center px-3 py-1 rounded-full gap-x-2 sm:col-span-2  w-min	 <c:choose>
                                                                               <c:when test="${task.status == 'NOT_STARTED'}">
                                                                                   bg-yellow-100/60 text-yellow-500 dark:bg-gray-800
                                                                               </c:when>
                                                                               <c:when test="${task.status == 'IN_PROGRESS'}">
                                                                                   bg-blue-100/60 text-blue-500 dark:bg-gray-800
                                                                               </c:when>
                                                                               <c:when test="${task.status == 'COMPLETED'}">
                                                                                   bg-green-100/60 text-green-500 dark:bg-gray-800
                                                                               </c:when>
                                                                               <c:when test="${task.status == 'CANCELED'}">
                                                                                   bg-red-100/60 text-red-500 dark:bg-gray-800
                                                                               </c:when>
                                                                               </c:choose>"  type="button">${task.status}
                                                                               <svg class="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">

          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
          </svg>
          </button>
              <!-- Dropdown menu -->
              <div id="dropdown" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                  <ul class="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownDefaultButton">
                    <li>
                      <button  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" type="submit" name="status" value="NOT_STARTED">NOT_STARTED</button>
                    </li>
                    <li>
                      <button  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" type="submit" name="status" value="IN_PROGRESS">IN_PROGRESS</button>
                    </li>
                    <li>
                      <button  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" type="submit" name="status" value="COMPLETED">COMPLETED</button>
                    </li>
                  </ul>
              </div>
        </form>
      </dd>
    </div>

    <div class="grid grid-cols-1 gap-1 py-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Description</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.description}
      </dd>
    </div>
    <div class="grid grid-cols-1 gap-1 py-3 sm:grid-cols-3 sm:gap-4">
          <dt class="font-medium text-gray-900">Tags</dt>
          <dd class="text-gray-700 sm:col-span-2"><c:forEach var="tag" items="${task.tags}">
           <span class="whitespace-nowrap rounded-full bg-purple-100 px-2.5 py-0.5 text-sm text-purple-700">
             ${tag.name}
           </span>
           </c:forEach>
          </dd>
    </div>
    <div class="grid grid-cols-1 gap-1 py-3 sm:grid-cols-3 sm:gap-4">
              <dt class="font-medium text-gray-900"></dt>
              <dd class="flex gap-4 text-gray-700 sm:col-span-2">
                <form action="requests?action=deleteTask&id=${task.id}" method="POST">
                    <input type="hidden" name="task_id" value="${task.id}" />
                    <button type="submit" class="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900">
                        Delete
                    </button>
                </form>
                <c:if test="${task.creator != task.assignee}">
                <button type="button" class="focus:outline-none text-white bg-purple-700 hover:bg-purple-800 focus:ring-4 focus:ring-purple-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-purple-600 dark:hover:bg-purple-700 dark:focus:ring-purple-900">Swap Task</button>
              </c:if>
              </dd>
    </div>
  </dl>
</div>
<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
  </body>
</html>
