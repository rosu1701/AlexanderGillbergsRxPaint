import reactor.core.publisher.Mono;

import java.time.Duration;

public class Launcher {

    public static void main(String[] args){
        // ADDED BY ROBIN FOR TESTING
      /*  Mono.delay(Duration.ofSeconds(1))
                .doOnNext(it -> {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .block(); */

        App.main(args);
    }
}
