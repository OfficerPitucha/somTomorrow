/**
 * Handles the registration process using username, password, and confirm password inputs.
 * Validates username format, checks password match, sends a POST request to the server
 * to register the user, and redirects to the login page upon successful registration.
 */
function handleRegister() {
    document.getElementById('registerForm').addEventListener('submit', function(event) {
        event.preventDefault();

        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;
        let confirmPassword = document.getElementById('confirmPassword').value;

        if (!username.startsWith('S') && !username.startsWith('T')) {
            alert("Username must start with 'S' or 'T'");
            return;
        }

        if (password !== confirmPassword) {
            alert('Passwords do not match');
            return;
        }

        const requestData = {
            username: username,
            password: password
        };

        fetch('./api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.text().then(text => {
                        try {
                            const error = JSON.parse(text);
                            throw new Error(error.message || 'Registration failed');
                        } catch {
                            throw new Error(text || 'An unknown error occurred');
                        }
                    });
                }
            })
            .then(data => {
                alert('Registration successful');
                window.location.href = 'login.html';
            })
            .catch(error => {
                console.error('Registration Error:', error.message);
                alert(error.message);
            });
    });
}
