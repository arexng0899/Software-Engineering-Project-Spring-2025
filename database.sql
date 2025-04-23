CREATE DATABASE professor_db;

USE professor_db;

-- USERS table: shared by students, professors, and admins
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100),
    password VARCHAR(255),
    role ENUM('student', 'professor', 'admin') NOT NULL
);

-- STUDENTS table
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    study_hours_per_week INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- PROFESSORS table
CREATE TABLE professors (
    professor_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    department VARCHAR(100),
    university VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- INSERT 10 students into users
INSERT INTO users (username, email, password, role) VALUES
('student1', 's1@college.edu', 'pass1', 'student'),
('student2', 's2@college.edu', 'pass2', 'student'),
('student3', 's3@college.edu', 'pass3', 'student'),
('student4', 's4@college.edu', 'pass4', 'student'),
('student5', 's5@college.edu', 'pass5', 'student'),
('student6', 's6@college.edu', 'pass6', 'student'),
('student7', 's7@college.edu', 'pass7', 'student'),
('student8', 's8@college.edu', 'pass8', 'student'),
('student9', 's9@college.edu', 'pass9', 'student'),
('student10', 's10@college.edu', 'pass10', 'student');

-- INSERT 10 professors into users
INSERT INTO users (username, email, password, role) VALUES
('prof1', 'p1@univ.edu', 'profpass1', 'professor'),
('prof2', 'p2@univ.edu', 'profpass2', 'professor'),
('prof3', 'p3@univ.edu', 'profpass3', 'professor'),
('prof4', 'p4@univ.edu', 'profpass4', 'professor'),
('prof5', 'p5@univ.edu', 'profpass5', 'professor'),
('prof6', 'p6@univ.edu', 'profpass6', 'professor'),
('prof7', 'p7@univ.edu', 'profpass7', 'professor'),
('prof8', 'p8@univ.edu', 'profpass8', 'professor'),
('prof9', 'p9@univ.edu', 'profpass9', 'professor'),
('prof10', 'p10@univ.edu', 'profpass10', 'professor');

-- INSERT student profiles (assuming user_id starts from 1 to 10)
INSERT INTO students (user_id, study_hours_per_week) VALUES
(1, 6),
(2, 8),
(3, 5),
(4, 7),
(5, 4),
(6, 9),
(7, 3),
(8, 6),
(9, 10),
(10, 2);

INSERT INTO professors (user_id, department, university) VALUES
(11, 'Math', 'Georgia State University'),
(12, 'Physics', 'Georgia State University'),
(13, 'Computer Science', 'Georgia Tech'),
(14, 'Engineering', 'MIT'),
(15, 'English', 'Harvard'),
(16, 'Biology', 'Stanford'),
(17, 'Economics', 'UCLA'),
(18, 'Art', 'SCAD'),
(19, 'History', 'NYU'),
(20, 'Chemistry', 'UGA');

CREATE TABLE IF NOT EXISTS reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id VARCHAR(50),
    class_name VARCHAR(100),
    content TEXT,
    rating INT,
    created_at TIMESTAMP,
    professor_id INT,
    student_id INT,
    FOREIGN KEY (professor_id) REFERENCES professors(professor_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

INSERT INTO reviews (course_id, class_name, content, rating, created_at, professor_id, student_id) VALUES
('MATH101', 'Introduction to Calculus', 'Professor explains concepts clearly and is always available during office hours.', 5, NOW(), 1, 1),
('CS101', 'Introduction to Programming', 'Great course! The professor makes complex topics easy to understand.', 5, NOW(), 3, 2),
('PHYS201', 'Physics II', 'Challenging but rewarding course. The professor is knowledgeable but sometimes goes too fast.', 4, NOW(), 2, 3),
('ENG101', 'English Composition', 'The professor provides excellent feedback on writing assignments.', 4, NOW(), 5, 4),
('HIST101', 'World History', 'Engaging lectures and interesting discussions. Highly recommended!', 5, NOW(), 9, 5),
('CHEM101', 'General Chemistry', 'The professor is passionate about the subject but the exams are very difficult.', 3, NOW(), 10, 6),
('ECON201', 'Microeconomics', 'Clear explanations and helpful problem-solving sessions.', 4, NOW(), 7, 7),
('ART101', 'Introduction to Drawing', 'The professor is supportive and provides constructive criticism.', 5, NOW(), 8, 8),
('BIO101', 'Introduction to Biology', 'Interesting labs but lectures can be dry at times.', 3, NOW(), 6, 9),
('MATH201', 'Linear Algebra', 'Challenging course but the professor is always willing to help.', 4, NOW(), 1, 10);

ALTER TABLE users ADD COLUMN profile_picture VARCHAR(255);
