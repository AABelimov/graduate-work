package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    @Value("${path.to.ad.photo}")
    private String photoPath;
    private final AdMapper adMapper;
    private final AdRepository adRepository;

    @Override
    public AdDto createAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image) {
        Ad ad = null;
        try {
            ad = saveImage(adMapper.toEntity(createOrUpdateAdDto), image);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: todo
        }
        return adMapper.toAdDto(ad);
    }

    private Ad saveImage(Ad ad, MultipartFile image) throws IOException {
        Path filePath = Path.of(photoPath, ad.hashCode() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            ad.setImage(filePath.toString());
            adRepository.save(ad);
        }

        return ad;
    }

    @Override
    public Ad getAd(Integer id) {
        return adRepository.findById(id).orElseThrow(); // TODO: добавить исключение
    }

    @Override
    public AdsDto getAll() {
        return adMapper.toAdsDto(adRepository.findAll());
    }

    @Override
    public ExtendedAdDto getInfoAboutAd(Integer id) {
        return adMapper.toExtendedAdDto(getAd(id));
    }

    @Override
    public void deleteAd(Integer id) {
        Ad ad = getAd(id);
        adRepository.delete(ad);
    }

    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad ad = getAd(id);

        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice().toString());

        return adMapper.toAdDto(adRepository.save(ad));
    }

    @Override
    public AdsDto getMyAds() {
        return adMapper.toAdsDto(adRepository.findAllByUserId(1)); // TODO: Достать авторизованного юзера сюда
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image) {
        try {
            Ad ad = saveImage(getAd(id), image);
            return Files.readAllBytes(Path.of(ad.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: Добавить исключение
        }
    }
}
