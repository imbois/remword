
# 项目设计文档


### 团队成员及分工
成员姓名 | 分工 | 比重
-------- | ------ | --------
宋凯伦  | 完善代码 | 20%
吴岱岚 | 收集资料| 20%
杨骏轩  | 撰写博客，画图 | 20%
黄栋 | 撰写需求文档 | 20%
孔祥龙 | 制作ppt | 20%
# 系统设计
### 1. 类图
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E7%B1%BB%E5%9B%BEnew.png)
Word类：每个单词的基本信息
Record类：每个对应单词记忆记录的基本信息
WordManager类：用于对单词进行查找，随机抽取
RecordManager类：保存对应单词的记忆记录，随机抽取记录等
ReciteManager类：获取下一个单词，保存记录使运行流程正常进行。


### 2. 用例图
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E7%94%A8%E5%86%B5%E5%9B%BEnew.png)

##### 用户用例


- 单词背诵：用户可通过单词学习进行新单词的学习记忆，通过单词复习根据艾宾浩斯曲线进行多阶段重复复习。
*  词库设置：可以选择当前词库，或导入词库，导出词库。
+ 查看记录：查看当前已记忆单词记录，包括记忆时间，艾宾浩斯时间阶段和陌生度。


### 3. 时序图
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E9%A1%BA%E5%BA%8F%E5%9B%BEnew.png)
用户首先选择词库，后根据显示的单词释义输入单词，根据输入单词正确与否提示用户并加入记录中以供下次复习，且显示下一个单词的释义继续学习。

# UI设计
### 1.已完成部分展示
##### 1.1主界面
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E4%B8%BB%E7%95%8C%E9%9D%A2.PNG)
软件的默认词库为TOFEL，第一部分是单词拼写区域。第二部分是音标区域，用于显示该单词的音标。第三部分为释义部分，用于显示单词的解释。第四部分为词库信息，显示词库的名称等相关信息。
 

##### 1.2 单词背诵
可通过键盘输入单词，退格键删除最后一个字母，ESC键清空输入，回车键确定，“`”键跳过该单词。
 
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E5%8D%95%E8%AF%8D%E8%83%8C%E8%AF%B5.PNG)
当单词拼写正确时，单词会以蓝色显示。
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E6%AD%A3%E7%A1%AE.PNG)
 
当单词拼写错误时，源单词会以红色显示，该单词的陌生度加1

 ![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E9%94%99%E8%AF%AF.PNG)

##### 2.第一次迭代与预期比较
第一次迭代结果与与其相比，实现的功能有：
背单词的主体功能，能进行“背”这一功能；
将单词库导入，可以进行词库选择；
将记忆结果记录，并按照艾宾浩斯遗忘规律来进行复习；

未实现功能有：
用户登录界面，进行用户注册与登录；
夜间模式；
另外两种记忆模式；

##### 3.总结
这两周的工作完成得不错，初步完成了目标，预计能在截止前将所有内容完成。
当然这次的作品并不代表成品，可能还有很大的调整，各位尽情期待吧！
