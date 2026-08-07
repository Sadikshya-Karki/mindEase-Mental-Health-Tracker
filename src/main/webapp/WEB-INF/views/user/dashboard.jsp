<%-- User dashboard: main interface displaying mood summary, stats, and quick actions --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/logo.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard - MindEase</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-dashboard.css">
</head>
<body>
<div class="user-layout">
    <jsp:include page="/WEB-INF/views/user/includes/sidebar.jsp" />

    <div class="user-main">
        <jsp:include page="/WEB-INF/views/admin/includes/topbar.jsp" />

        <main class="user-content">
            <%-- Success alert: shown when mood is saved --%>
            <c:if test="${param.success == 'true'}">
                <div class="alert-success">Mood saved successfully.</div>
            </c:if>
            <%-- Error alert: shown if operation fails --%>
            <c:if test="${not empty param.error}">
                <div class="alert-error">${param.error}</div>
            </c:if>

            <%-- Welcome banner with personalization and stats --%>
            <jsp:include page="/WEB-INF/views/user/includes/welcome-banner.jsp" />
            <%-- Key statistics cards: streak, logs, bookmarks, appointments --%>
            <jsp:include page="/WEB-INF/views/user/includes/stat-cards.jsp" />
            <%-- Quick action buttons to common user tasks --%>
            <jsp:include page="/WEB-INF/views/user/includes/quick-actions.jsp" />

            <%-- Two-column grid layout: mood chart + sessions on left, mood log + resources on right --%>
            <section class="dashboard-grid">
                <div>
                    <jsp:include page="/WEB-INF/views/user/includes/mood-week-chart.jsp" />
                    <jsp:include page="/WEB-INF/views/user/includes/upcoming-sessions.jsp" />
                </div>
                <div>
                    <jsp:include page="/WEB-INF/views/user/includes/quick-mood-log.jsp" />
                    <jsp:include page="/WEB-INF/views/user/includes/saved-resources.jsp" />
                </div>
            </section>
        </main>
    </div>
</div>

<script>
    <%-- Navigation active link highlighting --%>
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-link').forEach(link => {
        const href = link.getAttribute('href');
        if (href && (href === currentPath || currentPath.startsWith(href + '/'))) {
            link.classList.add('active');
        }
    });

    <%-- Topbar shadow effect on scroll --%>
    const content = document.querySelector('.user-content');
    const topbar = document.querySelector('.topbar');
    if (content && topbar) {
        content.addEventListener('scroll', () => {
            topbar.style.boxShadow = content.scrollTop > 10 ? '0 2px 16px rgba(0,0,0,0.08)' : 'none';
        });
    }

    <%-- Quick mood selector in dashboard: click mood emoji to select score --%>
    const moodOptions = document.querySelectorAll('.mood-option');
    const moodSubmit = document.getElementById('moodSubmit');
    const moodScoreInput = document.getElementById('moodScore');

    moodOptions.forEach(opt => {
        opt.addEventListener('click', function() {
            moodOptions.forEach(o => o.classList.remove('selected'));
            this.classList.add('selected');
            moodScoreInput.value = this.dataset.score;
            moodSubmit.disabled = false;
            moodSubmit.textContent = `Save Mood - \${this.dataset.score}/5`;
        });
    });

    <%-- Mood chart bar tooltip: show mood and date on hover --%>
    const bars = document.querySelectorAll('.chart-bar');
    const tooltip = document.createElement('div');
    tooltip.className = 'chart-tooltip';
    tooltip.style.display = 'none';
    document.body.appendChild(tooltip);

    bars.forEach(bar => {
        bar.addEventListener('mouseenter', (e) => {
            tooltip.textContent = bar.dataset.mood;
            tooltip.style.display = 'block';
            tooltip.style.left = e.pageX + 10 + 'px';
            tooltip.style.top = e.pageY - 30 + 'px';
        });
        bar.addEventListener('mouseleave', () => { tooltip.style.display = 'none'; });
        bar.addEventListener('mousemove', (e) => {
            tooltip.style.left = e.pageX + 10 + 'px';
            tooltip.style.top = e.pageY - 30 + 'px';
        });
    });

    <%-- Character counter for mood note textarea --%>
    const moodNote = document.getElementById('moodNote');
    const moodNoteCount = document.getElementById('moodNoteCount');
    if (moodNote && moodNoteCount) {
        moodNote.addEventListener('input', () => { moodNoteCount.textContent = moodNote.value.length; });
    }
</script>
</body>
</html>