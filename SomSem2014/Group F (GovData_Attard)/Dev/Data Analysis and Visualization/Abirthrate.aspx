<%@ Register Assembly="Libero.FusionCharts" Namespace="Libero.FusionCharts.Control" TagPrefix="fcl" %>
<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Abirthrate.aspx.cs" Inherits="Abirthrate" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<fcl:FChart runat="server" ID="chtProductSales" Width="800" Height="500" Font-Size="Large "  />
    
        <asp:GridView ID="gvAPopulation" runat="server" style="text-align: left" 
         OnPageIndexChanging="gvAPopulation_PageIndexChanging" OnSorting="gvAPopulation_Sorting"
            AllowPaging="True" AllowSorting="True" CellPadding="4" ForeColor="#333333" 
            GridLines="None" Width="751px" HorizontalAlign="Center">
            <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
            <EditRowStyle BackColor="#999999" />
            <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
            <HeaderStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
            <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
            <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
            <SortedAscendingCellStyle BackColor="#E9E7E2" />
            <SortedAscendingHeaderStyle BackColor="#506C8C" />
            <SortedDescendingCellStyle BackColor="#FFFDF8" />
            <SortedDescendingHeaderStyle BackColor="#6F8DAE" />
        </asp:GridView>
    
</asp:Content>

