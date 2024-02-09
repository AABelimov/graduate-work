package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

@Service
public class AdServiceImpl implements AdService {
    @Override
    public AdDto createAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image) {
        return new AdDto();
    }

    @Override
    public AdsDto getAll() {
        return new AdsDto();
    }

    @Override
    public ExtendedAdDto getInfoAboutAd(Integer id) {
        return new ExtendedAdDto();
    }

    @Override
    public void deleteAd(Integer id) {

    }

    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        return new AdDto();
    }

    @Override
    public AdsDto getMyAds() {
        return new AdsDto();
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image) {
        return new byte[0];
    }
}
