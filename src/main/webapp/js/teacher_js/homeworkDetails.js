/**
 * Retrieves query parameter from the URL.
 * @param {string} param - The parameter name to retrieve.
 * @returns {string|null} The parameter value if found, otherwise null.
 */
const deadline = document.getElementById("deadline").value;
const timeIndication = document.getElementById("timeIndication").value;
const description = document.getElementById("description").value;
const goal = document.getElementById("goal").value;

const homeworkId = getQueryParam("homeworkId");
const lessonId = getQueryParam("lessonId");
const classId = getQueryParam("classId");
const teacherId = getQueryParam("teacherId");

const allStudentsButton = document.getElementById("all-students");
const customizeButton = document.getElementById("customize");

let cutUpOption = "false";
if (allStudentsButton.classList.contains("selected")) {
    cutUpOption = "true";
} else if (customizeButton.classList.contains("selected")) {
    cutUpOption = "true";
}

/**
 * Object representing homework details.
 * @type {Object}
 * @property {boolean} isDivisible - Indicates if the homework is divisible.
 * @property {string} start_date - Start date of the homework in ISO format.
 * @property {string} due_date - Due date of the homework in ISO format.
 * @property {string} description - Description of the homework.
 * @property {number} timeIndication - Time indication for the homework.
 * @property {number} homework_id - ID of the homework.
 * @property {number} lesson_id - ID of the lesson associated with the homework.
 * @property {number} student_id - ID of the student.
 * @property {number} class_id - ID of the class associated with the homework.
 * @property {string} goal - Goal for the homework.
 */
const homeworkDetails = {
    isDivisible: cutUpOption === "true",
    start_date: new Date().toISOString().slice(0, 10),
    due_date: new Date(deadline).toISOString().slice(0, 10),
    description: description || "desc",
    timeIndication: parseInt(timeIndication, 10) || 20,
    homework_id: parseInt(homeworkId, 10) || 600,
    lesson_id: parseInt(lessonId, 10) || 5,
    student_id: 1, // random value
    class_id: parseInt(classId, 10) || 1,
    goal: goal || ""
};

/**
 * Event listener for 'click' event on all students button.
 * Toggles the 'selected' class and updates styles.
 */
allStudentsButton.addEventListener("click", function () {
    this.classList.toggle("selected");
    customizeButton.classList.remove("selected");
    this.style.backgroundColor = "rgb(255,255,255)";
    this.style.color = "black";
});

/**
 * Event listener for 'click' event on customize button.
 * Toggles the 'selected' class and updates styles.
 */
customizeButton.addEventListener("click", function () {
    this.classList.toggle("selected");
    allStudentsButton.classList.remove("selected");
    this.style.backgroundColor = "rgb(255,255,255)";
    this.style.color = "black";
});

/**
 * Event listener for DOMContentLoaded event. Handles adding parts functionality.
 */
document.addEventListener('DOMContentLoaded', () => {
    const plusButton = document.getElementById('listItemIcon'); // Reference to the plus button for parts
    const itemsList = document.querySelector('.list3'); // Reference to the parts list container

    // Event listener for 'click' event on plus button to add a new part item.
    plusButton.addEventListener('click', () => {
        addNewPartItem();
    });

    /**
     * Creates a new part item and appends it to the parts list container.
     */
    function addNewPartItem() {
        // Create new item for the part
        const newItem = document.createElement('div');
        newItem.className = 'row'; // Assign class to the new item

        // Create new link
        const newLink = document.createElement('a');
        newLink.href = '../homeworkParts/index.html'; // Set link URL
        newLink.style.color = 'black';
        newLink.style.textDecoration = 'none';
        newLink.className = 'item-icon-parent'; // Assign class to the link

        // Create frame container
        const frameContainer = document.createElement('div');
        frameContainer.className = 'frame-container'; // Assign class to the frame container

        const frame = document.createElement('div');
        frame.className = 'frame1';
        const icon = document.createElement('div');
        icon.className = 'icon1';
        icon.textContent = '📝'; // Set the icon text

        frame.appendChild(icon);
        frameContainer.appendChild(frame);
        newLink.appendChild(frameContainer);

        // Create title container
        const titleContainer = document.createElement('div');
        titleContainer.className = 'title-container'; // Assign class to the title container

        const title = document.createElement('div');
        title.className = 'title3'; // Assign class to the title
        title.textContent = 'New Part'; // Set the title text

        titleContainer.appendChild(title);
        newLink.appendChild(titleContainer);

        newItem.appendChild(newLink);

        // Create image
        const img = document.createElement('img');
        img.className = 'row-child'; // Assign class to the image
        img.alt = '';
        img.src = './public/vector-200.svg'; // Set the image source

        newItem.appendChild(img);

        // Append new item to parts list container
        itemsList.appendChild(newItem);
    }
});

/**
 * Event listener for customize button click.
 * Redirects to the customize page when clicked.
 */
var customize = document.getElementById("customize");
if (customize) {
    customize.addEventListener("click", function (e) {
        window.location.href = "../../teacherPages/cuttingUpCustom/index.html";
    });
}
