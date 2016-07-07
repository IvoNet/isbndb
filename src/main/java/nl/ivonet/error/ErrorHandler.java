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

package nl.ivonet.error;

import nl.ivonet.boundary.Keystats;

/**
 * @author Ivo Woltring
 */
public class ErrorHandler {
    private Keystats keystats;
    private String error;

    public void handle() {
        if (noError()) {
            return;
        }
        if (error.contains("Invalid api key")) {
            throw new IsbnInvalidApiKeyException(error);
        }
        if (error.contains("Unable to locate")) {
            throw new IsbndbNotFoundException(error);
        }
        if (error.contains("'query' or 'q' is a required parameter")) {
            throw new IsbndbEmptyQueryException(error);
        }
        if (error.contains("Daily request limit exceeded.")) {
            throw new IsbndbDailyLimitExceeded(error);
        }
        throw new IsbndbRuntimeException(error);
    }

    public boolean limitReached() {
        return (!noError() && error.contains("Daily request limit exceeded.")) || keystats.memberLimitReached();
    }

    public boolean noError() {
        return (error == null) || error.isEmpty();
    }
}
