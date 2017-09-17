package util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.pploder.events.Event;
import com.pploder.events.SimpleEvent;

/** @Author Patrick Hoonhout
 *  @copyright (c) 2017
 **/

public class ObjectEventDispatcher
{
	static final private Map<Class<?>, Object> eventMap = new HashMap<>();

	static public <T> boolean hasEvent(Class<T> type)
	{
		return eventMap.containsKey(type);
	}
	
	static public <T> boolean addEvent(Class<T> type, Consumer<T> listener) throws NullPointerException
	{
		if(!eventMap.containsKey(type))
		{
			SimpleEvent<T> event = new SimpleEvent<>();
			event.addListener(listener);
			eventMap.put(type, event);
			return true;
		}

		return false;
	}

	static public <T> boolean addListener(Class<T> type, Consumer<T> listener) throws NullPointerException
	{
		if(eventMap.containsKey(type))
		{
			Event<T> event = (Event<T>)eventMap.get(type);
			event.addListener(listener);
			return true;
		}

		return false;
	}
	
	static public <T> boolean addAllListeners(Class<T> type, Collection<Consumer<T>> listeners) throws NullPointerException
	{
		if(eventMap.containsKey(type))
		{
			Event<T> event = (Event<T>)eventMap.get(type);
			event.addAllListeners(listeners);
			return true;
		}

		return false;
	}
	
	static public <T> boolean removeListener(Class<T> type, Consumer<T> listener) throws NullPointerException
	{
		if(eventMap.containsKey(type))
		{
			Event<T> event = (Event<T>)eventMap.get(type);
			event.removeListener(listener);
			return true;
		}

		return false;
	}

	static public <T> void postObject(T data)
	{
		Event<T> event = (Event<T>)eventMap.get(data.getClass());

		if(event != null)
			event.trigger(data);
	}
}
