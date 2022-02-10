public class Supermarkt {
	
	private int anzahlRegale;
	private int maxKunden;
	private int anzahlKunden;
	private boolean[] regalBesetzt;
	private int anzahlWartendeAnKasse;
	private int[] warteschlangeKasse;
	
	public Supermarkt(int anzahlRegale, int maxKunden) {
		anzahlWartendeAnKasse = 0;
		warteschlangeKasse = new int[maxKunden];
		
		
		
		
	}
	
	public synchronized void supermarktBetreten(int id) throws InterruptedException {
		while (                                ) {
			
			
			System.out.println("Kunde " + id + " muss warten, da der Supermarkt voll ist.");
			
			
		}
		
		System.out.println("Kunde " + id + " hat den Supermarkt betreten.");
		
		
	}
	
	public synchronized void vorRegalStellen(int id, int regalnummer)
		throws InterruptedException {
		while (                                ) {
			
			
			System.out.println("Person " + id + " muss warten, da bereits jemand bei Regal " + regalnummer + " steht.");
			
			
		}
		
		
	}
	
	public synchronized void regalVerlassen(int id, int regalnummer) {
		
		
		System.out.println("Person " + id + " hat Regal " + regalnummer + " verlassen.");
		
		
	}
	
	public synchronized void anKasseAnstellen(int id) throws InterruptedException {
		
		
		System.out.println("Kunde " + id + " hat sich an der Kasse angestellt.");
		
		
		while (                                ) {
			
			
		}
		
		
	}
	
	public synchronized void supermarktVerlassen(int id) {
		
		
		
		
		System.out.println("Kunde " + id + " hat den Supermarkt verlassen.");
		
		
	}
	
	public int getAnzahlRegale() {
		return anzahlRegale;
	}
}
