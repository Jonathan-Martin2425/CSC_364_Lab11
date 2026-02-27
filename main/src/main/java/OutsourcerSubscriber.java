import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

public class OutsourcerSubscriber implements Runnable, MqttCallback {

    private final String broker;
    private final String topic = "awesomecsc364/work/request";

    public OutsourcerSubscriber(String brokerUrl) {
        this.broker = brokerUrl;
    }

    @Override
    public void run() {
        try {
            String clientId = MqttClient.generateClientId();
            MqttClient client = new MqttClient(broker, clientId);
            client.setCallback(this);
            client.connect();

            System.out.println("outsourcer subscribed to: " + topic);
            client.subscribe(topic, 2);

            while (true) {
                Thread.sleep(10);
            }

        } catch (Exception e) {}
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        System.out.println("worker request: " + payload);

        Outsourcer.getInstance().handleRequest(payload);
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("outsourcer subscriber lost connection");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {}
}
