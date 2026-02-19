import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Repository {
    private static final Repository instance = new Repository(5);
    private final Semaphore space;
    private final Semaphore avalibleJobs;
    private final ReentrantLock lock;

    private final Stack<Job> tasks;

    private Repository(Integer size){
        this.space = new Semaphore(size);
        this.avalibleJobs = new Semaphore(size);
        avalibleJobs.drainPermits();
        this.tasks = new Stack<>();
        this.lock = new ReentrantLock();
    }

    public static Repository getInstance() {
        return instance;
    }

    public void addJob(Job job){
        boolean addedJob = false;
        try{
            space.acquire();
            lock.lock();
            tasks.push(job);
            System.out.printf("added job: %d\n", avalibleJobs.availablePermits());
            addedJob = true;
        }catch (Exception e){
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
            if(addedJob) avalibleJobs.release();
        }
    }

    // may only be able to release job from space when completed.
    // if so, then space must be released by whoever called "getJob"
    public Job getJob(){
        boolean gotJob = false;
        try{
            avalibleJobs.acquire();
            lock.lock();
            Job job = tasks.pop();
            System.out.printf("took job: %d\n", avalibleJobs.availablePermits());
            gotJob = true;
            return job;
        }catch (Exception e){
            Thread.currentThread().interrupt();
            return null;
        }finally {
            lock.unlock();
            if(gotJob) space.release();
        }
    }


}
