/**
	 * the single linked nodes that are used to build the sorted list.
	 */
	protected class Node {
		E value;
		Node next;
		HeadOrTail myType = HeadOrTail.MID;

		/** sets the generic value to the given value and the next Node pointer to the given pointer.
		 *
		 * @param value a generic comparable object
		 * @param next a single link list node
		 */
		Node(E value, Node next) {
			this.value = value;
			this.next = next;
		}

		/** sets the generic value to the given value then setns the next Node pointer to be null.
		 *
		 * @param value a generic comparable object
		 */
		Node(E value) {
			this(value, null);
		}

		/**
		 * Sets both the next Node pointer and the enum HeadOrTail flag to the given parameters then sets the value
		 * field to be null.
		 *
		 * @param next a single link list node
		 * @param type the enum HeadOrTail flag used to identify the head and tail Nodes in the list.
		 */
		Node(Node next, HeadOrTail type) {
			this(null, next);
			this.myType = type;
		}

		/** sets the HeadOrTail enum flag to the given parameter, then sets the generic value field along with the
		 *  next Node pointer to be null.
		 *
		 * @param type the enum HeadOrTail flag used to identify the head and tail Nodes in the list.
		 */
		Node(HeadOrTail type) {
			this(null, type);
		}
	}
