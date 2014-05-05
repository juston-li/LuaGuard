package Obfuscate;

public class StringEncryption {
	public String xor (String  str,int num,byte xorchar)
	 {
		 //for(int i=0;i<str.length;i++)
		 //{
			 byte [] xor=str.getBytes();                             //按照unicode的编码方式得到字符串中的字节流
			 for(int j=0;j<xor.length;j++)
			 {
				 xor[j]=(byte)(xorchar^xor[j]);                               //用密匙对字符串中字符进行异或
			 }
			 //int choice=num%2;
			 switch(num%2)                                                   //根据参数的奇偶性决定字符串的字符的变换次序
			 {
			 case 0:for(int j=0;j<xor.length/2;j++)
			 		{
				 		byte mid=xor[j];
				 		xor[j]=xor[xor.length-j-1];
				 		xor[xor.length-j-1]=mid;
			 		}
			 		break;		
			 case 1:for(int j=0;j<xor.length;j+=2)
			 		{
				 		byte mid=xor[j];
				 		if(j+1<xor.length)
				 		{
				 			xor[j]=xor[j+1];
				 			xor[j+1]=mid;
				 		}
			 		}
			 		break;
			 	default:break;
		 }
			//str[i]=new String(xor);
			 byte [] nxor=change(xor);                                      //调用方法对可能出现的不可显示字符用转义字符替代
			 str=new String(nxor);
			 return str;
		 //}
	 }
	//对字符串中的控制字符产生转义字符
	private byte[]  change(byte [] byt)
	{
		int i=0;
		int num=0;
		while(i<byt.length)
		{
			if(byt[i]<0)
				i+=2;
			else
				if(byt[i]>=32&&byt[i]<127)
				i++;
				else
				{
					num++;
					i++;
					
				}
		}
	num=3*num+byt.length;
		byte [] newbyt=new byte[num];
		int pass=0;
		for(i=0;i<byt.length;i++)
		{
			if(byt[i]<0)
			{
				
				newbyt[pass++]=byt[i++];
				if(i<byt.length)
					newbyt[pass++]=byt[i];
			}
			else
				if(byt[i]>=32&&byt[i]<127 && byt[i]!='\"' && byt[i]!='\\')
				{
					newbyt[pass++]=byt[i];
				}
				else
				{
					if(pass<num)
					{
						String xx="\\x";
						byte [] s=xx.getBytes();
						if(s.length>=2)
						{
						newbyt[pass++]=s[0];
						newbyt[pass++]=s[1];
						}
						String hex=Integer.toHexString(byt[i]);
						byte [] bu=hex.getBytes();
						if(bu.length>=2)
						{
							newbyt[pass++]=bu[0];
							newbyt[pass++]=bu[1];
						}
						else
							{
							newbyt[pass++]='0';
							newbyt[pass++]=bu[0];
							}
					}
				}
		}
		return newbyt;
	}

}

