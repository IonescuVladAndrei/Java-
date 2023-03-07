# Java - Negative Image

 Proiectul are ca scop procesarea de imagini in Java folosind doar algoritmi si/ sau secvente de cod low-level. 

## Aplicatia contine:
- Multithreading
	 - Producer-Consumer
	 - Multithread Communication(notify)
- Comunicare prin Pipes
- Tratare exceptii
- Clase Abstracte & Metode Abstracte
- Multimodularitate (3 niveluri de mostenire)
- Constructori (inclusiv super)
- Varargs
- Bloc de intializare & Bloc static de initializare
- Operatii de lucru cu fisiere
- Operatii de intrare de la tastatura

## Etapele de executie ale aplicatiei sunt:
- citire informatii de identificare fisier sursa (fisiere sursa) si citire informatii de
- identificare fisier destinatie
- citire fisier sursa
- procesare imagine
- scriere fisier destinatie
- inregistrare timp de executie fiecare etapa
- afisare rezultate timp de procesare fiecare etapa

##  Descriere clase
- In ImgPath se citeste de la tastatura si se memoreaza path-urile pentru imaginea de intrare si imaginea de iesire.
- In Buffer se memoreaza intr-un tabel de tipul Color valorile RGB neprelucrate ale imaginii de intrare. De asemenea, metodele getter si setter
  (put) sunt sincronizate astfel incat prelucrarea imaginii sa inceapa numai dupa ce s-a consumat toata informatia.
- In ImageProducer, se citeste din imaginea de intrare pixel cu pixel si se memoreaza in Buffer. Se observa variabilele pentru pozitia (x, y) a fiecarui
  pixel, cat si timeValues, ce este folosit pentru memorarea perioadelor de timp.
- ImageConsumer, se extrage informatia din Buffer, se proceseaza (255-R,G,B) si se transmite la WriterResult prin intermediul pipe-urilor. Initial se 
  transmite 1/0 (daca am ajuns la ultimul segment sau nu pentru a incepe scrierea imaginii), pozitiile de inceput si sfarsit x,y ale blocului si in 2
  for-uri cele 3 valori R, G, B.
- PixelFound a fost implementat pentru a cauta o valoare specifica inainte si dupa procesare. Avem 3 metode care vor fi apelate in functie de rezultat.
- ExecutionTimeProducer, ExecutionTimeConsumer & ExecutionWriterResult au fost scrise pentru a memora timpii celor 12 thread-uri.
- WriterResult receptioneaza informatia (int-uri) de la ImageConsumer, o memoreaza in BufferWriteResult si la sfarsit o scrie in fisierul specificat. Tot
  aici se afiseaza timpii obtinuti la rulare.
- BufferWriterResult este similar cu Buffer, memorand numai datele primite prin intermediul pipe-urilor de la ImageConsumer la WriterResult.
- TestMain contine tratare exceptii, definire thread-uri, pipe-uri si obiecte.

## Descriere detaliata a modului de functionare


##  Bibliografie
- cursuri facultate
- https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
- https://www.geeksforgeeks.org/variable-arguments-varargs-in-java/
- https://courses.cs.washington.edu/courses/cse341/98au/java/jdk1.2beta4/docs/api/java/awt/Color.html
- https://en.wikipedia.org/wiki/BMP_file_format
