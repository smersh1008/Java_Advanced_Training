package com.epam.trainning.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecretInfo {
    private String secret;
    private long creationTime;
}
