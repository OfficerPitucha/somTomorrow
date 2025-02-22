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
 * Fetches details of a specific lesson from the server and populates the corresponding form fields.
 * Retrieves the lesson details based on the provided lesson ID.
 *
 * @param {string} lessonId - The ID of the lesson to fetch details for
 */
async function getOneLessonDetail(lessonId) {
    if (lessonId) {
        try {
            const response = await fetch(`../../api/lesson/${lessonId}`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const lesson = await response.json();
            console.log('Lesson:', lesson);

            document.getElementById("lessonName").value = lesson.title;
            document.getElementById("lessonLocation").value = lesson.location;
            document.getElementById("lessonPeriod").value = lesson.period;
            document.getElementById("lessonDay").value = lesson.day;

        } catch (error) {
            console.error('Error fetching lesson:', error);
        }
    }
}

/**
 * Saves lesson details either by updating an existing lesson or creating a new one.
 * Retrieves input values from the lesson form fields and sends a corresponding request to the server.
 * Handles both PUT (update) and POST (create) requests based on the presence of lesson ID and class ID.
 *
 * @param {Event} event - The form submission event
 */
async function saveLessonDetails(event) {
    event.preventDefault();

    // Get the values from the input fields
    const lessonName = document.getElementById('lessonName').value;
    const lessonLocation = document.getElementById('lessonLocation').value;
    const lessonPeriod = parseInt(document.getElementById('lessonPeriod').value, 10);
    const lessonDay = document.getElementById('lessonDay').value;

    console.log(lessonName);
    console.log(lessonDay);
    console.log(lessonPeriod);
    console.log(lessonLocation);

    const classId = parseInt(getQueryParam("classId"), 10);
    const lessonId = getQueryParam("lessonId");

    if (isNaN(lessonPeriod) || isNaN(classId)) {
        alert('Invalid period or class ID. Please ensure they are numbers.');
        return;
    }

    console.log(classId);
    console.log(lessonId);

    //If this lesson already exists, we update it
    if (classId && lessonId) {
        // Create an object with the data
        const lessonDataUpdate = {
            lessonId: lessonId,
            classId: classId,
            title: lessonName,
            hasHomework: false,
            location: lessonLocation,
            period: lessonPeriod,
            day: lessonDay
        };

        try {
            const response = await fetch('../../api/lesson', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(lessonDataUpdate),
            })

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const result = await response.json();
            console.log('Success:', result);
            alert('Lesson details updated successfully!');
        } catch (error) {
            console.error('Error updating lesson details:', error);
            alert('Failed to update lesson details.');
        }
    } else if (classId) { // create new lesson
        // Create an object with the data
        const lessonDataCreate = {
            classId: classId,
            title: lessonName,
            hasHomework: false,
            location: lessonLocation,
            period: lessonPeriod,
            day: lessonDay
        };

        // Send a POST request to the server
        try {
            const response = await fetch('../../api/lesson', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(lessonDataCreate)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            console.log('Success:', result);
            alert('Lesson details saved successfully!');

        } catch (error) {
            console.error('Error saving lesson details:', error);
            alert('Failed to save lesson details.');
        }
    }
}

/**
 * Deletes a lesson from the server based on the lesson ID.
 * Retrieves the lesson ID and class ID from the query parameters.
 * Redirects to the teacher's class page after successful deletion.
 */
async function deleteLessonDetails() {
    const classId = getQueryParam("classId");
    const lessonId = getQueryParam("lessonId");

    console.log(classId);
    console.log(lessonId);

    if (classId && lessonId) {
        try {
            const response = await fetch(`../../api/lesson/${lessonId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                console.log('Lesson deleted successfully');
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
