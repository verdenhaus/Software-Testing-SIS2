package data;

import java.util.UUID;

public class UserFactory {

    public static User createUser(){
        String uuid = UUID.randomUUID().toString();

        return new User(
            "Test","User", "2005-06-09", "KZ", 
            "030060", "42", "77055403350",
            "test-" + uuid + "@practicesoftwaretesting.com",
            "FoldedPaper623%");
    }

    public static User loginUser(){
        return new User(null, null, null, null,null, null, null,
            "customer3@practicesoftwaretesting.com", "pass123");
    }

    public static User invalidLoginUser(){
        return new User(null, null, null, null,null, null, null,
            "randomeUser@practicesoftwaretesting.com", "WrongPassword123!");
    }
}
