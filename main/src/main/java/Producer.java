import java.util.Random;

public class Producer implements Runnable{

    @Override
    public void run() {
        Random random = new Random();
        Operations[] allOps = Operations.values();
        while(true){
            try {
                int exp1 = random.nextInt(100) + 1;
                int exp2 = random.nextInt(100) + 1;

                int opIndex = random.nextInt(allOps.length);
                Operations operation = allOps[opIndex];

                Repository.getInstance().addJob(new Job(exp1, exp2, operation));
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }
        }
    }
}
