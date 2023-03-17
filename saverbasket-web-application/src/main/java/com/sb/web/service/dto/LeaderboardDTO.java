package com.sb.web.service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LeaderboardDTO {	
	@JsonFormat(pattern = "yyyy/MM/dd")
    @JsonProperty("start_date")
    private Date startDate;
    private Integer idUser;
    private String badge;
    private String name;
    private Integer points;
    private Integer rank;
}
