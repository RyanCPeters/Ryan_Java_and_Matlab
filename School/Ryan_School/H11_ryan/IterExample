/** A custom iterator set up for traversing the nodes in a this sorted single linked list class object.
	 *
	 */
	protected class MyIter implements Iterator<E> {
		private int pos;
		private boolean canRemove;
		private Node curr;
		private Node prev;


		/**
		 *
		 */
		public MyIter() {
			this.pos = -1;
			this.canRemove = false;
			this.curr = new Node(head, HeadOrTail.CURR);
			this.prev = new Node(head, HeadOrTail.PREV);

		}

		/**
		 * reveals the value stored at the node that curr is currently pointing at without advancing current
		 *
		 * @return the value stored at the node that curr is currently pointing at
		 */
		private E peepCurrVal() {
			return curr.next.value;
		}

		private E peepNextVal() {
			return curr.next.next.value;
		}

		/**
		 * This method helps check if the node following the one that curr is pointing at has the TAIL enum flag.
		 *
		 * @return the HeadOrTail enum flag assigned to the node following the current target of curr.
		 */
		private HeadOrTail checkForEndsFar() {
			return (curr.next.next != null) ? curr.next.next.myType : HeadOrTail.TAIL;
		}

		/**
		 * This method allows the checking of the HeadOrTail enum flag for the current node.
		 *
		 * @return the HeadOrTail enum flag assigned to the current target node of curr.
		 */
		private HeadOrTail checkForEndsNear() {
			return curr.next.myType;
		}

		private Node getCurrPointer() {
			return curr.next;
		}

		private Node getPrevPointer() {
			return prev.next;
		}
//
//		/**
//		 * A method for inserting a new node directly before the node curr is pointing at. It utilizes the fact that
//		 * the iterator already has a pointer for both current target node and the immediately preceding node.
//		 *
//		 * @param val
//		 */
//		private void insert(E val) {
//			Node n = new Node(val, curr.next);
//			prev.next.next = curr.next = n;
//		}

		/**
		 * Returns true if the iteration has more elements.
		 * (In other words, returns true if next() would
		 * return an element rather than throwing an exception.)
		 *
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			return pos+1 < size;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) throw new NoSuchElementException("there isn't a next node to reference");
			if (curr.next.next.value == null)
				throw new NoSuchElementException("there is a node here, but no data is stored");
			prev.next = curr.next;
			curr.next = curr.next.next;
			pos++;
			canRemove = true;
			return curr.next.value;
		}

		/**
		 * Removes from the underlying collection the last element returned
		 * by this iterator (optional operation).  This method can be called
		 * only once per call to {@link #next}.  The behavior of an iterator
		 * is unspecified if the underlying collection is modified while the
		 * iteration is in progress in any way other than by calling this
		 * method.
		 *
		 * @throws UnsupportedOperationException if the {@code remove}
		 *                                       operation is not supported by this iterator
		 * @throws IllegalStateException         if the {@code next} method has not
		 *                                       yet been called, or the {@code remove} method has already
		 *                                       been called after the last call to the {@code next}
		 *                                       method
		 * @implSpec The default implementation throws an instance of
		 * {@link UnsupportedOperationException} and performs no other action.
		 */
		@Override
		public void remove() {
			if (canRemove) {
				if (pos == 0) head.next = head.next.next;
				if (curr.next.next.myType == HeadOrTail.TAIL) tail.next = prev.next;
				prev.next.next = curr.next.next;
				curr.next = prev.next;
				canRemove = false;
				size--;
			} else {
				throw new IllegalStateException("\n\t\tthe next() method has not yet been called, \n" +
						"\t\tor the remove() method has already been called after the last call to the next() method");
			}
		}
	}
