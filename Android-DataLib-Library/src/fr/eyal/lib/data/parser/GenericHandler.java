/*
 * Copyright (C) 2012 Eyal LEZMY (http://www.eyal.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.eyal.lib.data.parser;

import fr.eyal.lib.data.model.ResponseBusinessObject;

/**
 * {@link GenericHandler} defines a class who parses a result and provide it through {@link ResponseBusinessObject}
 * 
 * @author Eyal LEZMY
 */
public interface GenericHandler {

    /**
     * Get the parsed data
     * 
     * @return the parsed data.
     * */
    public abstract ResponseBusinessObject getParsedData();

    /**
     * Parse the data
     * 
     * @param content returns the parsing's result
     */
    public abstract void parse(Object content);
}
