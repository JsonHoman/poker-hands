package com.ravtech.poker;

import com.ravtech.poker.model.Round;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger("MyLogger");
    private static FileHandler fileHandler;

    static {
        try {
            // Configure the file handler
            fileHandler = new FileHandler("application.log", true); // true for append mode
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO); // Set the desired log level

            // shutdown hook to close the logger and file handler
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.removeHandler(fileHandler);
                fileHandler.close();
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void close() {
        fileHandler.close();
    }

    public static void main(String[] args) {
        Round.evaluateRoundWinner("Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH");
        Round.evaluateRoundWinner("Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S");
        Round.evaluateRoundWinner("Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH");
        Round.evaluateRoundWinner("Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH");
    }
}