package com.example.hotsix.repository.build;

import com.example.hotsix.model.ServiceSchedule;
import com.example.hotsix.model.project.TeamProjectInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.hotsix.model.QServiceSchedule.serviceSchedule;
import static com.example.hotsix.model.QTeam.team;

@Repository
@RequiredArgsConstructor
public class ServiceScheduleRepositoryImpl implements ServiceScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public ServiceSchedule findEmptyService() {
        return queryFactory.selectFrom(serviceSchedule)
                .where(serviceSchedule.isUsed.eq(false).and(
                        serviceSchedule.isPendding.eq(false)
                ))
                .orderBy(serviceSchedule.id.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<TeamProjectInfo> findUsedProjectInfoIdByTeamId(Long teamId) {
        return queryFactory
                .select(serviceSchedule.teamProjectInfo)
                .from(serviceSchedule)
                .join(serviceSchedule.team, team)
                .where(team.id.eq(teamId)
                        .and(serviceSchedule.isUsed.isTrue()))
                .fetch();
    }
}
