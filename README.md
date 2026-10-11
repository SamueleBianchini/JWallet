# JWallet
Project for OOP by Samuele Bianchini, Enrico Potenza, Jacopo Girolamini, Davide Nanni

Email componenti:

enrico.potenza@studio.unibo.it
samuele.bianchini4@studio.unibo.it
jacopo.girolamini@studio.unibo.it
davide.nanni16@studio.unibo.it
L'obbiettivo del progetto è la realizzazione di un applicazione in linguaggio Java chiamata JWallet che offre al singolo utente la possibilità di gestire un conto bancario, con carte di credito, debito e prepagate annesse, affiancato alla possibilità di possedere un portafoglio di investimenti. In particolare, sul conto è possibile aprire/chiudere investimenti, eseguire transazioni come depositi, bonifici oppure operazioni con le carte. Da esse si compongono una carta di credito con un massimale spendibile e un metodo per scaricare il prestito dal conto in banca, una carta prepagata a cui aumentare il saldo con bonifico da conto bancario, e una carda di debito che dovrà riflettere eventuali operazioni direttamente sul conto. Come accennato, l'utente ha a disposizione un insieme di asset su cui è possibile eseguire investimenti, i quali verranno conseguentemente memorizzati nel portafoglio titoli. 

Funzionalità minime ritenute obbligatorie (realizzabili in circa il 60/70% del tempo a disposizione):

Interfaccia iniziale per la gestione del conto bancario, per la visualizzazione e gestione carte, per la ricerca di informazioni sugli asset finanziari, e visualizzazione/gestione portafoglio titoli. 
Implementazione di metodi per operazioni come bonifici o depositi del conto bancario. Visualizzazione delle informazioni primarie del conto quali ammontare, andamento del capitale, elenco transazioni. 
Implementazione di tutti i tipi di carte, ognuna con le sue caratteristiche specifiche dettate dal tipo di carta e descritte precedentemente. Per ciascuna carta è dedicata una interfaccia per la sua gestione e visualizzazione di operazioni.
Visualizzazione di informazioni generali riguardanti prezzo e andamento di prezzo di una serie limitata di asset finanziari messi a disposizione per l'utente, il quale avrà la possibilità di speculare sull'andamento di prezzo tramite investimenti.
Funzionalità opzionali (a completamento del 100% del tempo a disposizione):

Utilizzo di API per l'estrapolazione di dati reali per la rappresentazione degli asset e delle loro variazioni di prezzo. Il ritorno di investimento, alla chiusura, si baserà sulla differenza tra prezzo reale di entrata e di uscita. 
Implementazione della possibilità di creare, gestire ed eliminare più carte prepagate.
Differenziazione dei vari tipi di asset (azioni, obbligazioni e asset puri) e delle operazioni da loro derivanti come ricezione interessi o utili. 
Implementazione di un'applicazione separata per la simulazione di movimenti bancari dall'esterno verso il conto.
Challenge principali:

Gestione di diversi tipi di carte e delle loro specifiche operazioni. 
Gestione del portafoglio investimenti. 
Gestione delle interazioni tra conto e carte. 
Implementazione delle varie interfacce grafiche.
Suddivisione di massima del lavoro (con parti significative del model ciascuno):

Enrico Potenza -> Interfaccia asset; Gestione degli asset e acquisto e vendita investimenti. Parte facoltativa assegnata -> API
Samuele Bianchini -> Interfaccia conto; Gestione del conto bancario e delle operazioni relative al conto. Parte facoltativa assegnata -> Divisione tipi asset
Jacopo Girolamini -> Interfaccia portafoglio; Memorizzazione dei dati utente e della sessione (per memorizzare i dati anche dopo la chiusura dell'applicazione); Gestione della carta di credito e delle operazioni relative. Parte facoltativa assegnata -> Operazioni dall'esterno
Davide Nanni -> Interfaccia carte; Gestione della carta di debito e prepagata. Parte facoltativa assegnata -> Creazione e eliminazione carte prepagate e forse anche carte usa e getta
