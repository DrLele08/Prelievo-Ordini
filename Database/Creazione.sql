DROP DATABASE IF EXISTS PrelieviOrdini;
CREATE DATABASE PrelieviOrdini;

USE PrelieviOrdini;

CREATE TABLE TipoUtente(
	idTipo INT PRIMARY KEY AUTO_INCREMENT,
	Tipo VARCHAR(48) NOT NULL
);

CREATE TABLE Utente(
	idUtente INT PRIMARY KEY AUTO_INCREMENT,
	ksTipo INT NOT NULL,
	Nome VARCHAR(90) NOT NULL,
	Email VARCHAR(48) NOT NULL UNIQUE,
	Password VARCHAR(255) NOT NULL,
	Identificativo VARCHAR(24) NOT NULL,
	TokenAuth VARCHAR(45) NOT NULL,
	Cellulare VARCHAR(28) NOT NULL UNIQUE,
	FOREIGN KEY (ksTipo) REFERENCES TipoUtente(idTipo)
);

CREATE TABLE StatoOrdine(
	idStato INT PRIMARY KEY AUTO_INCREMENT,
	Stato VARCHAR(48) NOT NULL
);

CREATE TABLE Categoria(
	idCategoria INT PRIMARY KEY AUTO_INCREMENT,
	Nome VARCHAR(48) NOT NULL
);

CREATE TABLE CodiceIva(
	idCodiceIva INT PRIMARY KEY AUTO_INCREMENT,
	Iva INT NOT NULL
);


CREATE TABLE TipoEvento(
	idTipoEvento INT PRIMARY KEY AUTO_INCREMENT,
	Evento VARCHAR(48) NOT NULL
);

CREATE TABLE Articolo(
	idArticolo INT PRIMARY KEY AUTO_INCREMENT,
	ksIva INT NOT NULL,
	ksReparto INT NOT NULL,
	Descrizione VARCHAR(100) NOT NULL,
	QntDisponibile INT NOT NULL,
	PrezzoIvato FLOAT NOT NULL,
	PrezzoConsigliato FLOAT NOT NULL,
	PrezzoPreOrder FLOAT,
	Lunghezza FLOAT NOT NULL,
	Altezza FLOAT NOT NULL,
	Profondita FLOAT NOT NULL,
	Volume FLOAT NOT NULL,
	Peso INT NOT NULL,
	Tag VARCHAR(300),
	FOREIGN KEY (ksIva) REFERENCES CodiceIva(idCodiceIva),
	FOREIGN KEY (ksReparto) REFERENCES Categoria(idCategoria)
);

CREATE TABLE Reparto(
	idReparto INT PRIMARY KEY AUTO_INCREMENT,
	Nome VARCHAR(128) NOT NULL
);

CREATE TABLE PostoScaffale(
	idPosto INT PRIMARY KEY AUTO_INCREMENT,
	ksReparto INT NOT NULL,
	ksArticolo INT NOT NULL,
	Posto INT NOT NULL,
	PesoMax INT NOT NULL,
	Altezza FLOAT NOT NULL,
	Lunghezza FLOAT NOT NULL,
	Profondita FLOAT NOT NULL,
	Volume FLOAT NOT NULL,
	Qnt INT NOT NULL,
	FOREIGN KEY (ksReparto) REFERENCES Reparto(idReparto),
	FOREIGN KEY (ksArticolo) REFERENCES Articolo(idArticolo)
);

CREATE TABLE EAN(
	EAN VARCHAR(64) PRIMARY KEY,
	ksArticolo INT NOT NULL,
	DataInserimento DATETIME NOT NULL DEFAULT NOW(),
	QntConfezione INT NOT NULL DEFAULT 1,
	FOREIGN KEY (ksArticolo) REFERENCES Articolo(idArticolo)
);

CREATE TABLE Ordine(
	idOrdine INT PRIMARY KEY AUTO_INCREMENT,
	ksUtente INT NOT NULL,
	ksStato INT NOT NULL,
	Data DATETIME NOT NULL DEFAULT NOW(),
	NoteExtra VARCHAR(255),
	FOREIGN KEY (ksStato) REFERENCES StatoOrdine(idStato),
	FOREIGN KEY (ksUtente) REFERENCES Utente(idUtente)
);

CREATE TABLE OperatoriLettura(
	idOperatoriLettura INT PRIMARY KEY AUTO_INCREMENT,
	ksOrdine INT NOT NULL,
	ksUtente INT NOT NULL,
	DataInizio DATETIME NOT NULL,
	DataFine DATETIME,
	Note VARCHAR(124),
	FOREIGN KEY (ksOrdine) REFERENCES Ordine(idOrdine),
	FOREIGN KEY (ksUtente) REFERENCES Utente(idUtente)
);

CREATE TABLE RigaOrdine(
	idRigaOrdine INT PRIMARY KEY AUTO_INCREMENT,
	ksOrdine INT NOT NULL,
	ksArticolo INT NOT NULL,
	Prezzo FLOAT NOT NULL,
	Qnt INT NOT NULL,
	QntEvasa INT NOT NULL DEFAULT -1,
	FOREIGN KEY (ksOrdine) REFERENCES Ordine(idOrdine),
	FOREIGN KEY (ksArticolo) REFERENCES Articolo(idArticolo)
);

CREATE TABLE EventoLettura(
	idEvento INT PRIMARY KEY AUTO_INCREMENT,
	ksEvento INT NOT NULL,
	ksArticolo INT,
	ksOperatore INT NOT NULL,
	Data DATETIME NOT NULL DEFAULT NOW(),
	FOREIGN KEY (ksEvento) REFERENCES TipoEvento(idTipoEvento),
	FOREIGN KEY (ksArticolo) REFERENCES Articolo(idArticolo),
	FOREIGN KEY (ksOperatore) REFERENCES OperatoriLettura(idOperatoriLettura)
);

CREATE TABLE Carrello(
	idCarrello INT PRIMARY KEY AUTO_INCREMENT,
	ksUtente INT NOT NULL UNIQUE,
	DataCreazione DATETIME NOT NULL,
	DataModifica DATETIME NOT NULL,
	FOREIGN KEY (ksUtente) REFERENCES Utente(idUtente)
);

CREATE TABLE ProdottoCarrello(
	idProdottoCarrello INT PRIMARY KEY AUTO_INCREMENT,
	ksCarrello INT NOT NULL,
	ksArticolo INT NOT NULL,
	Qnt INT NOT NULL,
	DataInserimento DATETIME NOT NULL,
	FOREIGN KEY (ksCarrello) REFERENCES Carrello(idCarrello),
	FOREIGN KEY (ksArticolo) REFERENCES Articolo(idArticolo)
);