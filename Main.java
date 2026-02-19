public class Main {

    public static void main(String[] args){

        for (int i = 0; i < 1; i++){
            Thread prod = new Thread(new Producer());

            prod.start();
        }

        for (int i = 0; i < 2; i++){
            Thread worker = new Thread(new Worker());

            worker.start();
        }


    }
}
