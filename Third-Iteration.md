
# 第三次迭代文档

### 团队成员及分工
成员姓名 | 分工 | 比重
-------- | ------ | --------
宋凯伦  | 完善代码 | 20%
吴岱岚 | 收集资料| 20%
杨骏轩  | 撰写博客，画图 | 20%
黄栋 | 撰写需求文档 | 20%
孔祥龙 | 制作ppt | 20%
# 系统设计
### 1. 更新类图
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E7%B1%BB%E5%9B%BEnew.png)
Word类：每个单词的基本信息
Record类：每个对应单词记忆记录的基本信息
WordManager类：用于对单词进行查找，随机抽取
RecordManager类：保存对应单词的记忆记录，随机抽取记录等
ReciteManager类：获取下一个单词，保存记录使运行流程正常进行。


### 2. 更新用例图
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E7%94%A8%E5%86%B5%E5%9B%BEnew.png)




### 3. 更新时序图
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E9%A1%BA%E5%BA%8F%E5%9B%BEnew.png)

# UI设计
### 1.已完成部分展示
##### 1.1主界面更新
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E4%B8%BB%E7%95%8C%E9%9D%A2.PNG)

 

##### 1.2 单词背诵
可通过键盘输入单词，退格键删除最后一个字母，ESC键清空输入，回车键确定，正确拼写后点击回车进入下一个单词，错误拼写回车后可以看到正确拼写
 
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E5%8D%95%E8%AF%8D%E8%83%8C%E8%AF%B5.PNG)
当单词拼写正确时，单词会以蓝色显示。
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E6%AD%A3%E7%A1%AE.PNG)
 
 单词拼写错误时，会以红色显示正确拼写


##### 1.2 单词选择
点击单词的正确中文意思，点击正确后继续点击可查看具体单词释义，点击错误单词将以红色显示直到点击正确选项为止。
 
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E5%8D%95%E8%AF%8D%E8%83%8C%E8%AF%B5.PNG)
当单词点击正确时，会以蓝色显示。
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/time1/%E6%AD%A3%E7%A1%AE.PNG)
 当单词点击错误时，红色显示。


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
