/**
 * Asynchronously fetches all pieces associated with a homework ID from the server.
 * Displays the fetched pieces using the displayPieces function.
 *
 * @param {number} homeworkId - The ID of the homework for which pieces are fetched
 */
async function fetchAllPieces(homeworkId) {
    try {
        const response = await fetch(`../../api/pieces/${homeworkId}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const pieces = await response.json();
        console.log('Pieces:', pieces);
        displayPieces(pieces);
    } catch (error) {
        console.error('Error fetching pieces:', error);
    }
}

/**
 * Displays pieces in the DOM based on the provided pieces data.
 *
 * @param {Array<Object>} pieces - An array of piece objects to display
 */
function displayPieces(pieces) {
    const list = document.getElementById('homeworkList');

    // Clear existing content
    list.innerHTML = '';

    pieces.forEach((piece) => {
        // Create a new row container
        const newRow = document.createElement('div');
        newRow.className = 'row';
        newRow.id = `rowContainer${String(piece.id).padStart(2, '0')}`;

        // Create a new link for the homework part
        const newLink = document.createElement('a');
        newLink.href = `../homework_part/index.html?partId=${piece.pieceId}`;
        newLink.style.color = 'black';
        newLink.style.textDecoration = 'none';
        newLink.className = 'item-icon-parent';

        // Set the inner HTML of the link
        newLink.innerHTML = `
                    <div class="frame-parent">
                        <div class="frame">
                            <div class="icon1">📝</div>
                        </div>
                        <div class="title-wrapper">
                            <div class="title10">${piece.tasks}</div>
                        </div>
                    </div>
                `;

        // Append the link to the row container
        newRow.appendChild(newLink);

        // Append the row container to the list
        list.appendChild(newRow);
    });
}

/**
 * Retrieves the value of a query parameter from the URL.
 *
 * @param {string} param - The name of the query parameter to retrieve
 * @returns {string} The value of the query parameter
 */
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

/**
 * Function placeholder for showing a single piece of homework.
 * To be implemented as per specific requirements.
 */
function showOnePieceHomework() {
    // Implementation specific to showing a single piece of homework
    // Placeholder function
}

/**
 * Handles the submission and saving/updating of piece details asynchronously.
 *
 * @param {Event} event - The submit event triggering the function call
 */
async function savePieceDetails(event) {
    event.preventDefault();
    document.getElementById('primary').addEventListener('click', async function (event) {
        event.preventDefault();

        // Get the values from the input fields
        const name = document.getElementById('name').value;
        const tasks = document.getElementById('tasks').value;
        const deadline = document.getElementById('deadline').value;
        const goal = document.getElementById('goal').value;

        console.log(name);
        console.log(tasks);
        console.log(deadline);
        console.log(goal);

        const studentId = getQueryParam("student_id");
        const lessonId = getQueryParam("lesson_id");

        console.log(studentId);
        console.log(lessonId);

        //If this piece already exists we update it
        if (studentId && lessonId) {

            // Create an object with the data
            const pieceDataUpdate = {
                progress: "In progress",
                tasks: tasks,
                start_date: null,
                due_date: deadline,
                lesson_id: lessonId,
                student_Id: studentId,
                goal: goal,
                homework_id: null
            };

            try {
                const response = await fetch('../../api/pieces', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(pieceDataUpdate),
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const result = await response.json();
                console.log('Success:', result);
                alert('Piece details updated successfully!');
            } catch (error) {
                console.error('Error updating piece details:', error);
                alert('Failed to update piece details.');
            }
        } else if (studentId) { // create new piece

            // Create an object with the data
            const pieceDataCreate = {
                progress: "In progress",
                tasks: tasks,
                start_date: null,
                due_date: deadline,
                lesson_id: lessonId,
                student_Id: studentId,
                goal: goal,
                homework_id: null
            };

            try {
                const response = await fetch('../../api/pieces', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(pieceDataCreate)
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const result = await response.json();
                console.log('Success:', result);
                alert('Piece details saved successfully!');

            } catch (error) {
                console.error('Error saving piece details:', error);
                alert('Failed to save piece details.');
            }
        }
    });
}
