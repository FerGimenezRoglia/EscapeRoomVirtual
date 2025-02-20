package observer;

public class SMSNotifier implements Observer {
    @Override
    public void update(String message) {
        System.out.println("📱 Notificación por SMS: " + message);
    }
}