import java.lang.reflect.*;
import java.util.*;

public class SimpleGSON {
    public static String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) return "null";

        Class<?> clazz = obj.getClass();

        // Примитивы и строки
        if (clazz == String.class) return "\"" + obj + "\"";
        if (clazz.isPrimitive() || Number.class.isAssignableFrom(clazz) || clazz == Boolean.class)
            return obj.toString();

        // Массивы
        if (clazz.isArray()) {
            int length = Array.getLength(obj);
            List<String> items = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                Object element = Array.get(obj, i);
                items.add(toJson(element)); // рекурсия
            }
            return "[" + String.join(", ", items) + "]";
        }

        // Коллекции
        if (obj instanceof Collection<?>) {
            List<String> items = new ArrayList<>();
            for (Object element : (Collection<?>) obj) {
                items.add(toJson(element));
            }
            return "[" + String.join(", ", items) + "]";
        }

        // Объекты
        StringBuilder json = new StringBuilder("{");
        Field[] fields = clazz.getDeclaredFields();
        List<String> pairs = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            pairs.add("\"" + field.getName() + "\": " + toJson(value));
        }

        json.append(String.join(", ", pairs));
        json.append("}");
        return json.toString();
    }
}
