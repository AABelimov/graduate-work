package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

public interface AdService {

    AdDto createAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication);

    Ad getAd(Integer id);

    ExtendedAdDto getInfoAboutAd(Integer id);

    AdsDto getMyAds(Authentication authentication);

    AdsDto getAll();

    byte[] getImage(Integer id);

    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication);

    byte[] updateAdImage(Integer id, MultipartFile image, Authentication authentication);

    void deleteAd(Integer id, Authentication authentication);

}
