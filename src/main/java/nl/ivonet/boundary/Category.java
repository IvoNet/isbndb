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

import java.util.List;

/**
 * @author Ivo Woltring
 */
public class Category {
    private String categoryId;
    private String name;
    private String nameLatin;
    private String summary;
    private String parentId;
    private List<String> childIds;

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getName() {
        return this.name;
    }

    public String getNameLatin() {
        return this.nameLatin;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getParentId() {
        return this.parentId;
    }

    public List<String> getChildIds() {
        return this.childIds;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId='" + categoryId + '\'' + ", name='" + name + '\'' + ", nameLatin='" + nameLatin
               + '\'' + ", summary='" + summary + '\'' + ", parentId='" + parentId + '\'' + ", childIds=" + childIds
               + '}';
    }
}
