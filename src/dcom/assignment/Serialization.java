package dcom.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {
    private Serialization() {}

    public static void serialize(Object obj, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(File file, Class<T> type) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            return (T) obj; // or type.cast(obj);
        }
    }
}