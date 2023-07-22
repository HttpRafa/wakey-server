package de.rafael.wakey.utils;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael K.
 * @since 22/07/2023
 */

public class ReflectionUtils {

    @SneakyThrows
    public static Class<?> @NotNull [] classesFromPackages(ClassLoader classLoader, String @NotNull ... packages) {
        ClassPath classPath = ClassPath.from(classLoader);
        List<ClassPath.ClassInfo> classInfos = new ArrayList<>();
        for (String path : packages) {
            classInfos.addAll(classPath.getTopLevelClassesRecursive(path));
        }
        Class<?>[] classes = new Class<?>[classInfos.size()];
        for (int i = 0; i < classInfos.size(); i++) {
            classes[i] = Class.forName(classInfos.get(i).getName(), true, classLoader);
        }
        return classes;
    }

}
