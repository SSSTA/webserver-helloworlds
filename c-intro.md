# C玄学导论0 #

## 绪论 ##

------------

> 机器内部真正“运行”着的东西很简单，那只是一个单调的电平序列，无止无歇，蕴涵无尽可能。

> 机器对于这些电平序列保持绝对的无知——它们只是遵照最基本的物理规则而出现、流转、消逝，

> 有意义的并不是这些0与1，而是人对它们的解释：

> 它们被解释成数，数又被映射到现实世界，用以表示字母表中的一个字母、描述音频的频率与振幅、构成视频的亮度与色彩，他的身份证号，你的支付宝余额……

> 这也许就是为什么在计算机发展的早期，那些最早的开拓者被称作“巫师”：

> 他们用凭空创造的抽象与表达，重新创造了世界。

-------------
## C的核心 ##

C有一个极其精简的核心：

> 地址 = 基址 + 偏移

之所以说**C是一门低级语言**，也就是因为C语言有一个如此精简而强大的内核：写下的变量、数组、结构体、函数定义&调用……凡此种种，唯一目的就是为了帮助编译器计算**目标在内存中的真实地址**。

也正是由于这样的设计，C开放了**指针**，使得用户对合法资源有了超乎想象的操纵能力——这种能力本身，也就是C的晦涩之源了。


## 伪真相 ##

### 变量直接映射至内存 ###

+ 在你写代码的时候，C里的变量就像方程里的变量，每个变量都有确定的类型，每个变量都对应着内存中的一块存储区域。

+ 变量，顾名思义，是可变的。使用const关键字可以创造出“不可变”的变量。当然，只是编译程序不会允许编译通过，正如前面所说的，这一切对机器而言没什么两样。

+ 不同类型的变量之间相赋值会发生什么呢？或者截断或者升位，但是对于本来就不怎么精确的浮点数来说无异于雪上加霜。

### C的弱类型系统 ###

+ 类型即约定，一种人与机器之间对解读二进制模式的方法的约定。
机器把32位识别成一个int或者一个float, 就像你看到345839177会识别成一个QQ号, 看到hr@sssta.xyz会识别成一个电子邮箱地址一样。

+ 类型是给人看的。机器对它处理的二进制位保持绝对的无知，而程序设计语言的类型系统实一是一种抽象，使得用户能更简短的表示所想，同时也避免了程序员使用错误的方式操纵数据。

这里有一个很和谐的Hello World例程：

```C
#include <stdio.h>
int main()
{
	const char* hw = "Hello, world";
    printf("%s\n", hw);
    return 0;
}
```

但是，正如我说的，类型对机器不重要。下面的程序一样能打印出**Hello, wizard!**

```C
#include <stdio.h>
int main()
{
    int foo[] = {
    	1819043144,
    	1998597231,
    	1918990953,
    	8548
    };
    // "Hello, wizard!\0"
    printf("%s\n", foo);
    return 0;
}
```

更糟糕的是，C在**printf**这里不会作类型检查。事实上在更为危险的地方都不会。这意味着巨大的风险。C中的字符串以**0x00**标记结束。上面的"Hello, wizard!"是有意构造的，其结尾有足够的**0x00**。如果是一些更不幸的情况，比如：

```C
#include <stdio.h>

int bazinga[] = {1769627970, 560031598}; // "Bazinga!"
char oops[] = " !!LEAVE ME!! ";

int main()
{
    printf("%s\n", bazinga);
    // will print "Bazinga! !!LEAVE ME!! "
    return 0;
}
```

由于8个**char**刚好被映射到2个**int**上，这里没有**0x00**来终结bazinga。所以后面的**oops**也会被打印。这里的**oops**经过专门设计以确保它在内存中出现在紧邻**bazinga**的位置，并封堵**bazinga**的漏洞。对于更一般的情况，程序往往会走向崩溃，或者，更糟糕的，被入侵者利用。

### 数组 ###
+ 简单来说，数组不是真实存在的。编译器只是借助这种更简单的形式来计算某个元素的实际位置。

+ 数组名确定一个基准位置，元素类型确定每个元素有多长，下标用于确定元素位置。**arr[0]**其实是代数运算&解引用。

+ 数组，就是一组在当前进程的虚拟内存空间里逻辑相邻的变量。

> 相比“数组”这个翻译，array作名词讲的任何一个其他意思都更能表达array本身的含义。一维的array如“队列”，高维的如“阵列”，或者更直白的解释：一大堆。

+ 在绝绝大多数程序设计语言中数组下标都是从0开始的，Lua是个著名的例外。

+ 是的，数组下表访问这个操作，只是一个语法糖。

尝试这两个例子以验证这句话：

```C
#include <stdio.h>
int main()
{
    int foo[] = {1, 2, 3, 4};
    printf("%d\n", foo[2]);
    // will print 3
    return 0;
}
```

```C
#include <stdio.h>
int main()
{
    int foo[] = {1, 2, 3, 4};
    printf("%d\n", 2[foo]);
    // will print 3, too
    return 0;
}
```

### 枚举类型 ###
+ 简单来说，枚举类型不是真实存在的。C只是借助这种更简单的形式来标示状态。

+ 枚举值，在C中被直接处理成整数，你甚至可以让他们直接参与代数运算。但不要在C++中这么乱来。

### 结构体 ###
+ 简单来说，结构体不是真实存在的。编译器只是借助这种更简单的形式来计算某个元素的实际位置。

+ 结构，依然是一组在当前进程的虚拟内存空间里逻辑相邻的变量，只不过他们可以类型不同。
	
+ 依然是“实际内存地址 = 基址 + 偏移量”

+ 更进一步的说，结构提成员访问用的.和->，也只是语法糖。

```C
#include <stdio.h>

typedef unsigned char byte;

typedef struct
{
	byte R;
	byte G;
	byte B;
	const char* desc;
} RGBColor;

RGBColor make_color(byte R, byte G, byte B, const char* desc)
{
	RGBColor color = {R, G, B, desc};
	return color;
}

void print_color(RGBColor color)
{
	printf("%s: %02X%02X%02X\n",
		color.desc, color.R, color.G, color.B);
}

int main()
{
	RGBColor red = make_color(0x66, 0xCC, 0xFF, "天依蓝");
	print_color(red);
	return 0;
}
```

其中的**print_color**完全可以被修改成

```C
void print_colorp(RGBColor *color)
{
	printf("%s: %02X%02X%02X\n",
		color->desc,
		color->R,
		color->G,
		color->B);
}
```

或者，更接近**地址+偏移量**的写法：

```C
void print_color(RGBColor color)
{
	byte *base = (byte*)&color;
	printf("%s: %02X%02X%02X\n",
		*(char**)(base+8),
		*(base+0),
		*(base+1),
		*(base+2));
}
```

### 联合 ###
+ 大体同上，但是联合是共享空间的，同一时间只有一个成员有效。

### 指针 ###
+ 总体上这就是C的半壁江山。
+ C的灵魂所在。爱之深，恨之切。
+ 指针就是地址。就这么简单。但是指针之于C就像that之于English。

举个更疯狂的例子。

```C
#include <stdio.h>
const char* bazinga()
{
	return "Bazinga!";
}

int main()
{
    printf("%s\n", bazinga());
    return 0;
}
```

很和谐，不是吗？

但是如果你执意希望做点不一样的事情，我们依然可以：

```C
#include <stdio.h>
const char* bazinga()
{
	return "Bazinga!";
}

int main()
{
    printf("%s\n", *(int*)5[bazinga]);
    // will print "Bazinga!"
    // just, bazinga!
    return 0;
}
```

这不是黑魔法。这很简单。就像我在绪论说的，函数入口也只是用于计算内存地址——代码的内存地址。所以，函数bazinga本身也是一个地址，额……不过有个更高端的名字，函数指针。这段代码是怎么工作的呢？反编译上面的正常版本，测量bazinga函数被编译成二进制文件之后，返回的字符串常量的地址离函数入口有多远。答案是5字节，所以跳过5个字节，读取长4个字节的一个新的地址——"Bazinga!"的地址，然后把它交给printf。

我**不能**保证这个例子能在所有机器上运行——事实上，只有开启编译一些选项的特定版本的
gcc能勉强编译它，clang会默认拒绝编译的。这段代码对具体细节的依赖太多了。

不过也有一件很有意思的事情，那就是编译这段代码时候的报错信息：

```
pos-no.c: In function ‘int main()’:
pos-no.c:9:36: warning: pointer to a function used in arithmetic [-Wpointer-arith]
     printf("%s\n", *(int*)5[bazinga]);
                                    ^
```

pointer to a function used in **arithmetic**！！

所以说数组什么的，只是一个语法糖罢了。

------------------------------

C玄学导论0
