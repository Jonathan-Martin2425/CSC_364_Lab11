import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.Queue;

public class Outsourcer {

    private static final Outsourcer instance = new Outsourcer();

    private final Queue<Job> jobQueue = new LinkedList<>();
    private OutsourcerPublisher publisher;
    private ObjectMapper mapper = new ObjectMapper();

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
        try {
            String workerId = parseWorkerId(json);
            int capacity = parseCapacity(json);

            for (int i = 0; i <= capacity; i++) {
                Job job = Repository.getInstance().getJob();
                if (job == null) break;

                publisher.assignJob(workerId, job);
            }
        } catch (Exception e) {
            System.out.println("outsourcer parsing errored");
        }
    }

    private String parseWorkerId(String json) throws JsonProcessingException {
        RemoteWorkerPayload payload = mapper.readValue(json, RemoteWorkerPayload.class);
        return payload.workerId;
    }

    private int parseCapacity(String json) throws JsonProcessingException {
        RemoteWorkerPayload payload = mapper.readValue(json, RemoteWorkerPayload.class);
        return payload.capacity;
    }
}
