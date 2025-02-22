/**
 * Handles the click event on the secondary container element.
 * Redirects the user to the homework details page for teachers.
 *
 * @param {MouseEvent} e - The click event object
 */
var secondayContainer = document.getElementById("secondayContainer");
if (secondayContainer) {
    secondayContainer.addEventListener("click", function (e) {
        window.location.href = "../../teacherPages/homeworkDetails/index.html";
    });
}
