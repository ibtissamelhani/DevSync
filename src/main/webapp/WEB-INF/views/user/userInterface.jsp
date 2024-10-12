<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>DevSync</title>
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
        <button
          class="inline-flex items-center justify-center gap-1.5 rounded border border-gray-200 bg-white px-5 py-3 text-gray-900 transition hover:text-gray-700 focus:outline-none focus:ring"
          type="button"
        >
          <span class="text-sm font-medium"> View Website </span>

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
        </button>

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
<div class="grid grid-cols-1 gap-4 lg:grid-cols-4 lg:gap-8 lg:px-8">
  <div class="grid gap-4 px-2 h-32 h-full rounded-lg bg-gray-100/40">
      <span class="relative flex justify-center">
        <div
          class="absolute inset-x-0 top-1/2 h-px -translate-y-1/2 bg-transparent bg-gradient-to-r from-transparent via-gray-500 to-transparent opacity-75"
        >
        </div>
        <span class="relative z-10 bg-gray-100/70 px-6">NOT_STARTED</span>
      </span>
      <c:forEach var="task" items="${tasks}">
        <c:if test="${task.status == 'NOT_STARTED'}">
        <a href="users?action=taskDetails&id=${task.id}" class="block h-full rounded-lg bg-white border border-gray-700 p-4 hover:bg-gray-100">
          <p class="font-sm text-black">${task.title}</p>

          <p class="mt-1 text-xs font-medium text-green-600">
          Created :
            ${task.creationDate}
          </p>
          <p class="mt-1 text-xs font-medium text-red-600">
                    Due :
                      ${task.dueDate}
          </p>
           <div class="grid grid-cols-4 items-center my-2 gap-2">
          <c:forEach var="tag" items="${task.tags}">
          <span class="whitespace-nowrap rounded-full bg-purple-100 px-2.5 py-0.5 text-sm text-purple-700">
            ${tag.name}
          </span>
          </c:forEach>
          </div>
        </a>
        </c:if>
      </c:forEach>
  </div>
  <div class="grid gap-4 px-2 h-32 h-full rounded-lg bg-gray-100/40">
        <span class="relative flex justify-center">
          <div
            class="absolute inset-x-0 top-1/2 h-px -translate-y-1/2 bg-transparent bg-gradient-to-r from-transparent via-gray-500 to-transparent opacity-75"
          >
          </div>
          <span class="relative z-10 bg-gray-100/70 px-6">IN_PROGRESS</span>
        </span>
        <c:forEach var="task" items="${tasks}">
          <c:if test="${task.status == 'IN_PROGRESS'}">
          <a href="users?action=taskDetails&id=${task.id}" class="block h-full rounded-lg bg-white border border-gray-700 p-4 hover:bg-gray-100">
            <p class="font-sm text-black">${task.title}</p>

             <p class="mt-1 text-xs font-medium text-green-600">
                      Created :
                        ${task.creationDate}
                      </p>
                      <p class="mt-1 text-xs font-medium text-red-600">
                                Due :
                                  ${task.dueDate}
                      </p>
             <div class="grid grid-cols-4 items-center my-2 gap-2">
            <c:forEach var="tag" items="${task.tags}">
            <span class="whitespace-nowrap rounded-full bg-purple-100 px-2.5 py-0.5 text-sm text-purple-700">
              ${tag.name}
            </span>
            </c:forEach>
            </div>
          </a>
          </c:if>
        </c:forEach>
    </div>
  <div class="grid gap-4 px-2 h-32 h-full rounded-lg bg-gray-100/40">
        <span class="relative flex justify-center">
          <div
            class="absolute inset-x-0 top-1/2 h-px -translate-y-1/2 bg-transparent bg-gradient-to-r from-transparent via-gray-500 to-transparent opacity-75"
          >
          </div>
          <span class="relative z-10 bg-gray-100/70 px-6">COMPLETED</span>
        </span>
        <c:forEach var="task" items="${tasks}">
          <c:if test="${task.status == 'COMPLETED'}">
          <a href="users?action=taskDetails&id=${task.id}" class="block h-full rounded-lg bg-white border border-gray-700 p-4 hover:bg-gray-100">
            <p class="font-sm text-black">${task.title}</p>

            <p class="mt-1 text-xs font-medium text-green-600">
                     Created :
                       ${task.creationDate}
                     </p>
                     <p class="mt-1 text-xs font-medium text-red-600">
                               Due :
                                 ${task.dueDate}
                     </p>
             <div class="grid grid-cols-4 items-center my-2 gap-2">
            <c:forEach var="tag" items="${task.tags}">
            <span class="whitespace-nowrap rounded-full bg-purple-100 px-2.5 py-0.5 text-sm text-purple-700">
              ${tag.name}
            </span>
            </c:forEach>
            </div>
          </a>
          </c:if>
        </c:forEach>
    </div>
  <div class="grid gap-4 px-2 h-32 h-full rounded-lg bg-red-100">
        <span class="relative flex justify-center">
          <div
            class="absolute inset-x-0 top-1/2 h-px -translate-y-1/2 bg-transparent bg-gradient-to-r from-transparent via-gray-500 to-transparent opacity-75"
          >
          </div>
          <span class="relative z-10 bg-gray-100/70 px-6">CANCELED</span>
        </span>
        <c:forEach var="task" items="${tasks}">
          <c:if test="${task.status == 'CANCELED'}">
          <a href="users?action=taskDetails&id=${task.id}" class="block h-full rounded-lg bg-white border border-gray-700 p-4 hover:bg-gray-100">
            <p class="font-sm text-black">${task.title}</p>
             <p class="mt-1 text-xs font-medium text-green-600">
                      Created :
                        ${task.creationDate}
                      </p>
                      <p class="mt-1 text-xs font-medium text-red-600">
                                Due :
                                  ${task.dueDate}
                      </p>
             <div class="grid grid-cols-4 items-center my-2 gap-2">
            <c:forEach var="tag" items="${task.tags}">
            <span class="whitespace-nowrap rounded-full bg-purple-100 px-2.5 py-0.5 text-sm text-purple-700">
              ${tag.name}
            </span>
            </c:forEach>
            </div>
          </a>
          </c:if>
        </c:forEach>
    </div>
</div>
</body>
</html>
