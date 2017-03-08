/**
 * This interface provides the assurance that implementing classes have the necessary behavior to funciton as a
 * first in first out data collection.
 *
 * @author Ryan Peters
 * @date 1/12/2017
 */
public interface IFifo {
	int getLength();

	boolean isEmpty();

	void insert(int num);

	int delete();

	int getCapacity();

	int setCapacity(int newCapacity);
}
