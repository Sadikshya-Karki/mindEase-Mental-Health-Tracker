<%-- About page: company information, mission, and team --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/logo.png">
	<link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/logo.png">
	<link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/logo.png">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=5.0, user-scalable=yes">
	<title>About MindEase | Mental Health & Wellness Platform</title>
	<style>
		:root {
			--primary: #2b7a78;
			--accent: #3aafa9;
			--text-dark: #1f2937;
			--text-muted: #6b7280;
		}

		* {
			box-sizing: border-box;
			margin: 0;
			padding: 0;
		}

		body {
			font-family: "Segoe UI", Arial, sans-serif;
			color: var(--text-dark);
			background: #ffffff;
			line-height: 1.6;
		}

		a {
			text-decoration: none;
			color: inherit;
		}

		.navbar {
			position: fixed;
			top: 16px;
			left: 5%;
			right: 5%;
			z-index: 1000;
			backdrop-filter: none;
			background: transparent;
			border: 1px solid transparent;
			border-radius: 999px;
			padding: 12px 20px;
			display: flex;
			align-items: center;
			justify-content: space-between;
			box-shadow: none;
			transition: background 0.3s, box-shadow 0.3s;
		}

		.logo {
			font-size: 1.15rem;
			font-weight: 700;
			color: var(--primary);
			white-space: nowrap;
		}

		.nav-links {
			display: flex;
			align-items: center;
			gap: 26px;
			color: #334155;
			font-weight: 500;
		}

		.nav-links a:hover {
			color: var(--primary);
		}

		.nav-link {
			position: relative;
			color: #4b5563;
			font-size: 0.92rem;
			padding-bottom: 3px;
			transition: color 0.2s;
		}

		.nav-link::after {
			content: "";
			position: absolute;
			bottom: -2px;
			left: 0;
			width: 0;
			height: 2px;
			background: #2b7a78;
			border-radius: 999px;
			transition: width 0.25s ease;
		}

		.nav-link.active {
			color: #2b7a78;
			font-weight: 600;
		}

		.nav-link.active::after {
			width: 100%;
		}

		.nav-actions {
			display: flex;
			align-items: center;
			gap: 10px;
		}

		.btn {
			border-radius: 999px;
			padding: 12px 28px;
			cursor: pointer;
			transition: all 0.3s;
			font-weight: 600;
			border: 1px solid transparent;
			display: inline-flex;
			align-items: center;
			justify-content: center;
			gap: 8px;
		}

		.btn-outline-teal {
			color: var(--primary);
			border-color: var(--primary);
			background: transparent;
		}

		.btn-outline-teal:hover {
			background: var(--primary);
			color: #ffffff;
		}

		.btn-solid-teal {
			color: #ffffff;
			border-color: transparent;
			background: linear-gradient(135deg, var(--primary), var(--accent));
			box-shadow: 0 12px 28px rgba(43, 122, 120, 0.28);
		}

		.btn-solid-teal:hover {
			transform: translateY(-2px);
			box-shadow: 0 16px 30px rgba(43, 122, 120, 0.35);
		}

		.about-container { max-width: 1280px; margin: 0 auto; padding: 0 5%; }
		.bg-mist { background: #f8fafa; }
		.grid-gap-teal { display: grid; grid-template-columns: repeat(2, 1fr); gap: 1px; background: #e8f0ef; }
		.feature-card { background: white; padding: 32px; transition: background 0.2s; }
		.feature-card:hover { background: #f8fafa; }
		.accent-bar { width: 40px; height: 2px; background: #2b7a78; margin: 16px 0 20px; }
		.accent-bar-sm { width: 32px; height: 2px; background: #2b7a78; margin: 12px 0 16px; }
		.pill-badge { background: rgba(43,122,120,0.08); color: #2b7a78; padding: 4px 12px; border-radius: 40px; font-size: 0.7rem; letter-spacing: 0.1em; text-transform: uppercase; display: inline-block; }

		.section-pad {
			padding-top: 84px;
			padding-bottom: 84px;
		}

		.hero-layout {
			display: block;
			padding-top: 110px;
			padding-bottom: 60px;
		}

		.hero-kicker {
			display: flex;
			align-items: center;
			gap: 16px;
			margin-bottom: 20px;
			flex-wrap: wrap;
		}

		.hero-title {
			font-size: 3rem;
			font-weight: 800;
			color: #0f1f1f;
			margin-top: 20px;
			line-height: 1.15;
		}

		.hero-lead {
			color: #6b7280;
			line-height: 1.7;
			margin-top: 20px;
			max-width: 720px;
		}

		@media (max-width: 1024px) {
			.navbar { border-radius: 20px; top: 10px; left: 3%; right: 3%; flex-wrap: wrap; justify-content: center; }
			.hero-grid, .about-grid, .importance-grid, .cta-card { flex-direction: column; }
			.two-col, .two-col-start, .grid-gap-teal { grid-template-columns: 1fr; }
		}

		@media (max-width: 768px) {
			body { overflow-x: hidden; }
			section { padding: 60px 5%; }
			.navbar {
				position: static;
				margin: 12px auto 0;
				border-radius: 16px;
				padding: 12px 16px;
				gap: 12px;
			}
			.logo img { width: 44px !important; height: 44px !important; }
			.nav-links { display: none; }
			.nav-actions { width: 100%; justify-content: center; flex-wrap: wrap; }
			.btn { padding: 10px 18px; font-size: 0.9rem; }
			.hero-layout { padding-top: 40px; }
			.hero-kicker { gap: 10px; }
			.hero-grid { flex-direction: column; text-align: center; gap: 24px; }
			.hero-buttons { flex-direction: column; gap: 10px; }
			.feature-grid { grid-template-columns: 1fr; }
			.steps-grid { flex-direction: column; }
			.step-card::after { display: none; }
			.testimonial { flex-direction: column; }
			.cta-card { padding: 28px 20px; }
			.stats-grid { grid-template-columns: 1fr; }
			.about-feature-grid { grid-template-columns: 1fr !important; gap: 16px; }
			.about-feature-item { padding: 24px 16px !important; border: 1px solid #f0f4f4 !important; }
			.hero-title { font-size: 2.1rem; }
			.hero-lead { font-size: 0.95rem; }
		}

		@media (max-width: 480px) {
			.hero-copy h1 { font-size: 1.9rem; }
			.social-proof { flex-wrap: wrap; }
			.hero-title { font-size: 1.8rem; }
		}
	</style>
	<%@ include file="/WEB-INF/views/shared/footer-styles.jsp" %>
</head>
<body>

<%-- Main navigation: logo, site links, and auth actions --%>
<nav class="navbar" id="navbar" aria-label="Main navigation">
	<a href="${pageContext.request.contextPath}/" class="logo" style="display:flex;align-items:center;gap:8px;text-decoration:none;">
		<img src="${pageContext.request.contextPath}/images/logo.png" alt="MindEase Logo" style="width:60px;height:60px;object-fit:contain;flex-shrink:0;">
		<span style="font-weight:700;font-size:1.1rem;color:#2b7a78;">MindEase</span>
	</a>
	<div class="nav-links">
		<a href="${pageContext.request.contextPath}/#hero" class="nav-link">Home</a>
		<a href="${pageContext.request.contextPath}/about" class="nav-link active">About</a>
		<a href="${pageContext.request.contextPath}/#features" class="nav-link">Features</a>
		<a href="${pageContext.request.contextPath}/#importance" class="nav-link">Why It Matters</a>
		<a href="${pageContext.request.contextPath}/#how-it-works" class="nav-link">How It Works</a>
	</div>
	<div class="nav-actions">
		<a href="${pageContext.request.contextPath}/login" class="btn btn-outline-teal">Login</a>
		<a href="${pageContext.request.contextPath}/register" class="btn btn-solid-teal">Register</a>
	</div>
</nav>

<main>
	<%-- Hero intro: page title and summary --%>
	<section class="about-container">
		<div class="hero-layout">
			<div>
				<div class="hero-kicker">
					<a href="${pageContext.request.contextPath}/" style="display: flex; align-items: center; justify-content: center; width: 36px; height: 36px; background: #f3f4f6; border-radius: 50%; text-decoration: none; transition: all 0.2s;">
						<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#374151" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
							<path d="M19 12H5M12 19l-7-7 7-7"/>
						</svg>
					</a>
					<span class="pill-badge">About MindEase</span>
				</div>
				<h1 class="hero-title">Mental wellness <span style="color: #2b7a78;">that fits into</span> your life.</h1>
				<p class="hero-lead">Simple tools for checking in, finding help, and staying consistent. Thousands of people use MindEase to build better mental health habits.</p>
			</div>
		</div>
		<div style="height: 1px; background: linear-gradient(90deg, #2b7a78, #3aafa9, transparent); width: 100%; margin: 40px 0 0;"></div>
	</section>

	<%-- Mission section: product philosophy and goals --%>
	<section class="bg-mist section-pad">
		<div class="about-container" style="max-width: 860px;">
			<div>
				<span class="pill-badge">Our Mission</span>
				<h2 style="font-size: 2.2rem; font-weight: 700; color: #0f1f1f; margin-top: 16px;">Making mental wellness accessible to everyone.</h2>
				<div class="accent-bar"></div>
				<p style="color: #6b7280; line-height: 1.8;">Taking care of your mental health shouldn't feel like a chore. The tools we have are scattered, overwhelming, or hard to stick with. We built MindEase to fix that.</p>
				<p style="color: #6b7280; line-height: 1.8; margin-top: 16px;">Every feature - from one-tap mood log to curated resource library - removes friction. Because easy habits stick.</p>
			</div>
		</div>
	</section>

	<%-- Feature grid: core platform capabilities --%>
	<section class="section-pad" style="background: #ffffff;">
		<div class="about-container">
			<div class="section-head-center">
				<span class="pill-badge">What You Get</span>
				<h2 style="font-size: 2.2rem; font-weight: 700; color: #0f1f1f; text-align: center; margin: 16px 0 40px;">Four ways MindEase supports you.</h2>
			</div>
			<div class="grid-gap-teal">
				<div class="feature-card">
					<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
						<span style="color: #2b7a78; font-size: 0.7rem; letter-spacing: 0.08em; font-weight: 700;">01</span>
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="color: #2b7a78; width: 1.2rem; height: 1.2rem;"><circle cx="12" cy="12" r="9"></circle><path d="M8 15c1 1.5 2.3 2 4 2s3-.5 4-2"></path><circle cx="9" cy="10" r="1"></circle><circle cx="15" cy="10" r="1"></circle></svg>
					</div>
					<h3 style="font-weight: 700; color: #0f1f1f; margin-bottom: 8px;">Daily Mood Tracking</h3>
					<p style="color: #6b7280; font-size: 0.85rem; line-height: 1.6;">Log how you feel in seconds. See patterns over time. Notice what affects your mood.</p>
				</div>
				<div class="feature-card">
					<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
						<span style="color: #2b7a78; font-size: 0.7rem; letter-spacing: 0.08em; font-weight: 700;">02</span>
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="color: #2b7a78; width: 1.2rem; height: 1.2rem;"><path d="M3 5.5A2.5 2.5 0 0 1 5.5 3H11v18H5.5A2.5 2.5 0 0 1 3 18.5z"></path><path d="M21 5.5A2.5 2.5 0 0 0 18.5 3H13v18h5.5a2.5 2.5 0 0 0 2.5-2.5z"></path></svg>
					</div>
					<h3 style="font-weight: 700; color: #0f1f1f; margin-bottom: 8px;">Curated Resource Library</h3>
					<p style="color: #6b7280; font-size: 0.85rem; line-height: 1.6;">Articles and guides on anxiety, mindfulness, sleep, and more. Trusted content.</p>
				</div>
				<div class="feature-card">
					<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
						<span style="color: #2b7a78; font-size: 0.7rem; letter-spacing: 0.08em; font-weight: 700;">03</span>
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="color: #2b7a78; width: 1.2rem; height: 1.2rem;"><rect x="3" y="5" width="18" height="16" rx="2"></rect><path d="M8 3v4M16 3v4M3 10h18M9 15l2 2 4-4"></path></svg>
					</div>
					<h3 style="font-weight: 700; color: #0f1f1f; margin-bottom: 8px;">Counseling Sessions</h3>
					<p style="color: #6b7280; font-size: 0.85rem; line-height: 1.6;">Book appointments with counselors. Request, track, and manage sessions easily.</p>
				</div>
				<div class="feature-card">
					<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
						<span style="color: #2b7a78; font-size: 0.7rem; letter-spacing: 0.08em; font-weight: 700;">04</span>
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="color: #2b7a78; width: 1.2rem; height: 1.2rem;"><path d="M3 19h18"></path><polyline points="5 15 10 10 13 13 19 7"></polyline></svg>
					</div>
					<h3 style="font-weight: 700; color: #0f1f1f; margin-bottom: 8px;">Personal Insights &amp; History</h3>
					<p style="color: #6b7280; font-size: 0.85rem; line-height: 1.6;">View mood history, track streaks, and see your progress over time.</p>
				</div>
			</div>
		</div>
	</section>

	<%-- Approach section: guiding principles and audience fit --%>
	<section class="bg-mist section-pad">
		<div class="about-container two-col-start">
			<div>
				<span class="pill-badge">Our Approach</span>
				<h3 style="font-size: 1.8rem; font-weight: 700; color: #0f1f1f; margin-top: 12px;">Small steps.<br>Every day.</h3>
				<div class="accent-bar-sm"></div>
				<p style="color: #6b7280; line-height: 1.7;">We don't believe in quick fixes. We believe in consistency. A few seconds of checking in each day, a five-minute read when you need it - that's how real change happens.</p>
			</div>
			<div>
				<span class="pill-badge">For Everyone</span>
				<h3 style="font-size: 1.8rem; font-weight: 700; color: #0f1f1f; margin-top: 12px;">Mental health<br>doesn't have a type.</h3>
				<div class="accent-bar-sm"></div>
				<p style="color: #6b7280; line-height: 1.7;">Students, professionals, parents, anyone. Whether you're managing anxiety or just want to understand yourself better - MindEase meets you where you are.</p>
			</div>
		</div>
	</section>
</main>

<%-- Shared footer and contact modal --%>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>

<%-- Navbar script: apply scroll-based styling --%>
<script>
	(function () {
		const navbar = document.getElementById("navbar");

		window.addEventListener("scroll", function () {
			if (window.scrollY > 60) {
				navbar.style.background = "rgba(255,255,255,0.95)";
				navbar.style.backdropFilter = "blur(12px)";
				navbar.style.boxShadow = "0 2px 20px rgba(0,0,0,0.08)";
			} else {
				navbar.style.background = "transparent";
				navbar.style.backdropFilter = "none";
				navbar.style.boxShadow = "none";
			}
		});
	})();
</script>

</body>
</html>

