import java.io.*;
import java.util.*;
import java.util.PriorityQueue;

public class SortingTest
{

  //TODO: radix negative integer and check whether the sort is correct
	
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}
				
			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		
		//Modification from http://ko.wikipedia.org/wiki/%EA%B1%B0%ED%92%88_%EC%A0%95%EB%A0%AC
		
		int size = value.length; 
		 
		for(int i=size-1; i>0; i--) {
			for(int j=0; j<i; j++) {
				if(value[j] > value[j+1]) {
					int t=value[j];
	                value[j]=value[j+1];
	                value[j+1]=t;   
				}
	        }            
	    }
	    
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		// TODO : Insertion Sort 를 구현하라.
		
		//modification from http://ko.wikipedia.org/wiki/%EC%82%BD%EC%9E%85_%EC%A0%95%EB%A0%AC
		
		for(int i = 1 ; i < value.length ; i++)
		{
		      int temp = value[i];
		      int aux = i - 1;
		      while( (aux >= 0) && ( value[aux] > temp) ) 
		      {
		         value[aux+1] = value[aux];
		         aux--;
		      }
		      value[aux + 1] = temp;
		   }
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		
		//modification from http://en.wikibooks.org/wiki/Data_Structures/Min_and_Max_Heaps
		
		
		int[] heap = new int[value.length + 1];
		int t;
		int heapsize = value.length + 1;
		heap[1]=value[0];
		
		for(int i=2; i<heapsize;i++)
		{
			heap[i] = value[i-1];
			t = i;
			while(t>1)
			{
				if(heap[t/2]<=heap[t])
					break;
				int temp = heap[t/2];
				heap[t/2] = heap[t];
				heap[t] = temp;
				t=t/2;
			}
		}
		
		for(int i=0; i<value.length; i++)
		{
			value[i] = heap[1];
			heap[1] = heap[--heapsize];
			t = 1;
			while(t<heapsize)
			{
				int min=t;
		        if(t*2<heapsize && heap[t*2] < heap[min])
		            min=t*2;
		        if(t*2 + 1<heapsize && heap[t*2 + 1] < heap[min]) 
		            min=t*2+1;
		        if(min!=t){
		        	int temp = heap[t];
		        	heap[t] = heap[min];
		        	heap[min] = temp;
		            t=min;            
		        }
		        else 
		            break;
			}
			
		}
		
		return (value);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		
		//modification from https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Merge_sort
		
		if(value.length > 1)
		{
			int elementsInA1 = value.length / 2;
			int elementsInA2 = value.length - elementsInA1;
	    	int arr1[] = new int[elementsInA1];
			int arr2[] = new int[elementsInA2];
			for(int i = 0; i < elementsInA1; i++)
				arr1[i] = value[i];
			for(int i = elementsInA1; i < elementsInA1 + elementsInA2; i++)
				arr2[i - elementsInA1] = value[i];
			arr1 = DoMergeSort(arr1);
			arr2 = DoMergeSort(arr2);
	 
			int i = 0, j = 0, k = 0;
			while(arr1.length != j && arr2.length != k)
			{
				if(arr1[j] < arr2[k])
				{
					value[i] = arr1[j];
					i++;
					j++;
				}
				else
				{
					value[i] = arr2[k];
					i++;
					k++;
				}
			}
		
			while(arr1.length != j)
			{
				value[i] = arr1[j];
				i++;
				j++;
			}
			
			while(arr2.length != k)
			{
				value[i] = arr2[k];
				i++;
				k++;
			}
		}
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		
		// modification from http://www.softwareandfinance.com/Java/QuickSort_Iterative.html
		// and modification from http://ko.wikipedia.org/wiki/%ED%80%B5_%EC%A0%95%EB%A0%AC
		
		int left=0, right = value.length-1;
		int pleft,pright;
		int pivot,pval;
		
        LinkedList<QuickPosInfo> list = new LinkedList<QuickPosInfo>();
 
        QuickPosInfo info = new QuickPosInfo(left,right);
        
        list.add(info);
       
        while(true)
        {
            if(list.size() == 0)
                break;
            
            left = list.get(0).left;
            right = list.get(0).right;
            list.remove(0);
            
            if(left>right) continue;
            
            pval = value[left];
            pleft = left;
            pright = right;
            
            while (pleft<pright)
            {    
                while (value[pright] > pval)
                    pright--;
     
                while (value[pleft] <= pval && pleft < pright)
                    pleft++;
     
                
                int temp = value[pright];
                value[pright] = value[pleft];
                value[pleft] = temp;
            }
            value[left] = value[pleft];
            value[pleft] = pval;
            
            pivot = pleft;
            
            info = new QuickPosInfo(left,pivot-1);
            list.add(info);
            
            info = new QuickPosInfo(pivot+1,right);
            list.add(info);
            
        }
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		
		//modification from http://en.wikipedia.org/wiki/Radix_sort
		
		int i, m = value[0], exp = 1;
		int base = 10;
		int[] b = new int[value.length];
		int[] bucket = new int[base*2];
	 
		for (i = 1; i < value.length; i++) {
			if (Math.abs(value[i]) > m)
				m = Math.abs(value[i]);
		}
	 
		while(Math.abs(m / exp) > 0) {
			for(i=0;i<base*2;i++)
			{
				bucket[i]=0;
			}
	 
			for (i = 0; i < value.length; i++) {
				bucket[(value[i] / exp) % base + 9 ]++;
			}
	 
			for (i = 1; i < base * 2; i++) {
				bucket[i] += bucket[i - 1]; 
			}
	 
			for (i = value.length - 1; i >= 0; i--) {
				b[--bucket[(value[i] / exp) % base + 9]] = value[i];
			}
	 
			for (i = 0; i < value.length; i++) {
				value[i] = b[i];
			}
	 
			exp *= base;
		}
		return (value);
	}
}