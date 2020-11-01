package com.publicis.mowerapplication.services;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import com.publicis.mowerapplication.exceptions.IncorrectFileNameException;
import com.publicis.mowerapplication.model.Direction;
import com.publicis.mowerapplication.model.Lawn;
import com.publicis.mowerapplication.model.Mower;
import com.publicis.mowerapplication.model.RectangleLawn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
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

    public byte [] processFile(MultipartFile file) throws IOException, IncorrectFileNameException, IncorrectContentException {

        if(!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")){
            throw new IncorrectFileNameException("File with wrong format.");
        }

        List<String> linesFromFile = getLines(file);

        Lawn lawn = lawnFactory(linesFromFile.get(0));

        linesFromFile.remove(0);

        List<Mower> mowers = generateMowers(lawn, linesFromFile);

        StringBuilder stringBuilder = new StringBuilder();

        for(Mower m : mowers) {
            stringBuilder.append(m.toString());
            stringBuilder.append(System.getProperty("line.separator"));
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(stringBuilder.toString().getBytes());

        return  byteArrayOutputStream.toByteArray();
    }

    public List<String> getLines(MultipartFile file) throws IOException, IncorrectContentException {
        InputStream inputStreamInitial = file.getInputStream();
        List<String> lines = new BufferedReader(new InputStreamReader(inputStreamInitial, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());

        if(lines.size() %2 == 0) {
            throw new IncorrectContentException("Wrong number of lines.");
        }

        return lines;
    }

    public Lawn lawnFactory(String line) throws IncorrectContentException {
        String[] arrayDimensionLawn = line.split(" ");

        if(arrayDimensionLawn.length == 2) {
            return new RectangleLawn(arrayDimensionLawn);
        } else {
            throw new IncorrectContentException("The line concerning the coordinates of the upper right corner of the lawn is incorrect.");
        }
    }

    public List<Mower> generateMowers(Lawn lawn, List<String> instructions) throws IncorrectContentException {
        List<Mower> mowers = new ArrayList<>();

        for(int i = 0; i < instructions.size(); i+=2) {
            String[] arrayStartingPosition = instructions.get(i).split(" ");

            if(arrayStartingPosition.length != 3)   {
                throw new IncorrectContentException("The line concerning the coordinates of one of the starting points is incorrect.");
            }
            lawn.setInitialPosition(instructions.get(i));

            Direction direction = getDirectionFromString(arrayStartingPosition[2]);
            Mower mower = new Mower(lawn, direction);

            moveMower(mower, instructions.get(i+1), lawn);

            mowers.add(mower);
        }

        return mowers;
    }

    public Direction getDirectionFromString(String s) throws IncorrectContentException {
        return Direction.getDirectionFromString(s);
    }

    public void moveMower(Mower mower, String line, Lawn lawn) throws IncorrectContentException {
        char [] instructions = line.toCharArray();

        for(char c : instructions) {
            mower.move(c, lawn);
        }
    }
}
