package net.leek.application.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 黑云压城
 * @see 数据结构判断工具
 * @version 2013/3/20
 */
public class CollectionUtil {

	public static void main(String[] args) {
		List<int[]> survive1 = new ArrayList<int[]>();
		int[] e0 = {1,2};
		int[] e1 = {3,7};
		int[] e2 = {8,27};
		int[] e3 = {5,56};
		int[] e4 = {18,20};
		int[] e5 = {33,96};
		int[] e6 = {112,113};
		int[] e7 = {9,111};
		survive1.add(e0);
		survive1.add(e1);
		survive1.add(e2);
		survive1.add(e3);
		survive1.add(e4);
		survive1.add(e5);
		survive1.add(e6);
		survive1.add(e7);
		survive1 = merger(survive1,true);
		for(int[] data:survive1){
			System.out.println(Arrays.toString(data));
		}
	}
	
	
	
	private CollectionUtil(){};
	
	
	public static List<int[]> merger(List<int[]> data,boolean retry){
		List<int[]> survive1 = isEmpty(data)?new ArrayList<int[]>():data;
		List<int[]> survive2 = new ArrayList<int[]>();
		List<int[]> eden = new ArrayList<int[]>();
		while(survive1.size() > 0){
			int[] tmp = survive1.get(0);
			for(int[] one:survive1){
				int lmax = tmp[0]>one[0]?tmp[0]:one[0];
				int rmin = tmp[1]<one[1]?tmp[1]:one[1];
				if(lmax > rmin){
					//没有交集
					survive2.add(one);
				}else{
					//有交集
					int lmin = tmp[0]<one[0]?tmp[0]:one[0];
					int rmax = tmp[1]>one[1]?tmp[1]:one[1];
					tmp[0] = lmin;
					tmp[1] = rmax;
				}
			}
			eden.add(tmp);
			survive1.clear();
			survive1.addAll(survive2);
			survive2.clear();
		}
		if(retry){
			return merger(eden, false);
		}
		return eden;
	}
	
	public static boolean isEmpty(Collection<?> c){
		return c == null || c.size() == 0;
	}

	public static boolean isEmpty(Map<?,?> c){
		return c == null || c.isEmpty();
	}

	public static boolean isEmpty(Object c[]){
		return c == null || c.length == 0;
	}
}
