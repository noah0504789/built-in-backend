package com.example.hotsix.service.team;

import com.example.hotsix.dto.team.TeamDto;
import com.example.hotsix.model.Member;
import com.example.hotsix.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
// 팀 생성 해야함

    void createTeam(Team team, Long memberId);
    List<TeamDto> getAllMyTeams(Long memberId);
    void deleteTeam(Long teamId);

    Team findById(Long id);
    TeamDto getTeamById(Long teamId);
    TeamDto updateJiraUrl(String jiraUrl, Long teamId);
    TeamDto updateGitUrl(String gitUrl, Long teamId);
    TeamDto updateStatus(Long teamId);

    void join(Team teamToApply, Member applicant);
}
