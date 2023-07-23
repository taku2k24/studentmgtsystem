-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 23, 2023 at 04:44 PM
-- Server version: 8.0.33-0ubuntu0.22.04.2
-- PHP Version: 8.1.2-1ubuntu2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studentsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `course_ID` int NOT NULL,
  `course_Name` varchar(50) NOT NULL,
  `course_Fee` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`course_ID`, `course_Name`, `course_Fee`) VALUES
(2474, 'Electrical Engineering', 220000),
(2983, 'Software Engineering', 120000),
(3562, 'Computer Science', 200000),
(4892, 'Marine Science', 330000),
(5683, 'Information Systems', 70000),
(9273, 'Bio-engineering', 170000),
(9473, 'Mechatronics', 160000);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_ID` int NOT NULL,
  `student_Name` varchar(50) NOT NULL,
  `course_ID` int DEFAULT NULL,
  `payment_status` varchar(10) NOT NULL DEFAULT 'Not Paid'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_ID`, `student_Name`, `course_ID`, `payment_status`) VALUES
(25364, 'Bob', NULL, 'Not Paid'),
(62521, 'Samantha', 4892, 'Paid'),
(82263, 'Jane', NULL, 'Not Paid'),
(83652, 'Carl', NULL, 'Not Paid'),
(92736, 'Mathew', NULL, 'Not Paid'),
(93762, 'Alice', NULL, 'Not Paid');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_ID`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
