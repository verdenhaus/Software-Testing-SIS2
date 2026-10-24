package data;

public record User (
    String firstName,
    String lastName,
    String dateOfBirth,
    String country,
    String postalCode,
    String houseNumber,
    String phone,
    String email,
    String password
) {}
