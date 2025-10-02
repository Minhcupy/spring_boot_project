package com.springboot.springbootproject.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.springboot.springbootproject.dto.request.AuthenticationRequest;
import com.springboot.springbootproject.dto.request.IntrospectRequest;
import com.springboot.springbootproject.dto.request.LogoutRequest;
import com.springboot.springbootproject.dto.request.RefreshRequest;
import com.springboot.springbootproject.dto.response.AuthenticationResponse;
import com.springboot.springbootproject.dto.response.IntrospectResponse;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
