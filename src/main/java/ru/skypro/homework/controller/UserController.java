package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Users")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Update password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
    @PostMapping("set_password")
    public ResponseEntity<?> setUserPassword(@RequestBody NewPasswordDto newPasswordDto) {
        userService.setUserPassword(newPasswordDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get information about an authorized user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @GetMapping("me")
    public ResponseEntity<UserDto> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @Operation(
            summary = "Update authorized user information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UpdateUserDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @PatchMapping("me")
    public ResponseEntity<UpdateUserDto> updateUserInfo(@RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUserInfo(updateUserDto));
    }

    @Operation(
            summary = "Update the avatar of the authorized user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserAvatar(@RequestParam MultipartFile image) {
        userService.updateUseAvatar(image);
        return ResponseEntity.ok().build();
    }

}
