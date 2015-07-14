package ru.javarush.taskbook.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javarush.taskbook.logger.LoggerWrapper;


@Controller
@RequestMapping(value = "/")
public class MainController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MainController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String toIndex() {
        LOG.info("toIndex()");
        return "index.html";
    }

    /*
    @RequestMapping(value = "/userprofile/problems", method = RequestMethod.GET)
    public String toUserProfileProblems(){
        LOG.info("toUserProfileProblems()");
        return "userprofile/problems";
    }

    @RequestMapping(value = "/userprofile/tests", method = RequestMethod.GET)
    public String toUserProfileTests(){
        LOG.info("toUserProfileTests()");
        return "userprofile/tests";
    }

    @RequestMapping(value = "/userprofile/solved", method = RequestMethod.GET)
    public String toUserProfileSolved(){
        LOG.info("toUserProfileSolved()");
        return "userprofile/solved";
    }

    @RequestMapping(value = "/userprofile/passed", method = RequestMethod.GET)
    public String toUserProfilePassed(){
        LOG.info("toUserProfilePassed()");
        return "userprofile/passed";
    }

    @RequestMapping(value = "/problems/tests", method = RequestMethod.GET)
    public String toProblemsTests(){
        LOG.info("toProblemsTests()");
        return "problems/tests";
    }

    @RequestMapping(value = "/problems/problems", method = RequestMethod.GET)
    public String toProblemsProblems(){
        LOG.info("toProblemsProblems()");
        return "problems/problems";
    }

    @RequestMapping(value = "/problems/problem/*")
    public String toProblem(){
        LOG.info("toProblem()");
        return "problems/problem";
    }

    @RequestMapping(value = "/problems/test/*")
    public String toTest(){
        LOG.info("toTest()");
        return "problems/test";
    }*/
}
