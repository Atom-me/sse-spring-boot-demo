
# 服务器向浏览器推送信息，除了 WebSocket，还有一种方法：Server-Sent Events

## 一、SSE 的本质
严格地说，HTTP 协议无法做到服务器主动推送信息。但是，有一种变通方法，就是服务器向客户端声明，接下来要发送的是流信息（streaming）。

也就是说，发送的不是一次性的数据包，而是一个数据流，会连续不断地发送过来。这时，客户端不会关闭连接，会一直等着服务器发过来的新的数据流，视频播放就是这样的例子。本质上，这种通信就是以流信息的方式，完成一次用时很长的下载。

SSE 就是利用这种机制，使用流信息向浏览器推送信息。它基于 HTTP 协议，目前除了 IE/Edge，其他浏览器都支持。

## 二、SSE 的特点
SSE 与 WebSocket 作用相似，都是建立浏览器与服务器之间的通信渠道，然后服务器向浏览器推送信息。

总体来说，
    WebSocket 更强大和灵活。因为它是全双工通道，可以双向通信；
    SSE 是单向通道，只能服务器向浏览器发送，因为流信息本质上就是下载。如果浏览器向服务器发送信息，就变成了另一次 HTTP 请求。



但是，SSE 也有自己的优点。

- SSE 使用 HTTP 协议，现有的服务器软件都支持。WebSocket 是一个独立协议。
- SSE 属于轻量级，使用简单；WebSocket 协议相对复杂。
- SSE 默认支持断线重连，WebSocket 需要自己实现。
- SSE 一般只用来传送文本，二进制数据需要编码后传送，WebSocket 默认支持传送二进制数据。
- SSE 支持自定义发送的消息类型。
因此，两者各有特点，适合不同的场合。


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