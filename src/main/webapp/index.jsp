<%-- Landing page: home, about, features, testimonials, call-to-action, footer --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/logo.png">
	<link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/logo.png">
	<link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/logo.png">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=5.0, user-scalable=yes">
	<title>MindEase | Mental Health & Wellness Platform</title>
	<style>
		:root {
			--primary: #2b7a78;
			--accent: #3aafa9;
			--dark-bg: #1a2e2e;
			--mint: #a7f3d0;
			--text-dark: #1f2937;
			--text-muted: #6b7280;
			--shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
		}

		* {
			box-sizing: border-box;
			margin: 0;
			padding: 0;
		}

		html {
			scroll-behavior: smooth;
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

		section {
			padding: 80px 5%;
			position: relative;
			overflow: hidden;
		}

		.pill-badge {
			display: inline-block;
			padding: 6px 16px;
			border-radius: 999px;
			font-size: 0.8rem;
			font-weight: 600;
			letter-spacing: 0.2px;
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

		.btn:focus {
			outline: none;
			box-shadow: 0 0 0 3px rgba(58, 175, 169, 0.25);
		}

		.blob {
			position: absolute;
			width: 400px;
			height: 400px;
			border-radius: 50%;
			filter: blur(80px);
			opacity: 0.3;
			pointer-events: none;
		}

		.floating-card {
			position: absolute;
			background: #ffffff;
			border-radius: 14px;
			padding: 12px 18px;
			box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
			backdrop-filter: blur(4px);
			z-index: 3;
		}

		.image-wrap {
			position: relative;
			border-radius: 20px;
			overflow: hidden;
		}

		.image-wrap img {
			width: 100%;
			height: 100%;
			object-fit: cover;
			display: block;
		}

		.section-title {
			text-align: center;
			max-width: 760px;
			margin: 0 auto 42px;
		}

		.section-title h2 {
			font-size: clamp(1.8rem, 3vw, 2.5rem);
			line-height: 1.25;
			margin: 14px 0;
		}

		.section-title p {
			color: var(--text-muted);
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
			text-decoration: none;
			font-size: 0.92rem;
			padding-bottom: 3px;
			transition: color 0.2s;
		}

		.nav-link::after {
			content: '';
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

		#hero {
			min-height: 100vh;
			background: linear-gradient(135deg, #e8f5f5 0%, #eef0fb 50%, #fdf4f0 100%);
			display: flex;
			align-items: center;
			position: relative;
			overflow: hidden;
			padding-top: 130px;
			padding-bottom: 140px;
		}

		.hero-grid {
			position: relative;
			z-index: 1;
			display: flex;
			align-items: center;
			justify-content: space-between;
			gap: 56px;
			width: 100%;
		}

		.hero-copy,
		.hero-media {
			flex: 1;
		}

		.hero-badge {
			background: rgba(43, 122, 120, 0.1);
			color: var(--primary);
			margin-bottom: 18px;
		}

		.hero-badge .dot {
			width: 7px;
			height: 7px;
			border-radius: 50%;
			display: inline-block;
			background: var(--primary);
			margin-right: 8px;
			vertical-align: middle;
		}

		.hero-copy h1 {
			font-size: clamp(2.2rem, 4vw, 3.6rem);
			line-height: 1.1;
			margin-bottom: 16px;
		}

		.hero-copy h1 .highlight {
			color: var(--primary);
		}

		.hero-copy p {
			color: var(--text-muted);
			max-width: 560px;
			margin-bottom: 26px;
			font-size: 1.05rem;
		}

		.hero-buttons {
			display: flex;
			flex-wrap: wrap;
			gap: 12px;
			margin-bottom: 24px;
		}

		.social-proof {
			display: flex;
			align-items: center;
			gap: 10px;
			margin-top: 24px;
			flex-wrap: nowrap;
		}

		.avatars {
			display: flex;
		}

		.avatars img {
			width: 40px;
			height: 40px;
			border-radius: 50%;
			object-fit: cover;
			border: 2px solid #ffffff;
			margin-left: -10px;
		}

		.avatars img:first-child {
			margin-left: 0;
		}

		.stars {
			color: #f59e0b;
			letter-spacing: 2px;
			font-size: 1rem;
		}

		.social-proof .trust-text {
			color: var(--text-muted);
			font-size: 0.95rem;
			white-space: nowrap;
		}

		.hero-media .image-wrap {
			min-height: unset;
			height: auto;
			box-shadow: 0 22px 55px rgba(26, 46, 46, 0.2);
			position: relative;
			overflow: hidden;
			font-size: 0;
			line-height: 0;
			display: block;
		}

		.hero-media .image-wrap img {
			width: 100%;
			height: auto;
			object-fit: cover;
			display: block;
			vertical-align: bottom;
			border-radius: 20px;
		}

		.mood-card {
			bottom: -20px;
			left: 20px;
			background: #ffffff;
			border-radius: 12px;
			padding: 10px 16px;
			box-shadow: 0 8px 28px rgba(0, 0, 0, 0.12);
			display: flex;
			align-items: center;
			gap: 10px;
			white-space: nowrap;
			z-index: 5;
		}

		.streak-card {
			top: 20px;
			right: -26px;
		}

		.card-title {
			font-size: 0.82rem;
			color: #64748b;
			font-weight: 600;
		}

		.card-value {
			font-size: 0.95rem;
			font-weight: 700;
			color: #0f172a;
			margin-top: 2px;
		}

		.hero-icon {
			width: 24px;
			height: 24px;
			border-radius: 50%;
			display: inline-flex;
			align-items: center;
			justify-content: center;
			margin-right: 8px;
			font-size: 0.8rem;
			background: #dcfce7;
			color: #16a34a;
		}

		#about {
			background: #ffffff;
		}

		.about-grid {
			display: flex;
			gap: 60px;
			align-items: center;
			width: 100%;
		}

		.about-copy,
		.about-image {
			flex: 1;
		}

		.about-label {
			font-size: 0.72rem;
			letter-spacing: 0.12em;
			color: #2b7a78;
			font-weight: 600;
			margin-bottom: 14px;
			text-transform: uppercase;
		}

		.about-copy h2 {
			font-size: 2rem;
			font-weight: 700;
			color: #1a2e2e;
			line-height: 1.3;
			margin-bottom: 16px;
		}

		.about-copy p {
			color: #6b7280;
			font-size: 0.95rem;
			line-height: 1.8;
			margin-bottom: 12px;
		}

		.about-link {
			color: #2b7a78;
			font-size: 0.88rem;
			font-weight: 600;
			text-decoration: none;
			border-bottom: 1.5px solid #2b7a78;
			padding-bottom: 2px;
			display: inline-block;
			margin-top: 8px;
		}

		.about-link:hover {
			color: #1a4d4a;
			border-bottom-color: #1a4d4a;
		}

		.about-image img {
			width: 100%;
			border-radius: 4px;
			display: block;
			box-shadow: 0 2px 20px rgba(0, 0, 0, 0.08);
		}

		#features {
			background: #ffffff;
		}

		.feature-grid {
			display: flex;
			gap: 20px;
			flex-wrap: wrap;
			justify-content: center;
		}

		.feature-card {
			flex: 1 1 230px;
			max-width: 280px;
			background: #ffffff;
			border-radius: 16px;
			box-shadow: 0 2px 16px rgba(0, 0, 0, 0.07);
			padding: 24px;
			transition: transform 0.3s, box-shadow 0.3s;
		}

		.feature-card:hover {
			transform: translateY(-6px);
			box-shadow: 0 16px 30px rgba(0, 0, 0, 0.12);
		}

		.feature-icon {
			width: 56px;
			height: 56px;
			border-radius: 14px;
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 1.35rem;
			margin-bottom: 16px;
		}

		.feature-card h3 {
			font-size: 1.15rem;
			margin-bottom: 8px;
		}

		.feature-card p {
			color: var(--text-muted);
			margin-bottom: 14px;
		}

		.learn-link {
			font-weight: 700;
			font-size: 0.95rem;
		}

		.section-header {
			text-align: center;
			max-width: 700px;
			margin: 0 auto 60px auto;
		}

		.section-label {
			display: inline-block;
			background: rgba(43, 122, 120, 0.08);
			color: #2b7a78;
			font-size: 0.7rem;
			font-weight: 600;
			letter-spacing: 0.08em;
			text-transform: uppercase;
			padding: 5px 14px;
			border-radius: 40px;
			margin-bottom: 16px;
		}

		.feature-block {
			transition: background 0.2s ease, border-color 0.2s ease;
			cursor: pointer;
		}

		.feature-block:hover {
			background: #f8fafa !important;
			border-color: #2b7a78 !important;
		}

		#importance {
			background: linear-gradient(135deg, #1a2e2e, #2b7a78, #3aafa9);
			color: #ffffff;
		}

		#importance .section-label {
			background: rgba(255, 255, 255, 0.14);
			color: #ffffff;
			border: 1px solid rgba(255, 255, 255, 0.24);
			box-shadow: 0 10px 28px rgba(0, 0, 0, 0.14);
			backdrop-filter: blur(10px);
			-webkit-backdrop-filter: blur(10px);
		}

		.importance-grid {
			display: flex;
			gap: 42px;
			align-items: center;
			position: relative;
			z-index: 1;
		}

		.importance-image,
		.importance-content {
			flex: 1;
			text-align: center;
		}

		.importance-image {
			position: relative;
		}

		.importance-image .image-wrap {
			min-height: unset;
			height: auto;
			box-shadow: 0 22px 52px rgba(0, 0, 0, 0.28);
			overflow: hidden;
			font-size: 0;
			line-height: 0;
			display: block;
		}

		.importance-image .image-wrap img {
			width: 100%;
			height: auto;
			object-fit: cover;
			display: block;
			vertical-align: bottom;
		}

		.score-card {
			right: -16px;
			bottom: -16px;
			color: #0f172a;
			background: #ffffff;
			border-radius: 14px;
			padding: 12px 18px;
			box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
			display: flex;
			align-items: center;
			gap: 12px;
			z-index: 5;
		}

		.score-icon {
			font-size: 1.2rem;
			line-height: 1;
		}

		.importance-badge { margin-bottom: 16px; }

		.importance-content h2 {
			font-size: clamp(1.8rem, 3vw, 2.4rem);
			line-height: 1.3;
			margin-bottom: 14px;
		}

		.importance-content h2 .mint {
			color: var(--mint);
		}

		.importance-content p {
			opacity: 0.75;
			margin-bottom: 12px;
			max-width: 620px;
			margin-left: auto;
			margin-right: auto;
		}

		.stats-grid {
			margin-top: 22px;
			display: grid;
			grid-template-columns: repeat(2, minmax(180px, 1fr));
			gap: 14px;
		}

		.stat-card {
			background: rgba(255, 255, 255, 0.1);
			border: 1px solid rgba(255, 255, 255, 0.28);
			border-radius: 14px;
			padding: 16px;
			backdrop-filter: blur(6px);
		}

		.stat-icon {
			font-size: 1rem;
			opacity: 0.95;
			margin-bottom: 8px;
		}

		.stat-value {
			font-size: 1.5rem;
			font-weight: 800;
			margin-bottom: 4px;
		}

		.stat-text {
			font-size: 0.93rem;
			opacity: 0.9;
		}

		#how-it-works {
			background: #f8fafc;
		}

		.steps-grid {
			display: flex;
			flex-wrap: wrap;
			gap: 18px;
			justify-content: center;
		}

		.step-card {
			position: relative;
			flex: 1 1 220px;
			max-width: 260px;
			background: #ffffff;
			border-radius: 16px;
			padding: 22px;
			box-shadow: 0 2px 16px rgba(0, 0, 0, 0.07);
			transition: transform 0.3s, box-shadow 0.3s;
			overflow: visible;
		}

		.step-card:hover {
			transform: translateY(-6px);
			box-shadow: 0 14px 30px rgba(15, 23, 42, 0.12);
		}

		.step-card::after {
			content: "";
			position: absolute;
			top: 50%;
			right: -18px;
			width: 18px;
			height: 1px;
			background: #cbd5e1;
		}

		.steps-grid .step-card:last-child::after {
			display: none;
		}

		.step-number {
			position: absolute;
			top: 8px;
			right: 12px;
			font-size: 2.2rem;
			font-weight: 800;
			color: #0f172a;
			opacity: 0.07;
			line-height: 1;
		}

		.step-card h3 {
			margin: 14px 0 8px;
		}

		.step-card p {
			color: var(--text-muted);
		}

		.step-item {
			transition: all 0.2s ease;
			cursor: pointer;
		}

		.step-item:hover {
			border-color: #2b7a78 !important;
			background: #fafdfd !important;
			transform: translateY(-2px);
		}

		.testimonial {
			margin: 34px auto 0;
			max-width: 860px;
			background: linear-gradient(120deg, rgba(58, 175, 169, 0.15), rgba(199, 210, 254, 0.2));
			border-radius: 20px;
			padding: 26px;
			display: flex;
			gap: 16px;
			align-items: center;
		}

		.testimonial img {
			width: 72px;
			height: 72px;
			border-radius: 50%;
			object-fit: cover;
			border: 3px solid #ffffff;
		}

		.testimonial p {
			color: #4b5563;
			font-style: italic;
			margin: 6px 0;
		}

		.testimonial .author {
			color: var(--primary);
			font-weight: 700;
			font-style: normal;
		}

		#cta {
			background: #ffffff;
		}

		.cta-card {
			background: linear-gradient(135deg, #1a2e2e, #3aafa9);
			border-radius: 26px;
			padding: 48px;
			display: flex;
			align-items: center;
			justify-content: space-between;
			gap: 28px;
			position: relative;
			overflow: hidden;
		}

		.cta-content,
		.cta-actions {
			position: relative;
			z-index: 2;
		}

		.cta-content {
			max-width: 640px;
			color: #ffffff;
		}

		.cta-content .pill-badge {
			background: rgba(255, 255, 255, 0.14);
			color: #ffffff;
			margin-bottom: 14px;
		}

		.cta-content h2 {
			font-size: clamp(1.8rem, 3vw, 2.4rem);
			line-height: 1.25;
			margin-bottom: 10px;
		}

		.cta-content h2 .mint {
			color: var(--mint);
		}

		.cta-content p {
			opacity: 0.9;
		}

		.btn-frost {
			background: rgba(255, 255, 255, 0.2);
			border: 1px solid rgba(255, 255, 255, 0.45);
			color: #ffffff;
			backdrop-filter: blur(6px);
			margin-bottom: 10px;
		}

		.btn-frost:hover {
			background: #ffffff;
			color: var(--primary);
		}

		.cta-note {
			color: rgba(255, 255, 255, 0.85);
			font-size: 0.92rem;
			margin-bottom: 14px;
		}

		.trust-row {
			display: flex;
			gap: 10px;
			flex-wrap: wrap;
		}

		.trust-chip {
			background: rgba(255, 255, 255, 0.18);
			color: #ffffff;
			border: 1px solid rgba(255, 255, 255, 0.28);
			border-radius: 999px;
			padding: 7px 14px;
			font-size: 0.9rem;
		}


		@media (max-width: 1024px) {
			.navbar { border-radius: 20px; top: 10px; left: 3%; right: 3%; }
			.hero-grid, .about-grid, .importance-grid { flex-direction: column; }
			.cta-card { flex-direction: column; align-items: flex-start; }
			.stats-grid { grid-template-columns: repeat(2, 1fr); }
		}

		@media (max-width: 768px) {
			body { overflow-x: hidden; }

			section { padding: 48px 5%; }

			/* Nav */
			.nav-links { display: none; }
			.navbar { flex-wrap: nowrap; justify-content: space-between; padding: 10px 16px; top: 10px; left: 3%; right: 3%; background: rgba(255,255,255,0.95) !important; backdrop-filter: blur(12px); box-shadow: 0 2px 20px rgba(0,0,0,0.08) !important; }
			.nav-actions { width: auto; gap: 8px; }
			.nav-actions .btn { padding: 8px 16px; font-size: 0.82rem; }

			/* Hero */
			#hero { padding-top: 110px; padding-bottom: 60px; }
			.hero-grid { flex-direction: column; text-align: center; gap: 28px; }
			.hero-copy h1 { font-size: 2rem; }
			.hero-copy p { font-size: 0.95rem; margin: 0 auto 20px; }
			.hero-buttons { flex-direction: column; align-items: center; gap: 10px; }
			.hero-buttons .btn { width: 100%; max-width: 320px; }
			.hero-media { width: 100%; }
			.hero-media .image-wrap img { max-height: 280px; object-fit: cover; }
			.social-proof { justify-content: center; flex-wrap: wrap; }

			/* About */
			.about-feature-grid { grid-template-columns: 1fr !important; }
			.feature-block { padding: 24px 0 !important; border-right: none !important; border-bottom: 1px solid #f0f4f4 !important; }
			.feature-block:last-child { border-bottom: none !important; }

			/* Importance */
			.importance-grid { flex-direction: column; gap: 28px; }
			.stats-grid { grid-template-columns: repeat(2, 1fr); gap: 12px; }

			/* Steps */
			.steps-grid { flex-direction: column; align-items: stretch; }
			.step-line { display: none; }
			.step-item { min-width: unset !important; width: 100%; }

			/* CTA */
			.cta-card { padding: 28px 20px; flex-direction: column; align-items: flex-start; gap: 20px; }
			.cta-actions { width: 100%; }
			.btn-frost { width: 100%; }
			.trust-row { gap: 8px; }

		}

		@media (max-width: 480px) {
			section { padding: 40px 4%; }

			.hero-copy h1 { font-size: 1.75rem; }
			.social-proof { flex-wrap: wrap; justify-content: center; }

			.stats-grid { grid-template-columns: 1fr; }

			.cta-content h2 { font-size: 1.5rem; }

			.step-item { padding: 20px 16px !important; }
		}
	</style>
	<%@ include file="/WEB-INF/views/shared/footer-styles.jsp" %>
</head>
<body>
<section id="hero">
	<%-- Hero section: primary value proposition and call-to-action --%>
	<%-- Decorative background blobs for visual depth --%>
	<div class="blob hero-blob-teal"></div>
	<div class="blob hero-blob-lavender"></div>
	<div class="blob hero-blob-pink"></div>

	<%-- Fixed navbar: site navigation with logo and auth links --%>
	<nav class="navbar" id="navbar" aria-label="Main navigation">
		<a href="${pageContext.request.contextPath}/" class="logo" style="display:flex;align-items:center;gap:8px;text-decoration:none;">
			<img src="${pageContext.request.contextPath}/images/logo.png" alt="MindEase Logo" style="width:60px;height:60px;object-fit:contain;flex-shrink:0;">
			<span style="font-weight:700;font-size:1.1rem;color:#2b7a78;">MindEase</span>
		</a>
		<div class="nav-links">
			<a href="#hero" class="nav-link">Home</a>
			<a href="#about" class="nav-link">About</a>
			<a href="#importance" class="nav-link">Why It Matters</a>
			<a href="#how-it-works" class="nav-link">How It Works</a>
		</div>
		<div class="nav-actions">
			<a href="${pageContext.request.contextPath}/login" class="btn btn-outline-teal">Login</a>
			<a href="${pageContext.request.contextPath}/register" class="btn btn-solid-teal">Register</a>
		</div>
	</nav>

	<div class="hero-grid">
		<div class="hero-copy">
			<span class="pill-badge hero-badge"><span></span>Mental Health &amp; Wellness Platform</span>
			<%-- Hero heading: main value proposition --%>
			<h1>
				Take Care of<br>
				<span class="highlight">Your Mind,</span><br>
				Every Day
			</h1>
			<p>
				Build healthier habits with mood tracking, guided resources, and support tools designed
				to help you feel better, one day at a time.
			</p>
			<%-- CTA buttons: register and login --%>
			<div class="hero-buttons">
				<a href="${pageContext.request.contextPath}/register" class="btn btn-solid-teal">Get Started - It's Free</a>
				<a href="${pageContext.request.contextPath}/login" class="btn btn-outline-teal">Login</a>
			</div>
		</div>

		<div class="hero-media">
			<div class="image-wrap" style="display:block;line-height:0;font-size:0;overflow:hidden;border-radius:20px;">
				<img src="https://images.unsplash.com/photo-1545205597-3d9d02c29597?auto=format&amp;fit=crop&amp;w=1000&amp;q=80" alt="Woman meditating peacefully" style="display:block;width:100%;height:auto;vertical-align:bottom;">
			</div>
		</div>
	</div>

	<div style="position:absolute;bottom:-2px;left:0;width:100%;overflow:hidden;line-height:0;z-index:2;">
		<svg viewBox="0 0 1440 80" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="none" style="display:block;width:100%;height:80px;">
			<path d="M0,40 C180,80 360,0 540,40 C720,80 900,0 1080,40 C1260,80 1380,20 1440,40 L1440,80 L0,80 Z" fill="#ffffff"/>
		</svg>
	</div>
</section>

<section id="about" style="background: #ffffff; padding: 80px 8%;">
	<%-- About section: feature highlights and Learn More link --%>
	<div style="max-width: 860px; margin: 0 auto;">
		<%-- Section header: about title and description --%>
		<div class="section-header" style="margin-bottom: 48px;">
			<span class="section-label">ABOUT MINDEASE</span>
			<h2 style="font-size: 2.2rem; font-weight: 700; color: #1a2e2e; margin-bottom: 16px;">Features Built for Your Well-being</h2>
			<p style="color: #6b7280; font-size: 1rem; max-width: 600px; margin: 0 auto;">Simple, thoughtful tools that make mental wellness support easy to access and maintain.</p>
		</div>

		<%-- Feature grid: 2x2 grid of key product features --%>
		<div class="about-feature-grid" style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 0; margin-top: 20px;">
			<div class="feature-block about-feature-item about-feature-left about-feature-top" style="padding: 32px 32px 32px 0; border-bottom: 1px solid #f0f4f4; border-right: 1px solid #f0f4f4;">
				<div style="font-weight: 700; color: #1a2e2e; font-size: 1.1rem; margin-bottom: 8px;">Track Your Mood Daily</div>
				<div style="color: #6b7280; font-size: 0.9rem; line-height: 1.6;">Log emotions quickly, identify patterns, and understand how your routines affect your well-being.</div>
			</div>

			<div class="feature-block about-feature-item about-feature-right about-feature-top" style="padding: 32px 0 32px 32px; border-bottom: 1px solid #f0f4f4;">
				<div style="font-weight: 700; color: #1a2e2e; font-size: 1.1rem; margin-bottom: 8px;">Explore Mental Health Resources</div>
				<div style="color: #6b7280; font-size: 0.9rem; line-height: 1.6;">Discover practical exercises, articles, and guided techniques built by wellness professionals.</div>
			</div>

			<div class="feature-block about-feature-item about-feature-left" style="padding: 32px 32px 32px 0; border-right: 1px solid #f0f4f4;">
				<div style="font-weight: 700; color: #1a2e2e; font-size: 1.1rem; margin-bottom: 8px;">Book Counseling Sessions</div>
				<div style="color: #6b7280; font-size: 0.9rem; line-height: 1.6;">Connect with qualified support and schedule confidential sessions at times that suit your life.</div>
			</div>

			<div class="feature-block about-feature-item about-feature-right" style="padding: 32px 0 32px 32px;">
				<div style="font-weight: 700; color: #1a2e2e; font-size: 1.1rem; margin-bottom: 8px;">Monitor Your Progress</div>
				<div style="color: #6b7280; font-size: 0.9rem; line-height: 1.6;">Review growth over time with clear dashboards that highlight consistency and positive change.</div>
			</div>
		</div>

		<div style="text-align: center; margin-top: 48px;">
			<a href="${pageContext.request.contextPath}/about" style="background: #2b7a78; color: white; padding: 12px 32px; border-radius: 40px; text-decoration: none; font-weight: 600; display: inline-block;">Learn More →</a>
		</div>
	</div>
</section>

<section id="importance">
	<%-- Importance section: impact statement with stats grid --%>
	<%-- Decorative background blobs --%>
	<div class="blob importance-blob-white"></div>
	<div class="blob importance-blob-purple"></div>

	<%-- Section header: importance title --%>
	<div class="section-header" style="text-align: center; position: relative; z-index: 1; margin-bottom: 56px;">
		<span class="section-label">WHY IT MATTERS</span>
		<h2>Your mental health affects everything</h2>
		<p>When you feel better, you live better. Small daily habits create lasting change.</p>
	</div>

	<%-- Two-column layout: image on left, stats on right --%>
	<div class="importance-grid">
		<div class="importance-image">
			<div class="image-wrap" style="display:block;line-height:0;font-size:0;overflow:hidden;border-radius:20px;">
				<img src="https://images.unsplash.com/photo-1474418397713-7ede21d49118?auto=format&amp;fit=crop&amp;w=1000&amp;q=80" alt="Person journaling in a cozy room" style="display:block;width:100%;height:auto;vertical-align:bottom;">
			</div>

		</div>


		<div class="stats-grid">
			<div class="stat-card">

				<div class="stat-value">1 in 5</div>
				<div class="stat-text">people experience a mental health condition each year.</div>
			</div>
			<div class="stat-card">

				<div class="stat-value">70%</div>
				<div class="stat-text">of people with mental illness don’t seek help globally.</div>
			</div>
			<div class="stat-card">

				<div class="stat-value">80%</div>
				<div class="stat-text">improvement seen with consistent mood tracking.</div>
			</div>
			<div class="stat-card">

				<div class="stat-value">3×</div>
				<div class="stat-text">more likely to recover with professional support.</div>
			</div>
		</div>
	</div>
	</div>
</section>

<section id="how-it-works">
	<%-- How it works section: step-by-step user journey --%>
	<div class="about-container" style="max-width:1280px;margin:0 auto;">
		<div class="section-header">
			<span class="section-label">HOW IT WORKS</span>
			<h2 style="font-size:2rem;font-weight:700;color:#1a2e2e;margin-bottom:16px;">Start your wellness journey in four simple steps</h2>
			<p style="color:#6b7280;font-size:0.95rem;line-height:1.6;">From first sign-up to lasting progress — everything you need is built right in.</p>
		</div>

		<div class="steps-grid" style="display:flex;align-items:stretch;justify-content:center;flex-wrap:wrap;max-width:1100px;margin:0 auto;">

			<div class="step-item" style="flex:1;min-width:180px;background:#ffffff;border:1px solid #e5e7eb;border-radius:12px;padding:28px 20px;transition:all 0.2s;">
				<div style="font-size:0.7rem;font-weight:700;color:#2b7a78;letter-spacing:0.08em;margin-bottom:16px;">01</div>
				<h3 style="font-size:1rem;font-weight:700;color:#1a2e2e;margin-bottom:10px;">Register an Account</h3>
				<p style="font-size:0.85rem;color:#6b7280;line-height:1.5;">Create your free profile and set your wellness goals in under a minute.</p>
			</div>

			<div class="step-line" style="width:40px;height:1px;background:#d1d5db;margin:0 8px;align-self:center;"></div>

			<div class="step-item" style="flex:1;min-width:180px;background:#ffffff;border:1px solid #e5e7eb;border-radius:12px;padding:28px 20px;transition:all 0.2s;">
				<div style="font-size:0.7rem;font-weight:700;color:#2b7a78;letter-spacing:0.08em;margin-bottom:16px;">02</div>
				<h3 style="font-size:1rem;font-weight:700;color:#1a2e2e;margin-bottom:10px;">Log Your Daily Mood</h3>
				<p style="font-size:0.85rem;color:#6b7280;line-height:1.5;">Check in quickly every day and capture how you feel with simple entries.</p>
			</div>

			<div class="step-line" style="width:40px;height:1px;background:#d1d5db;margin:0 8px;align-self:center;"></div>

			<div class="step-item" style="flex:1;min-width:180px;background:#ffffff;border:1px solid #e5e7eb;border-radius:12px;padding:28px 20px;transition:all 0.2s;">
				<div style="font-size:0.7rem;font-weight:700;color:#2b7a78;letter-spacing:0.08em;margin-bottom:16px;">03</div>
				<h3 style="font-size:1rem;font-weight:700;color:#1a2e2e;margin-bottom:10px;">Access Resources or Book Help</h3>
				<p style="font-size:0.85rem;color:#6b7280;line-height:1.5;">Explore practical tools or schedule support when you need deeper guidance.</p>
			</div>

			<div class="step-line" style="width:40px;height:1px;background:#d1d5db;margin:0 8px;align-self:center;"></div>

			<div class="step-item" style="flex:1;min-width:180px;background:#ffffff;border:1px solid #e5e7eb;border-radius:12px;padding:28px 20px;transition:all 0.2s;">
				<div style="font-size:0.7rem;font-weight:700;color:#2b7a78;letter-spacing:0.08em;margin-bottom:16px;">04</div>
				<h3 style="font-size:1rem;font-weight:700;color:#1a2e2e;margin-bottom:10px;">Improve Over Time</h3>
				<p style="font-size:0.85rem;color:#6b7280;line-height:1.5;">Track progress trends and celebrate consistency as your habits strengthen.</p>
			</div>

		</div>
	</div>
</section>

<%-- Shared footer and contact modal --%>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>

<script>
	(function () {
		const navbar = document.getElementById('navbar');
		const sections = document.querySelectorAll('section[id]');
		const navLinks = document.querySelectorAll('.nav-link');

		window.addEventListener('scroll', function() {
			if (window.scrollY > 60) {
				navbar.style.background = 'rgba(255,255,255,0.95)';
				navbar.style.backdropFilter = 'blur(12px)';
				navbar.style.boxShadow = '0 2px 20px rgba(0,0,0,0.08)';
			} else {
				navbar.style.background = 'transparent';
				navbar.style.backdropFilter = 'none';
				navbar.style.boxShadow = 'none';
			}

			let current = '';
			sections.forEach(function(section) {
				const sectionTop = section.offsetTop - 200;
				if (window.scrollY >= sectionTop) {
					current = section.getAttribute('id');
				}
			});
			navLinks.forEach(function(link) {
				link.classList.remove('active');
				if (link.getAttribute('href') === '#' + current) {
					link.classList.add('active');
				}
			});
		});
		window.dispatchEvent(new Event('scroll'));
		if (navLinks.length > 0) navLinks[0].classList.add('active');
	})();
</script>
</body>
</html>
