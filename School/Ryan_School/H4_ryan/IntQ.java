/**
 * this class implements the IFifo interface and offers the client a means to create a que of int type values where the
 * first value saved to the que will also be the first value out. The values are stored in an array with an initial aize
 * of the client's choosing or a default size of 20 elements.
 *
 * @author Ryan Peters
 * @date 1/12/2017
 * @see IFifo
 */
public class IntQ implements IFifo {
	// an int that represents the  index value of the oldest element in the queue.
	private Integer first;

	// an in that represents the index value of the youngest element in the queue.
	private int last;

	// an int that represents index value for the last spot of the array, the corner of the block so to speak.
	private int corner;
	//Note that there is no need to define where the zero'th position in the array is... it's implicitly zero

	/* a boolean that gets updated to false whenever we succesfully add an element to the array, and gets updated to true
	*  inside the  delete method when the right conditions are met.*/
	private boolean isEmptyBool;

	// starts as false and gets updated to true when last == corner and first == 0. gets reset back to false with a
	// call to the setCapacity() method.
	private boolean isFullBool;
	private boolean lastHeldBool;
	private int[] theArray;

	/**
	 * @param initialCapacity an integer value for how large the array should be initialized to.
	 */
	public IntQ(int initialCapacity) {
		this.theArray = new int[greaterThanZero(initialCapacity)];
		this.isEmptyBool = true;
		this.isFullBool = false;
		this.corner = initialCapacity - 1;
		this.first = null;
		this.last = 0;
		this.lastHeldBool = false;
	}

	/**
	 *
	 */
	public IntQ() {
		this(20);
	}

	/**
	 * @return
	 */
	@Override
	public int getLength() {
		int len;
		if (first < last) {
			len = last - first + 1;
		} else {
			len = corner - first + last + 1;
		}
		return len;
	}

	/**
	 * @return returns true if the array is true as per the isEmptyBool field.
	 */
	public boolean isEmpty() {
		return isEmptyBool;
	}

	/**
	 * @param num the integer value to be added to the end of the queue array.
	 */
	@Override
	public void insert(int num) throws IndexHighBoundException {
		if (isFullBool) {
			throw new IndexHighBoundException(
					"an attempt was made to insert another element to the queue when the queue was already full." +
							"\n\tThe number " + num + " was to be inserted when:\n\tthe queue length   = " + getLength() + "\n\tthe" +
							" queue capacity = " + getCapacity());
		}

		Integer idx = last;
		// we need to advance the 'last' pointer, the following decides if we can and how.
		if (first == null) {
			first = last;
			isFullBool = false;
			if (last < corner) {
				last++;
				lastHeldBool = false;
			} else if (first > 0) {
				last = 0;
			}

		} else if (first <= last) {
			isFullBool = false;
			if (last < corner) {
				last++;
				lastHeldBool = false;
			} else if (first > 0) {
				last = 0;
				lastHeldBool = false;
			} else {
				isFullBool = true;
				lastHeldBool = true;
			}
		} else if (last++ < first) { // we have advanced our way around the array and now last's idx value is bellow first
			last++;
			lastHeldBool = false;
		} else { // first must be at index 0 and last is at corner, thus array is full.
			isFullBool = true;
		}


		theArray[idx] = num;
		isEmptyBool = false;

	}

	/**
	 * this method will  return the oldest value in the queue and then advance the pointer to the next oldest element.
	 *
	 * @return the value of the oldest element in the array.
	 * @throws IndexLowBoundException
	 */
	@Override
	public int delete() throws IndexLowBoundException {

		// now we determine if we can advance the 'first' pointer and how we do that.
		if (first == null) {
			throw new IndexLowBoundException(
					"There has been an attempt to remove an integer from the front of the IntQ object queue" +
							" while the queue was empty and:\n\tfirst = " + first + "\n\tlast = " + last);

		}
		Integer idx = first;

		if (first == last) {

			first = null;
			isEmptyBool = true;
		} else if (first < corner) {
			first++;
		} else {
			first = 0;
		}
		isFullBool = false;
		if (lastHeldBool) {
			advanceLast();
		}
		return theArray[idx];
	}

	private void advanceLast() {
		lastHeldBool = false;
		if (last >= first) {
			if (last < corner) {
				last++;
			} else if (first != 0) {
				last = 0;
			}

		} else if (last++ < first) {
			last++;
		} else lastHeldBool = true;
	}

	/**
	 * returns the total length of the array, ie it returns the highest index value the array can hold plus one. an array
	 * with 20 index positions would return the value  of 19 + 1 = 20.
	 *
	 * @return
	 */
	@Override
	public int getCapacity() {
		return corner + 1;
	}

	/**
	 * @param newCapacity the
	 */
	@Override
	public int setCapacity(int newCapacity) throws IndexOutOfBoundsException {
		System.out.println("the new Capacity is: " + newCapacity);
		System.out.println("the current capacity is; " + getCapacity());
		if (newCapacity >= this.getCapacity()) {
			int[] newArray = new int[newCapacity];

			if (first <= last) {
				for (int i = first; i <= last; i++) {
					newArray[i - first] = theArray[i];
				}
			} else {
				for (int i = first; i <= corner; i++) {
					newArray[i - first] = theArray[i];
				}
				for (int i = 0; i <= last; i++) {
					newArray[i] = theArray[i];
				}
			}

			corner = newCapacity - 1;
			theArray = newArray;
		} else {
			throw new IndexHighBoundException(
					"an attempt was made to set a new queue capacity that is lower than the existing one." +
							"\nA new capacity of " + newCapacity + " was requested when the current capacity is " + getCapacity());
		}
		return newCapacity;
	}

	/**
	 * used by this class constructor when calling public setCapacity() method to verify that the new array length
	 * will not result in a loss of capacity.
	 *
	 * @param numChex the input integer value for the new array capacity
	 * @return the input integer if it passes the checks
	 * @throws IndexLowBoundException
	 * @throws IndexHighBoundException
	 * @see IntQ#setCapacity(int)
	 */
	private int greaterThanZero(int numChex) throws IndexLowBoundException, IndexHighBoundException {
		if (numChex < 0) {
			throw new IndexOutOfBoundsException("the input value of " + numChex + " is outside of acceptable queue size." +
					"the input needs to be between 0 and " + Integer.MAX_VALUE);
		}
		if (numChex > Integer.MAX_VALUE) {
			throw new IndexHighBoundException("the input value of " + numChex + " is outside of acceptable queue size." +
					"the input needs to be between 0 and " + Integer.MAX_VALUE);
		}
		return numChex;


	}

	/**
	 * @return a string object representing the contents of the queue
	 */
	@Override
	public String toString() {
		if (isEmptyBool) {
			return this.getClass() + " = {}";
		}
		StringBuilder sb = new StringBuilder(this.getClass() + " = \n\t{ ");

        /* utilitySum is to be used as a means to choose when to add a newline character "\n" to the the
        * StringBuilder sb object. I could probably have used some sort of String.format method but I had a harder time
        * making proper sense of the String formatter tutorial  from oracle than I did with the StringBuilder.*/

		if (first < last) {
			int utilitySum = 0;
			// this condition is straight forward, because first < last < corner, we can just  iterate straight from
			// first to last.
			for (int i = first; i <= last; i++) {
				utilitySum++;
				if (i == last) {
					sb.append(theArray[i] + " } ");
				} else {
					sb.append(theArray[i] + ", ");
				}
				if (utilitySum % 10 == 0) {
					sb.append("\n\t");
					utilitySum = 0;
				}
			}
		} else {
			int utilitySum = 0;
			// this will take us from the position of the next idx to pop up to the end of the array before we wrap
			// back around to start from zero and  go to the idx position of the last element to pop.
			for (int i = first; i <= corner; i++) {
				utilitySum++;
				sb.append(theArray[i] + ", ");
				if (utilitySum % 10 == 0) {
					sb.append("\n\t");
					utilitySum = 0;
				}
			}// close first to corner loop

			for (int i = 0; i <= last; i++) {
				utilitySum++;
				if (i == last) {
					sb.append(theArray[i] + " } ");
				} else {
					sb.append(theArray[i] + ", ");
				}
				if (utilitySum % 10 == 0) {
					sb.append("\n\t");
					utilitySum = 0;
				}
			}// close 0 to last loop.

		}
		return sb.toString();
	}// end of toString()

	/**
	 * IndexLowBoundException extends IndexOutOfBoundsException a private inner class that's used to indicate more
	 * more clearly to client code what is causing the out of bounds exception
	 */
	protected class IndexLowBoundException extends IndexOutOfBoundsException {
		/**
		 * Constructs an <code>IndexOutOfBoundsException</code> with no
		 * detail message.
		 */
		IndexLowBoundException() {
			super();
		}

		/**
		 * Constructs an <code>IndexOutOfBoundsException</code> with the
		 * specified detail message.
		 *
		 * @param msg the detail message.
		 */
		IndexLowBoundException(String msg) {
			super(msg);
		}
	}

	/**
	 * IndexHighBoundException extends IndexOutOfBoundsException a private inner class that's used to indicate more
	 * more clearly to client code what is causing the out of bounds exception
	 */
	protected class IndexHighBoundException extends IndexOutOfBoundsException {
		/**
		 * Constructs an <code>IndexOutOfBoundsException</code> with no
		 * detail message.
		 */
		IndexHighBoundException() {
		}

		/**
		 * Constructs an <code>IndexOutOfBoundsException</code> with the
		 * specified detail message.
		 *
		 * @param msg the detail message.
		 */
		IndexHighBoundException(String msg) {
			super(msg);
		}
	}

}
