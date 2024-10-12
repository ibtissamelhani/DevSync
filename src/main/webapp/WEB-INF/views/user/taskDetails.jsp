<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>Title</title>
        <script src="https://cdn.tailwindcss.com"></script>
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

        <button
          class="inline-block rounded bg-indigo-600 px-5 py-3 text-sm font-medium text-white transition hover:bg-indigo-700 focus:outline-none focus:ring"
          type="button"
        >
          Create Post
        </button>
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
          <dd class="inline-flex items-center px-3 py-1 rounded-full gap-x-2 sm:col-span-2  w-min	 <c:choose>
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
  </dl>
</div>
  </body>
</html>
