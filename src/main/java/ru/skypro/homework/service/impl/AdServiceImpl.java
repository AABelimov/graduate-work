package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.InvalidValueException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

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
    private final UserService userService;

    @Override
    @Transactional
    public AdDto createAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication) {
        checkTitle(createOrUpdateAdDto.getTitle());
        checkPrice(createOrUpdateAdDto.getPrice());
        checkDescription(createOrUpdateAdDto.getDescription());

        try {
            Ad ad = adRepository.save(adMapper.toEntity(createOrUpdateAdDto));
            User user = userService.getUserByEmail(authentication.getName());
            ad.setUser(user);
            return adMapper.toAdDto(uploadImage(ad, image));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: todo
        }
    }

    private Ad uploadImage(Ad ad, MultipartFile image) throws IOException {
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
            return adRepository.save(ad);
        }
    }

    @Override
    public Ad getAd(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
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
    public byte[] getImage(Integer id) {
        try {
            Ad ad = getAd(id);
            return Files.readAllBytes(Path.of(ad.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: todo
        }
    }

    @Override
    public void deleteAd(Integer id, Authentication authentication) {
        if (isAdminOrOwner(id, authentication)) {
            try {
                Ad ad = getAd(id);
                Files.deleteIfExists(Path.of(ad.getImage()));
                adRepository.delete(ad);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e); // TODO: todo
            }
        }
        throw new ForbiddenException("No permission to delete this ad");
    }

    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication) {
        checkTitle(createOrUpdateAdDto.getTitle());
        checkPrice(createOrUpdateAdDto.getPrice());
        checkDescription(createOrUpdateAdDto.getDescription());

        if (isAdminOrOwner(id, authentication)) {
            Ad ad = getAd(id);

            ad.setTitle(createOrUpdateAdDto.getTitle());
            ad.setDescription(createOrUpdateAdDto.getDescription());
            ad.setPrice(createOrUpdateAdDto.getPrice().toString());

            return adMapper.toAdDto(adRepository.save(ad));
        }
        throw new ForbiddenException("No permission to edit this ad");
    }

    @Override
    public AdsDto getMyAds(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        return adMapper.toAdsDto(adRepository.findAllByUserIdOrderByPk(user.getId()));
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image, Authentication authentication) {
        if (isAdminOrOwner(id, authentication)) {
            try {
                Ad ad = getAd(id);
                ad = uploadImage(ad, image);
                return Files.readAllBytes(Path.of(ad.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e); // TODO: Добавить исключение
            }
        }
        throw new ForbiddenException("No permission to update this image");
    }

    private boolean isAdminOrOwner(Integer id, Authentication authentication) {
        Ad ad = getAd(id);
        User user = userService.getUserByEmail(authentication.getName());
        return user.equals(ad.getUser()) || user.getRole().equals(Role.ADMIN.toString());
    }

    private void checkTitle(String title) {
        if (title.length() < 4 || title.length() > 32) {
            throw new InvalidValueException("Title length must be from 4 to 32");
        }
    }

    private void checkPrice(Integer price) {
        if (price < 0) {
            throw new InvalidValueException("Price can't be below zero");
        }
        if (price > 10_000_000) {
            throw new InvalidValueException("Maximum price is 10.000.000");
        }
    }

    private void checkDescription(String description) {
        if (description.length() < 8 || description.length() > 64) {
            throw new InvalidValueException("Title length must be from 8 to 64");
        }
    }
}
