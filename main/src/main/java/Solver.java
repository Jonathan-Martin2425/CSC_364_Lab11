public class Solver {
    
    public static float solveJob(Job job){
        float res = 0;
        switch (job.operation){
            case PLUS -> res = job.exp1 + job.exp2;
            case MINUS -> res = job.exp1 - job.exp2;
            case MULTIPLY -> res = job.exp1 * job.exp2;
            case DIVIDE -> res = (job.exp2 == 0) ? 0 : (float) job.exp1 / job.exp2;
            case EXPONENT -> res = (float) Math.pow(job.exp1, job.exp2);
        }
        return res;
    }
}
