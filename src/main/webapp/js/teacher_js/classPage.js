// the code to add students ------------------------------------------------------------

/**
 * Creates a new student item and appends it to the student list container.
 */
function addNewStudentItem() {
    // Create new item for the student
    const newItem = document.createElement('div');
    newItem.className = 'item2'; // Assign class to the new item
    newItem.id = 'newItemContainer'; // Assign id to the new item

    // Create new link
    const newLink = document.createElement('a');
    newLink.href = '../studentDetails/index.html'; // Set link URL
    newLink.style.color = 'black';
    newLink.style.textDecoration = 'none';
    newLink.className = 'frame-parent2'; // Assign class to the link

    // Create frame
    const frame = document.createElement('div');
    frame.className = 'frame'; // Assign class to the frame

    const icon = document.createElement('div');
    icon.className = 'icon';
    icon.textContent = '👩‍🎓'; // Set the icon text

    frame.appendChild(icon);
    newLink.appendChild(frame);

    // Create title frame
    const titleFrame = document.createElement('div');
    titleFrame.className = 'title-frame'; // Assign class to the title frame

    const title = document.createElement('div');
    title.className = 'title4'; // Assign class to the title
    title.textContent = 'New Student'; // Set the title text

    titleFrame.appendChild(title);
    newLink.appendChild(titleFrame);

    newItem.appendChild(newLink);

    // Create image
    const img = document.createElement('img');
    img.className = 'item-inner'; // Assign class to the image
    img.alt = '';
    img.src = 'public/vector-200-5.svg'; // Set the image source

    newItem.appendChild(img);

    // Append new item to student list container
    listContainerStudent.appendChild(newItem); // Insert before the plus button
}

/**
 * Event listener for DOMContentLoaded event. Displays all homework assignments.
 */
document.addEventListener('DOMContentLoaded', () => {
    const plusButtonStudent = document.getElementById('listItemIconStudent'); // Reference to the plus button for student
    plusButtonStudent.addEventListener('click', () => {
        window.location.href = `../../teacherPages/studentDetails/index.html?teacherId=${getQueryParam("teacherId")}&classId=${getQueryParam("classId")}`;
    });
});
/**
 * Event listener for DOMContentLoaded event. Handles adding lessons functionality.
 */
document.addEventListener('DOMContentLoaded', () => {
    const plusButtonLesson = document.getElementById('listItemIconLesson'); // Reference to the plus button for student
    // Redirects to the page for adding a new lesson when the plus button is clicked.
    plusButtonLesson.addEventListener('click', () => {
        window.location.href = `../../teacherPages/lessonsPage/index.html?teacherId=${getQueryParam("teacherId")}&classId=${getQueryParam("classId")}`;
    });
});

function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}
