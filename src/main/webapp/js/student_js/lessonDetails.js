/**
 * Executes when the DOM content is fully loaded.
 * Sets up event listeners for scrolling buttons and item containers,
 * and updates the subject title based on a query parameter.
 */
document.addEventListener("DOMContentLoaded", function () {
    // Select the left and right buttons using their IDs
    const leftButton = document.getElementById("left-button");
    const rightButton = document.getElementById("right-button");

    // Select the items container using its ID
    const itemsContainer = document.getElementById("items-container");

    // Set the scroll amount (adjust based on how much you want to scroll)
    const scrollAmount = 200;

    // Add click event listener to the left button
    leftButton.addEventListener("click", function () {
        // Scroll the items container to the left by the scroll amount
        itemsContainer.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
    });

    // Add click event listener to the right button
    rightButton.addEventListener("click", function () {
        // Scroll the items container to the right by the scroll amount
        itemsContainer.scrollBy({ left: scrollAmount, behavior: 'smooth' });
    });

    // Add click event listener to itemContainer if it exists
    var itemContainer = document.getElementById("itemContainer");
    if (itemContainer) {
        itemContainer.addEventListener("click", function (e) {
            // Redirect to homework details page
            window.location.href = "../../studentPages/homework_details/index.html";
        });
    }

    // Add click event listener to itemContainer1 if it exists
    var itemContainer1 = document.getElementById("itemContainer1");
    if (itemContainer1) {
        itemContainer1.addEventListener("click", function (e) {
            // Redirect to homework details page
            window.location.href = "../../studentPages/homework_details/index.html";
        });
    }

    // Add click event listener to itemContainer2 if it exists
    var itemContainer2 = document.getElementById("itemContainer2");
    if (itemContainer2) {
        itemContainer2.addEventListener("click", function (e) {
            // Redirect to homework details page
            window.location.href = "../../studentPages/homework_details/index.html";
        });
    }

    // Set the subject title based on the 'subject' query parameter
    document.getElementById('subject-title').innerHTML = getQueryParam('subject');
});

/**
 * Function to retrieve query parameters from the URL.
 *
 * @param {string} name - The name of the query parameter to retrieve.
 * @returns {string|null} The value of the query parameter if found, otherwise null.
 */
function getQueryParam(name) {
    const urlSearchParams = new URLSearchParams(window.location.search);
    return urlSearchParams.get(name);
}
