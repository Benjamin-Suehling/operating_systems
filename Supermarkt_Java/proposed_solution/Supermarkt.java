public class Supermarkt {

    private int anzahlRegale;
    private int maxKunden;
    private int anzahlKunden;
    private boolean[] regalBesetzt;
    private int anzahlWartendeAnKasse;
    private int[] warteschlangeKasse;


    public Supermarkt(int anzahlRegale, int maxKunden) {
        anzahlWartendeAnKasse = 0; // nicht unbedingt notwendig, gilt schon per Default
        warteschlangeKasse = new int[maxKunden];
        this.anzahlRegale = anzahlRegale;
        this.maxKunden = maxKunden;
        anzahlKunden = 0; // nicht unbedingt notwendig, gilt schon per Default
        regalBesetzt = new boolean[anzahlRegale]; // per Default haben alle Felder des Arrays den Wert false
    }


    public synchronized void supermarktBetreten(int id) throws InterruptedException {
        while (anzahlKunden >= maxKunden) { // der Supermarkt ist bereits voll, somit muss der Kunde warten.

            System.out.println("Kunde " + id + " muss warten, da der Supermarkt voll ist.");
            wait(); // wait() sollte nach dem println() stehen, da die Zeile sonst erst nach dem erneuten Aufwecken
            // gedruckt wird
        }
        // nun gilt anzahlKunden < maxKunden und der Kunde darf den Supermarkt betreten
        System.out.println("Kunde " + id + " hat den Supermarkt betreten.");
        anzahlKunden++; // äquivalent dazu: anzahlKunden = anzahlKunden + 1; oder auch anzahlKunden += 1;
    }


    public synchronized void vorRegalStellen(int id, int regalnummer) {
        // man kann "throws InterruptedException" im Methodenkopf auch weglassen,
        // muss dann allerdings das wait() in einen try-catch-Block setzen

        while (regalBesetzt[regalnummer]) { // das entsprechende Regal ist besetzt, also muss die Person warten

            System.out.println("Person " + id + " muss warten, da bereits jemand bei Regal " + regalnummer + " steht.");
            try {
                wait();
            } catch (InterruptedException ignored) {

            }

        }

        // hier gilt nun: regalBesetzt[regalnummer] == false, also darf die Person sich vor das Regal stellen:
        regalBesetzt[regalnummer] = true;
    }


    public synchronized void regalVerlassen(int id, int regalnummer) {
        // das Regal wieder verlassen:
        regalBesetzt[regalnummer] = false;
        System.out.println("Person " + id + " hat Regal " + regalnummer + " verlassen.");

        // da wir ein Regal frei gemacht haben, kann vielleicht ein schlafender Thread wieder etwas tun,
        // also brauchen wir notifyAll():
        notifyAll();
    }


    public synchronized void anKasseAnstellen(int id) throws InterruptedException {
        warteschlangeKasse[anzahlWartendeAnKasse] = id; // die Person muss sich in die Warteschlange an hinterster
        // Position einreihen
        anzahlWartendeAnKasse++;
        System.out.println("Kunde " + id + " hat sich an der Kasse angestellt.");

        while (warteschlangeKasse[0] != id) { // der Kunde ist nicht ganz vorne in der Warteschlange, muss also warten
            wait();
        }

        // hier gilt nun: warteschlangeKasse[0] == id
        // somit ist diese Person ganz vorne und wird als nächstes abkassiert
    }


    public synchronized void supermarktVerlassen(int id) {
        // die Anzahl der Personen an der Kasse und im Supermarkt verringert sich nun um 1
        anzahlKunden--;
        anzahlWartendeAnKasse--;

        // verschiebe alle in der Schlange wartenden Kunden eine Position nach vorne:
        for (int i = 0; i < anzahlWartendeAnKasse; i++) {
            warteschlangeKasse[i] = warteschlangeKasse[i + 1];
        }
        // statt manuell eine for-Schleife zu schreiben, wäre auch folgende Zeile möglich:
        // System.arraycopy(warteschlangeKasse, 1, warteschlangeKasse, 0, anzahlWartendeAnKasse);

        System.out.println("Kunde " + id + " hat den Supermarkt verlassen.");

        // da wir die Kasse und den Supermarkt verlassen haben, kann vielleicht ein schlafender Thread wieder etwas tun,
        // also brauchen wir notifyAll():
        notifyAll();
    }


    public int getAnzahlRegale() {
        return anzahlRegale;
    }
}
