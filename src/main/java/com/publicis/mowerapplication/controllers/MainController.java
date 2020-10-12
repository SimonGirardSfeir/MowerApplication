package com.publicis.mowerapplication.controllers;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import com.publicis.mowerapplication.exceptions.IncorrectFileNameException;
import com.publicis.mowerapplication.services.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping
    public @ResponseBody byte[] postFile(@RequestParam("file") MultipartFile file) throws IOException, IncorrectFileNameException, IncorrectContentException {
        return mainService.processFile(file);
    }
}
