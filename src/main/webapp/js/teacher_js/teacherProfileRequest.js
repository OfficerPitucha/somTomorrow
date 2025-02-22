document.addEventListener('DOMContentLoaded', async function () {
    const username = 'T' + getQueryParam('teacherId');
    if (username) {
        console.log(`Fetching teacher ID for username: ${username}`);
        const teacherId = await fetchTeacherIdByUsername(username);
        if (teacherId) {
            console.log(`Teacher ID: ${teacherId}`);
            fetchTeacherDetails(teacherId);
        } else {
            console.error('No teacher ID found.');
        }
    }

    const homeLink = document.getElementById("home0");
    if (homeLink) {
        homeLink.href = `../teacherHomePage/homepageTeacher.html?teacherId=${username}`;
    }
});

function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

async function fetchTeacherIdByUsername(username) {
    try {
        const response = await fetch(`../../api/teacher/byUsername/${username}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const teacherId = await response.json();
        return teacherId;
    } catch (error) {
        console.error('Error fetching teacher ID by username:', error);
        return null;
    }
}

async function fetchTeacherDetails(teacherId) {
    try {
        const response = await fetch(`../../api/teacher/${teacherId}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const teacher = await response.json();

        if (teacher && teacher.person) {
            console.log('Teacher:', teacher);
            document.getElementById('name').value = teacher.person.name || '';
            document.getElementById('surname').value = teacher.person.surname || '';
            document.getElementById('email').value = teacher.person.email || '';
            document.getElementById('subject').value = teacher.subject || '';
            document.getElementById('teacherName').innerText = `${teacher.person.name || ''} ${teacher.person.surname || ''}`;
        } else {
            console.error('No teacher data found.');
        }
    } catch (error) {
        console.error('Error fetching teacher:', error);
    }
}

async function saveChanges() {
    const teacherId = getQueryParam('teacherId');
    if (!teacherId) return;

    const person = {
        person_id: teacherId,
        name: document.getElementById('name').value,
        surname: document.getElementById('surname').value,
        email: document.getElementById('email').value,
    };

    const teacher = {
        teacher_id: teacherId,
        subject: document.getElementById('subject').value,
        person: person
    };

    try {
        const response = await fetch('../../api/teacher', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(teacher)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Success:', result);
        alert('Teacher details updated successfully!');
    } catch (error) {
        console.error('Error updating teacher details:', error);
        alert('Failed to update teacher details.');
    }
}

function discardChanges() {
    const teacherId = getQueryParam('teacherId');
    if (teacherId) {
        fetchTeacherDetails(teacherId);
    }
}
