import jdk.dynalink.Operation;

public class Job {
    private Integer exp1;
    private Integer exp2;
    private Operations operation;

    public Job(Integer exp1, Integer exp2, Operations op){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = op;
    }
}
