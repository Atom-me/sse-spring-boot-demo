
# 服务器向浏览器推送信息，除了 WebSocket，还有一种方法：Server-Sent Events
# Server-Sent Events in Spring Boot

This repository contains 
- an example of how Server-Sent Events (SSE) can be implemented in Spring Boot (`sse-demo-server`)
- an example of how the events can be consumed (`sse-demo-html-client`)



To use SSE, the client must create an EventSource object and specify the URL of the server-side script that will generate the events. 
The server-side script can then use the Content-Type: text/event-stream header to send a stream of events to the client. 
The client can listen to these events and perform actions in response to them.

SSE is an efficient technology for real-time communication because it allows 
the server to send data to the client without the client having to send a request. 
This reduces the overhead of frequent HTTP requests and can save battery life on mobile devices.