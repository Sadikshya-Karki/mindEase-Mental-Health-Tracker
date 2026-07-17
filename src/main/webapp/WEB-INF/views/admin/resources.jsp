<%-- Admin resources page: manage published content, filters, and edits --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/logo.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Resources - MindEase Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<%-- Store the context path once for cleaner link generation --%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="page-wrapper">
    <%-- Shared admin sidebar for primary navigation --%>
    <jsp:include page="/WEB-INF/views/admin/includes/sidebar.jsp"/>
    <div class="main-area">
        <%-- Shared admin topbar for page context and account info --%>
        <jsp:include page="/WEB-INF/views/admin/includes/topbar.jsp"/>
        <main class="content">
            <%-- Page header: clarifies resource management scope --%>
            <div class="resources-header">
                <div class="resources-title">
                    <h1>Manage Resources</h1>
                    <p>Manage external mental health articles, guides, and videos.</p>
                </div>
                <button id="openAddModal" type="button" class="add-resource-btn">Add New Resource</button>
            </div>

            <%-- Show status feedback only when the controller reports a result --%>
            <c:if test="${param.success == 'created'}"><div class="alert-success">Resource created successfully.</div></c:if>
            <c:if test="${param.success == 'updated'}"><div class="alert-success">Resource updated successfully.</div></c:if>
            <c:if test="${param.success == 'deleted'}"><div class="alert-success">Resource deleted.</div></c:if>
            <c:if test="${not empty error}"><div class="alert-error">${error}</div></c:if>

            <%-- Filters preserve the current search and selection state --%>
            <div class="filter-card">
                <form method="get" action="${ctx}/admin/resources" id="filterForm">
                    <div class="filter-row">
                        <div class="search-wrap">
                            <input type="text" class="search-input" id="searchInput" name="search" value="${currentSearch}" placeholder="Search by title or tags...">
                        </div>
                        <select name="categoryId" class="filter-select category-select">
                            <option value="">All Categories</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryId}" <c:if test="${currentCategoryId == cat.categoryId}">selected</c:if>>${cat.name}</option>
                            </c:forEach>
                        </select>
                        <select name="status" class="filter-select status-select">
                            <option value="">All Status</option>
                            <option value="published" <c:if test="${currentStatus == 'published'}">selected</c:if>>published</option>
                            <option value="draft" <c:if test="${currentStatus == 'draft'}">selected</c:if>>draft</option>
                        </select>
                        <c:if test="${not empty currentSearch or not empty currentStatus or currentCategoryId != null}">
                            <a href="${ctx}/admin/resources" class="clear-filter-link">Clear</a>
                        </c:if>
                        <span class="result-count">${fn:length(resources)} resource(s) found</span>
                    </div>
                </form>
            </div>

            <%-- Loop through resources provided by the controller --%>
            <div class="resource-table-card table-responsive">
                <table class="resource-table">
                    <thead>
                    <tr>
                        <th>Thumbnail</th>
                        <th>Title &amp; Description</th>
                        <th>Category</th>
                        <th>Author</th>
                        <th>Status</th>
                        <th>Read Time</th>
                        <th>Tags</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty resources}">
                            <tr>
                                <td colspan="8" style="text-align:center;padding:48px;color:#9ca3af;">
                                    <div style="margin-bottom:12px;"><svg width="38" height="38" viewBox="0 0 24 24" fill="none" stroke="#9ca3af" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 12h-6l-2 3h-4l-2-3H2"></path><path d="M5.45 5.11L2 12v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2v-6l-3.45-6.89A2 2 0 0 0 16.76 4H7.24a2 2 0 0 0-1.79 1.11z"></path></svg></div>
                                    <div style="font-weight:600;color:#6b7280;">No resources found</div>
                                    <div style="font-size:0.85rem;margin-top:6px;">Try adjusting your filters or add a new resource.</div>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="r" items="${resources}">
                                <tr>
                                    <td class="thumb-cell">
                                        <c:choose>
                                            <c:when test="${not empty r.imageUrl}"><img src="${r.imageUrl}" alt="thumb" class="thumb-img"></c:when>
                                            <c:otherwise><div class="thumb-placeholder"><svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#2b7a78" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline></svg></div></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="max-width:200px;">
                                        <div class="title-main"><c:choose><c:when test="${fn:length(r.title) > 50}">${fn:substring(r.title, 0, 50)}...</c:when><c:otherwise>${r.title}</c:otherwise></c:choose></div>
                                        <div class="title-sub"><c:choose><c:when test="${empty r.description}">—</c:when><c:when test="${fn:length(r.description) > 70}">${fn:substring(r.description, 0, 70)}...</c:when><c:otherwise>${r.description}</c:otherwise></c:choose></div>
                                        <c:if test="${not empty r.url}"><a href="${r.url}" target="_blank" class="source-link">View Source</a></c:if>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty r.categoryName}"><span class="badge" style="background:#e6f4f3;color:#2b7a78;">${r.categoryName}</span></c:when>
                                            <c:otherwise><span style="color:#9ca3af;">—</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="font-size:0.85rem;color:#374151;">${r.authorName}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${r.status == 'published'}"><span class="badge badge-published">published</span></c:when>
                                            <c:otherwise><span class="badge badge-draft">draft</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="color:#6b7280;font-size:0.82rem;">${not empty r.readTime ? r.readTime : '—'}</td>
                                    <td style="max-width:140px;">
                                        <c:choose>
                                            <c:when test="${not empty r.tags}">
                                                <div class="micro-tag-wrap">
                                                    <c:forEach var="tag" items="${fn:split(r.tags, ',')}"><span class="tag-micro">${fn:trim(tag)}</span></c:forEach>
                                                </div>
                                            </c:when>
                                            <c:otherwise>—</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="action-cell action-buttons">
                                        <button type="button" class="btn btn-outline edit-resource-btn"
                                                data-id="${r.resourceId}"
                                                data-title="${fn:escapeXml(r.title)}"
                                                data-description="${fn:escapeXml(r.description)}"
                                                data-url="${fn:escapeXml(r.url)}"
                                                data-imageurl="${fn:escapeXml(r.imageUrl)}"
                                                data-categoryid="${r.categoryId}"
                                                data-status="${r.status}"
                                                data-readtime="${fn:escapeXml(r.readTime)}"
                                                data-tags="${fn:escapeXml(r.tags)}">Edit</button>
                                        <form method="post" action="${ctx}/admin/resources" class="inline-form">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="resourceId" value="${r.resourceId}">
                                            <button type="submit" class="btn btn-danger delete-resource-btn">Delete</button>
                                        </form>
                                        <c:choose>
                                            <c:when test="${r.status == 'published'}">
                                                <form method="post" action="${ctx}/admin/resources" class="inline-form">
                                                    <input type="hidden" name="action" value="draft">
                                                    <input type="hidden" name="resourceId" value="${r.resourceId}">
                                                    <button type="submit" class="btn btn-warning">Set Draft</button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form method="post" action="${ctx}/admin/resources" class="inline-form">
                                                    <input type="hidden" name="action" value="publish">
                                                    <input type="hidden" name="resourceId" value="${r.resourceId}">
                                                    <button type="submit" class="btn btn-success">Publish</button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>

<%-- Modal keeps create and edit flows in one place for easier admin maintenance --%>
<div id="resourceModal">
    <div class="resource-modal-box">
        <div class="modal-header">
            <h2 id="modalHeading">Add New Resource</h2>
            <button type="button" id="closeModal" class="modal-close">&#x2715;</button>
        </div>
        <form id="resourceForm" method="post" action="${ctx}/admin/resources">
            <input type="hidden" name="action" id="formAction" value="create">
            <input type="hidden" name="resourceId" id="formResourceId">
            <div class="form-grid-two">
                <div class="form-group form-col-full">
                    <label>Resource Title *</label>
                    <input type="text" class="form-control" name="title" id="fTitle" required placeholder="e.g. Understanding Anxiety">
                </div>
                <div class="form-group form-col-full">
                    <label>Description</label>
                    <textarea class="form-control" name="description" id="fDescription" rows="3" placeholder="Brief description of this resource..."></textarea>
                </div>
                <div class="form-group">
                    <label>Resource URL *</label>
                    <input type="text" class="form-control" name="url" id="fUrl" required placeholder="https://example.com/article">
                    <div class="form-note">Link users will be redirected to</div>
                </div>
                <div class="form-group">
                    <label>Thumbnail Image URL</label>
                    <input type="text" class="form-control" name="imageUrl" id="fImageUrl" placeholder="https://images.unsplash.com/...">
                    <div class="form-note">Leave empty for default thumbnail</div>
                </div>
                <div class="form-group">
                    <label>Category</label>
                    <select class="form-control" name="categoryId" id="fCategoryId">
                        <option value="">Select Category</option>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.categoryId}">${cat.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Status</label>
                    <select class="form-control" name="status" id="fStatus">
                        <option value="published">Published</option>
                        <option value="draft">Draft</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Read Time</label>
                    <input type="text" class="form-control" name="readTime" id="fReadTime" placeholder="e.g. 5 min read">
                </div>
                <div class="form-group">
                    <label>Author</label>
                    <input type="text" class="form-control" value="${sessionScope.loggedUser.name}" disabled>
                </div>
                <div class="form-group form-col-full">
                    <label>Tags</label>
                    <input type="text" class="form-control" name="tags" id="fTags" placeholder="e.g. anxiety, stress, sleep">
                    <div class="form-note">Comma-separated tags for filtering</div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="cancelModal" class="btn btn-outline">Cancel</button>
                <button type="submit" class="btn btn-primary" style="padding:11px 28px;">Save Resource</button>
            </div>
        </form>
    </div>
</div>

<%-- Keep the topbar shadow, modal state, filters, and search behavior in sync --%>
<script>
    var content = document.querySelector('.content');
    var topbar = document.querySelector('.topbar');
    if (content && topbar) {
        content.addEventListener('scroll', function() {
            topbar.style.boxShadow = content.scrollTop > 10 ? '0 2px 16px rgba(0,0,0,0.08)' : 'none';
        });
    }

    function openModal() {
        document.getElementById('resourceModal').style.display = 'flex';
        document.body.classList.add('modal-open');
    }

    function closeModal() {
        document.getElementById('resourceModal').style.display = 'none';
        document.body.classList.remove('modal-open');
    }

    document.getElementById('openAddModal').addEventListener('click', function() {
        document.getElementById('modalHeading').textContent = 'Add New Resource';
        document.getElementById('formAction').value = 'create';
        document.getElementById('formResourceId').value = '';
        document.getElementById('resourceForm').reset();
        openModal();
    });

    document.querySelectorAll('.edit-resource-btn').forEach(function(btn) {
        btn.addEventListener('click', function() {
            document.getElementById('modalHeading').textContent = 'Edit Resource';
            document.getElementById('formAction').value = 'update';
            document.getElementById('formResourceId').value = this.dataset.id;
            document.getElementById('fTitle').value = this.dataset.title || '';
            document.getElementById('fDescription').value = this.dataset.description || '';
            document.getElementById('fUrl').value = this.dataset.url || '';
            document.getElementById('fImageUrl').value = this.dataset.imageurl || '';
            document.getElementById('fCategoryId').value = this.dataset.categoryid || '';
            document.getElementById('fStatus').value = this.dataset.status || 'draft';
            document.getElementById('fReadTime').value = this.dataset.readtime || '';
            document.getElementById('fTags').value = this.dataset.tags || '';
            openModal();
        });
    });

    document.getElementById('closeModal').addEventListener('click', closeModal);
    document.getElementById('cancelModal').addEventListener('click', closeModal);
    document.getElementById('resourceModal').addEventListener('click', function(e) {
        if (e.target === this) closeModal();
    });

    document.querySelectorAll('.delete-resource-btn').forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            if (!confirm('Delete this resource? This cannot be undone.')) e.preventDefault();
        });
    });

    document.querySelectorAll('#filterForm select').forEach(function(sel) {
        sel.addEventListener('change', function() { document.getElementById('filterForm').submit(); });
    });

    var searchTimer;
    var searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            clearTimeout(searchTimer);
            searchTimer = setTimeout(function() { document.getElementById('filterForm').submit(); }, 400);
        });
    }
</script>
</body>
</html>