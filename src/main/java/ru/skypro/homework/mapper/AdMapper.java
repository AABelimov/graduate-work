package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdMapper {

    private final AdRepository adRepository;

    public Ad toEntity(CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad ad = new Ad();

        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice().toString());

        return ad;
    }

    public AdDto toAdDto(Ad ad) {
        AdDto adDto = new AdDto();

        adDto.setPk(ad.getPk());
        adDto.setAuthor(ad.getUser().getId());
        adDto.setImage(ad.getImage());
        adDto.setPrice(Integer.getInteger(ad.getPrice()));
        adDto.setTitle(ad.getTitle());

        return adDto;
    }

    public AdsDto toAdsDto(List<Ad> ads) {
        AdsDto adsDto = new AdsDto();
        List<AdDto> adDtoList = ads.stream()
                .map(this::toAdDto)
                .collect(Collectors.toList());

        adsDto.setCount(adDtoList.size());
        adsDto.setResults(adDtoList);

        return adsDto;
    }

    public ExtendedAdDto toExtendedAdDto(Ad ad) {
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();

        extendedAdDto.setPk(ad.getPk());
        extendedAdDto.setAuthorFirstName(ad.getUser().getFirstName());
        extendedAdDto.setAuthorLastName(ad.getUser().getLastName());
        extendedAdDto.setDescription(ad.getDescription());
        extendedAdDto.setEmail(ad.getUser().getEmail());
        extendedAdDto.setImage(ad.getImage());
        extendedAdDto.setPhone(ad.getUser().getPhone());
        extendedAdDto.setPrice(Integer.getInteger(ad.getPrice()));
        extendedAdDto.setTitle(ad.getTitle());

        return extendedAdDto;
    }
}
