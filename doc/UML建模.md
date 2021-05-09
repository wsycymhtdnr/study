逆向工程
将java文件导入到UML建模工具中，使其变成OOM类图模型
![逆向工程](img\逆向工程.png "逆向工程")
以PowerDesigner为例，逆向工程的操作流程如下
1、选择文件-新建模型
2、选择语言-Reverse Engineer Java
![第一步](img\first_step.png "第一步")
3、添加java文件
![第二步](img\second_step.png "第二步")
逆向工程完成后
![工作空间](img\workspace.png "工作空间") ![OOM模型](img\oom_model.png "OOM模型")
正向建模
正向工程则相反，指的是将OOM类图模型，导出为java文件

UML关系画法
1、依赖关系
车 类是房子 类的变量 房子 类持有车 类的引用
![](img\dependence.png "")
2、泛化关系
相当于是java中的继承
![](img\generalization.png "")
3、关联关系
分为单项关联、双向关联和自关联，默认创建的是双向关联关系
![](img\association.png "")
4、实现关系
相当于是java中的实现
![](img\realization.png "")
5、聚合关系
表示整体和部分的关系，但是整体和部分可以分开
![](img\aggregation.png "")
6、组合关系
看上去和聚合很像，区别就是整体和部分不能分开
![](img\composition.png "")
