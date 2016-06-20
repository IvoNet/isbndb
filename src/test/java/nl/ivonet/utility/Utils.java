/*
 * Copyright 2016 Ivo Woltring <WebMaster@ivonet.nl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.ivonet.utility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("UtilityClass")
public final class Utils {

    private Utils() {
    }

    /**
     * The most simple cdi like method.
     *
     * @param injectable the object you want to inject something in
     * @param fieldname  the fieldname to inject to
     * @param value      the value to assign to the fieldname
     */
    public static void injectField(final Object injectable, final String fieldname, final Object value) {
        try {
            final Field field = injectable.getClass()
                                          .getDeclaredField(fieldname);
            final boolean origionalValue = field.isAccessible();
            field.setAccessible(true);
            field.set(injectable, value);
            field.setAccessible(origionalValue);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Get a filname from the recourse folder.
     *
     * @param filename the filename to get in src/test/resources
     * @return the absolute path to the filename
     */
    public static String getFileResource(final String filename) {
        String abspath = new File(".").getAbsolutePath();
        abspath = abspath.substring(0, abspath.length() - 1);
        return new File(abspath + "src/test/resources/" + filename).getAbsolutePath();
    }

    /**
     * The location of project/target
     *
     * @return string representation of the target folder
     */
    public static String getTargetLocation() {
        String abspath = new File(".").getAbsolutePath();
        abspath = abspath.substring(0, abspath.length() - 1);
        return new File(abspath + "target/").getAbsolutePath();
    }


    public static String readTestResourceAsString(final String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(getFileResource(filename))));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
