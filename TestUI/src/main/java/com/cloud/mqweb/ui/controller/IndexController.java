package com.cloud.mqweb.ui.controller;

import com.cloud.mqweb.ui.dto.AgentForm;
import com.cloud.mqweb.ui.dto.PostForm;
import com.cloud.mqweb.ui.dto.UserForm;
import com.cloud.mqweb.ui.service.ApiService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private ApiService apiService;

    // login.mustache로 진입
    @GetMapping("/")
    public String loginIndex() {
        return "login";
    }

    // login에서 입력받은 userId, userPw를 session에 저장 후 index.mustache로 진입
    @PostMapping("/index")
    public String indexIndex(UserForm userForm, HttpSession session) {

        log.info(String.valueOf(userForm));

        Object userId = userForm.getUserId();
        Object userPw = userForm.getUserPw();
        session.setAttribute("userId", userId);
        session.setAttribute("userPw", userPw);

        log.info((String) session.getAttribute("userId"));
        return "index";
    }

    // post.mustache로 진입
    @GetMapping("/post")
    public String postIndex() {
        return "post";
    }

    @PostMapping("/mqui")
    public String mquiIndex(PostForm postForm, Model model, HttpSession session) {

        // session에 있는 userId, userPw를 변수에 저장
        String userId = (String) session.getAttribute("userId");
        String userPw = (String) session.getAttribute("userPw");

        // VM 생성 요청 후 vmGuestIp를 리턴
        String vmGuestIp = apiService.createApi(userId, userPw, postForm.getName(), postForm.getLocation());

        // vmGuestIp를 세션에 저장
        session.setAttribute("vmGuestIp", vmGuestIp);

        // model에 post 값을 저장 (post.mustache에서 해당 값 사용 예정)
        model.addAttribute("post",postForm);

        // mqui.mustache 진입
        return "mqui";
    }

    @PostMapping("/delete")
    public String deleteIndex(HttpSession session) {

        // session에 있는 userId, userPw, vmGuestIp를 변수에 저장
        String userId = (String) session.getAttribute("userId");
        String userPw = (String) session.getAttribute("userPw");
        String vmGuestIp = (String) session.getAttribute("vmGuestIp");

        // VM 삭제 요청
        apiService.deleteApi(userId, userPw, vmGuestIp);

        // index.mustache로 진입
        return "index";
    }

    // agent.mustache로 진입
    @GetMapping("/agent")
    public String agentIndex() {
        return "agent";
    }

    @GetMapping("/agentcontrol")
    public String agentControl(AgentForm agentForm) {

        // control 변수 초기값은 null
        String control=null;

        // 사용자가 start, stop, restart 버튼 중 하나를 누르면, 해당 parameter 값에 추가 (나머지 2개는 null)
        ArrayList<String> controlParam = new ArrayList<String>();
        controlParam.add(agentForm.getStart());
        controlParam.add(agentForm.getStop());
        controlParam.add(agentForm.getRestart());

        log.info(String.valueOf(controlParam));

        // null이 아닌 parameter 값을 control 변수에 추가
        for (String param:controlParam) {
            if (param != null) {
                control = param;
            }
        }

        String URI = "http://localhost:8000/agentmgmt/control";

        // service control 요청
        apiService.agentApi(URI, agentForm.getKafka(), agentForm.getZookeeper(), agentForm.getKafdrop(), control);

        // agent.mustache로 진입
        return "agent";
    }
}

