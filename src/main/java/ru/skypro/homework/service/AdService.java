package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;

import java.util.List;

public interface AdService {

    AdDto createAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image);

    AdsDto getAll();

    ExtendedAdDto getInfoAboutAd(Integer id);

    void deleteAd(Integer id);

    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);

    AdsDto getMyAds();

    byte[] updateAdImage(Integer id, MultipartFile image);
}
