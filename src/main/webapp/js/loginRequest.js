/**
 * The first method to log in is hard coded for the second sprint.
 */

/*function handleLogin() {
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();

        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;

        if (validateTeacher(username, password)) {
            window.location.href = 'teacherPages/teacherHomePage/homepageTeacher.html';
        } else if (validateStudent(username, password)) {
            window.location.href = 'studentPages/studentHomePage/index.html';
        } else {
            alert('Username or Password is incorrect');
        }
    });
}

function validateTeacher(username, password) {
    return username.startsWith('t') && password === 'teacher';
}

function validateStudent(username, password) {
    return username.startsWith('s') && password === 'student';
}*/

/**
 * This login uses the data stored in the database. We first get the username and the password
 * provided by the user. This information is stored in a json file and send as a post request to
 * the /login/login.html route. If the response received is teacher, then the teacher homepage is
 * shown. Likewise for student. If response is anything else, the user is informed that the
 * username or password is wrong. (Later when we have different teachers and student, we can refine
 * this method.)
 */


function extractNumbers(username) {
    const regex = /^[A-Za-z](\d+)$/;
    const match = username.match(regex);

    if (match) {
        return match[1];
    } else {
        return 0;
    }
}

/**
 * Handles the login process using username and password input. Sends a POST request
 * to the server with the provided credentials and redirects the user based on the role
 * returned from the server response.
 */
function handleLogin() {
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();

        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;


        const requestData = {
            username: username,
            password: password
        };

        fetch('./api/login', {
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
                            throw new Error(error.message || 'Incorrect username or password');
                        } catch {
                            throw new Error(text || 'An unknown error occurred');
                        }
                    });
                }
            })
            .then(data => {
                console.log(data)
                if (data.role === "teacher") {
                    window.location.href = `teacherPages/teacherHomePage/homepageTeacher.html?teacherId=${extractNumbers(username)}`;
                } else if (data.role === "student") {
                    window.location.href = `studentPages/studentHomePage/index.html?studentId=${extractNumbers(username)}`;
                } else {
                    alert('Username or Password is incorrect');
                }
            })
            .catch(error => {
                console.error('Login Error:', error.message);
                alert(error.message);
            });
    });
}