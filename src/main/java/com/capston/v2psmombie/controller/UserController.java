package com.capston.v2psmombie.controller;


import com.capston.v2psmombie.domain.User;
import com.capston.v2psmombie.dto.ResponseSmombieDto;
import com.capston.v2psmombie.dto.UserCreateDto;
import com.capston.v2psmombie.dto.UserUpdateDto;
import com.capston.v2psmombie.riskCalculate.RiskCalculator;
import com.capston.v2psmombie.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "User", description = "User 관련 REST API에 대한 명세")
public class UserController {

    private final UserService userService;


    /**
     * 사용자 등록
     **/
    @PostMapping("/users")
    @Operation(summary = "User 생성(create)", description = "User를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "[SUCCSS] 사용자 생성"),
            @ApiResponse(responseCode = "400", description = "[FAIL] 사용자 생성")
    })
    public ResponseEntity<String> create(@RequestBody UserCreateDto dto) {
        try {
            String createdUserDeviceId = userService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("[SUCCSS] 사용자 생성: " + createdUserDeviceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] 사용자 생성: " + e.getMessage());
        }
    }


    /**
     * 사용자 정보 수정
     **/
    @PatchMapping("/users/{deviceId}")
    @Operation(summary = "User 정보 수정(update)", description = "User 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "[SUCCESS] 사용자 데이터 수정"),
            @ApiResponse(responseCode = "400", description = "[FAIL] 사용자 데이터 수정")
    })
    public ResponseEntity<String> update(
            @Schema(description = "사용자 디바이스 ID", example = "b8581580-f97e-11ee-af4b-490ac169fde7")
            @PathVariable String deviceId,

            @RequestBody UserUpdateDto dto
    ) {
        try {
            String updatedDeviceId = userService.update(deviceId, dto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("[SUCCESS] 사용자 데이터 수정: " + updatedDeviceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] 사용자 데이터 수정: " + e.getMessage());
        }
    }


    /**
     * 사용자 삭제
     **/
    @DeleteMapping("/users/{deviceId}")
    @Operation(summary = "User 삭제(delete)", description = "User를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "[SUCCESS] 사용자 삭제"),
            @ApiResponse(responseCode = "400", description = "[FAIL] 사용자 삭제")
    })
    public ResponseEntity<String> delete(
            @Schema(description = "사용자 디바이스 ID", example = "b8581580-f97e-11ee-af4b-490ac169fde7")
            @PathVariable String deviceId
    ) {
        try {
            String delUserDeviceId = userService.delete(deviceId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("[SUCCESS] 사용자 삭제: " + delUserDeviceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] 사용자 삭제: " + e.getMessage());
        }
    }


    /**
     * 스몸비 보행자 정보 조회
     **/
    @GetMapping("/users/{deviceId}/smombies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseSmombieDto.class)))
                    }),
            @ApiResponse(responseCode = "400", description = "[FAIL] 스몸비 조회")
    })
    public ResponseEntity<Object> smombies(
            @Schema(description = "사용자 디바이스 ID", example = "b8581580-f97e-11ee-af4b-490ac169fde7")
            @PathVariable String deviceId
    ) {
        try {

            User car = userService.getUserByDeviceId(deviceId);
            List<User> smombies = userService.getSmombieUsers(deviceId);

            // 위험도 계산
            List<Integer> riskList = new RiskCalculator(smombies, car, carDeceleration).riskCheck();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseSmombieDto(riskList, smombies));

        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] 스몸비 조회: " + e.getMessage());
        }
    }

}
