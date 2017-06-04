package com.TPPA.GameLogic;

import java.io.*;

/**
 * Created by Lídia on 02/06/2017.
 */
public class FileUtilities {

    public static Object retrieveGameFromFile(File loadFile) throws ClassNotFoundException, IOException {
        ObjectInputStream readFile = null;

        try {
            readFile = new ObjectInputStream(new FileInputStream(loadFile));

            return readFile.readObject();
        } finally {
            if (readFile != null)
                readFile.close();
        }
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
