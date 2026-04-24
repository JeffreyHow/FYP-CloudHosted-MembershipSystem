-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 03, 2026 at 11:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `membership_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `activities`
--

CREATE TABLE `activities` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `max_participants` int(11) DEFAULT NULL,
  `participant_count` int(11) NOT NULL,
  `start_date` date DEFAULT NULL,
  `status` enum('ENDED','ONGOING','UPCOMING') DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activities`
--

INSERT INTO `activities` (`id`, `created_at`, `description`, `end_date`, `max_participants`, `participant_count`, `start_date`, `status`, `title`, `updated_at`) VALUES
(1, '2026-01-24 10:30:11.000000', 'An annual appreciation event organized to thank our valued members for their continued support. \r\nExclusive benefits and special rewards will be offered throughout the event period.\r\n\r\n', '2026-02-07', 1000, 0, '2026-02-01', 'UPCOMING', 'Annual Membership Appreciation Event', '2026-01-24 20:22:28.000000'),
(5, '2026-01-24 20:21:59.000000', 'Celebrating our 5th anniversary with exclusive offers and gifts for all members', '2026-07-24', 1000, 0, '2026-01-24', 'ONGOING', 'Anniversary Celebration', '2026-01-24 20:21:59.000000');

-- --------------------------------------------------------

--
-- Table structure for table `activity_target_levels`
--

CREATE TABLE `activity_target_levels` (
  `activity_id` bigint(20) NOT NULL,
  `membership_level` enum('INTERMEDIATE','JUNIOR','SENIOR') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity_target_levels`
--

INSERT INTO `activity_target_levels` (`activity_id`, `membership_level`) VALUES
(1, 'INTERMEDIATE'),
(1, 'SENIOR'),
(5, 'JUNIOR'),
(5, 'INTERMEDIATE'),
(5, 'SENIOR');

-- --------------------------------------------------------

--
-- Table structure for table `admin_users`
--

CREATE TABLE `admin_users` (
  `id` varchar(36) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_users`
--

INSERT INTO `admin_users` (`id`, `created_at`, `email`, `last_login`, `name`, `password_hash`) VALUES
('302535e2-e4c0-4ca9-80e7-5c1f9edb9a75', '2026-01-20 19:07:46.000000', 'admin@example.com', NULL, 'System Admin', '$2a$10$pwhN2BX/gzc1k735T2APwePnQoFxKIFdxxxGGut4Enw0l1de1t.ZW'),
('97b6c865-511e-425d-ad11-1a3328f05993', '2026-01-20 18:46:59.000000', 'jeffreyhow118@gmail.com', NULL, 'How Zi Yang', '$2a$10$aEWAZyx5.r0QuJwyiVIjQuokmUWY15TWSwNzUrvEq6zqIOvNyjtcK');

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `discount_type` enum('FIXED_AMOUNT','FREE_SHIPPING','PERCENTAGE') NOT NULL,
  `discount_value` int(11) NOT NULL,
  `expiry_date` date DEFAULT NULL,
  `min_purchase` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `points_cost` int(11) NOT NULL,
  `status` enum('ACTIVE','EXPIRED','INACTIVE') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `usage_limit` int(11) NOT NULL,
  `used_count` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `code`, `created_at`, `discount_type`, `discount_value`, `expiry_date`, `min_purchase`, `name`, `points_cost`, `status`, `updated_at`, `usage_limit`, `used_count`, `description`) VALUES
(16, 'WELCOME2026', '2026-01-23 03:15:06.000000', 'PERCENTAGE', 20, '2026-12-31', 100, 'New Member Welcome Coupon', 0, 'ACTIVE', '2026-02-02 00:34:58.000000', 1000, 13, 'Get 10% off on your next purchase'),
(17, 'VIP100', '2026-01-23 03:15:22.000000', 'FIXED_AMOUNT', 100, '2026-06-30', 500, 'Senior Member Exclusive', 5000, 'ACTIVE', '2026-01-23 03:15:22.000000', 300, 8, 'Receive RM100 discount on purchases over RM500 (Senior Members Only)');

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `id` varchar(36) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `join_date` date DEFAULT NULL,
  `level` enum('INTERMEDIATE','JUNIOR','SENIOR') NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `points` int(11) NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `subscription` enum('ANNUALLY','MONTHLY','NONE','WEEKLY') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`id`, `created_at`, `email`, `join_date`, `level`, `name`, `phone`, `points`, `status`, `updated_at`, `password_hash`, `subscription`) VALUES
('0df032dc-a12e-4ddf-a6be-837ffbb7604a', '2026-01-28 20:37:42.000000', 'kkk@example.com', '2026-01-28', 'JUNIOR', 'KKK', '0134533359', 0, 'ACTIVE', '2026-01-28 20:37:42.000000', '$2a$10$Sm9jQUR2mnPPxJ5DvWabg.kKw/5ZUbMIdZZw9Hj4c3.IC3oLqIca6', 'NONE'),
('459a243a-0bef-4956-a9b0-81f17fdcdcbd', '2026-01-24 15:19:26.000000', 'jeffreyhow119@gmail.com', '2026-01-24', 'JUNIOR', 'Jeffrey How', '01131489848', 0, 'ACTIVE', '2026-01-24 15:19:26.000000', '$2a$10$8RbjX5ucg.ml6KbtOeEbAeZTV1IJZbsD.Pgj.iwiWzz02w96mnZd.', 'NONE'),
('4bf4d5b9-14a0-42bd-9578-d09566042fd2', '2026-01-23 20:14:01.000000', 'jeffreyhow118@gmail.com', NULL, 'JUNIOR', 'Jeffrey How', '01131489847', 500, 'INACTIVE', '2026-01-31 23:25:21.000000', '$2a$10$5jmZi1Jow3ZfYlSGR6.yH.WDe1/l0qTJLc3u.xU1WXoXF1/ZXkkXK', 'NONE'),
('54d28b57-f624-11f0-a847-b40ede15b50c', '2026-01-20 12:00:00.000000', 'alice@test.com', '2024-01-10', 'JUNIOR', 'Alice Tan', '0121111111', 200, 'ACTIVE', '2026-01-22 16:07:34.000000', 'dummy', 'MONTHLY'),
('54d2a63c-f624-11f0-a847-b40ede15b50c', '2025-12-10 09:00:00.000000', 'ben@test.com', '2024-02-05', 'JUNIOR', 'Ben Lee', '0122222222', 350, 'ACTIVE', NULL, 'dummy', 'NONE'),
('54d2a864-f624-11f0-a847-b40ede15b50c', '2025-09-26 18:00:00.000000', 'cindy@test.com', '2023-11-20', 'JUNIOR', 'Cindy Lim', '0123333333', 120, 'INACTIVE', NULL, 'dummy', 'NONE'),
('54d2a9c2-f624-11f0-a847-b40ede15b50c', '2025-11-18 16:00:00.000000', 'david@test.com', '2023-12-01', 'INTERMEDIATE', 'David Wong', '0134444444', 800, 'ACTIVE', NULL, 'dummy', 'MONTHLY'),
('54d2aadb-f624-11f0-a847-b40ede15b50c', '2025-12-22 15:00:00.000000', 'eva@test.com', '2024-01-18', 'INTERMEDIATE', 'Eva Chan', '0135555555', 950, 'ACTIVE', NULL, 'dummy', 'ANNUALLY'),
('54d2abe8-f624-11f0-a847-b40ede15b50c', '2025-11-05 14:00:00.000000', 'frank@test.com', '2024-02-02', 'INTERMEDIATE', 'Frank Ho', '0136666666', 700, 'INACTIVE', NULL, 'dummy', 'WEEKLY'),
('54d2ad57-f624-11f0-a847-b40ede15b50c', '2025-10-08 10:30:00.000000', 'grace@test.com', '2023-08-15', 'SENIOR', 'Grace Low', '0147777777', 1500, 'ACTIVE', NULL, 'dummy', 'ANNUALLY'),
('54d2aea2-f624-11f0-a847-b40ede15b50c', '2025-09-03 09:15:00.000000', 'henry@test.com', '2023-07-01', 'SENIOR', 'Henry Ong', '0148888888', 1800, 'ACTIVE', NULL, 'dummy', 'ANNUALLY'),
('54d2afcb-f624-11f0-a847-b40ede15b50c', '2025-10-21 17:45:00.000000', 'ivy@test.com', '2023-09-10', 'SENIOR', 'Ivy Teo', '0149999999', 1600, 'ACTIVE', NULL, 'dummy', 'MONTHLY'),
('82567c81-f5c8-11f0-a847-b40ede15b50c', '2026-01-15 10:00:00.000000', 'john.tan@example.com', '2026-01-20', 'JUNIOR', 'John Tan', '0123456789', 0, 'ACTIVE', '2026-01-20 14:23:17.000000', 'customer123', 'NONE'),
('a3085a98-f5c8-11f0-a847-b40ede15b50c', '2026-01-18 11:00:00.000000', 'alice.lim@example.com', '2026-01-20', 'SENIOR', 'Alice Lim', '0139876543', 1200, 'ACTIVE', '2026-01-20 14:24:12.000000', 'mypassword', 'MONTHLY'),
('d2f2ebd2-20e8-45f8-8432-fa569c435ecc', '2026-02-01 23:27:07.000000', 'johan123@gmail.com', '2026-02-01', 'JUNIOR', 'Johan', '0123334444', 36500, 'ACTIVE', '2026-02-02 01:56:57.000000', '$2a$10$B3ibWWX9Szafuwg9N87Yvu3IXtCCJ7W/Wj5h5v6fJt96IZJyWdiuy', 'MONTHLY');

-- --------------------------------------------------------

--
-- Table structure for table `member_subscriptions`
--

CREATE TABLE `member_subscriptions` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) NOT NULL,
  `points_awarded` int(11) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `status` enum('ACTIVE','CANCELLED','EXPIRED') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `member_id` varchar(36) NOT NULL,
  `plan_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `member_subscriptions`
--

INSERT INTO `member_subscriptions` (`id`, `created_at`, `end_date`, `points_awarded`, `start_date`, `status`, `updated_at`, `member_id`, `plan_id`) VALUES
(1, '2026-01-31 19:35:59.000000', '2026-02-07 19:35:59.000000', 500, '2026-01-31 19:35:59.000000', 'CANCELLED', '2026-01-31 23:17:32.000000', '4bf4d5b9-14a0-42bd-9578-d09566042fd2', 'weekly'),
(2, '2026-01-31 23:17:32.000000', '2026-02-28 23:17:32.000000', 2000, '2026-01-31 23:17:32.000000', 'CANCELLED', '2026-01-31 23:25:21.000000', '4bf4d5b9-14a0-42bd-9578-d09566042fd2', 'monthly'),
(3, '2026-01-31 23:25:21.000000', '2026-02-07 23:25:21.000000', 500, '2026-01-31 23:25:21.000000', 'ACTIVE', '2026-01-31 23:25:21.000000', '4bf4d5b9-14a0-42bd-9578-d09566042fd2', 'weekly'),
(4, '2026-02-01 23:27:29.000000', '2026-03-01 23:27:29.000000', 2000, '2026-02-01 23:27:29.000000', 'CANCELLED', '2026-02-01 23:28:01.000000', 'd2f2ebd2-20e8-45f8-8432-fa569c435ecc', 'monthly'),
(5, '2026-02-01 23:28:01.000000', '2027-02-01 23:28:01.000000', 30000, '2026-02-01 23:28:01.000000', 'CANCELLED', '2026-02-01 23:28:05.000000', 'd2f2ebd2-20e8-45f8-8432-fa569c435ecc', 'annually'),
(6, '2026-02-01 23:28:05.000000', '2026-03-01 23:28:05.000000', 2000, '2026-02-01 23:28:05.000000', 'CANCELLED', '2026-02-02 01:56:55.000000', 'd2f2ebd2-20e8-45f8-8432-fa569c435ecc', 'monthly'),
(7, '2026-02-02 01:56:55.000000', '2026-02-09 01:56:55.000000', 500, '2026-02-02 01:56:55.000000', 'CANCELLED', '2026-02-02 01:56:57.000000', 'd2f2ebd2-20e8-45f8-8432-fa569c435ecc', 'weekly'),
(8, '2026-02-02 01:56:57.000000', '2026-03-02 01:56:57.000000', 2000, '2026-02-02 01:56:57.000000', 'ACTIVE', '2026-02-02 01:56:57.000000', 'd2f2ebd2-20e8-45f8-8432-fa569c435ecc', 'monthly');

-- --------------------------------------------------------

--
-- Table structure for table `rewards`
--

CREATE TABLE `rewards` (
  `id` bigint(20) NOT NULL,
  `availability` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` text DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `points_cost` int(11) NOT NULL,
  `popular` bit(1) NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `type` enum('CASHBACK','COUPON','GIFT') NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `coupon_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscription_plans`
--

CREATE TABLE `subscription_plans` (
  `id` varchar(50) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `features` text DEFAULT NULL,
  `highlighted` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `period` varchar(255) NOT NULL,
  `points` int(11) DEFAULT NULL,
  `price` varchar(255) NOT NULL,
  `price_cents` int(11) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subscription_plans`
--

INSERT INTO `subscription_plans` (`id`, `created_at`, `description`, `features`, `highlighted`, `name`, `period`, `points`, `price`, `price_cents`, `updated_at`) VALUES
('annually', NULL, 'Best value for committed members', '30000 bonus points,Access to all vouchers,15% cashback on purchases,Priority customer support,Free shipping on all orders,Birthday special rewards,Exclusive annual member events,Personal account manager,Early access to new products', b'0', 'Annual Plan', 'YEAR', 30000, 'RM1,299.99', 129999, NULL),
('monthly', NULL, 'Most popular choice for regular members', '2000 bonus points,Access to all vouchers,10% cashback on purchases,Priority customer support,Free shipping on all orders,Birthday special rewards', b'1', 'Monthly Plan', 'MONTH', 2000, 'RM129.99', 12999, NULL),
('weekly', NULL, 'Perfect for trying out membership benefits', '500 bonus points,Weekly exclusive vouchers,5% cashback on purchases,Priority customer support', b'0', 'Weekly Plan', 'WEEK', 500, 'RM39.99', 3999, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `voucher_codes`
--

CREATE TABLE `voucher_codes` (
  `code` varchar(50) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `is_used` bit(1) NOT NULL,
  `redeemed_at` datetime(6) DEFAULT NULL,
  `used_at` datetime(6) DEFAULT NULL,
  `coupon_id` bigint(20) NOT NULL,
  `member_id` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `voucher_codes`
--

INSERT INTO `voucher_codes` (`code`, `created_at`, `is_used`, `redeemed_at`, `used_at`, `coupon_id`, `member_id`) VALUES
('VCH-3AD7A27E', '2026-02-02 00:34:58.000000', b'0', '2026-02-02 00:34:58.000000', NULL, 16, 'd2f2ebd2-20e8-45f8-8432-fa569c435ecc'),
('VIP100-P4Q5R6', '2026-01-20 03:19:26.000000', b'0', NULL, NULL, 17, NULL),
('VIP100-S7T8U9', '2026-01-22 03:19:26.000000', b'0', NULL, NULL, 17, NULL),
('VIP100-X9Y8Z7', '2026-01-17 03:19:26.000000', b'1', '2026-01-18 03:19:26.000000', '2026-01-18 03:19:26.000000', 17, 'c3f5e3f2-3333-4ccc-8b03-ccc333333333'),
('WELCOME26-A1B2C3', '2026-01-13 03:18:41.000000', b'1', '2026-01-14 03:18:41.000000', '2026-01-14 03:18:41.000000', 16, 'a1f3c1e0-1111-4aaa-8b01-aaa111111111'),
('WELCOME26-D4E5F6', '2026-01-14 03:18:41.000000', b'1', '2026-01-15 03:18:41.000000', '2026-01-15 03:18:41.000000', 16, 'b2f4d2e1-2222-4bbb-8b02-bbb222222222'),
('WELCOME26-G7H8I9', '2026-01-18 03:18:41.000000', b'0', NULL, NULL, 16, NULL),
('WELCOME26-J1K2L3', '2026-01-19 03:18:41.000000', b'0', NULL, NULL, 16, NULL),
('WELCOME26-M4N5O6', '2026-01-21 03:18:41.000000', b'0', NULL, NULL, 16, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `voucher_redemptions`
--

CREATE TABLE `voucher_redemptions` (
  `id` bigint(20) NOT NULL,
  `redeemed_at` datetime(6) DEFAULT NULL,
  `used_at` datetime(6) DEFAULT NULL,
  `voucher_code` varchar(255) NOT NULL,
  `coupon_id` bigint(20) NOT NULL,
  `member_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `voucher_redemptions`
--

INSERT INTO `voucher_redemptions` (`id`, `redeemed_at`, `used_at`, `voucher_code`, `coupon_id`, `member_id`) VALUES
(61, '2026-01-14 03:20:57.000000', '2026-01-14 03:20:57.000000', 'WELCOME26-A1B2C3', 16, 'a1f3c1e0-1111-4aaa-8b01-aaa111111111'),
(62, '2026-01-15 03:20:57.000000', '2026-01-15 03:20:57.000000', 'WELCOME26-D4E5F6', 16, 'b2f4d2e1-2222-4bbb-8b02-bbb222222222'),
(63, '2026-01-18 03:20:57.000000', '2026-01-18 03:20:57.000000', 'VIP100-X9Y8Z7', 17, 'c3f5e3f2-3333-4ccc-8b03-ccc333333333'),
(64, '2026-02-02 00:34:58.000000', NULL, 'VCH-3AD7A27E', 16, 'd2f2ebd2-20e8-45f8-8432-fa569c435ecc');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activities`
--
ALTER TABLE `activities`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `activity_target_levels`
--
ALTER TABLE `activity_target_levels`
  ADD KEY `FKnbu5k1ggoeifpic0ifjbpsqkp` (`activity_id`);

--
-- Indexes for table `admin_users`
--
ALTER TABLE `admin_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKcp8822350s9vtyww7xdbgeuvu` (`email`);

--
-- Indexes for table `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKeplt0kkm9yf2of2lnx6c1oy9b` (`code`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK9d30a9u1qpg8eou0otgkwrp5d` (`email`),
  ADD UNIQUE KEY `UK1ftq959eeh8vwc4ywlxwclqjj` (`phone`);

--
-- Indexes for table `member_subscriptions`
--
ALTER TABLE `member_subscriptions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKc18j5p03etmummb59c1vdf3tg` (`member_id`),
  ADD KEY `FKen4rodjl372vc5kuh0vq1hb24` (`plan_id`);

--
-- Indexes for table `rewards`
--
ALTER TABLE `rewards`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa0pm3924oi1tq41lqoyvmwrqc` (`coupon_id`);

--
-- Indexes for table `subscription_plans`
--
ALTER TABLE `subscription_plans`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `voucher_codes`
--
ALTER TABLE `voucher_codes`
  ADD PRIMARY KEY (`code`),
  ADD KEY `FKr1q3saivexe22x8k40lqcmvre` (`coupon_id`),
  ADD KEY `FKsj21mvqjbgg14mq7j95lsnrr9` (`member_id`);

--
-- Indexes for table `voucher_redemptions`
--
ALTER TABLE `voucher_redemptions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKaylpqlfl8y8yu37f1m3nrqhtp` (`voucher_code`),
  ADD KEY `FKaqfhgbod3skqnoddiod6csp27` (`coupon_id`),
  ADD KEY `FKew5had5g69cu7gik9sugrgkdh` (`member_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activities`
--
ALTER TABLE `activities`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `member_subscriptions`
--
ALTER TABLE `member_subscriptions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `rewards`
--
ALTER TABLE `rewards`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `voucher_redemptions`
--
ALTER TABLE `voucher_redemptions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity_target_levels`
--
ALTER TABLE `activity_target_levels`
  ADD CONSTRAINT `FKnbu5k1ggoeifpic0ifjbpsqkp` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`);

--
-- Constraints for table `member_subscriptions`
--
ALTER TABLE `member_subscriptions`
  ADD CONSTRAINT `FKc18j5p03etmummb59c1vdf3tg` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
  ADD CONSTRAINT `FKen4rodjl372vc5kuh0vq1hb24` FOREIGN KEY (`plan_id`) REFERENCES `subscription_plans` (`id`);

--
-- Constraints for table `rewards`
--
ALTER TABLE `rewards`
  ADD CONSTRAINT `FKa0pm3924oi1tq41lqoyvmwrqc` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`);

--
-- Constraints for table `voucher_codes`
--
ALTER TABLE `voucher_codes`
  ADD CONSTRAINT `FKr1q3saivexe22x8k40lqcmvre` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`),
  ADD CONSTRAINT `FKsj21mvqjbgg14mq7j95lsnrr9` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`);

--
-- Constraints for table `voucher_redemptions`
--
ALTER TABLE `voucher_redemptions`
  ADD CONSTRAINT `FKaqfhgbod3skqnoddiod6csp27` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`),
  ADD CONSTRAINT `FKew5had5g69cu7gik9sugrgkdh` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
