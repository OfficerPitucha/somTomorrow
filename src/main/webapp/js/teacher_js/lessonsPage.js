/**
 * Executes when the DOM content is fully loaded.
 * Attaches event listeners to buttons and icons.
 */
document.addEventListener('DOMContentLoaded', function () {

    /**
     * Event listener for the "Update Lesson" button.
     * Calls the saveLessonDetails function when clicked.
     */
    document.getElementById('updateLessonButton').addEventListener('click', saveLessonDetails);

    /**
     * Event listener for the "Delete Lesson" button.
     * Calls the deleteLessonDetails function when clicked.
     */
    document.getElementById('deleteLessonButton').addEventListener('click', deleteLessonDetails);

    // Icon click event listener
    var listItemIcon = document.getElementById("listItemIcon");
    if (listItemIcon) {
        /**
         * Redirects to homework details page when the icon is clicked.
         * @param {Event} e - The click event object.
         */
        listItemIcon.addEventListener("click", function (e) {
            window.location.href = `../../teacherPages/homeworkDetails/index.html`;
        });
    }
});

// Global icon click event listener
var listItemIcon = document.getElementById("listItemIcon");
if (listItemIcon) {
    /**
     * Redirects to homework details page when the icon is clicked.
     * @param {Event} e - The click event object.
     */
    listItemIcon.addEventListener("click", function (e) {
        window.location.href = `../../teacherPages/homeworkDetails/index.html`;
    });
}
