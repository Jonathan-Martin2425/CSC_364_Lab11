import com.fasterxml.jackson.annotation.JsonProperty;

public class Job {
    public Integer exp1;
    public Integer exp2;
    public Operations operation;

    public Job(
            @JsonProperty("exp1") Integer exp1,
            @JsonProperty("exp2") Integer exp2,
            @JsonProperty("operation") Operations operation
    ){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }



}
