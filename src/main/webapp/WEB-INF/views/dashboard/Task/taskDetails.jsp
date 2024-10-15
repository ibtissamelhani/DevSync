<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
                <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="flex">
<jsp:include page="../../layouts/sideBar.jsp"    />
<div class="flow-root w-1/2 rounded-lg border border-gray-100 py-3 shadow-sm m-6">
  <dl class="-my-3 divide-y divide-gray-100 text-sm">
    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Title</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.title}</dd>
    </div>

    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Assignee</dt>
      <c:choose>
      <c:when test="${not empty task.assignee}">
      <dd class="text-gray-700 sm:col-span-2">${task.assignee.firstName} ${task.assignee.lastName}</dd>
      </c:when>
      <c:otherwise>
            <dd class="text-red-700 sm:col-span-2">Not Assigned</dd>
      </c:otherwise>
       </c:choose>
    </div>

    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Start Date</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.creationDate}</dd>
    </div>

    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Due Date</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.dueDate}</dd>
    </div>
    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
          <dt class="font-medium text-gray-900">Status</dt>
          <dd class="inline-flex items-center px-3 py-1 rounded-full gap-x-2 sm:col-span-2 w-min  <c:choose>
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
             </c:choose>">${task.status}</dd>
    </div>
    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
              <dt class="font-medium text-gray-900">Tags</dt>
              <dd class="text-gray-700 sm:col-span-2">
              <c:forEach var="tag" items="${task.tags}">
               <span class="whitespace-nowrap rounded-full bg-purple-100 px-2.5 py-0.5 text-sm text-purple-700">
                 ${tag.name}
               </span>
               </c:forEach>
              </dd>
    </div>
    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
      <dt class="font-medium text-gray-900">Description</dt>
      <dd class="text-gray-700 sm:col-span-2">${task.description}
      </dd>
    </div>
    <div class="grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4">
          <dt class="font-medium text-gray-900"></dt>
          <dd class="text-gray-700 sm:col-span-2">
                <form action="tasks?action=delete&id=${task.id}" method="POST">
                    <input type="hidden" name="task_id" value="${task.id}" />
                    <button type="submit" class="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900">
                        Delete
                    </button>
                </form>
          </dd>
    </div>
  </dl>
</div>
<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
  </body>
</html>
