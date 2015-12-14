package geneticalgorithms;

public interface FinishedObservable {
	public void addFinishedObserver(FinishedObserver observer);
	public void removeFinishedObserver(FinishedObserver observer);
	public void notifyFinishedObservers();
}
