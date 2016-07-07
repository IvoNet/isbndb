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

package nl.ivonet.io;

import nl.ivonet.error.NoMoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//TODO: 28-06-2016 Reload on change...

/**
 * Reads a config file with ISBN API keys and provides convenience methods
 * for getting to them.
 *
 * The config file should only have 1 api key per line.
 *
 * @author Ivo Woltring
 */
public class ApiKeyResource {
    private static final Logger LOG = LoggerFactory.getLogger(ApiKeyResource.class);
    private final List<String> keys;

    public ApiKeyResource() {
        List<String> result;
        final String location = ApiKeyResource.class.getResource("/isbndb_keys.txt")
                                                    .toExternalForm();
        try (final InputStreamReader in = new InputStreamReader(
                new URL(location).openStream()); final BufferedReader br = new BufferedReader(in)) {
            result = br.lines()
                       .parallel()
                       .map(String::trim)
                       .filter(p -> !p.isEmpty())
                       .collect(Collectors.toList());
        } catch (final IOException e) {
            result = Collections.emptyList();
            LOG.warn("You probably didn't create an [isbndb_keys.txt] file in the src/main/resources folder with API "
                     + "keys for isbndb.com in there (one key per line)");
        }
        this.keys = result;
    }

    public synchronized boolean hasMore() {
        return !keys.isEmpty();
    }

    public synchronized String next() {
        if (hasMore()) {
            final String key = keys.get(0);
            keys.remove(0);
            return key;
        }
        throw new NoMoreException("should not be here");
    }

}
