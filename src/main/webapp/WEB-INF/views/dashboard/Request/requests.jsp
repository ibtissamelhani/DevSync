<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
  <head>
    <title>Title</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
  </head>
  <body>
  <jsp:include page="../../layouts/sideBar.jsp"    />
        <div class="p-4 sm:ml-64">
     <div class="p-4 border-2 border-gray-200 border-dashed rounded-lg dark:border-gray-700">
  <section class="container p-4 mx-auto">
  <h2 class="text-lg font-medium text-gray-800 dark:text-white">REQUEST</h2>
      <div class="flex flex-col">
          <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
              <div class="inline-block min-w-full py-2 align-middle md:px-6 lg:px-8">
                  <div class="overflow-hidden border border-gray-200 dark:border-gray-700 md:rounded-lg">
                      <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                          <thead class="bg-gray-50 dark:bg-gray-800">
                              <tr>
                                  <th scope="col" class="py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                      <div class="flex items-center gap-x-3">
                                          <button class="flex items-center gap-x-2">
                                              <span>N°</span>
                                          </button>
                                      </div>
                                  </th>

                                  <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                      Date
                                  </th>

                                  <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                      Status
                                  </th>

                                  <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                      User
                                  </th>
                                  <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                      Task
                                  </th>

                                  <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                      action
                                  </th>

                                  <th scope="col" class="relative py-3.5 px-4">
                                      <span class="sr-only">Actions</span>
                                  </th>
                              </tr>
                          </thead>
                          <tbody class="bg-white divide-y divide-gray-200 dark:divide-gray-700 dark:bg-gray-900">
                              <c:forEach var="request" items="${requests}">
                              <tr>
                                  <td class="px-4 py-4 text-sm font-medium text-gray-700 dark:text-gray-200 whitespace-nowrap">
                                      <div class="inline-flex items-center gap-x-3">
                                          <span>${request.id}</span>
                                      </div>
                                  </td>
                                  <td class="px-4 py-4 text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">${request.requestDate}</td>
                                  <td class="px-4 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                                       <div class="inline-flex items-center px-3 py-1 rounded-full gap-x-2
                                               ${request.status == 'APPROVED' ? 'text-emerald-500 bg-emerald-100/60 dark:bg-emerald-800' :
                                                 request.status == 'REJECTED' ? 'text-red-500 bg-red-100/60 dark:bg-red-800' :
                                                 request.status == 'PENDING' ? 'text-yellow-500 bg-yellow-100/60 dark:bg-yellow-800' :
                                                 'text-gray-500 bg-gray-100/60 dark:bg-gray-800'}">
                                         <h2 class="text-sm font-normal">${request.status}</h2>
                                      </div>
                                  </td>
                                  <td class="px-4 py-4 text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">
                                      <div class="flex items-center gap-x-2">
                                          <img class="object-cover w-8 h-8 rounded-full" src="https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80" alt="">
                                          <div>
                                              <h2 class="text-sm font-medium text-gray-800 dark:text-white ">${request.user.firstName} ${request.user.lastName}</h2>
                                          </div>
                                      </div>
                                  </td>
                                  <td class="px-4 py-4 text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">${request.task.title}</td>
                                  <td class="px-4 py-4 text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">${request.type}</td>
                                  <td class="px-4 py-4 text-sm whitespace-nowrap">
                                  <c:if test="${request.status == 'PENDING'}">
                                      <div class="flex items-center gap-x-6">
                                          <form action="requests?action=editStatus" method="post" class="flex gap-4  justify-center">
                                              <input type="hidden" name="requestId" value="${request.id}">
                                              <input type="hidden" name="status" value="APPROVED">
                                                <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown" class="text-green-500 transition-colors duration-200 dark:hover:text-indigo-500 dark:text-gray-300 hover:text-indigo-500 focus:outline-none" type="button">
                                                Accept
                                                </button>

                                                <!-- Dropdown menu -->
                                                <div id="dropdown" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                                                    <ul class="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownDefaultButton">
                                                     <c:forEach var="user" items="${users}">
                                                       <button type="submit" name="userId" value="${user.id}" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                                                          ${user.firstName} ${user.lastName}
                                                       </button>
                                                      </c:forEach>
                                                    </ul>
                                                </div>

                                          </form>
                                          <form action="requests?action=editStatus" method="post" class="flex gap-4  justify-center">
                                              <input type="hidden" name="requestId" value="${request.id}">
                                              <input type="hidden" name="userId" value="1">
                                              <button type="submit" name="status" value="REJECTED" class="text-red-500 transition-colors duration-200 hover:text-indigo-500 focus:outline-none">
                                                  Reject
                                              </button>
                                          </form>
                                      </div>
                                      </c:if>
                                  </td>
                              </tr>
                              </c:forEach>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
      </div>
  </section>
  <script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
  </body>
</html>
