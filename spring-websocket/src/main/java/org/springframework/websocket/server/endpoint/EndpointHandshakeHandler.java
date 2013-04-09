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

package org.springframework.websocket.server.endpoint;

import javax.websocket.Endpoint;

import org.springframework.beans.BeanUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ClassUtils;
import org.springframework.websocket.WebSocketHandler;
import org.springframework.websocket.server.AbstractHandshakeHandler;
import org.springframework.websocket.server.HandshakeHandler;
import org.springframework.websocket.support.WebSocketHandlerEndpoint;


/**
 * A {@link HandshakeHandler} for use with standard Java WebSocket.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
public class EndpointHandshakeHandler extends AbstractHandshakeHandler {

	private static final boolean tomcatWebSocketPresent = ClassUtils.isPresent(
			"org.apache.tomcat.websocket.server.WsHandshakeRequest", EndpointHandshakeHandler.class.getClassLoader());

	private final WebSocketRequestUpgradeStrategy upgradeStrategy;


	public EndpointHandshakeHandler(WebSocketHandler webSocketHandler) {
		super(webSocketHandler);
		this.upgradeStrategy = createRequestUpgradeStrategy();
	}

	public EndpointHandshakeHandler(Class<? extends WebSocketHandler> handlerClass) {
		super(handlerClass);
		this.upgradeStrategy = createRequestUpgradeStrategy();
	}

	private static WebSocketRequestUpgradeStrategy createRequestUpgradeStrategy() {
		String className;
		if (tomcatWebSocketPresent) {
			className = "org.springframework.websocket.server.endpoint.TomcatRequestUpgradeStrategy";
		}
		else {
			throw new IllegalStateException("No suitable EndpointRequestUpgradeStrategy");
		}
		try {
			Class<?> clazz = ClassUtils.forName(className, EndpointHandshakeHandler.class.getClassLoader());
			return (WebSocketRequestUpgradeStrategy) BeanUtils.instantiateClass(clazz.getConstructor());
		}
		catch (Throwable t) {
			throw new IllegalStateException("Failed to instantiate " + className, t);
		}
	}

	@Override
	public void doHandshakeInternal(ServerHttpRequest request, ServerHttpResponse response, String protocol)
			throws Exception {

		logger.debug("Upgrading HTTP request");
		Endpoint endpoint = new WebSocketHandlerEndpoint(getWebSocketHandler());
		this.upgradeStrategy.upgrade(request, response, protocol, new EndpointRegistration("/dummy", endpoint));
	}

}