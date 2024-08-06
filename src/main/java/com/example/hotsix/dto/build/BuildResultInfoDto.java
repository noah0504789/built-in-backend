package com.example.hotsix.dto.build;

import com.example.hotsix.model.project.BuildResult;
import lombok.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildResultInfoDto {
    private Long id;
    private Long teamProjectInfoId;
    private Long deployNum;
    private String status;
    private LocalDateTime buildTime;

    private List<BuildStageInfoDto> buildStageInfoDtoList;

    public static BuildResultInfoDto from(BuildResult buildResult) {
        return BuildResultInfoDto.builder()
                .id(buildResult.getId())
                .teamProjectInfoId(buildResult.getTeamProjectInfo().getId())
                .deployNum(buildResult.getDeployNum())
                .status(buildResult.getStatus().name())
                .buildTime(buildResult.getBuildTime())
                .build();
    }
}
