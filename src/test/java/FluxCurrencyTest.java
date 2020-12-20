
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxCurrencyTest {
    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("nuevo", "hola");
        stringFlux.subscribe(System.out::println);
    }
}
