<%-- Shared footer + contact modal styles --%>
<style>
	#footer {
		background: #1a2e2e;
		color: #cbd5e1;
		padding: 48px 5% 26px;
		margin-top: 0;
	}

	.footer-grid {
		display: grid;
		grid-template-columns: repeat(4, minmax(0, 1fr));
		gap: 28px;
		margin-bottom: 28px;
	}

	.footer-brand p {
		color: rgba(226, 232, 240, 0.75);
		max-width: 280px;
	}

	.social-row {
		display: flex;
		gap: 8px;
		margin-top: 16px;
	}

	.social-btn {
		width: 36px;
		height: 36px;
		border-radius: 50%;
		border: 1px solid rgba(255, 255, 255, 0.25);
		display: inline-flex;
		align-items: center;
		justify-content: center;
		color: rgba(255, 255, 255, 0.8);
		transition: 0.3s;
	}

	.social-btn:hover {
		color: #3aafa9;
		border-color: #3aafa9;
	}

	.footer-title {
		color: #ffffff;
		font-size: 1.03rem;
		margin-bottom: 12px;
	}

	.footer-links {
		display: flex;
		flex-direction: column;
		gap: 8px;
	}

	.footer-links a,
	.footer-links span {
		color: rgba(226, 232, 240, 0.72);
		transition: color 0.2s;
	}

	.footer-links a:hover {
		color: #ffffff;
	}

	.footer-bottom {
		border-top: 1px solid rgba(255, 255, 255, 0.15);
		padding-top: 16px;
		display: flex;
		align-items: center;
		justify-content: space-between;
		gap: 16px;
		color: rgba(226, 232, 240, 0.62);
		font-size: 0.9rem;
		flex-wrap: wrap;
	}

	.footer-bottom-links {
		display: flex;
		gap: 14px;
	}

	.contact-cta {
		display: flex;
		flex-direction: column;
		gap: 14px;
	}

	.contact-cta-text {
		color: rgba(226, 232, 240, 0.72);
		font-size: 0.92rem;
		line-height: 1.6;
	}

	.contact-cta-btn {
		display: inline-flex;
		align-items: center;
		justify-content: center;
		gap: 8px;
		border-radius: 999px;
		padding: 10px 20px;
		font-weight: 600;
		color: #ffffff;
		background: linear-gradient(135deg, #2b7a78, #3aafa9);
		border: none;
		cursor: pointer;
		box-shadow: 0 12px 24px rgba(43, 122, 120, 0.32);
	}

	.contact-cta-btn:hover {
		transform: translateY(-1px);
		box-shadow: 0 16px 28px rgba(43, 122, 120, 0.4);
	}

	.contact-form {
		display: flex;
		flex-direction: column;
		gap: 10px;
	}

	.sr-only {
		position: absolute;
		width: 1px;
		height: 1px;
		padding: 0;
		margin: -1px;
		overflow: hidden;
		clip: rect(0, 0, 0, 0);
		border: 0;
		white-space: nowrap;
	}

	.contact-form input,
	.contact-form textarea {
		width: 100%;
		border-radius: 10px;
		border: 1px solid rgba(15, 23, 42, 0.15);
		background: #f8fafc;
		color: #0f172a;
		padding: 10px 12px;
		font: inherit;
	}

	.contact-form textarea {
		min-height: 105px;
		resize: vertical;
	}

	.contact-form input:focus,
	.contact-form textarea:focus {
		outline: none;
		border-color: #3aafa9;
	}

	body.modal-open {
		overflow: hidden;
	}

	.contact-modal {
		position: fixed;
		inset: 0;
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 24px;
		opacity: 0;
		pointer-events: none;
		transition: opacity 0.25s ease;
		z-index: 2000;
	}

	.contact-modal.open {
		opacity: 1;
		pointer-events: auto;
	}

	.contact-modal-overlay {
		position: absolute;
		inset: 0;
		background: rgba(15, 23, 42, 0.45);
		backdrop-filter: blur(2px);
	}

	.contact-modal-card {
		position: relative;
		width: min(450px, 100%);
		background: #ffffff;
		border-radius: 20px;
		box-shadow: 0 28px 60px rgba(15, 23, 42, 0.25);
		overflow: hidden;
		transform: translateY(20px);
		transition: transform 0.25s ease;
	}

	.contact-modal.open .contact-modal-card {
		transform: translateY(0);
	}

	.contact-modal-header {
		background: linear-gradient(135deg, #2b7a78, #3aafa9);
		color: #ffffff;
		padding: 20px 22px;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.contact-modal-kicker {
		font-size: 0.7rem;
		letter-spacing: 0.1em;
		text-transform: uppercase;
		opacity: 0.85;
		margin-bottom: 6px;
	}

	.contact-modal-close {
		background: rgba(255, 255, 255, 0.2);
		border: none;
		color: #ffffff;
		width: 34px;
		height: 34px;
		border-radius: 50%;
		font-size: 1.3rem;
		cursor: pointer;
		transition: background 0.2s ease;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		line-height: 1;
	}

	.contact-modal-close:hover {
		background: rgba(255, 255, 255, 0.35);
	}

	.contact-modal-body {
		padding: 22px;
	}

	.contact-form .counter-row {
		margin-top: -4px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		color: #64748b;
		font-size: 0.82rem;
	}

	.contact-submit {
		border: none;
		color: #ffffff;
		background: linear-gradient(135deg, #2b7a78, #3aafa9);
	}

	#contactStatus {
		min-height: 18px;
		font-size: 0.88rem;
		margin-top: 6px;
		line-height: 1.4;
		word-break: break-word;
	}

	#contactStatus.success {
		display: flex;
		align-items: center;
		gap: 6px;
		color: #16a34a;
		flex-wrap: wrap;
	}

	#contactStatus .checkmark {
		display: inline-flex;
		align-items: center;
		justify-content: center;
		width: 18px;
		height: 18px;
		border-radius: 50%;
		background: rgba(34, 197, 94, 0.15);
		color: #16a34a;
		font-size: 0.75rem;
		animation: checkPop 0.35s ease;
	}

	@keyframes checkPop {
		0% {
			transform: scale(0.6);
			opacity: 0;
		}
		100% {
			transform: scale(1);
			opacity: 1;
		}
	}

	@media (max-width: 1024px) {
		.footer-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); }
	}

	@media (max-width: 768px) {
		.footer-grid { grid-template-columns: 1fr; gap: 24px; }
		.footer-bottom { flex-direction: column; text-align: center; gap: 8px; }
		.footer-bottom-links { justify-content: center; }
	}

	@media (max-width: 480px) {
		.contact-modal { padding: 16px; }
		.contact-modal-card { border-radius: 16px; }
	}
</style>

