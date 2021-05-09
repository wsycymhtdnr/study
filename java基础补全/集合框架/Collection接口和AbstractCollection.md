#### 目录简介
- 1. Collection接口
- 2. AbstractCollection抽象类
    - 2.1 简介
    - 2.2 为什么AbstractCollection的add(E)等方法默认是抛出异常
    - 2.3 AbstractCollection源码分析

### 1. Collection接口
![](/img/Collection接口.png "java集合框架.png")
Collection接口是所有List和Set的根接口，该接口继承自Iterable，定义了集合的最基本方法，从功能上可以将Collection的方法分为以下几大类：
1、增加（add/addAll）
2、删除（remove/removeAll/clear/retainAll）
3、查询（contain/containAll/iterator/size/isEmpty）
4、转数组（toArray/toArray(T[])）

### 2.AbstractCollection
#### 2.1 简介
AbstractCollection是Collection的唯一直接实现类，该类也是一个抽象类，该类提供了对集合类操作的一些基本实现。从开发者的角度来讲，AbstractCollection类减小了开发者实现集合类的成本，这也是为什么Java定义了接口还要提供抽象方法的原因。
#### 2.2 为什么AbstractCollection的add(E)等方法默认是抛出异常
可以参考该问题 [stackoverflow](https://stackoverflow.com/questions/23889410/why-is-add-method-not-abstract-in-abstractcollection)
java这样设计是遵从标准的，如果我们要实现一个不可变的集合，我们仍然可以继承该抽象方法，而不用去创建新的接口或者抽象类，而如果我们需要实现一个可变的集合，那么我们只需要重写add()等方法即可。
#### 2.3 AbstractCollection源码分析
AbstractCollection默认实现的方法，能力都来自于Itorator，而iterator()方法是一个抽象方法，需要交由我们的实现类去实现。
```` java
public abstract class AbstractCollection<E> implements Collection<E> {
 
    protected AbstractCollection() {
    }
 
    public abstract Iterator<E> iterator();
 
    public abstract int size();
 
    //判断集合中是否有数据
    public boolean isEmpty() {
        return size() == 0;
    }
 
    /**
     * 判断是否包含指定的元素
     * （1）如果参数为null，查找值为null的元素，如果存在，返回true，否则返回false。
     * （2）如果参数不为null，则根据equals方法查找与参数相等的元素，如果存在，则返回true，否则返回false。
     * 注意：这里必须对null单独处理，否则null.equals会报空指针异常
     */
    public boolean contains(Object o) {
        Iterator<E> it = iterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return true;
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return true;
        }
        return false;
    }
 
    /**
     * 功能：将集合元素转换为数组
     * 实现：
     * （1）创建一个数组，大小为集合中元素的数量
     * （2）通过迭代器遍历集合，将当前集合中的元素复制到数组中（复制引用）
     * （3）如果集合中元素比预期的少，则调用Arrays.copyOf()方法将数组的元素复制到新数组中，并返回新数组，Arrays.copyOf的源码在后续文章中会分析.
     * （4）如果集合中元素比预期的多，则调用finishToArray方法生成新数组，并返回新数组，否则返回（1）中创建的数组
     */
    public Object[] toArray() {
        Object[] r = new Object[size()];
        Iterator<E> it = iterator();
        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) // fewer elements than expected
                return Arrays.copyOf(r, i);
            r[i] = it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }
 
    /**
     * 功能：通过泛型约束返回指定类型的数组
     * 实现：
     * （1）如果传入数组的长度的长度大于等于集合的长度，则将当前集合的元素复制到传入的数组中
     * （2）如果传入数组的长度小于集合的大小，则将创建一个新的数组来进行集合元素的存储
     */
    public <T> T[] toArray(T[] a) {
        // Estimate size of array; be prepared to see more or fewer elements
        int size = size();
        T[] r = a.length >= size ? a :
                  (T[])java.lang.reflect.Array
                  .newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = iterator();
 
        for (int i = 0; i < r.length; i++) {
            //集合元素大小小于数组的长度
            if (! it.hasNext()) { // fewer elements than expected
                if (a == r) {//如果数组是参数中的数组，则将剩余部分的值都设置为null
                    r[i] = null; // null-terminate
                } else if (a.length < i) {//如果传入的数组长度小于集合长度，则通过Arrays.copyOf将之前数组中的元素复制到新数组中
                    return Arrays.copyOf(r, i);
                } else {//如果传入数组的长度比集合大，则将多的元素设置为空
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i) {
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T)it.next();
        }
        // more elements than expected
        //集合元素大小大于数组的长度
        return it.hasNext() ? finishToArray(r, it) : r;
    }
 
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
 
    /**
     *  功能：数组扩容
     *  （1）当数组索引指向最后一个元素+1时，对数组进行扩容：即创建一个更长的数组，然后将原数组的内容复制到新数组中
     *  （2）扩容大小：cap + cap/2 +1
     *  （3）扩容前需要先判断是否数组长度是否溢出
     *  注意：这里的迭代器是从上层的方法（toArray）传过来的，并且这个迭代器已执行了一部分，而不是从头开始迭代的
     */
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int i = r.length;
        while (it.hasNext()) {
            int cap = r.length;
            if (i == cap) {
                int newCap = cap + (cap >> 1) + 1;
                // overflow-conscious code
                if (newCap - MAX_ARRAY_SIZE > 0)
                    newCap = hugeCapacity(cap + 1);
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T)it.next();
        }
        // trim if overallocated
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }
 
    /**
     * 判断数组容量是否溢出，最大为整型数据的最大值
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError
                ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
 
    /**
     * 未实现
     */
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }
 
    /**
     * 功能：移除指定元素
     * （1）如果参数为null，则找到第一个值为null的元素，并将其删除，返回true，如果不存在null的元素，返回false。
     * （2）如果参数不为null，则根据equals方法找到第一个与参数相等的元素，并将其删除，返回true，如果找不到，返回false。
     */
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        if (o==null) {
            while (it.hasNext()) {
                if (it.next()==null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }
 
    /**
     * 遍历参数集合，依次判断参数集合中的元素是否在当前集合中，
     * 只要有一个不存在，则返回false
     * 如果参数集合中所有的元素都在当前集合中，则返回true
     */
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }
 
    /**
     * 遍历参数集合，依次将参数集合中的元素添加当前集合中
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }
 
    /**
     * 功能：移除参数集合的元素
     * （1）获取当前集合的迭代器进行遍历
     * （2）如果当前集合中的元素包含在参数集合中，则删除当前集合中的元素
     *  注：只要参数集合中有任何一个元素在当前元素中，则返回true，表示当前集合有发送变化，否则返回false。
     */
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
 
    /***
     * 功能：求参数集合与当前集合的交集
     * （1）获取当前集合的迭代器进行遍历
     * （2）如果当前集合中的元素不在参数集合中，则将其移除。
     *  注意：如果当前集合是参数集合中的子集，则返回false，表示当前集合未发送变化，否则返回true。
     */
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
 
    //删除所有元素
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }
 
 
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext())
            return "[]";
 
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
````

