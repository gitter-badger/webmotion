<%--
  #%L
  WebMotion test
  
  $Id$
  $HeadURL$
  %%
  Copyright (C) 2011 - 2015 Debux
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as 
  published by the Free Software Foundation, either version 3 of the 
  License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Lesser Public License for more details.
  
  You should have received a copy of the GNU General Lesser Public 
  License along with this program.  If not, see
  <http://www.gnu.org/licenses/lgpl-3.0.html>.
  #L%
  --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>WebMotion</title>
    </head>

    <body>
        <h1>Notes</h1>
        <form method="GET" action="<c:url value="./create"/>">
            <textarea name="content" required="true"></textarea>
            <input type="submit" value="Create"/>
        </form>
            
        <table>
            <thead>
                <tr>
                    <td>Comment</td>
                    <td>Likes</td>
                    <td>Actions</td>
                </tr>
            </thead>
            <c:forEach items="${queryResult}" var="comment">
            <tr>
                <td><pre>${comment.content}</pre></td>
                <td>${comment.likes}</td>
                <td>
                    <a href="<c:url value="./incLike?id=${comment.id}"/>">Like</a> 
                    <a href="<c:url value="./delete?id=${comment.id}"/>">Delete</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
