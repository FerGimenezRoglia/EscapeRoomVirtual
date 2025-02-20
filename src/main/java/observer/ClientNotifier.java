package observer;

import models.Client;

public class ClientNotifier extends Subject {

    public void notifySubscriptionChange(Client client) {
        String message = client.isSubscribed()
                ? "📩 Cliente " + client.getName() + " se ha SUSCRITO."
                : "📩 Cliente " + client.getName() + " se ha DESUSCRITO.";

        notifyObservers(message);
    }

}
