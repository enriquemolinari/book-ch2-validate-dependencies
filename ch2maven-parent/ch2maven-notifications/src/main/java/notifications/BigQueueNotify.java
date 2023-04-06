package notifications;

import java.io.IOException;
import com.leansoft.bigqueue.BigQueueImpl;
import com.leansoft.bigqueue.IBigQueue;
import domain.api.DomainException;
import domain.api.Notify;

public class BigQueueNotify implements Notify {

  private IBigQueue bigQueue;

  public BigQueueNotify(String path, String name) throws IOException {
    this.bigQueue = new BigQueueImpl(path, name);
  }

  @Override
  public void publish(String userEmail) {
    try {
      this.bigQueue.enqueue(userEmail.getBytes());
    } catch (IOException e) {
      throw new DomainException("The email notification could not be enqueued",
          e);
    }
  }

}
