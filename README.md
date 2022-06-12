<div id="top"></div>

[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <img src="https://github.com/DrLele08/Prelievo-Ordini/blob/main/Server/public/logo.png">
  <h3 align="center">Prelievo Ordini</h3>

  <p align="center">
    Applicazione sviluppata per la tesi triennale in Informatica
    <br />
    <a href="https://github.com/DrLele08/Prelievo-Ordini/releases"><strong>Scarica l'app</strong></a>
    <br />
    <br />
    <a href="https://github.com/DrLele08/Prelievo-Ordini/issues">Segnala Bug</a>
    ·
    <a href="https://github.com/DrLele08/Prelievo-Ordini/issues">Consiglia Funzionalità</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Indice</summary>
  <ol>
    <li>
      <a href="#Il-Progetto">Il Progetto</a>
      <ul>
        <li><a href="#Realizzato-Con">Realizzato Con</a></li>
      </ul>
    </li>
    <li>
      <a href="#Scaricare-Il-Progetto">Scaricare Il Progetto</a>
      <ul>
        <li><a href="#Requisiti">Requisiti</a></li>
        <li><a href="#Installazione">Installazione</a></li>
      </ul>
    </li>
    <li><a href="#Utilizzo">Utilizzo</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#Contatti">Contatti</a></li>
    <li><a href="#Ringraziamenti">Ringraziamenti</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## Il Progetto


L'applicazione (commissionata da un azienda) ha come obiettivo quello di semplificare e migliorare il mondo del B2B con l'aiuto dell'informatica, non solo l'azienda, ma anche clienti e dipendenti, il nostro obiettivo è quello di avvicinare il mondo del B2B a quello del B2C

Alcuni dei motivi:
* Negli ultimi anni la clientela diventa sempre più esigente, ma mentre il mondo del B2C tenta di rimanere al passo coi tempi...il mondo del B2B è indietro di almeno 10 anni.
* Il mercato del B2B diventà sempre più difficile e pericoloso.
* La maggior parte delle aziende B2B non possiede un Sito/E-Commerce (O ne possiede uno datato)

Prelievi Ordini è un app che cerca di avvicinarsi alla tecnologia moderna e di stare al passo con la tecnologia usata nel mondo del B2C
<p align="right">(<a href="#top">Torna in alto</a>)</p>



### Realizzato Con

Sono presenti tutti i linguaggi e tutte le librerie utilizzate per il progetto:

* [Node JS](https://nodejs.org/it/)
* [Kotlin](https://kotlinlang.org/)

Librerie utilizzate con <b>Node JS:</b>
* [WhatsApp Bot](https://github.com/open-wa/wa-automate-nodejs)
* [Crypt Password](https://www.npmjs.com/package/bcrypt)
* [Express](https://expressjs.com/)
* [MySQL](https://www.npmjs.com/package/mysql)
* [Random String](https://www.npmjs.com/package/randomstring)


Librerie utilizzate con <b>Kotlin:</b>
* [Toast](https://github.com/Spikeysanju/MotionToast)
* [Dialog](https://github.com/afollestad/material-dialogs)
* [Animazioni](https://github.com/airbnb/lottie-android)
* [Image Downloader](https://github.com/bumptech/glide)
* [Image Picker](https://github.com/Drjacky/ImagePicker)
* [Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition/android)
* [Barcode Scanner](https://developers.google.com/ml-kit/vision/barcode-scanning/android)
* [Stepper Input](https://github.com/kojofosu/Quantitizer)
* [JSON Util](https://github.com/google/gson)

<p align="right">(<a href="#top">Torna in alto</a>)</p>



<!-- GETTING STARTED -->
## Scaricare Il Progetto

Qui è presente una guida per scaricare il progetto e utilizzarlo localmente

### Requisiti

* npm
  ```sh
  npm install npm@latest -g
  ```
* Android Studio
* Kotlin
* MySQL Server

### Installazione

1. Scarica il progetto
   ```sh
   git clone https://github.com/DrLele08/Prelievo-Ordini
   ```
2. Configura il database
3. Installa le librerie
   ```sh
   npm install
   ```
4. Inserisci le variabili in `.env`
   ```
	DB_HOST=<IP Database>
	DB_USER=<User Database>
	DB_PWD=<Password Database>
	DB_NAME=<Nome Database>
	TOKEN_AUTH=<Token Auth>
	TOKEN_NOTIFICA=<OneSignal API>
	WA_AUTH=<WhatsApp Random Session Key>
	ORDINE_PAGINA=<Numero Ordini Pagina>
	PROD_PAGINA=<Numero Prodotti Pagina>
5. Avvia il server
   ```
   node index
   ```

<p align="right">(<a href="#top">Torna in alto</a>)</p>



<!-- USAGE EXAMPLES -->
## Utilizzo

Il server RESTFUL API e l'app sono ready to use, basterà seguire i procedimenti sopra elencati

Per informazioni sul funzionamento delle API controllare la [Documentazione](https://documenter.getpostman.com/view/5321024/Uz5Knad3)

<p align="right">(<a href="#top">Torna in alto</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Documentazione API
- [x] Suppurto Multi lingua
    - [x] Italiano
    - [x] Inglese
    - [ ] Cinese
- [ ] Gestione DueIn
- [ ] Gestione PreOrder
- [ ] Payment Integrazione
    - [ ] PayPal
    - [ ] Stripe
    - [ ] SEPA Transfer
- [ ] Applicazione iOS
- [ ] E-Commerce

Controlla [Trello](https://trello.com/b/ztV1FzTM/tesi-triennale) per le nuove funzionalità in arrivo e i bug individuati.

<p align="right">(<a href="#top">Torna in alto</a>)</p>




<!-- CONTACT -->
## Contatti

Sais Raffaele - [@DrLele08](https://twitter.com/drlele08) - saisraffaele08@gmail.com


<p align="right">(<a href="#top">Torna in alto</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Ringraziamenti

Qui di seguito sono elencati alcuni contribuenti (Passivi e Attivi) alla realizzazione del progetto

* [FreePick](https://it.freepik.com/)
* [FlatIcon](https://www.flaticon.com/)
* [PixelPerfect User](https://it.freepik.com/)
* [JuicyFish User](https://it.freepik.com/)
* [Srip User](https://it.freepik.com/)

<p align="right">(<a href="#top">Torna in alto</a>)</p>


[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/drlele08/
