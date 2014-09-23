# encoding=utf-8
# Android组编程练习 in Python

# 已为压缩长度牺牲风格, 不要模仿
# 不过Python天生就不适合写短码
# 所以不要在意长度这些细节...
# 不要问我这是怎么写出来的
# 我也记不住了

P = print
C = chr
M = map
S = sum
R = range
I = int

# 1. 水仙花数
[P(i) for i in R(2,1000) if i == S(M(lambda x:I(x)**3,'%d'%i))]

# 2. [1, 1000] 素数, 慢就慢吧
P([s for s in R(2,1000) if not[i for i in R(2,s) if s//i*i==s]])

# 3. 公约公倍
def gcd(a,b):return gcd(a-b,b) if a>b else gcd(b,a) if a<b else a
def lcm(a,b):return a*b//gcd(a,b)

print(gcd(13, 169), lcm(13, 169))

# 4. 打印
[P('*'*i)for i in R(1,6)]

# 5. 还打印
[P(' '*(4-n), '*'*(2*n+1))for n in R(5)]

# 6. 继续打印
[P(' '*(25-n),*M(C,R(97,98+n)))for n in R(26)]

# 7. 日历
def print_calendar(mon):
    days=[0,31,28,31,30,31,30,31,31,30,31,30,31]
    pos=(3+sum(days[:mon]))%7
    P("       %2d月" % mon, "日 一 二 三 四 五 六", sep='\n')
    [P(("%2d "%(i-pos)if i>pos else"-- ")+('\n'if(i>=6and(i%7==0))else''),end='')for i in R(1,1+days[mon]+pos)]
    P()

for m in range(1, 13):
    print_calendar(m)

# 8. 9x9
[P(("%d "%y if y else"- "),end='')or([P("   "if x<y else("%2d "%(x*max(y,1))),end='') for x in R(1,10)]and P())for y in R(10)]
