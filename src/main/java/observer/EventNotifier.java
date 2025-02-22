package observer;

public class EventNotifier extends Subject{

    public void notifyEvent(String eventType, String entityName) {
        String message = "📢 Evento importante: " + eventType + " → " + entityName;
        notifyObservers(message);
    }
}
