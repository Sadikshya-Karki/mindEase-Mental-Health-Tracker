<%-- Topbar header: breadcrumb navigation and user info display --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%-- Resolve user display name from session, fallback to 'User' --%>
<c:set var="displayName" value="User" />
<c:if test="${not empty sessionScope.loggedUser and not empty sessionScope.loggedUser.name}">
    <c:set var="displayName" value="${fn:trim(sessionScope.loggedUser.name)}" />
</c:if>
<%-- Resolve page title from request attribute, fallback to 'Dashboard' --%>
<c:set var="resolvedPageTitle" value="${empty requestScope.pageTitle ? 'Dashboard' : requestScope.pageTitle}" />

<header class="topbar" id="topbar">
    <div class="topbar-left">
        <%-- Breadcrumb navigation: MindEase > current page --%>
        <nav class="breadcrumb">
            <span class="breadcrumb-root">MindEase</span>
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
            <div class="topbar-avatar">
                ${fn:substring(displayName, 0, 2)}
            </div>
        </div>
    </div>
</header>


