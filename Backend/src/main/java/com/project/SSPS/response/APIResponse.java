package com.project.SSPS.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class APIResponse<T> {

    @Builder.Default
    int code = 1000;

    String message;
    T data;
}
