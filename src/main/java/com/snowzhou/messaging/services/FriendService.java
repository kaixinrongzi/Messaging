package com.snowzhou.messaging.services;

import com.snowzhou.messaging.dto.FriendInvitationDTO;
import com.snowzhou.messaging.dto.UserDTO;
import com.snowzhou.messaging.requests.SendFriendInvitationRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class FriendService {

    @Autowired private UserService userService;
    @Autowired private FriendInvitationDAO friendInvitationDAO;

    public void sendFriendInvitation(String loginToken, int receiverUserId, String message) throws Exception {
        UserDTO sender = this.userService.authenticate(loginToken);
        FriendInvitationDTO receiver = new FriendInvitationDTO();
        receiver.set
    }

}
