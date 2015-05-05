using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using Libero.FusionCharts;
using System.Configuration;
using System.Web.Configuration;
using System.Data.Sql;
using System.Data.SqlClient;


public partial class FDeathrate : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

        //MSColumn2DLineDYChart oChart = new MSColumn2DLineDYChart();
        // MSColumn3DLineDYChart oChart = new MSColumn3DLineDYChart();
        // MSBar2DChart oChart = new MSBar2DChart();
        // MSColumn3DChart oChart = new MSColumn3DChart();

        MSLineChart oChart = new MSLineChart();
        //  MSArea2DChart oChart = new MSArea2DChart();

        //Pie3DChart oChart = new Pie3DChart();
        //  FunnelChart oChart = new FunnelChart();
        //StackedColumn3DChart oChart = new StackedColumn3DChart();
        // StackedBar2DChart oChart = new StackedBar2DChart();

        // Set properties
        oChart.Background.BgColor = "ffffff";
        oChart.Background.BgAlpha = 50;
        oChart.ChartTitles.Caption = "Death of France (Death/1000 Population) ";

        // Set a template
        oChart.Template = new Libero.FusionCharts.Template.OfficeDarkTemplate();
        //  oChart.Template = new Libero.FusionCharts.Template.OfficeTemplate();
        //oChart.Template = new Libero.FusionCharts.Template.OceanTemplate();
        //oChart.Template = new Libero.FusionCharts.Template.OceanTemplate();

        // Set data   *DataTable OR IList<T>
        DataTable dtSample = GetpopulationDataTable();//GetSalesDataTable();
        oChart.DataSource = dtSample;
        oChart.DataCategoryTextField = "Year";
        oChart.DataSeriesTextField = "Country";
        oChart.DataSeriesValueField = "Deathrate";
        //oChart.DataTextField = "Year";
        //oChart.DataValueField = "Growthrate";
        // oChart.DataTextField = "Country";
        //oChart.DataSeriesValueField = "Usman Sb";

        // Set the special category (Secundary. Will by draw as a line, not a column)
        //  oChart.SetAxisType("Birthrate", CombinationAxisType.Secundary);

        // Link the WebControl and the Chart
        chtProductSales.ShowChart(oChart);


    }
    private DataTable GetpopulationDataTable()
    {
        //DataTable dtPopulation = new DataTable();
        string constring = ConfigurationManager.ConnectionStrings["dbGovtDataPopulationConnectionString"].ToString();
        using (SqlConnection con = new SqlConnection(constring))
        {
            using (SqlCommand cmd = new SqlCommand("SELECT Year,Country,Deathrate FROM tblPopupation WHERE Country='France'", con))
            {
                cmd.CommandType = CommandType.Text;
                using (SqlDataAdapter sda = new SqlDataAdapter(cmd))
                {
                    using (DataTable dt = new DataTable())
                    {
                        sda.Fill(dt);
                        gvAPopulation.DataSource = dt;
                        gvAPopulation.DataBind();
                        return dt;
                        //dataGridView1.DataSource = dtPopulation;
                    }
                }
            }

        }

    }
    private string ConvertSortDirectionToSql(SortDirection sortDirection)
    {
        string newSortDirection = String.Empty;

        switch (sortDirection)
        {
            case SortDirection.Ascending:
                newSortDirection = "ASC";
                break;

            case SortDirection.Descending:
                newSortDirection = "DESC";
                break;
        }

        return newSortDirection;
    }

    protected void gvAPopulation_PageIndexChanging(object sender, GridViewPageEventArgs e)
    {
        gvAPopulation.PageIndex = e.NewPageIndex;
        gvAPopulation.DataBind();
    }

    protected void gvAPopulation_Sorting(object sender, GridViewSortEventArgs e)
    {
        DataTable dataTable = gvAPopulation.DataSource as DataTable;

        if (dataTable != null)
        {
            DataView dataView = new DataView(dataTable);
            dataView.Sort = e.SortExpression + " " + ConvertSortDirectionToSql(e.SortDirection);

            gvAPopulation.DataSource = dataView;
            gvAPopulation.DataBind();
        }
    }

}