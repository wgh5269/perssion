package com.wgh.beans;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Administrator on 2018/5/1.
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private  String subject;

    private  String message;

    private Set<String> receivers;

}
