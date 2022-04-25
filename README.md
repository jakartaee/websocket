# Jakarta WebSocket

This repository contains the source for:

 - the Jakarta WebSocket [API](https://jakarta.ee/specifications/websocket/2.0/apidocs) (/api) - 
 - the Jakarta WebSocket [specification](https://jakarta.ee/specifications/websocket/2.0/websocket-spec-2.0.html) (/spec)


## Building

### API

Jakarta WebSocket API can be built by executing the following from the project root:

```
cd api
mvn clean package
```
The API jars can then be found in `/api/client/target` and `/api/server/target`.

### Specification

Jakarta Expression Language specification can be built by executing the following from the project root:

```
cd spec
mvn clean package
```
The zip archive containing the specification in PDF and HTML format can then be found in `/spec/target`.


## Making Changes

To make changes, fork this repository, make your changes, and submit a pull request.


## About Jakarta WebSocket

Jakarta WebSocket defines defines a set of Java APIs for the development of WebSocket applications.