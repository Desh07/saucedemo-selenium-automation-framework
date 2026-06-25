package utils;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "users")
    public Object[][] users() {

        return new Object[][]{
                {"standard_user"},
                {"problem_user"},
                {"performance_glitch_user"},
                {"error_user"},
                {"visual_user"}
        };
    }
}