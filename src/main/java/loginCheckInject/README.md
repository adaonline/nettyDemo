使用 channelHandler 的热插拔实现客户端身份校验

我们之前的登陆还有发送协议信息的 login包里，不通过登陆校验，服务端仍然
可以接受协议并且返回

这里实现了
1. 每次登陆校验，不通过则断开连接
2. 为了不必要每次都登陆校验，进行头一次连接验证后，就删除验证handler（具体操作在AuthHandler中）


如果去掉LoginRequestHandler中的    ctx.channel().writeAndFlush(loginResponsePacket);不给客户端返回对应的登录响应，
发送消息时候就会直接断开连接