
        function validateForm() {
        let valid = true;

        // Validate last name
        const firstName = document.getElementById('firstName').value;
        const firstNameError = document.getElementById('firstNameError');
        if (!firstName || firstName.length < 2) {
        firstNameError.textContent = "First name must be at least 2 characters.";
        valid = false;
        } else {
            firstNameError.textContent = '';
        }

        // Validate first nam
        const lastName = document.getElementById('lastName').value;
        const lastNameError = document.getElementById('lastNameError');
        if (!lastName || lastName.length < 2) {
        lastNameError.textContent = "Last name must be at least 2 characters.";
        valid = false;
        } else {
            lastNameError.textContent = '';
        }

        // Validate email
        const email = document.getElementById('email').value;
        const emailError = document.getElementById('emailError');
        const emailPattern = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
        if (!email || !emailPattern.test(email)) {
        emailError.textContent = "Invalid email address.";
        valid = false;
        } else {
            emailError.textContent = '';
        }

        // Validate password
        const password = document.getElementById('password').value;
        const passwordError = document.getElementById('passwordError');
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
        if (!password || !passwordPattern.test(password)) {
        passwordError.textContent = "Password must be at least 8 characters, including one uppercase, one lowercase letter, and one digit.";
        valid = false;
        } else {
            passwordError.textContent = '';
        }
        // Validate the repeat password field
        const repeatPassword = document.getElementById('repeatPassword').value;
        const repeatPasswordError = document.getElementById('repeatPasswordError');

        if (repeatPassword !== password) {
                repeatPasswordError.textContent = "Passwords do not match.";
                valid = false;
        } else {
                repeatPasswordError.textContent = '';
        }

        // Validate role
        const role = document.getElementById('role').value;
        const roleError = document.getElementById('roleError');
        if (!role) {
        roleError.textContent = "Role must be selected.";
        valid = false;
        } else {
            roleError.textContent = '';
        }

        return valid;
    }
