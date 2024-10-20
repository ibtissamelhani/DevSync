
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Title</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
</head>
<body>
<jsp:include page="../../layouts/sideBar.jsp"/>
      <div class="p-4 sm:ml-64">
   <div class="p-4 border-2 border-gray-200 border-dashed rounded-lg dark:border-gray-700">

<section class="bg-white dark:bg-gray-900">
  <div>
    <div class="w-full max-w-3xl p-8 mx-auto lg:px-12">
      <div class="w-full">
        <h1 class="text-2xl font-semibold tracking-wider text-gray-800 capitalize dark:text-white">
          Update User.
        </h1>

        <form class="grid grid-cols-1 gap-6 mt-8 md:grid-cols-2" action="users?action=edit&id=${user.id}" method="POST">
          <div>
            <label class="block mb-2 text-sm text-gray-600 dark:text-gray-200">First Name</label>
            <input type="text" name="firstName" value="${user.firstName}"  class="block w-full px-5 py-3 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
          </div>

          <div>
            <label class="block mb-2 text-sm text-gray-600 dark:text-gray-200">Last name</label>
            <input type="text" value="${user.lastName}" name="lastName"  class="block w-full px-5 py-3 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
          </div>

          <div>
            <label class="block mb-2 text-sm text-gray-600 dark:text-gray-200">Email address</label>
            <input type="email" name="email" value="${user.email}" class="block w-full px-5 py-3 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
          </div>

          <div>
            <label class="block mb-2 text-sm text-gray-600 dark:text-gray-200">Role</label>
            <select name="role" class="block w-full px-5 py-3 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40">
              <option selected disabled>Choose a role</option>
              <option value="MANAGER" ${user.role == 'MANAGER' ? 'selected' : ''}>Manager</option>
              <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>User</option>
            </select>
          </div>
          <div>

          </div>

          <button type="submit"
                  class="mx-auto w-full px-6 py-3 text-sm tracking-wide text-white capitalize transition-colors duration-300 transform bg-blue-500 rounded-lg hover:bg-blue-400 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-50">
            <span>update </span>
          </button>
        </form>
      </div>
    </div>
  </div>
</section>
</body>
<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>

</html>
