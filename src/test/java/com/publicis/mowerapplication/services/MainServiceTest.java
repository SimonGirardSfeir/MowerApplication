package com.publicis.mowerapplication.services;

import com.publicis.mowerapplication.model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MainServiceTest {

    MultipartFile file;

    MainService mainService;

    @BeforeEach
    void setUp() throws IOException {

        mainService = new MainService();

        Path path = Paths.get("./src/test/resources/inputFile.txt");
        String name = "inputFile.txt";
        String originalFileName = "inputFile.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);

        file = new MockMultipartFile(name,
                originalFileName, contentType, content);
    }

    @Test
    void processFile() throws IOException {
        InputStream inputStream = mainService.processFile(file);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        assertEquals("1 3 N", reader.readLine());
        assertEquals("5 1 E", reader.readLine());
    }

    @Test
    void getDirectionFromString() {

        assertEquals(Direction.SOUTH, mainService.getDirectionFromString("S"));
        assertEquals(Direction.EAST, mainService.getDirectionFromString("E"));
        assertEquals(Direction.WEST, mainService.getDirectionFromString("W"));
        assertEquals(Direction.NORTH, mainService.getDirectionFromString("N"));
        assertNull(mainService.getDirectionFromString("A"));
    }
}