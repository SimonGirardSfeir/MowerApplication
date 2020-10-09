package com.publicis.mowerapplication.services;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import com.publicis.mowerapplication.exceptions.IncorrectFileNameException;
import com.publicis.mowerapplication.model.Direction;
import com.publicis.mowerapplication.model.Mower;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MainService {

    public InputStream processFile(MultipartFile file) throws IOException, IncorrectFileNameException, IncorrectContentException {

        if(!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")){
            throw new IncorrectFileNameException("File with wrong format.");
        }

        List<String> linesFromFile = getLines(file);
        if(linesFromFile.size() %2 == 0) {
            throw new IncorrectContentException("Wrong number of lines.");
        }
        List<Mower> mowers = new ArrayList<>();

        String[] arrayDimensionLawn = linesFromFile.get(0).split(" ");

        if(arrayDimensionLawn.length != 2) {
            throw new IncorrectContentException("The line concerning the coordinates of the upper right corner of the lawn is incorrect.");
        }

        int xMax = Integer.parseInt(arrayDimensionLawn[0]);
        int yMax = Integer.parseInt(arrayDimensionLawn[1]);

        for(int i = 1; i < linesFromFile.size(); i+=2) {
            String[] arrayStartingPosition = linesFromFile.get(i).split(" ");

            if(arrayStartingPosition.length != 3)   {
                throw new IncorrectContentException("The line concerning the coordinates of one of the starting points is incorrect.");
            }
            int x = Integer.parseInt(arrayStartingPosition[0]);
            int y = Integer.parseInt(arrayStartingPosition[1]);
            Direction direction = getDirectionFromString(arrayStartingPosition[2]);
            Mower mower = new Mower(x, y, direction);

            moveMower(mower, linesFromFile.get(i+1), xMax, yMax);

            mowers.add(mower);
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(Mower m : mowers) {
            stringBuilder.append(m.toString());
            stringBuilder.append(System.getProperty("line.separator"));
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(stringBuilder.toString().getBytes());

        byte[] bytes = byteArrayOutputStream.toByteArray();
        return new ByteArrayInputStream(bytes);
    }

    public List<String> getLines(MultipartFile file) throws IOException {
        InputStream inputStreamInitial = file.getInputStream();

        return new BufferedReader(new InputStreamReader(inputStreamInitial, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());
    }

    public Direction getDirectionFromString(String s) throws IncorrectContentException {
        if(s.charAt(0) == 'N') {
            return Direction.NORTH;
        } else if (s.charAt(0) == 'S') {
            return Direction.SOUTH;
        } else if (s.charAt(0) == 'E') {
            return Direction.EAST;
        } else if (s.charAt(0) == 'W') {
            return Direction.WEST;
        } else {
            throw new IncorrectContentException("The data concerning the cardinal point is incorrect.");
        }
    }

    public void moveMower(Mower mower, String line, int xMax, int yMax) throws IncorrectContentException {

        char [] instructions = line.toCharArray();

        for(char c : instructions) {
            mower.move(c, xMax, yMax);
        }

    }
}
