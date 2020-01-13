package _12;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable {

	private int id;
	private Random random;
	private CyclicBarrier barrier;

	public Worker(int id, CyclicBarrier barrier) {
		super();
		this.id = id;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		System.out.println("thread with id " + id + " starts the task ... ");
		try {
			Thread.sleep(random.nextInt(3000));
		} catch (Exception e) {
		}

		System.out.println("thread with id " + id + " finished!");

		try {
			barrier.await();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + "]";
	}

}

public class App {
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {

			@Override
			public void run() {
				System.out.println("all task finished!");
			}
		});

		for (int i = 0; i < 5; i++) {
			service.execute(new Worker(i + 1, barrier));
		}
		
		service.shutdown();
	}
}
