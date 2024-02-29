package ru.skypro.homework.constants;

import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;

import java.util.ArrayList;
import java.util.List;

import static ru.skypro.homework.constants.UserConstants.*;

public class AdConstants {

    public static final Integer AD_ID_1 = 1;
    public static final Integer AD_ID_2 = 2;
    public static final Integer AD_ID_3 = 3;
    public static final Integer AD_ID_INCORRECT = 500;
    public static final Integer AD_AUTHOR_ID_1 = USER_ID_1;
    public static final Integer AD_AUTHOR_ID_2 = USER_ID_2;
    public static final Integer AD_AUTHOR_ID_3 = USER_ID_1;
    public static final String AD_TITLE_1 = "AD_TITLE_1";
    public static final String AD_TITLE_2 = "AD_TITLE_2";
    public static final String AD_TITLE_3 = "AD_TITLE_3";
    public static final String AD_TITLE_EDIT = "AD_TITLE_EDIT";
    public static final String AD_PATH_TO_IMAGE_1 = "/market/users/photos/default-photo.jpg";
    public static final String AD_PATH_TO_IMAGE_2 = "/market/users/photos/default-photo.jpg";
    public static final String AD_PATH_TO_IMAGE_3 = "/market/users/photos/default-photo.jpg";
    public static final String AD_LINK_TO_IMAGE_1 = "/ads/" + AD_ID_1 + "/image";
    public static final String AD_LINK_TO_IMAGE_2 = "/ads/" + AD_ID_2 + "/image";
    public static final String AD_LINK_TO_IMAGE_3 = "/ads/" + AD_ID_3 + "/image";
    public static final String AD_DESCRIPTION_1 = "AD_DESCRIPTION_1";
    public static final String AD_DESCRIPTION_2 = "AD_DESCRIPTION_2";
    public static final String AD_DESCRIPTION_3 = "AD_DESCRIPTION_3";
    public static final String AD_DESCRIPTION_EDIT = "AD_DESCRIPTION_EDIT";
    public static final Integer AD_PRICE_1 = 100;
    public static final Integer AD_PRICE_2 = 200;
    public static final Integer AD_PRICE_3 = 300;
    public static final Integer AD_PRICE_EDIT = 100_000;
    public static final AdDto AD_DTO_1 = new AdDto(AD_ID_1, AD_AUTHOR_ID_1, AD_LINK_TO_IMAGE_1, AD_PRICE_1, AD_TITLE_1);
    public static final AdDto AD_DTO_2 = new AdDto(AD_ID_2, AD_AUTHOR_ID_2, AD_LINK_TO_IMAGE_2, AD_PRICE_2, AD_TITLE_2);
    public static final AdDto AD_DTO_3 = new AdDto(AD_ID_3, AD_AUTHOR_ID_3, AD_LINK_TO_IMAGE_3, AD_PRICE_3, AD_TITLE_3);
    public static final AdDto AD_DTO_EDIT = new AdDto(AD_ID_1, AD_AUTHOR_ID_1, AD_LINK_TO_IMAGE_1, AD_PRICE_EDIT, AD_TITLE_EDIT);
    public static final ExtendedAdDto EXTENDED_AD_DTO_1 = new ExtendedAdDto(AD_ID_1, USER_FIRST_NAME_1, USER_LAST_NAME_1, AD_DESCRIPTION_1, USER_EMAIL_1, AD_LINK_TO_IMAGE_1, USER_PHONE_NUMBER_1, AD_PRICE_1, AD_TITLE_1);
    public static final List<AdDto> AD_DTO_LIST_ALL = new ArrayList<>(List.of(
            AD_DTO_1,
            AD_DTO_2
    ));
    public static final List<AdDto> AD_DTO_LIST_1 = new ArrayList<>(List.of(AD_DTO_1));
    public static final AdsDto ADS_DTO_ALL = new AdsDto(2, AD_DTO_LIST_ALL);
    public static final AdsDto ADS_DTO_1 = new AdsDto(1, AD_DTO_LIST_1);
    public static final CreateOrUpdateAdDto CREATE_OR_UPDATE_AD_DTO_EDIT = new CreateOrUpdateAdDto(AD_TITLE_EDIT, AD_PRICE_EDIT, AD_DESCRIPTION_EDIT);
}
