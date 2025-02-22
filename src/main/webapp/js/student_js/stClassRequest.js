/**
 * Retrieves the value of a query parameter from the current URL.
 *
 * @param {string} param - The name of the query parameter to retrieve.
 * @returns {string|null} The value of the query parameter if found, otherwise null.
 */
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

/**
 * Fetches the class information for a given student ID from the API.
 *
 * @param {number} student_id - The ID of the student whose class information to fetch.
 * @returns {Promise<void>} A Promise that resolves when the class information is fetched and processed.
 */
async function findClass(student_id) {
    try {
        const response = await fetch(`../../api/student/${student_id}/class`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const res = await response.json();
        console.log('Classes:', res);
        setHrefId(res.class_id);
    } catch (error) {
        console.error('Error fetching classes:', error);
    }
}

/**
 * Sets the href attribute of the lesson button based on the provided class ID.
 *
 * @param {number} classId - The ID of the class to set in the href attribute.
 */
function setHrefId(classId) {
    document.getElementById('lesson_button').innerHTML = `
    <a href="../LessonStudent/index.html?classId=${classId}" class="lessons-button">
        LESSONS
    </a>`;
}
