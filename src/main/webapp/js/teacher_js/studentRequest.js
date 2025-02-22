/**
 * Retrieves a list of students for a specified class ID from the server.
 * @param {string} classId - The ID of the class for which students are to be retrieved.
 * @returns {Promise} A promise that resolves to the fetched student data.
 */
function retrieveStudents(classId) {
    return fetch(`/api/student?classID=${classId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Students:', data);
            return data;
        })
        .catch(error => {
            console.error('Failed to fetch:', error);
        });
}

/**
 * Adds a new student to the server.
 * @param {Object} data - The student data to be added.
 */
function addNewStudent(data) {
    fetch('../../api/student', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create new student.');
            } else {
                window.location.href = `../../teacherPages/classPage/index.html`;
            }
        })
        .catch(error => {
            console.error('Error fetching', error);
        });
}

/**
 * Updates an existing student record on the server.
 * @param {Object} student - The updated student data.
 */
function updateStudent(student) {
    fetch('../../api/student/', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(student)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update student.');
            } else {
                console.log('Student updated successfully');
                window.location.href = `../../teacherPages/classPage/index.html`;
            }
        })
        .catch(error => {
            console.error('Error fetching', error);
        });
}

/**
 * Retrieves and displays details of a specific student.
 */
async function showStudent() {
    const classId = getQueryParam('classId');
    const studentId = getQueryParam('studentId');

    try {
        const response = await fetch(`../../api/student/${classId}/${studentId}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const student = await response.json();
        console.log('Student:', student);

        const newStudentName = student.name;
        const newStudentSurname = student.surname;
        const newClass = classId;
        const newEmail = student.email;

        document.getElementById('student').textContent = `${newStudentName} ${newStudentSurname}`;
        document.getElementById('classTitle').textContent = `Class ID: ${classId}`;
        document.getElementById('name').value = `${newStudentName} ${newStudentSurname}`;
        document.getElementById('class').value = newClass;
        document.getElementById('email').value = newEmail;

    } catch (error) {
        console.error('Error fetching student:', error);
    }
}

/**
 * Deletes a student record from the server.
 */
async function deleteStudent() {
    const classId = getQueryParam('classId');
    const studentId = getQueryParam('studentId');

    if (classId && studentId) {
        try {
            const response = await fetch(`../../api/student/${studentId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                console.log('Student deleted successfully');
                window.location.href = `../../teacherPages/classPage/index.html`;
            } else {
                const errorText = await response.text();
                alert('Error: ' + errorText);
                console.log(errorText);
            }
        } catch (error) {
            alert('Error: ' + error.message);
            console.log(error.message);
        }
    }
}

/**
 * Retrieves a query parameter value from the current URL.
 * @param {string} param - The parameter name to retrieve.
 * @returns {string} The value of the query parameter.
 */
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}
