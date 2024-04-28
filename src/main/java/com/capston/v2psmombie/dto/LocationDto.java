package com.capston.v2psmombie.dto;

import com.capston.v2psmombie.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
        title = "LocationDto: User 위치 DTO",
        description = "User location DTO")
public class LocationDto {
    @Schema(description = "사용자 디바이스 ID", example = "b8581580-f97e-11ee-af4b-490ac169fde7")
    private String deviceId;

    @Schema(description = "위도", example = "37.554")
    private double latitude;

    @Schema(description = "경도", example = "126.97")
    private double longitude;

    public LocationDto(User user) {
        this.deviceId = user.getDeviceId();
        this.latitude = user.getLatitude();
        this.longitude = user.getLongitude();
    }
}
