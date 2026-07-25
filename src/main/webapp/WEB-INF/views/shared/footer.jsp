<%-- Shared footer: brand, navigation, and contact entry point --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<section id="footer">
	<div class="footer-grid">
		<%-- Brand column: logo, tagline, and social links --%>
		<div class="footer-brand">
			<a href="${pageContext.request.contextPath}/" style="display:flex;align-items:center;gap:8px;text-decoration:none;">
				<img src="${pageContext.request.contextPath}/images/logo.png" alt="MindEase" style="width:36px;height:36px;object-fit:contain;flex-shrink:0;">
				<span style="font-weight:700;font-size:1.1rem;color:#ffffff;">MindEase</span>
			</a>
			<p>Your daily companion for better mental clarity, emotional balance, and meaningful progress.</p>
			<div class="social-row">
				<a class="social-btn" href="https://twitter.com" target="_blank" rel="noopener noreferrer" aria-label="Twitter">X</a>
				<a class="social-btn" href="https://instagram.com" target="_blank" rel="noopener noreferrer" aria-label="Instagram">IG</a>
				<a class="social-btn" href="https://facebook.com" target="_blank" rel="noopener noreferrer" aria-label="Facebook">f</a>
				<a class="social-btn" href="https://linkedin.com" target="_blank" rel="noopener noreferrer" aria-label="LinkedIn">in</a>
			</div>
		</div>

		<%-- Navigation column: quick internal links --%>
		<div>
			<h3 class="footer-title">Quick Links</h3>
			<div class="footer-links">
				<a href="${pageContext.request.contextPath}/#hero">Home</a>
				<a href="${pageContext.request.contextPath}/about">About</a>
				<a href="${pageContext.request.contextPath}/#importance">Why It Matters</a>
				<a href="${pageContext.request.contextPath}/#how-it-works">How It Works</a>
			</div>
		</div>

		<%-- Platform column: auth links and support details --%>
		<div>
			<h3 class="footer-title">Platform</h3>
			<div class="footer-links">
				<a href="${pageContext.request.contextPath}/login">Login</a>
				<a href="${pageContext.request.contextPath}/register">Register</a>
				<span>support@mindease.app</span>
				<span>+977 9842236927</span>
			</div>
		</div>

		<%-- Contact column: short prompt and modal trigger --%>
		<div>
			<h3 class="footer-title">Get in Touch</h3>
			<div class="contact-cta">
				<p class="contact-cta-text">Have a question? We'd love to hear from you. Reach out anytime.</p>
				<button class="contact-cta-btn" type="button" data-contact-open>Contact Us</button>
			</div>
		</div>
	</div>

	<%-- Footer bottom: legal and policy placeholders --%>
	<div class="footer-bottom">
		<span>&copy; 2026 MindEase. All rights reserved.</span>
		<div class="footer-bottom-links">
			<span style="color:#6b7280;font-size:0.82rem;cursor:default;">Privacy</span>
			<span style="color:#6b7280;font-size:0.82rem;cursor:default;">Terms</span>
			<span style="color:#6b7280;font-size:0.82rem;cursor:default;">Cookies</span>
		</div>
	</div>
</section>

<%-- Contact modal: UI-only support form dialog --%>
<div class="contact-modal" id="contactModal" aria-hidden="true" role="dialog" aria-modal="true">
	<div class="contact-modal-overlay" id="contactModalOverlay"></div>
	<div class="contact-modal-card" role="document">
		<div class="contact-modal-header">
			<div>
				<p class="contact-modal-kicker">Get in touch</p>
				<h3>Contact Us</h3>
			</div>
			<button class="contact-modal-close" type="button" id="contactModalClose" aria-label="Close contact form">&times;</button>
		</div>
		<div class="contact-modal-body">
			<%-- Contact form: name, email, subject, and message --%>
			<form id="contactForm" class="contact-form" novalidate>
				<label class="sr-only" for="contactName">Name</label>
				<input id="contactName" name="name" type="text" placeholder="Your name" required>

				<label class="sr-only" for="contactEmail">Email</label>
				<input id="contactEmail" name="email" type="email" placeholder="Your email" required>

				<label class="sr-only" for="contactSubject">Subject</label>
				<input id="contactSubject" name="subject" type="text" placeholder="Subject" required>

				<label class="sr-only" for="message">Message</label>
				<textarea id="message" name="message" placeholder="Write your message..." maxlength="500" required></textarea>

				<div class="counter-row">
					<span>Characters</span>
					<span id="charCount">0/500</span>
				</div>

				<button class="btn contact-submit" type="submit">Send Message</button>
				<div id="contactStatus" role="status" aria-live="polite"></div>
			</form>
		</div>
	</div>
</div>

<%-- Modal script: open/close handling and form feedback --%>
<script>
	(function () {
		const contactModal = document.getElementById('contactModal');
		if (!contactModal) return;

		const contactOverlay = document.getElementById('contactModalOverlay');
		const contactClose = document.getElementById('contactModalClose');
		const contactForm = document.getElementById('contactForm');
		const contactStatus = document.getElementById('contactStatus');
		const contactOpenButtons = document.querySelectorAll('[data-contact-open]');
		const messageField = document.getElementById('message');
		const charCount = document.getElementById('charCount');

		function updateCount() {
			if (!messageField || !charCount) return;
			charCount.textContent = messageField.value.length + '/500';
		}

		function openContactModal() {
			contactModal.classList.add('open');
			contactModal.setAttribute('aria-hidden', 'false');
			document.body.classList.add('modal-open');
			if (contactForm) {
				contactForm.reset();
			}
			if (contactStatus) {
				contactStatus.textContent = '';
				contactStatus.className = '';
			}
			updateCount();
			const nameField = document.getElementById('contactName');
			if (nameField) {
				setTimeout(function () {
					nameField.focus();
				}, 60);
			}
		}

		function closeContactModal() {
			contactModal.classList.remove('open');
			contactModal.setAttribute('aria-hidden', 'true');
			document.body.classList.remove('modal-open');
		}

		if (contactOpenButtons.length > 0) {
			contactOpenButtons.forEach(function (button) {
				button.addEventListener('click', function (event) {
					event.preventDefault();
					openContactModal();
				});
			});
		}

		if (contactOverlay) {
			contactOverlay.addEventListener('click', closeContactModal);
		}

		if (contactClose) {
			contactClose.addEventListener('click', closeContactModal);
		}

		window.addEventListener('keydown', function (event) {
			if (event.key === 'Escape' && contactModal.classList.contains('open')) {
				closeContactModal();
			}
		});

		if (contactForm) {
			contactForm.addEventListener('submit', function (event) {
				event.preventDefault();
				if (!contactForm.checkValidity()) {
					contactForm.reportValidity();
					return;
				}
				if (contactStatus) {
					contactStatus.className = 'success';
					contactStatus.innerHTML = '<span class="checkmark">&check;</span> Message Sent!';
				}
				setTimeout(closeContactModal, 2500);
			});
		}

		if (messageField) {
			messageField.addEventListener('input', updateCount);
			updateCount();
		}
	})();
</script>
