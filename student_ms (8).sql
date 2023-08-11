-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 11, 2023 at 10:16 AM
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
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL,
  `course_code` varchar(10) NOT NULL,
  `course_name` varchar(120) NOT NULL,
  `course_duration` varchar(50) NOT NULL,
  `status` enum('active','archived') NOT NULL,
  `tuition` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`course_id`, `course_code`, `course_name`, `course_duration`, `status`, `tuition`) VALUES
(1, 'CS101', 'Bachelor of Arts (BA)', '4', 'active', 45000),
(2, 'ENG202', 'Bachelor of Science (BS)', '3', 'active', 30000),
(3, 'MATH301', 'Bachelor of Business Administration (BBA)', '4', 'active', 40000),
(4, 'BUS101', 'Bachelor of Computer Science (BCS)', '3', 'active', 47000),
(5, 'SCI201', 'Bachelor of Engineering (BE)', '4', 'active', 47000),
(6, 'PHYSICS401', 'Bachelor of Education (BEd)', '3', 'active', 69000),
(7, 'CHEM202', 'Bachelor of Fine Arts (BFA)', '4', 'active', 41000),
(8, 'HIST101', 'Bachelor of Commerce (BCom)', '3', 'active', 46000),
(9, 'ARTS301', 'Bachelor of Nursing (BN)', '4', 'active', 35000),
(10, 'PSYCH101', 'Bachelor of Social Work (BSW)', '3', 'active', 47000),
(11, 'SOCIOLOGY2', 'Bachelor of Architecture (BArch)', '4', 'active', 48000),
(12, 'ECONOMICS3', 'Bachelor of Medicine and Bachelor of Surgery (MBBS)', '3', 'active', 50000);

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
(27, 13, 93, 75, 82.5),
(28, 14, 94, 68, 90),
(29, 14, 2, 90.5, 92),
(30, 15, 1, 78, 85.5),
(31, 15, 93, 83.5, 88),
(32, 16, 6, 70, 76.5),
(33, 18, 98, 92.5, 90.5),
(34, 18, 9, 88, 84),
(35, 18, 10, 79.5, 80),
(36, 20, 2, 94, 92.5),
(37, 20, 94, 85, 78.5),
(38, 20, 101, 80.5, 86),
(39, 22, 8, 72, 78.5),
(40, 22, 100, 85, 88.5),
(41, 16, 98, 78.5, 85),
(42, 16, 5, 92, 90),
(43, 18, 6, 88.5, 86.5),
(44, 22, 7, 76, 82),
(45, 13, 5, 84.5, 89.5),
(46, 14, 9, 87, 80.5),
(47, 22, 10, 90, 94),
(48, 24, 3, 82.5, 85.5),
(49, 24, 95, 78, 81),
(50, 24, 1, 88, 92.5),
(76, 14, 103, 67.5, 80);

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `module_id` int(11) NOT NULL,
  `module_name` varchar(100) NOT NULL,
  `credits` int(11) NOT NULL,
  `course_name` varchar(120) NOT NULL,
  `day` varchar(10) DEFAULT NULL,
  `time_slot` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`module_id`, `module_name`, `credits`, `course_name`, `day`, `time_slot`) VALUES
(1, 'Introduction to Literature', 3, 'Bachelor of Arts (BA)', 'MONDAY', '08:00:00'),
(2, 'Calculus I', 4, 'Bachelor of Science (BS)', 'TUESDAY', '09:30:00'),
(3, 'Principles of Management', 3, 'Bachelor of Business Administration (BBA)', 'WEDNESDAY', '11:00:00'),
(4, 'Introduction to Programming', 4, 'Bachelor of Computer Science (BCS)', 'MONDAY', '12:00:00'),
(5, 'Engineering Mechanics', 3, 'Bachelor of Arts (BA)', 'MONDAY', '13:00:00'),
(6, 'Educational Psychology', 4, 'Bachelor of Education (BEd)', 'MONDAY', '14:00:00'),
(7, 'Painting Techniques', 3, 'Bachelor of Fine Arts (BFA)', 'SATURDAY', '15:00:00'),
(8, 'Marketing Fundamentals', 4, 'Bachelor of Commerce (BCom)', 'WEDNESDAY', '08:30:00'),
(9, 'Anatomy and Physiology', 3, 'Bachelor of Science (BS)', 'TUESDAY', '11:30:00'),
(10, 'Social Work Ethics', 4, 'Bachelor of Education (BEd)', 'WEDNESDAY', '10:30:00'),
(11, 'Architectural Design', 3, 'Bachelor of Architecture (BArch)', 'MONDAY', '08:00:00'),
(12, 'Human Anatomy', 4, 'Bachelor of Medicine and Bachelor of Surgery (MBBS)', 'MONDAY', '09:30:00'),
(93, 'Introduction to Biology', 3, 'Bachelor of Arts (BA)', 'THURSDAY', '12:00:00'),
(94, 'Advanced Calculus', 4, 'Bachelor of Science (BS)', 'TUESDAY', '09:30:00'),
(95, 'Financial Accounting', 3, 'Bachelor of Business Administration (BBA)', 'WEDNESDAY', '11:00:00'),
(96, 'Java Programming', 4, 'Bachelor of Computer Science (BCS)', 'TUESDAY', '12:00:00'),
(97, 'Electromagnetism', 3, 'Bachelor of Engineering (BE)', 'MONDAY', '13:00:00'),
(98, 'Educational Technology', 4, 'Bachelor of Education (BEd)', 'MONDAY', '14:00:00'),
(99, 'Watercolor Painting', 3, 'Bachelor of Fine Arts (BFA)', 'TUESDAY', '15:00:00'),
(100, 'Marketing Management', 4, 'Bachelor of Commerce (BCom)', 'FRIDAY', '08:30:00'),
(101, 'Human Physiology', 3, 'Bachelor of Science (BS)', 'WEDNESDAY', '11:30:00'),
(102, 'Social Work Interventions', 4, 'Bachelor of Social Work (BSW)', 'WEDNESDAY', '08:00:00'),
(103, 'Human Physiology', 3, 'Bachelor of Science (BS)', 'MONDAY', '08:00:00'),
(104, 'Medical Ethics', 4, 'Bachelor of Medicine and Bachelor of Surgery (MBBS)', 'TUESDAY', '09:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `payment_status` varchar(50) DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `admin_fee` tinyint(1) DEFAULT NULL,
  `tuition_fee` tinyint(1) DEFAULT NULL,
  `transport_fee` tinyint(1) DEFAULT NULL,
  `Other` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`payment_id`, `userID`, `payment_status`, `payment_method`, `admin_fee`, `tuition_fee`, `transport_fee`, `Other`) VALUES
(1, 13, 'Complete Payment Done', 'Cash', 1, 1, NULL, 'Metro Card'),
(2, 13, 'Complete Payment Done', 'Cash', 1, 1, NULL, 'Bus Pass');

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
  `course_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_id`, `first_name`, `last_name`, `date_of_birth`, `gender`, `address`, `contact_number`, `email`, `admission_date`, `batch`, `course_id`, `username`) VALUES
(13, 'John', 'Doe', '2000-01-01', 'Male', '123 Main St', '1234567890', 'john.doe@example.com', '2022-09-01', 'Batch A', 1, 'john_doe123'),
(14, 'Jane', 'Smith', '1999-05-15', 'Female', '456 Oak Ave', '9876543210', 'jane.smith@example.com', '2022-09-01', 'Batch B', 2, 'jane_smith456'),
(15, 'Michael', 'Johnson', '2001-03-20', 'Male', '789 Elm Rd', '4561237890', 'michael.johnson@example.com', '2022-09-01', 'Batch C', 1, 'michael_johnson789'),
(16, 'Emily', 'Williams', '1998-12-05', 'Female', '321 Pine St', '9876543210', 'emily.williams@example.com', '2022-09-01', 'Batch D', 6, 'emily_williams321'),
(18, 'Sarah', 'Brown', '1997-09-25', 'Female', '987 Cedar Ave', '9876543210', 'sarah.brown@example.com', '2022-09-01', 'Batch B', 6, 'sarah_brown987'),
(20, 'Jessica', 'Miller', '1999-07-18', 'Female', '567 Oak Rd', '9876543210', 'jessica.miller@example.com', '2022-09-01', 'Batch D', 2, 'jessica_miller567'),
(22, 'Ashley', 'Anderson', '1998-11-12', 'Female', '432 Pine Rd', '9876543210', 'ashley.anderson@example.com', '2022-09-01', 'Batch B', 8, 'ashley_anderson432'),
(24, 'Amanda', 'Harris', '1997-10-22', 'Female', '901 Cedar Ave', '9876543210', 'amanda.harris@example.com', '2022-09-01', 'Batch D', 3, 'amanda_harris901'),
(25, 'Getty', 'Getty', '1998-05-12', 'Male', 'my house77', '7830928167', 'gettywods@gmail.com', '2023-01-15', 'Batch A', 1, 'getty77'),
(26, 'Lion', 'Lioness', '1999-07-08', 'Female', '456 Park Ave', '9876543210', 'lion@example.com', '2022-12-01', 'Batch B', 2, 'lion44'),
(27, 'Today', 'Iamtired', '2000-03-20', 'Male', '789 Broadway', '9876543210', 'today@example.com', '2023-05-30', 'Batch C', 3, 'todayy');

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
(24, 24, '2022-09-01', 'credit_card'),
(25, 25, '2023-01-15', 'credit_card'),
(26, 26, '2022-12-01', 'cash'),
(27, 27, '2023-05-30', 'direct_debit');

-- --------------------------------------------------------

--
-- Table structure for table `student_modules`
--

CREATE TABLE `student_modules` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `module_id` int(11) NOT NULL,
  `module_name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_modules`
--

INSERT INTO `student_modules` (`id`, `username`, `module_id`, `module_name`) VALUES
(2, 'jessica_miller567', 2, 'Calculus I'),
(3, 'john_doe123', 1, 'Introduction to Literature'),
(5, 'john_doe123', 5, 'Engineering Mechanics'),
(6, 'john_doe123', 93, 'Introduction to Biology');

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
  `userType` enum('student','staff','admin') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `userType`) VALUES
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
(24, 'amanda_harris901', 'password901', 'student'),
(25, 'getty77', 'iamgetty11', 'student'),
(26, 'lion44', 'lioness44', 'student'),
(27, 'todayy', 'iamtired44', 'student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`);

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
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `fk_course_id` (`course_id`),
  ADD KEY `username` (`username`);

--
-- Indexes for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD PRIMARY KEY (`enrollment_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `student_modules`
--
ALTER TABLE `student_modules`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`),
  ADD KEY `module_id` (`module_id`);

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
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `marks`
--
ALTER TABLE `marks`
  MODIFY `mark_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `module_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `student_courses`
--
ALTER TABLE `student_courses`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `student_modules`
--
ALTER TABLE `student_modules`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `transcript`
--
ALTER TABLE `transcript`
  MODIFY `transcript_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

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
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `student` (`student_id`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_course_id` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE;

--
-- Constraints for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD CONSTRAINT `student_courses_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `student_modules`
--
ALTER TABLE `student_modules`
  ADD CONSTRAINT `student_modules_ibfk_1` FOREIGN KEY (`username`) REFERENCES `student` (`username`),
  ADD CONSTRAINT `student_modules_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`);

--
-- Constraints for table `transcript`
--
ALTER TABLE `transcript`
  ADD CONSTRAINT `transcript_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
