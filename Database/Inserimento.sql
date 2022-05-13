-- Inserimento categorie
INSERT INTO Categoria(Nome) VALUES ("Alimentari");
INSERT INTO Categoria(Nome) VALUES ("Carta & Monouso");
INSERT INTO Categoria(Nome) VALUES ("Casalinghi");
INSERT INTO Categoria(Nome) VALUES ("Cosmetica");
INSERT INTO Categoria(Nome) VALUES ("Detersivi");
INSERT INTO Categoria(Nome) VALUES ("Eventi");
INSERT INTO Categoria(Nome) VALUES ("Giochi");
INSERT INTO Categoria(Nome) VALUES ("Scuola & Ufficio");

-- Inserimento reparti
INSERT INTO Reparto(Nome) VALUES ("Corsia 1");
INSERT INTO Reparto(Nome) VALUES ("Corsia 2");
INSERT INTO Reparto(Nome) VALUES ("Corsia 3");
INSERT INTO Reparto(Nome) VALUES ("Corsia 4");
INSERT INTO Reparto(Nome) VALUES ("Corsia 5");
INSERT INTO Reparto(Nome) VALUES ("Corsia 6");
INSERT INTO Reparto(Nome) VALUES ("Corsia 7");
INSERT INTO Reparto(Nome) VALUES ("Corsia 8");

-- Inserimento codici iva
INSERT INTO CodiceIva(Iva) VALUES(0);
INSERT INTO CodiceIva(Iva) VALUES(10);
INSERT INTO CodiceIva(Iva) VALUES(22);

-- Inserimento tipo utenti
INSERT INTO TipoUtente(Tipo) VALUES("Admin");
INSERT INTO TipoUtente(Tipo) VALUES("Dipendente");
INSERT INTO TipoUtente(Tipo) VALUES("Utente");
INSERT INTO TipoUtente(Tipo) VALUES("Bannato");


-- Inserimento utenti (Password == 'Password1!')
INSERT INTO Utente(ksTipo,Nome,Email,Password,TokenAuth,Identificativo,Cellulare) VALUES(1,"Raffaele Sais","saisraffaele08@gmail.com","$2b$10$7XjpKMj.ARY5kQfvbNDC4un4aOxrgFwJgDKxFomFHRi68v94TLpWO","50LFYobUHXM3GAkpDcFLQ3WbPJukVNYPEKWKHu2KZ3l8a","SSARFL00L30F839Z","3292251164");
INSERT INTO Utente(ksTipo,Nome,Email,Password,TokenAuth,Identificativo,Cellulare) VALUES(2,"Tim Cook","iloveapple@gmail.com","$2b$10$7XjpKMj.ARY5kQfvbNDC4un4aOxrgFwJgDKxFomFHRi68v94TLpWO","50LFYobUHXM3GAkpDcFLQ3WbPJukVNYPEKWKHu2KZ3l8a","383838482842","3383932209");
INSERT INTO Utente(ksTipo,Nome,Email,Password,TokenAuth,Identificativo,Cellulare) VALUES(3,"Vincenzo Coppola","vincenzocoppola04@gmail.com","$2b$10$7XjpKMj.ARY5kQfvbNDC4un4aOxrgFwJgDKxFomFHRi68v94TLpWO","50LFYobUHXM3GAkpDcFLQ3WbPJukVNYPEKWKHu2KZ3l8a","383838482844","3341334261");
INSERT INTO Utente(ksTipo,Nome,Email,Password,TokenAuth,Identificativo,Cellulare) VALUES(4,"Mario Peluso","mariopeluso00@gmail.com","$2b$10$7XjpKMj.ARY5kQfvbNDC4un4aOxrgFwJgDKxFomFHRi68v94TLpWO","50LFYobUHXM3GAkpDcFLQ3WbPJukVNYPEKWKHu2KZ3l8a","383838482849","3391671194");

-- Inserimento articoli
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(1,3,"Mascherine Chirurgiche 100 Pezzi",2,9.99,14.99,25,22,12,Altezza*Profondita*Lunghezza,400);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(2,1,"Milka Bar Tuc",4,0.60,0.99,16,8,1.1,Altezza*Profondita*Lunghezza,87);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(2,1,"KitKat White Wafer",4,0.22,0.49,17,9,3,Altezza*Profondita*Lunghezza,41);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso,Tag) VALUES(2,1,"Kinder Happy Hippo",4,1.39,2.49,17,10,3,Altezza*Profondita*Lunghezza,136,"Ippopotamo:Raffaele");
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(2,1,"Lindt Bar Latte",4,0.99,1.99,8,16,1.1,Altezza*Profondita*Lunghezza,100);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,PrezzoPreOrder,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,5,"Ace Detersivo Lavatrice 25 Lavaggi Colorati",3,1.99,2.99,1.89,20,10,10,Altezza*Profondita*Lunghezza,1400);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,PrezzoPreOrder,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,5,"Ace Detersivo Lavatrice 25 Lavaggi Classico",3,1.99,2.99,1.89,20,10,10,Altezza*Profondita*Lunghezza,1400);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,PrezzoPreOrder,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,5,"Ace Detersivo Lavatrice 25 Lavaggi Energy",3,1.99,2.99,1.89,20,10,10,Altezza*Profondita*Lunghezza,1400);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,5,"Svelto Detersivo 1L Limone",3,0.99,1.49,9.5,28.5,5.7,Altezza*Profondita*Lunghezza,1000);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,5,"Svelto Detersivo 1L Antibatterico",3,0.99,1.49,9.5,28.5,5.7,Altezza*Profondita*Lunghezza,1000);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,5,"Svelto Detersivo 1L Aceto",3,0.99,1.49,9.5,28.5,5.7,Altezza*Profondita*Lunghezza,1000);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,6,"Piscina Rotonda 3 Anelli 86x25",3,6.99,11.99,19.3,18.8,6.6,Altezza*Profondita*Lunghezza,610);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,4,"Gillette Fusion 5 Power Con 2 Lamette",3,6.99,11.99,10.4,20,4.5,Altezza*Profondita*Lunghezza,60);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,6,"Piscina Gonfiabile 180x135x60",3,34.99,49.99,42.2,32.2,13.7,Altezza*Profondita*Lunghezza,3700);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,4,"Gillette Blue 3 Usa e Getta (4+2)",3,1.99,2.49,8,21,4,Altezza*Profondita*Lunghezza,10);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,4,"Davidoff Profumo Cool Water 100ML",3,24.99,44.99,6,15,4,Altezza*Profondita*Lunghezza,190);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,4,"Katy Perry Profumo Killer Queen 100ML",3,17.99,24.99,12.7,22.9,17.8,Altezza*Profondita*Lunghezza,300);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,7,"Gioco E Se Fossi Mimo?",3,9.99,14.99,27,27,5,Altezza*Profondita*Lunghezza,500);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,PrezzoPreOrder,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,7,"Carte UNO",3,2.99,5.99,2.59,9.2,14.6,2,Altezza*Profondita*Lunghezza,115);
INSERT INTO Articolo(ksIva,ksReparto,Descrizione,QntDisponibile,PrezzoIvato,PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso) VALUES(3,7,"Gioco Non Svegliare Papà",3,17.99,29.99,27,27,8,Altezza*Profondita*Lunghezza,500);

-- Inserimento EAN
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840001",1,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840002",2,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840003",3,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840004",4,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840005",5,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840006",6,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840007",7,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840008",8,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840009",9,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840010",10,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840011",11,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840012",12,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840013",13,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840014",14,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840015",15,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840016",16,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840017",17,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840018",18,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840019",19,1);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647840020",20,1);

INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841101",1,4);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841102",2,24);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841103",3,24);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841104",4,10);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841105",5,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841106",6,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841107",7,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841108",8,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841109",9,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841110",10,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841111",11,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841112",12,4);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841113",13,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841114",14,4);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841115",15,12);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841116",16,6);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841117",17,6);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841118",18,4);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841119",19,10);
INSERT INTO EAN(EAN,ksArticolo,QntConfezione) VALUES("8058647841120",20,4);


-- Inserimento Stato Ordine
INSERT INTO StatoOrdine(Stato) VALUES("In attesa di lettura");
INSERT INTO StatoOrdine(Stato) VALUES("In corso");
INSERT INTO StatoOrdine(Stato) VALUES("Completato");
INSERT INTO StatoOrdine(Stato) VALUES("Annullato");
INSERT INTO StatoOrdine(Stato) VALUES("Bloccato da un dispositivo");

-- Inserimento Tipo Evento
INSERT INTO TipoEvento(Evento) VALUES("Lattura confermata");
INSERT INTO TipoEvento(Evento) VALUES("Lattura errata");
INSERT INTO TipoEvento(Evento) VALUES("Chiusura motivata");
INSERT INTO TipoEvento(Evento) VALUES("Dispositivo scollegato");
INSERT INTO TipoEvento(Evento) VALUES("Dispositivo collegato");

-- Inserimento Ordini
INSERT INTO Ordine(ksUtente,ksStato,Data) VALUES(3,3,"2022-04-10 19:23:21");
INSERT INTO Ordine(ksUtente,ksStato,Data) VALUES(4,4,"2022-04-12 11:24:45");
INSERT INTO Ordine(ksUtente,ksStato,Data) VALUES(3,2,"2022-04-18 12:43:32");
INSERT INTO Ordine(ksUtente,ksStato,Data) VALUES(3,1,"2022-04-24 16:24:55");

-- Inserimento Articoli Ordine
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(1,1,9.99,4,4);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(1,2,0.6,12,10);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(1,15,1.99,6,6);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(1,4,1.39,30,20);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(1,6,1.99,1,1);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(2,2,0.6,120);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(3,17,17.99,5,5);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(3,20,17.99,1,-1);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(3,9,0.99,12,-1);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(3,12,6.99,2,2);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt,QntEvasa) VALUES(3,1,9.99,4,-1);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(4,1,9.99,10);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(4,7,1.99,6);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(4,3,0.22,6);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(4,9,0.99,9);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(4,10,0.99,3);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(4,11,0.99,24);
INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES(4,17,17.99,12);

-- Inserimento Operatori Lettura
INSERT INTO OperatoriLettura(ksOrdine,ksUtente,DataInizio,DataFine,Note) VALUES(1,1,"2022-04-11 10:03:22","2022-04-11 13:24:21","Pausa pranzo");
INSERT INTO OperatoriLettura(ksOrdine,ksUtente,DataInizio,DataFine) VALUES(1,2,"2022-04-11 13:10:19","2022-04-11 14:33:08");
INSERT INTO OperatoriLettura(ksOrdine,ksUtente,DataInizio) VALUES(3,1,"2022-04-18 15:01:52");

-- Inserimento Evento Lettura
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(1,1,1,"2022-04-11 10:05:22");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(1,2,1,"2022-04-11 10:05:56");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(2,2,1,"2022-04-11 10:06:06");
INSERT INTO EventoLettura(ksEvento,ksOperatore,Data) VALUES(4,1,"2022-04-11 10:06:10");
INSERT INTO EventoLettura(ksEvento,ksOperatore,Data) VALUES(5,1,"2022-04-11 10:06:44");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(1,3,1,"2022-04-11 10:07:12");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(3,3,1,"2022-04-11 13:24:21");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(1,4,2,"2022-04-11 13:12:10");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(1,5,2,"2022-04-11 13:15:11");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(1,7,3,"2022-04-18 15:02:44");
INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Data) VALUES(1,10,3,"2022-04-18 15:03:37");

-- Inserimento Posto Scaffale
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(1,1,1,300000,60,60,100,Altezza*Profondita*Lunghezza,2);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(1,2,2,300000,60,60,100,Altezza*Profondita*Lunghezza,4);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(2,3,1,300000,60,60,100,Altezza*Profondita*Lunghezza,4);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(2,4,2,300000,60,60,100,Altezza*Profondita*Lunghezza,4);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(3,5,1,300,30,30,30,Altezza*Profondita*Lunghezza,4);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(3,6,2,300,30,30,30,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(4,7,1,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(4,8,2,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(5,9,1,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(5,10,2,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(6,11,1,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(6,12,2,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(7,13,1,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(7,14,2,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(8,15,1,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(8,16,2,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(7,17,1,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(7,18,2,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(8,19,1,300000,60,60,100,Altezza*Profondita*Lunghezza,3);
INSERT INTO PostoScaffale(ksReparto,ksArticolo,Posto,PesoMax,Altezza,Lunghezza,Profondita,Volume,Qnt) VALUES(8,20,2,300000,60,60,100,Altezza*Profondita*Lunghezza,3); 