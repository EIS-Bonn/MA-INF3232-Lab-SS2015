-- phpMyAdmin SQL Dump
-- version 2.11.11.3
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.3
-- Erstellungszeit: 03. Oktober 2014 um 15:40
-- Server Version: 5.6.19
-- PHP-Version: 4.4.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Datenbank: `db403573`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `predictiondata`
--

CREATE TABLE `predictiondata` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `timehour` time NOT NULL,
  `solar` double NOT NULL,
  `wind` double NOT NULL,
  `conventional` double NOT NULL,
  `total` double NOT NULL,
  `percentage` double NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

--
-- Daten für Tabelle `predictiondata`
--

INSERT INTO `predictiondata` (`ID`, `timehour`, `solar`, `wind`, `conventional`, `total`, `percentage`) VALUES
(1, '23:45:00', 0, 724.5, 5114.5, 5839, 12.41),
(2, '00:45:00', 0, 287.3, 5531.7, 5819, 4.94),
(3, '01:45:00', 0, 175.9, 5884.1, 6060, 2.9),
(4, '02:45:00', 0, 88.7, 5947.3, 6036, 1.47),
(5, '03:45:00', 0, 185.8, 5864.2, 6050, 3.07),
(6, '04:45:00', 0, 230.4, 5867.6, 6098, 3.78),
(7, '05:45:00', 16, 302.1, 5880.9, 6199, 5.13),
(8, '06:45:00', 277.2, 403.2, 6578.6, 7259, 9.37),
(9, '07:45:00', 801.8, 396.9, 6252.3, 7451, 16.09),
(10, '08:45:00', 1624.3, 528, 5302.7, 7455, 28.87),
(11, '09:45:00', 2427.1, 748.3, 3575.6, 6751, 47.04),
(12, '10:45:00', 2536.2, 721.4, 3698.4, 6956, 46.83),
(13, '11:45:00', 3173.8, 1013.6, 2438.6, 6626, 63.2),
(14, '12:45:00', 3663.5, 1278.3, 1429.2, 6371, 77.57),
(15, '13:45:00', 3918.2, 1459.8, 1081, 6459, 83.26),
(16, '14:45:00', 3253.4, 1312, 2074.6, 6640, 68.76),
(17, '15:45:00', 3000.5, 898.3, 2813.2, 6712, 58.09),
(18, '16:45:00', 2114.5, 764.6, 3613.9, 6493, 44.34),
(19, '17:45:00', 1435, 562.2, 4735.8, 6733, 29.66),
(20, '18:45:00', 764.4, 707.3, 5190.3, 6662, 22.09),
(21, '19:45:00', 314.1, 400.6, 6291.3, 7006, 10.2),
(22, '20:45:00', 52.6, 685.8, 6198.6, 6937, 10.64),
(23, '21:45:00', 0, 525.7, 5619.3, 6145, 8.55),
(24, '22:45:00', 0, 429.4, 5699.6, 6129, 7.01);
