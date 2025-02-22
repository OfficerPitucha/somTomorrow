/**
 * Sets content based on query parameters for a specific topic, due date, and instructions.
 */
document.getElementById('topic').innerHTML = getQueryParam('subject');
document.getElementById('due_date').innerHTML = getQueryParam('dueDate');
document.getElementById('instructions').innerHTML = getQueryParam('hom_des');

/**
 * Adds event listeners after DOM content is loaded.
 */
document.addEventListener('DOMContentLoaded', () => {
    // Reference to the plus button for parts
    const plusButton = document.getElementById('listItemIcon');

    // Reference to the parts list container
    const itemsList = document.querySelector('.list3');

    /**
     * Event listener for the plus button click to add a new part item.
     */
    plusButton.addEventListener('click', () => {
        // Add new part item
        addNewPartItem();

        // Redirect to given link
        window.location.href = '../../studentPages/homework_part/index.html';
    });

    /**
     * Function to add a new part item to the list.
     */
    function addNewPartItem() {
        // Create new item for the part
        const newItem = document.createElement('div');
        newItem.className = 'row'; // Assign class to the new item

        // Create new link
        const newLink = document.createElement('a');
        newLink.href = '../homework_part/index.html'; // Set link URL
        newLink.style.color = 'black';
        newLink.style.textDecoration = 'none';
        newLink.className = 'item-icon-parent'; // Assign class to the link

        // Create frame container
        const frameContainer = document.createElement('div');
        frameContainer.className = 'frame-parent'; // Assign class to the frame container

        const frame = document.createElement('div');
        frame.className = 'frame';
        const icon = document.createElement('div');
        icon.className = 'icon1';
        icon.textContent = '📝'; // Set the icon text

        frame.appendChild(icon);
        frameContainer.appendChild(frame);
        newLink.appendChild(frameContainer);

        // Create title container
        const titleContainer = document.createElement('div');
        titleContainer.className = 'title-wrapper'; // Assign class to the title container

        const title = document.createElement('div');
        title.className = 'title10'; // Assign class to the title
        title.textContent = 'New Part'; // Set the title text

        titleContainer.appendChild(title);
        newLink.appendChild(titleContainer);

        newItem.appendChild(newLink);

        // Create image
        const img = document.createElement('img');
        img.className = 'row-child'; // Assign class to the image
        img.alt = '';
        img.src = 'public/vector-200-1.svg'; // Set the image source

        newItem.appendChild(img);

        // Append new item to parts list container
        itemsList.appendChild(newItem);
    }
});

/**
 * Event listener to redirect to student homepage on tab text click.
 */
var tabText = document.getElementById("tabText");
if (tabText) {
    tabText.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/studentHomePage/index.html"
    });
}

/**
 * Event listener to redirect to homework part page on item container click.
 */
var itemContainer = document.getElementById("itemContainer");
if (itemContainer) {
    itemContainer.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/homework_part/index.html";
    });
}

/**
 * Event listener to redirect to homework part page on row container click.
 */
var rowContainer = document.getElementById("rowContainer");
if (rowContainer) {
    rowContainer.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/homework_part/index.html";
    });
}

/**
 * Event listener to redirect to homework part page on action icon container click.
 */
var actionIconContainer = document.getElementById("actionIconContainer");
if (actionIconContainer) {
    actionIconContainer.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/homework_part/index.html";
    });
}

/**
 * Event listener to redirect to homework part page on row container 1 click.
 */
var rowContainer1 = document.getElementById("rowContainer1");
if (rowContainer1) {
    rowContainer1.addEventListener("click", function (e) {
        window.location.href = "../../studentPages/homework_part/index.html";
    });
}

/**
 * Function to retrieve query parameter value from URL.
 *
 * @param {string} param - The query parameter name
 * @returns {string} The value of the query parameter
 */
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}
