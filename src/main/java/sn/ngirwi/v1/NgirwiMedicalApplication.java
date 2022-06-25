package sn.ngirwi.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NgirwiMedicalApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(NgirwiMedicalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
