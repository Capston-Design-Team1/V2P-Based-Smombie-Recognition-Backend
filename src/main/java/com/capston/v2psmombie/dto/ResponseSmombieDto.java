package com.capston.v2psmombie.dto;

import com.capston.v2psmombie.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(
        title = "ResponseSmombieDto: 스몸비 조회 응답 DTO",
        description = "Smombie Search response DTO")
public class ResponseSmombieDto {

    @Schema(description = "위험도 1")
    private List<LocationDto> riskLevel1 = new ArrayList<>();

    @Schema(description = "위험도 2")
    private List<LocationDto> riskLevel2 = new ArrayList<>();

    @Schema(description = "위험도 3")
    private List<LocationDto> riskLevel3 = new ArrayList<>();


    public void classifySmombieByRiskLevel(Integer riskLevel, User user) {
        LocationDto dto = new LocationDto(user);

        if (riskLevel == 1) {
            riskLevel1.add(dto);
        } else if (riskLevel == 2) {
            riskLevel2.add(dto);
        } else if (riskLevel == 3) {
            riskLevel3.add(dto);
        }
    }
}
