package eis.lab.groupb;

/*
 * 
 * DONE BY HEUR. 2015(C)
 * 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import com.google.common.collect.Lists;

public class MainDriver
{
	int aux = 0;
	private JFrame frmBigDataIntegrator;
	private JTextField tfOutputFileName;
	
	static String uri;
	static String inputpath;
	static String temppath;
	static String outputpath;
	static String URIAnalysis;
	static String inputPathAnalysis;
	static String outputPathAnalysis;
	
	/******************************************************************************
	ANALYSIS PART
	******************************************************************************/
//analysis main panel
//JPanel containerAnalysis = new JPanel();
	
	
/*variables for panel: selection of input files */
	
//panel for Selecting input files
JPanel panelSelectInputFileAnalysis = new JPanel();
	
//label for Selecting input files
JLabel lblSelectInputFileAnalysis = new JLabel("Select Input Files");
	
//scroll panel for input file list
final JScrollPane scrPanelInputFIleAnalysis = new JScrollPane();
	
//list to display the files from HDFS (output folder from Integration)
@SuppressWarnings("rawtypes")
JList listInputFilesAnalysis;
	
//label to display number of selected files
JLabel lblSelectedInputAnalysis = new JLabel("0 files selected");
	
//button to execute selection input files
final JButton btnAddFileAnalysis = new JButton("Add");

JPanel panelInputQuery = new JPanel();

JPanel panelFieldNames = new JPanel(null);

//JScrollPane scrFieldNames = new JScrollPane();

//scroll pane for input list
JScrollPane scrInputList = null;

JTextPane txtAreaOutputAnalysis = new JTextPane();

//JList<String> listFieldNames = null;

JScrollPane scrInputQuery = new JScrollPane();
JTextArea txtAreaInputQuery = new JTextArea();
JPanel panelOutputAnalysis = new JPanel();
JLabel lblOutputAnalysis = new JLabel("Results");
JScrollPane scrOutputAnalysis = new JScrollPane();
JTextField txtFileNameToSave;

/*Spark Initialization*/
SparkConf conf = null;
JavaSparkContext sc = null;
DataFrame fileForAnalysis, results = null;
SQLContext sqlContext = null;

DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


/******************************************************************************
END ANALYSIS PART
******************************************************************************/
	
	public static void main(String[] args)  throws Exception 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainDriver window = new MainDriver();
					window.frmBigDataIntegrator.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	JList lColumns = new JList();
	JScrollPane pColumns = new JScrollPane();
	JScrollPane spOperations = new JScrollPane();
	
	JPanel pOutput = new JPanel();
 	JScrollPane spOutput = new JScrollPane();
 	String listColumns[];
 	
 	JList lInputFiles;
	JLabel lNumberColumns = new JLabel("0 column selected.");
	JLabel lNumberColumn = new JLabel("0 columns selected.");
	JLabel lNumberFiles = new JLabel("0 file selected.");
	JScrollPane spProcessing = new JScrollPane();
	JPanel pSelectColumns = new JPanel();
	JList lFinalOutputColumns = new JList();
	JScrollPane pFinalList = new JScrollPane();
	JPanel pInput = new JPanel();
	JButton bSelectFiles = new JButton("Select");
	JButton bSelectColumns = new JButton("Select");
	JButton btnSave_1 = new JButton("Save");
	JScrollPane spReadFile = new JScrollPane();
	
	@SuppressWarnings("rawtypes")
	JComboBox cbOperationsOperator = new JComboBox();
	@SuppressWarnings("rawtypes")
	JComboBox cbOperations = new JComboBox();

	
	int xx = 0;
	int numberadded = 1;
	int numberrename = 1;
	int numbermerge = 1;
	int numbersplit = 1;
	int numbercasing = 1;
	int numberformating = 1;
	int numberoperations = 1;
	int xxrename=0;
	int xxoperations=0;
	int xxmerge=0;
	int xxsplit=0;
	int xxcasing=0;
	int xxformating=0;
	
	
	String Separator="";
	
	List<JList> listofJLists = new ArrayList<JList>();
	List<JTextField> listofJTextFields = new ArrayList<JTextField>();
	
	List<JComboBox> listofJComboBoxRename = new ArrayList<JComboBox>();
	List<JTextField> listofJTextFieldsRename = new ArrayList<JTextField>();
	
	List<JComboBox> listofJComboBoxMerge1 = new ArrayList<JComboBox>();
	List<JComboBox> listofJComboBoxMerge2 = new ArrayList<JComboBox>();
	List<JTextField> listofJTextFieldsMerge = new ArrayList<JTextField>();
	
	List<JComboBox> listofJComboBoxSplit = new ArrayList<JComboBox>();
	List<JTextField> listofJTextFieldsSplit1 = new ArrayList<JTextField>();
	List<JTextField> listofJTextFieldsSplit2 = new ArrayList<JTextField>();
	List<JTextField> listofJTextFieldsSplitChar = new ArrayList<JTextField>();
	
	List<JComboBox> listofJComboBoxCasing = new ArrayList<JComboBox>();
	List<JComboBox> listofJComboBoxCasingChoices = new ArrayList<JComboBox>();
	
	List<JComboBox> listofJComboBoxFormating = new ArrayList<JComboBox>();
	List<JComboBox> listofJComboBoxFormatingChoices = new ArrayList<JComboBox>();
	
	List<JComboBox> listofJComboBoxOperations = new ArrayList<JComboBox>();
	List<JComboBox> listofJComboBoxOperationsOperators = new ArrayList<JComboBox>();
	List<JTextField> listofJTextFieldsOperations = new ArrayList<JTextField>();	
	
	JButton bPlusColumns = new JButton("+");
	JButton bGenerateFiles = new JButton("Run");
	
	 List<String[]> ListColFilePos = new ArrayList<String[]>();
	 String[][] arrays;
	 
	 String[][] selectedColumns;
	
	 List<String> listofFinalColumns = new ArrayList<String>();
	 
	 private final JPanel panel = new JPanel();
	 private final JPanel panel_1 = new JPanel();
	 private final JLabel lblIntegrationModule = new JLabel("Integration Module:");
	 private final JLabel lblOutputFilesPath = new JLabel("Output files path:");
	 private JTextField tfURI;
	 private JTextField tfInputPath;
	 private JTextField tfTempPath;
	 private JTextField tfOutputPath;
	 private JTextField tfSeparator;
	 private final JPanel pSplit = new JPanel();
	 private final JPanel pCasing = new JPanel();
	 private final JComboBox cbRename = new JComboBox();
	 private final JLabel lblTo = new JLabel("to");
	 private final JTextField tfRename = new JTextField();
	 private final JButton btnProceedRename = new JButton("Proceed");
	 private final JButton btnPlusRename = new JButton("+");
	 private final JComboBox cbMerge1 = new JComboBox();
	 private final JComboBox cbMerge2 = new JComboBox();
	 private final JLabel label_1 = new JLabel("+");
	 private final JLabel label_2 = new JLabel("=");
	 private final JTextField tfMerge = new JTextField();
	 private final JComboBox cbSplit = new JComboBox();
	 private final JLabel lblNewLabel = new JLabel("=");
	 private final JTextField tfSplit1 = new JTextField();
	 private final JTextField tfSplit2 = new JTextField();
	 private final JButton btnPlusMerge = new JButton("+");
	 private final JButton btnProceedMerge = new JButton("Proceed");
	 private final JButton btnPlusSplit = new JButton("+");
	 private final JButton btnProceedSplit = new JButton("Proceed");
	 private final JPanel pFormating = new JPanel();
	 private final JComboBox cbFormat = new JComboBox();
	 private final JLabel lblTo_1 = new JLabel("to");
	 private final JComboBox cbFormatOutput = new JComboBox();
	 private final JLabel lblAnd = new JLabel("and");
	 private final JComboBox cbCasing = new JComboBox();
	 private final JLabel label_3 = new JLabel("to");
	 private final JComboBox cbCasingResult = new JComboBox();
	 private final JButton btnPlusCasing = new JButton("+");
	 private final JButton bProceedCasing = new JButton("Proceed");
	 private final JButton btnPlusFormating = new JButton("+");
	 private final JButton btnProceedFormating = new JButton("Proceed");
	 private final JLabel lblRenameAColumn = new JLabel("Rename a column.");
	 private final JLabel lblMergeTwoColumns = new JLabel("Merge two columns in one");
	 private final JLabel lblSplitOneColumn = new JLabel("Split one column on two");
	 private final JLabel lblPos = new JLabel("Char");
	 private final JTextField tfChar = new JTextField();
	 private final JLabel lblCaseColumnValues = new JLabel("Case column values to UpperCase or LowerCase");
	 private final JLabel lblTemporalFilesPath = new JLabel("Temp files path:");
	 private final JLabel lblAgregationOperations = new JLabel("4. Restriction Operations (Optional):");
	 private JTextField tfOperations;
	 private final JButton bProceedOperations = new JButton("Proceed");
	 private final JTextArea taReadFile = new JTextArea();
		final JButton btnWrite = new JButton("Write");
		final JLabel lblLoad = new JLabel("No file loaded");

	 String xinputfile;
	 String xoutputfile;
	 String xseparator;
	 String xprojectedcolumnsid;
	 String xprojectedcolumnsname;
	 String xheader;
	 String xmergecolumns = "empty";
	 String xsplitcolumns = "empty";
	 String xsplitchar;
	 String xcasingcolums = "empty";
	 String xformatingcolumns = "empty";
	 String xoperations = "empty";
	 int load=0;
	 private final JLabel lblWrite = new JLabel("");
	 private final JLabel lblNewLabel_1 = new JLabel("Select columns, operators and assign values to be compared.");
	 final JButton bPlusOperations = new JButton("+");
	 private JTextField txtOutputPathAnalysisPath;
	 private JTextField tfMergeChar;
	 private JTextField txtSeparatorAnalysis;
	 private JTextField txtInputFilePathAnalysis = new JTextField();
	 private JTextField txtURIAnalysis = new JTextField();
	 private final JLabel lblDevelopedBy = new JLabel("Developed by");
	 private final JLabel lblNewLabel_2 = new JLabel("Boroukhian Tina");
	 private final JLabel lblNewLabel_3 = new JLabel("Kumar Gaurav");
	 private final JLabel lblM = new JLabel("Mármol Miguel");
	 private final JLabel lblUgarteHctor = new JLabel("Ugarte Héctor");
	 private final JLabel lblUniversityOfBonn = new JLabel("University of Bonn, 2015 ®");
		public MainDriver() 
		{	 
	    	
			BufferedReader br = null;
		        try 
		        {
//		            String sCurrentLine;
		            br = new BufferedReader(new FileReader("/home/hduser/Desktop/Settings"));
		            int a = 0;
		            for (String s; (s = br.readLine()) != null; ) 
		            {
		            	a++;
		            	if(a==1)
		                uri = s;
		            	if(a==2)
		            	inputpath = s;
		            	if(a==3)
		            	temppath = s;
		            	if(a==4)
		            	outputpath = s;
		            	if(a==5)
		            		URIAnalysis = s;
		            	if (a==6)
		            		inputPathAnalysis = s;
		            	if(a==7)
		            		outputPathAnalysis = s;
		            	
		            }

		        } catch (IOException e) 
		        {
		            e.printStackTrace();
		        } finally 
		        {
		            try 
		            {
		                if (br != null)br.close();
		            } catch (IOException ex) 
		            {
		                ex.printStackTrace();
		            }
		        }

		        initialize();
		        
		        tfURI.setText(uri);
		        tfInputPath.setText(inputpath);
		        tfTempPath.setText(temppath);
		        tfOutputPath.setText(outputpath);
		        txtURIAnalysis.setText(URIAnalysis);
		        txtInputFilePathAnalysis.setText(inputPathAnalysis);
		        txtOutputPathAnalysisPath.setText(outputPathAnalysis);
		        
		        JLabel lblInputFIlePathAnalysis = new JLabel("Input files path:");
		        lblInputFIlePathAnalysis.setBounds(10, 50, 130, 15);
		        panel_1.add(lblInputFIlePathAnalysis);
		        
		       
		        txtInputFilePathAnalysis.setBounds(141, 50, 410, 19);
		        panel_1.add(txtInputFilePathAnalysis);
		        txtInputFilePathAnalysis.setColumns(10);
		        
		        JLabel lblURIAnalysis = new JLabel("URI:");
		        lblURIAnalysis.setBounds(10, 25, 70, 15);
		        panel_1.add(lblURIAnalysis);
		        
		        
		        txtURIAnalysis.setBounds(141, 25, 410, 19);
		        panel_1.add(txtURIAnalysis);
		        txtURIAnalysis.setColumns(10);
		        lblTemporalFilesPath.setBounds(10, 85, 132, 15);
		        
		        panel.add(lblTemporalFilesPath);
		        
		   
		        
		        String[] listDatas = new String[2];
		        listDatas[0] ="UpperCase";
		        listDatas[1] ="LowerCase";
				for(String str : listDatas) 
				{		
					 cbCasingResult.addItem(str);
				}
				
				String[] listDatas2 = new String[3];
				listDatas2[0] = "DD/MM/YYYY";
				listDatas2[1] = "MM/DD/YYYY";
				listDatas2[2] = "YYYY/MM/DD";
		
				for(String str : listDatas2) 
				{		
					 cbFormatOutput.addItem(str);
				}

				String[] listDatas3 = new String[9];
				listDatas3[0] = "=";
				listDatas3[1] = "<>";
				listDatas3[2] = ">";
				listDatas3[3] = "<";
				listDatas3[4] = ">=";
				listDatas3[5] = "<=";
				listDatas3[6] = "EQUAL";
				listDatas3[7] = "NOT EQUAL";
				listDatas3[8] = "CONTAINS";

				for(String str : listDatas3) 
				{		
					 cbOperationsOperator.addItem(str);
				}
		}
	 
	 
	 public static void ShowDialog(Boolean option)
	 {
		 if (option == true)
		 {

		 }
		 else
		 {
			 JOptionPane optionPane = new JOptionPane("Hello world", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
			 JDialog dialog = new JDialog();
			 dialog.setTitle("Message");
			 dialog.setModal(true);
			 dialog.setContentPane(optionPane);
			 dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			 dialog.pack();
			 dialog.setVisible(true);
		 }	 
	 }
	 
	 public static void RemoveFileHDFS(String input)
	 {
		 FileSystem fs;
		 try 
			{
				fs = FileSystem.get(new URI(uri), new Configuration());
				fs.delete(new Path(input),true);												
			}
			        catch (IOException | URISyntaxException e1) 
			        {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}	 
	 }
	 
	 public static void RenameFileHDFS(String input, String output)
	 {
		   FileSystem fs;
			try 
			{
				fs = FileSystem.get(new URI(uri), new Configuration());	
				fs.rename(new Path(input), new Path(output));
			}
			        catch (IOException | URISyntaxException e1) 
			        {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				} 
	 }
	 
	 public static String[] ReadFilesHDFS(String input)
	 {
			/*
			 * Populate the List with the files from HDFS.
			 */
			  List<String> auxList = new ArrayList<String>();      
		      //1. Get the Configuration instance
		      Configuration configuration = new Configuration();
		      //2. Get the instance of the HDFS
		      FileSystem hdfs = null;
				try 
				{
					hdfs = FileSystem.get(new URI(uri), configuration);
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      //3. Get the metadata of the desired directory
		      FileStatus[] fileStatus = null;
				try {
					fileStatus = hdfs.listStatus(new Path(input));
				} catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      //4. Using FileUtil, getting the Paths for all the FileStatus
		      Path[] paths = FileUtil.stat2Paths(fileStatus);
		      //5. Iterate through the directory and display the files in it
		      for(Path path : paths)
		      {
		    	  if(path.getName().toString().contains(".csv"))
		    	  auxList.add(path.getName().toString());
		      }
		      
		      return auxList.toArray(new String[auxList.size()]);	 
	 }
	 
	 
	private void initialize() 
	{
		frmBigDataIntegrator = new JFrame();
		frmBigDataIntegrator.setTitle("llama Big Data Integration & Analysis");
		frmBigDataIntegrator.setBounds(100, 100, 629, 680);
		frmBigDataIntegrator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBigDataIntegrator.getContentPane().setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 12, 601, 640);
		frmBigDataIntegrator.getContentPane().add(tabbedPane);
		JPanel pTabIntegration = new JPanel();
		tabbedPane.addTab("Data Integration", null, pTabIntegration, null);
		pTabIntegration.setLayout(null);
			
			final JLabel lblOutput = new JLabel("5. Output files:");
			lblOutput.setVisible(false);
			lblOutput.setBounds(6, 410, 180, 15);
			pTabIntegration.add(lblOutput);
			lblOutput.setForeground(Color.RED);
		
			final JLabel lblIntegrationOperations = new JLabel("3. Transformation operations (Optional):");
			lblIntegrationOperations.setForeground(Color.RED);
			lblIntegrationOperations.setBounds(6, 173, 344, 15);
			pTabIntegration.add(lblIntegrationOperations);
			lblIntegrationOperations.setVisible(false);
		
		final JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				  JFileChooser fileChooser = new JFileChooser();
				  fileChooser.setApproveButtonText("Load");
					fileChooser.setDialogTitle("Specify a file to open");  
					if (fileChooser.showOpenDialog(frmBigDataIntegrator) == JFileChooser.APPROVE_OPTION) 
					{
						File file = fileChooser.getSelectedFile();
						lblLoad.setText(file.getName()+" loaded.");
						
						BufferedReader br = null;
				        try 
				        {
//				            String sCurrentLine;
				            br = new BufferedReader(new FileReader(file));
				          //  int a = 0;
				            for (String s; (s = br.readLine()) != null; ) 
				            {
				            	taReadFile.append(s+"\n");
				            }

				        } catch (IOException ef) 
				        {
				            ef.printStackTrace();
				        } 
				        finally 
				        {
				            try 
				            {
				                if (br != null)br.close();
				            } catch (IOException ex) 
				            {
				                ex.printStackTrace();
				            }
				        }

				        btnSave_1.setVisible(true);
				    
						load =1;
						btnLoad.setEnabled(false);
						bGenerateFiles.setEnabled(true);
						spReadFile.setVisible(true);
						
						btnWrite.setEnabled(false);
						btnLoad.setEnabled(false);
						bSelectFiles.setEnabled(false);
						lInputFiles.setEnabled(false);
						tfSeparator.setEnabled(false);
						
						spReadFile.setBounds(12,185,572,300);
						spReadFile.revalidate();
					    spReadFile.repaint();
					}
			}
		});
		btnLoad.setBounds(502, 0, 80, 25);
		pTabIntegration.add(btnLoad);
		pInput.setBounds(12, 24, 291, 139);
		pTabIntegration.add(pInput);
		pInput.setLayout(null);	
		JLabel lblSelectInputFiles = new JLabel("1. Input file:");
		lblSelectInputFiles.setForeground(Color.RED);
		lblSelectInputFiles.setBounds(0, 0, 123, 15);
		pInput.add(lblSelectInputFiles);	
		
		JScrollPane pInputList = new JScrollPane();
		pInputList.setBounds(10, 12, 275, 81);
		pInput.add(pInputList);
		
		//	ReadFiles and populate them in the JList
	      lInputFiles = new JList( ReadFilesHDFS(inputpath) );
	      
	      //Modify value of label with the number of rows selected
	      lInputFiles.addListSelectionListener(new ListSelectionListener() 
			{
				public void valueChanged(ListSelectionEvent e) 
				{
					lNumberFiles.setText(Integer.toString(lInputFiles.getSelectedIndices().length)+" files selected.");
				}
			});
			pInputList.setViewportView(lInputFiles);
	
		tfSeparator = new JTextField();
		tfSeparator.setText(",");
	   
		final List<List<String>> listOfColumns = Lists.newArrayList();
		final List<List<String>> listOfSelectedColumns = Lists.newArrayList();
		final List<List<String>> listOfSelectedColumnsAux = Lists.newArrayList();
		final List<List<String>> listOfMergeColumns = Lists.newArrayList();
		final List<List<String>> listOfSplitColumns = Lists.newArrayList();
		final List<List<String>> listOfCasingColumns = Lists.newArrayList();
		final List<List<String>> listOfFormatingColumns = Lists.newArrayList();	
		final List<List<String>> listOfOperations = Lists.newArrayList();
	
		//Select files
		bSelectFiles.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{		
				if(lInputFiles.getSelectedValuesList().size() > 0)
				{
					btnLoad.setEnabled(false);
					btnWrite.setEnabled(false);
				Separator = tfSeparator.getText();
				lInputFiles.setEnabled(false);
				pSelectColumns.setVisible(true);
				bSelectFiles.setEnabled(false);
				tfSeparator.setEnabled(false);

				String input = "";
				
				 List objs = lInputFiles.getSelectedValuesList();
				 int gg=0;
				 for (int i=0;i<objs.size();i++)
				 {	 
					 //Read the first row. Name of the columns
					 input = inputpath+objs.get(i).toString();
					
					 					 FileSystem fs;				
					 try 
									{
										fs = FileSystem.get(new URI(uri), new Configuration());
										 BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(new Path(input))));
									        String line;
									        line=br.readLine();

									      
									        	listColumns = line.split("\\"+Separator);									     
									        								     	
									        	for (int j=0;j<listColumns.length;j++)
									        	{
									        		//id,posinfile,name,file
									        		listOfColumns.add(Lists.newArrayList(Integer.toString(gg),Integer.toString(j),listColumns[j],objs.get(i).toString()));
									        		gg++;
									        	}
									        	//populate the list with the columns names
									        	String listDatas[] = new String[listOfColumns.size()];
										        	
									        	for(int k = 0;k<listOfColumns.size();k++)
									        	{  		
									        		listDatas[k] = listOfColumns.get(k).get(2);//+" --> "+listOfColumns.get(k).get(3);
									        	}
									        
									        	lColumns.setListData(listDatas);
									       pColumns.revalidate();
									       pColumns.repaint();
									         
									}
									        catch (IOException | URISyntaxException e1) 
									        {
										// TODO Auto-generated catch block
										e1.printStackTrace();
										}
									//Remove temporal file from HDFS 
				 }
				 		 
				 arrays = ListColFilePos.toArray(new String[][] {});
			}
			}
		});
		//change label value with the number of columns
		lColumns.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
			{
				lNumberColumn.setText(Integer.toString(lColumns.getSelectedIndices().length)+" columns selected.");	
			}
		});
	
		//allow multiple selection on list
		lColumns.setSelectionModel(new DefaultListSelectionModel() 
		{
		    @Override
		    public void setSelectionInterval(int index0, int index1) 
		    {
		        if(super.isSelectedIndex(index0)) 
		        {
		            super.removeSelectionInterval(index0, index1);
		        }
		        else {
		            super.addSelectionInterval(index0, index1);
		        }
		    }
		});
	
		bSelectFiles.setBounds(194, 107, 86, 25);
		pInput.add(bSelectFiles);
		lNumberFiles.setVisible(false);
		lNumberFiles.setForeground(Color.GRAY);
		lNumberFiles.setBounds(194, 94, 123, 15);
		pInput.add(lNumberFiles);
		
		JLabel lblSeparator = new JLabel("Separator");
		lblSeparator.setBounds(10, 112, 80, 15);
		pInput.add(lblSeparator);	
		tfSeparator.setBounds(85, 108, 25, 19);
		pInput.add(tfSeparator);
		tfSeparator.setColumns(10);
		
		
			pSelectColumns.setBounds(305, 24, 291, 139);
			pTabIntegration.add(pSelectColumns);
			pSelectColumns.setLayout(null);
			pColumns.setBounds(10, 12, 275, 83);
			pSelectColumns.add(pColumns);
			pColumns.setViewportView(lColumns);
			
			bSelectColumns.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					lblOutput.setVisible(true);
					spOperations.setVisible(true);
					lblAgregationOperations.setVisible(true);
					
					
					if(lColumns.getSelectedValuesList().size() > 0)
					{
				    bGenerateFiles.setEnabled(true);
					listofJLists.add(lFinalOutputColumns);
					listofJTextFields.add(tfOutputFileName);	
					
					listofJComboBoxRename.add(cbRename);
					listofJTextFieldsRename.add(tfRename);
					
					listofJComboBoxMerge1.add(cbMerge1);
					listofJComboBoxMerge2.add(cbMerge2);
					listofJTextFieldsMerge.add(tfMerge);
					
					listofJComboBoxSplit.add(cbSplit);
					listofJTextFieldsSplit1.add(tfSplit1);
					listofJTextFieldsSplit2.add(tfSplit2);
					listofJTextFieldsSplitChar.add(tfChar);
					
					listofJComboBoxCasing.add(cbCasing);
					listofJComboBoxCasingChoices.add(cbCasingResult);
					
					listofJComboBoxFormating.add(cbFormat);
					listofJComboBoxFormatingChoices.add(cbFormatOutput);
					
					listofJComboBoxOperations.add(cbOperations);
					listofJComboBoxOperationsOperators.add(cbOperationsOperator);
					listofJTextFieldsOperations.add(tfOperations);
					
			
					lblIntegrationOperations.setVisible(true);
					lColumns.setEnabled(false);
					bSelectColumns.setEnabled(false);
				
					List objs = lColumns.getSelectedValuesList();
					int[] SelectedIndices = lColumns.getSelectedIndices();
							
					for (int t=0;t<SelectedIndices.length;t++)
					{
						for (int u=0;u<listOfColumns.size();u++)
						{
						if(SelectedIndices[t]==Integer.parseInt(listOfColumns.get(u).get(0)))	
						{
							//idonselected,realid,posinfile,name,file
							listOfSelectedColumns.add(Lists.newArrayList(Integer.toString(t),listOfColumns.get(u).get(0),listOfColumns.get(u).get(1),listOfColumns.get(u).get(2),listOfColumns.get(u).get(3)));
						listOfSelectedColumnsAux.add(Lists.newArrayList(Integer.toString(t),listOfColumns.get(u).get(0),listOfColumns.get(u).get(1),listOfColumns.get(u).get(2),listOfColumns.get(u).get(3)));
						}
						}
					}
					
				
					String listDatas[] = new String[listOfSelectedColumns.size()];
		        	
		        	for(int k = 0;k<listOfSelectedColumns.size();k++)
		        	{
		        		
		        		listDatas[k] = listOfSelectedColumns.get(k).get(3);//+" --> "+listOfSelectedColumns.get(k).get(4);
		        	}
					

					spProcessing.setVisible(true);
					spOutput.setVisible(true);
					
					for (int i=0;i<objs.size();i++)
					{
						listofFinalColumns.add(objs.get(i).toString());
					}
					
					String[] listCol = listofFinalColumns.toArray(new String[listofFinalColumns.size()]);
					lFinalOutputColumns.setListData(listCol);
				
					for(String str : listDatas) 
					{
						   cbRename.addItem(str);
						   cbMerge1.addItem(str);
						   cbMerge2.addItem(str);
						   cbSplit.addItem(str);
						   cbCasing.addItem(str);
						   cbFormat.addItem(str);
						   cbOperations.addItem(str);
					}
					
					pFinalList.revalidate();
					pFinalList.repaint();
					pOutput.revalidate();
					pOutput.repaint();
					spOutput.revalidate();
					spOutput.repaint();
				}
					
					
					
					
					
				}
			});
			bSelectColumns.setBounds(187, 107, 92, 25);
			pSelectColumns.add(bSelectColumns);			
			JLabel lblSelectDesiredColumns = new JLabel("2. Desired columns:");
			lblSelectDesiredColumns.setForeground(Color.RED);
			lblSelectDesiredColumns.setBounds(0, 0, 212, 15);
			pSelectColumns.add(lblSelectDesiredColumns);
			lNumberColumn.setForeground(Color.GRAY);
			
			lNumberColumn.setBounds(116, 94, 169, 15);
			pSelectColumns.add(lNumberColumn);
			spProcessing.setBackground(Color.GRAY);
			spProcessing.setForeground(Color.RED);
			
			spProcessing.setBounds(12, 185, 572, 108);
			pTabIntegration.add(spProcessing);
					tfChar.setText(" ");
			
					tfChar.setBounds(505, 25, 23, 19);
					tfChar.setColumns(10);
					tfSplit2.setBounds(347, 24, 114, 19);
					tfSplit2.setColumns(10);
					tfSplit1.setBounds(203, 24, 114, 19);
					tfSplit1.setColumns(10);
					tfMerge.setBounds(262, 55, 222, 19);
					tfMerge.setColumns(10);
					
					final JTabbedPane tabsIntegration = new JTabbedPane(JTabbedPane.TOP);
					tabsIntegration.setBackground(Color.CYAN);
					spProcessing.setViewportView(tabsIntegration);
					
					final JPanel pMerge = new JPanel();
					pMerge.setBackground(Color.WHITE);
					tabsIntegration.addTab("Merge", null, pMerge, null);
					pMerge.setLayout(null);
					cbMerge1.setBounds(12, 24, 222, 24);
					
					pMerge.add(cbMerge1);
					cbMerge2.setBounds(262, 24, 222, 24);
					
					pMerge.add(cbMerge2);
					label_1.setBounds(241, 29, 61, 15);
					
					pMerge.add(label_1);
					label_2.setBounds(238, 56, 19, 15);
					
					pMerge.add(label_2);
					
					pMerge.add(tfMerge);
					btnPlusMerge.setVisible(false);
					btnPlusMerge.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							xxmerge = xxmerge+46;
							btnProceedMerge.setBounds(85, 51+xxmerge, 82, 25);
							btnPlusMerge.setBounds(12,52+xxmerge,61,25);
							
							pMerge.setPreferredSize(new Dimension(pMerge.getSize().width, pMerge.getSize().height + 46));
							
							listofJComboBoxMerge1.add(new JComboBox());
							listofJComboBoxMerge2.add(new JComboBox());
							
							listofJTextFieldsMerge.add(new JTextField());	
							
							listofJComboBoxMerge1.get(numbermerge).setBounds(12, 24+ xxmerge, 222, 24);
							listofJComboBoxMerge2.get(numbermerge).setBounds(262, 24+ xxmerge, 222, 24);							
							listofJTextFieldsMerge.get(numbermerge).setBounds(262, 55+ xxmerge, 222, 19);
							
							for (int k=0;k<cbMerge1.getModel().getSize();k++)
							{
								listofJComboBoxMerge1.get(numbermerge).addItem(cbMerge1.getModel().getElementAt(k).toString());
								listofJComboBoxMerge2.get(numbermerge).addItem(cbMerge2.getModel().getElementAt(k).toString());
							}
					
							pMerge.add(listofJComboBoxMerge1.get(numbermerge));
							pMerge.add(listofJComboBoxMerge2.get(numbermerge));
							pMerge.add(listofJTextFieldsMerge.get(numbermerge));
							
							numbermerge++;			
							pMerge.revalidate();
							pMerge.repaint();
					
							
						}
					});
					btnPlusMerge.setBounds(12, 52, 61, 25);
					
					pMerge.add(btnPlusMerge);
					
					btnProceedMerge.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							int SelectedIndex1 = cbMerge1.getSelectedIndex();
							int SelectedIndex2 = cbMerge2.getSelectedIndex();
							
							int IndexOfColumnstoMerge1 = Integer.parseInt(listOfSelectedColumnsAux.get(SelectedIndex1).get(1));
							int IndexOfColumnstoMerge2 = Integer.parseInt(listOfSelectedColumnsAux.get(SelectedIndex2).get(1));
							
							String nameofFile = listOfSelectedColumnsAux.get(SelectedIndex2).get(4);
							
							listOfMergeColumns.add(Lists.newArrayList(Integer.toString(IndexOfColumnstoMerge1),Integer.toString(IndexOfColumnstoMerge2),tfMergeChar.getText(),nameofFile));
							
							for(int j=0;j<listOfSelectedColumns.size();j++)
							{
								if(SelectedIndex1 == Integer.parseInt(listOfSelectedColumns.get(j).get(0)))
								{
									//idonselected,realid,posinfile,name,file
									listOfSelectedColumns.get(j).set(3, tfMerge.getText());
								}
							}
							
							for(int j=0;j<listOfSelectedColumns.size();j++)
							{
								if(SelectedIndex2 == Integer.parseInt(listOfSelectedColumns.get(j).get(0)))
								{
									listOfSelectedColumns.remove(j);
								}
							}		
							String listDatas[] = new String[listOfSelectedColumns.size()];
							for(int k = 0;k<listOfSelectedColumns.size();k++)
		        	{
		        		
		        		listDatas[k] = listOfSelectedColumns.get(k).get(3);//+" --> "+listOfSelectedColumns.get(k).get(4);
		        	}
							
							lFinalOutputColumns.removeAll();
							lFinalOutputColumns.setListData(listDatas);
					        	
							cbRename.removeAllItems();
							cbMerge1.removeAllItems();
							cbMerge2.removeAllItems();
							cbSplit.removeAllItems();
							cbCasing.removeAllItems();
							cbFormat.removeAllItems();
							cbOperations.removeAllItems();
							for(String str : listDatas) 
							{		
								   cbRename.addItem(str);
								   cbMerge1.addItem(str);
								   cbMerge2.addItem(str);
								   cbSplit.addItem(str);
								   cbCasing.addItem(str);	
								   cbFormat.addItem(str);
								   cbOperations.addItem(str);
							}
									
							btnProceedRename.setEnabled(false);
							btnPlusRename.setEnabled(false);
							btnProceedSplit.setEnabled(false);
							bProceedCasing.setEnabled(false);
							btnProceedFormating.setEnabled(false);
							bPlusOperations.setEnabled(false);
							bProceedOperations.setEnabled(false);
							
							
							pFinalList.revalidate();
							pFinalList.repaint();
							pOutput.revalidate();
							pOutput.repaint();
							spOutput.revalidate();
							spOutput.repaint();
						
							btnProceedMerge.setEnabled(false);
							btnPlusMerge.setEnabled(false);		
						}
					});
					
					
					btnProceedMerge.setBounds(85, 51, 95, 25);
					
					pMerge.add(btnProceedMerge);
					lblMergeTwoColumns.setBounds(12, 8, 311, 15);
					
					pMerge.add(lblMergeTwoColumns);
					
					JLabel label_4 = new JLabel("Char");
					label_4.setBounds(492, 29, 35, 15);
					pMerge.add(label_4);
					
					tfMergeChar = new JTextField();
					tfMergeChar.setText(" ");
					tfMergeChar.setColumns(10);
					tfMergeChar.setBounds(529, 27, 23, 19);
					pMerge.add(tfMergeChar);
					pSplit.setBackground(Color.WHITE);
					
					tabsIntegration.addTab("Split", null, pSplit, null);
					pSplit.setLayout(null);
					cbSplit.setBounds(12, 24, 170, 24);
					
					pSplit.add(cbSplit);
					lblNewLabel.setBounds(187, 24, 61, 15);
					
					pSplit.add(lblNewLabel);
					pSplit.add(tfSplit1);
					
					pSplit.add(tfSplit2);
					btnPlusSplit.setVisible(false);
					btnPlusSplit.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							xxsplit = xxsplit+23;
							btnProceedSplit.setBounds(454, 52+xxsplit, 82, 25);
							btnPlusSplit.setBounds(12,52+xxsplit,61,25);
							
							pSplit.setPreferredSize(new Dimension(pSplit.getSize().width, pSplit.getSize().height + 23));
							
							listofJComboBoxSplit.add(new JComboBox());	
							listofJTextFieldsSplit1.add(new JTextField());	
							listofJTextFieldsSplit2.add(new JTextField());	
							listofJTextFieldsSplitChar.add(new JTextField());
							
							listofJComboBoxSplit.get(numbersplit).setBounds(12, 24+ xxsplit, 170, 24);
							listofJTextFieldsSplit1.get(numbersplit).setBounds(203, 24+ xxsplit, 114, 19);
							listofJTextFieldsSplit2.get(numbersplit).setBounds(347, 24+ xxsplit, 114, 19);
							listofJTextFieldsSplitChar.get(numbersplit).setBounds(505, 25+ xxsplit, 23, 19);
							
							for (int k=0;k<cbSplit.getModel().getSize();k++)
							{
								listofJComboBoxSplit.get(numbersplit).addItem(cbSplit.getModel().getElementAt(k).toString());
							}
						
							pSplit.add(listofJComboBoxSplit.get(numbersplit));
							pSplit.add(listofJTextFieldsSplit1.get(numbersplit));
							pSplit.add(listofJTextFieldsSplit2.get(numbersplit));
							pSplit.add(listofJTextFieldsSplitChar.get(numbersplit));
							
							numbersplit++;				
							pSplit.revalidate();
							pSplit.repaint();

						}
					});
					btnPlusSplit.setBounds(12, 52, 61, 25);
					
					pSplit.add(btnPlusSplit);
					btnProceedSplit.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							int SelectedIndex = cbSplit.getSelectedIndex();
							
							int IndexOfColumnstoSplit = Integer.parseInt(listOfSelectedColumns.get(SelectedIndex).get(1));
							String nameofFile = listOfSelectedColumns.get(SelectedIndex).get(4);
							
							listOfSplitColumns.add(Lists.newArrayList(Integer.toString(IndexOfColumnstoSplit),nameofFile));
							
							listOfSelectedColumns.get(SelectedIndex).set(3, tfSplit1.getText());
							//final List<List<String>> listOfMergeColumns = Lists.newArrayList();
											
							List<String> addedelement = Lists.newArrayList(Integer.toString(listOfSelectedColumns.size()),Integer.toString(listOfColumns.size()),Integer.toString(1000),tfSplit2.getText(),listOfSelectedColumns.get(SelectedIndex).get(4));
							listOfSelectedColumns.add(SelectedIndex+1,addedelement);
							//listOfSelectedColumns.remove(SelectedIndex2);
							String listDatas[] = new String[listOfSelectedColumns.size()];
							for(int k = 0;k<listOfSelectedColumns.size();k++)
		        	{
		        		
		        		listDatas[k] = listOfSelectedColumns.get(k).get(3);//+" --> "+listOfSelectedColumns.get(k).get(4);
		        	}
							
							lFinalOutputColumns.removeAll();
							
							lFinalOutputColumns.setListData(listDatas);
					        	
							cbRename.removeAllItems();
							cbMerge1.removeAllItems();
							cbMerge2.removeAllItems();
							cbSplit.removeAllItems();
							cbCasing.removeAllItems();
							cbOperations.removeAllItems();
							cbFormat.removeAllItems();
							for(String str : listDatas) 
							{		
								   cbRename.addItem(str);
								   cbMerge1.addItem(str);
								   cbMerge2.addItem(str);
								   cbSplit.addItem(str);
								   cbCasing.addItem(str);
								   cbOperations.addItem(str);
								   cbFormat.removeAllItems();
							}
							btnProceedSplit.setEnabled(false);
							btnPlusSplit.setEnabled(false);
							
							btnProceedRename.setEnabled(false);
							btnPlusRename.setEnabled(false);
							btnProceedMerge.setEnabled(false);
							bProceedCasing.setEnabled(false);
							btnProceedFormating.setEnabled(false);
							bPlusOperations.setEnabled(false);
							bProceedOperations.setEnabled(false);
							
							
							pFinalList.revalidate();
							pFinalList.repaint();
							pOutput.revalidate();
							pOutput.repaint();
							spOutput.revalidate();
							spOutput.repaint();					
					
						}
					});
					btnProceedSplit.setBounds(454, 52, 95, 25);
					
					pSplit.add(btnProceedSplit);
					lblAnd.setBounds(320, 24, 61, 15);
					
					pSplit.add(lblAnd);
					lblSplitOneColumn.setBounds(12, 8, 311, 15);
					
					pSplit.add(lblSplitOneColumn);
					lblPos.setBounds(468, 25, 35, 15);
					
					pSplit.add(lblPos);
					
					pSplit.add(tfChar);
					pCasing.setBackground(Color.WHITE);
					
					tabsIntegration.addTab("Casing", null, pCasing, null);
					pCasing.setLayout(null);
					cbCasing.setBounds(12, 24, 222, 24);
					
					pCasing.add(cbCasing);
					label_3.setBounds(252, 29, 61, 15);
					
					pCasing.add(label_3);
					cbCasingResult.setBounds(279, 24, 222, 24);
					
					pCasing.add(cbCasingResult);
					btnPlusCasing.setVisible(false);
					btnPlusCasing.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							xxcasing = xxcasing+23;
							bProceedCasing.setBounds(454, 52+xxcasing, 82, 25);
							btnPlusCasing.setBounds(12,52+xxcasing,61,25);
							
							pCasing.setPreferredSize(new Dimension(pCasing.getSize().width, pCasing.getSize().height + 23));
							
							listofJComboBoxCasing.add(new JComboBox());	
							listofJComboBoxCasingChoices.add(new JComboBox());
							
							
							listofJComboBoxCasing.get(numbercasing).setBounds(12, 24+ xxcasing, 222, 24);
							listofJComboBoxCasingChoices.get(numbercasing).setBounds(279, 24+ xxcasing, 222, 24);
														
							for (int k=0;k<cbCasing.getModel().getSize();k++)
							{
								listofJComboBoxCasing.get(numbercasing).addItem(cbCasing.getModel().getElementAt(k).toString());
							}
							
							for (int k=0;k<cbCasingResult.getModel().getSize();k++)
							{
								listofJComboBoxCasingChoices.get(numbercasing).addItem(cbCasingResult.getModel().getElementAt(k).toString());
							}
													
							pCasing.add(listofJComboBoxCasing.get(numbercasing));
							pCasing.add(listofJComboBoxCasingChoices.get(numbercasing));
							
							numbercasing++;			
							pCasing.revalidate();
							pCasing.repaint();

						}
					});
					btnPlusCasing.setBounds(12, 52, 61, 25);
					
					pCasing.add(btnPlusCasing);
					bProceedCasing.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							int SelectedIndex = cbCasing.getSelectedIndex();
							
							int IndexOfColumnstoCase = Integer.parseInt(listOfSelectedColumns.get(SelectedIndex).get(1));
							String nameofFile = listOfSelectedColumns.get(SelectedIndex).get(4);
							
							listOfCasingColumns.add(Lists.newArrayList(Integer.toString(IndexOfColumnstoCase),Integer.toString(cbCasingResult.getSelectedIndex()),nameofFile));				
							bProceedCasing.setEnabled(false);
							btnProceedSplit.setEnabled(false);
							btnPlusCasing.setEnabled(false);
							btnProceedRename.setEnabled(false);
							btnPlusRename.setEnabled(false);
							btnProceedMerge.setEnabled(false);
						}
					});
					bProceedCasing.setBounds(454, 52, 95, 25);
					
					pCasing.add(bProceedCasing);
					lblCaseColumnValues.setBounds(12, 8, 311, 15);
					
					pCasing.add(lblCaseColumnValues);
					pFormating.setBackground(Color.WHITE);
					
					tabsIntegration.addTab("Formatting", null, pFormating, null);
					pFormating.setLayout(null);
					cbFormat.setBounds(12, 24, 222, 24);
					
					pFormating.add(cbFormat);
					lblTo_1.setBounds(252, 29, 61, 15);
					
					pFormating.add(lblTo_1);
					cbFormatOutput.setBounds(279, 24, 222, 24);
					
					pFormating.add(cbFormatOutput);
					btnPlusFormating.setVisible(false);
					btnPlusFormating.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							xxformating = xxformating+23;
							btnProceedFormating.setBounds(454, 52+xxformating, 82, 25);
							btnPlusFormating.setBounds(12,52+xxformating,61,25);
							
							pFormating.setPreferredSize(new Dimension(pFormating.getSize().width, pFormating.getSize().height + 23));
							
							listofJComboBoxFormating.add(new JComboBox());	
							listofJComboBoxFormatingChoices.add(new JComboBox());
							
							
							listofJComboBoxFormating.get(numberformating).setBounds(12, 24+ xxformating, 222, 24);
							listofJComboBoxFormatingChoices.get(numberformating).setBounds(279, 24+ xxformating, 222, 24);
														
							for (int k=0;k<cbFormat.getModel().getSize();k++)
							{
								listofJComboBoxFormating.get(numberformating).addItem(cbFormat.getModel().getElementAt(k).toString());
							}
							
							for (int k=0;k<cbFormatOutput.getModel().getSize();k++)
							{
								listofJComboBoxFormatingChoices.get(numberformating).addItem(cbFormatOutput.getModel().getElementAt(k).toString());
							}
													
							pFormating.add(listofJComboBoxFormating.get(numberformating));
							pFormating.add(listofJComboBoxFormatingChoices.get(numberformating));
							
							numberformating++;			
							pFormating.revalidate();
							pFormating.repaint();

						}
					});
					btnPlusFormating.setBounds(12, 52, 61, 25);
					
					pFormating.add(btnPlusFormating);
					btnProceedFormating.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							int SelectedIndex = cbFormat.getSelectedIndex();
							
							int IndexOfColumnstoFormat = Integer.parseInt(listOfSelectedColumns.get(SelectedIndex).get(1));
							String nameofFile = listOfSelectedColumns.get(SelectedIndex).get(4);
							
							listOfFormatingColumns.add(Lists.newArrayList(Integer.toString(IndexOfColumnstoFormat),cbFormatOutput.getSelectedItem().toString(),nameofFile));				
						
							btnProceedFormating.setEnabled(false);
							btnPlusFormating.setEnabled(false);
							btnProceedSplit.setEnabled(false);
							btnProceedRename.setEnabled(false);
							btnPlusRename.setEnabled(false);
							btnProceedMerge.setEnabled(false);
						}
					});
					btnProceedFormating.setBounds(454, 52, 95, 25);
					
					pFormating.add(btnProceedFormating);
					
					JLabel lblFormatDateColumns = new JLabel("Format Date columns");
					lblFormatDateColumns.setBounds(12, 8, 311, 15);
					pFormating.add(lblFormatDateColumns);
					tfRename.setBounds(273, 27, 222, 19);
					tfRename.setColumns(10);
					
					final JPanel pRename = new JPanel();
					pRename.setBackground(SystemColor.text);
					tabsIntegration.addTab("Rename", null, pRename, null);
					pRename.setLayout(null);
					cbRename.setBounds(12, 24, 222, 24);
					
					pRename.add(cbRename);
					lblTo.setBounds(249, 29, 18, 15);
					
					pRename.add(lblTo);
					
					pRename.add(tfRename);
					btnProceedRename.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							for (int i=0;i<listofJComboBoxRename.size();i++)
							{
							//listofJComboBoxRename.add(new JComboBox());
							//listofJTextFieldsRename.add(new JTextField());	
							int SelectedIndex = listofJComboBoxRename.get(i).getSelectedIndex();
							int IndexOfColumnstoRename = Integer.parseInt(listOfSelectedColumns.get(SelectedIndex).get(1));
						
							listOfSelectedColumns.get(SelectedIndex).set(3, listofJTextFieldsRename.get(i).getText());			
							listOfColumns.get(IndexOfColumnstoRename).set(2, listofJTextFieldsRename.get(i).getText());
							}
							
							for (int i=0;i<listofJComboBoxRename.size();i++)
							{
								String listDatas[] = new String[listOfSelectedColumns.size()];
							//listDatas[k] = listOfColumns.get(k).get(2)+" --> "+listOfColumns.get(k).get(3);
							
								for(int k = 0;k<listOfSelectedColumns.size();k++)
				        	
								{
									listDatas[k] = listOfSelectedColumns.get(k).get(3);//+" --> "+listOfSelectedColumns.get(k).get(4);
								}
							
							lFinalOutputColumns.removeAll();
							lFinalOutputColumns.setListData(listDatas);
							listofJComboBoxRename.get(i).removeAllItems();
							
							cbMerge1.removeAllItems();
							cbMerge2.removeAllItems();
							cbSplit.removeAllItems();
							cbCasing.removeAllItems();
							cbFormat.removeAllItems();
							cbOperations.removeAllItems();
							cbRename.removeAllItems();
					
						
							
							for(String str : listDatas) 
							{	
								listofJComboBoxRename.get(i).addItem(str);	
								   cbRename.addItem(str);
								   cbMerge1.addItem(str);
								   cbMerge2.addItem(str);
								   cbSplit.addItem(str);
								   cbCasing.addItem(str);
								   cbFormat.addItem(str);
								   cbOperations.addItem(str);					   
							}
							}
							pFinalList.revalidate();
							pFinalList.repaint();
							pOutput.revalidate();
							pOutput.repaint();
							spOutput.revalidate();
							spOutput.repaint();
							btnProceedRename.setEnabled(false);
							btnPlusRename.setEnabled(false);
						}
					});
					
					btnProceedRename.setBounds(437, 52, 95, 25);
					
					pRename.add(btnProceedRename);
					btnPlusRename.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							xxrename= xxrename+23;
							btnProceedRename.setBounds(454, 52+xxrename, 82, 25);
							btnPlusRename.setBounds(12,52+xxrename,61,25);
							//btn
							pRename.setPreferredSize(new Dimension(pRename.getSize().width, pRename.getSize().height + 23));
							
							listofJComboBoxRename.add(new JComboBox());
							listofJTextFieldsRename.add(new JTextField());	
							//listofJLists.get(numberadded).setBounds(10, 33+xx, 250, 100);
							listofJComboBoxRename.get(numberrename).setBounds(12, 24+ xxrename, 222, 24);
							listofJTextFieldsRename.get(numberrename).setBounds(273, 27+ xxrename, 222, 19);
							
					
							for (int k=0;k<cbRename.getModel().getSize();k++)
							{
								listofJComboBoxRename.get(numberrename).addItem(cbRename.getModel().getElementAt(k).toString());
							}
						
							
							pRename.add(listofJComboBoxRename.get(numberrename));
							pRename.add(listofJTextFieldsRename.get(numberrename));
							
							numberrename++;			
							pRename.revalidate();
							pRename.repaint();
							
						}
					});
					btnPlusRename.setBounds(12, 52, 61, 25);
					
					pRename.add(btnPlusRename);
					lblRenameAColumn.setBounds(12, 8, 311, 15);
					
					pRename.add(lblRenameAColumn);
			spOutput.setBounds(12, 422, 303, 134);
			pTabIntegration.add(spOutput);
			spOutput.setViewportView(pOutput);
			pOutput.setPreferredSize(new Dimension(290, 131));
			pOutput.setLayout(null);
			
			tfOutputFileName = new JTextField();
			tfOutputFileName.setBounds(79, 9, 114, 21);
			pOutput.add(tfOutputFileName);
			tfOutputFileName.setColumns(10);
			
			JLabel lblFileName = new JLabel("File name:");
			lblFileName.setBounds(10, 12, 88, 15);
			pOutput.add(lblFileName);
			
				
				bPlusColumns.setBounds(10, 82, 61, 25);
				pOutput.add(bPlusColumns);
				
			
				pFinalList.setBounds(10, 34, 272, 46);
				pOutput.add(pFinalList);
				
		
				pFinalList.setViewportView(lFinalOutputColumns);
				
				JLabel lblcsv = new JLabel(".csv");
				lblcsv.setBounds(195, 12, 32, 15);
				pOutput.add(lblcsv);
				
				lFinalOutputColumns.setSelectionModel(new DefaultListSelectionModel() 
				{
				    @Override
				    public void setSelectionInterval(int index0, int index1) 
				    {
				        if(super.isSelectedIndex(index0)) {
				            super.removeSelectionInterval(index0, index1);
				        }
				        else {
				            super.addSelectionInterval(index0, index1);
				        }
				    }
				});
					bGenerateFiles.setEnabled(false);
					bGenerateFiles.setBounds(523, 531, 61, 25);
					pTabIntegration.add(bGenerateFiles);
					
					
					spOperations.setVisible(false);
					spOperations.setBounds(12, 318, 572, 95);
					pTabIntegration.add(spOperations);
					
					final JPanel pOperations = new JPanel();
					spOperations.setViewportView(pOperations);
					pOperations.setLayout(null);
					
									cbOperations.setBounds(12, 22, 222, 24);
					pOperations.add(cbOperations);
					
				
					cbOperationsOperator.setBounds(246, 22, 72, 24);
					pOperations.add(cbOperationsOperator);
					
					tfOperations = new JTextField();
					tfOperations.setBounds(330, 25, 78, 19);
					pOperations.add(tfOperations);
					tfOperations.setColumns(10);
					
					
					bPlusOperations.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							xxoperations= xxoperations+23;
							bProceedOperations.setBounds(460, 55+xxoperations, 82, 25);
							bPlusOperations.setBounds(12,55+xxoperations,61,25);
							//btn
							pOperations.setPreferredSize(new Dimension(pOperations.getSize().width, pOperations.getSize().height + 23));
							
							listofJComboBoxOperations.add(new JComboBox());
							listofJComboBoxOperationsOperators.add(new JComboBox());
							listofJTextFieldsOperations.add(new JTextField());	
	
							listofJComboBoxOperations.get(numberoperations).setBounds(12, 22+ xxoperations, 222, 24);
							listofJComboBoxOperationsOperators.get(numberoperations).setBounds(246, 22+ xxoperations, 72, 24);
							listofJTextFieldsOperations.get(numberoperations).setBounds(330, 25+ xxoperations, 78, 19);
					
							for (int k=0;k<cbOperations.getModel().getSize();k++)
							{
								listofJComboBoxOperations.get(numberoperations).addItem(cbOperations.getModel().getElementAt(k).toString());
							}
						
							for (int k=0;k<cbOperationsOperator.getModel().getSize();k++)
							{
								listofJComboBoxOperationsOperators.get(numberoperations).addItem(cbOperationsOperator.getModel().getElementAt(k).toString());
							}
						
							
							pOperations.add(listofJComboBoxOperations.get(numberoperations));
							pOperations.add(listofJComboBoxOperationsOperators.get(numberoperations));
							pOperations.add(listofJTextFieldsOperations.get(numberoperations));
							
							numberoperations++;			
		
							pOperations.revalidate();
							pOperations.repaint();
						}
					});
					bPlusOperations.setBounds(12, 55, 61, 25);
					pOperations.add(bPlusOperations);
					bProceedOperations.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							
							for(int i=0;i<listofJComboBoxOperations.size();i++)
							{
								int SelectedIndex = listofJComboBoxOperations.get(i).getSelectedIndex();
							//	int IndexOfColumnstoOperate = Integer.parseInt(listOfSelectedColumns.get(SelectedIndex).get(1));
							listOfOperations.add(Lists.newArrayList(Integer.toString(SelectedIndex),listofJComboBoxOperationsOperators.get(i).getSelectedItem().toString(),listofJTextFieldsOperations.get(i).getText()));
							}
						
							
							//select all elements
							
							 int start = 0;
							    int end = lFinalOutputColumns.getModel().getSize() - 1;
							    if (end >= 0)
							    {
							      lFinalOutputColumns.setSelectionInterval(start, end);
							    }
							    
							    lFinalOutputColumns.setEnabled(false);
							    bPlusColumns.setVisible(false);
							    bProceedOperations.setEnabled(false);
							    bPlusOperations.setEnabled(false);
							    btnProceedRename.setEnabled(false);
								btnPlusRename.setEnabled(false);
								btnProceedMerge.setEnabled(false);
								btnProceedSplit.setEnabled(false);
								bProceedCasing.setEnabled(false);
								btnProceedFormating.setEnabled(false);
						}
					});
					bProceedOperations.setBounds(436, 55, 95, 25);
					
					pOperations.add(bProceedOperations);
					lblNewLabel_1.setBounds(12, 3, 452, 15);
					
					pOperations.add(lblNewLabel_1);
					lblAgregationOperations.setForeground(Color.RED);
					lblAgregationOperations.setVisible(false);
					lblAgregationOperations.setBounds(6, 301, 309, 15);
					
					pTabIntegration.add(lblAgregationOperations);
					
				
					spReadFile.setVisible(false);
					spReadFile.setBounds(316, 418, 276, 110);
					pTabIntegration.add(spReadFile);
					taReadFile.setFont(new Font("Liberation Sans", Font.BOLD, 12));
					taReadFile.setForeground(Color.WHITE);
					taReadFile.setBackground(Color.BLACK);
					spReadFile.setViewportView(taReadFile);
					
				
					btnWrite.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							btnSave_1.setVisible(true);
							
							lInputFiles.setEnabled(false);
							tfSeparator.setEnabled(false);
							bSelectFiles.setEnabled(false);
							bGenerateFiles.setEnabled(true);
							btnWrite.setEnabled(false);
							btnLoad.setEnabled(false);
							spReadFile.setVisible(true);
							taReadFile.setText("");
		            		taReadFile.append("INPUTFILE "+"\n");
		            		taReadFile.append("OUTPUTFILE "+"\n");
		            		taReadFile.append("SEPARATOR "+"\n");
		            		taReadFile.append("PROJECTEDCOLUMNS "+"\n");
		            		taReadFile.append("PROJECTEDNAMES "+"\n");
		            		taReadFile.append("MERGE "+"\n");
		            		taReadFile.append("SPLIT "+"\n");
		            		taReadFile.append("CASE "+"\n");
		            		taReadFile.append("FORMATDATES "+"\n");
		            		taReadFile.append("RESTRICTION ");
		            		
						spReadFile.setBounds(12,185,572,300);
						 lblWrite.setBounds(12,170,250,15);
						  lblWrite.setText("Write instructions to be executed.");
						spReadFile.revalidate();
					    spReadFile.repaint();
					   load = 1;
					  
						}
					});
					btnWrite.setBounds(440, 531, 75, 25);
					pTabIntegration.add(btnWrite);
					
				
					lblLoad.setBounds(200, 5, 350, 15);
					pTabIntegration.add(lblLoad);
					lblWrite.setBounds(305, 531, 125, 15);
					
					pTabIntegration.add(lblWrite);
					
				
					btnSave_1.setVisible(false);
					btnSave_1.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							  JFileChooser fileChooser = new JFileChooser();
								fileChooser.setDialogTitle("Specify a file to save");  
								if (fileChooser.showSaveDialog(frmBigDataIntegrator) == JFileChooser.APPROVE_OPTION) 
								{
									File file = fileChooser.getSelectedFile();
									String fileName = "Settings";
							
							        try 
							        {
							            // Assume default encoding.
							            FileWriter fileWriter =
							                new FileWriter(file);

							            // Always wrap FileWriter in BufferedWriter.
							            BufferedWriter bufferedWriter =
							                new BufferedWriter(fileWriter);

							            // Note that write() does not automatically
							            // append a newline character.
					
							            bufferedWriter.write(taReadFile.getText());
							            bufferedWriter.newLine();
							        							

							            // Always close files.
							            bufferedWriter.close();
							    
							        } 
							        
							            catch(IOException ex) 
					        {
					             ex.printStackTrace();
					        }								
								}

							
							
						}
					});
					btnSave_1.setBounds(363, 531, 71, 25);
					pTabIntegration.add(btnSave_1);
				
					bGenerateFiles.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							
								if(load==1)
								{
									String textStr[] = taReadFile.getText().split("\\r?\\n");
							
									for (int i=0;i<textStr.length;i++)
									{
										if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("INPUTFILE"))
					            	{
					            		xinputfile = inputpath+textStr[i].substring(textStr[i].indexOf(" ")+1);	
					            	}
					            	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("OUTPUTFILE"))
					            	{
					            		xoutputfile = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("SEPARATOR"))
					            	{
					            		xseparator = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("PROJECTEDCOLUMNS"))
					            	{
					            		xprojectedcolumnsid = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("PROJECTEDNAMES"))
					            	{
					            		xprojectedcolumnsname = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("MERGE"))
					            	{
					            		xmergecolumns = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("SPLIT"))
					            	{
					            		xsplitcolumns = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("CASE"))
					            	{
					            		xcasingcolums = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("FORMATDATES"))
					            	{
					            		xformatingcolumns = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
	
					            	if(textStr[i].substring(0,textStr[i].indexOf(' ')).toUpperCase().equals("RESTRICTION"))
					            	{
					            		xoperations = textStr[i].substring(textStr[i].indexOf(" ")+1);
					            	}
									}
									
									Job job;
									try 
									{
										job = new Job();
									
																job.setJarByClass(MainDriver.class);
																job.setJobName("Projection Job");
																
																Configuration conf=job.getConfiguration();
					
																conf.set("separator", xseparator);
																conf.set("columns", xprojectedcolumnsid);
																conf.set("namescolumns", xprojectedcolumnsname);
																conf.set("mergecolumns", xmergecolumns);
																conf.set("splitcolumns", xsplitcolumns);
																conf.set("casingcolumns", xcasingcolums);
																conf.set("formatingcolumns", xformatingcolumns);
																conf.set("operations", xoperations);
										
																FileInputFormat.setInputPaths(job, new Path(xinputfile));
																FileOutputFormat.setOutputPath(job, new Path(temppath));
																
																job.setMapperClass(ProjectionMapper.class);
																job.setReducerClass(ProjectionReducer.class);
	
																job.setMapOutputKeyClass(Text.class);
																job.setMapOutputValueClass(Text.class);
																
																job.setOutputKeyClass(Text.class);
																job.setOutputValueClass(Text.class);
																  
																  try 
																  {
																	job.waitForCompletion(true);
																} 
																  catch (ClassNotFoundException e1) 
																{
																	// TODO Auto-generated catch block
																	e1.printStackTrace();
																} 
																  catch (InterruptedException e1) 
																{
																	// TODO Auto-generated catch block
																	e1.printStackTrace();
																}
									
										
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
															
									 FileSystem fs;
										try 
										{
											fs = FileSystem.get(new URI(uri), new Configuration());
											fs.mkdirs(new Path(outputpath));
											fs.rename(new Path(temppath+"part-r-00000"), new Path(outputpath+xoutputfile));
										}
										        catch (IOException | URISyntaxException e1) 
										        {
											// TODO Auto-generated catch block
											e1.printStackTrace();
											} 	
									RemoveFileHDFS(temppath);					
								}
								
								else
								{
								
								for(int i=0;i<listofJLists.size();i++)
								{
								int[] SelectedIndices = listofJLists.get(i).getSelectedIndices();
										
								String input="";
								String ProjectedColumns="";
								String ProjectedColumnsNames="";
								String MergeColumns="empty";
								String SplitColumns="empty";
								String CasingColumns="empty";
								String FormatingColumns="empty";
								String SplitChar=tfChar.getText();
								String Operations="empty";
								
								if(listOfOperations.isEmpty() == false)
								{
									Operations = "";
									for(int j=0;j<listOfOperations.size();j++)
									{
										if(j==(listOfOperations.size()-1))
											Operations = Operations+listOfOperations.get(j).get(0)+"|"+listOfOperations.get(j).get(1)+"|"+listOfOperations.get(j).get(2);
										else
											Operations = Operations+listOfOperations.get(j).get(0)+"|"+listOfOperations.get(j).get(1)+"|"+listOfOperations.get(j).get(2)+"|";
									}	
								}
								
								if(listOfSplitColumns.isEmpty() == false)
								{
									SplitColumns = listOfSplitColumns.get(0).get(0)+"|"+SplitChar;
								}
								
								if(listOfFormatingColumns.isEmpty() == false)
								{
									FormatingColumns = listOfFormatingColumns.get(0).get(0)+"|"+listOfFormatingColumns.get(0).get(1);
								}
								
								if(listOfCasingColumns.isEmpty() == false)
								{
									CasingColumns = listOfCasingColumns.get(0).get(0)+"|"+listOfCasingColumns.get(0).get(1);//+"|"+listOfCasingColumns.get(0).get(2);
								}
								
								if(listOfMergeColumns.isEmpty() == false)
								{
									MergeColumns="";
											MergeColumns = MergeColumns + listOfMergeColumns.get(0).get(0)+"|"+listOfMergeColumns.get(0).get(1)+"|"+listOfMergeColumns.get(0).get(2);
								}
								
								for (int t=0;t<SelectedIndices.length;t++)
								{
									if(t<(SelectedIndices.length-1))
									{
									ProjectedColumns = ProjectedColumns+listOfSelectedColumns.get(SelectedIndices[t]).get(2)+"|";
									ProjectedColumnsNames = ProjectedColumnsNames+listOfSelectedColumns.get(SelectedIndices[t]).get(3)+Separator;
									
									}
									if(t==(SelectedIndices.length-1))
									{
										ProjectedColumns = ProjectedColumns+listOfSelectedColumns.get(SelectedIndices[t]).get(2);
										ProjectedColumnsNames = ProjectedColumnsNames+listOfSelectedColumns.get(SelectedIndices[t]).get(3);
									}
									//Id,Id,Pos,Name,File
									input = inputpath+listOfSelectedColumns.get(t).get(4);	
								}
							
								Job job;
								try 
								{
									job = new Job();
								
															job.setJarByClass(MainDriver.class);
															job.setJobName("Projection Job");
															
															Configuration conf=job.getConfiguration();
															
															conf.set("separator", Separator);
															conf.set("columns",ProjectedColumns);
															conf.set("namescolumns",ProjectedColumnsNames);
															conf.set("mergecolumns", MergeColumns);
															conf.set("splitcolumns", SplitColumns);
															conf.set("casingcolumns", CasingColumns);
															conf.set("formatingcolumns", FormatingColumns);
															conf.set("operations", Operations);
									
															FileInputFormat.setInputPaths(job, new Path(input));
															FileOutputFormat.setOutputPath(job, new Path(temppath));
															
															job.setMapperClass(ProjectionMapper.class);
															job.setReducerClass(ProjectionReducer.class);
	
															job.setMapOutputKeyClass(Text.class);
															job.setMapOutputValueClass(Text.class);
															
															job.setOutputKeyClass(Text.class);
															job.setOutputValueClass(Text.class);
															  
															  try 
															  {
																job.waitForCompletion(true);
															} 
															  catch (ClassNotFoundException e1) 
															{
																// TODO Auto-generated catch block
																e1.printStackTrace();
															} 
															  catch (InterruptedException e1) 
															{
																// TODO Auto-generated catch block
																e1.printStackTrace();
															}
								
									
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
														
								 FileSystem fs;
									try 
									{
										
										fs = FileSystem.get(new URI(uri), new Configuration());
										fs.mkdirs(new Path(outputpath));
										fs.rename(new Path(temppath+"part-r-00000"), new Path(outputpath+listofJTextFields.get(i).getText()+".csv"));
									}
									        catch (IOException | URISyntaxException e1) 
									        {
										// TODO Auto-generated catch block
										e1.printStackTrace();
										} 	
								RemoveFileHDFS(temppath);
								
								
								int dialogButton = JOptionPane.YES_NO_OPTION;
								int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to save this work?", "Save", dialogButton);
								if(dialogResult == 0) 
								{
								
								  JFileChooser fileChooser = new JFileChooser();
									fileChooser.setDialogTitle("Specify a file to save");  
									if (fileChooser.showSaveDialog(frmBigDataIntegrator) == JFileChooser.APPROVE_OPTION) 
									{
										File file = fileChooser.getSelectedFile();
										String fileName = "Settings";
								
								        try 
								        {
								            // Assume default encoding.
								            FileWriter fileWriter =
								                new FileWriter(file);
	
								            // Always wrap FileWriter in BufferedWriter.
								            BufferedWriter bufferedWriter =
								                new BufferedWriter(fileWriter);
	
								            // Note that write() does not automatically
								            // append a newline character.
								            
								            //	input = inputpath+listOfSelectedColumns.get(t).get(4);	
								            bufferedWriter.write("INPUTFILE "+listOfSelectedColumns.get(0).get(4));
								            bufferedWriter.newLine();
								            bufferedWriter.write("OUTPUTFILE "+listofJTextFields.get(i).getText()+".csv");
								            bufferedWriter.newLine();
								            bufferedWriter.write("SEPARATOR "+Separator);
								            bufferedWriter.newLine();
								            bufferedWriter.write("PROJECTEDCOLUMNS "+ProjectedColumns);
								            bufferedWriter.newLine();
								            bufferedWriter.write("PROJECTEDNAMES "+ProjectedColumnsNames);
								            bufferedWriter.newLine();
								            bufferedWriter.write("MERGE "+MergeColumns);
								            bufferedWriter.newLine();
								            bufferedWriter.write("SPLIT "+SplitColumns);
								            bufferedWriter.newLine();
								            bufferedWriter.write("CASE "+CasingColumns);
								            bufferedWriter.newLine();
								            bufferedWriter.write("FORMATDATES "+FormatingColumns);
								            bufferedWriter.newLine();
								            bufferedWriter.write("RESTRICTION "+Operations);
								            bufferedWriter.newLine();
								
	
								            // Always close files.
								            bufferedWriter.close();
								    
								        } 
								        
								            catch(IOException ex) 
						        {
						             ex.printStackTrace();
						        }								
									}
	
								
								} 
								else 
								{
								} 
								}
							}
								
								
							
						}//end public void actionPerformed(ActionEvent e) 
					});
				
			
				
				bPlusColumns.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						xx = xx+73;
						JLabel label = new JLabel("File Name");
						JLabel labelCsv = new JLabel(".csv");
						
						JScrollPane scrollpanel = new JScrollPane();
						scrollpanel.setBounds(10, 33+xx, 272, 46);
			
						labelCsv.setBounds(195,12+xx,32,15);
						label.setBounds(10,12+xx,88,15);
						
					
						
						pOutput.add(label);
						pOutput.add(labelCsv);
						
						listofJLists.add(new JList());
						listofJTextFields.add(new JTextField());	
						listofJTextFields.get(numberadded).setBounds(77, 9+ xx, 114, 21);
						String[] test = new String[lFinalOutputColumns.getModel().getSize()];
						for (int k=0;k<lFinalOutputColumns.getModel().getSize();k++)
						{
							test[k]=lFinalOutputColumns.getModel().getElementAt(k).toString();
							
						}
						listofJLists.get(numberadded).setListData(test);
						scrollpanel.setViewportView(listofJLists.get(numberadded));
						
						
						listofJLists.get(numberadded).setSelectionModel(new DefaultListSelectionModel() 
						{
						    @Override
						    public void setSelectionInterval(int index0, int index1) 
						    {
						        if(super.isSelectedIndex(index0)) {
						            super.removeSelectionInterval(index0, index1);
						        }
						        else {
						            super.addSelectionInterval(index0, index1);
						        }
						    }
						});
						
						
						bPlusColumns.setBounds(10, 82+xx, 61, 25);
						
						pOutput.add(scrollpanel);
						pOutput.add(listofJTextFields.get(numberadded));
						
				
						pOutput.setPreferredSize(new Dimension(pOutput.getSize().width, pOutput.getSize().height + 73));
						

						numberadded++;
						pOutput.revalidate();
						pOutput.repaint();
					}
				});
				
				JPanel pTabAnalysis = new JPanel();
				tabbedPane.addTab("Data Analysis", null, pTabAnalysis, null);
				pTabAnalysis.setLayout(null);
				
				JPanel pSettings = new JPanel();
				tabbedPane.addTab("Settings", null, pSettings, null);
				pSettings.setLayout(null);
				panel.setBounds(12, 12, 572, 143);
				
				pSettings.add(panel);
				panel.setLayout(null);
				lblIntegrationModule.setBounds(0, 0, 167, 15);
				
				panel.add(lblIntegrationModule);
				
				JLabel lblInputFilesPath = new JLabel("Input files path:");
				lblInputFilesPath.setBounds(10, 56, 132, 15);
				panel.add(lblInputFilesPath);
				lblOutputFilesPath.setBounds(10, 114, 132, 15);
				
				panel.add(lblOutputFilesPath);
				
				tfURI = new JTextField();
				tfURI.setBounds(129, 27, 420, 19);
				panel.add(tfURI);
				tfURI.setColumns(10);
				
				JLabel lblUri = new JLabel("URI:");
				lblUri.setBounds(10, 27, 132, 15);
				panel.add(lblUri);
				
				tfInputPath = new JTextField();
				tfInputPath.setColumns(10);
				tfInputPath.setBounds(129, 56, 420, 19);
				panel.add(tfInputPath);
				
			     tfTempPath = new JTextField();
			       // tfTempPath.setText((String) null);
			        tfTempPath.setColumns(10);
			        tfTempPath.setBounds(129, 83, 420, 19);
			        panel.add(tfTempPath);
				
				tfOutputPath = new JTextField();
				tfOutputPath.setColumns(10);
				tfOutputPath.setBounds(129, 112, 420, 19);
				panel.add(tfOutputPath);
				panel_1.setBounds(12, 167, 572, 95);
				
				pSettings.add(panel_1);
				panel_1.setLayout(null);
				
				JLabel lblAnalysisModule = new JLabel("Analysis Module:");
				lblAnalysisModule.setBounds(0, 0, 141, 15);
				panel_1.add(lblAnalysisModule);
				
				JLabel lblOutputFilesPath_1 = new JLabel("Output files path:");
				lblOutputFilesPath_1.setBounds(10, 75, 131, 15);
				panel_1.add(lblOutputFilesPath_1);
				
				txtOutputPathAnalysisPath = new JTextField();
				txtOutputPathAnalysisPath.setBounds(141, 75, 410, 19);
				panel_1.add(txtOutputPathAnalysisPath);
				txtOutputPathAnalysisPath.setColumns(10);
				
				JButton btnSave = new JButton("Save");
				
				btnSave.addActionListener(new ActionListener() 
				{	
					public void actionPerformed(ActionEvent e) 
					{
					    // The name of the file to open.
				        String fileName = "/home/hduser/Desktop/Settings";

				        try {
				            // Assume default encoding.
				            FileWriter fileWriter =
				                new FileWriter(fileName);

				            // Always wrap FileWriter in BufferedWriter.
				            BufferedWriter bufferedWriter =
				                new BufferedWriter(fileWriter);

				            // Note that write() does not automatically
				            // append a newline character.
				            bufferedWriter.write(tfURI.getText());
				            bufferedWriter.newLine();
				            bufferedWriter.write(tfInputPath.getText());
				            bufferedWriter.newLine();
				            bufferedWriter.write(tfTempPath.getText());
				            bufferedWriter.newLine();
				            bufferedWriter.write(tfOutputPath.getText());
				            bufferedWriter.newLine();
				            bufferedWriter.write(txtURIAnalysis.getText());
				            bufferedWriter.newLine();
				            bufferedWriter.write(txtInputFilePathAnalysis.getText());
				            bufferedWriter.newLine();
				            bufferedWriter.write(txtOutputPathAnalysisPath.getText());
				            bufferedWriter.newLine();

				            // Always close files.
				            bufferedWriter.close();
				           JFrame frame = null;
				            JOptionPane.showMessageDialog(frame, "Parameters saved.");
				        }
				        catch(IOException ex) 
				        {
				             ex.printStackTrace();
				        }
					}
				});
				
				btnSave.setBounds(483, 270, 80, 25);
				pSettings.add(btnSave);
				
				JPanel pAbout = new JPanel();
				tabbedPane.addTab("About", null, pAbout, null);
				pAbout.setLayout(null);
				
				try{
				BufferedImage myPicture = ImageIO.read(new File("/home/hduser/Desktop/llama.jpg"));
				JLabel picLabel = new JLabel(new ImageIcon(myPicture));
				
				picLabel.setBounds(12, 12, 427, 157);
				pAbout.add(picLabel);
				lblDevelopedBy.setFont(new Font("Dialog", Font.BOLD, 16));
				lblDevelopedBy.setBounds(12, 206, 203, 15);
				
				pAbout.add(lblDevelopedBy);
				lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 14));
				lblNewLabel_2.setBounds(12, 230, 142, 15);
				
				pAbout.add(lblNewLabel_2);
				lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 14));
				lblNewLabel_3.setBounds(12, 250, 186, 15);
				
				pAbout.add(lblNewLabel_3);
				lblM.setFont(new Font("Dialog", Font.PLAIN, 14));
				lblM.setBounds(12, 270, 146, 15);
				
				pAbout.add(lblM);
				lblUgarteHctor.setFont(new Font("Dialog", Font.PLAIN, 14));
				lblUgarteHctor.setBounds(12, 290, 152, 15);
				
				pAbout.add(lblUgarteHctor);
				lblUniversityOfBonn.setBounds(12, 327, 229, 15);
				
				pAbout.add(lblUniversityOfBonn);
				}catch(Exception exx){
					
				}
		
		pSelectColumns.setVisible(false);
		spProcessing.setVisible(false);
		spOutput.setVisible(false);	
		
		/************************************************
		 *  ANALYSIS CODE
		 *  ********************************************/
		conf = new SparkConf().setMaster(URIAnalysis).setAppName("llama Big Data Integration & Analysis");
    	sc = new JavaSparkContext(conf);
    	sqlContext = new SQLContext(sc);
		//set up for select input files panel
		panelSelectInputFileAnalysis.setBounds(10, 0, 550, 145);
		pTabAnalysis.add(panelSelectInputFileAnalysis);
		panelSelectInputFileAnalysis.setLayout(null);
		lblSelectInputFileAnalysis.setBounds(10, 11, 210, 14);
		panelSelectInputFileAnalysis.add(lblSelectInputFileAnalysis);
		scrPanelInputFIleAnalysis.setBounds(10, 34, 540, 74);
		panelSelectInputFileAnalysis.add(scrPanelInputFIleAnalysis);
		btnAddFileAnalysis.setBounds(461, 119, 89, 23);
		panelSelectInputFileAnalysis.add(btnAddFileAnalysis);
		btnAddFileAnalysis.addActionListener(new ActionListener() {
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e) {
				try{
					
					String tableName = null;
					List objs = listInputFilesAnalysis.getSelectedValuesList();
					if(objs == null || objs.size() == 0){
						JOptionPane.showMessageDialog(frmBigDataIntegrator, "Select at least one column");
					}else{
						JPanel panel = new JPanel();
						panel.setLayout(null);
				        int height = 80;
				        int y = 0;
				        for(Object obj:objs){
				        	tableName = obj.toString().substring(0, obj.toString().length()-4);
				        	fileForAnalysis = sqlContext.read().format("com.databricks.spark.csv").option("header", "true").option("delimiter", txtSeparatorAnalysis.getText()).load(inputPathAnalysis + obj.toString());
				        	fileForAnalysis.registerTempTable(tableName);
				        	JList list = new JList(fileForAnalysis.schema().fieldNames());
				        	JLabel lblTableName = new JLabel("Table: " +  tableName);
				        	lblTableName.setBounds(0, y, 300, 20);
				        	panel.add(lblTableName);
				        	y = y + 20;
				        	JScrollPane sc1 = new JScrollPane();
				        	sc1.setBounds(0, y, 520, height);
				        	sc1.setViewportView(list);
				        	panel.add(sc1);
				        	y = y + height;
				        }
				        
				        panel.setPreferredSize(new Dimension(500, y));
				        JScrollPane scrollPane = new JScrollPane(panel);
				        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				        scrollPane.setBounds(1, 1, 540, 100);
				        panelFieldNames.add(scrollPane);
					}
					panelOutputAnalysis.setVisible(false);
					panelInputQuery.setVisible(true);
					panelFieldNames.setVisible(true);
					scrPanelInputFIleAnalysis.setEnabled(false);
					listInputFilesAnalysis.setEnabled(false);
					btnAddFileAnalysis.setEnabled(false);
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		listInputFilesAnalysis = new JList<Object>(ReadFilesHDFS(inputPathAnalysis));
		//Modify value of label with the number of rows selected
	    listInputFilesAnalysis.addListSelectionListener(new ListSelectionListener() 
		{
	    	public void valueChanged(ListSelectionEvent e) 
			{
	    		lblSelectedInputAnalysis.setText(Integer.toString(listInputFilesAnalysis.getSelectedIndices().length)+" file(s) selected");
			}
		});
		scrPanelInputFIleAnalysis.setViewportView(listInputFilesAnalysis);
		lblSelectedInputAnalysis.setBounds(200, 119, 130, 15);
		panelSelectInputFileAnalysis.add(lblSelectedInputAnalysis);
		
		JLabel lblSeparatorAnalysis = new JLabel("Separator");
		lblSeparatorAnalysis.setBounds(10, 119, 89, 15);
		panelSelectInputFileAnalysis.add(lblSeparatorAnalysis);
		
		txtSeparatorAnalysis = new JTextField();
		txtSeparatorAnalysis.setText(",");
		txtSeparatorAnalysis.setBounds(85, 119, 15, 19);
		panelSelectInputFileAnalysis.add(txtSeparatorAnalysis);
		txtSeparatorAnalysis.setColumns(1);
		
		
		panelFieldNames.setBounds(20, 155, 574, 100);
		pTabAnalysis.add(panelFieldNames);
		panelFieldNames.setLayout(null);
		panelFieldNames.setVisible(false);
		
		panelInputQuery.setBounds(10, 255, 574, 153);
		pTabAnalysis.add(panelInputQuery);
		panelInputQuery.setLayout(null);
		
		JLabel lblInputQuery = new JLabel("Input Query");
		lblInputQuery.setBounds(10, 5, 156, 15);
		panelInputQuery.add(lblInputQuery);
		
		JButton btnExecuteQuery = new JButton("Execute");
		btnExecuteQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(txtFileNameToSave.getText() == null || txtFileNameToSave.getText().trim().length()==0)
						JOptionPane.showMessageDialog(frmBigDataIntegrator, "Please, specify a name for the file");
					else{
						panelOutputAnalysis.setVisible(false);
//						Date dateStart = new Date();
//						System.out.println("Start execute query time: " + dateFormat.format(dateStart));
						results = fileForAnalysis.sqlContext().sql(txtAreaInputQuery.getText());
						results.repartition(1).write().format("com.databricks.spark.csv").save(outputPathAnalysis + txtFileNameToSave.getText() + ".csv");
						DataFrame partialResults = sqlContext.read().format("com.databricks.spark.csv").option("header", "true").option("delimiter", txtSeparatorAnalysis.getText()).load(outputPathAnalysis + txtFileNameToSave.getText() + ".csv");
						txtAreaOutputAnalysis.setText("Sample data\n" + partialResults.showString(10));
						panelOutputAnalysis.setVisible(true);
//						Date dateEnd = new Date();
//						System.out.println("End execute query time: " + dateFormat.format(dateEnd));
						FileSystem fs;
						String header = "";
						int i = 1;
						for(String s: results.schema().fieldNames()){
							header = header + s;
							if(i < results.schema().fieldNames().length)
								header = header + ",";
							i++;
						}
						fs = FileSystem.get(new URI(uri), new Configuration());
						String src = outputPathAnalysis + txtFileNameToSave.getText() + ".csv" + "/part-00000";
						String dest = outputPathAnalysis + txtFileNameToSave.getText() + ".csv1";
						copyMergeWithHeader(fs, src, dest, true, new Configuration(), header);
						RenameFileHDFS(dest, dest.substring(0, dest.length()-1));
						txtFileNameToSave.setText(null);
						
					}
					
					
				}catch(Exception ex){
					ex.printStackTrace();
					JFrame frame = null;
					JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", 1);
					panelOutputAnalysis.setVisible(false);
				}
			}
		});
		btnExecuteQuery.setBounds(461, 125, 89, 23);
		panelInputQuery.add(btnExecuteQuery);
		scrInputQuery.setBounds(10, 20, 540, 100);
		panelInputQuery.setVisible(false);
		
		panelInputQuery.add(scrInputQuery);
		
		scrInputQuery.setViewportView(txtAreaInputQuery);
		
		JLabel lblFileNameToSave = new JLabel("File Name:");
		lblFileNameToSave.setBounds(10, 125, 84, 15);
		panelInputQuery.add(lblFileNameToSave);
		
		txtFileNameToSave = new JTextField();
		txtFileNameToSave.setBounds(85, 125, 210, 19);
		panelInputQuery.add(txtFileNameToSave);
		txtFileNameToSave.setColumns(10);
		
		JLabel lblCSVExtension = new JLabel(".csv");
		lblCSVExtension.setBounds(295, 125, 70, 15);
		panelInputQuery.add(lblCSVExtension);
		panelOutputAnalysis.setBounds(10, 409, 574, 201);
		panelOutputAnalysis.setVisible(false);
		
		pTabAnalysis.add(panelOutputAnalysis);
		panelOutputAnalysis.setLayout(null);
		lblOutputAnalysis.setBounds(10, 0, 70, 15);
		
		panelOutputAnalysis.add(lblOutputAnalysis);
		scrOutputAnalysis.setBounds(10, 15, 540, 180);
		
		panelOutputAnalysis.add(scrOutputAnalysis);
		
		
		scrOutputAnalysis.setViewportView(txtAreaOutputAnalysis);
		txtAreaOutputAnalysis.setEditable(false);
		
		/************************************************
		 * END ANALYSIS CODE
		 *  ********************************************/
	}	
	
	private static Path checkDest(String srcName, FileSystem dstFS, Path dst, boolean overwrite) throws IOException {
        if(dstFS.exists(dst)) {
            FileStatus sdst = dstFS.getFileStatus(dst);
            if(sdst.isDirectory()) {
                if(null == srcName) {
                    throw new IOException("Target " + dst + " is a directory");
                }
 
                return checkDest((String)null, dstFS, new Path(dst, srcName), overwrite);
            }
 
            if(!overwrite) {
                throw new IOException("Target " + dst + " already exists");
            }
        }
        return dst;
    }
	
	public static boolean copyMergeWithHeader(FileSystem fs, String srcDir, String dstFile, boolean deleteSource, Configuration conf, String header) throws IOException {
        Path srcDirPath = new Path(srcDir);
        Path dstFilePath = new Path(dstFile);
        dstFilePath = checkDest(srcDirPath.getName(), fs, dstFilePath, true);
        if(fs.getFileStatus(srcDirPath).isDirectory()) {
            return false;
        } else {
            FSDataOutputStream out = fs.create(dstFilePath);
            if(header != null) {
                out.write((header + "\n").getBytes("UTF-8"));
            }
 
            try {
                FileStatus[] contents = fs.listStatus(srcDirPath);
 
                for(int i = 0; i < contents.length; ++i) {
                    if(!contents[i].isDirectory()) {
                        FSDataInputStream in = fs.open(contents[i].getPath());
 
                        try {
                            IOUtils.copyBytes(in, out, conf, false);
 
                        } finally {
                            in.close();
                        }
                    }
                }
            } finally {
                out.close();
            }
            return deleteSource?fs.delete(new Path(srcDir.substring(0, srcDir.length()-10)), true):true;
        }
    }
}