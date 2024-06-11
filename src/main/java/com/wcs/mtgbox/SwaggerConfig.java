package com.wcs.mtgbox;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition (info =
@Info(
        title = "MTG Box",
        version = "1.0",
        description = "MTG Box is a platform for managing Magic: The Gathering card collections. Users can register, log in, and build their own collections. The app allows users to search for cards from other users and propose card trades."
)
)
@Configuration
public class SwaggerConfig {




}

