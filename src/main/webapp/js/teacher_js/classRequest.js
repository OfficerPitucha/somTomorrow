/**
 * Retrieves the value of a query parameter from the current URL.
 *
 * @param {string} param - The name of the query parameter to retrieve
 * @returns {string | null} The value of the query parameter, or null if not found
 */
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

/**
 * Fetches all classes associated with a teacher from the server and displays them.
 * Retrieves the teacher ID from the query parameters.
 */
document.addEventListener('DOMContentLoaded', function() {
    fetchAllClasses();
});

/**
 * Adds event listener to the "Add Class" button to handle class creation.
 * Sends a POST request to the server to create a new class with the provided name.
 * Retrieves the teacher ID from the query parameters.
 */
document.addEventListener('DOMContentLoaded', function() {
    const addClassBtn = document.getElementById('add-class-btn');
    const classNameInput = document.getElementById('class-name');

    addClassBtn.addEventListener('click', async () => {
        const className = classNameInput.value.trim();
        if (className) {
            const teacherId = getQueryParam("teacherId");

            const formData = {
                name: className
            };

            try {
                const response = await fetch(`../../api/class/${teacherId}`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                });

                if (response.ok) {
                    await fetchAllClasses();
                    classNameInput.value = '';
                    document.getElementById('popup-form').style.display = 'none';
                    document.getElementById('popup-overlay').style.display = 'none';
                } else {
                    const errorText = await response.text();
                    alert("Error: " + errorText);
                }
            } catch (error) {
                alert("Error: " + error.message);
            }
        } else {
            alert("Please fill out the class name.");
        }
    });
});

/**
 * Fetches all classes associated with a teacher from the server.
 * Retrieves the teacher ID from the query parameters.
 */
async function fetchAllClasses() {
    try {
        const response = await fetch(`../../api/class/`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const classes = await response.json();
        console.log('Classes:', classes);

        const teacherId = getQueryParam("teacherId");

        if (!teacherId) {
            console.error('Error: teacherId not found in URL');
            return;
        }

        displayClasses(classes, teacherId);
    } catch (error) {
        console.error('Error fetching classes:', error);
    }
}

/**
 * Displays classes in the DOM based on the provided classes data.
 *
 * @param {Array<Object>} classes - An array of class objects to display
 * @param {string} teacherId - The ID of the teacher associated with the classes
 */
function displayClasses(classes, teacherId) {
    const cardParent = document.getElementById('card-parent');

    if (!cardParent) {
        console.error('Error: card-parent element not found');
        return;
    }

    cardParent.innerHTML = '';

    classes.forEach((classItem) => {
        console.log(`Setting up class: ${classItem.classId} - ${classItem.name}`); // Debug statement

        const newCard = document.createElement('a');
        newCard.href = `../classPage/index.html?classId=${classItem.classId}&teacherId=${teacherId}`;
        newCard.className = 'card2';
        newCard.style.textDecoration = 'none';
        newCard.id = classItem.classId;
        newCard.innerHTML = `
            <div class="text-content">
                <div class="title3">Class</div>
                <div class="subtitle1">${classItem.name}</div>
            </div>
        `;

        newCard.addEventListener('click', () => {
            window.location.href = `../classPage/index.html?classId=${classItem.classId}`;
        });

        cardParent.appendChild(newCard);
    });
}

/**
 * Retrieves and displays details of a specific class.
 * Retrieves the class ID from the query parameters.
 */
async function showClassDetails() {
    const classId = getQueryParam('classId');

    if (!classId) {
        console.error('Error: classId not found in URL');
        return;
    }

    try {
        const response = await fetch(`../../api/class/${classId}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const classe = await response.json();
        console.log('Class:', classe);

        const newClassName = classe.name;
        const newDescription = `Student Count ${classe.studentCount}`;

        document.getElementById('className').textContent = newClassName;
        document.getElementById('description').textContent = newDescription;

        await showStudentDetails(classe.classId);
        await showLessonDetails(classe.classId);

    } catch (error) {
        console.error('Error fetching class:', error);
    }
}

/**
 * Retrieves and displays details of all students in a specific class.
 * Retrieves the class ID and teacher ID from the query parameters.
 * Displays student details in the DOM.
 *
 * @param {string} classId - The ID of the class for which to fetch student details
 */
async function showStudentDetails(classId) {
    const teacherId = getQueryParam("teacherId");

    if (!classId || !teacherId) {
        console.error('Error: classId or teacherId not found in URL');
        return;
    }

    try {
        const response = await fetch(`../../api/student/${classId}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const students = await response.json();
        console.log('Students:', students);

        const studentList = document.getElementById('studentList');
        studentList.innerHTML = '';

        students.forEach((student) => {
            const newStudent = document.createElement('div');
            newStudent.className = 'item2';
            newStudent.id = `${student.student_id}`;

            newStudent.innerHTML = `
                <a href="../studentDetails/index.html?teacherId=${teacherId}&classId=${getQueryParam("classId")}&studentId=${student.student_id}" style="color: black; text-decoration: none;" class="frame-parent2">
                    <div class="frame">
                        <div class="icon">👩‍🎓</div>
                    </div>
                    <div class="title-frame">
                        <div class="title4">${student.name} ${student.surname}</div>
                    </div>
                </a>
            `;

            studentList.appendChild(newStudent);
        });

    } catch (error) {
        console.error('Error fetching students:', error);
    }
}

/**
 * Retrieves and displays details of all lessons in a specific class.
 * Retrieves the class ID and teacher ID from the query parameters.
 * Displays lesson details in the DOM.
 *
 * @param {string} classId - The ID of the class for which to fetch lesson details
 */
async function showLessonDetails(classId) {
    const teacherId = getQueryParam("teacherId");

    if (!classId || !teacherId) {
        console.error('Error: classId or teacherId not found in URL');
        return;
    }

    try {
        const response = await fetch(`../../api/lesson/${classId}/lessons`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const lessons = await response.json();
        console.log('Lessons:', lessons);

        const lessonList = document.getElementById('lessonList');
        lessonList.innerHTML = '';

        lessons.forEach((lesson) => {
            const newLesson = document.createElement('div');
            newLesson.className = 'item2';
            newLesson.id = `${lesson.lessonId}`;

            newLesson.innerHTML = `
                <a href="../lessonsPage/index.html?teacherId=${teacherId}&classId=${classId}&lessonId=${lesson.lessonId}" style="color: black; text-decoration: none;" class="frame-parent2">
                    <div class="frame">
                        <div class="icon">📚</div>
                    </div>
                    <div class="title-frame">
                        <div class="title4">${lesson.title}</div>
                    </div>
                </a>
            `;

            lessonList.appendChild(newLesson);
        });

    } catch (error) {
        console.error('Error fetching lessons:', error);
    }
}

/**
 * Deletes a class from the server based on the class ID.
 * Retrieves the class ID and teacher ID from the query parameters.
 * Redirects to the teacher's homepage after successful deletion.
 */
async function deleteClass() {
    const classId = getQueryParam("classId");
    const teacherId = getQueryParam("teacherId");

    if (!classId) {
        alert("Class ID is required");
        return;
    }

    try {
        const response = await fetch(`../../api/class/${classId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            console.log('Class deleted successfully');
            window.location.href = `../teacherHomePage/homepageTeacher.html?teacherId=${teacherId}`;
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
