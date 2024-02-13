package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Ads")
@RestController
@RequestMapping("ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @Operation(
            summary = "Adding an ad",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> createAd(@RequestPart(value = "properties") CreateOrUpdateAdDto createOrUpdateAdDto,
                                          @RequestPart(value = "image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.createAd(createOrUpdateAdDto, image));
    }

    @Operation(
            summary = "Receiving all ads",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAll());
    }

    @Operation(
            summary = "Getting information about an ad",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtendedAdDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<ExtendedAdDto> getInfoAboutAd(@PathVariable Integer id) {
        return ResponseEntity.ok(adService.getInfoAboutAd(id));
    }

    @Operation(
            summary = "Receiving ads from an authorized user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @GetMapping("me")
    public ResponseEntity<AdsDto> getMyAds() {
        return ResponseEntity.ok(adService.getMyAds());
    }

    @Operation(
            summary = "Updating ad information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PatchMapping("{id}")
    public ResponseEntity<AdDto> updateAd(@PathVariable Integer id,
                                          @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        return ResponseEntity.ok(adService.updateAd(id, createOrUpdateAdDto));
    }

    @Operation(
            summary = "Update ad picture",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = byte[].class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdImage(@PathVariable Integer id, @RequestParam MultipartFile image) {
        return ResponseEntity.ok(adService.updateAdImage(id, image));
    }

    @Operation(
            summary = "Remove ad",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        adService.deleteAd(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
