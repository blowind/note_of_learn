函数:
fun sum(a: Int, b: Int): Int {
    return a + b
}
// 无返回值的函数可以使用Unit表示，也可以直接省略
fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}
