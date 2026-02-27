public class LocalWorker implements Runnable{

    @Override
    public void run() {
        while(true){
            try{
                Job curJob = Repository.getInstance().getJob();

                float res = Solver.solveJob(curJob);
                Thread.sleep(10000);
                System.out.printf("Local Worker Job Finished: res -> %f\n", res);
                Thread.sleep(5000);
            }catch (Exception e){

            }
        }
    }
}
