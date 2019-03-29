module jakarta.websocket.server {
	uses javax.websocket.server.ServerEndpointConfig.Configurator;
	requires transitive jakarta.websocket.client;

	exports javax.websocket.server;
}
