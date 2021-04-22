package others.ye;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalSequenceTest {

	@Test
	public void testSeq() {
		System.out.println(new SeqName("t").equals(new SeqName("test3")));
	}

	ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new BasicThreadFactory.Builder().namingPattern("test-pool-%d").daemon(true).build());

	@Test
	public void getNext() throws Exception {
		LocalSequence localSequence = new LocalSequence(() -> {
			ArrayList<DocSequence> docSequences = new ArrayList<>();
			docSequences.add(new DocSequence("test1", 100l));
			docSequences.add(new DocSequence("test2", 200l));
			docSequences.add(new DocSequence("test3", 300l));
			return docSequences;
		}, "");

		int size = 10;
		CountDownLatch latch = new CountDownLatch(size);
		List<Callable<String>> tests = Stream.iterate(0, n -> n + 1).limit(size).map(n -> (Callable<String>) () -> {
			try {
				Long val = localSequence.getNext("test1");
				String str = String.valueOf(val);
				System.out.println(str);
				return str;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new RuntimeException(e);
			} finally {
				latch.countDown();
			}
		}).collect(Collectors.toList());
		Thread.sleep(1000);
		long start = System.currentTimeMillis();
		List<Future<String>> futures = executorService.invokeAll(tests);
		latch.await();
		System.out.println("end");
		System.out.println(System.currentTimeMillis() - start);
		executorService.shutdown();
		long count = futures.stream().filter(f -> {
			try {
				return f.get() != null;
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}).map(f -> {
			try {
				return f.get();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}).distinct().count();
		Assert.isTrue(count == size, "有部分请求失败了");
		Thread.sleep(3000);//等待三秒 也许数据库保存比较慢
	}
}