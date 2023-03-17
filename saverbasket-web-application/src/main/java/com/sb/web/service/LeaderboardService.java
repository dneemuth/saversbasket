package com.sb.web.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.entities.User;
import com.sb.web.repositories.UserRepository;
import com.sb.web.response.dto.CreditUpdateForUserResponse;
import com.sb.web.service.dto.LeaderboardDTO;
import com.sb.web.service.dto.paging.Page;
import com.sb.web.service.dto.paging.Paged;
import com.sb.web.service.dto.paging.Paging;

@Service
public class LeaderboardService {	
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private BasketService basketService;
	
	/**
	 * Retrieve leaderboard with score points.
	 * 
	 * @param pageNumber
	 * @param size
	 * @return
	 */
	public Paged<LeaderboardDTO> getLeaderboardScore(int pageNumber, int size) {
        List<LeaderboardDTO> scorePoints = new ArrayList<LeaderboardDTO>();              	
        	
    	List<User> qualifiedUsers = userRepository.getAllActiveUsers();
    	if (qualifiedUsers != null && qualifiedUsers.size() > 0) {   
    		
    		for (User user : qualifiedUsers) {
    			LeaderboardDTO leaderboardDTO = new LeaderboardDTO();
        		leaderboardDTO.setIdUser(user.getIdUser());
        		leaderboardDTO.setName(user.getUsername());       		
        		
        		/* get total points per user */
        		CreditUpdateForUserResponse creditUpdateForUserResponse  = basketService.getCreditsForUser(user.getIdUser());         		
        		leaderboardDTO.setPoints(creditUpdateForUserResponse.getTotalCredits().intValue());
        		
        		leaderboardDTO.setBadge("*");
        		leaderboardDTO.setStartDate(creditUpdateForUserResponse.getLastCreditDate());
        		scorePoints.add(leaderboardDTO);
    		}        		
    	}     

        List<LeaderboardDTO> paged = scorePoints.stream().sorted(Comparator.comparingInt(LeaderboardDTO::getPoints).reversed())
                                        .skip(pageNumber)
                                        .limit(size)
                                        .collect(Collectors.toList());

        int totalPages = ( (scorePoints.size() - 1 ) / size ) +1 ;
        int skip = pageNumber > 1 ? (pageNumber - 1) * size : 0;

        paged = scorePoints.stream().sorted(Comparator.comparingInt(LeaderboardDTO::getPoints).reversed())
        .skip(skip)
        .limit(size)
        .collect(Collectors.toList());
        
        /** Annotate rank */
        int rank = 1;
        for (LeaderboardDTO leaderboard : paged) {
        	leaderboard.setRank(rank++);
        }

        return new Paged(new Page(paged, totalPages), Paging.of(totalPages, pageNumber, size));
    }

}
