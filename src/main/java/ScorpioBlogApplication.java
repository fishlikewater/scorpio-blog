
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("scorpio")
@ServletComponentScan({"scorpio"})
public class ScorpioBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScorpioBlogApplication.class, args);
	}
}
