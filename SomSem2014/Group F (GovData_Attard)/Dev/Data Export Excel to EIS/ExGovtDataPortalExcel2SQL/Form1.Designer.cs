namespace EXGovtDataPortalExcel2SQL
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.ofdPatent = new System.Windows.Forms.OpenFileDialog();
            this.btnSelectFile = new System.Windows.Forms.Button();
            this.gvGovtData = new System.Windows.Forms.DataGridView();
            this.btnShowResult = new System.Windows.Forms.Button();
            this.btnSaveRecord = new System.Windows.Forms.Button();
            this.fbdPatent = new System.Windows.Forms.FolderBrowserDialog();
            this.btnSelectDirectory = new System.Windows.Forms.Button();
            this.rdbtnFile = new System.Windows.Forms.RadioButton();
            this.rdbtnDirectory = new System.Windows.Forms.RadioButton();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.lblPbar = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.lblfname = new System.Windows.Forms.Label();
            this.lblfnumber = new System.Windows.Forms.Label();
            this.gbDatabase = new System.Windows.Forms.GroupBox();
            this.btnCheckConnection = new System.Windows.Forms.Button();
            this.txtPassword = new System.Windows.Forms.TextBox();
            this.txtUserName = new System.Windows.Forms.TextBox();
            this.txtdbName = new System.Windows.Forms.TextBox();
            this.txtdbserver = new System.Windows.Forms.TextBox();
            this.lblPassword = new System.Windows.Forms.Label();
            this.lblUsername = new System.Windows.Forms.Label();
            this.lblDatabase = new System.Windows.Forms.Label();
            this.lblServer = new System.Windows.Forms.Label();
            this.lblteststr = new System.Windows.Forms.Label();
            this.txtOperatorName = new System.Windows.Forms.TextBox();
            this.lblOperatorName = new System.Windows.Forms.Label();
            this.lblOperatorID = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.gvGovtData)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.gbDatabase.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnSelectFile
            // 
            this.btnSelectFile.Location = new System.Drawing.Point(9, 50);
            this.btnSelectFile.Name = "btnSelectFile";
            this.btnSelectFile.Size = new System.Drawing.Size(75, 23);
            this.btnSelectFile.TabIndex = 11;
            this.btnSelectFile.Text = "Select File";
            this.btnSelectFile.UseVisualStyleBackColor = true;
            this.btnSelectFile.Click += new System.EventHandler(this.button1_Click);
            // 
            // gvGovtData
            // 
            this.gvGovtData.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gvGovtData.Location = new System.Drawing.Point(12, 164);
            this.gvGovtData.Name = "gvGovtData";
            this.gvGovtData.Size = new System.Drawing.Size(1244, 300);
            this.gvGovtData.TabIndex = 17;
            // 
            // btnShowResult
            // 
            this.btnShowResult.Location = new System.Drawing.Point(90, 50);
            this.btnShowResult.Name = "btnShowResult";
            this.btnShowResult.Size = new System.Drawing.Size(75, 23);
            this.btnShowResult.TabIndex = 12;
            this.btnShowResult.Text = "Parse File";
            this.btnShowResult.UseVisualStyleBackColor = true;
            this.btnShowResult.Click += new System.EventHandler(this.btnShowResult_Click);
            // 
            // btnSaveRecord
            // 
            this.btnSaveRecord.Location = new System.Drawing.Point(171, 50);
            this.btnSaveRecord.Name = "btnSaveRecord";
            this.btnSaveRecord.Size = new System.Drawing.Size(93, 23);
            this.btnSaveRecord.TabIndex = 13;
            this.btnSaveRecord.Text = "Save Record";
            this.btnSaveRecord.UseVisualStyleBackColor = true;
            this.btnSaveRecord.Click += new System.EventHandler(this.btnSaveRecord_Click);
            // 
            // btnSelectDirectory
            // 
            this.btnSelectDirectory.AccessibleName = "";
            this.btnSelectDirectory.Location = new System.Drawing.Point(90, 50);
            this.btnSelectDirectory.Name = "btnSelectDirectory";
            this.btnSelectDirectory.Size = new System.Drawing.Size(123, 23);
            this.btnSelectDirectory.TabIndex = 11;
            this.btnSelectDirectory.Text = "Select Directory";
            this.btnSelectDirectory.UseVisualStyleBackColor = true;
            this.btnSelectDirectory.Click += new System.EventHandler(this.btnSelectDirectory_Click);
            // 
            // rdbtnFile
            // 
            this.rdbtnFile.AutoSize = true;
            this.rdbtnFile.Location = new System.Drawing.Point(6, 17);
            this.rdbtnFile.Name = "rdbtnFile";
            this.rdbtnFile.Size = new System.Drawing.Size(88, 17);
            this.rdbtnFile.TabIndex = 12;
            this.rdbtnFile.TabStop = true;
            this.rdbtnFile.Text = "File Selection";
            this.rdbtnFile.UseVisualStyleBackColor = true;
            this.rdbtnFile.CheckedChanged += new System.EventHandler(this.rdbtnFile_CheckedChanged);
            // 
            // rdbtnDirectory
            // 
            this.rdbtnDirectory.AutoSize = true;
            this.rdbtnDirectory.Location = new System.Drawing.Point(14, 19);
            this.rdbtnDirectory.Name = "rdbtnDirectory";
            this.rdbtnDirectory.Size = new System.Drawing.Size(114, 17);
            this.rdbtnDirectory.TabIndex = 13;
            this.rdbtnDirectory.TabStop = true;
            this.rdbtnDirectory.Text = "Directory Selection";
            this.rdbtnDirectory.UseVisualStyleBackColor = true;
            // 
            // progressBar1
            // 
            this.progressBar1.Location = new System.Drawing.Point(452, 118);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(313, 23);
            this.progressBar1.TabIndex = 14;
            // 
            // lblPbar
            // 
            this.lblPbar.AutoSize = true;
            this.lblPbar.Location = new System.Drawing.Point(785, 127);
            this.lblPbar.Name = "lblPbar";
            this.lblPbar.Size = new System.Drawing.Size(0, 13);
            this.lblPbar.TabIndex = 15;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.btnShowResult);
            this.groupBox2.Controls.Add(this.btnSaveRecord);
            this.groupBox2.Controls.Add(this.btnSelectFile);
            this.groupBox2.Controls.Add(this.rdbtnFile);
            this.groupBox2.Location = new System.Drawing.Point(12, 12);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(288, 100);
            this.groupBox2.TabIndex = 16;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Single File Selection";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.btnSelectDirectory);
            this.groupBox3.Controls.Add(this.rdbtnDirectory);
            this.groupBox3.Location = new System.Drawing.Point(883, 12);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(288, 100);
            this.groupBox3.TabIndex = 17;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Select Excel Files Folder";
            // 
            // lblfname
            // 
            this.lblfname.AutoSize = true;
            this.lblfname.Location = new System.Drawing.Point(21, 118);
            this.lblfname.Name = "lblfname";
            this.lblfname.Size = new System.Drawing.Size(0, 13);
            this.lblfname.TabIndex = 18;
            // 
            // lblfnumber
            // 
            this.lblfnumber.AutoSize = true;
            this.lblfnumber.Location = new System.Drawing.Point(21, 148);
            this.lblfnumber.Name = "lblfnumber";
            this.lblfnumber.Size = new System.Drawing.Size(0, 13);
            this.lblfnumber.TabIndex = 19;
            // 
            // gbDatabase
            // 
            this.gbDatabase.Controls.Add(this.btnCheckConnection);
            this.gbDatabase.Controls.Add(this.txtPassword);
            this.gbDatabase.Controls.Add(this.txtUserName);
            this.gbDatabase.Controls.Add(this.txtdbName);
            this.gbDatabase.Controls.Add(this.txtdbserver);
            this.gbDatabase.Controls.Add(this.lblPassword);
            this.gbDatabase.Controls.Add(this.lblUsername);
            this.gbDatabase.Controls.Add(this.lblDatabase);
            this.gbDatabase.Controls.Add(this.lblServer);
            this.gbDatabase.Location = new System.Drawing.Point(136, 549);
            this.gbDatabase.Name = "gbDatabase";
            this.gbDatabase.Size = new System.Drawing.Size(629, 142);
            this.gbDatabase.TabIndex = 2;
            this.gbDatabase.TabStop = false;
            this.gbDatabase.Text = "Database Settings";
            // 
            // btnCheckConnection
            // 
            this.btnCheckConnection.Location = new System.Drawing.Point(527, 52);
            this.btnCheckConnection.Name = "btnCheckConnection";
            this.btnCheckConnection.Size = new System.Drawing.Size(96, 23);
            this.btnCheckConnection.TabIndex = 8;
            this.btnCheckConnection.Text = "Test Connection";
            this.btnCheckConnection.UseVisualStyleBackColor = true;
            this.btnCheckConnection.Click += new System.EventHandler(this.btnCheckConnection_Click);
            // 
            // txtPassword
            // 
            this.txtPassword.Location = new System.Drawing.Point(135, 119);
            this.txtPassword.Name = "txtPassword";
            this.txtPassword.PasswordChar = '*';
            this.txtPassword.Size = new System.Drawing.Size(388, 20);
            this.txtPassword.TabIndex = 5;
            this.txtPassword.Text = "123";
            // 
            // txtUserName
            // 
            this.txtUserName.Location = new System.Drawing.Point(135, 88);
            this.txtUserName.Name = "txtUserName";
            this.txtUserName.Size = new System.Drawing.Size(388, 20);
            this.txtUserName.TabIndex = 4;
            this.txtUserName.Text = "sa";
            // 
            // txtdbName
            // 
            this.txtdbName.Location = new System.Drawing.Point(135, 57);
            this.txtdbName.Name = "txtdbName";
            this.txtdbName.Size = new System.Drawing.Size(388, 20);
            this.txtdbName.TabIndex = 3;
            this.txtdbName.Text = "dbGovtDataPopulation";
            // 
            // txtdbserver
            // 
            this.txtdbserver.Location = new System.Drawing.Point(135, 20);
            this.txtdbserver.Name = "txtdbserver";
            this.txtdbserver.Size = new System.Drawing.Size(388, 20);
            this.txtdbserver.TabIndex = 2;
            this.txtdbserver.Text = "USMAN-PC\\SQLEXPRESS";
            // 
            // lblPassword
            // 
            this.lblPassword.AutoSize = true;
            this.lblPassword.Location = new System.Drawing.Point(10, 126);
            this.lblPassword.Name = "lblPassword";
            this.lblPassword.Size = new System.Drawing.Size(56, 13);
            this.lblPassword.TabIndex = 3;
            this.lblPassword.Text = "Password:";
            // 
            // lblUsername
            // 
            this.lblUsername.AutoSize = true;
            this.lblUsername.Location = new System.Drawing.Point(10, 88);
            this.lblUsername.Name = "lblUsername";
            this.lblUsername.Size = new System.Drawing.Size(63, 13);
            this.lblUsername.TabIndex = 2;
            this.lblUsername.Text = "User Name:";
            // 
            // lblDatabase
            // 
            this.lblDatabase.AutoSize = true;
            this.lblDatabase.Location = new System.Drawing.Point(7, 57);
            this.lblDatabase.Name = "lblDatabase";
            this.lblDatabase.Size = new System.Drawing.Size(84, 13);
            this.lblDatabase.TabIndex = 1;
            this.lblDatabase.Text = "Database Name";
            // 
            // lblServer
            // 
            this.lblServer.AutoSize = true;
            this.lblServer.Location = new System.Drawing.Point(7, 20);
            this.lblServer.Name = "lblServer";
            this.lblServer.Size = new System.Drawing.Size(72, 13);
            this.lblServer.TabIndex = 0;
            this.lblServer.Text = "Server Name:";
            // 
            // lblteststr
            // 
            this.lblteststr.AutoSize = true;
            this.lblteststr.Location = new System.Drawing.Point(833, 549);
            this.lblteststr.Name = "lblteststr";
            this.lblteststr.Size = new System.Drawing.Size(0, 13);
            this.lblteststr.TabIndex = 21;
            // 
            // txtOperatorName
            // 
            this.txtOperatorName.Location = new System.Drawing.Point(271, 526);
            this.txtOperatorName.Name = "txtOperatorName";
            this.txtOperatorName.Size = new System.Drawing.Size(388, 20);
            this.txtOperatorName.TabIndex = 1;
            this.txtOperatorName.Text = "usman";
            // 
            // lblOperatorName
            // 
            this.lblOperatorName.AutoSize = true;
            this.lblOperatorName.Location = new System.Drawing.Point(146, 526);
            this.lblOperatorName.Name = "lblOperatorName";
            this.lblOperatorName.Size = new System.Drawing.Size(79, 13);
            this.lblOperatorName.TabIndex = 23;
            this.lblOperatorName.Text = "Operator Name";
            // 
            // lblOperatorID
            // 
            this.lblOperatorID.AutoSize = true;
            this.lblOperatorID.Location = new System.Drawing.Point(683, 526);
            this.lblOperatorID.Name = "lblOperatorID";
            this.lblOperatorID.Size = new System.Drawing.Size(62, 13);
            this.lblOperatorID.TabIndex = 24;
            this.lblOperatorID.Text = "Operator ID";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(894, 122);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(301, 13);
            this.label3.TabIndex = 25;
            this.label3.Text = "Make sure the directory is included in Excel\'s trusted locations!";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 20F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(343, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(534, 31);
            this.label1.TabIndex = 26;
            this.label1.Text = "Exploiting Data from Govt. Data Portals ";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(392, 43);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(409, 29);
            this.label2.TabIndex = 27;
            this.label2.Text = "Excel to SQL Data Export Tool 1.0";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(475, 72);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(246, 26);
            this.label4.TabIndex = 28;
            this.label4.Text = "EIS LAB Summer 2014 ";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1263, 703);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.lblOperatorID);
            this.Controls.Add(this.lblOperatorName);
            this.Controls.Add(this.txtOperatorName);
            this.Controls.Add(this.lblteststr);
            this.Controls.Add(this.gbDatabase);
            this.Controls.Add(this.lblfnumber);
            this.Controls.Add(this.lblfname);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.lblPbar);
            this.Controls.Add(this.progressBar1);
            this.Controls.Add(this.gvGovtData);
            this.Name = "Form1";
            this.Text = "Exploiting Data from Govt Data Portal Excel File Parser";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.gvGovtData)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.gbDatabase.ResumeLayout(false);
            this.gbDatabase.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.OpenFileDialog ofdPatent;
        private System.Windows.Forms.Button btnSelectFile;
        private System.Windows.Forms.DataGridView gvGovtData;
        private System.Windows.Forms.Button btnShowResult;
        private System.Windows.Forms.Button btnSaveRecord;
        private System.Windows.Forms.FolderBrowserDialog fbdPatent;
        private System.Windows.Forms.Button btnSelectDirectory;
        private System.Windows.Forms.RadioButton rdbtnFile;
        private System.Windows.Forms.RadioButton rdbtnDirectory;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.Label lblPbar;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Label lblfname;
        private System.Windows.Forms.Label lblfnumber;
        private System.Windows.Forms.GroupBox gbDatabase;
        private System.Windows.Forms.TextBox txtPassword;
        private System.Windows.Forms.TextBox txtUserName;
        private System.Windows.Forms.TextBox txtdbName;
        private System.Windows.Forms.TextBox txtdbserver;
        private System.Windows.Forms.Label lblPassword;
        private System.Windows.Forms.Label lblUsername;
        private System.Windows.Forms.Label lblDatabase;
        private System.Windows.Forms.Label lblServer;
        private System.Windows.Forms.Button btnCheckConnection;
        private System.Windows.Forms.Label lblteststr;
        private System.Windows.Forms.TextBox txtOperatorName;
        private System.Windows.Forms.Label lblOperatorName;
        private System.Windows.Forms.Label lblOperatorID;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label4;
    }
}

