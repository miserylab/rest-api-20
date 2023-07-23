package in.reqres.utils;

import com.github.javafaker.Faker;

public class RandomUtils {
    Faker faker = new Faker();

    public String getRandomUserName() {
        return faker.name().username();
    }

    public String getRandomJob() {
        return faker.job().position();
    }
}
