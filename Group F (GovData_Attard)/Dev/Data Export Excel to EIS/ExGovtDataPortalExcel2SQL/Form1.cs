/* EXGovtDataPortal Excel 2 SQL is developed and designed by
 * Muhammad Usman Asghar Bajwa
 * Matriculation: 2383436
 * During the Enterprise Information Systems Lab"Exploiting Govt.Data Portals"
 * all of its script can only be used for research or acdamic purpose.
 * This programm takes excell files as input by selecting a single govt. Data excel file of a specific Domain or by selecting a directory of specific domain data of Govt. Data portals excell files.
    /// Then it process these files, extract needed information, parse these files and save the whole data to sql server.
 *  This */
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
////////
using System.Collections;
using System.Data.OleDb;
using System.Data.SqlClient;
using Microsoft.Office.Interop.Excel;
using Microsoft;
using System.Web;
using Microsoft.Office;
using System.Reflection;
using System.Diagnostics;
using System.Data.Common;
using Microsoft.Office.Interop;
using Microsoft.Office.Interop.Excel;
using Microsoft.Office.Core;
using System.Runtime.InteropServices;
using System.Data.Sql;
using System.Data.SqlTypes;
using System.Globalization;
using System.IO;
using System.IO.Log;
using System.Configuration;

namespace EXGovtDataPortalExcel2SQL
{
    /// < Exploiting Govt. Data Portal Excel 2SQL>
    /// This programm takes excell files as input by selecting a single govt. Data excel file of a specific Domain or by selecting a directory of specific domain data of Govt. Data portals excell files.
    /// Then it process these files, extract needed information, parse these files and save the whole data to sql server.
    /// </Exploiting Govt. Data Portal Excel 2SQL>
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        CultureInfo US = new CultureInfo("en-US");

        bool HadErrors;
        System.Data.DataTable dtvalidator = new System.Data.DataTable();
        

/// <summary>
/// This is the starging point of the EXGovt.Dataportals Excel 2 sql parser!
/// </summary>
/// <param name="sender"></param>
/// <param name="e"></param>
        private void Form1_Load(object sender, EventArgs e)
        {

            //lblteststr.Text = " Server Name:LENOVO_USMAN\\PATENT_SQL;Database=PATENTDB;User ID=sa;Password=usman1131";
          
            btnSaveRecord.Enabled = false;                   

        }
      
       
      //  ArrayList logfile = new ArrayList();

        /// <excelSaveinArray(string filename)>
      /// this function load the file from the selected path and retun a datatable containing that file.
      /// It also extract the Parent company name and its BVD ID number from the file.
        /// </excelSaveinArray(string filename)>
      /// <param name="filename"></param>
      /// <returns datatable></returns>         
       
        public System.Data.DataTable excelSaveinArray(string filename)
         {
                string pName = string.Empty;
                string bvd = string.Empty;
                int pcount = 0;
                string xlSheetName = string.Empty;
                System.Data.DataTable dt = new System.Data.DataTable();
                string filepath = filename;//ofdPatent.FileName;
               
               return dt;
       
           }
        /// <RemoveColumn>
        /// this function remove the empty columns
        /// </RemoveColumn>
        /// <param name="fname"></param>
        /// <returns> datatable</returns>
        public System.Data.DataTable RemoveColumn(string fname)
         {
           // lblfname.Text = Convert.ToString(File.GetCreationTime(fname));
           // lblfnumber.Text = Convert.ToString(DateTime.Now);
            ArrayList arrColToRemove = new ArrayList();
            System.Data.DataTable dt1 = new System.Data.DataTable();
            dt1 = excelSaveinArray(fname);
            int count = 0;
            int b = dt1.Columns.Count;
            
            try
            {
            for (int c = 1; c < dt1.Columns.Count; c++)
            {
                count = 0;
                for (int i = 0; i < dt1.Rows.Count; i++)
                {
                    DataRow dr = dt1.Rows[i];

                    
                    if (string.IsNullOrEmpty(dr[c.ToString()].ToString()))
                    {
                        count = count + 1;
                    }
                    if (count == dt1.Rows.Count)
                    {
                        //

                        arrColToRemove.Add(c.ToString());
                    }
                }
            }
                //delete columns
            foreach (string ColName in arrColToRemove)
            {
                dt1.Columns.Remove(ColName);
            }
           gvGovtData.DataSource = dt1;
            }
            catch(Exception ex)
            {
                 bool a = false;
                            int c= 0;
                        //  MessageBox.Show(ex.Message + lblfname.Text + "Galti noo pharr");
                        //  ErrorLog(("C:/Users/Usman/Documents/Visual Studio 2010/Projects/PatentExcel2SQL/PatentExcel2SQL/Logs/ErrorLog"), lblfname.Text+"\t" + ex.Message) ;
                            b= ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" +ex.Message  + "File is invalid ");
                            switch (c)
                             {
                            case 1 :
                       
                           break;
                            default :
                            break;
                            }
            }
            return dt1;
        }

       
        /// <summar>
        /// it just select the files and filters them as Microsoft Excell files
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>

        private void   button1_Click(object sender, EventArgs e)
        {
           // sortFiles();
        //connection checking
            int n= 1;
         if (!(string.IsNullOrEmpty(txtOperatorName.Text) || (string.IsNullOrEmpty(txtdbserver.Text) || (string.IsNullOrEmpty(txtdbserver.Text)))))
           
            {
                int a = checkConnection();
                if (a != -1)
                {
                    if (rdbtnFile.Checked)
                    {
                        string strpath = string.Empty;
                        ofdPatent.ShowDialog();
                        string filename = string.Empty;
                        string Pfad = string.Empty;


                        ofdPatent.Filter = "Patent Excel 2007-2010 Files(*.xlsx)|*.xlsx| Patent Excel 1995-2003 Files(*.xls)|*.xls|All Files(*.*)|*.*";
                        //ofdPatent.Filter = "Patent Excel Files(*.xls*)|*.xls*";
                        if (string.IsNullOrEmpty(ofdPatent.FileName))
                        {
                            btnShowResult.Enabled = false;
                            btnSaveRecord.Enabled = false;

                        }
                        else
                        {
                            lblfname.Text = ofdPatent.FileName;
                            btnShowResult.Enabled = true;
                            btnSaveRecord.Enabled = true;
                           
                        }
                    }
                    else
                    {
                        MessageBox.Show("Please check the select file first!");
                        //MessageBox.Show(this, "Check Select File: Please check Error Log", "Data Validation Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Stop);
                    }
                }
            }
            else
            {
               // MessageBox.Show("Please review Operater name or databse credential!");
                MessageBox.Show(this, "Data Base Credentials: Please review User Name or Database Credentials!!!", "Invalid Database Credentials !!!", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            //...................

       
        
            
         
        }
     
        /// <Summary>
        /// this function parse the file and return a datatable in case of successfull parsing. Otherwise it make en entry into the Error log and skip the file.
        /// </Summarx>
        /// <param name="f"></param>
        /// <returns></returns>
        ///
        private void Import_To_Grid(string FilePath, string Extension, string isHDR)
        {
            string conStr = "";
            switch (Extension)
            {
                case ".xls": //Excel 97-03
                    conStr = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source={0};Extended Properties='Excel 8.0;HDR={1}'";//ConfigurationManager.ConnectionStrings["Excel03ConString"].ConnectionString;
                    break;
                case ".xlsx": //Excel 07
                    conStr = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source={0};Extended Properties='Excel 8.0;HDR={1}'";//ConfigurationManager.ConnectionStrings["Excel07ConString"].ConnectionString;
                    break;
            }
            conStr = String.Format(conStr, FilePath, isHDR);
            OleDbConnection connExcel = new OleDbConnection(conStr);
            OleDbCommand cmdExcel = new OleDbCommand();
            OleDbDataAdapter oda = new OleDbDataAdapter();
            System.Data.DataTable dt = new System.Data.DataTable();
            cmdExcel.Connection = connExcel;

            //Get the name of First Sheet
            connExcel.Open();
            System.Data.DataTable dtExcelSchema;
            dtExcelSchema = connExcel.GetOleDbSchemaTable(OleDbSchemaGuid.Tables, null);
            string SheetName = dtExcelSchema.Rows[0]["TABLE_NAME"].ToString();
            connExcel.Close();

            //Read Data from First Sheet
            connExcel.Open();
            cmdExcel.CommandText = "SELECT * From [" + SheetName + "]";
            oda.SelectCommand = cmdExcel;
            oda.Fill(dt);
            connExcel.Close();

            //Bind Data to GridView

            gvGovtData.DataSource = dt;
            
           // GridView1.Caption = Path.GetFileName(FilePath);
           // GridView1.DataSource = dt;
           // GridView1.DataBind();
        }
        public System .Data .DataTable parserResult(string f)
        {
            

            
                System.Data.DataTable dt = new System.Data.DataTable();
                System.Data.DataTable dtParser = new System.Data.DataTable();
                dt = excelSaveinArray(f);// dtvalidator;RemoveColumn(f);
               // MessageBox.Show(dt.Rows.Count.ToString());
            try
            {
                if (dt.Rows.Count > 0)
                {    
                    //define the data columns of the parser
                    //MessageBox.Show(dt.Columns.Count.ToString());
                    dtParser.Columns.Add("Sno", typeof(string));
                    dtParser.Columns.Add("Country", typeof(string));
                    dtParser.Columns.Add("Year", typeof(string));
                    dtParser.Columns.Add("total_Population", typeof(string));
                    dtParser.Columns.Add("pop_Density", typeof(string));
                    dtParser.Columns.Add("pop_Growthrate", typeof(string));
                    dtParser.Columns.Add("pop_BirthRate", typeof(string));
                    dtParser.Columns.Add("pop_DeathRate", typeof(string));
                    gvGovtData.DataSource = dtParser;
                    MessageBox.Show(dt.Rows.Count.ToString());
                }
                else
                {
                    //lblPid.Text = "";
                   // lblPname.Text = "";
                    btnShowResult.Enabled = false;
                    btnSaveRecord.Enabled = false;
                }
               return dtParser;
            }
            catch (Exception ex)
            {
                bool a = false;
                int b= 0;
              //  MessageBox.Show(ex.Message + lblfname.Text + "Galti noo pharr");
              //  ErrorLog(("C:/Users/Usman/Documents/Visual Studio 2010/Projects/PatentExcel2SQL/PatentExcel2SQL/Logs/ErrorLog"), lblfname.Text+"\t" + ex.Message) ;
                b= ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + "Error Type 2: " + ex.Message);
                throw new ArgumentException(ex.Message);
                
                 switch (b)
                {
                    case 1 :
                       
                           break;
                    default :
                       break;
                } 
                 
            }
            return dtParser;
            

    }
        /// <summary>
        /// It call the store procedue "s_AddBvDSubsidiary" and save the parsed record into the database. It make an entry
        /// into the Success log in case of success and also make an enty to error log in case of any error.
        /// </summary>
        /// <param name="file"></param>
        /// <returns>bool</returns>
        public bool SaveParsedRecord(string file)
        {
            try
            {
                

                // added by Nils: Test if db connection is OK
                try
                {
                          //  int a = GetOperatorID(lblOperatorName.Text);
                            int a = OperatorID(lblOperatorName.Text);
                            lblOperatorID.Text = a.ToString();
                            if (a == -1)
                            {
                                MessageBox.Show("Please first input valid database credentials");
                                HadErrors = true;
                                return false;
                            }
                }
                catch
                {
                    MessageBox.Show("Please first input valid database credentials");
                    HadErrors = true;
                    return false;
                }


                // String strConnection = "Data Source=LENOVO_USMAN\\PATENT_SQL;Initial Catalog=PATENTDB;Integrated Security=True"; //"Data Source=MYCBJ017550027;Initial Catalog=MySamplesDB;Integrated Security=True";
                // Added and changed by Nils to make SQL logins work
                string strSecurity = "";

                if (txtdbserver.Text == "")
                    return false;

                if (txtPassword.Text == "")
                {
                    strSecurity = ";Integrated Security=True";
                }
                string strConn = "Data Source=" + txtdbserver.Text + ";Initial Catalog=" + txtdbName.Text + "; User ID=" + txtUserName.Text + ";Password=" + txtPassword.Text + strSecurity + ";";
              
                
                System.Data.DataTable dt = new System.Data.DataTable();
                dt = parserResult(file);// RemoveColumn();
                if (dt.Rows.Count > 0)
                {
                    btnSaveRecord.Enabled = true;
                    // SqlConnection conn = new SqlConnection(strConnection);
                    SqlConnection conn = new SqlConnection(strConn);
                    SqlCommand cmd = new SqlCommand("insertPopulation", conn);
                    cmd.CommandType = CommandType.StoredProcedure;
                    //  DateTime.date = DateTime.FromOADate();



                    conn.Open();
                    // cmd.Parameters.Clear();
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {


                        cmd.Parameters.Add("@s#", SqlDbType.Int).Value = dt.Rows[i]["Sno"];
                        cmd.Parameters.Add("@Country", SqlDbType.VarChar).Value = dt.Rows[i]["Country"];

                        cmd.Parameters.Add("@Year", SqlDbType.VarChar).Value = dt.Rows[i]["Year"];
                        cmd.Parameters.Add("@Population", SqlDbType.Money).Value = dt.Rows[i]["total_Population"];
                        cmd.Parameters.Add("@Density", SqlDbType.Float).Value = dt.Rows[i]["pop_Density"];
                        cmd.Parameters.Add("@GrowthRate", SqlDbType.Float).Value = dt.Rows[i]["pop_Growthrate"];
                        cmd.Parameters.Add("@BirthRate", SqlDbType.Float).Value = dt.Rows[i]["pop_BirthRate"];
                        cmd.Parameters.Add("@DeathRate", SqlDbType.Float).Value = dt.Rows[i]["pop_DeathRate"];
                        
                      // string firstDayDate = dateNow.AddDays(-(dateNow.Day - 1)).ToString(); //first day

                       // cmd.Parameters.Add("@filename", SqlDbType.VarChar).Value = dt.Rows[i]["filename"]; // fileuploadExcel.PostedFile.FileName; ;
                        //cmd.Parameters.Add("operator_ID_created", SqlDbType.Int).Value = dt.Rows[i]["operator_ID_created"];
                       
                        cmd.ExecuteNonQuery();
                        cmd.Parameters.Clear();
                    }

                    conn.Close();
                    //MessageBox.Show("Congrats! Data Stored Successfully");// due to progress baar

                }
                btnSaveRecord.Enabled = false;
                gvGovtData.DataSource = null;
                //lblPid.Text = "";
              //  lblPname.Text = "";
                ofdPatent.FileName = null;
            }
            catch (Exception ex)
            {
                HadErrors = true;
                bool a = false;
                int b = 0;
                
                b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + "Error Type 1: " + ex.Message);
                switch (b)
                {
                    case 1:

                        break;
                    default:
                        break;
                }
                return false;
            }
            //btnSaveRecord.Enabled = false;
           return true;
           

        }
        /// <summary>
        /// this is parse file button. That check the file before parsing it and then parse that files if there is no error in column and data validation of File.
        /// it also shows the parsed record into a gridview. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnShowResult_Click(object sender, EventArgs e)
        {
             if(!(string.IsNullOrEmpty(ofdPatent.FileName)))
            {
                string fName= ofdPatent.FileName;
                string Extension= Path.GetExtension(fName);
                string hasHeader= "Yes";
                
               Import_To_Grid(fName, Extension,hasHeader );

            }
           // Import_To_Grid(string FilePath, string Extension, string isHDR);         
           // parserResult(ofdPatent.FileName);
           
        }
        /// <summary>
        /// It save the parsed Record into the database
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnSaveRecord_Click(object sender, EventArgs e)
        {
           // MessageBox.Show("Record Saved Successfully!");
            
            
            try
            {
                if (SaveParsedRecord(ofdPatent.FileName))
                {
                    SuccessLog(ofdPatent.FileName);
                    MessageBox.Show("Record Saved Successfully!");
                }
                else
                {
                    MessageBox.Show("Error while saving Records,Please Check Error Log!!!");
                }
            }
            catch (Exception ex)
            {
                int b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + ex.Message);
            }
             
        }
        /// <summary>
        /// It check the connection before browsing the directoy. if connection or its credential is ok then open file browser dialog to brows the directory of Patent files.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// 
        
        private void btnSelectDirectory_Click(object sender, EventArgs e)
        {
            if (!(string.IsNullOrEmpty(txtOperatorName.Text) || (string.IsNullOrEmpty(txtdbserver.Text) || (string.IsNullOrEmpty(txtdbserver.Text)))))
            {
                int a= checkConnection();
                if (a != -1)
                {
                    directorySelection();
                }
            }
            else
            {
                //MessageBox.Show("Please review Operater name or databse credential!");
                MessageBox.Show(this, "Data Base Credentials: Please review Operator Name or Database Credentials!!!", "Invalid Database Credentials !!!", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            
           
        }
        
        /// <summary>
        /// This function select the directory of Patent Excell files and shows the number of files.
        /// In case of any error it writes the error in the Error log and skip that file and if file is successfully imported then it make an entry to the successlog accordingly.
        /// </summary>
        /// 
        
         
        public void directorySelection()
        {
            DialogResult btnResult;
            if (txtdbserver.Text == "")
            {
                MessageBox.Show("Please provide valid database credentials first");
                        return;
            }

            
            // brows directory from a specific location 
            DialogResult result = fbdPatent.ShowDialog();
            if (result == DialogResult.OK)
            {
                
                if (rdbtnDirectory.Checked)
                {
                    HadErrors = false;
                    //new code to implent
                    //.........................
                    string msg = "Parse and Save Files";
                    string caption = "Patent Excel2SQL Parser";
                    
                   // DialogResult result;
                    //result = MessageBox.Show(this, msg, caption, buttons, MessageBoxIcon.Question);
            
                    //.............
                    int barcount = 0;
                    progressBar1.Value = 0;
                    // filters files and save them in an array of strings
                    string[] files = Directory.GetFiles(fbdPatent.SelectedPath, "*.xls*", SearchOption.TopDirectoryOnly);

                   btnResult = MessageBox.Show(this,"Files found: " + files.Length.ToString()+"\n"+msg, caption,MessageBoxButtons.YesNo, MessageBoxIcon.Question);

                    
                    progressBar1.Maximum = files.Length;
                    if (btnResult == DialogResult.No)
                    {
                        return;
                    }
                    if(btnResult == DialogResult .Yes )
                    {
                        int v;
                       
                        for (int i = 0; i < files.Length; i++)
                        {
                            lblfname.Text = files[i];
                            //fnumber = i + 1;
                            lblfnumber.Text = i.ToString();
                            //check data validation before data persing and saving records into database.
                            v = CheckHeadersAndValidateData(files[i]);
                            if (v == 1)
                            {
                                if (SaveParsedRecord(files[i]))
                                {
                                    SuccessLog(files[i])

                                    ;
                                    //barcount = barcount + (100 / files.Length);
                                    if (i + 1 == files.Length)
                                    {

                                        progressBar1.Value = i;
                                        lblPbar.Text = "100% Completed";
                                        progressBar1.Visible = false;
                                    }
                                    else
                                    {
                                        progressBar1.Value = i;
                                        //lblPbar.Text = i.ToString() + "% Completed";
                                        progressBar1.Visible = true;
                                    }
                                    progressBar1.PerformStep();
                                }
                                else
                                {
                                    // break;
                                    // i++;
                                    int b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + "Skipping this file. ");
                                    HadErrors = true;

                                }
                            }
                            else
                            {
                                int k = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + "Data Validation Error:Skipping this file. ");
                                HadErrors = true;
                            }

                      }
                    }
                }
            }
            if (HadErrors)
            {
                MessageBox.Show("Had errors uploading files. Please look at errorlog in directory [MyApplicationDir]\\Logs");
            }

        }
        private void rdbtnFile_CheckedChanged(object sender, EventArgs e)
        {
            
        }

        private void groupBox1_Enter(object sender, EventArgs e)
        {

        }
        
        private string sLogFormat;
        private string sErrorTime;
        private string arrow = "\t"; 
        /// <summary>
        /// create and set the Name of the log file.
        /// </summary>
        /// <returns>string</returns>
        public string CreateLogFiles()
{
    //sLogFormat used to create log files format :
        // dd/mm/yyy hh:mm:ss AM/PM => File Nname => Error Message=> Line Number
    
    sLogFormat = DateTime.Now.ToShortDateString().ToString()+" "+DateTime.Now.ToLongTimeString().ToString()+" ==> ";

            
    //this variable used to create log filename format "
    //for example filename : ErrorLogYYYYMMDD
    string sYear    = DateTime.Now.Year.ToString();
    string sMonth    = DateTime.Now.Month.ToString();
    string sDay    = DateTime.Now.Day.ToString();
    sErrorTime = sYear+sMonth+sDay;
    return sErrorTime;
}
        /// <summary>
    /// Error Log
    /// </summary>
    /// <param name="sPathName"></param>
    /// <param name="sErrMsg"></param>
    ///       
        public int ErrorLog(string sPathName, string sErrMsg)
        { 
            //bool a= true;
            int b = 1;
            string err = CreateLogFiles();
            string directorystring = System.Windows.Forms.Application.StartupPath.ToString() + "/Logs";
            if (!(Directory.Exists(directorystring)))
            {
                Directory.CreateDirectory(directorystring );
            }
            StreamWriter sw = new StreamWriter(sPathName + sErrorTime + ".txt", true);
            sw.WriteLine(sLogFormat + sErrMsg);
            sw.Flush();
            sw.Close();
            return b;
        }
        /// <summary>
/// It create a success log in the specified directory and write the entries into success log accoringly if file is successfully imported into the database.
/// It also move successfully moved Files to Archieve Directoy.
/// </summary>
/// <param name="SuccessFile"></param>
        public void SuccessLog(string SuccessFile)
        {
            try
            {
                //bool a= true;
                int b = 1;
                //delete possibly existing trailing \
                if (SuccessFile.LastIndexOf("\\") == SuccessFile.Length - 1)
                    SuccessFile = SuccessFile.Substring(0, SuccessFile.Length - 1);

                string strDestination = SuccessFile.Insert(SuccessFile.LastIndexOf("\\") + 1, "ARCHIVE\\");

                string err = CreateLogFiles();
                string directorystring = System.Windows.Forms.Application.StartupPath.ToString() + "/Logs";
                if (!(Directory.Exists(directorystring)))
                {
                    Directory.CreateDirectory(directorystring);
                }
                StreamWriter sw = new StreamWriter(directorystring + "\\SuccessLog" + sErrorTime + ".txt", true);
                sw.WriteLine("MOVED " + SuccessFile + "=> " + strDestination);
                // Move fiel from source Directory to Archieve Directory: if Destination Directory does not Exist then it Create that directoy 
                if (!(Directory.Exists(Path.GetDirectoryName(strDestination))))
                {
                    Directory.CreateDirectory(Path.GetDirectoryName(strDestination));
                }
                else
                {
                    File.Move(SuccessFile, strDestination);
                }
                //if(Directory.Exists(strDestination.len))
                sw.Flush();
                sw.Close();
            }
            catch (Exception ex)
            {
                int b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), "File Moving Error!" + "\t"+ ex.Message);
            }
            
        }
        /// <summary>
        /// it validate the database credential and get the operator ID. It makes an entry to Error log in case of any error.
        /// </summary>
        /// <param name="OperatorName"></param>
        /// <returns>integer: success:1, Error: -1</returns>
        public int GetOperatorID(string OperatorName)
      {
          try
          { object a;
              string strSecurity = "";

              // Added and changed by Nils to make SQL logins work
              if (txtdbserver.Text == "")
                  return -1;

              if (txtPassword.Text == "")
              {
                  strSecurity = ";Integrated Security=True";
              }
              string strConn = "Data Source=" + txtdbserver.Text + ";Initial Catalog=" + txtdbName.Text + "; User ID=" + txtUserName.Text + ";Password=" + txtPassword.Text + strSecurity + ";";
              
              SqlConnection conn = new SqlConnection(strConn);//old statement before using
                  conn.Open();
              


                  //object a;

                  SqlCommand cmd = new SqlCommand("SELECT operator_ID FROM ENTITY.dbo.Operator WHERE name=@name", conn);
                 // SqlCommand cmd = new SqlCommand("SELECT operator_ID FROM tbl_Test WHERE name=@name", conn);
                  cmd.CommandType = CommandType.Text;
                  cmd.CommandTimeout = 5;   //NILS: Needs useful timeout in case doesn't work
                  cmd.Parameters.Add("@name", SqlDbType.VarChar).Value = txtOperatorName.Text;

                  a = cmd.ExecuteScalar();


                  conn.Close();

                  if (string.IsNullOrEmpty(Convert.ToString(a)))
                  {
                      MessageBox.Show("Username doesn't match any operator in the database!", "Cannot proceed", MessageBoxButtons.OK);
                      bool c = false;
                      int b = 0;
                      //  MessageBox.Show(ex.Message + lblfname.Text + "Galti noo pharr");
                      //  ErrorLog(("C:/Users/Usman/Documents/Visual Studio 2010/Projects/PatentExcel2SQL/PatentExcel2SQL/Logs/ErrorLog"), lblfname.Text+"\t" + ex.Message) ;
                      b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), "Username doesn't match any operator in the database!" + "Cannot proceed");

                      switch (b)
                      {
                          case 1:

                              break;
                          default:
                              break;

                      }
                      return -1;

                  }
                  else
                  {
                      return Convert.ToInt32(a);
                  }
              //}// end try
              //catch (Exception ex)
              //{
              //    MessageBox.Show("please check credential" + ex.Message);
              //    return -1;
              //}

          }//end try
          catch (Exception ex)
          {
              bool a = false;
              int b = 0;
              
              b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + ex.Message + "\n"+"Please correct the connection credential!");
              MessageBox.Show("Please correct/provide the database connection credentials!" );
              switch (b)
              {
                  case 1:

                      break;
                  default:
                      break;
                      
              }
              return -1;
          }
             
        }
        private void btnCheckConnection_Click(object sender, EventArgs e)
      {
         // int a = GetOperatorID(lblOperatorName.Text);
          checkConnection();
          //lblOperatorID.Text = a.ToString();
      }
        /// <summary>
        /// This function check the connection and validate credentials.It also call a function to get the operator id
       /// </summary>
        private int checkConnection()
      {
          //int a = GetOperatorID(lblOperatorName.Text);
          int a = OperatorID(lblOperatorName.Text);

          lblOperatorID.Text = a.ToString();
          return a;
      }     
        /// <Check Database Connection and operator>a
       /// It consume the Can open function and get the operator ID after validating the credentials.
       /// </Check Database Connection and Operator>
       /// <param name="operatorName"></param>
       /// <returns>integer: operator id</returns>
        public int OperatorID(string operatorName)
      {
          try
          {
              object a;
              string strSecurity = "";

              // Added and changed by usman to make SQL logins work
              if (txtdbserver.Text == "") { return -1; }
              if (txtPassword.Text == "") { strSecurity = ";Integrated Security=True"; }
              string strConn = "Data Source=" + txtdbserver.Text + ";Initial Catalog=" + txtdbName.Text + "; User ID=" + txtUserName.Text + ";Password=" + txtPassword.Text + strSecurity + ";";
              //SqlConnection conn = new SqlConnection(strConn);//old statement before using
              using (var connection = new SqlConnection(strConn)) {
                  if (connection.CanOpen())
                  {
                      connection.Open();
                    // SqlCommand cmd = new SqlCommand("SELECT operator_ID FROM ENTITY.dbo.Operator WHERE name=@name", connection);
                      SqlCommand cmd = new SqlCommand("SELECT operator_ID FROM tbl_User WHERE name=@name", connection);
                      cmd.CommandType = CommandType.Text;
                      cmd.CommandTimeout = 5;   //NILS: Needs useful timeout in case doesn't work
                      cmd.Parameters.Add("@name", SqlDbType.VarChar).Value = txtOperatorName.Text;
                      a = cmd.ExecuteScalar();
                      connection.Close();
                      if (string.IsNullOrEmpty(Convert.ToString(a)))
                      {
                          MessageBox.Show("Username doesn't match any operator in the database!", "Cannot proceed", MessageBoxButtons.OK);
                          bool c = false;
                          int b = 0;
                          b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), "Username doesn't match any operator in the database!" + "Cannot proceed");
                          switch (b)
                          {
                              case 1:
                                  break;
                              default:
                                  break;
                          }
                          return -1;
                      }// end if
                      else { return Convert.ToInt32(a); }
                  }//end if:CanOpen()
                  else { MessageBox.Show("connection cannot be open is called"); return -1; }                
                }// end using
          }//end try
          catch (Exception ex)
          {
              bool a = false;
              int b = 0;

              b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + ex.Message + "\n" + "Please correct the connection credential!");
              MessageBox.Show("Please correct/provide the database connection credentials!");
              switch (b)
              {
                  case 1:

                      break;
                  default:
                      break;

              }
              return -1;
          }// end catch

      }       
        /// <check and validate data>
    /// This functin check the patent excel file headers and also validate the data before parsing it.
    /// </check adn validate data>
    /// <param name="f"></param>
    /// <returns>integer 0 or 1</returns>
    /// 
        
    private int CheckHeadersAndValidateData(string f)
     {
       

            int validator = 0;
            System.Data.DataTable dt;
            bool status = false;
            int erow;
            int parsedInt;
            string parsedString;
            double parsedDouble;
            float parsedFloat;
            // Nils: validate the crucial assumptions of what is where !!!!
            //-------------------------------------------------------
           try
           {
             dtvalidator = null;
            dt = RemoveColumn(f);
            bool bln_StartRowFound = false;
            bool bln_PassedCheck = false;
            int start = 0;


            int row = 0;
            // search the row in which any of the columns contains "Subsidiary name"
            for (int r = 0; r < dt.Rows.Count; r++)
            {
                for (int c = 0; c < dt.Columns.Count; c++)
                {
                    if (dt.Rows[r][c].ToString() == "Sno")
                    {
                        bln_StartRowFound = true;
                        row = r;
                        //break;
                    }
                    //:::::::
                    if ((dt.Rows[r][c].ToString() == "(Country)") && (dt.Rows[r][c + 1].ToString() == ("(Year)")))
                    {

                        status = true;


                        break;
                    }
                    //______
                }
                if (bln_StartRowFound && status)
                {
                    break;
                }
            }
            // test that row (header)
            if (bln_StartRowFound == false)
            {
                int a = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"),
                    lblfname.Text + "\t" + "Table column headers not supported");
                validator = 0;
                return validator;
                //throw new ArgumentException("Table column headers not supported");
            }
            else
            {
                // test header
                // Assumption Set 1:
                //  dt.Rows[i][1]     name_c
                //  dt.Rows[i][16]  BVD_ID_c
                // dt.Rows[i][7] = share direct
                // dt.Rows[i][8] = share total
                // dt.Rows[i][9] = hlevel
                //Assumtion Set 2:
                //dt.Rows[row][1].ToString() == "Subsidiary name"
                //dt.Rows[row][2].ToString() == "Coun- try"
                //dt.Rows[row][3].ToString() == "City"
                //dt.Rows[row][4].ToString() == "Type"
                //dt.Rows[row][5].ToString() == ""
                //dt.Rows[row][14].ToString() == "Total"
                //dt.Rows[row][15].ToString() == ""
                //dt.Rows[row][16].ToString() == "BvD ID number"
                //dt.Rows[row][17].ToString() == "Ticker"


                if (!(
                        dt.Rows[row][1].ToString() == "Sno"
                        & dt.Rows[row][2].ToString() == "Country"
                        & dt.Rows[row][3].ToString() == "Year"
                        & dt.Rows[row][4].ToString() == "total_Population"
                        & dt.Rows[row][5].ToString() == "pop_Density"
                        & dt.Rows[row][6].ToString() == "pop_Growthrate"
                        & dt.Rows[row][7].ToString() == "pop_Birthrate"
                        & dt.Rows[row][8].ToString() == "pop_Deathrate"
                    ) )
                    // & dt.Rows[row][18].ToString() == "Address"
                    //& dt.Rows[row][19].ToString() == "Zip code"
                    //& dt.Rows[row][20].ToString() == "Web site address"
                    
                {
                    // Header not ok
                    int b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" + "Table column headers not supported");

                    //throw new ArgumentException("Table column headers not supported");
                    validator = 0;
                    return validator;
                }
                else
                {

                    if (!(status == true))
                    {

                        int a = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"),
                        lblfname.Text + "\t" + "Data Validation:File is not in proper format: skipped ");

                        //throw new ArgumentException("File is not in the Euro");
                        validator = 0;
                        return validator;


                    }//end if
                    //after checking the whether file is in euro or not
                    else
                    {
                        //string a= "PK";
                        //// MessageBox.Show("File is in euro" + start.ToString());
                        // validator= 1;
                        // return validator;

                        string Sno;
                        string Country;
                        string Year;
                        string total_Population;
                        string pop_Density;
                        string pop_Growthrate;
                        string pop_Birthrate;
                        string pop_Deathrate;
                                               
                        for (int r = 0; r < dt.Rows.Count; r++)
                        {
                            //  for (int c = 0; c < dt.Columns.Count; c++) { 
                            //start from subidiary_name
                            Sno= dt.Rows[r][1].ToString();
                            Country= dt.Rows[r][2].ToString();
                            Year= dt.Rows[r][3].ToString();
                            total_Population= dt.Rows[r][4].ToString();
                            pop_Density= dt.Rows[r][5].ToString();
                            pop_Growthrate= dt.Rows[r][6].ToString();
                            pop_Birthrate= dt.Rows[r][7].ToString();
                            pop_Deathrate= dt.Rows[r][8].ToString();
                            r++;
                        }
                    }
                }
            } return validator; // 01102014
        }//end try
        catch (Exception ex)
        {
            bool a = false;
            int b = 0;

            b = ErrorLog((System.Windows.Forms.Application.StartupPath.ToString() + "/Logs/ErrorLog"), lblfname.Text + "\t" +  " Data validation:"+ ex.Message);
            switch (b)
            {
                case 1:

                    break;
                default:

                    break;
            }
            validator = 0;
            return validator;
        }        
    }

        
        }
  }// end class
    /// <summary>
    /// This is extension class that contain different SQL extension methods.
    /// </summary>
    public static class extension
    {
        /// <summary>
        /// this method checks whether a connection can be opened or not!
        /// </summary>
        /// <param name="connection"></param>
        /// <returns>bool</returns>
        public static  bool CanOpen(this SqlConnection connection)
        {
            try
            {
                if (connection == null) { return false; }
                connection.Open();
                var canOpen = connection.State == ConnectionState.Open;
                connection.Close();
                return canOpen;
            }
            catch { return false; }
            
        }//end CanOpen
        
    }//end extension class
// end name space
