import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class OutsourcerPublisher {

    private final MqttClient client;

    public OutsourcerPublisher(String broker) throws Exception {
        String clientId = MqttClient.generateClientId();
        client = new MqttClient(broker, clientId);
        client.connect();
        System.out.println("outsourcer publisher connected");
    }

    public void assignJob(String workerId, Job job) {
        try {
            String topic = "awesomecsc364/work/assign/" + workerId;

            String json = String.format(
                    "{\"exp1\":\"%d\",\"exp2\":\"%d\",\"operation\":\"%s\"}",
                    job.exp1, job.exp2, job.operation
            );

            MqttMessage msg = new MqttMessage(json.getBytes());
            msg.setQos(2);

            client.publish(topic, msg);
            System.out.println("sent job to " + workerId);

        } catch (Exception e) {}
    }
}
