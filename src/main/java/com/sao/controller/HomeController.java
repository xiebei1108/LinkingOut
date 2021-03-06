package com.sao.controller;

import com.sao.domain.model.Activity;
import com.sao.domain.model.News;
import com.sao.domain.model.UserDetail;
import com.sao.service.ActivityService;
import com.sao.service.NewsService;
import com.sao.service.UserDetailService;
import com.sao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedList;

@Controller
@RequestMapping("/")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private NewsService newsService;

    @GetMapping
    public String home(Model model) {
        Iterable<News> news = newsService.findBannerNews();
        LinkedList<Activity> activities = new LinkedList<>();
        for (Activity activity : activityService.findAll()) {
            activities.add(activity);
        }

        LinkedList<Activity> activitiesReverse = new LinkedList<>();
        while (activities.size() != 0) {
            activitiesReverse.push(activities.pop());
        }

        model.addAttribute("news", news);
        model.addAttribute("activities", activitiesReverse);
        return "home";
    }

//    @PostMapping
//    public String addActivity(HttpSession session) {
//        Long uid = Long.valueOf(session.getAttribute("uid").toString());
//        logger.info("--------------------- add activity");
//        logger.info("uid:" + uid);
//        return "home";
//    }

    @PostMapping
    public String addActivity(HttpSession session,
                              @RequestParam(name = "content") String content,
                              @RequestParam(name = "type") String type) {
        Long uid = Long.valueOf(session.getAttribute("uid").toString());
        Activity activity = new Activity();
        activity.setUid(uid);
        activity.setContent(content);
        activity.setCreated(new Date());
        activity.setLikes(new Long(0));
        activity.setType(type);
        activityService.addActivity(activity);

        logger.info("--------------------- add activity");
        logger.info("uid:" + uid);
        logger.info("content" + uid);

        return "home";
    }
}
