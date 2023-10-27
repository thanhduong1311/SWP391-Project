package com.demo.homemate.dtos.image;

import com.demo.homemate.dtos.notification.MessageObject;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
    public class ImageResponse {
        private String imgUrl;
        private MessageObject messageObject;
    }
