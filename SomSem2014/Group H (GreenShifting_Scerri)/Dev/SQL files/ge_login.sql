-- phpMyAdmin SQL Dump
-- version 2.11.11.3
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.3
-- Erstellungszeit: 03. Oktober 2014 um 15:39
-- Server Version: 5.6.19
-- PHP-Version: 4.4.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Datenbank: `db403573`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ge_login`
--

CREATE TABLE `ge_login` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` char(40) NOT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

--
-- Daten für Tabelle `ge_login`
--

INSERT INTO `ge_login` (`ID`, `username`, `password`, `score`) VALUES
(24, 'tester', 'ab4d8d2a5f480a137067da17100271cd176607a1', 0),
(23, 'tschocker', 'c03dd32822de4ffc4260a34980649380508c688d', 3),
(6, 'nika', '836e7ec46c6210268a153f90bd060f8af0b9fb41', 1714),
(7, 'sarvy', '7c4a8d09ca3762af61e59520943dc26494f8941b', 0),
(8, 'mah', '8cb2237d0679ca88db6464eac60da96345513964', 0),
(9, 'mahi', '8cb2237d0679ca88db6464eac60da96345513964', 0),
(22, 'test123', '7288edd0fc3ffcbe93a0cf06e3568e28521687bc', 0),
(11, 'theresa', '66c02d06bd545f9d3b10dad8de3bcb9c78b7645d', 14),
(12, 'sarvenaz', '7c4a8d09ca3762af61e59520943dc26494f8941b', 4),
(13, 'mahnazzz', '7bbd2454b50b59bf28ab33c9e60a37caabaf852c', 0),
(15, 'mahnazjoon', '8cb2237d0679ca88db6464eac60da96345513964', 10),
(21, 'Newtest', '1872f6eb6b6831cba7a3f4fdbaaf7cca7bd638f1', 0),
(20, 'benutzer', '6d3c60eeb2ddd9cce8de6c092c091ae23ffd2264', 8);
