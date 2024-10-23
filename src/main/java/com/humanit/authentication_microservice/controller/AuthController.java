package com.humanit.authentication_microservice.controller;

import com.humanit.authentication_microservice.model.SignInRequest;
import com.humanit.authentication_microservice.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Operation(summary = "Sign in a user and generate a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, invalid credentials")
    })
    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInRequest request) {
        return JwtService.generateToken(request.getUsername());

    }

    @Operation(summary = "Validate a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "401", description = "Token is invalid or expired")
    })
    @PostMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(jwtService.validateTokenResponse(authorizationHeader));
    }


}


