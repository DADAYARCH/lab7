package client.utils;

import client.io.OutputHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class PrettyPrint {
    OutputHandler output;

    public PrettyPrint(OutputHandler output) {
        this.output = output;
    }

    public void print(Object object) {
        print(object, "");
    }

    public void print(Object object, String prefix) {
        if (isMap(object)) {
            printMap((Map<?, ?>) object, prefix);
        } else if (isCollection(object)) {
            printCollection((Collection<?>) object, prefix);
        } else {
            printObject(object, prefix);
        }
    }

    protected void printMap(Map<?, ?> map, String prefix) {
        for (Entry<?, ?> entry : map.entrySet()) {
            if (isPrintableObject(entry.getValue())) {
                printKeyValue(entry.getKey(), entry.getValue(), prefix);
            } else {
                printKey(entry.getKey(), prefix);
                print(entry.getValue(), nextPrefix(prefix));
            }
        }
    }

    protected void printCollection(Collection<?> collection, String prefix) {
        for (Object element : collection) {
            if (isPrintableObject(element)) {
                printArrayElement(element, prefix);
            } else {
                printArrayKey(prefix);
                print(element, nextPrefix(prefix));
            }
        }
    }

    protected void printObject(Object object, String prefix) {
        if (object != null) {
            Class<?> cls = object.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                try {
                    Method fieldGetter = cls.getMethod(getFieldGetterName(fieldName));
                    Object value = fieldGetter.invoke(object);
                    if (isPrintableObject(value)) {
                        printKeyValue(fieldName, value, prefix);
                    } else {
                        printKey(fieldName, prefix);
                        print(value, nextPrefix(prefix));
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
                }
            }
        }
    }

    protected void printKeyValue(Object key, Object value, String prefix) {
        output.alwaysPrintln(prefix + key + ": " + value);
    }

    protected void printKey(Object key, String prefix) {
        output.alwaysPrintln(prefix + key + ':');
    }

    protected void printArrayKey(String prefix) {
        output.alwaysPrintln(prefix + '-');
    }

    protected void printArrayElement(Object element, String prefix) {
        output.alwaysPrintln(prefix + "- " + element);
    }

    protected String nextPrefix(String prefix) {
        return prefix + '\t';
    }

    protected String getFieldGetterName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    protected boolean isMap(Object object) {
        return object instanceof Map;
    }

    protected boolean isCollection(Object object) {
        return object instanceof Collection;
    }

    protected boolean isEnum(Object object) {
        return object != null && object.getClass().isEnum();
    }

    protected boolean isPrimitive(Object object) {
        return object != null && object.getClass().isPrimitive();
    }

    protected boolean isPrimitiveWrapper(Object object) {
        if (object != null) {
            String type = object.getClass().getTypeName();
            return type.startsWith("java.lang.");
        } else {
            return false;
        }
    }

    protected boolean isDateObject(Object object) {
        return object instanceof Date;
    }

    protected boolean isPrintableObject(Object object) {
        return isEnum(object) || isPrimitive(object) || isPrimitiveWrapper(object) || isDateObject(object) || object == null;
    }
}
