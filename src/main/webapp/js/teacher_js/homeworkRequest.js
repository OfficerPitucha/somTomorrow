/**
 * Retrieves query parameter from the URL.
 * @param {string} param - The parameter name to retrieve.
 * @returns {string|null} The parameter value if found, otherwise null.
 */
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

/**
 * Fetches and displays all homework associated with a specific class and lesson.
 * @param {string} classId - The ID of the class.
 * @param {string} lessonId - The ID of the lesson.
 */
async function showAllHomework(classId, lessonId) {
    if (classId && lessonId) {
        try {
            const response = await fetch(`../../api/homework/${classId}/${lessonId}`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const homeworks = await response.json();
            console.log('Homework:', homeworks);
            displayHomework(homeworks);
        } catch (error) {
            console.error('Error fetching homework:', error);
        }
    }
}

/**
 * Displays homework items in the DOM.
 * @param {Array} homeworks - An array of homework objects to display.
 */
function displayHomework(homeworks) {
    // Clear existing content
    const homeworkHolder = document.getElementById('homework-holder');
    homeworkHolder.innerHTML = '';

    homeworks.forEach((homework) => {
        // Update the DOM with the new homework
        const newHomework = document.createElement('div');
        newHomework.className = 'row2';

        // Set the inner HTML of the new item
        newHomework.innerHTML = `
        <div class="item">
            <a href="../homeworkDetails/index.html?teacherId=${getQueryParam("teacherId")}&classId=${getQueryParam("classId")}&lessonId=${getQueryParam("lessonId")}&homeworkId=${homework.homework_id}"
               style="color: black; text-decoration: none;"
               class="item-icon-parent">
                <div class="list-item-content">
                    <div class="frame">
                        <div class="icon1">📝</div>
                    </div>
                    <div class="title-container">
                        <div class="title7">${homework.description}</div>
                    </div>
                </div>
            </a>
        </div>`;

        homeworkHolder.appendChild(newHomework);
    });
}

/**
 * Deletes a homework item.
 * Sends a DELETE request to the server.
 */
async function deleteHomework() {
    try {
        const response = await fetch(`../../api/homework/${getQueryParam("homeworkId")}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            console.log('Homework deleted successfully');
            window.location.href = `../../teacherPages/lessonsPage/index.html`;
        } else {
            const errorText = await response.text();
            alert('Error: ' + errorText);
            console.log(errorText);
        }
    } catch (error) {
        alert('Error: ' + error.message);
        console.error(error.message);
    }
}

/**
 * Fetches details of a specific homework item.
 * Displays the details on the page.
 */
async function getOneHomeworkDetail() {
    const classId = getQueryParam("classId");
    const lessonId = getQueryParam("lessonId");
    const homeworkId = getQueryParam("homeworkId")

    if (lessonId && classId) {
        try {
            const response = await fetch(`../../api/homework/${homeworkId}`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const homework = await response.json();
            console.log('Homework:', homework);

            // Function to format date as YYYY-MM-DD
            function convertDate(inputDate) {
                const date = new Date(inputDate);
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
                const day = String(date.getDate()).padStart(2, '0');
                return `${year}-${month}-${day}`;
            }

            // Formatting due date and start date
            const options = { year: 'numeric', month: 'long', day: 'numeric' };
            const dueDate = convertDate(new Date(homework.due_date).toLocaleDateString('en-US', options));
            const startDate = new Date(homework.start_date).toLocaleDateString('en-US', options);

            console.log(dueDate);
            console.log(startDate);

            // Setting values in the form fields
            document.getElementById("description").value = homework.description;
            document.getElementById("deadline").value = dueDate;
            document.getElementById("timeIndication").value = startDate;
            document.getElementById("goal").value = homework.isDivisible ? "Yes" : "No";

        } catch (error) {
            console.error('Error fetching homework:', error);
        }
    }
}

/**
 * Updates homework details on the server.
 * Sends a PUT request with updated details.
 * @param {Object} homeworkDetails - Updated homework details.
 */
async function updateHomeworkDetails(homeworkDetails) {
    try {
        const response = await fetch(`../../api/homework`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(homeworkDetails)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const data = await response.json();
        console.log('Success:', data);
        window.location.href = `../../teacherPages/lessonsPage/index.html`;

    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

/**
 * Creates a new homework item on the server.
 * Sends a POST request with homework details.
 * @param {Object} homeworkDetails - Details of the new homework to create.
 */
async function createHomework(homeworkDetails) {
    try {
        const response = await fetch(`../../api/homework`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(homeworkDetails)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.statusText);
        }

        console.log(await response.json());
        window.location.href = `../../teacherPages/lessonsPage/index.html`;

    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}
