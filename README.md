# FlyingChess

#### 浩东
## UI
### 跳转逻辑：
刚进游戏的页面会是activity_main.xml，然后登录后跳转到setting_layout，在这个主界面中设置跳转到activity_setting.xml，这里的设置可以后期再添加，简单难度就是电脑不会吃玩家的棋，而困难难度会。点多人游戏跳转到local_rooms，然后接下来就是网络部分的说明文档了。

****

#### 黄鹏
## Activity
每个Act与一个layout对应

### Main
目前只有一个注册按钮，点击调用qq的API进行用户登录，收集到用户信息后跳转到设置界面

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

### RefreshableView
用于下拉刷新

***
