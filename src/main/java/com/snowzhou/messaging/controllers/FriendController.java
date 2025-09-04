package com.snowzhou.messaging.controllers;

import com.snowzhou.messaging.requests.SendFriendInvitationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @PostMapping("/sendFriendInvitations")
    public void sendFriendInvitation(@CookieValue String loginToken,
                                     @RequestBody SendFriendInvitationRequest sendFriendInvitationRequest){}

    @GetMapping("listPendingInvitations")
    public void listPendingInvitation(@CookieValue String loginToken,
                                      @RequestParam(defaultValue = "1")){}



}
