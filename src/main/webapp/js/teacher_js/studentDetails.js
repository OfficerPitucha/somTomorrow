/**
 * Redirects to the teacher's homepage when the tab text is clicked.
 */
document.addEventListener('DOMContentLoaded', () => {
    var tabText = document.getElementById("tabText");
    if (tabText) {
        tabText.addEventListener("click", function () {
            window.location.href = "../../teacherPages/teacherHomePage/homepageTeacher.html";
        });
    }
});

/**
 * Handles form submission for student information.
 * If studentId is not provided, adds a new student; otherwise, updates an existing student.
 */
document.getElementById('student-info').addEventListener('submit', function (e) {
    e.preventDefault();

    const fullName = document.getElementById('name').value;
    const [name, ...surnameParts] = fullName.trim().split(' ');
    const surname = surnameParts.join(' ');

    const classId = getQueryParam("classId");
    const studentId = getQueryParam("studentId");

    const email = document.getElementById('email').value;

    console.log(fullName);
    console.log(email);
    console.log(name);
    console.log(surname)

    if (!studentId && classId) {
        // Adding a new student
        const addStudent = {
            name: name,
            surname: surname,
            student_id: 30, // random, it will generate a student_id itself
            class_id: parseInt(classId),
            status: "enrolled",
            schedule_id: 4,
            weighted_avg: 0.0,
            email: email
        };

        addNewStudent(addStudent);
        window.location.href = `../../teacherPages/classPage/index.html`;
    } else if (classId && studentId) {
        // Updating an existing student
        const student = {
            name: name,
            surname: surname,
            student_id: parseInt(studentId), // Convert to integer here
            class_id: parseInt(classId),
            status: "enrolled",
            schedule_id: 0,
            weighted_avg: 0.0,
            email: email
        };

        updateStudent(student);
        window.location.href = `../classPage/index.html?teacherId=${getQueryParam("teacherId")}&classId=${getQueryParam("classId")}`;
    }
});
