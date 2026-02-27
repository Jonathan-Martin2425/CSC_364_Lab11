public class Main {

    public static void main(String[] args) throws Exception {

        String broker = "tcp://test.mosquitto.org:1883";

        OutsourcerPublisher pub = new OutsourcerPublisher(broker);
        Outsourcer.getInstance().setPublisher(pub);

        Thread outsourcerSub = new Thread(new OutsourcerSubscriber(broker));
        outsourcerSub.start();

        System.out.println("Outsourcer running");

        for (int i = 0; i < 5; i++) {
            Thread prod = new Thread(new Producer());
            prod.start();
        }

        for (int i = 0; i < 1; i++) {
            Thread prod = new Thread(new RemoteWorker(broker));
            prod.start();
        }
        //for (int i = 0; i < 1; i++) {
        //    Thread worker = new Thread(new LocalWorker());
        //    worker.start();
        //}

        System.out.println("Local system running: Producers + LocalWorker + Outsourcer");
    }
}
