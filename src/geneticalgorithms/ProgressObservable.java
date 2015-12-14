package geneticalgorithms;

public interface ProgressObservable {
	public void addProgressObserver(ProgressObserver observer);
	public void removeProgressObserver(ProgressObserver observer);
	public void notifyProgressObservers(int progress);
}
