public class LocalWorker implements Runnable{

    @Override
    public void run() {
        while(true){
            try{
                Job curJob = Repository.getInstance().getJob();

                float res = 0;
                switch (curJob.operation){
                    case PLUS -> res = curJob.exp1 + curJob.exp2;
                    case MINUS -> res = curJob.exp1 - curJob.exp2;
                    case MULTIPLY -> res = curJob.exp1 * curJob.exp2;
                    case DIVIDE -> res = (curJob.exp2 == 0) ? 0 : (float) curJob.exp1 / curJob.exp2;
                    case EXPONENT -> res = (float) Math.pow(curJob.exp1, curJob.exp2);
                }
                Thread.sleep(10000);
                System.out.printf("Job Finished: res -> %f\n", res);
                Thread.sleep(5000);
            }catch (Exception e){

            }
        }
    }
}
