public class Simulation {
	
	private final static int maxKunden = 8;
	private final static int anzahlRegale = 10;

	public static void main(String[] args) {
		Supermarkt supermarkt = new Supermarkt(anzahlRegale, maxKunden);
		
		Mitarbeiter mitarbeiter = new Mitarbeiter(supermarkt, -1);
		mitarbeiter.start();
		
		for (int i = 0; i < 10; i++) {
			Kunde kunde = new Kunde(supermarkt, i);
			kunde.start();
		}
	}

}
