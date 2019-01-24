package gui;

import java.util.List;

public class GuiObservable {

	private List<Observer> observers;
	
	private void notifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
}
