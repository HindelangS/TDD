-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 04. Mrz 2018 um 21:37
-- Server-Version: 5.6.26
-- PHP-Version: 5.5.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `ballondb`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ballonfahrt`
--

CREATE TABLE IF NOT EXISTS `ballonfahrt` (
  `FlugID` int(255) NOT NULL,
  `Zeitpunkt` varchar(12) NOT NULL,
  `Preis` int(11) NOT NULL,
  `MaxAnzPersonen` int(11) NOT NULL,
  `PilotID` int(11) NOT NULL,
  `OrtID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `ballonfahrt`
--

INSERT INTO `ballonfahrt` (`FlugID`, `Zeitpunkt`, `Preis`, `MaxAnzPersonen`, `PilotID`, `OrtID`) VALUES
(1, '12.12.12', 122, 12, 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `buchung`
--

CREATE TABLE IF NOT EXISTS `buchung` (
  `BuchungsID` int(255) NOT NULL,
  `Datum` varchar(12) NOT NULL,
  `PassagierID` int(255) NOT NULL,
  `ReiseBuchungsID` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `buchung`
--

INSERT INTO `buchung` (`BuchungsID`, `Datum`, `PassagierID`, `ReiseBuchungsID`) VALUES
(1, '12.12.12', 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ort`
--

CREATE TABLE IF NOT EXISTS `ort` (
  `OrtID` int(11) NOT NULL,
  `PLZ` int(8) NOT NULL,
  `NameOrt` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `ort`
--

INSERT INTO `ort` (`OrtID`, `PLZ`, `NameOrt`) VALUES
(1, 6600, 'Reutte'),
(2, 6630, 'Lechtal'),
(3, 6640, 'Tannheim'),
(6, 6606, 'Ort1');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `passagier`
--

CREATE TABLE IF NOT EXISTS `passagier` (
  `PassagierID` int(255) NOT NULL,
  `Vorname` varchar(50) NOT NULL,
  `Nachname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `passagier`
--

INSERT INTO `passagier` (`PassagierID`, `Vorname`, `Nachname`, `email`) VALUES
(1, 'Sara', 'HIndelang', 'sari.@mai.lcom'),
(2, 'x', 'y', 'z');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `pilot`
--

CREATE TABLE IF NOT EXISTS `pilot` (
  `PilotID` int(11) NOT NULL,
  `Vorname` varchar(50) NOT NULL,
  `Nachname` varchar(50) NOT NULL,
  `FlugscheinNr` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `pilot`
--

INSERT INTO `pilot` (`PilotID`, `Vorname`, `Nachname`, `FlugscheinNr`) VALUES
(1, 'Hans', 'Joachim', 33),
(2, 'Marie', 'Curie', 76),
(3, 'Philipe', 'Nusser', 82);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `reise`
--

CREATE TABLE IF NOT EXISTS `reise` (
  `ReiseBuchungsID` int(11) NOT NULL,
  `FlugID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `reise`
--

INSERT INTO `reise` (`ReiseBuchungsID`, `FlugID`) VALUES
(1, 1);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `ballonfahrt`
--
ALTER TABLE `ballonfahrt`
  ADD PRIMARY KEY (`FlugID`),
  ADD UNIQUE KEY `PilotID` (`PilotID`),
  ADD UNIQUE KEY `OrtID` (`OrtID`),
  ADD UNIQUE KEY `FlugID` (`FlugID`),
  ADD UNIQUE KEY `PilotID_2` (`PilotID`),
  ADD UNIQUE KEY `OrtID_2` (`OrtID`),
  ADD UNIQUE KEY `PilotID_3` (`PilotID`,`OrtID`),
  ADD UNIQUE KEY `PilotID_4` (`PilotID`,`OrtID`),
  ADD UNIQUE KEY `MaxAnzPersonen` (`MaxAnzPersonen`),
  ADD UNIQUE KEY `MaxAnzPersonen_2` (`MaxAnzPersonen`,`PilotID`,`OrtID`);

--
-- Indizes für die Tabelle `buchung`
--
ALTER TABLE `buchung`
  ADD PRIMARY KEY (`BuchungsID`),
  ADD UNIQUE KEY `PassagierID` (`PassagierID`),
  ADD UNIQUE KEY `ReiseBuchungsID` (`ReiseBuchungsID`),
  ADD UNIQUE KEY `PassagierID_2` (`PassagierID`,`ReiseBuchungsID`),
  ADD UNIQUE KEY `PassagierID_3` (`PassagierID`);

--
-- Indizes für die Tabelle `ort`
--
ALTER TABLE `ort`
  ADD PRIMARY KEY (`OrtID`),
  ADD UNIQUE KEY `PLZ` (`PLZ`),
  ADD UNIQUE KEY `OrtID` (`OrtID`);

--
-- Indizes für die Tabelle `passagier`
--
ALTER TABLE `passagier`
  ADD PRIMARY KEY (`PassagierID`);

--
-- Indizes für die Tabelle `pilot`
--
ALTER TABLE `pilot`
  ADD PRIMARY KEY (`PilotID`),
  ADD UNIQUE KEY `FlugscheinNr` (`FlugscheinNr`),
  ADD UNIQUE KEY `PilotID` (`PilotID`);

--
-- Indizes für die Tabelle `reise`
--
ALTER TABLE `reise`
  ADD PRIMARY KEY (`ReiseBuchungsID`),
  ADD UNIQUE KEY `FlugID` (`FlugID`),
  ADD UNIQUE KEY `ReiseBuchungsID` (`ReiseBuchungsID`),
  ADD UNIQUE KEY `ReiseBuchungsID_2` (`ReiseBuchungsID`,`FlugID`),
  ADD UNIQUE KEY `ReiseBuchungsID_3` (`ReiseBuchungsID`,`FlugID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `buchung`
--
ALTER TABLE `buchung`
  MODIFY `BuchungsID` int(255) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `ballonfahrt`
--
ALTER TABLE `ballonfahrt`
  ADD CONSTRAINT `ballonfahrt_ibfk_1` FOREIGN KEY (`PilotID`) REFERENCES `pilot` (`PilotID`),
  ADD CONSTRAINT `ballonfahrt_ibfk_2` FOREIGN KEY (`OrtID`) REFERENCES `ort` (`OrtID`);

--
-- Constraints der Tabelle `buchung`
--
ALTER TABLE `buchung`
  ADD CONSTRAINT `buchung_ibfk_1` FOREIGN KEY (`PassagierID`) REFERENCES `passagier` (`PassagierID`),
  ADD CONSTRAINT `buchung_ibfk_2` FOREIGN KEY (`ReiseBuchungsID`) REFERENCES `reise` (`ReiseBuchungsID`);

--
-- Constraints der Tabelle `reise`
--
ALTER TABLE `reise`
  ADD CONSTRAINT `reise_ibfk_1` FOREIGN KEY (`FlugID`) REFERENCES `ballonfahrt` (`FlugID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
