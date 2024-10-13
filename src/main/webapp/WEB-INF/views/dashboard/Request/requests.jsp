<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
  <head>
    <title>Title</title>
        <script src="https://cdn.tailwindcss.com"></script>
  </head>
  <body class="flex">
  <jsp:include page="../../layouts/sideBar.jsp"    />
  <section class="container p-4 mx-auto">
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
                                  <td class="px-4 py-4 text-sm text-red-500 dark:text-gray-300 whitespace-nowrap">${request.type}</td>
                                  <td class="px-4 py-4 text-sm whitespace-nowrap">

                                      <div class="flex items-center gap-x-6">
                                          <c:if test="${request.status!=PENDING }">
                                          <form action="requests?action=editStatus" method="post" class="flex gap-4  justify-center">
                                              <input type="hidden" name="requestId" value="${request.id}">  <!-- Ensure you have the request ID here -->
                                              <button type="submit" name="status" value="APPROVED" class="text-green-500 transition-colors duration-200 dark:hover:text-indigo-500 dark:text-gray-300 hover:text-indigo-500 focus:outline-none">
                                                  Accept
                                              </button>

                                              <button type="submit" name="status" value="REJECTED" class="text-red-500 transition-colors duration-200 hover:text-indigo-500 focus:outline-none">
                                                  Reject
                                              </button>
                                          </form>
                                          </c:if>
                                      </div>
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
  </body>
</html>
