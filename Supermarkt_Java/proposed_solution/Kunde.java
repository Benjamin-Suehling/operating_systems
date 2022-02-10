import java.util.Random;

public class Kunde extends Thread {

    private Supermarkt supermarkt;
    private int id;
    private Random random;

    public Kunde(Supermarkt supermarkt, int id) {
        this.supermarkt = supermarkt;
        this.id = id;
        random = new Random();
    }

    public void run() {
        try {
            supermarkt.supermarktBetreten(id);
            for (int i = 0; i < 5; i++) { // Kunde kauft insgesamt 5 Artikel ein
                Thread.sleep(random.nextInt(500) + 100); // Kunde überlegt, wo der nächste Artikel ist
                int regalnummer = random.nextInt(supermarkt.getAnzahlRegale());
                supermarkt.vorRegalStellen(id, regalnummer);
                System.out.println("Kunde " + id + " nimmt einen Artikel aus Regal " + regalnummer + ".");
                Thread.sleep(random.nextInt(250) + 50); // Kunde nimmt benötigten Artikel aus dem Regal
                supermarkt.regalVerlassen(id, regalnummer);
            }
            supermarkt.anKasseAnstellen(id);
            Thread.sleep(random.nextInt(250) + 750); // Kunde wird abkassiert
            supermarkt.supermarktVerlassen(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
