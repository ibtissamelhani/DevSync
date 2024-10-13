<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>DevSync</title>
        <script src="${pageContext.request.contextPath}/assets/js/TaskValidation.js" type="text/javascript" defer></script>
    <script src="https://cdn.tailwindcss.com"></script>
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js" ></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet"/>
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
        <a href="users?action=logout"
          class="inline-flex items-center justify-center gap-1.5 rounded border border-gray-200 bg-white px-5 py-3 text-gray-900 transition hover:text-gray-700 focus:outline-none focus:ring"
        >
          <span class="text-sm font-medium"> Logout </span>

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


<!-- Modal toggle -->
<button data-modal-target="authentication-modal" data-modal-toggle="authentication-modal" class="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800" type="button">
  Self-Assign
</button>

<!-- Main modal -->
<div id="authentication-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-md max-h-full">
        <!-- Modal content -->
        <div class="relative bg-white w-full px-2 rounded-lg shadow dark:bg-gray-700">
            <!-- Modal header -->
            <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                    Take Task
                </h3>
                <button type="button" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="authentication-modal">
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                    </svg>
                    <span class="sr-only">Close modal</span>
                </button>
            </div>
            <!-- Modal body -->
            <div class="p-4 md:p-5">
                <form class="space-y-4" action="users?action=selfAssign" method="post">
                          <div>
                            <label class="" for="title">Title</label>
                            <input
                                    class="w-full rounded-lg border border-gray-300 p-3 text-sm"
                                    placeholder="Title"
                                    type="text"
                                    name="title"
                                    id="title"
                            />
                          </div>

                          <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
                            <div>
                              <label class="" for="creationDate">start Date</label>
                              <input
                                      class="w-full rounded-lg border border-gray-300 p-3 text-sm"
                                      placeholder="Start Date"
                                      type="date"
                                      name="creationDate"
                                      id="creationDate"
                              />
                            </div>

                            <div>
                              <label class="" for="end-date">due Date</label>
                              <input
                                      class="w-full rounded-lg border border-gray-300 p-3 text-sm"
                                      placeholder="End Date"
                                      type="date"
                                      name="dueDate"
                                      id="end-date"
                              />
                            </div>
                          </div>
                          <div>
                            <label class="" for="tag">Tags</label>
                            <select id="tag" class="js-example-basic-multiple w-60 rounded-lg border border-gray-300 p-3 text-sm" name="tags[]" multiple="multiple">
                              <c:forEach var="tag" items="${tags}">
                              <option value="${tag.id}">${tag.name}</option>
                              </c:forEach>
                           </select>
                          </div>

                          <div>
                            <label class="" for="Description">Description
                            <textarea
                                    class="w-full rounded-lg border border-gray-300 p-3 text-sm"
                                    placeholder="Description"
                                    rows="8"
                                    name="description"
                                    id="Description"
                            ></textarea>
                          </div>
                          <div class="mt-4">
                              <button
                                      type="submit"
                                      class="inline-block w-full rounded-lg bg-blue-500 px-5 py-3 font-medium text-white sm:w-auto"
                              >
                                Self-Assign
                              </button>
                          </div>
                </form>
            </div>
        </div>
    </div>
</div>

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
          <span class="relative z-10 bg-gray-100/70 px-6">OVERDUE</span>
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
<%
   String errorMessage = (String) request.getSession().getAttribute("errorMessage");
   request.getSession().removeAttribute("errorMessage");
%>


<%
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
    <script>
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "<%= errorMessage %>",
        });
    </script>
<%
    }
%>
 <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</body>
</html>
