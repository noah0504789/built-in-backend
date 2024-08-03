package com.example.hotsix.model;

import com.example.hotsix.converter.ListToJsonConverter;
import com.example.hotsix.dto.recruit.RecruitRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@Table(name = "recruit")
@SuperBuilder
@Getter
@NoArgsConstructor
@DiscriminatorValue("RECRUIT")
public class Recruit extends Board {

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @Column(name = "domain", nullable = false)
    private String domain;

    @Column(name = "desired_pos_list", nullable = false)
    @Convert(converter = ListToJsonConverter.class)
    private List<String> desiredPosList;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public void update(RecruitRequest request) {
        this.thumbnail = request.thumbnail().getOriginalFilename();
        this.introduction = request.introduction();
        this.domain = request.domain();
        this.desiredPosList = request.desiredPosList();
        this.content = request.content();
    }
}
