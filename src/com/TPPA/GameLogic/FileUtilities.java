package com.TPPA.GameLogic;

import java.io.*;

/**
 * Created by Lídia on 02/06/2017.
 */
public class FileUtilities {

    public static GameStateController retrieveGameFromFile(File loadFile) throws ClassNotFoundException, IOException {
        GameStateController loadedGame;
        ObjectInputStream readFile = null;

        try {
            readFile = new ObjectInputStream(new FileInputStream(loadFile));

            loadedGame = (GameStateController) readFile.readObject();
        } finally {
            if (readFile != null)
                readFile.close();
        }

        return loadedGame;
    }

    public static void saveGameToFile(Object o, File saveFile) throws IOException {
        ObjectOutputStream writeFile = null;

        try {
            writeFile = new ObjectOutputStream(new FileOutputStream(saveFile));
            writeFile.writeObject(o);
        } finally {
            if (writeFile != null)
                writeFile.close();
        }
    }
}
