package others.dataStructure;

import java.util.BitSet;

/**
 * description
 *
 * @author fengjirong 2020/06/04 17:28
 */
public class BitSetTest {

	public static void main(String[] args) {
		BitSet bitSet1 = new BitSet();
		BitSet bitSet2 = new BitSet();
		for(int i=0;i<=16;i++){
			if(i%2==0){
				bitSet1.set(i);
			}
			if (i%3==0){
				bitSet2.set(i);
			}
		}
		System.out.println(bitSet1);
		System.out.println(bitSet2);
		System.out.println("--------------and----------------");
		bitSet1.and(bitSet2);
		System.out.println(bitSet1);
		System.out.println("--------------or----------------");
		bitSet1.or(bitSet2);
		System.out.println(bitSet1);
		System.out.println("--------------andNot----------------");
		bitSet1.andNot(bitSet2);
		System.out.println(bitSet1);
		System.out.println("--------------cardinality----------------");
		System.out.println(bitSet1.cardinality());
		System.out.println("--------------clear----------------");
		bitSet1.clear(1);
		System.out.println(bitSet1);

	}

}
