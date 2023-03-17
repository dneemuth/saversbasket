package com.sb.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sb.web.service.LeaderboardService;
import com.sb.web.utils.ApplicationUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/gaming")
@Api(tags = "gaming")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Transactional
public class GameController extends AbstractController {

	@Autowired
	private LeaderboardService leaderboardService;

	@Autowired
	BuildProperties buildProperties;

	@GetMapping("/viewLeaderboard")
	public ModelAndView viewLeaderboard(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
		ModelAndView modelAndView = new ModelAndView("desktop/leaderboard");
		if (isAuthorized()) {
			modelAndView.addObject("isAnonymous", false);
			// retrieve email logged
			modelAndView.addObject("userLogged", getPrincipal());
		} else {
			modelAndView.addObject("isAnonymous", true);
		}
		modelAndView.addObject("scores", leaderboardService.getLeaderboardScore(pageNumber, size));

		// Add version information for display
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		return modelAndView;
	}

}
