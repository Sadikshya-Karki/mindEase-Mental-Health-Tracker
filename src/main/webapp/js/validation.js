document.addEventListener("DOMContentLoaded", function () {
    // Registration form validation: keep passwords in sync before submit.
    var form = document.getElementById("registerForm");
    if (form) {
        form.addEventListener("submit", function (e) {
            var password = document.getElementById("password").value;
            var confirm = document.getElementById("confirmPassword").value;
            var errorDiv = document.getElementById("passError");
            // Show inline error and block submit when the passwords differ.
            if (password !== confirm) {
                e.preventDefault();
                errorDiv.style.display = "block";
            } else {
                errorDiv.style.display = "none";
            }
        });
    }
});
