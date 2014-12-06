package hr.nhex.graphic.timer.observer;

public interface TimerSubject {

	public void register(TimerObserver obj);
	public void unregister(TimerObserver obj);

	public void notifyObservers();

	public Object getUpdate(TimerObserver obj);

}
