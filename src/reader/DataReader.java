package reader;

import engine.MoveDirection;
import engine.State;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataReader {
    private static final String SEPARATOR = " ";

    private String fileName;
    private State inputState;

    public DataReader(String fileName) {
        this.fileName = fileName;
    }

    public void read() {
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(fileName))) {
            String[] firstLine = bufferedReader.readLine().split(SEPARATOR);
            byte dimensionY = Byte.parseByte(firstLine[0]);
            byte dimensionX = Byte.parseByte(firstLine[1]);

            byte[] initialValues = new byte[dimensionX * dimensionY];

            for (byte row = 0; row < dimensionY; row++) {
                String[] line = bufferedReader.readLine().split(SEPARATOR);

                for (byte column = 0; column < line.length; column++) {
                    byte value = Byte.parseByte(line[column]);

                    initialValues[row * dimensionX + column] = value;
                }
            }

            inputState = new State(dimensionX, dimensionY, initialValues, null, MoveDirection.NO_MOVE, 0);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find input file!");
        } catch (IOException e) {
            System.out.println("Can't read input file!");
        }
    }

    public State getInputState() {
        return inputState;
    }
}
