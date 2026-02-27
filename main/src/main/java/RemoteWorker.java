import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Optional;

public class RemoteWorker implements Runnable{
    private RemoteWorkerPublisher pub;
    private RemoteWorkerSubscriber sub;
    private final Integer TIMEOUT = 1000;
    public RemoteWorker(String broker) throws MqttException {
        this.pub = new RemoteWorkerPublisher(broker);
        this.sub = new RemoteWorkerSubscriber(broker, pub.getWorkerId());
    }

    @Override
    public void run() {
        while(true) {

            // send request then listen for TIMEOUT time until a job is found
            Optional<Job> curJob;
            while (true) {
                // send request
                pub.sendRequest();

                // listen for assignment
                curJob = sub.listen(TIMEOUT);
                if(curJob.isPresent()) break;
            }

            // solve job
            try {
                float res = Solver.solveJob(curJob.get());
                Thread.sleep(10000);
                System.out.printf("Remote Worker Job Finished: res -> %f\n", res);
                Thread.sleep(5000);
            }catch (Exception e){}
        }
    }
}
