/**
 * Object containing emoji icons mapped to numeric indices.
 * @type {Object.<number, string>}
 */
var ICONS = {
    0: "📘",
    1: "🏛️",
    2: "📑",
    3: "💃",
    4: "👩‍💻",
    5: "🧬",
    6: "🔭",
    7: "🎾",
    8: "🖌️",
    9: "🎼"
};

/**
 * Fetches lessons for a specific class asynchronously.
 * @param {number} classId - The ID of the class for which lessons are to be fetched.
 */
async function fetchLesssons_st(classId) {
    try {
        const response = await fetch(`../../api/lesson/${classId}/lessons`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const lessons = await response.json();
        console.log('Classes:', lessons);
        displayLessons_st(lessons);
    } catch (error) {
        console.error('Error fetching classes:', error);
    }
}

/**
 * Retrieves a query parameter value from the current URL.
 * @param {string} param - The query parameter key.
 * @returns {string|null} - The value of the query parameter, or null if not found.
 */
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

/**
 * Displays lessons on the webpage.
 * @param {Array} lessons - An array of lesson objects to display.
 */
function displayLessons_st(lessons) {
    // Clear existing content
    const lessonsHolder = document.getElementById('lessons-holder');
    lessonsHolder.innerHTML = '';

    lessons.forEach((lesson) => {
        // Create a new lesson item
        const newLesson = document.createElement('div');
        newLesson.className = 'item';
        newLesson.id = 'itemContainer';

        // Set the inner HTML of the new item
        newLesson.innerHTML = `
            <a href="../lessonDetails/index.html?classId=${getQueryParam('classId')}&lesson_id=${lesson.lessonId}&subject=${lesson.title}"
                style="text-decoration: none; color: inherit;">
                <div class="frame-wrapper">
                    <div class="frame">
                        <div class="icon">${ICONS[lesson.icon]}</div>
                    </div>
                </div>
                <div class="title-parent">
                    <div class="title2">${lesson.title}</div>
                    <div class="subtitle">${lesson.location}</div>
                </div>
            </a>`;

        lessonsHolder.appendChild(newLesson);
    });
}

/**
 * Fetches details of a specific lesson asynchronously and updates the DOM.
 * @param {number} lessonId - The ID of the lesson to fetch details for.
 */
async function getOneLessonDetail(lessonId) {
    if (lessonId) {
        try {
            const response = await fetch(`../../api/lesson/${lessonId}`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const lesson = await response.json();
            console.log('Lesson:', lesson.title);

            // Update the DOM with the lesson title
            document.getElementById("lessonsName").innerText = lesson.title;

        } catch (error) {
            console.error('Error fetching homework:', error);
        }
    }
}
