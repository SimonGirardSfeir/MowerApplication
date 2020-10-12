package com.publicis.mowerapplication.services;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import com.publicis.mowerapplication.exceptions.IncorrectFileNameException;
import com.publicis.mowerapplication.model.Direction;
import com.publicis.mowerapplication.model.Mower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    void processFile() throws IOException, IncorrectFileNameException, IncorrectContentException {
        byte [] byteArray = mainService.processFile(file);

        InputStream inputStream = new ByteArrayInputStream(byteArray);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        assertEquals("1 3 N", reader.readLine());
        assertEquals("5 1 E", reader.readLine());
    }

    @Test
    void processFileWithWrongExtension() throws IOException {
        Path path = Paths.get("./src/test/resources/inputFile.txt");
        String name = "inputFile.js";
        String originalFileName = "inputFile.js";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);

        MultipartFile fileWithWrongExtension = new MockMultipartFile(name,
                originalFileName, contentType, content);

        assertThrows(IncorrectFileNameException.class, () -> mainService.processFile(fileWithWrongExtension));
    }

    @Test
    void processFileWithWrongFirstLine() throws IOException {
        Path path = Paths.get("./src/test/resources/firstLineWrong.txt");
        String name = "firstLineWrong.txt";
        String originalFileName = "firstLineWrong.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);

        MultipartFile fileWithWrongFirstLine = new MockMultipartFile(name,
                originalFileName, contentType, content);

        assertThrows(IncorrectContentException.class, () -> mainService.processFile(fileWithWrongFirstLine));
    }

    @Test
    void processFileWithNumberFormatException() throws IOException {
        Path path = Paths.get("./src/test/resources/numberFormatException.txt");
        String name = "numberFormatException.txt";
        String originalFileName = "numberFormatException.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);

        MultipartFile fileWithNumberFormatException = new MockMultipartFile(name,
                originalFileName, contentType, content);

        assertThrows(NumberFormatException.class, () -> mainService.processFile(fileWithNumberFormatException));
    }

    @Test
    void processFileWithWrongStartingPoint() throws IOException {
        Path path = Paths.get("./src/test/resources/wrongStartingPoint.txt");
        String name = "wrongStartingPoint.txt";
        String originalFileName = "wrongStartingPoint.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);

        MultipartFile fileWithWrongStartingPoint = new MockMultipartFile(name,
                originalFileName, contentType, content);

        assertThrows(IncorrectContentException.class, () -> mainService.processFile(fileWithWrongStartingPoint));
    }

    @Test
    void processFileWithWrongNumberOfLines() throws IOException {
        Path path = Paths.get("./src/test/resources/wrongNumberOfLines.txt");
        String name = "wrongNumberOfLines.txt";
        String originalFileName = "wrongNumberOfLines.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);

        MultipartFile fileWithWrongNumberOfLines = new MockMultipartFile(name,
                originalFileName, contentType, content);

        assertThrows(IncorrectContentException.class, () -> mainService.processFile(fileWithWrongNumberOfLines));
    }
    @Test
    void processFileWithStartingPointOutOfLawn() throws IOException {
        Path path = Paths.get("./src/test/resources/fileWithStartingPointOufOfLawn.txt");
        String name = "fileWithStartingPointOufOfLawn.txt";
        String originalFileName = "fileWithStartingPointOufOfLawn.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(path);

        MultipartFile fileWithStartingPointOufOfLawn = new MockMultipartFile(name,
                originalFileName, contentType, content);

        assertThrows(IncorrectContentException.class, () -> mainService.processFile(fileWithStartingPointOufOfLawn));
    }

    @Test
    void getLines() throws IOException {
        List<String> list = mainService.getLines(file);

        assertEquals(5, list.size());
        assertEquals("5 5", list.get(0));
        assertEquals("1 2 N", list.get(1));
        assertEquals("GAGAGAGAA", list.get(2));
        assertEquals("3 3 E", list.get(3));
        assertEquals("AADAADADDA", list.get(4));

    }

    @Test
    void moveMower() throws IncorrectContentException {
        Mower mower = new Mower(1, 2, Direction.NORTH);
        mainService.moveMower(mower, "GAGAGAGAA", 5, 5);

        assertEquals(1, mower.getX());
        assertEquals(3, mower.getY());
        assertEquals(Direction.NORTH, mower.getDirection());
    }

    @Test
    void getDirectionFromString() throws IncorrectContentException {

        assertEquals(Direction.SOUTH, mainService.getDirectionFromString("S"));
        assertEquals(Direction.EAST, mainService.getDirectionFromString("E"));
        assertEquals(Direction.WEST, mainService.getDirectionFromString("W"));
        assertEquals(Direction.NORTH, mainService.getDirectionFromString("N"));
        assertThrows(IncorrectContentException.class, () -> mainService.getDirectionFromString("T"));
    }
}