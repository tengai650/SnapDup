package util;

public class Pair<T, T1>
{
    public final T first;
    public final T1 second;
    
    public Pair(T f, T1 s)
    {
		first = f;
		second = s;
    }
    
    public T getFirst() { return first; }
    public T1 getSecond() { return second; }
}
