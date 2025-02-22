/**
 * Redirects to the student home page when tabText element is clicked.
 */
var tabText = document.getElementById("tabText");
if (tabText) {
    tabText.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/studentHomePage/index.html";
    });
}

/**
 * Redirects to the lesson page when tabText1 element is clicked.
 */
var tabText1 = document.getElementById("tabText1");
if (tabText1) {
    tabText1.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/LessonStudent/index.html";
    });
}

/**
 * Redirects to the student profile page when tabText2 element is clicked.
 */
var tabText2 = document.getElementById("tabText2");
if (tabText2) {
    tabText2.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/student_profile/index.html";
    });
}

/**
 * Redirects to the homework details page when tabText3 element is clicked.
 */
var tabText3 = document.getElementById("tabText3");
if (tabText3) {
    tabText3.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/homework_details/index.html";
    });
}

/**
 * Saves piece details on button click and sends data to the server.
 */
document.getElementById('primary').addEventListener('click', savePieceDetails);

/**
 * Adds an event listener to the primary button to handle form submission.
 * Validates input and sends data to the server asynchronously.
 */
document.addEventListener('DOMContentLoaded', function() {
    const addPieceBtn = document.getElementById('primary');
    addPieceBtn.addEventListener('click', async () => {

        const name = document.getElementById("name").value.trim();
        if (name) {
            // Prepare data to be sent
            const formData = {
                name: name
            };

            console.log(formData);

            try {
                // Send data to the server
                const response = await fetch('../../api/pieces', {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                });

                if (response.ok) {
                    console.log(response);
                    console.log(formData);
                    fetchAllPieces();
                    name.value = '';
                } else {
                    const errorText = await response.text();
                    alert("Error: " + errorText);
                    console.log(errorText)
                }
            } catch (error) {
                alert("Error: " + error.message);
                console.log(error.message)
            }
        } else {
            alert("Please fill out the piece name.");
        }
    });
});
