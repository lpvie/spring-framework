/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.websocket;

import java.io.InputStream;

/**
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
public class WebSocketHandlerAdapter implements WebSocketHandler {

	@Override
	public void newSession(WebSocketSession session) throws Exception {
	}

	@Override
	public void handleTextMessage(WebSocketSession session, String message) throws Exception {
	}

	@Override
	public void handleBinaryMessage(WebSocketSession session, InputStream message) throws Exception {
	}

	@Override
	public void handleException(WebSocketSession session, Throwable exception) {
	}

	@Override
	public void sessionClosed(WebSocketSession session, int statusCode, String reason) throws Exception {
	}

}