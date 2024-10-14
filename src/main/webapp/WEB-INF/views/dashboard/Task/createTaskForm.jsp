<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>create task form</title>
    <script src="${pageContext.request.contextPath}/assets/js/TaskValidation.js" type="text/javascript" defer></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js" ></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet"/>
</head>
<body class="flex items-center min-h-screen bg-gray-100">
<jsp:include page="../../layouts/sideBar.jsp"    />
<section class="mx-auto w-6/12">
      <div class="rounded-lg bg-white  p-8 shadow-lg lg:col-span-3 my-4">
        <form class="space-y-4" action="tasks?action=create" method="post">
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
              <label for="HeadlineAct" class="">Assignee </label>
              <select
                name="assignee_id"
                id="HeadlineAct"
                class="w-full rounded-lg border border-gray-300 p-3 text-sm"
              >
              <option value="" selected disabled>Assign to ...</option>
              <c:forEach var="user" items="${users}">
                <option value="${user.id}">${user.firstName} ${user.lastName}</option>
              </c:forEach>
              </select>
            </div>
          <div>
            <label class="" for="tag">Tags</label>
            <select id="tag" class="js-example-basic-multiple w-full rounded-lg border border-gray-300 p-3 text-sm" name="tags[]" multiple="multiple">
              <c:forEach var="tag" items="${tags}">
              <option value="${tag.id}">${tag.name}</option>
              </c:forEach>
            </select>
          </div>

          <div>
            <label class="" for="Description">Description</label>

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
              create task
            </button>
          </div>
        </form>
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
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</body>

</html>
