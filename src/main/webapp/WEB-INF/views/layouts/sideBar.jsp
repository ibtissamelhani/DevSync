<%@ page import="org.example.model.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="top-0 left-0 h-screen flex flex-col justify-between border-e bg-white">
    <div class="px-4 py-6">
    <span class="grid h-10 w-32 place-content-center rounded-lg bg-gray-100 text-xs text-gray-600">
      DevSync
    </span>

        <ul class="mt-6 space-y-1">

            <li>
                <a
                        href="users?action=list"
                        class="block rounded-lg px-4 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                >
                    Users
                </a>
            </li>

        <li>
                        <a
                                href="tasks?action=list"
                        class="block rounded-lg px-4 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                        >
                            Tasks
                        </a>
                    </li>
                <li>
                                <a
                                        href="tags?action=list"
                                        class="block rounded-lg px-4 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                                >
                                    Tags
                                </a>
                            </li>
                            <li>
                                                            <a
                                                                    href="requests"
                                                                    class="block rounded-lg px-4 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                                                            >
                                                                Request
                                                            </a>
                                                        </li>
                             <li>
                                                            <a
                                                                    href="users?action=logout"
                                                                    class="block rounded-lg px-4 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-700"
                                                            >
                                                                Logout
                                                            </a>
                                                        </li>

        </ul>
    </div>
    <%
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
    %>

    <div class="sticky inset-x-0 bottom-0 border-t border-gray-100">
        <a href="#" class="flex items-center gap-2 bg-white p-4 hover:bg-gray-50">
            <img
                    alt=""
                    src="https://images.unsplash.com/photo-1600486913747-55e5470d6f40?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"
                    class="size-10 w-8 h-8 rounded-full object-cover"
            />

            <div>
                <p class="text-xs">
                    <strong class="block font-medium"><%= loggedUser.getFirstName() + " " + loggedUser.getLastName() %></strong>

                    <span> <%= loggedUser.getEmail() %> </span>
                </p>
            </div>
        </a>
    </div>
    <%
        }
    %>
</div>

