package eis.lab.groupb;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProjectionMapper extends Mapper<LongWritable, Text, Text, Text> 
{	
	
	//skip first row
		@Override
		public void run(Context context) throws IOException, InterruptedException 
		{
		  setup(context);
		  int rows = 0;
		  
		  while (context.nextKeyValue()) 
		  {
		    if (rows == 0) 
		    {
		    }
		    else
		    	map(context.getCurrentKey(), context.getCurrentValue(), context);
		    rows++;
		  }

		  cleanup(context);
		}

	
	
	  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException //OutputCollector  output,Reporter reporter) throws IOException 
	  {
		  Configuration conf=context.getConfiguration();
		  String separator=conf.get("separator");
		  String columns = conf.get("columns");
		  String mergecolumns = conf.get("mergecolumns");
		  String splitcolumns = conf.get("splitcolumns");
		  String casingcolumns = conf.get("casingcolumns");
		  String formatingcolumns = conf.get("formatingcolumns");
		  
		  String[] indicesofcolumns = columns.split("\\|");	
		  
		  String[] mergecolumnsvalues = null;
		  String[] splitcolumnsvalues = null;
		  String[] casingcolumnsvalues = null;
		  String[] formatingcolumnsvalues = null;
		  
		  if(!mergecolumns.equals("empty"))
		  mergecolumnsvalues = mergecolumns.split("\\|");	
			  
		  if(!splitcolumns.equals("empty"))
			  splitcolumnsvalues = splitcolumns.split("\\|");	
		  
		  if(!casingcolumns.equals("empty"))
			  casingcolumnsvalues = casingcolumns.split("\\|");	
		  
		  if(!formatingcolumns.equals("empty"))
			  formatingcolumnsvalues = formatingcolumns.split("\\|");	
		  		  
		  String line;
		  String Columns[];
		  String Columns2[];
		  String inputLine = value.toString();
		  
		  Columns = inputLine.split("\\"+separator);
		  Columns2 = inputLine.split("\\"+separator);
		  if(!mergecolumns.equals("empty"))
		  {
			  for(int i=0;i<(mergecolumnsvalues.length);i=i+3)
			  {
				  Columns2[Integer.parseInt(mergecolumnsvalues[i])] = Columns[Integer.parseInt(mergecolumnsvalues[i])]+mergecolumnsvalues[i+2]+Columns[Integer.parseInt(mergecolumnsvalues[i+1])];
			  }  
		  }
		 
		  if(!splitcolumns.equals("empty"))
		  {
			  String split1 = Columns[Integer.parseInt(splitcolumnsvalues[0])].substring(0,Columns[Integer.parseInt(splitcolumnsvalues[0])].indexOf(splitcolumnsvalues[1])); // "72"
			  String split2 = Columns[Integer.parseInt(splitcolumnsvalues[0])].substring(Columns[Integer.parseInt(splitcolumnsvalues[0])].indexOf(splitcolumnsvalues[1])+1); // "72"		  			 
			  Columns2[Integer.parseInt(splitcolumnsvalues[0])] = split1+separator+split2;
		  }
		  
		  if(!casingcolumns.equals("empty"))
		  {
			  if (casingcolumnsvalues[1].equals("0"))
			  {
			  Columns2[Integer.parseInt(casingcolumnsvalues[0])] = Columns2[Integer.parseInt(casingcolumnsvalues[0])].toUpperCase();
			  }
			  if (casingcolumnsvalues[1].equals("1"))
				  Columns2[Integer.parseInt(casingcolumnsvalues[0])] = Columns2[Integer.parseInt(casingcolumnsvalues[0])].toLowerCase();  
		  }
		  
		  if(!formatingcolumns.equals("empty"))
		  {  
			  String[] dates =  Columns2[Integer.parseInt(formatingcolumnsvalues[0])].split("\\/");
			  String day;
			  String month;
			  String year;
			  if(Integer.parseInt(dates[0])>0 && Integer.parseInt(dates[0])<13 && Integer.parseInt(dates[1])>0 && Integer.parseInt(dates[1])<32)
			  {
				  day = dates[1];
				  month = dates[0];
				  year = dates[2];
			  }
			  else
				  if(Integer.parseInt(dates[0])>0 && Integer.parseInt(dates[0])<32 && Integer.parseInt(dates[1])>0 && Integer.parseInt(dates[1])<13)
				  {
					  day = dates[0];
					  month = dates[1];
					  year = dates[2];
				  }
				  else
					  
				  {
					  day = dates[2];	  
					  month = dates[1];
					  year = dates[0]; 
				  }
			  
			  if (formatingcolumnsvalues[1].equals("DD/MM/YYYY"))
			  {  
				  Columns2[Integer.parseInt(formatingcolumnsvalues[0])] = day+"/"+month+"/"+year;			 
			  }
			  if (formatingcolumnsvalues[1].equals("MM/DD/YYYY"))
			  {  
				  Columns2[Integer.parseInt(formatingcolumnsvalues[0])] = month+"/"+day+"/"+year;			 
			  }
			  if (formatingcolumnsvalues[1].equals("YYYY/MM/DD"))
			  {  
				  Columns2[Integer.parseInt(formatingcolumnsvalues[0])] = year+"/"+month+"/"+day;			 
			  }
		  }
		
		  
		  line ="";
		  
		  for(int i=0;i<indicesofcolumns.length;i++)
		  {
			  if(indicesofcolumns[i].equals("1000"))
			  {}
			  else
			  {
			  if (i<(indicesofcolumns.length-1))
			  {
			  line=line+Columns2[Integer.parseInt(indicesofcolumns[i])]+separator;
			  }
			  if (i==(indicesofcolumns.length-1))
			  {
				  line=line+Columns2[Integer.parseInt(indicesofcolumns[i])];
			  }
			  }
		  }
		
		  //**********************************************************************************************************
		  String operations = conf.get("operations");
		  String[] operationsvalues = null;
		  operationsvalues = operations.split("\\|");	
				 
				 String ColumnsOutput[];
				 ColumnsOutput = line.split("\\"+separator);
				 line = "";
//				 String ColumnsOutputAux[];
				 String aux ="";
				 
//				 String first = operationsvalues[0];
//				 int h=1;
//				 int index = 0;
				 
								 
				 if(!operations.equals("empty"))
				 {
				 for(int j=0;j < operationsvalues.length;j=j+3)
				 {
					 line = "";
					
								 for(int i=0;i<ColumnsOutput.length;i++)
								  {
										 
									 if (operationsvalues[j+1].equals("=") && !aux.equals("EMPTY"))
									 {
										 
												 if(Float.parseFloat(ColumnsOutput[Integer.parseInt(operationsvalues[j])]) == Float.parseFloat(operationsvalues[j+2])) 
											 {
												 {
													 if (i<(ColumnsOutput.length-1))
													 {
														 line=line+ColumnsOutput[i]+separator;
													 }
													 if (i==(ColumnsOutput.length-1))
									
													 {
														 line=line+ColumnsOutput[i];
													 }
												 }
											 }	 
											 else
											 {
												 line="EMPTY";
											 }
										 
										 
										 }
									 
									 if (operationsvalues[j+1].equals("EQUAL") && !aux.equals("EMPTY"))
									 {
										 
												 if(ColumnsOutput[Integer.parseInt(operationsvalues[j])].equals(operationsvalues[j+2]))
											 {
												 {
													 if (i<(ColumnsOutput.length-1))
													 {
														 line=line+ColumnsOutput[i]+separator;
													 }
													 if (i==(ColumnsOutput.length-1))
									
													 {
														 line=line+ColumnsOutput[i];
													 }
												 }
											 }	 
											 else
											 {
												 line="EMPTY";
											 }
										 
										 
										 }
									 
									 
									 if (operationsvalues[j+1].equals("NOT EQUAL") && !aux.equals("EMPTY"))
									 {
												 if(ColumnsOutput[Integer.parseInt(operationsvalues[j])].equals(operationsvalues[j+2])==false)
											 {
													
												 {
													 if (i<(ColumnsOutput.length-1))
													 {
														 line=line+ColumnsOutput[i]+separator;
													 }
													 if (i==(ColumnsOutput.length-1))
									
													 {
														 line=line+ColumnsOutput[i];
													 }
												 }
											 }	 
											 else
											 {
												 line="EMPTY";
											 }
										 
										 
										 }
									 
									 
									 if (operationsvalues[j+1].equals("CONTAINS") && !aux.equals("EMPTY"))
									 {
										 
												 if(ColumnsOutput[Integer.parseInt(operationsvalues[j])].contains(operationsvalues[j+2]))
											 {
												 {
													 if (i<(ColumnsOutput.length-1))
													 {
														 line=line+ColumnsOutput[i]+separator;
													 }
													 if (i==(ColumnsOutput.length-1))
									
													 {
														 line=line+ColumnsOutput[i];
													 }
												 }
											 }	 
											 else
											 {
												 line="EMPTY";
											 }
										 
										 
										 }
					
					
									 
									 
					 
					 if (operationsvalues[j+1].equals("<>")&& !aux.equals("EMPTY"))
					 {
							 if(Float.parseFloat(ColumnsOutput[Integer.parseInt(operationsvalues[j])]) != Float.parseFloat(operationsvalues[j+2])) 
							 {
								
									  {
									  if (i<(ColumnsOutput.length-1))
									  {
									  line=line+ColumnsOutput[i]+separator;
									  }
									  if (i==(ColumnsOutput.length-1))
									  {
										  line=line+ColumnsOutput[i];
									  }
									  }
									 
						     }
							 else
							 {
								 line="EMPTY";
							 }
					 }
					 
					 if (operationsvalues[j+1].equals(">")&& !aux.equals("EMPTY"))
					 {
							 if(Float.parseFloat(ColumnsOutput[Integer.parseInt(operationsvalues[j])]) > Float.parseFloat(operationsvalues[j+2])) 
							 {
								
									  {
									  if (i<(ColumnsOutput.length-1))
									  {
									  line=line+ColumnsOutput[i]+separator;
									  }
									  if (i==(ColumnsOutput.length-1))
									  {
										  line=line+ColumnsOutput[i];
									  }
									  }
									
									
						     }
							 else
							 {
								 line="EMPTY";
							 }
					 }
					 

				if (operationsvalues[j+1].equals("<")&& !aux.equals("EMPTY"))
				 {
						 if(Float.parseFloat(ColumnsOutput[Integer.parseInt(operationsvalues[j])]) < Float.parseFloat(operationsvalues[j+2])) 
						 {
							
								  {
								  if (i<(ColumnsOutput.length-1))
								  {
								  line=line+ColumnsOutput[i]+separator;
								  }
								  if (i==(ColumnsOutput.length-1))
								  {
									  line=line+ColumnsOutput[i];
								  }
								  }					
					     }
						 else
						 {
							 line="EMPTY";
						 }
				 }
				
				if (operationsvalues[j+1].equals(">=")&& !aux.equals("EMPTY"))
				 {
						 if(Float.parseFloat(ColumnsOutput[Integer.parseInt(operationsvalues[j])]) >= Float.parseFloat(operationsvalues[j+2])) 
						 {
							 
								  {
								  if (i<(ColumnsOutput.length-1))
								  {
								  line=line+ColumnsOutput[i]+separator;
								  }
								  if (i==(ColumnsOutput.length-1))
								  {
									  line=line+ColumnsOutput[i];
								  }
								  }
								
								
					     }
						 else
						 {
							 line="EMPTY";
						 }
				 }
				
				if (operationsvalues[j+1].equals("<=")&& !aux.equals("EMPTY"))
				 {
						 if(Float.parseFloat(ColumnsOutput[Integer.parseInt(operationsvalues[j])]) <= Float.parseFloat(operationsvalues[j+2])) 
						 {
							
								  {
								  if (i<(ColumnsOutput.length-1))
								  {
								  line=line+ColumnsOutput[i]+separator;
								  }
								  if (i==(ColumnsOutput.length-1))
								  {
									  line=line+ColumnsOutput[i];
								  }
								  }
								  
					     }
						 else
						 {
							 line="EMPTY";
						 }
				 }
								  }
							
								 if(!line.equals("EMPTY"))
								 {
								 ColumnsOutput = line.split("\\"+separator);
								 }
								 else
								 {
									 aux="EMPTY";
								 }
				 }
					}
				 else
				 {
					 for(int i=0;i<ColumnsOutput.length;i++)
					  {
						  if (i<(ColumnsOutput.length-1))
						  {
						  line=line+ColumnsOutput[i]+separator;
						  }
						  if (i==(ColumnsOutput.length-1))
						  {
							  line=line+ColumnsOutput[i];
						  }
						  }
				 }
				 aux="";
				  //**********************************************************************************************************
				 if(!line.equals("EMPTY") )
				 {
					 if(!line.equals("") )		
					 context.write(new Text(line), new Text(""));
				 } 
	  }
}