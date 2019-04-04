ChannelHandler 开始回调方法的执行顺序为
1. handlerAdded() ----channel新连接之后，addlast之后回调表示成功添加handler
2. channelRegistered()----说明逻辑关系处理器与某个nio线程绑定了
3. channelActive()----所有的链准备好并且绑定NIO线程后，算是完全机会，回调此方法
4. channelRead()----收到数据，表示有数据可读
5. channelReadComplete()----每次读取完整的数据后，回调该方法，说明数据读取完了

关闭客户端后的回调顺序，
1. channelInactive()----连接关闭
2. channelUnregistered()----连接关闭说明逻辑链已经不用了，对应的NIO线程取消注册，移除掉
3. handlerRemoved()----所有的业务处理器都移除掉