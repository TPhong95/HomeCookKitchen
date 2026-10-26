package user.chefuser;

import org.springframework.boot.SpringApplication;

public class TestChefUserApplication {

    public static void main(String[] args) {
        SpringApplication.from(TestChefUserApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
