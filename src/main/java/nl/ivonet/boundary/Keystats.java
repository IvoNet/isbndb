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

package nl.ivonet.boundary;

/**
 * @author Ivo Woltring
 */
public class Keystats {
    private int keyUseRequests;
    private String keyLimit;
    private int freeUseLimit;
    private int memberUseRequests;
    private String keyId;
    private int dailyMaxPayUses;
    private int keyUseGranted;
    private int memberUseGranted;

    public String getKeyId() {
        return this.keyId;
    }

    public int getKeyUseRequests() {
        return this.keyUseRequests;
    }

    public String getKeyLimit() {
        return this.keyLimit;
    }

    public int getFreeUseLimit() {
        return this.freeUseLimit;
    }

    public int getMemberUseRequests() {
        return this.memberUseRequests;
    }

    public int getDailyMaxPayUses() {
        return this.dailyMaxPayUses;
    }

    public int getKeyUseGranted() {
        return this.keyUseGranted;
    }

    public int getMemberUseGranted() {
        return this.memberUseGranted;
    }

    /**
     * @return true if total member limit has been reached
     */
    public boolean memberLimitReached() {
        return (freeUseLimit - memberUseGranted) == 0;
    }

    /**
     * @return true if current key limit has been reached.
     */
    public boolean keyLimitReached() {
        return (freeUseLimit - keyUseGranted) == 0;
    }


    @Override
    public String toString() {
        return "Keystats{" + "keyUseRequests=" + keyUseRequests + ", keyLimit='" + keyLimit + '\'' + ", freeUseLimit="
               + freeUseLimit + ", memberUseRequests=" + memberUseRequests + ", dailyMaxPayUses=" + dailyMaxPayUses
               + ", keyUseGranted=" + keyUseGranted + ", memberUseGranted=" + memberUseGranted + '}';
    }
}
