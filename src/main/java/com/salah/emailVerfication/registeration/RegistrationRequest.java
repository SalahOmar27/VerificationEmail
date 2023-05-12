package com.salah.emailVerfication.registeration;

public record RegistrationRequest( String firstName,
        String lastName,
        String email,
        String password,
        String role) {

}
