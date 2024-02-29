package ru.skypro.homework.constants;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.auth.RegisterDto;

public class UserConstants {

    public static final Integer USER_ID_1 = 1;
    public static final Integer USER_ID_2 = 2;
    public static final String USER_EMAIL_1 = "email@email.com";
    public static final String USER_EMAIL_2 = "email2@email.com";
    public static final String USER_PASSWORD_1 = "password12345";
    public static final String USER_PASSWORD_2 = "password12345";
    public static final String USER_FIRST_NAME_1 = "firstName";
    public static final String USER_FIRST_NAME_2 = "firstName2";
    public static final String USER_LAST_NAME_1 = "lastName";
    public static final String USER_LAST_NAME_2 = "lastName2";
    public static final String USER_PHONE_NUMBER_1 = "+7(999)999-99-99";
    public static final String USER_PHONE_NUMBER_2 = "+7(000)000-00-00";
    public static final Role USER_ROLE_1 = Role.USER;
    public static final Role USER_ROLE_2 = Role.ADMIN;
    public static final RegisterDto REGISTER_DTO_1 = new RegisterDto(USER_EMAIL_1, USER_PASSWORD_1, USER_FIRST_NAME_1, USER_LAST_NAME_1, USER_PHONE_NUMBER_1, USER_ROLE_1);
    public static final RegisterDto REGISTER_DTO_2 = new RegisterDto(USER_EMAIL_2, USER_PASSWORD_2, USER_FIRST_NAME_2, USER_LAST_NAME_2, USER_PHONE_NUMBER_2, USER_ROLE_2);
}
