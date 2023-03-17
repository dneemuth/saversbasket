package com.sb.web.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.entities.User;
import com.sb.web.entities.UserContribution;
import com.sb.web.repositories.UserContributionRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.utils.ApplicationUtils;

@Transactional
@Service
public class PlanService {

	@Autowired
	private UserContributionRepository userContributionRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * convenience method to check if user can perform allowed number of compare
	 * operations per day.
	 * 
	 * @param idUser
	 * @return
	 */
	public boolean canPerformCompareBasketDaily(Integer idUser) {
		boolean comparisonAllowed = false;
		 // subtract 1 day from today
		LocalDate dateBefore1Day = LocalDate.now().minusDays(1);
		List<UserContribution> comparisonsPerformed = userContributionRepository
				.findByDateGreaterThan(ApplicationUtils.convertToDateViaInstant(dateBefore1Day));

		User userTarget = userRepository.getOne(idUser);
		int numberOfBasketComparisonsAllowed = 10;
		if (userTarget != null && userTarget.getAccount().getSubscriptions() != null
				&& userTarget.getAccount().getSubscriptions().size() > 0
				&& userTarget.getAccount().getSubscriptions().get(0).getPlan() != null) {
			numberOfBasketComparisonsAllowed = userTarget.getAccount().getSubscriptions().get(0).getPlan()
					.getNumberOfBasketComparisonsAllowed();
		}

		if (comparisonsPerformed.size() <= numberOfBasketComparisonsAllowed) {
			comparisonAllowed = true;
		}
		return comparisonAllowed;
	}

	/**
	 * convenience method to check if user can perform allowed number of scans per
	 * day.
	 * 
	 * @param idUser
	 * @return
	 */
	public boolean canPerformScanDaily(Integer idUser) {
		boolean scanningsAllowed = false;
		 // subtract 1 day from today
		LocalDate dateBefore1Day = LocalDate.now().minusDays(1);
		List<UserContribution> scansPerformed = userContributionRepository
				.findByDateGreaterThan(ApplicationUtils.convertToDateViaInstant(dateBefore1Day));

		User userTarget = userRepository.getOne(idUser);
		int numberOfBasketComparisonsAllowed = 10;
		if (userTarget != null && userTarget.getAccount().getSubscriptions() != null
				&& userTarget.getAccount().getSubscriptions().size() > 0
				&& userTarget.getAccount().getSubscriptions().get(0).getPlan() != null) {
			numberOfBasketComparisonsAllowed = userTarget.getAccount().getSubscriptions().get(0).getPlan()
					.getNumberOfDailyScansAllowed();
		}		

		if (scansPerformed.size() <= numberOfBasketComparisonsAllowed) {
			scanningsAllowed = true;
		}
		return scanningsAllowed;
	}
}
