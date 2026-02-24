import java.util.LinkedList;
import java.util.Queue;

public class Outsourcer {

    private static final Outsourcer instance = new Outsourcer();

    private final Queue<Job> jobQueue = new LinkedList<>();
    private OutsourcerPublisher publisher;

    private Outsourcer() {}

    public static Outsourcer getInstance() {
        return instance;
    }

    public void setPublisher(OutsourcerPublisher pub) {
        this.publisher = pub;
    }

    public void addJob(Job job) {
        jobQueue.add(job);
        System.out.println("added job to queue. total: " + jobQueue.size());
    }

    public void handleRequest(String json) {
        String workerId = parseWorkerId(json);
        int capacity = parseCapacity(json);

        for (int i = 0; i < capacity; i++) {
            Job job = jobQueue.poll();
            if (job == null) break;

            publisher.assignJob(workerId, job);
        }
    }

    private String parseWorkerId(String json) {
        return json.split("\"workerId\":\"")[1].split("\"")[0];
    }

    private int parseCapacity(String json) {
        return Integer.parseInt(json.split("\"capacity\":")[1].split("}")[0]);
    }
}
