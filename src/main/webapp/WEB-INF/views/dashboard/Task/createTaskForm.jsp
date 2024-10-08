<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>create task form</title>
    <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
  <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

</head>
<body class="flex items-center min-h-screen bg-gray-100">
<jsp:include page="../../layouts/sideBar.jsp"    />
<section class="mx-auto w-6/12">
      <div class="rounded-lg bg-white  p-8 shadow-lg lg:col-span-3">
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
              <label class="" for="creationDate">creation Date</label>
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
            <select id="tag" class="js-example-basic-multiple w-full rounded-lg border border-gray-300 p-3 text-sm" name="states[]" multiple="multiple">
              <option value="AL">Alabama</option>
              <option value="WY">Wyoming</option>
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

<script>
$(document).ready(function(){
$('.js-example-basic-multiple').select2();
 });
</script>
</body>

</html>
