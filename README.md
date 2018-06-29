# FlyingChess

## Activity
每个Act与一个layout对应

### Main
目前只有一个注册按钮，点击调用qq的API，收集到用户信息后跳转到设置界面

### setting
 设置界面，目前只有“多人游戏”按钮是可以用的，跳转到房间列表


### localAct
房间列表，用listView存储房间，下拉可以刷新房间列表  
还有一个创建房间按钮，点击跳转到创建房间界面  

### createroomAct
创建房间界面，输入房间名，点击“创建”跳转到房间界面

### roomAct
房间内部，点击开始游戏跳转到游戏界面

### gameAct
游戏界面，游戏的主题功能在此完成

## Service
### SocketService
目前这个类是完整的，但调用的时候有问题，正在找解决方案

## class
### Room
房间类，未完成

### Refresh ableView
用于下拉刷新
