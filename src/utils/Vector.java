package utils;

import java.util.Collection;


public class Vector<E> extends java.util.Vector<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2415448560624029449L;

	public Vector() {
		super();
	}

	public Vector(int initialCapacity) {
		super(initialCapacity);
	}

	public Vector(Collection<? extends E> c) {
		super(c);
	}

	public Vector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
	}
	
	/**
	 * Put (or replace) an element at a specific position
	 * The index should be <= size()
	 * @param index
	 * @param element
	 * @return true if the index was already taken
	 */
	public boolean put(int index,E element)
	{
		//System.out.println("Avant : " + this + " index " + index);
		boolean ret;
		if(size() == index)
		{
			add(element);
			ret = false;
		}
		else
		{
			ret =true;
			set(index, element);
		}
	//	System.out.println("Apres : " + this + "\n" + "ret = " + ret);
		return ret;
	}
	
	
//	/**
//	 * Put (or replace) an element at a specific position
//	 * if the index is higher than the size of this vector,
//	 * we add null element betwen the last str and the added str
//	 * in order that the element will be at the given position
//	 * @param index
//	 * @param element
//	 */
//	public void put(int index,E element)
//	{
//		if(size() < index)
//		{
//			while(size() < index)
//			{
//				add(null);
//			}
//			//size == index
//			add(element);
//		}
//		else if(size() == index)
//			add(element);
//		else
//			set(index, element);
//	}

	/**
	 * Return true if the vector is empty, or every composante is null
	 */
	public boolean isNull() {
		boolean ret = true;
		for(int i = 0 ; i < this.size() ; i++)
		{
			ret &= (this.get(i) == null);
		}
		return ret;
	}

}
