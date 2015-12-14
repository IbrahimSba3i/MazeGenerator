package mazegame.generator;


public class DistancePositionEntry {
	public int x, y, distance;
	
	public DistancePositionEntry() {
		this.x = 0;
		this.y = 0;
		this.distance = 0;
	}
	
	public DistancePositionEntry(int x, int y) {
		this.x = x;
		this.y = y;
		this.distance = 0;
	}
	
	public DistancePositionEntry(int x, int y, int distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
	}	
}
