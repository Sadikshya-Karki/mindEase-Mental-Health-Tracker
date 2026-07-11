<%-- Admin topbar include: shared page context and account summary --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%-- Use the user topbar for non-admin sessions to keep the shared include safe --%>
<c:choose>
    <c:when test="${not empty sessionScope.loggedUser and not empty sessionScope.loggedUser.role and fn:toLowerCase(sessionScope.loggedUser.role) ne 'admin'}">
        <jsp:include page="/WEB-INF/views/user/includes/topbar.jsp" />
    </c:when>
    <c:otherwise>
        <%-- Resolve a safe display name for the signed-in admin user --%>
        <c:set var="displayName" value="Admin" />
        <c:if test="${not empty sessionScope.loggedUser and not empty sessionScope.loggedUser.name}">
            <c:set var="displayName" value="${fn:trim(sessionScope.loggedUser.name)}" />
        </c:if>
        <c:set var="resolvedPageTitle" value="${empty requestScope.pageTitle ? 'Dashboard' : requestScope.pageTitle}" />

        <%-- Topbar shows the current admin section and account summary --%>
        <header class="topbar" id="topbar">
            <div class="topbar-left">
                <nav class="breadcrumb">
                    <span class="breadcrumb-root">Admin</span>
                    <span class="breadcrumb-sep">&rsaquo;</span>
                    <span class="breadcrumb-current">${resolvedPageTitle}</span>
                </nav>
            </div>
            <div class="topbar-right">
                <div class="topbar-user">
                    <div class="topbar-user-info">
                        <span class="topbar-user-name">${displayName}</span>
                        <span class="topbar-user-role">${sessionScope.loggedUser.role}</span>
                    </div>
                    <div class="topbar-avatar">${fn:substring(displayName, 0, 2)}</div>
                </div>
            </div>
        </header>
    </c:otherwise>
</c:choose>