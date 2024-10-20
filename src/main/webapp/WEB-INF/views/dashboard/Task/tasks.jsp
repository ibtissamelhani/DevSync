
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body>
<jsp:include page="../../layouts/sideBar.jsp"    />

<div class="p-4 sm:ml-64">
      <div class="grid grid-cols-3 gap-4 mb-4">
         <div class="flex flex-col items-center justify-center h-24 rounded-lg bg-yellow-50  dark:bg-gray-800">
             <dt class="order-last text-md font-medium text-gray-500">Not Started Tasks</dt>
             <dd class="text-md font-extrabold text-yellow-600 md:text-5xl"><fmt:formatNumber value="${notStartPercent}" maxFractionDigits="1"/> %</dd>
         </div>
         <div class="flex flex-col items-center justify-center h-24 rounded-lg bg-blue-50  dark:bg-gray-800">
             <dt class="order-last text-md font-medium text-gray-500">In Progress Tasks</dt>
             <dd class="text-md font-extrabold text-blue-600 md:text-5xl"><fmt:formatNumber value="${inProgPercent}" maxFractionDigits="1"/> %</dd>
         </div>
         <div class="flex flex-col items-center justify-center h-24 rounded-lg bg-green-50  dark:bg-gray-800">
             <dt class="order-last text-md font-medium text-gray-500">Completed Tasks</dt>
             <dd class="text-md font-extrabold text-green-600 md:text-5xl"><fmt:formatNumber value="${compPercent}" maxFractionDigits="1"/> %</dd>
         </div>
      </div>
   <div class="p-4 border-2 border-gray-200 border-dashed rounded-lg dark:border-gray-700">

<section class="px-11 m-auto">
    <div class="sm:flex sm:items-center sm:justify-between">
            <h2 class="text-lg font-medium text-gray-800 dark:text-white">Tasks</h2>

            <div class="flex items-center mt-4 gap-x-3">
                <a href="tasks?action=create" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Add Task</a>
            </div>
    </div>
    <div class="flex flex-col mt-6">
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
                                Title
                            </th>

                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Status
                            </th>

                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Assignee
                            </th>
                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                Purchase
                            </th>
                        </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200 dark:divide-gray-700 dark:bg-gray-900">
                        <c:forEach var="task" items="${tasks}">
                        <tr>
                            <td class="px-4 py-4 text-sm font-medium text-gray-700 dark:text-gray-200 whitespace-nowrap">
                                <div class="inline-flex items-center gap-x-3">
                                    <span>#${task.id}</span>
                                </div>
                            </td>
                            <td class="px-4 py-4 text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">${task.title}</td>
                            <td class="px-4 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                                <div class="inline-flex items-center px-3 py-1 rounded-full gap-x-2
                                            <c:choose>
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
                                            </c:choose>">
                                    <h2 class="text-xs font-normal">${task.status}</h2>
                                </div>
                            </td>
                            <td class="px-4 py-4 text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">
                                <div class="flex items-center gap-x-2">
                                     <c:choose>
                                         <c:when test="${not empty task.assignee}">
                                    <img class="object-cover w-8 h-8 rounded-full" src="https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80" alt="">
                                    <div>

                                        <h2 class="text-sm font-medium text-gray-800 dark:text-white ">${task.assignee.firstName} ${task.assignee.lastName}</h2>
                                    </c:when>
                                <c:otherwise>
                                        <h2 class="text-xs font-medium text-red-400/50 dark:text-white ">Not Assigned</h2>
                                        </c:otherwise>
                                    </c:choose>
                                    </div>
                                </div>
                            </td>
                            <td class="px-4 py-4 text-sm whitespace-nowrap">
                                <div class="flex items-center gap-x-6">
                                    <a href="tasks?action=details&id=${task.id}" class="text-gray-500 transition-colors duration-200 dark:hover:text-indigo-500 dark:text-gray-300 hover:text-indigo-500 focus:outline-none">
                                        <span class="material-symbols-outlined">
                                        visibility
                                        </span>
                                    </a>
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
</body>
</html>

