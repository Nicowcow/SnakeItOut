package com.cowlabs.games.snakeitout.framework.events;

import android.annotation.SuppressLint;

import com.cowlabs.games.snakeitout.framework.events.EventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class EventManager {
	
	private final Map<Class<?>, Vector<EventListener>> listenersMap = 
			new HashMap<Class<?>, Vector<EventListener>>() ;
	private final Vector<Vector<Object>> queues = new Vector<Vector<Object>>();
	private int activeQueue = 0;
	
	public EventManager(){
		queues.add(new Vector<Object>());
		queues.add(new Vector<Object>());
	}
	
	
	public void addListener(EventListener newListener, Class<?> eventType){		
		Vector<EventListener> specificTypeListeners = listenersMap.get(eventType);
		
		if(specificTypeListeners == null){
			specificTypeListeners = new Vector<EventListener>();
			listenersMap.put(eventType, specificTypeListeners);
		}
		
		
		for(int i = 0; i < specificTypeListeners.size(); i++){
			EventListener listener = specificTypeListeners.get(i);
			if(listener == newListener){
				throw new RuntimeException("Listener " + listener.toString() + " for event " + eventType.toString()+ " shouldn't be registered twice!");
			}
		}
		
		specificTypeListeners.add(newListener);
	}
	
	@SuppressLint("DefaultLocale")
	public boolean removeListener(EventListener oldListener, Class<?> type){
		
		
		boolean removed;
		
		Vector<EventListener> specificTypeListeners = listenersMap.get(type);
		
		if(specificTypeListeners == null)
			throw new RuntimeException("No listener registered for event " + type.toString() +"!");
		
		removed = specificTypeListeners.remove(oldListener);
		
		if(specificTypeListeners.size() == 0)
			listenersMap.remove(specificTypeListeners);
		
		return removed;
	}
	
	public void removeListener(EventListener oldListener){
		
		Vector<Class<?>> types = new Vector<Class<?>>(listenersMap.keySet());
		
		boolean oneRemoved = false;
		
		for(int i = 0; i < types.size(); i++)
			oneRemoved |= this.removeListener(oldListener, types.get(i));
		
		if(!oneRemoved)
			throw new RuntimeException("No listener " + oldListener.toString() + " registered!");

	}
	
	public void queueEvent(Object event){
		
		Class<?> type = event.getClass();
		
		if(this.listenersMap.containsKey(type))
			this.queues.get(activeQueue).add(event);
		else
			throw new RuntimeException("No listener registered for event " + event.toString());
		
	}
	
	public void tick(){
		int queueToProcess = activeQueue;
		activeQueue = (activeQueue + 1)%2;
		
		Vector<Object> eventQueue = queues.get(queueToProcess);
		
		for(int i = 0; i < eventQueue.size(); i++){
			Object event = eventQueue.get(i);
			Vector<EventListener> regListeners = listenersMap.get(event.getClass());
			
			for(int j = 0; j < regListeners.size(); j++)
				regListeners.get(j).handleEvent(event);
			
		}
		
		eventQueue.clear();
		
	}
	
}
