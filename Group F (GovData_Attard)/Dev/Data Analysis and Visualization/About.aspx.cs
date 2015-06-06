using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Libero.FusionCharts;
using System.Data;

public partial class About : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        /*
        MSColumn2DLineDYChart oChart = new MSColumn2DLineDYChart();

        // Set properties
        oChart.Background.BgColor = "ffffff";
        oChart.Background.BgAlpha = 50;
        oChart.ChartTitles.Caption = "# Mobile in last months";

        // Set a template
        oChart.Template = new Libero.FusionCharts.Template.OfficeDarkTemplate();

        // Set data   *DataTable OR IList<T>
        DataTable dtSample = GetSalesDataTable();
        oChart.DataSource = dtSample;
        oChart.DataCategoryTextField = "Month";
        oChart.DataSeriesTextField = "Brand";
        oChart.DataSeriesValueField = "Quantity";
        //oChart.DataSeriesValueField = "Usman Sb";

        // Set the special category (Secundary. Will by draw as a line, not a column)
        oChart.SetAxisType("Market Average", CombinationAxisType.Secundary);

        // Link the WebControl and the Chart
        chtProductSales.ShowChart(oChart);
         */

    }
    /*
    private DataTable GetSalesDataTable()
    {
        DataTable dt = new DataTable();

        dt.Columns.Add("Brand", typeof(String));
        dt.Columns.Add("Month", typeof(String));
        dt.Columns.Add("Quantity", typeof(int));
        //dt.Columns.Add("Usman Sb", typeof(string));

        dt.Rows.Add("Market Average", "08/2010", 1500);
        dt.Rows.Add("Sansung", "08/2010", 2000);
        dt.Rows.Add("Nokia", "08/2010", 3000);
        dt.Rows.Add("Market Average", "09/2010", 1200);
        dt.Rows.Add("Sansung", "09/2010", 1300);
        dt.Rows.Add("Nokia", "09/2010", 2100);
        dt.Rows.Add("Market Average", "10/2010", 1900);
        dt.Rows.Add("Sansung", "10/2010", 3000);
        dt.Rows.Add("Nokia", "10/2010", 3400);
        dt.Rows.Add("Market Average", "11/2010", 6000);
        dt.Rows.Add("Sansung", "11/2010", 5200);
        dt.Rows.Add("Nokia", "11/2010", 7200);

        return dt;
    }
     */
}
