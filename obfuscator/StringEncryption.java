import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class StringEncryption {
	
	private walker TexasRanger = new walker();
	
	public void obfuscate(File ast, ArrayList<String> bl){		
		TexasRanger.addToBlacklist(bl);
		JsonReader jreader = TexasRanger.read_ast(ast.getAbsolutePath());
		StringWriter swriter = new StringWriter();
		JsonWriter jwriter = new JsonWriter(swriter);
		obf0(jreader, jwriter);
		TexasRanger.write_ast(swriter, ast.getAbsolutePath());

	}
	
	// walks the ast, obfuscates names, and writes it out
		public void obf0(JsonReader reader, JsonWriter writer){
			try {
				int i = 2;
				while (true){
					i++;
					JsonToken token = reader.peek();
					switch (token){
						case BEGIN_ARRAY:
							reader.beginArray();
							writer.beginArray();
							break;
						case END_ARRAY:
							reader.endArray();
							writer.endArray();
							break;
						case BEGIN_OBJECT:
							reader.beginObject();
							writer.beginObject();
							break;
						case END_OBJECT:
							reader.endObject();
							writer.endObject();
							break;
						case NAME:
							String name = reader.nextName();
							if (i == 1 && name.equals("name")){
								i = -1;
							}
							writer.name(name);
							break;
						case STRING:
							String str = reader.nextString();
							if (str.equals("Identifier")){
								i = 0;
							} else if (i == 0 && !TexasRanger.check_blacklist(str)){
								// check if in blacklist and previously obfuscated
								if (!TexasRanger.check_name(str)){
									String ob_name = xor(str,(int)Math.random(),(byte)'x');
									TexasRanger.obfuscated_names.put(str, ob_name);
									writer.value(ob_name);
								} else {
									writer.value(TexasRanger.obfuscated_names.get(str));
								}
								i = 2;
								break;
							}
							writer.value(str);
							break;
						case NUMBER:
							String num = reader.nextString();
							writer.value(new BigDecimal(num));
							break;
						case BOOLEAN:
							boolean bool = reader.nextBoolean();
							writer.value(bool);
							break;
						case NULL:
							reader.nextNull();
							writer.nullValue();
							break;
						case END_DOCUMENT:
							return;
					}
				}
			} catch (IOException e){
				System.err.println(e.getMessage());
			}
		}
	
	
	public String xor (String  str,int num,byte xorchar)
	 {
		 //for(int i=0;i<str.length;i++)
		 //{
			 byte [] xor=str.getBytes();                             
			 for(int j=0;j<xor.length;j++)
			 {
				 xor[j]=(byte)(xorchar^xor[j]);                             
			 }
			 //int choice=num%2;
			 switch(num%2)                                                   
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
			 byte [] nxor=change(xor);                                   
			 str=new String(nxor);
			 return str;
		 //}
	 }

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

