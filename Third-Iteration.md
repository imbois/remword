
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
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/ver/%E4%B8%BB%E7%95%8C%E9%9D%A2.PNG)

 

##### 1.2 单词背诵
可通过键盘输入单词，退格键删除最后一个字母，ESC键清空输入，回车键确定，正确拼写后点击回车进入下一个单词，错误拼写回车后可以看到正确拼写
 
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/ver/%E9%BB%98%E5%86%99%E6%AD%A3%E7%A1%AE.PNG)

 当单词拼写正确时，单词会以蓝色显示。


![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/ver/%E9%BB%98%E5%86%99%E9%94%99%E8%AF%AF.PNG)
 
 单词拼写错误时，会以红色显示正确拼写


##### 1.3 单词选择
点击单词的正确中文意思，点击正确后继续点击可查看具体单词释义，点击错误单词将以红色显示直到点击正确选项为止。
 
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/ver/%E9%80%89%E6%8B%A9%E6%A8%A1%E5%BC%8F%E5%AF%B9.PNG)

 当单词点击正确时，会以蓝色显示。

 
![在这里插入图片描述](https://github.com/kailunsong/remword/blob/master/source/ver/%E9%80%89%E6%8B%A9%E6%A8%A1%E5%BC%8F%E9%94%99.PNG)

 当单词点击错误时，红色显示。


##### 2.第三次迭代与前两次相比
+ 第三次迭代与第二次迭代相比，增加了选择模式下背诵单词，更加接近单词app的记忆方式，便于用户操作。
+ 修改了程序的操作细节，回车会查看单词正确释义，next会主动进入下一个单词背诵。
+ 调整了两种单词记忆模式的切换
+ 完善了复习模式



##### 3.总结
通过这段时间的开发工作，熟悉了Java应用程序的开发流程，从实际生活需求出发我们开发了单词记忆系统。
本程序实现了根据拼写方式和选择模式记忆单词的功能，通过干净清爽的界面给了用户较好的体验。
不足主要在用户注册方面，如果可能的话，适当拓展这个程序可以使其可以产生更好的商业效果。

