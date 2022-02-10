import java.util.Random;

public class Mitarbeiter extends Thread {

    private Supermarkt supermarkt;
    private int id;
    private Random random;

    public Mitarbeiter(Supermarkt supermarkt, int id) {
        this.supermarkt = supermarkt;
        this.id = id;
        random = new Random();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(2000) + 500); // Mitarbeiter geht zu einem anderen Regal
                int regalnummer = random.nextInt(supermarkt.getAnzahlRegale());
                supermarkt.vorRegalStellen(id, regalnummer);
                System.out.println("Mitarbeiter räumt Regal " + regalnummer + " ein.");
                Thread.sleep(random.nextInt(2000) + 500); // Mitarbeiter räumt Regal ein
                supermarkt.regalVerlassen(id, regalnummer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
