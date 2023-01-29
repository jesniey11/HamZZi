package com.ssafy.api.controller;

import com.ssafy.api.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("api/kakao")
public class MainController {
    @Autowired
    KakaoService kakaoService;

//
//    @ResponseBody
    @GetMapping()
    public String testConnect() {
        return "연결성공";
    }
    @RequestMapping("kakao/sign_in")
    public String kakaoSignIn(@RequestParam("code") String code) {
        Map<String,Object> result = kakaoService.execKakaoLogin(code);
        return "redirect:webauthcallback://success?customToken="+result.get("customToken").toString();
    }
}
