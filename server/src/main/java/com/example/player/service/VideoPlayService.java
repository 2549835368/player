package com.example.player.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface VideoPlayService {
    public void play(int id, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
