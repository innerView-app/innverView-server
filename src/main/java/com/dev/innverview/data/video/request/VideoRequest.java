package com.dev.innverview.data.video.request;

import com.dev.innverview.validation.UUID;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.Parameter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRequest {
    @Parameter(in = ParameterIn.PATH)
    @UUID(message = "invalid video id !")
    String id;
}
