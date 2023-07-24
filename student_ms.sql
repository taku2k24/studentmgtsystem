-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 24, 2023 at 07:37 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `student_ms`
--

-- --------------------------------------------------------

--
-- Table structure for table `marks`
--

CREATE TABLE `marks` (
  `mark_id` int(11) NOT NULL,
  `enrollment_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `coursework_marks` float NOT NULL,
  `exam_marks` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `marks`
--

INSERT INTO `marks` (`mark_id`, `enrollment_id`, `module_id`, `coursework_marks`, `exam_marks`) VALUES
(26, 13, 1, 85.5, 78),
(27, 13, 2, 75, 82.5),
(28, 13, 3, 68, 90),
(29, 14, 4, 90.5, 92),
(30, 15, 5, 78, 85.5),
(31, 16, 6, 83.5, 88),
(32, 16, 7, 70, 76.5),
(33, 18, 8, 92.5, 90.5),
(34, 18, 9, 88, 84),
(35, 18, 10, 79.5, 80),
(36, 20, 11, 94, 92.5),
(37, 20, 12, 85, 78.5),
(38, 20, 1, 80.5, 86),
(39, 22, 2, 72, 78.5),
(40, 22, 3, 85, 88.5),
(41, 16, 4, 78.5, 85),
(42, 16, 5, 92, 90),
(43, 18, 6, 88.5, 86.5),
(44, 22, 7, 76, 82),
(45, 20, 8, 84.5, 89.5),
(46, 20, 9, 87, 80.5),
(47, 22, 10, 90, 94),
(48, 24, 11, 82.5, 85.5),
(49, 24, 12, 78, 81),
(50, 24, 1, 88, 92.5);

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `module_id` int(11) NOT NULL,
  `module_name` varchar(100) NOT NULL,
  `credits` int(11) NOT NULL,
  `course_name` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`module_id`, `module_name`, `credits`, `course_name`) VALUES
(1, 'Introduction to Literature', 3, 'Bachelor of Arts (BA)'),
(2, 'Calculus I', 4, 'Bachelor of Science (BS)'),
(3, 'Principles of Management', 3, 'Bachelor of Business Administration (BBA)'),
(4, 'Introduction to Programming', 4, 'Bachelor of Computer Science (BCS)'),
(5, 'Engineering Mechanics', 3, 'Bachelor of Engineering (BE)'),
(6, 'Educational Psychology', 4, 'Bachelor of Education (BEd)'),
(7, 'Painting Techniques', 3, 'Bachelor of Fine Arts (BFA)'),
(8, 'Marketing Fundamentals', 4, 'Bachelor of Commerce (BCom)'),
(9, 'Anatomy and Physiology', 3, 'Bachelor of Nursing (BN)'),
(10, 'Social Work Ethics', 4, 'Bachelor of Social Work (BSW)'),
(11, 'Architectural Design', 3, 'Bachelor of Architecture (BArch)'),
(12, 'Human Anatomy', 4, 'Bachelor of Medicine and Bachelor of Surgery (MBBS)'),
(13, 'Introduction to Law', 3, 'Bachelor of Law (LLB)'),
(14, 'Introduction to Psychology', 4, 'Bachelor of Psychology (BPsych)'),
(15, 'Environmental Science', 3, 'Bachelor of Environmental Science (BEnvSc)'),
(16, 'Mass Communication', 4, 'Bachelor of Communication (BComm)'),
(17, 'Media and Society', 3, 'Bachelor of Media Studies (BMS)'),
(18, 'Microeconomics', 4, 'Bachelor of Economics (BEcon)'),
(19, 'Data Structures and Algorithms', 3, 'Bachelor of Information Technology (BIT)'),
(20, 'Music Theory', 4, 'Bachelor of Music (BMus)'),
(21, 'Public Health Policy', 3, 'Bachelor of Public Health (BPH)'),
(22, 'International Relations', 4, 'Bachelor of International Relations (BIR)'),
(23, 'Nursing Care Management', 3, 'Bachelor of Science in Nursing (BSN)'),
(24, 'Sociology', 4, 'Bachelor of Social Sciences (BSS)'),
(25, 'Graphic Design Principles', 3, 'Bachelor of Graphic Design (BDes)'),
(26, 'Ethics and Philosophy', 4, 'Bachelor of Philosophy (BPhil)'),
(27, 'Hospitality Operations', 3, 'Bachelor of Hospitality Management (BHM)'),
(28, 'Sports Psychology', 4, 'Bachelor of Sports Science (BSSc)'),
(29, 'Environmental Engineering', 3, 'Bachelor of Environmental Engineering (BEnvE)'),
(30, 'Health Sciences Research', 4, 'Bachelor of Health Sciences (BHSc)');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `student_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `admission_date` date NOT NULL,
  `batch` varchar(20) NOT NULL,
  `course_name` varchar(120) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_id`, `first_name`, `last_name`, `date_of_birth`, `gender`, `address`, `contact_number`, `email`, `admission_date`, `batch`, `course_name`, `username`) VALUES
(13, 'John', 'Doe', '2000-01-01', 'Male', '123 Main St', '1234567890', 'john.doe@example.com', '2022-09-01', 'Batch A', 'Bachelor of Arts (BA)', 'john_doe123'),
(14, 'Jane', 'Smith', '1999-05-15', 'Female', '456 Oak Ave', '9876543210', 'jane.smith@example.com', '2022-09-01', 'Batch B', 'Bachelor of Science (BS)', 'jane_smith456'),
(15, 'Michael', 'Johnson', '2001-03-20', 'Male', '789 Elm Rd', '4561237890', 'michael.johnson@example.com', '2022-09-01', 'Batch C', 'Bachelor of Business Administration (BBA)', 'michael_johnson789'),
(16, 'Emily', 'Williams', '1998-12-05', 'Female', '321 Pine St', '9876543210', 'emily.williams@example.com', '2022-09-01', 'Batch D', 'Bachelor of Computer Science (BCS)', 'emily_williams321'),
(18, 'Sarah', 'Brown', '1997-09-25', 'Female', '987 Cedar Ave', '9876543210', 'sarah.brown@example.com', '2022-09-01', 'Batch B', 'Bachelor of Education (BEd)', 'sarah_brown987'),
(20, 'Jessica', 'Miller', '1999-07-18', 'Female', '567 Oak Rd', '9876543210', 'jessica.miller@example.com', '2022-09-01', 'Batch D', 'Bachelor of Commerce (BCom)', 'jessica_miller567'),
(22, 'Ashley', 'Anderson', '1998-11-12', 'Female', '432 Pine Rd', '9876543210', 'ashley.anderson@example.com', '2022-09-01', 'Batch B', 'Bachelor of Social Work (BSW)', 'ashley_anderson432'),
(24, 'Amanda', 'Harris', '1997-10-22', 'Female', '901 Cedar Ave', '9876543210', 'amanda.harris@example.com', '2022-09-01', 'Batch D', 'Bachelor of Medicine and Bachelor of Surgery (MBBS)', 'amanda_harris901');

-- --------------------------------------------------------

--
-- Table structure for table `student_courses`
--

CREATE TABLE `student_courses` (
  `enrollment_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `enrollment_date` date NOT NULL,
  `payment_method` enum('cash','credit_card','direct_debit') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_courses`
--

INSERT INTO `student_courses` (`enrollment_id`, `student_id`, `enrollment_date`, `payment_method`) VALUES
(13, 13, '2022-09-01', 'cash'),
(14, 14, '2022-09-01', 'credit_card'),
(15, 15, '2022-09-01', 'direct_debit'),
(16, 16, '2022-09-01', 'cash'),
(18, 18, '2022-09-01', 'credit_card'),
(20, 20, '2022-09-01', 'direct_debit'),
(22, 22, '2022-09-01', 'cash'),
(24, 24, '2022-09-01', 'credit_card');

-- --------------------------------------------------------

--
-- Table structure for table `transcript`
--

CREATE TABLE `transcript` (
  `transcript_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `semester` int(11) NOT NULL,
  `total_credits` int(11) NOT NULL,
  `total_marks` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `user_type` enum('student','staff','admin') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `user_type`) VALUES
(13, 'john_doe123', 'password123', 'student'),
(14, 'jane_smith456', 'password456', 'student'),
(15, 'michael_johnson789', 'password789', 'student'),
(16, 'emily_williams321', 'password321', 'student'),
(17, 'david_lee654', 'password654', 'staff'),
(18, 'sarah_brown987', 'password987', 'student'),
(19, 'chris_wilson234', 'password234', 'admin'),
(20, 'jessica_miller567', 'password567', 'student'),
(21, 'matt_taylor890', 'password890', 'admin'),
(22, 'ashley_anderson432', 'password432', 'student'),
(23, 'andrew_thomas876', 'password876', 'staff'),
(24, 'amanda_harris901', 'password901', 'student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `marks`
--
ALTER TABLE `marks`
  ADD PRIMARY KEY (`mark_id`),
  ADD KEY `enrollment_id` (`enrollment_id`),
  ADD KEY `module_id` (`module_id`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`module_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD PRIMARY KEY (`enrollment_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `transcript`
--
ALTER TABLE `transcript`
  ADD PRIMARY KEY (`transcript_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `marks`
--
ALTER TABLE `marks`
  MODIFY `mark_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `module_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `student_courses`
--
ALTER TABLE `student_courses`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `transcript`
--
ALTER TABLE `transcript`
  MODIFY `transcript_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `marks`
--
ALTER TABLE `marks`
  ADD CONSTRAINT `marks_ibfk_1` FOREIGN KEY (`enrollment_id`) REFERENCES `student_courses` (`enrollment_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `marks_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE CASCADE;

--
-- Constraints for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD CONSTRAINT `student_courses_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `transcript`
--
ALTER TABLE `transcript`
  ADD CONSTRAINT `transcript_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
