package config;

public final class TestConfig {
    public static final String BASE_URL = env("BASE_URL", "https://practicesoftwaretesting.com").replaceAll("/+$", "");
    public static final String CUSTOMER_EMAIL = env("CUSTOMER_EMAIL", "customer2@practicesoftwaretesting.com");
    public static final String CUSTOMER_PASSWORD = env("CUSTOMER_PASSWORD", "welcome01");

    private TestConfig() {
    }

    private static String env(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? fallback : value;
    }
}