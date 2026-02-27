import org.eclipse.paho.client.mqttv3.MqttException;

public class RemoteWorker implements Runnable{
    private RemoteWorkerPublisher pub;
    private RemoteWorkerSubscriber sub;
    public RemoteWorker(String broker) throws MqttException {
        this.pub = new RemoteWorkerPublisher(broker);
        this.sub = new RemoteWorkerSubscriber(broker, pub.getWorkerId());
    }

    @Override
    public void run() {
        while(true) {
            // send request
            pub.sendRequest();

            // listen for assignment
            Job curJob = sub.listen();

            // solve job
            try {
                float res = Solver.solveJob(curJob);
                Thread.sleep(10000);
                System.out.printf("Remote Worker Job Finished: res -> %f\n", res);
                Thread.sleep(5000);
            }catch (Exception e){}
        }
    }
}
