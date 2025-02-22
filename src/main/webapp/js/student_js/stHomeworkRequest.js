/**
 * Fetches all homework assignments for a specific class and lesson from the API.
 *
 * @param {number} classId - The ID of the class for which homework assignments are fetched.
 * @param {number} lessonId - The ID of the lesson within the class for which homework assignments are fetched.
 * @returns {Promise<void>} A Promise that resolves when homework assignments are fetched and displayed.
 */
async function fetchAllHomework_st(classId, lessonId) {
    try {
        const response = await fetch(`../../api/homework/${classId}/${lessonId}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const homeworks = await response.json();
        console.log('Homework:', homeworks);
        displayHomework_st(homeworks);
    } catch (error) {
        console.error('Error fetching homework assignments:', error);
    }
}

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
 * Displays homework assignments on the webpage.
 *
 * @param {Array<object>} homeworks - An array of homework assignment objects to display.
 */
function displayHomework_st(homeworks) {
    const homeworkHolder = document.getElementById('items-container');
    homeworkHolder.innerHTML = '';

    homeworks.forEach((homework) => {
        const newHomework = document.createElement('div');
        newHomework.className = 'item1';

        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        const dueDate = new Date(homework.due_date).toLocaleDateString('en-US', options);

        newHomework.innerHTML = `
        <div class="item1" id="itemContainer1">
            <a href="../homework_details/index.html?subject=${getQueryParam('subject')}&dueDate=${dueDate}&hom_des=${homework.description}"
            style="color: black; text-decoration: none;">
                <div class="frame-wrapper">
                    <div class="frame1">
                        <div class="icon1">📚</div>
                    </div>
                </div>
                <div class="title-group">
                    <div class="title4">${homework.description}</div>
                    <div class="subtitle1">Due Date: ${dueDate}</div>
                </div>
            </a>
        </div>`;

        homeworkHolder.appendChild(newHomework);
    });
}
