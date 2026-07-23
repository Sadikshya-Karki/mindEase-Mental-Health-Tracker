<%-- Login page: user authentication entry point --%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/logo.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=5.0, user-scalable=yes">
    <title>MindEase - Login</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        html, body { height: 100%; }
        body { font-family: "Segoe UI", Arial, sans-serif; background: #f8fffe; color: #1f2937; overflow: hidden; }
        .auth-page { min-height: 100vh; min-height: 100dvh; height: 100vh; height: 100dvh; display: flex; overflow: hidden; }

        .left-panel {
            width: 45%;
            background: linear-gradient(135deg, #1a2e2e 0%, #2b7a78 55%, #3aafa9 100%);
            position: relative;
            overflow: hidden;
            padding: 32px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .left-panel .blob {
            position: absolute;
            width: 300px;
            height: 300px;
            border-radius: 50%;
            filter: blur(70px);
            pointer-events: none;
        }

        .blob-lavender { top: -80px; right: -70px; background: rgba(167, 139, 250, 0.2); }
        .blob-pink { bottom: -100px; left: -80px; background: rgba(249, 168, 212, 0.2); }

        .brand {
            position: absolute;
            top: 32px;
            left: 32px;
            color: #ffffff;
            font-size: 1.35rem;
            font-weight: 700;
        }

        .left-content { max-width: 420px; width: 100%; position: relative; z-index: 2; }
        .left-image {
            width: 100%;
            max-width: 320px;
            border-radius: 16px;
            margin-bottom: 28px;
            box-shadow: 0 14px 30px rgba(0, 0, 0, 0.25);
            display: block;
        }

        .left-title { color: #ffffff; font-size: 1.6rem; font-weight: 700; line-height: 1.3; margin-bottom: 10px; }
        .left-title .mint { color: #a7f3d0; }
        .left-subtitle { color: rgba(255, 255, 255, 0.7); font-size: 0.88rem; margin-bottom: 24px; }

        .mood-row { display: flex; flex-wrap: wrap; gap: 8px; }
        .mood-chip {
            border-radius: 999px;
            padding: 6px 14px;
            font-size: 0.82rem;
            font-weight: 500;
            border: 1px solid rgba(255, 255, 255, 0.15);
            background: rgba(255, 255, 255, 0.12);
            color: rgba(255, 255, 255, 0.75);
            transition: all 0.4s;
        }

        .mood-chip[data-style="happy"].active,
        .mood-chip[data-style="peaceful"].active { background: #d1fae5; color: #065f46; }
        .mood-chip[data-style="calm"].active { background: #ede9fe; color: #5b21b6; }
        .mood-chip[data-style="motivated"].active,
        .mood-chip[data-style="loved"].active { background: #fce7f3; color: #9d174d; }
        .mood-chip[data-style="grateful"].active,
        .mood-chip[data-style="hopeful"].active { background: #fef3c7; color: #92400e; }
        .mood-chip[data-style="mindful"].active { background: #e0f2fe; color: #075985; }

        .stats-row { margin-top: 24px; display: flex; gap: 10px; }
        .stat-card {
            flex: 1;
            background: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.15);
            border-radius: 12px;
            padding: 14px 16px;
        }

        .stat-icon { font-size: 1rem; margin-bottom: 4px; display: block; }
        .stat-value { color: #ffffff; font-weight: 700; font-size: 1rem; }
        .stat-label { color: rgba(255, 255, 255, 0.6); font-size: 0.75rem; }

        .right-panel {
            width: 55%;
            position: relative;
            overflow: hidden;
            background: #f8fffe;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 24px;
        }

        .right-panel .blob { position: absolute; width: 300px; height: 300px; border-radius: 50%; filter: blur(70px); pointer-events: none; }
        .blob-mint { top: -90px; right: -90px; background: rgba(209, 250, 229, 0.75); }
        .blob-soft-lavender { bottom: -90px; left: -90px; background: rgba(237, 233, 254, 0.75); }

        .form-shell { max-width: 480px; width: 100%; padding: 48px 40px; position: relative; z-index: 2; }
        .badge {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            background: #e6f4f3;
            color: #2b7a78;
            border-radius: 999px;
            padding: 6px 14px;
            font-size: 0.78rem;
            font-weight: 600;
            margin-bottom: 20px;
        }

        h1 { color: #1a2e2e; font-size: 2rem; font-weight: 700; line-height: 1.2; margin-bottom: 8px; }
        h1 .accent { color: #2b7a78; }
        .subtitle { color: #6b7280; font-size: 0.95rem; margin-bottom: 28px; }

        .alert {
            border-radius: 8px;
            padding: 10px 14px;
            margin-bottom: 16px;
            font-size: 0.88rem;
        }

        .alert.error { background: rgba(239, 68, 68, 0.08); border: 1px solid rgba(239, 68, 68, 0.2); color: #b91c1c; }
        .alert.success { background: rgba(16, 185, 129, 0.08); border: 1px solid rgba(16, 185, 129, 0.2); color: #047857; }

        .field { position: relative; margin-bottom: 18px; }
        .field label { display: block; font-size: 0.85rem; font-weight: 500; color: #374151; margin-bottom: 6px; }
        .field input {
            width: 100%;
              padding: 11px 14px;
            border: 1.5px solid #e5e7eb;
            border-radius: 10px;
            background: #ffffff;
            font-size: 0.95rem;
            color: #1f2937;
            outline: none;
            transition: all 0.25s;
        }

        .field input:focus { border-color: #2b7a78; background: #f0fbfa; }
        .field input:focus + .input-icon { color: #0d9488; }
        .input-icon {
            position: absolute;
            left: 14px;
            top: 50%;
            transform: translateY(-50%);
            color: #9ca3af;
            font-size: 1rem;
            pointer-events: none;
        }

        .toggle-password {
            position: absolute;
            right: 14px;
            top: 50%;
            transform: translateY(-50%);
            color: #9ca3af;
            font-size: 1rem;
            cursor: pointer;
            user-select: none;
              line-height: 0;
              display: flex;
              align-items: center;
        }

            input[type="password"]::-ms-reveal,
            input[type="password"]::-ms-clear {
              display: none !important;
              visibility: hidden;
              pointer-events: none;
            }

            .login-options {
              display: flex;
              align-items: center;
              gap: 12px;
              margin-bottom: 16px;
            }

        .remember-option {
            display: flex;
            align-items: center;
            gap: 8px;
        }


        .submit-btn {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #2b7a78, #3aafa9);
            color: #ffffff;
            font-size: 1rem;
            font-weight: 600;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            box-shadow: 0 4px 20px rgba(43, 122, 120, 0.35);
            transition: all 0.3s;
        }

        .submit-btn:hover { transform: translateY(-2px); box-shadow: 0 8px 24px rgba(43, 122, 120, 0.4); }

        .bottom-link { margin-top: 18px; text-align: center; color: #6b7280; font-size: 0.92rem; }
        .bottom-link a { color: #2b7a78; font-weight: 600; }

        @media (max-width: 768px) {
            body { overflow-y: auto; }
            .left-panel { display: none; }
            .right-panel { width: 100%; padding: 12px; }
            .form-shell { padding: 36px 18px; }
        }

        @media (max-width: 480px) {
            h1 { font-size: 1.6rem; }
            .input-grid { grid-template-columns: 1fr; gap: 0; }
            .social-row { flex-direction: column; }
        }
    </style>
</head>
<body>
<div class="auth-page">
    <%-- Left panel: branding and marketing content shown on desktop --%>
    <aside class="left-panel">
        <div class="blob blob-lavender"></div>
        <div class="blob blob-pink"></div>
        <%-- Brand logo and home nav: positioned for left panel header --%>
        <a href="${pageContext.request.contextPath}/" style="display:flex;align-items:center;gap:8px;text-decoration:none;position:absolute;top:28px;left:28px;">
            <div style="width:38px;height:38px;background:rgba(255,255,255,0.15);border-radius:10px;display:flex;align-items:center;justify-content:center;flex-shrink:0;">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="MindEase" style="width:26px;height:26px;object-fit:contain;">
            </div>
            <span style="font-weight:700;font-size:1.1rem;color:#ffffff;">MindEase</span>
        </a>
      <a href="${pageContext.request.contextPath}/" style="position:absolute;top:32px;right:28px;display:inline-flex;align-items:center;gap:5px;color:rgba(255,255,255,0.70);font-size:0.82rem;text-decoration:none;background:rgba(255,255,255,0.12);border:1px solid rgba(255,255,255,0.20);border-radius:999px;padding:5px 12px;transition:all 0.2s;" onmouseover="this.style.background='rgba(255,255,255,0.20)';this.style.color='#ffffff'" onmouseout="this.style.background='rgba(255,255,255,0.12)';this.style.color='rgba(255,255,255,0.70)'">Home
      </a>

        <div class="left-content">
            <img class="left-image" src="https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=600" alt="Mindfulness landscape">
            <h2 class="left-title">Your daily <span class="mint">mind check-in</span><br>starts here</h2>
            <p class="left-subtitle">Build calm habits one day at a time with small, guided mental wellness moments.</p>

            <%-- Mood chips: cycled through via JavaScript on interval --%>
            <div class="mood-row" id="moodRow">
                <span class="mood-chip" data-style="happy">Happy</span>
                <span class="mood-chip" data-style="calm">Calm</span>
                <span class="mood-chip" data-style="motivated">Motivated</span>
                <span class="mood-chip" data-style="peaceful">Peaceful</span>
                <span class="mood-chip" data-style="grateful">Grateful</span>
                <span class="mood-chip" data-style="mindful">Mindful</span>
                <span class="mood-chip" data-style="loved">Loved</span>
                <span class="mood-chip" data-style="hopeful">Hopeful</span>
            </div>

            <%-- Stats cards: display platform highlights --%>
            <div class="stats-row">
                <div class="stat-card">

                    <div class="stat-value">12,400+</div>
                    <div class="stat-label">Active Users</div>
                </div>
                <div class="stat-card">

                    <div class="stat-value">98%</div>
                    <div class="stat-label">Feel Better</div>
                </div>
                <div class="stat-card">

                    <div class="stat-value">100%</div>
                    <div class="stat-label">Private &amp; Safe</div>
                </div>
            </div>
        </div>
    </aside>

    <main class="right-panel">
        <div class="blob blob-mint"></div>
        <div class="blob blob-soft-lavender"></div>

        <%-- Login form shell: right-side authentication form container --%>
        <div class="form-shell">
            <div class="badge"> WELCOME BACK</div>
            <h1>Good to see<br><span class="accent">you again </span></h1>
            <p class="subtitle">Sign in to continue your wellness journey.</p>

            <%-- Server-side error alert: only displayed if error message is set --%>
            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>
            <%-- Server-side success alert: only displayed if success message is set --%>
            <c:if test="${not empty success}">
                <div class="alert success">${success}</div>
            </c:if>

            <%-- Login form: posts to /login with email and password --%>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="field">
                    <label for="email">Email</label>
                    <input id="email" type="email" name="email" required placeholder="Enter your email">
                </div>

                <div class="field">
                    <label for="password">Password</label>
                    <div style="position:relative;">
                        <input id="password" type="password" name="password" autocomplete="new-password" required placeholder="Enter your password" style="width:100%;padding:11px 40px 11px 14px;">
                        <%-- Password visibility toggle: show/hide button controlled by JavaScript --%>
                        <button type="button" class="eye-toggle" onclick="togglePassword(this)" style="position:absolute;right:12px;top:50%;transform:translateY(-50%);background:none;border:none;cursor:pointer;color:#9ca3af;padding:0;display:none;align-items:center;justify-content:center;width:20px;height:20px;">
                            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                                <circle cx="12" cy="12" r="3"/>
                            </svg>
                        </button>
                    </div>
                </div>

                          <div class="login-options">
                            <%-- Remember me checkbox: persists session token in cookie if checked --%>
                            <div class="remember-option">
                              <input type="checkbox" name="rememberMe" id="rememberMe"
                                   style="width:16px;height:16px;accent-color:#2b7a78;cursor:pointer;">
                              <label for="rememberMe"
                                   style="font-size:0.85rem;color:#6b7280;cursor:pointer;">Remember me</label>
                            </div>
                          </div>
                <button type="submit" class="submit-btn">Sign In </button>
            </form>


            <%-- Registration link: direct new users to sign-up page --%>
            <p class="bottom-link">Don't have an account? <a href="${pageContext.request.contextPath}/register">Create new account</a></p>
        </div>
    </main>
</div>

<%-- JavaScript: mood chip rotation, password toggle, form utilities --%>
<script>
    (function () {
        var chips = document.querySelectorAll(".mood-chip");
        var activeIndex = 0;

        function activateChip(index) {
            for (var i = 0; i < chips.length; i++) {
                chips[i].classList.remove("active");
            }
            chips[index].classList.add("active");
        }

        if (chips.length > 0) {
            activateChip(activeIndex);
            setInterval(function () {
                activeIndex = (activeIndex + 1) % chips.length;
                activateChip(activeIndex);
            }, 1800);
        }

        document.querySelectorAll('input[type="password"], input[type="text"]').forEach(function(input) {
          const btn = input.parentElement.querySelector('.eye-toggle');
          if (!btn) return;
          btn.style.display = 'none';
          input.addEventListener('input', function() {
            btn.style.display = this.value.length > 0 ? 'flex' : 'none';
          });
        });

        window.togglePassword = function togglePassword(btn) {
          const input = btn.parentElement.querySelector('input');
          if (!input) return;
          if (input.type === 'password') {
            input.type = 'text';
            btn.style.color = '#2b7a78';
          } else {
            input.type = 'password';
            btn.style.color = '#9ca3af';
          }
        }
    })();
</script>
</body>
</html>

