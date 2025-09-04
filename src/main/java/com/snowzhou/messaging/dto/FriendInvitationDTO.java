package com.snowzhou.messaging.dto;

import com.snowzhou.messaging.enumeration.FriendInvitationStatus;
import lombok.Data;

@Data
public class FriendInvitationDTO {
    private int id;
    private int sendUserId;
    private int receiverUserId;
    private String message;
    private Data sendTime;
    private FriendInvitationStatus friendInvitationStatus;

}
